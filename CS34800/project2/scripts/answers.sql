USE yoon161;

DELIMITER $$
DROP FUNCTION IF EXISTS TOTAL_APPOINTMENT_TIME;
CREATE FUNCTION TOTAL_APPOINTMENT_TIME(physician_id INT) RETURNS INT
BEGIN
  DECLARE noMoreAppointments INT;
  DECLARE curr_start DATETIME;
  DECLARE curr_end DATETIME;
  DECLARE total_time INT DEFAULT 0;
  
  DECLARE appointment_cursor CURSOR FOR
    SELECT `Start`, `End` FROM Appointment
      WHERE Physician = physician_id;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMoreAppointments = 0;

  OPEN appointment_cursor;
  appointment_itr: LOOP
    FETCH appointment_cursor INTO curr_start, curr_end;
    IF noMoreAppointments = 0 THEN
      LEAVE appointment_itr;
    END IF;

    SET total_time = total_time + TIMESTAMPDIFF(HOUR, curr_start, curr_end);

  END LOOP appointment_itr;
  CLOSE appointment_cursor;

  RETURN total_time;

END$$

DROP FUNCTION IF EXISTS DEPT_DETAILS;
CREATE FUNCTION DEPT_DETAILS(dept_id INT) RETURNS INT
BEGIN
  DECLARE curr_physician INT;
  DECLARE temp_time INT;
  DECLARE max_time INT DEFAULT 0;
  DECLARE busiest_physician INT;
  DECLARE noMorePhysicians INT;

  DECLARE physician_cursor CURSOR FOR
    SELECT Physician FROM Affiliated_With
      WHERE Department = dept_id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMorePhysicians = 0;

  OPEN physician_cursor;
  physician_itr:LOOP
    FETCH physician_cursor INTO curr_physician;
    IF noMorePhysicians = 0 THEN
      LEAVE physician_itr;
    END IF;

    SET temp_time = TOTAL_APPOINTMENT_TIME(curr_physician);

    IF temp_time > max_time THEN
      SET max_time = temp_time;
      SET busiest_physician = curr_physician;
    END IF;
  END LOOP physician_itr;
  CLOSE physician_cursor;

  RETURN busiest_physician;
END$$

-- # Write a function and use the return value in a query or echo your output  to screen
SELECT PhysicianID, Name, Position FROM Physician
  WHERE PhysicianID = DEPT_DETAILS(1);

DROP FUNCTION IF EXISTS TOTAL_HRS_WORKED;
CREATE FUNCTION TOTAL_HRS_WORKED(dept_id INT) RETURNS INT
BEGIN
  DECLARE total_time INT DEFAULT 0;
  DECLARE noMorePhysicians INT;
  DECLARE curr_physician INT;

  DECLARE physician_cursor CURSOR FOR
    SELECT Physician FROM Affiliated_With
      WHERE Department = dept_id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMorePhysicians = 0;

  OPEN physician_cursor;
  physician_itr:LOOP
    FETCH physician_cursor INTO curr_physician;
    IF noMorePhysicians = 0 THEN
      LEAVE physician_itr;
    END IF;

    SET total_time = total_time + TOTAL_APPOINTMENT_TIME(curr_physician);
  END LOOP physician_itr;
  CLOSE physician_cursor;

  RETURN total_time;
END$$

DROP FUNCTION IF EXISTS COUNT_APPOINTMENTS;
CREATE FUNCTION COUNT_APPOINTMENTS(physician_id INT) RETURNS INT
BEGIN
  DECLARE noMoreAppointments INT;
  DECLARE curr_appointmentID INT;
  DECLARE total_appointments INT DEFAULT 0;
  
  DECLARE appointment_cursor CURSOR FOR
    SELECT AppointmentID FROM Appointment
      WHERE Physician = physician_id;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMoreAppointments = 0;

  OPEN appointment_cursor;
  appointment_itr: LOOP
    FETCH appointment_cursor INTO curr_appointmentID;
    IF noMoreAppointments = 0 THEN
      LEAVE appointment_itr;
    END IF;

    SET total_appointments = total_appointments + 1;
  END LOOP appointment_itr;
  CLOSE appointment_cursor;

  RETURN total_appointments;

END$$

DROP FUNCTION IF EXISTS NUMBER_OF_APPOINTMENTS;
CREATE FUNCTION NUMBER_OF_APPOINTMENTS(dept_id INT) RETURNS INT
BEGIN
  DECLARE total_appointments INT DEFAULT 0;
  DECLARE noMorePhysicians INT;
  DECLARE curr_physician INT;

  DECLARE physician_cursor CURSOR FOR
    SELECT Physician FROM Affiliated_With
      WHERE Department = dept_id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMorePhysicians = 0;

  OPEN physician_cursor;
  physician_itr:LOOP
    FETCH physician_cursor INTO curr_physician;
    IF noMorePhysicians = 0 THEN
      LEAVE physician_itr;
    END IF;

    SET total_appointments = total_appointments + COUNT_APPOINTMENTS(curr_physician);
  
  END LOOP physician_itr;
  CLOSE physician_cursor;

  RETURN total_appointments;
