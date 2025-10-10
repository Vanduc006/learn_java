package course;

import java.util.Arrays;

public class Array {
    public static void main(String[] args) {
        String name[] = {
            "Duc",
            "Nga",
            "Huyen",
            "Thai",
        };
        for (int i=0; i < name.length;i++) {
            System.out.println(name[i]);
        }
        System.out.println(Arrays.toString(name));
        // Prebuild sort
        int  nums[] = {1,2,30,4,5,90};
        // Arrays.sort(nums);
        // System.out.println(Arrays.toString(nums));

        // compare each otherand sort
        for (int i=0; i < name.length-1;i++) {
            for(int j=i+1; j < name.length; j++) {
                if (nums[i] > nums[j]) {
                    int temps = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temps;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
    }
}
