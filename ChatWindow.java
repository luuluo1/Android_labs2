package com.example.oliver.android_labs;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ChatWindow extends AppCompatActivity {

    final ArrayList<String> chatArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        final ListView listViewChat = (ListView) findViewById(R.id.chat_list);
        final ChatAdapter chatAdapter = new ChatAdapter(this);
        listViewChat.setAdapter(chatAdapter);
        final EditText editTextChat = (EditText) findViewById(R.id.Msg_text);
        Button buttonSend = (Button) findViewById(R.id.send_btn);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatString = editTextChat.getText().toString();
                chatArray.add(chatString);
                chatAdapter.notifyDataSetChanged();
                editTextChat.setText("");
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context context) {
            super(context, 0);
        }

        public int getCount() {
            return chatArray.size();
        }

        public String getItem(int position) {
            return chatArray.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position%2 == 0) {
                result = inflater.inflate(R.layout.activity_content_chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.activity_content_chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById((R.id.Msg_display));
            message.setText(getItem(position));
            return result;
        }
    }

}