END$$

DROP PROCEDURE IF EXISTS DEPT_STATS;
CREATE PROCEDURE DEPT_STATS()
BEGIN
  DECLARE noMoreDepartments INT;
  DECLARE curr_department INT;

  DECLARE department_cursor CURSOR FOR
    SELECT DepartmentID FROM Department;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMoreDepartments = 0;

  OPEN department_cursor;
  department_itr:LOOP
    FETCH department_cursor INTO curr_department;
    IF noMoreDepartments = 0 THEN
      LEAVE department_itr;
    END IF;

    SELECT D.Name as Department, P.Name as "Dept head", TOTAL_HRS_WORKED(D.DepartmentID) as "Total hrs worked", NUMBER_OF_APPOINTMENTS(DepartmentID) as "Number of appointments"
      FROM Department as D, Physician as P
      WHERE D.Head = P.PhysicianID AND D.DepartmentID = curr_department
      ORDER BY D.Name ASC;

  END LOOP department_itr;
  CLOSE department_cursor;
END$$

CALL DEPT_STATS();

DROP FUNCTION IF EXISTS IS_NO_OVERLAP;
CREATE FUNCTION IS_NO_OVERLAP(phys_id INT, replace_start DATETIME, replace_end DATETIME) RETURNS INT
BEGIN
  DECLARE overlap BIT DEFAULT 1;
  DECLARE noMoreAppointments INT;
  DECLARE curr_start DATETIME;
  DECLARE curr_end DATETIME;

  DECLARE appointment_cursor CURSOR FOR
    SELECT `Start`, `End` FROM Appointment
      WHERE Physician = phys_id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMoreAppointments = 0;

  OPEN appointment_cursor;
  appointment_itr:LOOP
    FETCH appointment_cursor INTO curr_start, curr_end;
    IF noMoreAppointments = 0 THEN
      LEAVE appointment_itr;
    END IF;

    IF (curr_start <= replace_end) AND (curr_end >= replace_start) THEN
      SET overlap = 0;
      LEAVE appointment_itr;
    END IF;
  
  END LOOP appointment_itr;
  CLOSE appointment_cursor;

  RETURN overlap;
END$$

DROP PROCEDURE IF EXISTS FIND_REPLACEMENT;
CREATE PROCEDURE FIND_REPLACEMENT(IN appt_id INT, IN retire_id INT, IN replace_start DATETIME, IN replace_end DATETIME)
BEGIN
  DECLARE noMorePhysicians INT;
  DECLARE curr_physician INT;

  DECLARE physician_cursor CURSOR FOR
    SELECT A.Physician FROM Appointment as A, Affiliated_With as W1, Affiliated_With as W2
      WHERE A.Physician != retire_id AND A.Physician = W1.Physician AND W2.Physician = retire_id AND W1.Department = W2.Department;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMorePhysicians= 0;

  OPEN physician_cursor;
  physician_itr:LOOP
    FETCH physician_cursor INTO curr_physician;
    IF noMorePhysicians = 0 THEN
      LEAVE physician_itr;
    END IF;

    IF IS_NO_OVERLAP(curr_physician, replace_start, replace_end) = 1 THEN
      UPDATE Appointment SET Physician = curr_physician WHERE AppointmentID = appt_id;
      LEAVE physician_itr;
    END IF;

  END LOOP physician_itr;
  CLOSE physician_cursor;
END $$

DROP PROCEDURE IF EXISTS Retire;
CREATE PROCEDURE RETIRE(IN phy_id INT)
BEGIN
  DECLARE noMoreRetiredAppointments INT;
  DECLARE curr_retiredAppointment INT;
  DECLARE replace_start DATETIME;
  DECLARE replace_end DATETIME;

  DECLARE retiredAppointment_cursor CURSOR FOR
    SELECT AppointmentID, `Start`, `End` FROM Appointment WHERE Physician = phy_id;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET noMoreRetiredAppointments = 0;

  OPEN retiredAppointment_cursor;
  retiredAppointment_itr:LOOP
    FETCH retiredAppointment_cursor INTO curr_retiredAppointment, replace_start, replace_end;
    IF noMoreRetiredAppointments = 0 THEN
      LEAVE retiredAppointment_itr;
    END IF;

    CALL FIND_REPLACEMENT(curr_retiredAppointment, phy_id, replace_start, replace_end);
  END LOOP retiredAppointment_itr;
  CLOSE retiredAppointment_cursor;

  SELECT * FROM Appointment;
END $$
DELIMITER ;

CALL RETIRE (1005); 
#Try changing the value.



