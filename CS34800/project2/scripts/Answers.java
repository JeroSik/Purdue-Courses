import java.sql.*;

public class Answers {
  private Connection con;

  public Answers() {
	  try {
		  Class.forName("com.mysql.cj.jdbc.Driver");
	  } catch (ClassNotFoundException e) {
		  e.printStackTrace();
		}
		try {
			String u = "yoon161";
			String p = "Jerosik99";
			con = DriverManager.getConnection("jdbc:mysql://mydb.itap.purdue.edu/yoon161?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", u, p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

  public void getNursesWithMostPatients() {
    String query = "Select NurseID, Name from Nurse";

    try {
		  Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
      String busiestNurse = "";
      int maxPatientCount = -1;
      while (rs.next()) {
			  String nurseID = rs.getString("NurseID");
        String name = rs.getString("Name");
        String nestedQuery = "Select Patient from Appointment where PrepNurse = " + nurseID + " order by Patient DESC";
        Statement stmtNested = con.createStatement();
        ResultSet rsNested = stmtNested.executeQuery(nestedQuery);

        String prevPatient = "";
        int patientCount = 0;
        while (rsNested.next()) {
          String currPatient = rsNested.getString("Patient");
          
          //if (!currPatient.equals(prevPatient)) {
            patientCount++;
          //}
          prevPatient = currPatient;
        }

        if (patientCount >= maxPatientCount) {
          maxPatientCount = patientCount;
          busiestNurse = name;
        }
        rsNested.close();
				stmtNested.close();
			}

      System.out.println("Name is : " + busiestNurse);
      System.out.println("Number of Patients : " + maxPatientCount);

			rs.close();
			stmt.close();
		} catch (SQLException e) {
		  e.printStackTrace();
		}
  }

  public void getAppointmentDetailsOnDate(String date) {
    String query = "Select Patient, PrepNurse, Physician from Appointment where Start like '%" + date + "%'";

    try {
		  Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
      while (rs.next()) {
        String patient = rs.getString("Patient");
        String nestedQuery = "Select SSN, Name, Address, Phone from Patient where SSN = " + patient;
        Statement stmtNested = con.createStatement();
				ResultSet rsNested = stmtNested.executeQuery(nestedQuery);
				while (rsNested.next()) {
				  String ssn = rsNested.getString("SSN");
				  String name = rsNested.getString("Name");
				  String address = rsNested.getString("Address");
				  String phone = rsNested.getString("Phone");
				  System.out.println("Patient SSN is : " + ssn);
				  System.out.println("Patient Name : " + name);
				  System.out.println("Patient Address : " + address);
				  System.out.println("Patient Phone : " + phone);
				}
				
        String physician = rs.getString("Physician");
        nestedQuery = "Select Name, Position from Physician where PhysicianID = " + physician;
        stmtNested = con.createStatement();
				rsNested = stmtNested.executeQuery(nestedQuery);
				while (rsNested.next()) {
				  String name = rsNested.getString("Name");
				  String position = rsNested.getString("Position");
				  System.out.println("Physician Name : " + name);
				  System.out.println("Physician Position : " + position);
				}
        
        String prepNurse = rs.getString("PrepNurse");
        nestedQuery = "Select Name, Position from Nurse where NurseID = " + prepNurse;
        stmtNested = con.createStatement();
				rsNested = stmtNested.executeQuery(nestedQuery);
				while (rsNested.next()) {
				  String name = rsNested.getString("Name");
				  String position = rsNested.getString("Position");
				  System.out.println("Nurse name : " + name);
				  System.out.println("Nurse Position : " + position);
				}
        
        rsNested.close();
				stmtNested.close();
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
		  e.printStackTrace();
		}
  }

  public static void main(String[] args) {
    Answers ans = new Answers();
   
    ans.getNursesWithMostPatients();
    System.out.println("\n");

    ans.getAppointmentDetailsOnDate("08-24");
  }
}
