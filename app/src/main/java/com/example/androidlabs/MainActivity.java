package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedpref=null;
    Button saveButton;
    EditText typeField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {// like main in java

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//this load the layout of screen

        sharedpref = getSharedPreferences("FileName",Context.MODE_PRIVATE);

        String savedString = sharedpref.getString("EmailAddress","Default Value");
        typeField=findViewById(R.id.editText1);
        typeField.setText(savedString);

        saveButton = findViewById(R.id.button);
        onPause();

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveButton.setOnClickListener(bt -> saveSharedPrefs(typeField.getText().toString()));

    }

    private void saveSharedPrefs(String stringToSave){
        SharedPreferences.Editor editor=sharedpref.edit();
        editor.putString("EmailAddress",stringToSave);
        editor.commit();
    }


}