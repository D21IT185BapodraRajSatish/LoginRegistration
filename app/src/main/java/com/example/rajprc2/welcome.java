package com.example.rajprc2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class welcome extends AppCompatActivity {

    TextView tv;
    Button btn_logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getSupportActionBar().setTitle("Welcome");

        tv = findViewById(R.id.TXT_welcome);
        btn_logout = findViewById(R.id.btnLogout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv.setText("Hello "+getIntent().getExtras().getString("name"));

        btn_logout.setOnClickListener(v->{
            SharedPreferences shrp = getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shrp.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(this,MainActivity.class));finish();
        });
    }
}