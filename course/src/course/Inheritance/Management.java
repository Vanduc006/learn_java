package course.Inheritance;
public class Management {
    public static void main(String[] args) {
        IT student1 = new IT();
        SinhVien student2 = new CoKhi();

        student1.initIT("Java","123","Duc",1200,10);
        // student1.initIT("Java");

        System.out.println(student1.getLanguage());
        System.out.println("Tuition fee: "+student1.getPriceTax());

        // Override
        // System.out.println(student1.testSuper());
        student1.testSuper(); // test in IT
    }
}
