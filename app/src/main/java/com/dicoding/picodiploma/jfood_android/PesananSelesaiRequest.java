package com.dicoding.picodiploma.jfood_android;

import android.net.sip.SipSession;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananSelesaiRequest extends StringRequest {
    public static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;

    public PesananSelesaiRequest (int id, String status, Response.Listener<String> listener){
        super(Method.PUT, URL + id, listener, null);
        params = new HashMap<>();
        params.put("Status", status);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
