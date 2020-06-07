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

public class PromoListAdapter extends RecyclerView.Adapter <PromoListAdapter.MyViewHolder>{

    private  ArrayList<Promo> list;


    public PromoListAdapter (ArrayList<Promo> list){

        super();
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.promolist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        Promo promo = list.get(position);

        holder.PromoName.setText(promo.getCode());
        holder.Discount.setText(Integer.toString(promo.getDiscount()));
        holder.MinPrice.setText(Integer.toString(promo.getMinPrice()));
        holder.Active.setText(Boolean.toString(promo.getActive()));
        holder.staticPromoName.setText("Kode Promo");
        holder.staticMinPrice.setText("Minimum Pembelian");
        holder.staticDiscount.setText("Potongan Harga");
        holder.staticActive.setText("Status");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView staticPromoName, staticDiscount, staticMinPrice, staticActive, PromoName, Discount, MinPrice, Active;

        public MyViewHolder(View itemView) {
            super(itemView);
            staticPromoName = itemView.findViewById(R.id.staticPromoName);
            staticDiscount = itemView.findViewById(R.id.staticDiscount);
            staticMinPrice = itemView.findViewById(R.id.staticMinPrice);
            staticActive = itemView.findViewById(R.id.staticActive);
            PromoName = itemView.findViewById(R.id.PromoName);
            Discount = itemView.findViewById(R.id.Discount);
            MinPrice = itemView.findViewById(R.id.MinPrice);
            Active = itemView.findViewById(R.id.Active);
        }
    }
}
