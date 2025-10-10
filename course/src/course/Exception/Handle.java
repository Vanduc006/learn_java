package course.Exception;

import java.util.Scanner;

public class Handle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            try {
                System.out.println("Type number: ");
                int number = scanner.nextInt();
                System.out.println(number);
                break;

            } catch (Exception e) {
                scanner.next(); //
                // TODO: handle exception
                System.out.println(e);
            } finally {
                System.out.println("end");
            }
            // scanner.close();

        }

    }
}
