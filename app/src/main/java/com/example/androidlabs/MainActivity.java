package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {// like main in java
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//this load the layout of screen
    }
}