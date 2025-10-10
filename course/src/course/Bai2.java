package course;

import java.util.Scanner;

public class Bai2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chieu dai: ");
        int cd = scanner.nextInt();
        System.out.println("Chieu rong: ");
        int cr = scanner.nextInt();
        int chuvi = (cd + cr)/2;

        System.out.println("chu vi" +chuvi);
        // sys
        System.out.println("Canh cua lap phuong");
        int cube = scanner.nextInt();
        double thetich = Math.pow(3, cube);
        System.out.println(thetich);
        scanner.close();
    }
}
