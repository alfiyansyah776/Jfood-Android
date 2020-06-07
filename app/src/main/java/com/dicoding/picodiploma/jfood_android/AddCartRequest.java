package com.dicoding.picodiploma.jfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddCartRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/cart/";
    private Map<String, String> params;

    public AddCartRequest (int id, int foodId,  String name, int price, int quantity, int foodImage,
                           Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("customerId", Integer.toString(id));
        params.put("id", Integer.toString(foodId));
        params.put("name", name);
        params.put("price", Integer.toString(price));
        params.put("quantity", Integer.toString(quantity));
        params.put("foodImage", Integer.toString(foodImage));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
