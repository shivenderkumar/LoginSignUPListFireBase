package com.shivenderkumar.demologinsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserDetailActivity extends AppCompatActivity {

    private TextView txtname, txtcity, txtgender, txtage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String city = intent.getStringExtra("city");
        String gender = intent.getStringExtra("gender");
        String age = intent.getStringExtra("age");

        txtname = findViewById(R.id.name);
        txtcity = findViewById(R.id.city);
        txtgender = findViewById(R.id.gender);
        txtage = findViewById(R.id.age);

        txtname.setText(name);
        txtcity.setText(city);
        txtgender.setText(gender);
        txtage.setText(age);

    }
}