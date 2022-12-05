package com.example.surface.ramhotelapp;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.text.SimpleDateFormat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class Room extends AppCompatActivity {

    String nameValue;

    LinearLayout bookLayout;
    TextView descText1, descText2, descText3;
    ImageButton more1, less1, more2, less2, more3, less3;
    Button btn1, btn2, btn3, etDate;
    Date date1 = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
    String str1 = formatter.format(date1);
    String type;
    private String date;
    int i;

    // Gets the uid from the current user
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid().toString();

    // Variables / objects which will be used to add data to the database
    private DatabaseReference mDatabase;
    double price = 0.0;
    int numberOfDays = 0;
    int numberOfGuests = 0;
    //for showing the calendar
    public Boolean fragmentShown = false;
    public String booked = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Blocks orientation change
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_room);

        // Instantiates the Firebase Database object
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Instantiate the other required controllers to gather input from users

        // Gets the extras from the intent
        Bundle extrasName = getIntent().getExtras();
        nameValue = extrasName.getString("userName");
        // home button
        FloatingActionButton fabHome = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Room.this, HomeActivity.class);
                i.putExtra("userName", nameValue);
                startActivity(i);
            }
        });

        //ImageView for Thread
        final ImageView imgActivity1 = (ImageView) findViewById(R.id.imageViewRoom);
        // Thread to change the image of a room
        new Thread((new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i<4) {
                    SystemClock.sleep(2000);
                    i++;
                    final int curCount = i;
                    if (curCount == 0) {
                        imgActivity1.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity1.setImageResource(R.drawable.room1);
                            }
                        });
                    }
                    else if (curCount == 1) {
                        imgActivity1.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity1.setImageResource(R.drawable.room2);
                            }
                        });
                    }
                    else if (curCount == 2) {
                        imgActivity1.post(new Runnable() {
                            @Override
                            public void run() {
                                imgActivity1.setImageResource(R.drawable.room3);
                            }
                        });
                    }
                    else if (curCount == 3) {
                        i = -1;
                    }
                }
            }
        })).start();
        //page elements for hiding/showing booking details
        //elements for hiding/showing room description
        descText1 = (TextView) findViewById(R.id.description_room1);
        more1 = (ImageButton) findViewById(R.id.imgBtnMoreRoom);
        less1 = (ImageButton) findViewById(R.id.imgBtnLessRoom1);

        descText2 = (TextView) findViewById(R.id.description_room2);
        more2 = (ImageButton) findViewById(R.id.imgBtnMoreRoom2);
        less2 = (ImageButton) findViewById(R.id.imgBtnLessRoom2);

        descText3 = (TextView) findViewById(R.id.description_room3);
        more3 = (ImageButton) findViewById(R.id.imgBtnMoreRoom3);
        less3 = (ImageButton) findViewById(R.id.imgBtnLessRoom3);

        btn1 = (Button) findViewById(R.id.button1room);
        btn2 = (Button) findViewById(R.id.button2room);
        btn3 = (Button) findViewById(R.id.button3room);
        Intent i = new Intent(Room.this, bookingroompay.class);
        //book button of a Comfort Suite
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Comfort Suite - $120/day

                i.putExtra("userName", nameValue);
                i.putExtra("type", "Mini Suite");
                i.putExtra("price", 120);

                startActivity(i);
            }
        });
        //book button of a Kings Suite
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kings Suite - $180/day
                i.putExtra("userName", nameValue);
                i.putExtra("type", "Mater Suite");
                i.putExtra("price", 180);

                startActivity(i);
            }
        });
        //book button a Family suite
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Family Suite - $220/day
                i.putExtra("userName", nameValue);
                i.putExtra("type", "Villa Suite");
                i.putExtra("price", 220);

                startActivity(i);
            }
        });

        //showing the description of the Comfort Suite
        more1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                more1.setVisibility(View.GONE);
                less1.setVisibility(View.VISIBLE);
                descText1.setMaxLines(Integer.MAX_VALUE);

            }
        });
        //hiding the description of the Comfort Suite leaving 2 lines
        less1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                less1.setVisibility(View.GONE);
                more1.setVisibility(View.VISIBLE);
                descText1.setMaxLines(2);

            }
        });

        more2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                more2.setVisibility(View.GONE);
                less2.setVisibility(View.VISIBLE);
                descText2.setMaxLines(Integer.MAX_VALUE);

            }
        });

        less2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                less2.setVisibility(View.GONE);
                more2.setVisibility(View.VISIBLE);
                descText2.setMaxLines(2);

            }
        });

        more3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                more3.setVisibility(View.GONE);
                less3.setVisibility(View.VISIBLE);
                descText3.setMaxLines(Integer.MAX_VALUE);

            }
        });

        less3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                less3.setVisibility(View.GONE);
                more3.setVisibility(View.VISIBLE);
                descText3.setMaxLines(2);

            }
        });
        // Show confirmation fragment if user pressed "Book" button
        try
        {
            Bundle extras = getIntent().getExtras();
            booked = extras.getString("booked?");

            if(booked.equals("Yes"))
            {
                showConfirmation();
            }
        }
        catch (Exception ex) {}
    }


    // Methods to make confirmation fragment visible
    public void displayFragment()
    {
        ConfirmationFragment confirmationFragment = ConfirmationFragment.newInstance(2);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, confirmationFragment).addToBackStack(null).commit();

        fragmentShown = true;
    }

    // Methods to make confirmation fragment invisible
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

    // Method for handling fragment visibility in the page
    public void showConfirmation()
    {
        displayFragment();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFragment();
            }
        }, 2000);
    }
}
