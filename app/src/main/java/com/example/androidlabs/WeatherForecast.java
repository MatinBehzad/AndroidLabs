package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherForecast extends AppCompatActivity {


    protected static final String ACTIVITY_NAME = "Weather Forecast";
    public TextView text1;
    public TextView text2;
    public TextView text3;
    public TextView text4;
    public ProgressBar bar;
    public  String [] result=new String [4];
    public Bitmap image;
    public  String iconName;
    public ImageView weatherImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        text1=findViewById(R.id.TextViewMin);
        text2=findViewById(R.id.TextViewMax);
        text3=findViewById(R.id. TextViewCurrent);
        text4=findViewById(R.id. TextViewUV);
        weatherImage=findViewById(R.id.weatherImage);

        bar=findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);

        ForecastQuery req= new ForecastQuery();
        req.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric","http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args)

        {
            URL url,urlJson;


            try {



                url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();


                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8"); //response is data from the server

                urlJson = new URL(args[1]);
                HttpURLConnection urlConnectionJson = (HttpURLConnection) urlJson.openConnection();
                InputStream responseJson = urlConnectionJson.getInputStream();

                //JSON reading:   Look at slide 26
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseJson, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String resultJson = sb.toString(); //result is the whole string
                JSONObject uvReport = new JSONObject(resultJson);

                //get the double associated with "value"
                result[3] = String.valueOf(uvReport.getDouble("value"));

                Log.i("MainActivity", "The uv is now: " + result[3]) ;





                String parameter = null;
                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {

                        if(xpp.getName().equals("temperature"))
                        {
                            result[0]=xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            result[1]=xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            result[2]=xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                        }


                        else if(xpp.getName().equals("weather"))
                        {
                            iconName=xpp.getAttributeValue(null,"icon");
                        }

                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

                String iconFile=iconName+".png";
                if(fileExistance(iconFile)){

                    FileInputStream fis = null;
                    try {   fis = openFileInput(iconFile); }
                    catch (FileNotFoundException e) {

                        e.printStackTrace();
                    }

                    image = BitmapFactory.decodeStream(fis);
                    Log.i(ACTIVITY_NAME, "Weather image exists, read from file");
                  }

                    else {

                    URL url2 = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                    HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                        if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                      }

                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i(ACTIVITY_NAME, "Weather image does not exist, adding new image from URL");

                        }
                Log.i(ACTIVITY_NAME, "file name = " + iconFile);
                publishProgress(100);

            }
            catch (Exception e)
            {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }

            return "Done";
        }

        public void onProgressUpdate(Integer... args ){

            bar.setVisibility(View.VISIBLE);
            bar.setProgress(args[0]);
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");

        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public void onPostExecute(String fromDoInBackground)
        {
            bar.setVisibility(View.INVISIBLE);
            text1.setText(result[0]);
            text2.setText(result[1]);
            text3.setText(result[2]);
            text4.setText(result[3]);
            weatherImage.setImageBitmap(image);

        }
    }
}