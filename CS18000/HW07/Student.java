public class Student implements Person {
  private String name;
  private int age;
  private Gender gender;
  private double gpa;
  private String major;

  public Student(String name, int age, Gender gender, double gpa, String major) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.gpa = gpa;
    this.major = major;
  }

  public String getName() {
    return name;
  }
  public int getAge() {
    return age;
  }
  public Gender getGender() {
    return gender;
  }
  public double getGpa() {
    return gpa;
  }
  public String getMajor(){
    return major;
  }

  public void setName(String name) {
    this.name = name;
  }
  public void setAge(int age) {
    this.age = age;
  }
  public void setGender(Gender gender) {
    this.gender = gender;
  }
  public void setGpa​(double gpa) {
    this.gpa = gpa;
  }
  public void setMajor(String major) {
    this.major = major;
  }

  public void speak() {
    System.out.printf("Hello. I am a student. My name is %s, my GPA is %.2f, and my major is %s.", this.name, this.gpa, this.major);
  }
}
