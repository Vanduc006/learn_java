package course.Lab4;

public class Product {
    private String name;
    private double price;
    private double tax;

    public void nhapThongTin(String name, double price, double tax) {
        this.name = name;
        this.price = price;
        this.tax = tax;
    }

    public String xuatThongTin() {
        return "Name product: "+this.name+"\n"+"Price: "+this.price+"\n"+"Tax: "+this.tax;
    }

    public double getTaxPrice() {
        return this.price*this.tax;
    }

    // public idk() {}
}
