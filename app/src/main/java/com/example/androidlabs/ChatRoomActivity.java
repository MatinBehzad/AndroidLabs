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

        ListView list = (ListView)findViewById(R.id.theListView);

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
                boolean positive=true;
                ContentValues newRowValues =new ContentValues();

               newRowValues.put(MYopener.COL_Message,mess);
               newRowValues.put(String.valueOf(MYopener.COL_ISSEND),positive);
               long newId =db.insert(MYopener.TABLE_NAME,null,newRowValues);


               Message mio=new Message(mess,positive,newId);
                elements.add(mio);
                myAdapter.notifyDataSetChanged();

                meaage.setText("");



             });

        Button receive = findViewById(R.id.button8);
        receive.setOnClickListener(click ->{


              EditText meaage = findViewById(R.id.editChatMessage);
                String mess=meaage.getText().toString();
                boolean negetive=false;

                ContentValues newRowValues =new ContentValues();
                newRowValues.put(MYopener.COL_Message,mess);
                newRowValues.put(String.valueOf(MYopener.COL_ISSEND),negetive);
                long newId =db.insert(MYopener.TABLE_NAME,null,newRowValues);


                Message mio=new Message(mess,negetive,newId);
                elements.add(mio);

                myAdapter.notifyDataSetChanged();

                meaage.setText("");


        });

    }

    protected void deleteContact(Message c)
    {
       db.delete(MYopener.TABLE_NAME, MYopener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    protected void printCursor( Cursor c, int version){



    }

    private void loadDataFromDatabase(){
        //get a database connection:
        MYopener dbOpener = new MYopener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer

       /* String selectQuery = "SELECT  * FROM " + dbOpener.TABLE_NAME;
        db = dbOpener.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null,null);

        if(cursor.moveToFirst()){
            do {
                Message r =new Message();
                r.setMessage(cursor.getString(cursor.getColumnIndex(MYopener.COL_Message)));
               r.isSendMessage(cursor.getInt(cursor.getColumnIndex(MYopener.COL_ISSEND)==1 ? true: false;);
               r.setId(cursor.getLong(cursor.getColumnIndex(MYopener.COL_ID)));
               elements.add(r);
            } while (cursor.moveToNext());
        }*/

       // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MYopener.COL_ID, String.valueOf(MYopener.COL_ISSEND), MYopener.COL_Message};
        //query all the results from the database:
        Cursor results = db.query(false, MYopener.TABLE_NAME, columns,
                null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(MYopener.COL_Message);
        int sendColIndex = results.getColumnIndex(String.valueOf(MYopener.COL_ISSEND));
        int idColIndex = results.getColumnIndex(MYopener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String name = results.getString( messageColumnIndex);
           Boolean sr =  results.getInt(sendColIndex)==1 ? true: false;
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            elements.add(new Message(name, sr, id));
        }

        //At this point, the contactsList array has loaded every row from the cursor.

    }

    protected void showContact(int position)
    {
        Message selectedContact = elements.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE" + position)
                .setMessage("DO YOU WANT TO DELETE?")


                .setPositiveButton("YES", (click, b) -> {
                    deleteContact(selectedContact); //remove the contact from database
                    Toast.makeText(this,"deleted item id:"+myAdapter.getItemId(position),Toast.LENGTH_LONG).show();
                    elements.remove(position); //remove the contact from contact list
                    myAdapter.notifyDataSetChanged(); //there is one less item so update the list



                })

                .setNegativeButton("No", (click, b) -> {  })
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