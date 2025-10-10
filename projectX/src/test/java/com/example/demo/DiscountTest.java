package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DiscountTest {

    @Test
    public void testNoDiscount() {
        Discount testDiscount = new Discount();

        // < 10 return 0
        double total = testDiscount.calDiscount(50);
        assertEquals(0, total);
    }

    @Test
    public void test10Discount() {
        Discount testDiscount = new Discount();

        // < 10 return 0
        double total = testDiscount.calDiscount(110);
        assertEquals(110*0.10, total);
    }

    @Test
    public void test20Discount() {
        Discount testDiscount = new Discount();

        // < 10 return 0
        double total = testDiscount.calDiscount(600);
        assertEquals(600*0.20, total);
    }
}
