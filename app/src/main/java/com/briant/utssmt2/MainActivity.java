package com.briant.utssmt2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String strid,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tved);
    }

    @Override
    public void onStart(){
        super.onStart();
        Intent ambildata=getIntent();
        strid = ambildata.getStringExtra("username");
        textView.setText(strid);
    }
}