package com.dicoding.picodiploma.jfood_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    private int currentUserId;
    SharedPreferences prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshList();


        /*expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listSeller.get(groupPosition)
                                + " : "
                                + childMapping.get(
                                listSeller.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listSeller.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

    }

        protected void refreshList () {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    expListView = (ExpandableListView) findViewById(R.id.lvExp);
                    MainListAdapter listAdapter = new MainListAdapter(MainActivity.this, listSeller, childMapping);
                    expListView.setAdapter(listAdapter);
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

                            //input to array list
                            listSeller.add(seller1);
                            foodIdList.add(food1);
                            childMapping.put(listSeller.get(i), foodIdList);

                            //input object food and arraylist seller to hashmap
                        for (Seller sel : listSeller) {
                            ArrayList<Food> temp = new ArrayList<>();
                            for (Food food2 : foodIdList) {
                                if (food2.getSeller().getName().equals(sel.getName()) || food2.getSeller().getEmail().equals(sel.getEmail()) || food2.getSeller().getPhoneNumber().equals(sel.getPhoneNumber())){
                                    temp.add(food2);
                                }
                            }
                            childMapping.put(sel, temp);
                        }


                        }

                    } catch (JSONException e) {
                        AlertDialog.Builder bulder = new AlertDialog.Builder(MainActivity.this);
                        bulder.setMessage("Main Failed").create().show();
                    }



                }
            };
            /*MenuRequest menuRequest = new MenuRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(menuRequest);*/
        }
    }

