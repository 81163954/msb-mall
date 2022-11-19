package com.msb.mall.product;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 14; i++) {
            new Thread(new MyThread()).start();

        }

    }
}
