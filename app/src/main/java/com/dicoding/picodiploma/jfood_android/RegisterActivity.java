package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText etName = findViewById(R.id.etName);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView ayoLogin = findViewById(R.id.ayoLogin);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill the field", Toast.LENGTH_SHORT).show();
                }else if (!EMAIL_PATTERN.matcher(etEmail.getText().toString()).matches()){
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else if (!PASSWORD_PATTERN.matcher(etPassword.getText().toString()).matches()){
                    Toast.makeText(RegisterActivity.this, "Your Password to weak", Toast.LENGTH_SHORT).show();
                }
                else {
                   String Username = etName.getText().toString();
                   String email = etEmail.getText().toString();
                   String password = etPassword.getText().toString();
                   RegisterRequest registerRequest = new RegisterRequest(Username, email, password, responseListener);
                   RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                   queue.add(registerRequest);
                   Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                   startActivity(intent);

                }

            }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject != null) {
                                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                            }
                        }

                };


        });

        ayoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }
}
