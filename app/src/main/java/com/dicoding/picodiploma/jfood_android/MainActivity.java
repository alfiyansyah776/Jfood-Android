package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();
    MainListAdapter listAdapter;
    ExpandableListView expListView;

    private int currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        Button pesananButton = (Button) findViewById(R.id.pesanan);
        Button cartButton = (Button) findViewById(R.id.Cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            currentUserId = intent.getIntExtra("currentUserId", 0);
        }
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        currentUserId = sessionManagement.getIdinSession();

        //Memanggil fungsi untuk menampilkan expendableList
        refreshList();


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Food selected = childMapping.get(listSeller.get(groupPosition)).get(childPosition);
                Intent buatPesanan = new Intent(MainActivity.this, FoodDetailsActivity.class);
                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                buatPesanan.putExtra("currentUserId", currentUserId = sessionManagement.getIdinSession());
                buatPesanan.putExtra("foodId", selected.getId());
                buatPesanan.putExtra("foodName", selected.getName());
                buatPesanan.putExtra("foodCategory", selected.getCategory());
                buatPesanan.putExtra("foodPrice", selected.getPrice());
                startActivity(buatPesanan);
                return false;
            }
        });


       pesananButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent selesaiPesananIntent = new Intent(MainActivity.this, SelesaiPesananActivity.class);
               selesaiPesananIntent.putExtra("currentUserId", currentUserId);
               startActivity(selesaiPesananIntent);
           }
       });

       cartButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, CartActivity.class);
               intent.putExtra("currentUserId", currentUserId);
               startActivity(intent);
           }
       });

    }

        protected void refreshList () {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonResponse = new JSONArray(response);
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject food = jsonResponse.getJSONObject(i);
                            JSONObject seller = food.getJSONObject("seller");
                            JSONObject location = seller.getJSONObject("location");

                            //food
                            int idFood = food.getInt("id");
                            String nameFood = food.getString("name");
                            int price = food.getInt("price");
                            String category = food.getString("category");

                            //Sellsr
                            int id = seller.getInt("id");
                            String name = seller.getString("name");
                            String email = seller.getString("email");
                            String phoneNumber = seller.getString("phoneNumber");

                            //Location
                            String province = location.getString("province");
                            String description = location.getString("description");
                            String city = location.getString("city");

                            //Make new object
                            Location loc = new Location(province, description, city);
                            Seller seller1 = new Seller(id, name, email, phoneNumber, loc);
                            Food food1 = new Food(idFood, nameFood, price, category, seller1);


                            boolean tester = true;
                            for (Seller temp : listSeller){
                                if(temp.getId() == seller1.getId()){
                                    tester = false;
                                }
                            }
                            if (tester){
                                listSeller.add(seller1);
                            }
                            foodIdList.add(food1);

                            //input object food and arraylist seller to hashmap

                        for (Seller sel : listSeller) {
                            ArrayList<Food> temp = new ArrayList<>();
                            for (Food food2 : foodIdList) {
                                if (food2.getSeller().getName().equals(sel.getName()) && food2.getSeller().getEmail().equals(sel.getEmail()) && food2.getSeller().getPhoneNumber().equals(sel.getPhoneNumber())){
                                    temp.add(food2);
                                }
                            }
                            childMapping.put(sel, temp);
                        }

                        }

                    } catch (JSONException e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Main Failed").create().show();
                    }
                    listAdapter = new MainListAdapter(MainActivity.this, listSeller, childMapping);
                    expListView.setAdapter(listAdapter);
                }
            };
            MenuRequest menuRequest = new MenuRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(menuRequest);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void setMode(int itemId) {
        switch (itemId){
            case R.id.LogOut :
                logOut();
                break;
            case R.id.History :
                goToHistoryInvoice();
                break;
            case R.id.Promo :
                goToPromoList();
                break;
        }


    }

    private void logOut(){
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        Intent intent = new Intent(MainActivity.this, LoginLauncherActivity.class);
        startActivity(intent);
    }

    private void goToHistoryInvoice (){
        Intent intent = new Intent(MainActivity.this, HistoryInvoiceActivity.class);
        intent.putExtra("currentUserId", currentUserId);
        startActivity(intent);
    }

    private void goToPromoList (){
        Intent intent = new Intent(MainActivity.this, PromoListActivity.class);
        intent.putExtra("currentUserId", currentUserId);
        startActivity(intent);
    }




}

