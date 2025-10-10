package course.Encapsultion;

public class Student {
    private String name;
    private int age;
    private String fullName;
    private String address;

    public Student() {

    };

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void setName(String name) {
        this.name = name;
        // return
    }

    public void setAge(int age) {
        this.age = age;
    }

    // class method
    public void LearnJava() {
        System.out.println("Learning");
    }

    // int getAge() {
    //     return age;
    // }

    // int getBirthYear() {
    //     return 2025-age;
    // }


}
