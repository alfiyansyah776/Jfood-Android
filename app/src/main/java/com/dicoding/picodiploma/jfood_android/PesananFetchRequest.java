package com.dicoding.picodiploma.jfood_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PesananFetchRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:8080/invoice/customer/";

    public PesananFetchRequest (int id, Response.Listener<String> listener){
        super(Method.GET, URL + id, listener, null);
    }

}
