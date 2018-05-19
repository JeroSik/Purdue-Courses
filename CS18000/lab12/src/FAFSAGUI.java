/**
 *
 *CS180 - Lab 11
 *
 *A GUI that mimics FAFSA.
 *
 *@author Youngsik Yoon, yoon161@purdue.edu, 4
 *
 *@version 1.0 Nov 9, 2017
 *
 */
import javax.swing.JOptionPane;

public class FAFSAGUI {
    public static void main(String[] args) {
        boolean welcome = true;
        boolean validAge;
        boolean validCredit;
        boolean validStudentIncome;
        boolean validParentIncome;
        int age;
        int creditHours;
        double studentIncome;
        double parentIncome;
        String[] gradeLevel = {"Freshman", "Sophomore", "Junior", "Senior", "Graduate"};

        boolean isAcceptedStudent;
        boolean isSSregistered;
        boolean hasSSN;
        boolean hasValidResidency;
        boolean isDependent;
        do {
            JOptionPane.showMessageDialog(null, "Welcome to the FAFSA!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
            int programAcceptance = JOptionPane.showOptionDialog(null, "Have you been accepted into a degree or certificate program?", "Program Acceptance",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (programAcceptance == 0) {
                isAcceptedStudent = true;
            } else if (programAcceptance == 1) {
                isAcceptedStudent = false;
            } else {
                return;
            }
            int selectiveService = JOptionPane.showOptionDialog(null, "Are you registered for the selective service?", "Selective Service",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (selectiveService == 0) {
                isSSregistered = true;
            } else if (selectiveService == 1) {
                isSSregistered = false;
            } else {
                return;
            }
            int socialSecurityNumber = JOptionPane.showOptionDialog(null, "Do you have a social security number?", "Social Security Number",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (socialSecurityNumber == 0) {
                hasSSN = true;
            } else if (socialSecurityNumber == 1) {
                hasSSN = false;
            } else {
                return;
            }
            int residencyStatus = JOptionPane.showOptionDialog(null, "Do you have valid residency status?", "Residency Status",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (residencyStatus == 0) {
                hasValidResidency = true;
            } else if (residencyStatus == 1) {
                hasValidResidency = false;
            } else {
                return;
            }
            do {
                age = Integer.parseInt(JOptionPane.showInputDialog(null, "How old are you?", "Age", JOptionPane.QUESTION_MESSAGE));
                if (age < 0) {
                    JOptionPane.showMessageDialog(null, "Age cannot be a negative number.", "Error: Age", JOptionPane.ERROR_MESSAGE);
                    validAge = false;
                } else {
                    validAge = true;
                }
            } while (!validAge);
            do {
                creditHours = Integer.parseInt(JOptionPane.showInputDialog(null, "How many credit hours do you plan taking?", "Credit Hours", JOptionPane.QUESTION_MESSAGE));
                if (creditHours < 0 || creditHours > 24) {
                    JOptionPane.showMessageDialog(null, "Credit hours must be between 1 and 24, inclusive.", "Error: Credit Hours", JOptionPane.ERROR_MESSAGE);
                    validCredit = false;
                } else {
                    validCredit = true;
                }
            } while (!validCredit);
            do {
                studentIncome = Integer.parseInt(JOptionPane.showInputDialog(null, "What is your total yearly income?", "Student Income", JOptionPane.QUESTION_MESSAGE));
                if (studentIncome < 0) {
                    JOptionPane.showMessageDialog(null, "Income cannot be a negative number.", "Error: Student Income", JOptionPane.ERROR_MESSAGE);
                    validStudentIncome = false;
                } else {
                    validStudentIncome = true;
                }
            } while (!validStudentIncome);
            do {
                parentIncome = Integer.parseInt(JOptionPane.showInputDialog(null, "What is your parent's total yearly income?", "Parent Income", JOptionPane.QUESTION_MESSAGE));
                if (parentIncome < 0) {
                    JOptionPane.showMessageDialog(null, "Income cannot be a negative number.", "Error: Parent Income", JOptionPane.ERROR_MESSAGE);
                    validParentIncome = false;
                } else {
                    validParentIncome = true;
                }
            } while (!validParentIncome);
            int dependency = JOptionPane.showOptionDialog(null, "Are you a dependent", "Dependent",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (dependency == 0) {
                isDependent = true;
            } else if (dependency == 1) {
                isDependent = false;
            } else {
                return;
            }
            String classStanding = (String)JOptionPane.showInputDialog(null, "What is your current class standing?", "Class Standing", JOptionPane.PLAIN_MESSAGE, null, gradeLevel, null);
            if (classStanding.equals("Freshman") || classStanding.equals("Sophomore") || classStanding.equals("Junior") || classStanding.equals("Senior")) {
                classStanding = "Undergraduate";
            }
            FAFSA fafsa = new FAFSA(isAcceptedStudent, isSSregistered, hasSSN, hasValidResidency, isDependent, age, creditHours, studentIncome, parentIncome, classStanding);
            JOptionPane.showMessageDialog(null, "Loans: " + fafsa.calcStaffordLoan() + "\n" +
                    "Grants: " + fafsa.calcFederalGrant() + "\n" +
                    "Work Study: " + fafsa.calcWorkStudy() + "\n" +
                    "---------------------------" +
                    "Total: " + fafsa.calcFederalAidAmount(), "FAFSA Results", JOptionPane.INFORMATION_MESSAGE);
            int restart = JOptionPane.showOptionDialog(null, "Would you like to complete another Application?", "Continue",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (restart == 0) {
                welcome = true;
            } else if (restart == 1) {
                welcome = false;
            } else {
                return;
            }
        } while (welcome);
    }
}
