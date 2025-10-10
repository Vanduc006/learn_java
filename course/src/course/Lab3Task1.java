package course;

import java.util.Scanner;

public class Lab3Task1 {
    public static void main(String[] args) {
        System.out.println("Prime number ?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("type num: ");
        int num = scanner.nextInt();
        int n = 0;
        for (int i=0; i < num-1; i++) {
            if (num % i == 0) {
                n++;
            }
        }
        if (n == 2) {
            System.out.println("Prime");
        }
        else {
            System.out.println("Not prime");
        }
    }
}
