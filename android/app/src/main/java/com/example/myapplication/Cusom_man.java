package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cusom_man extends BaseAdapter {
    String[] s1,s2,s3,s4,s5,s6,s7,s8;
    private Context context;
    public Cusom_man(Context applicationContext, String[] s1, String[] s2, String[] s3, String[] s4, String[] s5, String[] s6, String[] s7) {

   this.context=applicationContext;
   this.s1=s1;
   this.s2=s2;
   this.s3=s3;
   this.s4=s4;
   this.s5=s5;
   this.s6=s6;
   this.s7=s7;

    }

    @Override
    public int getCount() {
        return s4.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_cusom_man,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView m=(TextView)gridView.findViewById(R.id.textView3);
        TextView l=(TextView)gridView.findViewById(R.id.textView13);
        TextView y=(TextView)gridView.findViewById(R.id.textView14);
        TextView a=(TextView) gridView.findViewById(R.id.textView15);
        TextView e=(TextView) gridView.findViewById(R.id.textView16);
        TextView p=(TextView) gridView.findViewById(R.id.textView17);

        l.setTextColor(Color.BLUE);//color setting
        y.setTextColor(Color.BLUE);
        a.setTextColor(Color.BLUE);
        e.setTextColor(Color.BLUE);
        p.setTextColor(Color.BLUE);

        Button im=gridView.findViewById(R.id.button2);

        im.setTag(i);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int ik= Integer.parseInt(view.getTag().toString());
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("mid",s1[ik]);
                ed.commit();
                Intent ii = new Intent(context, View_prod.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);


            }
        });

        m.setText(s2[i]);
        l.setText(s3[i]);
        y.setText(s4[i]);
        a.setText(s5[i]);
        e.setText(s6[i]);
        p.setText(s7[i]);

        return gridView;

    }
}
