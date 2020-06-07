package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;


public class SelesaiPesananActivity extends AppCompatActivity {
    private int InvoiceId;
    private String CustomerName;
    private String FoodName;
    private String foodId = "0";
    private int FoodPrice;
    private int promoDiscount;
    private String date;
    private String paymentType;
    private String status;
    private int currentUserId;
    private int totalPrice;
    private int deliveryFee;
    private String PromoCode;
    private int currentInvoiceId;
    private String pesananBerhasil = "Finished";
    private String pesananGagal = "Cancelled";
    ArrayList<String> listName = new ArrayList<>();

    TextView invoiceId;
    TextView Customer_name;
    TextView Food_name;
    TextView Food_price;
    TextView dateInvoice;
    TextView PaymentStatus;
    TextView PaymentType;
    TextView promo_code;
    TextView delivery_fee;
    TextView total_price;

    TextView staticIdInvoice;
    TextView staticCustomerName;
    TextView staticFoodName;
    TextView staticFoodPrice;
    TextView staticDateInvoice;
    TextView staticPaymentStatus;
    TextView staticPaymentType;
    TextView staticPromoCode;
    TextView staticDeliveryFee;
    TextView staticTotalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        invoiceId = (TextView) findViewById(R.id.idInvoice);
        Customer_name = (TextView) findViewById(R.id.namaCustomer);
        Food_name = (TextView) findViewById(R.id.namaMakanan);
        Food_price = (TextView) findViewById(R.id.FOODPRICE);
        dateInvoice = (TextView) findViewById(R.id.tanggalPesan);
        PaymentStatus = (TextView) findViewById(R.id.statusInvoice);
        PaymentType = (TextView) findViewById(R.id.tipeInvoice);
        promo_code = (TextView) findViewById(R.id.promo_code);
        delivery_fee = (TextView) findViewById(R.id.DeliveryFee);
        total_price = (TextView) findViewById(R.id.totalBiaya);

        staticIdInvoice = (TextView) findViewById(R.id.staticIdInvoice);
        staticCustomerName= (TextView) findViewById(R.id.staticNamaCustomer);
        staticFoodName= (TextView) findViewById(R.id.staticNamaMakanan);
        staticFoodPrice= (TextView) findViewById(R.id.staticHargaMakanan);
        staticDateInvoice= (TextView) findViewById(R.id.staticTanggalPesan);
        staticPaymentStatus= (TextView) findViewById(R.id.staticStatusInvoice);
        staticPaymentType= (TextView) findViewById(R.id.staticTipeInvoice);
        staticPromoCode= (TextView) findViewById(R.id.staticPromoCode);
        staticDeliveryFee= (TextView) findViewById(R.id.staticDeliveryFee);
        staticTotalPrice = (TextView) findViewById(R.id.staticTotalPrice);

        final Button Finish = (Button) findViewById(R.id.selesai);
        final Button Cancel = (Button) findViewById(R.id.batal);

        staticIdInvoice.setVisibility(View.GONE);
        staticCustomerName.setVisibility(View.GONE);
        staticFoodName.setVisibility(View.GONE);
        staticFoodPrice.setVisibility(View.GONE);
        staticDateInvoice.setVisibility(View.GONE);
        staticPaymentStatus.setVisibility(View.GONE);
        staticPaymentType.setVisibility(View.GONE);
        staticPromoCode.setVisibility(View.GONE);
        staticDeliveryFee.setVisibility(View.GONE);
        staticTotalPrice.setVisibility(View.GONE);

