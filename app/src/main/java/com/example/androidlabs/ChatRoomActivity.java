package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;


public class ChatRoomActivity extends AppCompatActivity {

    private MyListAdapter myAdapter;
    private ArrayList<String> elements = new ArrayList<>(Arrays.asList());
    private Message mi=new Message();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView list = findViewById(R.id.theListView);
        list.setAdapter(myAdapter = new MyListAdapter());


        list.setOnItemLongClickListener( (p, b, pos, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("A title");
            alertDialogBuilder.setMessage("Do you want to delete?");

            alertDialogBuilder.setPositiveButton("Yes", (click, arg) -> {

                elements.remove(pos);
                myAdapter.notifyDataSetChanged();
            });

            alertDialogBuilder .setNegativeButton("No",(click, arg) -> {});

            alertDialogBuilder.show();
            return true;
        });




        Button send = findViewById(R.id.button7);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText meaage = findViewById(R.id.editChatMessage);
                elements.add(meaage.getText().toString());
                String mess=meaage.getText().toString();
               mi= new Message(mess,true);
                meaage.setText("");
                myAdapter.notifyDataSetChanged();

            }

        });

        Button receive = findViewById(R.id.button8);
        receive.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText meaage = findViewById(R.id.editChatMessage);
                elements.add(meaage.getText().toString());
                String mess=meaage.getText().toString();
                mi= new Message(mess,false);
                meaage.setText("");
                myAdapter.notifyDataSetChanged();


            }

        });
    }


    private class MyListAdapter extends BaseAdapter {


        public int getCount() {
            return elements.size();
        }

        public String getItem(int position) {
            return elements.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {


            LayoutInflater inflater = getLayoutInflater();




                if (position%2==0) {

                    old = inflater.inflate(R.layout.row_layout, parent, false);

                }
                else {

                    old = inflater.inflate(R.layout.recive_layout, parent, false);
                }

                    TextView tView = old.findViewById(R.id.textGoesHere);
                    tView.setText(getItem(position).toString());
                    return old;


        }
    }

}