public class Tester {
  public static void main(String[] args) {
    Person person = new Student("Jane Doe", 18, Gender.FEMALE, 3, "Computer Science");

    System.out.println(person.getName());
    System.out.println(person.getAge());
    System.out.println(person.getGender());
    System.out.println(((Student) person).getGpa());
    System.out.println(((Student) person).getMajor());

    person.setAge(19);

    System.out.println(person.getAge());

    person.speak();
  }
}
