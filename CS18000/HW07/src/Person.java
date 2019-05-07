public interface Person {
  public String getName();
  public int getAge();
  public Gender getGender();
  public void setAge(int age);
  public void setName(String name);
  public void setGender(Gender gender);

  default void speak() {
    System.out.println("Hello. I am a person. My name is " + getName() + ".");
  }
}
