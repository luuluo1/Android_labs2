package com.example.oliver.android_labs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import junit.framework.Test;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button secAct=(Button) findViewById(R.id.button_to_sec);
        secAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(), SecActivity.class);
                startActivity(it);


            }
        });
        Button weather_fc=(Button) findViewById(R.id.button_to_fc);
        weather_fc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),WeatherForecast.class);
                startActivity(it);
            }
        });

        Button chat_b=(Button) findViewById(R.id.button_to_chat);
        chat_b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),ChatWindow.class);
                startActivity(it);
            }
        });

        Button Test_b=(Button) findViewById(R.id.button_to_Test);
        Test_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),TestActivity.class);
                startActivity(it);
            }
        });

        Button test_tb=findViewById(R.id.Test_tb);
        test_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(getApplicationContext(),TestToolbar.class);
                startActivity(it);
            }
        });

    }
}
