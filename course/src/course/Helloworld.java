package course;

import java.util.Scanner;

public class Helloworld {
	public static void main(String[] args) {
//		int a = 1;
//		int b = 2;
//		int c = Math.max(a,b);
		Scanner scanner = new Scanner(System.in);
//		System.out.println("Type your name :");
//		String name = scanner.nextLine();
//		System.out.println("Type your age :");
//		int age = scanner.nextInt();
//		System.out.println("My name is " + name + ". My age is " + age);
		System.out.println("number a: ");
		int a = scanner.nextInt();
		System.out.println("number b: ");
		int b = scanner.nextInt();
		int c = Math.max(a, b);
//		int c = Math.min(a, b);
		System.out.println("Max is: " + c);
		;
		scanner.close();

		// scanner

	}
}
