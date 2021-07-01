package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

//        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://google.com", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("gelukt", response.substring(50));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("niet gelukt", error.getMessage());
//            }
//        });
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onClick(View v) {
        TextView email = findViewById(R.id.email_textview);
        TextView password = findViewById(R.id.password_textview);
        String URL = "http://192.168.178.208:80/api/login";

        HashMap data = new HashMap();
        data.put("email", email.getText().toString());
        data.put("password", password.getText().toString());

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("gelukt", "response ontvangen!");
                        Log.d("response: ", response.toString());
                        try {
                            String token = response.get("token").toString();
                            Log.d("token", token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaald", error.getMessage());
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObj);
    }
}