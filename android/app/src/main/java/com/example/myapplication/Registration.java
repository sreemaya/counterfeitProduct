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

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2,e3,e4;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        e1=findViewById(R.id.ET_uname);
        e2=findViewById(R.id.ET_pswd1);
        e3=findViewById(R.id.editText2);
        e4=findViewById(R.id.editText);
        b1=findViewById(R.id.btn_login);
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final  String n = e1.getText().toString();
        final String e = e2.getText().toString();
        final String p = e3.getText().toString();
        final String p1 = e4.getText().toString();
        int flg = 0;
        if(n.equals("")){
            e1.setError("*");
            flg++;
        }
        if(e.equals("")){
            e2.setError("*");
            flg++;
        }if(p.equals("")){
            e3.setError("*");
            flg++;
        }if(p1.equals("")){
            e4.setError("*");
            flg++;
        }
        if(p.length()!=10){
            e3.setError("*");
            flg++;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches())
        {
            e2.setError("Invalid Email");
            e2.requestFocus();
            flg++;

        }

        if(flg==0){
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final String ip = sh.getString("url", "");
            final String url = ip+"reg1";

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
                                    Intent intp = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intp);

                                } else {
                                    Toast.makeText(getApplicationContext(), ""+status, Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception ex) {

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

                    params.put("n", n);
                    params.put("e", e);
                    params.put("p", p);
                    params.put("p1", p1);


                    return params;
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(60000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(postRequest);


        }

    }
}
