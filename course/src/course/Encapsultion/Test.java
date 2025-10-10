package course.Encapsultion;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Student student = new Student();

        System.out.println("Type name: ");
        String name = scanner.nextLine();
        student.setName(name);
        System.out.println("Type age: ");
        int age = scanner.nextInt();
        student.setAge(age);
        System.out.println("Name: "+student.getName());
        System.out.println("Age: "+student.getAge());

        scanner.close();
    }
}
