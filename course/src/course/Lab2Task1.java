package course;

import java.util.Scanner;

public class Lab2Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("value for a: ");
        float a = scanner.nextInt();
        System.out.println("value for b: ");
        float b = scanner.nextInt();
        if (a == 0 || b == 0) {
            System.out.println("vo so nghiem");
        }
        if ( a == 0 && b != 0) {
            System.out.println("vo nghiem");
        }
        else {
            System.out.println("nghiem"+(-b/a));
        }

    }
}
