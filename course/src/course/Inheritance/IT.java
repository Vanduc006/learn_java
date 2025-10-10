package course.Inheritance;

public class IT extends SinhVien {
    private String programmingLanguage;

    public String getLanguage() {
        return this.programmingLanguage;
    }

    public void initIT(String programmingLanguage, String id, String name, double price, double tax) throws ArithmeticException {
        // init in SinhVien class
        super.initSinhVien(id, name, price, tax);
        this.programmingLanguage = programmingLanguage;
    }

    public void test() {
        super.testSuper(); // this get form SinhVien
        // this.testSuper(); // this get form SinhVien.IT
    }

    public void testSuper() {
        System.out.println("Test in IT");
    }

    @Override
    public void changeName(String name) {
        this.name = name;
    }

    // Override

    // this -> get method in subclass
    // super -> get method in superclass
}
