package com.example.surface.ramhotelapp;

public class Activities {

    public String userID;
    public String activity;
    public int quantity;
    public int price;
    public String dateOfPurchase;
    public String Activity_Order_Id;
    // Default empty constructor
    public Activities() {

    }

    public Activities(String userID, String activity, int quantity, int price, String dateOfPurchase,String Activity_Order_Id) {
        this.userID = userID;
        this.activity = activity;
        this.quantity = quantity;
        this.price = price;
        this.dateOfPurchase = dateOfPurchase;
        this.Activity_Order_Id= Activity_Order_Id;
    }

}
