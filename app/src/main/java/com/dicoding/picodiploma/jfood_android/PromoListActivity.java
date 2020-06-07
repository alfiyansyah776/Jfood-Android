package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PromoListActivity extends AppCompatActivity {
    private ArrayList<Promo> list = new ArrayList<>();
    private int currentUserId;
    private int PromoId;
    private String PromoCode;
    private int discount;
    private int MinPrice;
    private boolean Status;
    private RecyclerView recyclerView;
    private PromoListAdapter promoListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_invoice);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Promo List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //get intent
        if(getIntent().getExtras()!=null){
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(PromoListActivity.this);
        builder.setMessage("Current Id = " + currentUserId).create().show();
        showRecyclerList();


    }

    protected void showRecyclerList () {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject promo = jsonArray.getJSONObject(i);


                        // Promo
                       PromoId = promo.getInt("id");
                       PromoCode = promo.getString("code");
                       discount = promo.getInt("discount");
                       MinPrice = promo.getInt("minPrice");
                       Status = promo.getBoolean("active");


                        // make new object
                        Promo promo1 = new Promo(PromoId, PromoCode, discount, MinPrice, Status);
                        list.add(promo1);
                    }

                }catch (JSONException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PromoListActivity.this);
                    builder.setMessage("gagal menampilkan list").create().show();
                    Intent intent = new Intent(PromoListActivity.this, PromoListActivity.class);
                    startActivity(intent);
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(PromoListActivity.this));
                promoListAdapter = new PromoListAdapter(list);
                recyclerView.setAdapter(promoListAdapter);

            }
        };
        PromoDatabaseRequest promoDatabaseRequest = new PromoDatabaseRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(PromoListActivity.this);
        queue.add(promoDatabaseRequest);
    }


    }

