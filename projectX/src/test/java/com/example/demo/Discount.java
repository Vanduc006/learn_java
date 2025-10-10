package com.example.demo;

public class Discount {
    public double calDiscount(double totalAmount) {
        if (totalAmount < 100) {
            return 0;
        } else if (totalAmount < 500){
            return totalAmount * 0.10;
        } else {
            return totalAmount * 0.20;
        }
    }
}
