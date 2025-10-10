package course.ArrayList;

// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import course.Encapsultion.Student;

public class StudentManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // ArrayList<Student> students = new ArrayList<Student>();
        int numberStudent = 3;
        Student[] students = new Student[numberStudent];


        for (int i=0; i < numberStudent; i++) {
            students[i] = new Student();
            System.out.println("Name for student "+(i+1)+": ");
            String name = scanner.nextLine();
            students[i].setName(name);

            System.out.println("Age for student "+(i+1)+": ");
            int age = scanner.nextInt();
            scanner.nextLine();
            students[i].setAge(age);
        }

        System.out.println(Arrays.toString(students));

        System.out.println("Find student with first name: ");
        String findStd = scanner.nextLine();
        for (int i=0; i < numberStudent; i++) {
            // String stdName = ;
            if (students[i].getName().startsWith(findStd)) {
                System.out.println("Found student "+findStd+" at index "+(i+1));
            }
            // if (students[i].getName() == )
        }

        scanner.close();
    }
} 
