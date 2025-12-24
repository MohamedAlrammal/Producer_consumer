package com.example.backend.producer_consumer_backend.main;

public class Product {
    private static int rgb1 = 1;
    private static int rgb2 = 1;
    private static int rgb3 = 1;
    private String color;
    public int id;
    public Product(int id){
        this.id = id;
    }
    public static void changeColor(Product product){
        product.color = String.valueOf(rgb1 + rgb2 + rgb3);
        rgb1 =(rgb1 + 1)%256;
        rgb2 =(rgb2 + 1)%256;
        rgb3 =(rgb3 + 1)%256;
    }
    public String toString(){
        return "product " + id;
    }
}

