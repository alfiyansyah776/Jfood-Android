package com.dicoding.picodiploma.jfood_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest {
    public static final String URL_CASH_INVOICE = "http://10.0.2.2:8080/invoice/createCashInvoice";
    public static final String URL_CASHLESS_INVOICE = "http://10.0.2.2:8080/invoice/createCashlessInvoice";
    private Map<String, String> params;

    public BuatPesananRequest(ArrayList<Integer> foodList, int totalPrice ,int CustomerId, int deliveryFee, Response.Listener<String> listener){
        super(Method.POST, URL_CASH_INVOICE, listener, null);

        String[] str = new String[foodList.size()];
        Object[] obj = foodList.toArray();

        int i = 0;
        for(Object object : obj){
            str[i++] = Integer.toString((Integer)object);
        }

        String newString = Arrays.toString(str);
        newString = newString.substring(1, newString.length()-1);

        params = new HashMap<>();
        params.put("foodIdList", newString);
        params.put("totalPrice", Integer.toString(totalPrice));
        params.put("customerId", Integer.toString(CustomerId));
        params.put("deliveryFee",Integer.toString(deliveryFee));

    }

    public BuatPesananRequest(ArrayList<Integer> foodList, int totalPrice ,int CustomerId, String promoCode, Response.Listener<String> listener){
        super(Method.POST, URL_CASHLESS_INVOICE, listener, null);

        String[] str = new String[foodList.size()];
        Object[] obj = foodList.toArray();

        int i = 0;
        for(Object object : obj){
            str[i++] = Integer.toString((Integer)object);
        }

        String newString = Arrays.toString(str);
        newString = newString.substring(1, newString.length()-1);

        params = new HashMap<>();
        params.put("foodIdList", newString);
        params.put("totalPrice", Integer.toString(totalPrice));
        params.put("customerId", Integer.toString(CustomerId));
        params.put("Promo", promoCode);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
