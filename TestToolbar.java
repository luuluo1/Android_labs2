package com.example.oliver.android_labs;

import com.example.oliver.android_labs.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.LayoutInflater;

public class TestToolbar extends AppCompatActivity {
    Snackbar snackbar;
    String msg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ToolBar);
        setSupportActionBar(toolbar);

        msg1="You selected item 1";

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You selected item 1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.tool_bar,m);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem mi){
        int id=mi.getItemId();
        switch(id){
            case R.id.action_back:
                Log.d("Toolbar","Option call selected");

                        AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                        // 2. Chain together various setter methods to set the dialog characteristics

                        builder.setMessage(R.string.q1)
                                .setPositiveButton(R.string.y1, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User clicked OK button
                                        Intent resultIntent = new Intent(getApplicationContext(),StartActivity.class);
                                     //   resultIntent.putExtra("Response", getResources().getString(R.string.r1));
                                       // setResult(Activity.RESULT_OK, resultIntent);
                                        startActivity(resultIntent);
                                        finish();
                                    }
                                })
                                .setNegativeButton(R.string.y2, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.action_sbar:
                snackbar.make(findViewById(android.R.id.content), msg1, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    Log.d("Toolbar","Option camera selected");
                break;

            case R.id.action_msg:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(TestToolbar.this);
                // Get the layout inflater
                LayoutInflater inflater = TestToolbar.this.getLayoutInflater();
                View v1=inflater.inflate(R.layout.activity_dialong_layout,null);
                final EditText edt1=v1.findViewById(R.id.input_msg);
                builder1.setView(v1)
                        // Add action buttons
                        .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                               msg1= edt1.getText().toString();
                            }
                        })
                        .setNegativeButton(R.string.cancel_1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                 builder1.create().show();
                Log.d("Toolbar","Option musik selected");

                break;
            case R.id.T_about:
                Toast toast = Toast.makeText(getApplicationContext(), "Version 1.0, Yang Luo", Toast.LENGTH_SHORT);
                toast.show();
        }
    return true; }

}
