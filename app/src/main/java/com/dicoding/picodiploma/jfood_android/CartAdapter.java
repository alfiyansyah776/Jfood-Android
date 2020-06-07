package com.dicoding.picodiploma.jfood_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter <CartAdapter.MyViewHolder>{

    private  ArrayList<FoodCart> list;


    public CartAdapter (ArrayList<FoodCart> list){

        super();
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        FoodCart cart = list.get(position);
        Glide.with(holder.itemView.getContext())
                .load(cart.getFoodImage())
                .apply(new RequestOptions().override(55,55))
                .into(holder.gambarMakanan);
        holder.foodName.setText(cart.getFoodName());
        holder.foodPrice.setText(Integer.toString(cart.getFoodPriceTimesQuantity()));
        holder.quantity.setText(Integer.toString(cart.getQuantity()));
        holder.staticFoodName.setText("Makanan");
        holder.staticFoodPrice.setText("Harga");
        holder.staticQuantity.setText("Kuantitas");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView gambarMakanan;
        TextView foodName, foodPrice, quantity, staticFoodName, staticFoodPrice, staticQuantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            gambarMakanan = itemView.findViewById(R.id.food_photo);
            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.food_price);
            quantity = itemView.findViewById(R.id.quantity);
            staticFoodName = itemView.findViewById(R.id.static_food_name);
            staticFoodPrice = itemView.findViewById(R.id.static_food_price);
            staticQuantity = itemView.findViewById(R.id.static_quantity);
        }
    }
}
