package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginLauncherActivity extends AppCompatActivity {
    TextView registerTrigger;
    Button loginTrigger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_launcher);
        Intent mainIntent = new Intent(LoginLauncherActivity.this, MainActivity.class);

        registerTrigger = (TextView) findViewById(R.id.register);
        loginTrigger = (Button) findViewById(R.id.login);

        //tombol untuk melakukan register
        registerTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginLauncherActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //tombol untuk melakukan login
        loginTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginLauncherActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart(){
        //Mengecek Session
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(LoginLauncherActivity.this);
        int userId = sessionManagement.getSession();

        //Apabaila user tidak pernah login sebelumnya atau telah logout, maka id defaultnya adalah -1, jika sudah pernah melakukan login maka dia akan mengambil id yang sudah di commit
        if(userId != -1){
            Intent intent = new Intent(LoginLauncherActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
