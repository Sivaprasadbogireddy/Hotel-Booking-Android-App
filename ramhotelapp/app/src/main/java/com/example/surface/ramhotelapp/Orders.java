package com.example.surface.ramhotelapp;

public class Orders {
    public String item;
    public double price;
    public int quantity;
    public String date;
    public double totalPrice;
    public String Service_Order_Id;

    // Default empty constructor
    public Orders() {

    }

    public Orders(String item, double price, int quantity, String date,String Service_Order_Id) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.totalPrice = price * quantity;
        this.Service_Order_Id=Service_Order_Id;
    }

}
