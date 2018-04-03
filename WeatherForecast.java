package com.example.oliver.android_labs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    String current, min, max, iconName,speed;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar pb=(ProgressBar)findViewById(R.id.Progress_bar);
        pb.setVisibility(View.VISIBLE);

        factoryQue fq = new factoryQue();
        fq.execute();

    }

    public  class factoryQue extends  AsyncTask<String, Integer, String>{

        protected String doInBackground(String... strings) {
            String in="";
            try {
              URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Toronto,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
              HttpURLConnection hc =(HttpURLConnection) url.openConnection();
              InputStream inputSt=hc.getInputStream();

              XmlPullParserFactory xpf =XmlPullParserFactory.newInstance();
              xpf.setNamespaceAware(false);

              XmlPullParser xpp=xpf.newPullParser();
              xpp.setInput(inputSt,"UTF-8");

              int type;
              while((type =xpp.getEventType())!=XmlPullParser.END_DOCUMENT){
                  if(xpp.getEventType()==XmlPullParser.START_TAG){


                      if(xpp.getName().equals("temperature")){
                            max= xpp.getAttributeValue(null,"max");
                            publishProgress(20);
                         //   Thread.sleep(600);
                            Log.i("Getting Max temp",max);

                          min=xpp.getAttributeValue(null,"min");
                          publishProgress(40);
                          //Thread.sleep(400);
                          Log.i("Getting Min temp",min);

                          current=xpp.getAttributeValue(null,"value");
                          publishProgress(60);
                         // Thread.sleep(100);
                          Log.i("Getting Current temp",current);


                      }else if (xpp.getName().equals("speed")){
                          speed=xpp.getAttributeValue(null,"value");
                          publishProgress(80);
                         // Thread.sleep(200);
                          Log.i("Getting speed",speed);


                      }else if(xpp.getName().equals("weather")){
                          iconName=xpp.getAttributeValue(null,"icon");
                          publishProgress(82);
                          //Thread.sleep(100);
                          Log.i("Getting pic",iconName);

                            Log.i("Checking local icon",iconName);
                          if(!fileExistence(iconName+".png")){
                                URL imgURL=new URL("http://openweathermap.org/img/w/"+iconName+".png");
                                Bitmap inner_img=getImage(imgURL);
                                FileOutputStream out=openFileOutput(iconName+".png",MODE_PRIVATE);
                                inner_img.compress(Bitmap.CompressFormat.PNG,80,out);
                                Log.i("downloading icon",iconName);
                                publishProgress(90);
                                out.flush();out.close();

                                FileInputStream fileIn=null;
                                try{
                                    fileIn =openFileInput(iconName+".png");

                                }catch (FileNotFoundException e){
                                    e.printStackTrace();
                                }
                                image= BitmapFactory.decodeStream(fileIn);

                          }else {
                              Log.i("Icon exists",iconName);
                              FileInputStream fileput=null;
                              try{
                                  fileput =openFileInput(iconName+".png");

                              }catch (FileNotFoundException e){
                                  e.printStackTrace();
                              }
                              image= BitmapFactory.decodeStream(fileput);
                          }publishProgress(100);

                      }

                  }

                  xpp.next();
              }


          }
        catch (Exception e){
            e.printStackTrace();
        }
            return "";
        }
        public boolean fileExistence(String filename){
            File f1=getBaseContext().getFileStreamPath(filename);
            return f1.exists();

        }
        public Bitmap getImage(URL url){
            HttpURLConnection connection=null;
            try{
                connection=(HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode=connection.getResponseCode();
                if(responseCode==200){
                    return BitmapFactory.decodeStream(connection.getInputStream());
                }else
                    return null;

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }finally{
                if(connection !=null){
                    connection.disconnect();
                }
            }
        }
        public Bitmap getImage(String urlString){
            try{
                URL url=new URL(urlString);
                return getImage(url);

            }catch (MalformedURLException e){
                return null;
            }
        }
        protected void onProgressUpdate(Integer... values) {
            ProgressBar pb =(ProgressBar)findViewById(R.id.Progress_bar);
            pb.setProgress(values[0]);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            TextView spdText=(TextView)findViewById(R.id.W_speed);
            spdText.setText(getString(R.string.wind_speed)+" : "+speed);

            TextView currentTempview=(TextView)findViewById(R.id.current_temp);
            currentTempview.setText(getString(R.string.cur_tep)+" : "+current);

            TextView minTempView=(TextView)findViewById(R.id.Mini_temp);
            minTempView.setText(getString(R.string.min_tep)+" : "+min);

            TextView maxTempView=(TextView)findViewById(R.id.Max_temp);
            maxTempView.setText(getString(R.string.max_tep)+" : "+max);

            ImageView imageView=(ImageView) findViewById(R.id.weather_img);
            imageView.setImageBitmap(image);

            ProgressBar pb= (ProgressBar)findViewById(R.id.Progress_bar);
            pb.setVisibility(View.INVISIBLE);

        }

    }




}