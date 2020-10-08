package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton mImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText emailEditText=findViewById(R.id.editText4);

        Intent fromMain = getIntent();
        emailEditText.setText( fromMain.getStringExtra("Email"));

        mImageButton=(ImageButton)findViewById(R.id.button2);
        mImageButton.setOnClickListener(bt ->  dispatchTakePictureIntent());

        Log.e("PROFILE_ACTIVITY","function is" + "onCreate");


    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e("PROFILE_ACTIVITY","function is" + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("PROFILE_ACTIVITY","function is" + "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("PROFILE_ACTIVITY","function is" + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("PROFILE_ACTIVITY","function is" + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("PROFILE_ACTIVITY","function is" + "onDestroy");
    }



}