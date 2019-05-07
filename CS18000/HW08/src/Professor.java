public class Professor extends Person {
  private double salary;
  String course;
  String rank;

  public Professor(String name, String address, String course, String rank) {
    super(name, address);
    this.course = course;
    this.rank = rank;
    if (this.rank.equals("Assistant")) {
      this.salary = 70000;
    } else if (this.rank.equals("Professor")) {
      this.salary = 100000;
    } else {
      this.salary = 0;
    }
  }

  public double getSalary() {
    return this.salary;
  }
  public void setSalary(double salary) {
    this.salary = salary;
  }

  public String getCourse() {
    return this.course;
  }
  public void setCourse(String course) {
    this.course = course;
  }

  public String getRank() {
    return this.rank;
  }
  public void setRank(String rank) {
    this.rank = rank;
    if (this.rank.equals("Assistant")) {
      this.salary = 70000;
    } else if (this.rank.equals("Professor")) {
      this.salary = 100000;
    } else {
      this.salary = 0;
    }
  }
}
