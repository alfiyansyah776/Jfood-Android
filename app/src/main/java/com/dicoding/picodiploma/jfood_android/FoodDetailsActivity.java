package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodDetailsActivity extends AppCompatActivity {

    private int quantity = 1;
    private int foodId;
    private String foodName;
    private int foodPrice;
    private int currentUserId;
    private int totalPrice;
    private int foodImage;
    TextView namaMakanan;
    TextView hargaMakanan;
    TextView kuantitas;
    Button cart;
    Button plus;
    Button minus;
    ImageView gambarMakanan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details2);

        namaMakanan = (TextView)findViewById(R.id.NamaMakanan);
        hargaMakanan = (TextView)findViewById(R.id.hargaMakanan);
        kuantitas = (TextView) findViewById(R.id.kuantitas);
        cart = (Button)findViewById(R.id.Order);
        plus = (Button)findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        gambarMakanan = (ImageView) findViewById(R.id.gambarMakanan);
        SessionManagement sessionManagement = new SessionManagement(FoodDetailsActivity.this);
        currentUserId = sessionManagement.getIdinSession();

        //Mendapatkan data melalui intent
        if (getIntent().getExtras()!= null){
            Intent intent = getIntent();
            foodName = intent.getStringExtra("foodName");
            foodPrice = intent.getIntExtra("foodPrice", 0);
            foodId = intent.getIntExtra("foodId", 0);
            AlertDialog.Builder builder = new AlertDialog.Builder(FoodDetailsActivity.this);
            builder.setMessage("Current Id = " + currentUserId).create().show();
        }

        namaMakanan.setText(foodName);
        hargaMakanan.setText(Integer.toString(foodPrice));

        //Memasukkan gambar ke imageView Sesuai dengan nama gambar

        if (foodName.equals("Rawon")){
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.rawon));
            foodImage = R.drawable.rawon;
        }else if (foodName.equals("Tempura")){
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.tempura));
            foodImage = R.drawable.tempura;
        }else if (foodName.equals("Onigiri")){
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.onigiri));
            foodImage = R.drawable.error404;
        }else if (foodName.equals("Tuna Onigiri")) {
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.tunaonigiri1));
            foodImage = R.drawable.error404;
        }else if (foodName.equals("Kare")) {
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.kare));
            foodImage = R.drawable.error404;
        }else if (foodName.equals("Kaarage")) {
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.kaarage1));
            foodImage = R.drawable.error404;
        }else if (foodName.equals("Tonkotsu ramen")) {
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.tonkotsuramen));
            foodImage = R.drawable.error404;
        }else if (foodName.equals("Ebi furai")) {
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.ebifurai));
            foodImage = R.drawable.error404;
        }else if (foodName.equals("Omelette")) {
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.omelette1));
            foodImage = R.drawable.error404;
        }else if (foodName.equals("Don")) {
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.don));
            foodImage = R.drawable.error404;
        }else{
            gambarMakanan.setImageDrawable(getDrawable(R.drawable.error404));
            foodImage = R.drawable.error404;
        }

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    quantity = quantity + 1;
                    kuantitas.setText(Integer.toString(quantity));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = quantity - 1;
                kuantitas.setText(Integer.toString(quantity));
            }
        });

        //Melakukan request POST pada databaseCart
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPrice = foodPrice * quantity;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject!= null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(FoodDetailsActivity.this);
                                builder.setMessage("Makanan telah ditambahkan ke keranjang").create().show();
                                Intent mainIntent = new Intent(FoodDetailsActivity.this, MainActivity.class);
                                mainIntent.putExtra("currentUserId", currentUserId);
                                startActivity(mainIntent);
                                AlertDialog.Builder build = new AlertDialog.Builder(FoodDetailsActivity.this);
                                build.setMessage("Current Id = " + foodId).create().show();;
                            }

                        }catch (JSONException e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(FoodDetailsActivity.this);
                            builder.setMessage("Makanan gagal ditambahkan ke keranjang").create().show();
                    }
                }

                };
                AddCartRequest addCartRequest = new AddCartRequest(currentUserId, foodId, foodName, totalPrice, quantity, foodImage, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FoodDetailsActivity.this);
                queue.add(addCartRequest);

            }
        });




    }
}
