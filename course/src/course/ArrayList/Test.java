package course.ArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import course.Encapsultion.Student;

public class Test {
    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<String>(); // generic

        ArrayList<Student> arr1 = new ArrayList<Student>();
        List<String> list = new ArrayList<String>();

        // generic collection like type interface in Typescript
        // fit when process respone

        for (int i = 0; i < 10; i++) {
            arr.add("Item" + i);
        }

        System.out.println(arr.size());
        System.out.println(arr.toString());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to remove data y/n ?");
        String choice = scanner.nextLine();
        if (choice.equals("y") || choice.equals("Y")) {
            System.out.println("Type index you want remove");
            int index = scanner.nextInt();
            arr.remove(index);

            System.out.println(arr.toString());
            scanner.close();
        }
        else {
            System.out.println("end");
        }

    }
}
