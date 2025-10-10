package course.Constructor;

public class Student {

    // function overloading
    public Student() {

    }

    public Student(String name, int age) { // constructor
        this.age = age;
        this.name = name;
        this.LearnJava();
    }

    // class attributes
    String name;
    int age;
    String fullName;
    String address;

    // class method
    public void LearnJava() {
        System.out.println("Learning");
    }

    int getAge() {
        return age;
    }

    int getBirthYear() {
        return 2025-age;
    }
} 
