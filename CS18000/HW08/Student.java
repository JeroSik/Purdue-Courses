public class Student extends Person {
  private String[] courses;
  private char[] grades;

  public Student(String name, String address) {
    super(name, address);
    courses = new String[]{"none", "none", "none", "none", "none", "none"};
    grades = new char[]{'A', 'A', 'A', 'A', 'A', 'A'};
  }

  public String[] getCourses() {
    return this.courses;
  }
  public char[] getGrades() {
    return this.grades;
  }

  public boolean addCourse(String course) {
    boolean adding = false;
    for (int i = 0; i < courses.length; i++) {
      if (courses[i].equals("none")) {
        courses[i] = course;
        adding = true;
        break;
      }
      if (courses[i].equals(course)) {
        adding = false;
        break;
      }
    }
    return adding;
  }
}
