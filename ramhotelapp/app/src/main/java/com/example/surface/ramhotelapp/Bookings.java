package com.example.surface.ramhotelapp;

public class Bookings {

    public String userID;
    public String type;
    public String date;
    public int duration;
    public int numberOfGuests;
    public double priceOfBooking;
    public String Room_Order_Id;
    // Default empty constructor
    public Bookings()    {

    }

    public Bookings(String userID, String type, String date, int duration, int numberOfGuests, double priceOfBooking,String Room_Order_Id)    {
        this.userID = userID;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.numberOfGuests = numberOfGuests;
        this.priceOfBooking = priceOfBooking;
        this.Room_Order_Id=Room_Order_Id;
    }

}