        invoiceId.setVisibility(View.GONE);
        Customer_name.setVisibility(View.GONE);
        Food_name.setVisibility(View.GONE);
        Food_price.setVisibility(View.GONE);
        dateInvoice.setVisibility(View.GONE);
        PaymentStatus.setVisibility(View.GONE);
        PaymentType.setVisibility(View.GONE);
        promo_code.setVisibility(View.GONE);
        delivery_fee.setVisibility(View.GONE);
        total_price.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Pesanan");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(getIntent().getExtras()!=null)
        {
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
            currentInvoiceId = intent.getIntExtra("currentInvoiceId", 0);
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(SelesaiPesananActivity.this);
        builder1.setMessage(Integer.toString(currentUserId)).create().show();

        fetchPesanan();


        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Penyelesaian Pesanan Success").create().show();
                                Intent mainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                                mainIntent.putExtra("currentUserId", currentUserId);
                                startActivity(mainIntent);
                            }
                        }catch (JSONException e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Penyelesaian Pesanan Failed").create().show();
                        }
                    }
                };

                PesananSelesaiRequest pesananSelesaiRequest = new PesananSelesaiRequest(currentInvoiceId, pesananBerhasil, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananSelesaiRequest);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Pembatalan Pesanan Success").create().show();
                                Intent mainIntent = new Intent(SelesaiPesananActivity. this, MainActivity.class);
                                mainIntent.putExtra("currentUserId", currentUserId);
                                startActivity(mainIntent);
                            }
                        }catch (JSONException e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Pembatalan Pesanan Failed").create().show();
                        }
                    }
                };

                PesananSelesaiRequest pesananSelesaiRequest = new PesananSelesaiRequest(currentInvoiceId, pesananGagal, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(pesananSelesaiRequest);
            }
        });

    }

    protected void fetchPesanan() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    if(jsonObject != null){

                        staticIdInvoice.setVisibility(View.VISIBLE);
                        staticCustomerName.setVisibility(View.VISIBLE);
                        staticFoodName.setVisibility(View.VISIBLE);
                        staticFoodPrice.setVisibility(View.VISIBLE);
                        staticDateInvoice.setVisibility(View.VISIBLE);
                        staticPaymentStatus.setVisibility(View.VISIBLE);
                        staticPaymentType.setVisibility(View.VISIBLE);
                        staticTotalPrice.setVisibility(View.VISIBLE);

                        invoiceId.setVisibility(View.VISIBLE);
                        Customer_name.setVisibility(View.VISIBLE);
                        Food_name.setVisibility(View.VISIBLE);
                        Food_price.setVisibility(View.VISIBLE);
                        dateInvoice.setVisibility(View.VISIBLE);
                        PaymentStatus.setVisibility(View.VISIBLE);
                        PaymentType.setVisibility(View.VISIBLE);
                        total_price.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonObject.length(); i++)  {
                            //object Invoice
                            JSONObject invoice = jsonObject.getJSONObject(i);
                            InvoiceId = invoice.getInt("id");
                            date = invoice.getString("date");
                            totalPrice = invoice.getInt("totalPrice");
                            status = invoice.getString("status");
                            paymentType = invoice.getString("paymentType");

                            if (paymentType.equals("Cash")){
                                staticDeliveryFee.setVisibility(View.VISIBLE);
                                delivery_fee.setVisibility(View.VISIBLE);
                                staticPromoCode.setVisibility(View.GONE);
                                promo_code.setVisibility(View.GONE);
                                deliveryFee = invoice.getInt("deliveryFee");
                                delivery_fee.setText(Integer.toString(deliveryFee));
                                Food_price.setText(Integer.toString(totalPrice - deliveryFee));
                            }else if (paymentType.equals("Cashless")){
                                staticPromoCode.setVisibility(View.VISIBLE);
                                promo_code.setVisibility(View.VISIBLE);
                                staticDeliveryFee.setVisibility(View.GONE);
                                delivery_fee.setVisibility(View.GONE);
                                JSONObject promo = invoice.getJSONObject("promo");
                                PromoCode = promo.getString("code");
                                promo_code.setText(PromoCode);
                                promoDiscountFetch(PromoCode, totalPrice);
                            }
                            //object Customer
                            JSONObject customer = invoice.getJSONObject("customer");
                            CustomerName = customer.getString("name");

                            //object Food
                            JSONArray foods = invoice.getJSONArray("food");
                            if(currentInvoiceId == InvoiceId){
                                for (int x = 0; x < foods.length(); x++){
                                    JSONObject food = foods.getJSONObject(x);
                                    listName.add(food.getString("name"));
                                    ArrayToString(listName);
                            }
                            }

                            invoiceId.setText(Integer.toString(InvoiceId));
                            Customer_name.setText(CustomerName);
                            dateInvoice.setText(date);
                            total_price.setText(Integer.toString(totalPrice));
                            PaymentStatus.setText(status);
                            PaymentType.setText(paymentType);

                            if(currentInvoiceId == 0){
                                Intent mainIntent = new Intent(SelesaiPesananActivity.this, SelesaiPesananActivity.class);
                                mainIntent.putExtra("currentUserId", currentUserId);
                                mainIntent.putExtra("currentInvoiceId", InvoiceId);
                                startActivity(mainIntent);
                            }

                        }
                    }

                }catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                    builder.setMessage("Fetch Pesanan Failed").create().show();
                    Intent mainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                    mainIntent.putExtra("currentUserId", currentUserId);
                    startActivity(mainIntent);

                }
                }
            };
        PesananFetchRequest pesananFetchRequest = new PesananFetchRequest(currentUserId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(pesananFetchRequest);

        }

        protected void ArrayToString (ArrayList<String> name){
            //change arraylist into string
            String[] str = new String[name.size()];
            Object[] obj = name.toArray();
            int i = 0;
            for(Object object : obj){
                str[i++] = (String)object;
            }

            String newString = null;
            newString = Arrays.toString(str);
            newString = newString.substring(1, newString.length()-1);
            newString = newString.replace(",", System.getProperty("line.separator"));
            Food_name.setText(newString);

        }

        protected int promoDiscountFetch (String code, final int totalPrice){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject != null){
                            promoDiscount = jsonObject.getInt("discount");
                            int temp = totalPrice + promoDiscount;
                            Food_price.setText(Integer.toString(temp));
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            };
        PromoRequest promoRequest = new PromoRequest(code, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(promoRequest);
        return promoDiscount;
        }



}
