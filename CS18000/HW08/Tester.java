public class Tester {
  public static void main(String[] args) {
    String courseName = "MA16200";
    Professor p = new Professor("Dr. Malcom", "MATH", courseName, "Professor");
    Course c = new Course(p, courseName); // creates a new Course

    Student s = new Student("Bobby Jones", "Earhart Hall"); // Creates new Student

    System.out.println(s.getCourses()[0]); // prints "none"
    System.out.println(c.enroll(s)); // prints "true"
    System.out.println(s.getCourses()[0]); // prints "MA16200"
    System.out.println(c.getStudents()[0].getName()); // prints "Bobby Jones"
    System.out.println(c.getProfessor().getName()); // prints "Dr. Malcom"
  }
}
