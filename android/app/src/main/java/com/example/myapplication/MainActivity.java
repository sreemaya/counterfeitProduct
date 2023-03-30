package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText ip;
    Button save;

    @Override
    public void onBackPressed() {

        Intent ins=new Intent(Intent.ACTION_MAIN);
        ins.addCategory(Intent.CATEGORY_HOME);
        ins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ins);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip=(EditText)findViewById(R.id.ET_ip);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ip.setText(sh.getString("ip",""));
        save=(Button)findViewById(R.id.btn_ip);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String IP=ip.getText().toString();
        int flag=0;
        if(IP.equals("")){
            ip.setError("*");
            flag++;
        }
        if (flag==0) {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("ip", IP);
            ed.putString("url", "http://"+IP+":5000/");
            ed.commit();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }
    }
}
