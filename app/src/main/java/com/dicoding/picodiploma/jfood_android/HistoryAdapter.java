package com.dicoding.picodiploma.jfood_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter <HistoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Invoice> list;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public HistoryAdapter(Context context, ArrayList<Invoice> list, OnItemClickListener mListener) {
        //this.namelist = namelist;
        //this.priceList = priceList;
        //this.quantityList = quantityList;
        super();
        this.context = context;
        this.list = list;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layouthistory, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Invoice invoice = list.get(position);
        holder.customerName.setText(invoice.getCustomerName());
        holder.invoiceDate.setText(invoice.getDate());
        holder.invoiceId.setText(Integer.toString(invoice.getInvoiceId()));
        holder.status.setText(invoice.getStatus());
        holder.staticInvoiceId.setText("ID Invoice");
        holder.cName.setText("Nama Customer");
        holder.dateInvoice.setText("Tanggal Pesanan");
        holder.staticStatus.setText("Status");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView invoiceId, invoiceDate, customerName, staticInvoiceId, cName, dateInvoice, status, staticStatus;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            invoiceId = itemView.findViewById(R.id.invoiceId);
            invoiceDate = itemView.findViewById(R.id.invoiceDate);
            customerName = itemView.findViewById(R.id.customerName);
            status = itemView.findViewById(R.id.status);
            staticInvoiceId = itemView.findViewById(R.id.staticId);
            cName = itemView.findViewById(R.id.staticCustomerName);
            dateInvoice = itemView.findViewById(R.id.staticDate);
            staticStatus = itemView.findViewById(R.id.staticStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());

        }
    }
}
