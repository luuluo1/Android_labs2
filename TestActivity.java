package com.example.oliver.android_labs;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestActivity extends AppCompatActivity {

    Context ctx;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button update_1=(Button) findViewById(R.id.Update_1);
        update_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fac f1=new fac();
                f1.execute();

            }
        });

    }


    class fac extends AsyncTask<String,Integer,String>{


        @Override
        protected String doInBackground(String... strings) {
           try {
               URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Toronto,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
               HttpURLConnection hc= (HttpURLConnection) url.openConnection();
               InputStream in = hc.getInputStream();

               XmlPullParserFactory xpf= XmlPullParserFactory.newInstance();
               XmlPullParser xpp=xpf.newPullParser();
               xpf.setNamespaceAware(false);

               xpp.setInput(in, "UTF-8");

               int type;
               while((type =xpp.getEventType())!=XmlPullParser.END_DOCUMENT){
                   if(xpp.getEventType()==XmlPullParser.START_TAG){
                       if(xpp.getName().equals("temperature")){
                           temp= xpp.getAttributeValue(null,"value");
                         //  publishProgress(20);
                           Log.i("Getting current temp",temp);

                       }
                   }
                    xpp.next();
               }

           }catch(Exception e){e.printStackTrace();;}return "";

        }

        @Override
        protected void onPostExecute(String s) {
            TextView t1= (TextView)findViewById(R.id.temp_1);
            t1.setText(temp);

        }
    }
}
