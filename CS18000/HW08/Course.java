public class Course {
  private Professor professor;
  private Student[] students;
  private String courseName;
  private int numEnrolled;

  public Course(Professor professor, String courseName) {
    this.professor = professor;
    this.courseName = courseName;
    this.students = new Student[100];
    this.numEnrolled = 0;
  }

  public Professor getProfessor() {
    return this.professor;
  }

  public Student[] getStudents() {
    return this.students;
  }

  public String getCourseName() {
    return this.courseName;
  }

  public int getNumEnrolled() {
    return this.numEnrolled;
  }

  public boolean enroll(Student s) {
    for (int i = 0; i < this.students.length; i++) {
      if (numEnrolled < 100) {
        if (s.addCourse(this.courseName)) {
          this.students[i] = s;
          numEnrolled++;
          return true;
        }
      }
    }
    return false;
  }
}
