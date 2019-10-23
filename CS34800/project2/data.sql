USE "loginID"
#Physician
INSERT INTO Physician VALUES(1001,'Jimmy','Cardiologist',284976513);
INSERT INTO Physician VALUES(1002,'Brown','Dermatologist',145672789);
INSERT INTO Physician VALUES(1003,'John','Cardiologist',179654328);
INSERT INTO Physician VALUES(1004,'Chris','Obstetrician',128963457);
INSERT INTO Physician VALUES(1005,'Alex','Cardiologist',156324897);
INSERT INTO Physician VALUES(1006,'Jason','Pediatrician',486517895);
INSERT INTO Physician VALUES(1007,'Jacob','Surgical Attending Physician',125786349);
INSERT INTO Physician VALUES(1008,'Robert','Senior attending Physician',489632751);
INSERT INTO Physician VALUES(1009,'Susan','Psychiatrist',765893567);

#Department
INSERT INTO Department VALUES(1,'Cardiology',1001);
INSERT INTO Department VALUES(2,'Obstetrics',1004);
INSERT INTO Department VALUES(3,'Dermatology',1002);
INSERT INTO Department VALUES(4,'Pediatrics Division',1006);
INSERT INTO Department VALUES(5,'Psychiatry',1009);

#Patient
INSERT INTO Patient VALUES(784562198,'Dwayne','1001 Trent St','895-458-7844',7984);
INSERT INTO Patient VALUES(451976325,'Smith','977 Washington St','548-461-1816',5151);
INSERT INTO Patient VALUES(489315678,'Sam','102 Tellow St','864-846-4531',8451);
INSERT INTO Patient VALUES(289784621,'Shawn','7660 Tetlow St','846-845-1658',6843);
INSERT INTO Patient VALUES(946752553,'Rebecca','5501 Yeager St','843-168-7844',5188);
INSERT INTO Patient VALUES(845693217,'Skylar','2550 Holbrook Dr','915-845-1513',1813);

#Nurse
INSERT INTO Nurse VALUES(101,'David','Nurse',TRUE,831831815);
INSERT INTO Nurse VALUES(102,'Jennifer','Nurse',FALSE,218644674);
INSERT INTO Nurse VALUES(103,'Justin','Nurse',TRUE,984321845);
INSERT INTO Nurse VALUES(104,'Hannah','Head Nurse',TRUE,782965421);

#affliated
INSERT INTO Affiliated_With VALUES(1001,1,TRUE);
INSERT INTO Affiliated_With VALUES(1002,3,TRUE);
INSERT INTO Affiliated_With VALUES(1003,1,TRUE);
INSERT INTO Affiliated_With VALUES(1004,2,TRUE);
INSERT INTO Affiliated_With VALUES(1005,1,TRUE);
INSERT INTO Affiliated_With VALUES(1006,4,TRUE);
INSERT INTO Affiliated_With VALUES(1007,1,FALSE);
INSERT INTO Affiliated_With VALUES(1008,4,FALSE);
INSERT INTO Affiliated_With VALUES(1008,5,FALSE);

#Appointment
INSERT INTO Appointment VALUES(1,845693217,101,1001,'2019-11-05 14:30:00','2019-11-05 15:30:00');
INSERT INTO Appointment VALUES(2,784562198,104,1005,'2019-11-15 13:00:00','2019-11-15 15:30:00');
INSERT INTO Appointment VALUES(3,289784621,102,1002,'2019-09-05 12:30:00','2019-09-05 13:30:00');
INSERT INTO Appointment VALUES(4,289784621,101,1002,'2019-11-05 14:30:00','2019-11-05 15:30:00');
INSERT INTO Appointment VALUES(5,946752553,103,1006,'2019-08-24 10:30:00','2019-08-24 11:30:00');
INSERT INTO Appointment VALUES(6,451976325,104,1007,'2019-12-05 11:30:00','2019-12-05 15:30:00');
INSERT INTO Appointment VALUES(7,489315678,103,1004,'2019-07-21 15:30:00','2019-07-21 16:30:00');
INSERT INTO Appointment VALUES(8,489315678,103,1003,'2019-08-21 10:30:00','2019-08-21 13:30:00');
INSERT INTO Appointment VALUES(9,289784621,102,1009,'2019-10-21 16:30:00','2019-10-21 17:30:00');
INSERT INTO Appointment VALUES(10,489315678,103,1008,'2019-10-10 10:30:00','2019-10-10 14:30:00');