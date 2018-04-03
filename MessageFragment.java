package com.example.oliver.android_labs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MessageFragment extends Fragment {
    public boolean isTablet;
    TextView message_here;
    TextView message_id;
    Button btn_delete;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_message_textview, container, false);

        message_here = view.findViewById(R.id.textView_message);
        message_id = view.findViewById(R.id.textView2_id);
        btn_delete = view.findViewById(R.id.button_delete_message);

        bundle = getArguments();

        String message = bundle.getString("Message");
        final long id = bundle.getLong("ID");
        final long id_inChat = bundle.getLong("IDInChat");

        message_here.setText(message);
        message_id.setText(String.valueOf(id));

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTablet) {
                    ChatWindow cw = (ChatWindow)getActivity();
                    cw.deleteForTablet(id, id_inChat);
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("DeleteID", id);
                    resultIntent.putExtra("IDInChat", id_inChat);
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setIsTablet(boolean isTablet){

        this.isTablet = isTablet;
    }
}