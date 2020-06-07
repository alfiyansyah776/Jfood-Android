package com.dicoding.picodiploma.jfood_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class RemoveCartRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:8080/cart/customer/delete/";

    public RemoveCartRequest (int id, Response.Listener<String> listener){
        super(Method.DELETE, URL + id, listener, null);
    }
}
