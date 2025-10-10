package course;

import java.util.Scanner;

public class Bai1 {
    public static void main(String[] args) {
        // System.out.println("");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type name: ");
        String name = scanner.nextLine();
        System.out.println("Type score; ");
        int score = scanner.nextInt();
        System.out.println("Your name is: "+name+". Your scroe is: "+score);
        scanner.close();
    }
}
