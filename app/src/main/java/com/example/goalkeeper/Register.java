package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerBtn = findViewById(R.id.register_button);
        registerBtn.setOnClickListener(this);

        Button backBtn = findViewById(R.id.register_cancel);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:{
                TextView regName = findViewById(R.id.register_name);
                checkIfEmpty(regName);
                TextView regEmail = findViewById(R.id.register_email);
                checkIfEmpty(regEmail);
                TextView regPass = findViewById(R.id.register_password);
                checkIfEmpty(regPass);
                String URL = "http://192.168.178.208:80/api/register";

                if(!regName.getText().toString().equals("") || !regEmail.getText().toString().equals("") || !regPass.getText().toString().equals("")){
                    HashMap data = new HashMap();
                    data.put("name", regName.getText().toString());
                    data.put("email", regEmail.getText().toString());
                    data.put("password", regPass.getText().toString());

                    RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
                    JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(data),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("gelukt", "response ontvangen!");
                                    Log.d("response: ", response.toString());
                                    try {
                                        String message = response.get("message").toString();
                                        Log.d("message", message);
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
                    Intent toPrevScreenIntent = new Intent(this, Login.class);
                    startActivity(toPrevScreenIntent);
                    Toast toast = Toast.makeText(this.getApplicationContext(), "Account aangemaakt", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            }

            case R.id.register_cancel:{
                Intent toPrevScreenIntent = new Intent(this, Login.class);
                startActivity(toPrevScreenIntent);
                break;
            }
        }
    }

    public void checkIfEmpty(TextView tv){
        if(tv.getText().toString().equals("")){
            tv.setBackgroundColor(getResources().getColor(R.color.GK_red));
        }
    }
}