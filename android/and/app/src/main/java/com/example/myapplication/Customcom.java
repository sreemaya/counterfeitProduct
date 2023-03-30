package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Customcom extends BaseAdapter {
    String[] name,photo,feedback,date,s3;
    private  Context context;
    public Customcom(Context applicationContext, String[] name, String[] photo, String[] feedback, String[] date, String[] s3) {
        this.context=applicationContext;
        this.name=name;
        this.photo=photo;
        this.feedback=feedback;
        this.date=date;
        this.s3=s3;
    }


    @Override
    public int getCount() {
        return date.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_customcom,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView cla=(TextView)gridView.findViewById(R.id.textView30);
        TextView sc=(TextView)gridView.findViewById(R.id.textView32);
        TextView ty=(TextView)gridView.findViewById(R.id.textView34);
        TextView ij=(TextView) gridView.findViewById(R.id.textView36);

        cla.setTextColor(Color.BLACK);//color setting
        sc.setTextColor(Color.BLACK);
        ty.setTextColor(Color.BLUE);
        ij.setTextColor(Color.BLUE);

        ImageView im=gridView.findViewById(R.id.imageView4);

        im.setTag(i);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int ik= Integer.parseInt(view.getTag().toString());
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                String ip=sh.getString("url","");
                String url=ip+"delcom";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//
                                        Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
                                        Intent ii = new Intent(context, Comp.class);
                                        ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(ii);


                                    } else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    //                value Passing android to python
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("pid", s3[ik]);


                        return params;
                    }
                };


                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

            }
        });

        cla.setText(name[i]);
        sc.setText(photo[i]);
        ty.setText(feedback[i]);
        ij.setText(date[i]);

        return gridView;

    }
}
