package course.Inheritance;

public abstract class SinhVien{
    protected String id;
    protected String name;
    protected double price;
    protected double tax;

    public abstract void changeName(String name);

    public double getPriceTax() {
        return this.price * this.tax;
    }

    public void testSuper() {
        System.out.println("Test in SinhVien");
    }

    public void initSinhVien(String id, String name, double price, double tax) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.tax = tax;
    }
}
