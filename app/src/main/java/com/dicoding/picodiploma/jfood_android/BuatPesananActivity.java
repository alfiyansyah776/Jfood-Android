package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class BuatPesananActivity extends AppCompatActivity {


    private int currentUserId;
    //private int id_food;
    //private String foodName = "0";
    //private String foodCategory = "0";
    private int foodPrice = 0;
    private String promoCode = "0";
    private String selectedPayment= "paid";
    private boolean promoActive;
    private int promoDiscount;
    private int promoMinPrice;
    private int DeliveryFee = 5000;
    private int totalPrice;
    ArrayList<Integer> listId = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();
    TextView food_name;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        food_name = (TextView) findViewById(R.id.food_name);
        final TextView food_price = (TextView) findViewById(R.id.food_price);
        final TextView total_price = (TextView) findViewById(R.id.total_price);
        final TextView textCode = (TextView) findViewById(R.id.textCode);
        final EditText promo_code = (EditText) findViewById(R.id.promo_code);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final Button count = (Button) findViewById(R.id.hitung);
        final Button order = (Button) findViewById(R.id.pesan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Order");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(getIntent().getExtras()!= null){
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
            listId = (ArrayList<Integer>) getIntent().getSerializableExtra("foodId");
            listName = (ArrayList<String>) getIntent().getSerializableExtra("foodName");
            foodPrice = intent.getIntExtra("foodPrice", 0);
            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
            builder.setMessage("Current Id = " + listId).create().show();;
        }

        order.setVisibility(View.GONE);
        textCode.setVisibility(View.GONE);
        promo_code.setVisibility(View.GONE);

        foodToString(listName);
        food_price.setText(Integer.toString(foodPrice));


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.cashless){
                    promo_code.setVisibility(View.VISIBLE);
                    textCode.setVisibility(View.VISIBLE);
                    //params.setMargins(0,248,0,0);
                    //total_price.setLayoutParams(params);
                    selectedPayment = "Cashless";
                }
                else if(checkedId == R.id.cash){
                    textCode.setVisibility(View.GONE);
                    promo_code.setVisibility(View.GONE);
                   // params.setMargins(0,248,0,0);
                    //total_price.setLayoutParams(params);
                    selectedPayment = "Cash";
                }
            }
        });

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPayment.equals("Cashless")){
                    if (!promo_code.getText().toString().isEmpty()){
                        promoCode = promo_code.getText().toString();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject != null){
                                        promoActive = jsonObject.getBoolean("active");
                                        promoDiscount = jsonObject.getInt("discount");
                                        promoMinPrice = jsonObject.getInt("minPrice");
                                        if(promoActive && foodPrice > promoMinPrice ){
                                            totalPrice = foodPrice - promoDiscount;
                                            total_price.setText(Integer.toString(totalPrice));
                                            count.setVisibility(View.GONE);
                                            order.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            Toast.makeText(BuatPesananActivity.this, "Minimum price not reached", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }catch (JSONException e){
                                    Toast.makeText(BuatPesananActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        PromoRequest promoRequest = new PromoRequest(promoCode, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                        queue.add(promoRequest);
                    }else {
                        Toast.makeText(BuatPesananActivity.this, "Please fill the promo code", Toast.LENGTH_SHORT).show();
                    }

                }else if (selectedPayment.equals("Cash")){
                    totalPrice = foodPrice + DeliveryFee;
                    total_price.setText(Integer.toString(totalPrice));
                    count.setVisibility(View.GONE);
                    order.setVisibility(View.VISIBLE);

                }
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int CustomerId = currentUserId;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null){
                                Toast.makeText(BuatPesananActivity.this, "Buat Pesanan Berhasil", Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(BuatPesananActivity.this, MainActivity.class);
                                mainIntent.putExtra("currentUserId", currentUserId);
                                startActivity(mainIntent);
                            }
                        }catch (JSONException e){
                            Toast.makeText(BuatPesananActivity.this, "Buat Pesanan gagal", Toast.LENGTH_LONG);
                        }
                    }
                };
                BuatPesananRequest buatPesananRequest = null;
                if(selectedPayment.equals("Cash")){
                    buatPesananRequest = new BuatPesananRequest(listId, totalPrice, CustomerId, DeliveryFee, responseListener);

                }else if (selectedPayment.equals("Cashless")){
                    promoCode = promo_code.getText().toString();
                    buatPesananRequest = new BuatPesananRequest(listId, totalPrice, CustomerId, promoCode, responseListener);
            }
                RemoveCartRequest removeCartRequest = new RemoveCartRequest(currentUserId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(removeCartRequest);

                RequestQueue queue1 = Volley.newRequestQueue(BuatPesananActivity.this);
                queue1.add(buatPesananRequest);


            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home :
                Intent intent = new Intent(BuatPesananActivity.this, CartActivity.class);
                intent.putExtra("currentUserId", currentUserId);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void foodToString (ArrayList<String> name){
        //change arraylist into string
        String[] str = new String[name.size()];
        Object[] obj = name.toArray();

        int i = 0;
        for(Object object : obj){
            str[i++] = (String)object;
        }

        String newString = Arrays.toString(str);
        newString = newString.substring(1, newString.length()-1);
        newString = newString.replace(",", System.getProperty("line.separator"));
        food_name.setText(newString);
    }
}
