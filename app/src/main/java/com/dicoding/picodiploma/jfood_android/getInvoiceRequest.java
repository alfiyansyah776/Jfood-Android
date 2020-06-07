package com.dicoding.picodiploma.jfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getInvoiceRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/customer/";
    public getInvoiceRequest(int customerId, Response.Listener<String> listener)
    {
        super(Method.GET, URL + customerId, listener, null);
    }

}
