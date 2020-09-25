package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {// like main in java

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//this load the layout of screen

        TextView textView=findViewById(R.id.Textview);

        Button button=findViewById(R.id.button);
        button.setOnClickListener( (v) -> { Toast.makeText(this, this.getResources().getString(R.string.toast_message), LENGTH_LONG).show(); ; } );



        Switch onOffSwitch =  findViewById(R.id.switch2);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {

                Snackbar.make(textView, "The switch is now", Snackbar.LENGTH_LONG).setAction("Undo",click ->cb.setChecked(!b)).show();

            }

        });
    }
}