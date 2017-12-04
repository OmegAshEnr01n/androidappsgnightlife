package com.example.dhritiwasan.nightlife;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button start;
    private TextView heading;
    private Button map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Shree714.ttc");
        start = (Button)findViewById(R.id.button2);
        start.setTypeface(custom_font);
        map = (Button) findViewById(R.id.Map);
        map.setTypeface(custom_font);
        heading = (TextView) findViewById(R.id.heading);
        heading.setTypeface(custom_font);
    }
    public void check(View v){
        Intent intent = new Intent(this, VishalActivity.class);
        startActivity(intent);
    }

    public void getStarted(View v) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }
}
