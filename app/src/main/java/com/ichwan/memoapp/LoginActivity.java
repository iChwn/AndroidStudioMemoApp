package com.ichwan.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button login, register;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        username = (EditText)findViewById(R.id.input_username);
        password = (EditText)findViewById(R.id.input_password);
        login = (Button)findViewById(R.id.login_btn);

        // register text on click
        TextView textRegis = (TextView)findViewById(R.id.register_text);
        textRegis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        //login btn on click
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword= password.getText().toString();
                if(strUsername.isEmpty() || strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Semua Field Harus di isi", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean masuk = db.checkLogin(strUsername, strPassword);
                    if(masuk == true) {
                        Boolean updateSession = db.upgradeSession("ada", 1);
                        if (updateSession == true) {
                            Toast.makeText(getApplicationContext(), "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email atau Password anda Salah!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
