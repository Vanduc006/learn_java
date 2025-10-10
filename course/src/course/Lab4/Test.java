package course.Lab4;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Product product1 = new Product();
        System.out.println("Name product: ");
        String name = scanner.nextLine();
        System.out.println("Price: ");
        double price = scanner.nextDouble();
        System.out.println("Tax: ");
        double tax = scanner.nextDouble();
        product1.nhapThongTin(name, price, tax);

        System.out.println(product1.xuatThongTin());

        System.out.println("Price x Tax: "+product1.getTaxPrice());

        scanner.close();
    }
}
