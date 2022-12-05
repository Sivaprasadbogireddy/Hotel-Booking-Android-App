package com.example.surface.ramhotelapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.util.Calendar;

public class bookingroompay extends AppCompatActivity {

    Button calender;
    Button book;
    String booked;
    String CHANNEL_ID="Book";
    String nameValue;
    EditText noOfGuests;
    EditText noOfDays;
    double price = 0.0;

    Calendar c = Calendar.getInstance();
    DateFormat fmtDate = DateFormat.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid().toString();
    private DatabaseReference mDatabase;
    private DatabaseReference dbRefActivities;
    private FirebaseDatabase mFirebaseDatabase;
    public Boolean fragmentShown = false;
    private String date;
    private static FragmentManager fragmentManager;
    private TextView overallTxt;
    private TextView totalTxt;
    private TextView gstTxt;
    double over;
    double overgst;
    double total;
    DatePickerDialog datePickerDialog;

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            c.set(Calendar.YEAR, i);
            c.set(Calendar.MONTH, i1);
            c.set(Calendar.DAY_OF_MONTH, i2);
            date = fmtDate.format(c.getTime());
        }
    };

    @SuppressLint({"CutPasteId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Blocks orientation change
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.bookingroompay);
        fragmentManager = getFragmentManager();
        // Gets the extras from the previous page
        booked = "Yes";
        Bundle extras = getIntent().getExtras();
        nameValue = extras.getString("userName");
        final String activity = extras.getString("type");
        final int individualPrice = extras.getInt("price");
        // Connecting to database to get data which will be send to notification
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRefActivities = mFirebaseDatabase.getReference("bookings");
        final EditText noOfGuests = (EditText) findViewById(R.id.etNumberOfGuests1);
        final EditText noOfDays = (EditText) findViewById(R.id.etNumberOfDays1);
        overallTxt= (TextView)findViewById(R.id.overallroom);
        gstTxt=(TextView)findViewById(R.id.gstroom);
        totalTxt=(TextView)findViewById(R.id.totalcostroom);


        this.setFinishOnTouchOutside(false);
        Random rand = new Random();
        calender = (Button) findViewById(R.id.calendarroom);
        book = (Button) findViewById(R.id.btnBookroom);

        // Pick the date
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(bookingroompay.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1);
                datePickerDialog.show();
            }
        });
        TextWatcher textw=new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!(noOfGuests.getText().toString().isEmpty() || noOfDays.getText().toString().isEmpty()))
                {
                    int temp1=Integer.parseInt(noOfDays.getText().toString());
                    final double over=temp1*individualPrice;
                    final double overgst=over*0.12;
                    final double total=over+overgst;
                    overallTxt.setText("Overall Charges: $" + over);
                    gstTxt.setText("GST + PST: 12%: $"+overgst);
                    totalTxt.setText("Grand Total: $" + total);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        noOfDays.addTextChangedListener(textw);
        noOfGuests.addTextChangedListener(textw);
        // Event handler for when the user attempts to book his/her activity
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noOfGuests.getText().toString().isEmpty() || noOfDays.getText().toString().isEmpty())  {
                    Toast.makeText(bookingroompay.this, "Please Enter All fields", Toast.LENGTH_LONG).show();
                }
                else
                {
                    final int tempprice=extras.getInt("price");
                    int tempdays=Integer.parseInt(noOfDays.getText().toString());
                    int tempguests=Integer.parseInt(noOfGuests.getText().toString());
                    Intent i = new Intent(bookingroompay.this, roompayment.class);
                    i.putExtra("userName", nameValue);
                    i.putExtra("roomtype",activity);
                    i.putExtra("rprice", tempprice);
                    i.putExtra("roomdays", tempdays);
                    i.putExtra("roomguests", tempguests);
                    i.putExtra("roomdate1",date);
                    startActivity(i);
                }

            }
        });

    }


}
