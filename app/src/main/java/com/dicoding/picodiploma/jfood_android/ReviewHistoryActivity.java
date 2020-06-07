package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import java.util.Arrays;

public class ReviewHistoryActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_review_history);

        invoiceId = (TextView) findViewById(R.id.idInvoice);
        Customer_name = (TextView) findViewById(R.id.namaCustomer);
        Food_name = (TextView) findViewById(R.id.namaMakanan);
        Food_price = (TextView) findViewById(R.id.foodPrice);
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
            actionBar.setTitle("Invoice");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        if(getIntent().getExtras()!=null)
        {
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
            currentInvoiceId = intent.getIntExtra("currentInvoiceId", 0);
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(ReviewHistoryActivity.this);
        builder1.setMessage(Integer.toString(currentInvoiceId)).create().show();

        fetchPesanan();


    }

    protected void fetchPesanan() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
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
                            //object Invoice
                        int i = 0;

                            InvoiceId = jsonObject.getInt("id");
                            date = jsonObject.getString("date");
                            totalPrice = jsonObject.getInt("totalPrice");
                            status = jsonObject.getString("status");
                            paymentType = jsonObject.getString("paymentType");

                            if (paymentType.equals("Cash")){
                                staticDeliveryFee.setVisibility(View.VISIBLE);
                                delivery_fee.setVisibility(View.VISIBLE);
                                staticPromoCode.setVisibility(View.GONE);
                                promo_code.setVisibility(View.GONE);
                                deliveryFee = jsonObject.getInt("deliveryFee");
                                delivery_fee.setText(Integer.toString(deliveryFee));
                                int temp = totalPrice - deliveryFee;
                                Food_price.setText(Integer.toString(temp));
                            }else if (paymentType.equals("Cashless")){
                                staticPromoCode.setVisibility(View.VISIBLE);
                                promo_code.setVisibility(View.VISIBLE);
                                staticDeliveryFee.setVisibility(View.GONE);
                                delivery_fee.setVisibility(View.GONE);
                                JSONObject promo = jsonObject.getJSONObject("promo");
                                PromoCode = promo.getString("code");
                                promo_code.setText(PromoCode);
                                promoDiscountFetch(PromoCode, totalPrice);
                            }
                            //object Customer
                            JSONObject customer = jsonObject.getJSONObject("customer");
                            CustomerName = customer.getString("name");

                            //object Food
                            JSONArray foods = jsonObject.getJSONArray("food");
                                for (int x = 0; x < foods.length(); x++){
                                    JSONObject food = foods.getJSONObject(x);
                                    listName.add(food.getString("name"));
                                    ArrayToString(listName);
                                }
                            invoiceId.setText(Integer.toString(InvoiceId));
                            Customer_name.setText(CustomerName);
                            dateInvoice.setText(date);
                            total_price.setText(Integer.toString(totalPrice));
                            PaymentStatus.setText(status);
                            PaymentType.setText(paymentType);


                        }


                }catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewHistoryActivity.this);
                    builder.setMessage("Fetch Pesanan Failed").create().show();
                    Intent mainIntent = new Intent(ReviewHistoryActivity.this, HistoryInvoiceActivity.class);
                    mainIntent.putExtra("currentUserId", currentUserId);
                    startActivity(mainIntent);

                }
            }
        };

        PesananFetchByIdRequest pesananFetchRequest = new PesananFetchByIdRequest(currentInvoiceId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReviewHistoryActivity.this);
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

    protected void promoDiscountFetch (String code, final int totalPrice){
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
        RequestQueue queue = Volley.newRequestQueue(ReviewHistoryActivity.this);
        queue.add(promoRequest);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home :
                Intent intent = new Intent(ReviewHistoryActivity.this, HistoryInvoiceActivity.class);
                intent.putExtra("currentUserId", currentUserId);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
