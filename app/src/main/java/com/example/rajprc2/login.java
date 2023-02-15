package com.example.rajprc2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    EditText username,password;
    Button btn_login;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login Activity");

        SharedPreferences shrp = getSharedPreferences("login",Context.MODE_PRIVATE);
        String name = shrp.getString("name","null");
        if(!name.equals("null")){
            startActivity(new Intent(this,welcome.class).putExtra("name",name));
            finish();
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.submitButton);
        db= new DBHandler(login.this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_login.setOnClickListener(view -> {


                    UserModel um;
                    um = db.fetchUserByID(username.getText().toString().trim());
                    if(um.getName().equals("null")){
                        Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
                    }
                    else if(!SecurityMD5.getMd5Hash(password.getText().toString().trim()).equals(um.getPassword())){
                        Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        SharedPreferences shrp = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shrp.edit();
                        editor.putString("name",um.getName());
                        editor.commit();
                        startActivity(new Intent(this,welcome.class).putExtra("name",um.getName()));
                        finish();
                    }


        });
    }

}