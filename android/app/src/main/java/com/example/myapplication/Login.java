package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText uname, pswd;
    TextView fp, reg;
    Button log;


    @Override
    public void onBackPressed() {
        Intent ins=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(ins);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uname = (EditText) findViewById(R.id.username);
        pswd = (EditText) findViewById(R.id.password);
        fp = (TextView) findViewById(R.id.forgotpass);
        reg = (TextView) findViewById(R.id.others);
        log = (Button) findViewById(R.id.loginbtn);
        log.setOnClickListener(this);
        reg.setOnClickListener(this);
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),forgo.class);
                startActivity(in);

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==reg)
        {
            Intent in=new Intent(getApplicationContext(),Registration.class);
            startActivity(in);
        }
        if (view == log) {

            final  String username = uname.getText().toString();
            final String password = pswd.getText().toString();
            int flg = 0;
            if (username.equalsIgnoreCase("")) {
                uname.setError("missing");
                flg++;
            }
            if (password.equalsIgnoreCase("")) {
                pswd.setError("Missing");
                flg++;
            }

            if (flg == 0) {
                //  Toast.makeText(getApplicationContext(),"hloo",Toast.LENGTH_SHORT).show();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String ip = sh.getString("url", "");
                final String url = ip+"login";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {


                                    JSONObject js = new JSONObject(response);
                                    String status = js.getString("status");
                                    String lid = js.getString("lid");
                                    if (status.equalsIgnoreCase("ok")) {

                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor ed = sh.edit();
                                        ed.putString("lid", lid);
                                        ed.commit();
                                        Intent intp = new Intent(getApplicationContext(), MainActivity2.class);
                                        startActivity(intp);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception ex) {
                                    Toast.makeText(Login.this, ""+ex, Toast.LENGTH_SHORT).show();
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

                        params.put("username", username);
                        params.put("password", password);


                        return params;
                    }
                };
                postRequest.setRetryPolicy(new DefaultRetryPolicy(60000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(postRequest);


            }

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

