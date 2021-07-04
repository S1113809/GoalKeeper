package com.example.goalkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements SubjectAdapter.OnNoteListener{
    SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Subject> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        String URL = "http://192.168.178.208:80/api/subjects";
        HashMap data = new HashMap();
        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, URL,new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("gelukt", "response ontvangen!");
                        Log.d("response: ", response.toString());
                        for(int i=0; i<response.length();i++){
                            try {
                                String index = Integer.toString(i);
                                subjects.add(new Subject(Integer.parseInt(response.getJSONObject(index).get("id").toString()),response.getJSONObject(index).get("title").toString(), response.getJSONObject(index).get("description").toString()));
                                recyclerViewAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

        recyclerViewAdapter = new SubjectAdapter(subjects, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createSubjectIntent = new Intent(getApplicationContext(), createSubject.class);
                startActivity(createSubjectIntent);
            }
        });
    }

    @Override
    public void OnNoteClick(int position) {
        Subject subject = subjects.get(position);
        String nameStr = subject.getName();
        String descStr = subject.getDescription();
        int idStr = subject.getId();
        Bundle bundle = new Bundle();
        bundle.putString("subjectName", nameStr);
        bundle.putString("subjectDesc", descStr);
        bundle.putInt("subjectId", idStr);
        Intent toNextScreenIntent = new Intent(this, GoalsInSubject.class);
        toNextScreenIntent.putExtras(bundle);
        startActivity(toNextScreenIntent);
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