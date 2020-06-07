package com.dicoding.picodiploma.jfood_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PromoDatabaseRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:8080/promo";

    public PromoDatabaseRequest (Response.Listener<String> listener){
        super(Method.GET, URL, listener, null);
    }
}
