package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    Intent mainIntent;
    private int id;
    private String email;
    private String password;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mainIntent = new Intent(LoginActivity.this, MainActivity.class);

        //Menginisiasi komponen view
        final EditText etEmail = findViewById(R.id.Email);
        final EditText etPassword = findViewById(R.id.Password);
        Button btnLogin = findViewById(R.id.LoginButton);
        final TextView tvRegister = findViewById(R.id.Register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Conditional Statement untuk mengecek input field dari email dan password
                if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill the field", Toast.LENGTH_SHORT).show();
                } else if (!EMAIL_PATTERN.matcher(etEmail.getText().toString()).matches()){
                    Toast.makeText(LoginActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else if (!PASSWORD_PATTERN.matcher(etPassword.getText().toString()).matches()){
                    Toast.makeText(LoginActivity.this, "Your Password to weak", Toast.LENGTH_SHORT).show();
                }
                //Jika input sudah cocok data baru disimpan pada variabel yang nantinya dipakai untuk melakukan request
                else{
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    Log.println(Log.DEBUG, null, email);
                    Log.println(Log.DEBUG, null, password);
                }

                //Fungsi untuk melakukan String Request
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                id = jsonObject.getInt("id");
                                //Menyimpan id yang telah didapat ke dalan sharedPrefferences
                                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                                sessionManagement.saveSession(id);
                                mainIntent.putExtra("currentUserId", id);
                                mainIntent.setFlags(mainIntent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                //Melakukan Request String
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }


}
