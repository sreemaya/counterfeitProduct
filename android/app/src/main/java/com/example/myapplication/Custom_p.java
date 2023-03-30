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
import android.widget.TextView;

public class Custom_p extends BaseAdapter {
    String[] s1,s2,s3,s4,s5,s6,s7,s8;
    private Context context;

    public Custom_p(Context applicationContext, String[] s1, String[] s2, String[] s3, String[] s4, String[] s5) {
        this.context=applicationContext;
        this.s1=s1;
        this.s2=s2;
        this.s3=s3;
        this.s4=s4;
        this.s5=s5;

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
            gridView=inflator.inflate(R.layout.activity_custom_p,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView m=(TextView)gridView.findViewById(R.id.textView20);
        TextView l=(TextView)gridView.findViewById(R.id.textView22);
        TextView y=(TextView)gridView.findViewById(R.id.textView24);
        TextView a=(TextView)gridView.findViewById(R.id.textView26);
        TextView e=(TextView) gridView.findViewById(R.id.textView28);

        l.setTextColor(Color.BLACK);//color setting
        y.setTextColor(Color.BLACK);
        a.setTextColor(Color.BLUE);
        e.setTextColor(Color.BLUE);
        m.setTextColor(Color.BLUE);

        m.setText(s1[i]);
        l.setText(s2[i]);
        y.setText(s3[i]);
        a.setText(s4[i]);
        e.setText(s5[i]);

        return gridView;

    }
}