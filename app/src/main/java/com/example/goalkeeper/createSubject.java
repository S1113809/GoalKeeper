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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class createSubject extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);

        Button finishSubject = findViewById(R.id.finishGoalBtn);
        finishSubject.setOnClickListener(this);

        Button backButton = findViewById(R.id.createGoalBackBtn);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finishGoalBtn:{
                TextView subjectName = findViewById(R.id.createGoalName);
                checkIfEmpty(subjectName);
                TextView subjectDescription = findViewById(R.id.createGoalDesc);
                checkIfEmpty(subjectDescription);
                String URL = "http://192.168.178.208:80/api/create/subject";

                if(!subjectName.getText().toString().equals("") || !subjectDescription.getText().toString().equals("")){
                    HashMap data = new HashMap();
                    data.put("name", subjectName.getText().toString());
                    data.put("description", subjectDescription.getText().toString());

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
                                        Log.d("error", "onResponse: ");
                                    }
                                }
                            }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("gefaald", error.getMessage());

                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("Authorization", getToken());
                            return params;
                        }
                    };

                    VolleySingleton.getInstance(this).addToRequestQueue(jsonObj);
                    Intent toPrevScreenIntent = new Intent(this, MainActivity.class);
                    startActivity(toPrevScreenIntent);
                    Toast toast = Toast.makeText(this.getApplicationContext(), "Subject aangemaakt", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            }

            case R.id.createGoalBackBtn:{
                Intent back = new Intent(this, MainActivity.class);
                startActivity(back);
                break;
            }
        }
    }

    public void checkIfEmpty(TextView tv){
        if(tv.getText().toString().equals("")){
            tv.setBackgroundColor(getResources().getColor(R.color.GK_red));
        }
    }

    public String getToken(){
        final String SHARED_PREFS = "sharedPrefs";
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        Log.d("SHAREDPREFS", "saveToken: Token Opgehaald");
        Log.d("SHAREDPREFS", sharedPreferences.getString("token", ""));
        String fullToken = "Bearer " + sharedPreferences.getString("token", "");
        return fullToken;
    }
}