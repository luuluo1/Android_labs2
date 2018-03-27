package com.example.oliver.android_labs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.FontsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    ListView messageListView;
    EditText editText;
    Button send;
    ArrayList<String> chatMessages;
    ContentValues cv;
    SQLiteDatabase sDB;
    ChatDatabaseHelper cdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        messageListView = (ListView)findViewById(R.id.chat_list);
        editText = (EditText) findViewById(R.id.Msg_text);
        send = (Button) findViewById(R.id.send_btn);
        chatMessages = new ArrayList<String>();

        //in this case, “this” is the ChatWindow, which is-A Context object
        cdHelper = new ChatDatabaseHelper(this);
       final ChatAdapter messageAdapter = new ChatAdapter( this );
        messageListView.setAdapter (messageAdapter);
        messageAdapter.notifyDataSetChanged();


        sDB = cdHelper.getWritableDatabase();
        Cursor cursor = sDB.query(false, "Lab5", new String[] {"ID", "MESSAGE"}, "ID not null", null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            String ms = cursor.getString(1);
            chatMessages.add(ms);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor\'s  column count =" + cursor.getColumnCount() );
        for(int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Cursor\'s column count = " + cursor.getColumnName(i));
        }
        cv = new ContentValues();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                chatMessages.add(message);
                cv.put("MESSAGE", message);
                sDB.insert("Lab5", "Null replacement value", cv);
                editText.setText("");
                messageAdapter.notifyDataSetChanged();

            }
        });

    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return chatMessages.size();
        }

        public String getItem(int position){
            return chatMessages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup Parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0) {
                result = inflater.inflate(R.layout.activity_content_chat_row_outgoing, null);
            }
            else {
                result = inflater.inflate(R.layout.activity_content_chat_row_incoming, null);
            }

            TextView message = (TextView)result.findViewById(R.id.Msg_display);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sDB.close();
    }
}