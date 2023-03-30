package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_man extends AppCompatActivity {
    ListView li;
    SharedPreferences sh;
    String url1,url;
    String[] s1,s2,s3,s4,s5,s6,s7,s8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_man);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url1=sh.getString("url","");
        url=url1+"viewman";
        li=findViewById(R.id.lk);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//view service code
                                JSONArray js= jsonObj.getJSONArray("data");//from python
//                                Toast.makeText(Viewfeedback.this, ""+js.toString(), Toast.LENGTH_SHORT).show();
                                s1=new String[js.length()];
                                s2=new String[js.length()];
                                s3=new String[js.length()];
                                s4=new String[js.length()];
                                s5=new String[js.length()];
                                s6=new String[js.length()];
                                s7=new String[js.length()];

                                for(int i=0;i<js.length();i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    s1[i] = u.getString("ins_id");//dbcolumn name
                                    s2[i] = u.getString("ins_name");//dbcolumn name
                                    s3[i] = u.getString("ins_license");
                                    s4[i] = u.getString("ins_establishedyr");
                                    s5[i] = u.getString("ins_place")+"\n"+u.getString("ins_post")+"\n"
                                            +u.getString("ins_district")+"\n"+u.getString("ins_pin");
                                    s6[i] = u.getString("ins_email");
                                    s7[i] = u.getString("ins_ph");

                                }
                                li.setAdapter(new Cusom_man(getApplicationContext(),s1,s2,s3,s4,s5,s6,s7));//custom_view_service.xml and li is the listview object
                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("lid",sh.getString("lid",""));
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

    }
}