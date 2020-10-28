package com.example.androidlabs;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class ChatRoomActivity extends AppCompatActivity {

    private MyListAdapter myAdapter;
    private ArrayList<Message> elements = new ArrayList<>();
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView list = findViewById(R.id.theListView);

        loadDataFromDatabase();

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
                String mess=meaage.getText().toString();

                ContentValues newRowValues =new ContentValues();

                newRowValues.put(MYOpener.COL_Message,mess);

                long newId =db.insert(MYOpener.TABLE_NAME,null,newRowValues);


                Message mio=new Message(mess,true);
                elements.add(mio);
                myAdapter.notifyDataSetChanged();

                meaage.setText("");

                Toast.makeText(this,"inserted item id:"+newId,Toast.LENGTH_LONG).show();


            }
        });

        Button receive = findViewById(R.id.button8);
        receive.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText meaage = findViewById(R.id.editChatMessage);

                String mess=meaage.getText().toString();
                Message mio=new Message(mess,false);
                elements.add(mio);
                meaage.setText("");
                myAdapter.notifyDataSetChanged();
            }

        });
    }

    private void loadDataFromDatabase(){

    }
//•	You can select a chat row to view an AlertDialog with the index and database id	+1

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }
        public Message getItem(int position) {
            return elements.get(position);
        }
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        public View getView(int position, View old, ViewGroup parent) {


            LayoutInflater inflater = getLayoutInflater();

            Message mi = getItem(position);


                if (mi.isSendMessage()) {

                    old = inflater.inflate(R.layout.row_layout, parent, false);
                }
                else {

                    old = inflater.inflate(R.layout.recive_layout, parent, false);
                }

                    TextView tView = old.findViewById(R.id.textGoesHere);
                    tView.setText(getItem(position).getMessage());
                    return old;


        }
    }

}