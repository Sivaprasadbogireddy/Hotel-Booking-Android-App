package com.example.surface.ramhotelapp;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class roompayment extends AppCompatActivity {
    String booked;
    String CHANNEL_ID = "Book";
    String OrderId;
    String activityName;
    String personQuantity;
    Button pyes, pno, pcancel;
    String pay1;
    String mMessage;
    String mEmail;
    String mSubject;
    String bookingdate1;
    private Session mSession;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid().toString();
    private DatabaseReference mDatabase;
    private DatabaseReference dbRefActivities;
    private FirebaseDatabase mFirebaseDatabase;
    private static FragmentManager fragmentManager;
    public boolean fragmentShown=false;
    private static final String TAG = "Stripe";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Blocks orientation change
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.roompayment);

        fragmentManager = getFragmentManager();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRefActivities = mFirebaseDatabase.getReference("bookings");

        booked = "Yes";
        Bundle extras = getIntent().getExtras();
        final String nameValue = extras.getString("userName");
        final String activity = extras.getString("roomtype");
        final int tp=extras.getInt("rprice");
        final int noOfDays=extras.getInt("roomdays");
        final int noOfGuests=extras.getInt("roomguests");
        bookingdate1=extras.getString("roomdate1");
        pyes = (Button) findViewById(R.id.roompayyes);
        pno = (Button) findViewById(R.id.roompayno);
        pcancel = (Button) findViewById(R.id.roompaycancel);
        Random rand = new Random();
        pyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOfDays = noOfDays;
                int numberOfGuests=noOfGuests;
                int indiprice=tp;
                double tprice=indiprice*numberOfDays;
                tprice=1.12*tprice;
                int rand_int1 = rand.nextInt(10000);
                rand_int1=rand_int1%10;
                int rand_int2 = rand.nextInt(10000);
                rand_int2=rand_int2%1000;
                OrderId="R"+rand_int1+""+rand_int2;

                try {
                    Bookings booking=new Bookings(uid,activity,bookingdate1,numberOfDays,numberOfGuests,tprice,OrderId);
                    mDatabase.child("bookings").child(uid).push().setValue(booking);

                    // Listen for data change events
                    dbRefActivities.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // Foreach loop for searching the data in activities table related to user by uid
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                            {
                                for (DataSnapshot postSnapshot1: postSnapshot.getChildren())
                                {
                                    // If activity is found take the name of it and quantity of persons booked
                                    if (postSnapshot1.child("userID").getValue().toString().equals(uid))
                                    {
                                        activityName =  postSnapshot1.child("activity").getValue().toString();
                                        personQuantity = postSnapshot1.child("quantity").getValue().toString();

                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    // Get back to activities page after booking was made
                    Intent i = new Intent(roompayment.this, Room.class);
                    pay1="Yes";
                    mEmail="Siva.sotc@gmail.com";
                    mSubject="Order Confirmed";
                    mMessage="Successful Booking";

                    // Send Confirmation message trigger if booking was made
                    i.putExtra("booked?", booked);
                    i.putExtra("payconfirm",pay1);
                    // Send user name for navBar
                    i.putExtra("userName", nameValue);
                    Toast.makeText(roompayment.this, "Payment Successful", Toast.LENGTH_LONG).show();
                    startActivity(i);

                }
                catch (Exception ex)   {
                    Log.d("RoomBookingError", ex.getMessage());
                }
                try
                {
                    Bundle extras = getIntent().getExtras();
                    booked=pay1;

                    if(booked.equals("Yes"))
                    {
                        showConfirmation();
                    }
                }
                catch (Exception ex) {}

            }
        });
        pno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(roompayment.this,Room.class);
                i1.putExtra("userName", uid);
                i1.putExtra("type", "Family Suite");
                i1.putExtra("price", tp);
                Toast.makeText(roompayment.this, "Payment Failed", Toast.LENGTH_LONG).show();
                startActivity(i1);
            }
        });
        pcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(roompayment.this,Room.class);
                i2.putExtra("username",uid);
                Toast.makeText(roompayment.this, "Payment Cancelled", Toast.LENGTH_LONG).show();
                startActivity(i2);
            }
        });
    }

    private void sendEmail() {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.yandex.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        mSession = javax.mail.Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
                    }
                });
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(mSession);

            //Setting sender address
            mm.setFrom(new InternetAddress(Utils.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            //Adding subject
            mm.setSubject(mSubject);
            //Adding message
            mm.setText(mMessage);
            //Sending email
            Transport.send(mm);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void displayFragment()
    {
        ConfirmationFragment confirmationFragment = ConfirmationFragment.newInstance(2);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, confirmationFragment).addToBackStack(null).commit();

        fragmentShown = true;
    }
    public void closeFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        ConfirmationFragment confirmationFragment = (ConfirmationFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        if(confirmationFragment != null)
        {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(confirmationFragment).commit();
        }

        fragmentShown = false;
    }

    private void showConfirmation() {
        displayFragment();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFragment();
            }
        }, 2000);
    }


}
