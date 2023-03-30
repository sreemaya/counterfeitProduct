package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class Scan_rfid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_rfid);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String ip = sh.getString("url", "");
        final String url = ip+"scanrfid";

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

                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("srno", js.getString("rfid"));
                                ed.commit();
                                Intent intp = new Intent(getApplicationContext(), Product.class);
                                startActivity(intp);

                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
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
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(60000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(postRequest);



    }
}