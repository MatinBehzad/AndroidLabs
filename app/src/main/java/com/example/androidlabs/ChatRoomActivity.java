package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
    private ArrayList<String> elements = new ArrayList<>();
    public boolean clickButton=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView list=findViewById(R.id.theListView);
        list.setAdapter(myAdapter=new MyListAdapter() );


        Button send=findViewById(R.id.button7);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                new ChatRoomActivity.Message().save();
                clickButton=true;
            }

        } );

        Button receive=findViewById(R.id.button8);
        receive.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                new ChatRoomActivity.Message().recieve();
                clickButton=false;

            }

        } );
    }


    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }

        public String getItem(int position) {
            return  elements.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {


            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.row_layout, parent, false);

            if(clickButton==true) {

                TextView tView = newView.findViewById(R.id.textGoesHere);
                tView.setText(getItem(position).toString());
            }

            else{

                TextView tView = newView.findViewById(R.id.recieve);
                tView.setText(getItem(position).toString());

            }

            return newView;
        }
    }

    private class Message {


        public EditText meaage=findViewById(R.id.editChatMessage);

        public void save(){

                    elements.add(meaage.getText().toString());
                    meaage.getText().clear();
                    myAdapter.notifyDataSetChanged();

        }

        private EditText getText() {
            return this.meaage;
        }

        private void setText(EditText message){
            this.meaage=message;
        }

        public void recieve(){

            elements.add(meaage.getText().toString());
            meaage.getText().clear();
            myAdapter.notifyDataSetChanged();
        }

    }

}