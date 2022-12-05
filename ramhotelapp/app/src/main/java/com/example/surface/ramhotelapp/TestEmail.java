package com.example.surface.ramhotelapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surface.ramhotelapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TestEmail extends AppCompatActivity {

    public EditText mEmail;
    public EditText mSubject;
    public EditText mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                sendMail();
        Intent iback=new Intent(TestEmail.this,Room.class);
        startActivity(iback);
    }

    private void sendMail() {

        String mail = "reddysp2777@gmail.com";
        String message = "Order Confirmed";
        String subject = "You booked Room Successfully 1234";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(TestEmail.this, mail, subject, message);

        javaMailAPI.execute();

    }

}