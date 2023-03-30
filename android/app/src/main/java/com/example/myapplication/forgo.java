package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class forgo extends AppCompatActivity {
    EditText ip;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgo);
        ip=(EditText)findViewById(R.id.ET_ip);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        save=(Button)findViewById(R.id.btn_ip);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IP=ip.getText().toString();
                int flag=0;
                if(IP.equals("")){
                    ip.setError("*");
                    flag++;
                }
                if (flag==0) {

                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    final String ip = sh.getString("url", "");
                    final String url = ip+"forgo";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject js = new JSONObject(response);
                                        String status = js.getString("status");
                                        if (status.equalsIgnoreCase("ok")) {
                                            Toast.makeText(forgo.this, "Check your mail", Toast.LENGTH_SHORT).show();
                                            Intent intp = new Intent(getApplicationContext(), Login.class);
                                            startActivity(intp);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception ex) {
                                        Toast.makeText(getApplicationContext(), ""+ex, Toast.LENGTH_SHORT).show();
                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("username", IP);


                            return params;
                        }
                    };
                    postRequest.setRetryPolicy(new DefaultRetryPolicy(60000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    requestQueue.add(postRequest);

                }
            }
        });

    }
}