package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Product extends AppCompatActivity{
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t13;
    Button b,b2;
    ImageView img;
    String srno,url,urll;
    SharedPreferences sh;
    @Override
    public void onBackPressed() {
        Intent ins=new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(ins);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        t1=(TextView) findViewById(R.id.textView20);
        t2=(TextView) findViewById(R.id.textView22);
        t3=(TextView) findViewById(R.id.textView24);
        t4=(TextView) findViewById(R.id.textView26);
        t5=(TextView) findViewById(R.id.textView28);

        t6=(TextView) findViewById(R.id.textView18);
        t7=(TextView) findViewById(R.id.textView21);
        t8=(TextView) findViewById(R.id.textView23);
        t9=(TextView) findViewById(R.id.textView25);
        t10=(TextView) findViewById(R.id.textView27);
        t11=(TextView) findViewById(R.id.textView29);
        t13=(TextView) findViewById(R.id.textView4);
        t11.setVisibility(View.INVISIBLE);
        b2=findViewById(R.id.button3);
        b2.setVisibility(View.INVISIBLE);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intp = new Intent(getApplicationContext(), Complaintsend.class);
                startActivity(intp);
            }
        });
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        srno = sh.getString("srno", "");//getting id from scan qr
        String hu = sh.getString("url", "");
        url = hu + "andviewproducts";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // Display the response string.

               try {

                    JSONObject jsonObj1 = new JSONObject(response);
                    if (jsonObj1.getString("status").equalsIgnoreCase("ok")) {
//                        JSONObject jsonObj=jsonObj1.getJSONObject("result");

                        t1.setText(jsonObj1.getString("p"));
                        t2.setText(jsonObj1.getString("l"));
                        t3.setText(jsonObj1.getString("t"));
                        t4.setText(jsonObj1.getString("i"));
                        t5.setText(jsonObj1.getString("d"));
                        t13.setText("Copy right @ "+jsonObj1.getString("m"));
//                        b2.setVisibility(View.VISIBLE);

                    } else {
                        t1.setVisibility(View.INVISIBLE);
                        t2.setVisibility(View.INVISIBLE);
                        t3.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.INVISIBLE);
                        t5.setVisibility(View.INVISIBLE);
                        t6.setVisibility(View.INVISIBLE);
                        t7.setVisibility(View.INVISIBLE);
                        t8.setVisibility(View.INVISIBLE);
                        t9.setVisibility(View.INVISIBLE);
                        t10.setVisibility(View.INVISIBLE);
                        t13.setVisibility(View.INVISIBLE);
                        t11.setVisibility(View.VISIBLE);
                        b2.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("=========", e.toString());
                    Toast.makeText(Product.this, ""+e, Toast.LENGTH_SHORT).show();
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

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("srno", srno);//passing to python
                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=120000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


}