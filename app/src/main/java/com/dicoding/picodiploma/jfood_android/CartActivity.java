package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class CartActivity extends AppCompatActivity {
    private ArrayList<Integer> foodlistId = new ArrayList<>();
    private  ArrayList<FoodCart> list = new ArrayList<>();
    private ArrayList<String> foodlistName = new ArrayList<>();
    private ArrayList<Integer> Price = new ArrayList<>();
    private ArrayList<Integer> quantityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private int currentUserId;
    private int foodId;
    private String foodName;
    private int foodImage;
    private int foodPrice;
    private int quantity;
    private int total_price;
    private int total_quantity;
    private String customerName;
    private CartAdapter cartAdapter;
    Button order;
    TextView total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //initialize component
        recyclerView = findViewById(R.id.recycler_view);
        order = findViewById(R.id.btn_placeorder);
        total = findViewById(R.id.Total);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Cart");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //get intent
        if(getIntent().getExtras()!=null){
            //
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
            //list = (ArrayList<FoodCart>) getIntent().getSerializableExtra("cartList");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setMessage("Current Id = " + currentUserId).create().show();

        showRecyclerList();

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setMessage("Makanan telah ditambahkan ke keranjang").create().show();
                        Intent intent = new Intent(CartActivity.this, BuatPesananActivity.class);
                        intent.putExtra("currentUserId", currentUserId);
                        intent.putExtra("foodPrice", total_price);
                        intent.putExtra("foodName", foodlistName);
                        intent.putExtra("foodId", foodlistId);
                        startActivity(intent);
            }
        });


    }

    protected void showRecyclerList (){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject cart = jsonArray.getJSONObject(i);
                        JSONObject customer = cart.getJSONObject("customer");

                        // cart
                        foodId = cart.getInt("foodId");
                        foodName = cart.getString("foodName");
                        foodPrice = cart.getInt("foodPriceTimesQuantity");
                        quantity = cart.getInt("quantity");
                        foodImage = cart.getInt("foodImage");


                        // customer
                        currentUserId = customer.getInt("id");
                        customerName = customer.getString("name");

                        // make new object
                        FoodCart foodCart = new FoodCart(currentUserId, foodId, foodName, foodPrice, quantity, foodImage);
                        list.add(foodCart);

                        // input some data to other array list for next intent
                        foodlistId.add(foodId);
                        foodlistName.add(foodName);

                        //count food price and quantity
                        total_price = total_price + foodPrice;
                        total.setText(Integer.toString(total_price));
                        total_quantity = total_quantity + quantity;
                    }

                }catch (JSONException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setMessage("gagal membuat pesanan").create().show();
                    Intent intent = new Intent(CartActivity.this, CartActivity.class);
                    startActivity(intent);
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                cartAdapter = new CartAdapter(list);
                recyclerView.setAdapter(cartAdapter);
            }

        };

        CartFetchRequest cartFetchRequest = new CartFetchRequest(currentUserId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
        queue.add(cartFetchRequest);
    }

}
