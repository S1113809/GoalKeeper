package com.example.goalkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity implements View.OnClickListener{
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        Button registerButton = findViewById(R.id.create_Acc_Button);
        registerButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:{
                TextView email = findViewById(R.id.createGoalDesc);
                TextView password = findViewById(R.id.register_password);
                String URL = "http://192.168.178.208:80/api/login";

                HashMap data = new HashMap();
                data.put("email", email.getText().toString());
                data.put("password", password.getText().toString());

                RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
                JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(data),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Login response: ", response.toString());
                                try {
                                    String status_code = response.get("status_code").toString();
                                    switch (status_code){
                                        case "400":{
                                            Toast toast = Toast.makeText(getApplicationContext(), "Vul alle velden in", Toast.LENGTH_SHORT);
                                            toast.show();
                                            break;
                                        }

                                        case "500":{
                                            Toast toast = Toast.makeText(getApplicationContext(), "E-mail/wachtwoord onjuist", Toast.LENGTH_SHORT);
                                            toast.show();
                                            break;
                                        }
                                        case "200":{
                                            Log.d("test", "ingelogd");
                                            saveToken(response.get("token").toString());
                                            Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(loginIntent);
                                            break;
                                        }
                                    }
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
                break;
            }
            case R.id.create_Acc_Button:{
                Intent toCreateAccScreen = new Intent(this, Register.class);
                startActivity(toCreateAccScreen);
                break;
            }
        }
    }

    public void saveToken(String token){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
        Log.d("SHAREDPREFS", "saveToken: Opgeslagen");
        Log.d("SHAREDPREFS", sharedPreferences.getString("token", ""));
    }
}