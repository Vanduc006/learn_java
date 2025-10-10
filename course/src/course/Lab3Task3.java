package course;

import java.util.Arrays;
import java.util.Scanner;

public class Lab3Task3 {
    public static void main(String[] args) {
        System.out.println("init array, sort, max and min");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type size for array: ");
        int size = scanner.nextInt();
        int arr[] = new int[size];
        for (int i = 0; i < size; i++) {
            System.out.println("Type value for "+i+": ");
            arr[i] = scanner.nextInt();
        }
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        int maxx = arr[0];
        int minn = arr[0];
        for (int i=0; i < arr.length; i++) {
            if (arr[i] > maxx) {
                maxx = arr[i];
            }
            if (arr[i] < minn) {
                minn = arr[i];
            }
        }
        System.out.println("max: "+maxx);
        System.out.println("Min: "+minn);
    }
}
