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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryInvoiceActivity extends AppCompatActivity {
    private ArrayList<Invoice> list = new ArrayList<>();
    private int currentUserId;
    private int currentInvoiceId;
    private String customerName;
    private String date;
    private String status;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private HistoryAdapter.OnItemClickListener listener;


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
            actionBar.setTitle("Invoice");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //get intent
        if(getIntent().getExtras()!=null){
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryInvoiceActivity.this);
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
                        JSONObject invoice = jsonArray.getJSONObject(i);
                        JSONObject customer = invoice.getJSONObject("customer");

                        // Invoice
                        currentInvoiceId = invoice.getInt("id");
                        date = invoice.getString("date");
                        status = invoice.getString("status");



                        // customer
                        customerName = customer.getString("name");


                        // make new object
                        Invoice invoice1 = new Invoice(currentInvoiceId, date, customerName, status);
                        list.add(invoice1);
                    }

                }catch (JSONException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(HistoryInvoiceActivity.this);
                    builder.setMessage("gagal menampilkan list").create().show();
                    Intent intent = new Intent(HistoryInvoiceActivity.this, HistoryInvoiceActivity.class);
                    startActivity(intent);
                }
                setOnClickListener();
                recyclerView.setLayoutManager(new LinearLayoutManager(HistoryInvoiceActivity.this));
                historyAdapter = new HistoryAdapter(HistoryInvoiceActivity.this, list, listener);
                recyclerView.setAdapter(historyAdapter);
              


            }
        };
        getInvoiceRequest invoiceRequest = new getInvoiceRequest(currentUserId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(HistoryInvoiceActivity.this);
        queue.add(invoiceRequest);
    }

    private void setOnClickListener() {
        listener = new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                list.get(position);
                Intent intent = new Intent(HistoryInvoiceActivity.this, ReviewHistoryActivity.class);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("currentInvoiceId", position + 1);
                startActivity(intent);
            }
        };
    }
}
