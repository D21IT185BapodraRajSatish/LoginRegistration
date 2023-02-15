package com.example.rajprc2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText et_name,et_password,et_conformpassword;
    Button btn_submit;
    Button btn_log;
    DBHandler dbHandler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Registration Activity");

        SharedPreferences shrp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String name = shrp.getString("name","null");
        if(!name.equals("null")){
            startActivity(new Intent(this,welcome.class).putExtra("name",name));
            finish();
        }

        et_name = findViewById(R.id.name);
        et_password =  findViewById(R.id.password);
        et_conformpassword =  findViewById(R.id.conformPassword);
        btn_submit=findViewById(R.id.submitbtn);
        btn_log=findViewById(R.id.mainlog);

        dbHandler = new DBHandler(MainActivity.this);

    }
    @Override
    protected void onStart() {
        super.onStart();

        btn_submit.setOnClickListener(v ->{

            if(et_name.getText().toString().length()>=5){
                if(et_password.getText().toString().length()>=8) {
                    if (isValidPassword(et_password.getText().toString().trim())) {
                        if (et_password.getText().toString().equals(et_conformpassword.getText().toString())) {
                            UserModel userModel = new UserModel();
                            userModel.setName(et_name.getText().toString());
                            userModel.setPassword(et_password.getText().toString());


                            dbHandler.addNewUser(userModel);

                            startActivity(new Intent(this, login.class));
                        } else {
                            Toast.makeText(this, "Password and Conform Password does not match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Password pattern not matched", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Password must be at least 8 character long", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Username length should be 5 to 8 charter long ", Toast.LENGTH_SHORT).show();
            }

            //finish();
        });

        btn_log.setOnClickListener(v->{
            startActivity(new Intent(this,login.class));
        });

    }

    private boolean isValidPassword(String password){
        String regx ="^(?=.*[0-9])"
                    +"(?=.*[a-z])(?=.*[A-Z])"
                    +"(?=.*[@#$%^&+=])"
                    +"(?=.*\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regx);
        if(password== null){
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }
}