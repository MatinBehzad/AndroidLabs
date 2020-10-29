package com.example.androidlabs;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
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

import static android.widget.Toast.LENGTH_LONG;


public class ChatRoomActivity extends AppCompatActivity {

    private MyListAdapter myAdapter;
    private ArrayList<Message> elements = new ArrayList<>();
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView list = findViewById(R.id.theListView);



        list.setAdapter(myAdapter = new MyListAdapter());
        loadDataFromDatabase();

        list.setOnItemClickListener(( parent,  view,  position,  id) -> {
            showContact( position );
        });


        Button send = findViewById(R.id.button7);
        send.setOnClickListener(click ->

             {
                 EditText meaage = findViewById(R.id.editChatMessage);
                String mess=meaage.getText().toString();
                boolean b=true;
                ContentValues newRowValues =new ContentValues();

                newRowValues.put(MYOpener.COL_Message,mess);
               // newRowValues.put(MYOpener.COL_ISSEND,b);

                long newId =db.insert(MYOpener.TABLE_NAME,null,newRowValues);




                Message mio=new Message(mess,b);
                elements.add(mio);
                myAdapter.notifyDataSetChanged();

                meaage.setText("");



             });

        Button receive = findViewById(R.id.button8);
        receive.setOnClickListener(click ->{



                EditText meaage = findViewById(R.id.editChatMessage);
                String mess=meaage.getText().toString();
                boolean f=false;

                ContentValues newRowValues =new ContentValues();
                newRowValues.put(MYOpener.COL_Message,mess);
                //newRowValues.put(MYOpener.COL_ISSEND,f);

                long newId =db.insert(MYOpener.TABLE_NAME,null,newRowValues);


                Message mio=new Message(mess,f);
                elements.add(mio);

                myAdapter.notifyDataSetChanged();

                meaage.setText("");


        });

    }

    protected void deleteContact(Message c)
    {
        db.delete(MYOpener.TABLE_NAME, MYOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    private void loadDataFromDatabase(){



        MYOpener dbOpener =new MYOpener(this);

        db=dbOpener.getWritableDatabase();

        String [] columns= {MYOpener.COL_ID,MYOpener.COL_Message};



        Cursor results = db.query(false, MYOpener.TABLE_NAME, columns,
                null, null, null, null, null, null);

        int messageCol = results.getColumnIndex(MYOpener.COL_Message);
        int idCol= results.getColumnIndex(MYOpener.COL_ID);
    //    int send =results.getColumnIndex(MYOpener.COL_ISSEND);


        while(results.moveToNext())
        {
            String payam = results.getString(messageCol);
            long id = results.getLong(idCol);
         //   boolean se=results.moveToPosition(send);

            //add the new Contact to the array list:
            elements.add(new Message(payam,id));
        }

    }

    protected void showContact(int position)
    {
        Message selectedContact = elements.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE" + position)
                .setMessage("DO YOU WANT TO DELETE?")


                .setPositiveButton("YES", (click, b) -> {
                    deleteContact(selectedContact); //remove the contact from database
                    elements.remove(position); //remove the contact from contact list
                    myAdapter.notifyDataSetChanged(); //there is one less item so update the list

                   Toast.makeText(this,"deleted item id:"+myAdapter.getItemId(position),Toast.LENGTH_LONG).show();

                })

                .setNegativeButton("No", (click, b) -> { })
                .create().show();
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