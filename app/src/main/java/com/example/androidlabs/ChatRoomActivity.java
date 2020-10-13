package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    private MyListAdapter myAdapter;
   // private Message message=new Message();
    private ArrayList<String> elements = new ArrayList<>();
    SharedPreferences sharedpref=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView list=findViewById(R.id.theListView);
        list.setAdapter(myAdapter=new MyListAdapter() );

        /*Button send=findViewById(R.id.button7);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                elements.add("Hi");
                myAdapter.notifyDataSetChanged();
            }

        } );*/
    }


    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return 3;//elements.size();
        }

        public String getItem(int position) {
            return "This is row " + position;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.row_layout, parent, false);
            TextView tView = newView.findViewById(R.id.textGoesHere);
            tView.setText( getItem(position) );

         /*  TextView v=new TextView(ChatRoomActivity.this);
           v.setText(getItem(position));

            return v;*/
         return newView;
        }
    }

    private class Message{


        SharedPreferences sharedpref=getSharedPreferences("FileName",Context.MODE_PRIVATE);;
        EditText text=findViewById(R.id.editChatMessage);
        String savedString = sharedpref.getString("ChatMessage","Default Value");

       /* typeField=findViewById(R.id.editText1);
        typeField.setText(savedString);*/


        private void saveSharedPrefs(String stringToSave){
            SharedPreferences.Editor editor=sharedpref.edit();
            editor.putString("ChatMessage",stringToSave);
            editor.commit();
        }

    }

}