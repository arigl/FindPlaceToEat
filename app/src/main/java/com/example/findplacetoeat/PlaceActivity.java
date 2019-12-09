package com.example.findplacetoeat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PlaceActivity extends AppCompatActivity {

    Button btnHit;
    TextView txtJson;

    TextView resturantOne;
    TextView resturantTwo;
    TextView resturantThree;
    TextView resturantFour;
    TextView resturantFive;


    ProgressDialog pd;
    String categoryFood;
    String apiKey = "AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";
    String link = "";

    String[] namesArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        generateFood();



        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateFood();
            }
        });

    }

    private void generateFood() {
        namesArray = new String[1000];

        btnHit = (Button) findViewById(R.id.btnHit);
        txtJson = (TextView) findViewById(R.id.tvJsonItem);


        resturantOne = (TextView) findViewById(R.id.resturantOne);
        resturantTwo = (TextView) findViewById(R.id.resturantTwo);
        resturantThree = (TextView) findViewById(R.id.resturantThree);
        resturantFour = (TextView) findViewById(R.id.resturantFour);
        resturantFive = (TextView) findViewById(R.id.resturantFive);

        int PresentedImage = 0;
        int category = new Random().nextInt(4);

        if(category == 0){
            categoryFood = "Chinese";
            PresentedImage = R.mipmap.chinese;

        }
        if(category == 1){
            categoryFood = "Italian";
            PresentedImage = R.mipmap.italian;

        }
        if(category == 2){
            categoryFood = "American";
            PresentedImage = R.mipmap.american;

        }
        if(category == 3){
            categoryFood = "Indian";
            PresentedImage = R.mipmap.indian;

        }

        ((TextView)findViewById(R.id.text1)).setText("You are eating: " + categoryFood);
        //resturantLabel.setText("Here are some resturants in your area: ");
        ((ImageView)findViewById(R.id.typeimg)).setImageDrawable(getResources().getDrawable(PresentedImage));

        Intent OpenList = getIntent();
        //link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + categoryFood +"in+[" + OpenList.getStringExtra("zipcode") + "]&key=[" + apiKey + "]";
        link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+categoryFood+"+in+"+OpenList.getStringExtra("zipcode")+"&key=AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";
        new JsonTask().execute(link);
        generateResults();

    }

    private void generateResults(){

    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(PlaceActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                    //System.out.println("Response: " + "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            //txtJson.setText(result);

            try {
                JSONObject jResult = new JSONObject(result);
                // JSONObject jsonObject = jResult.getJSONObject("result");
                //JSONObject jsonObject = jResult.getJSONObject("result");
                //JSONObject jsonName = jsonObject.getJSONObject("name");


                JSONArray names = jResult.getJSONArray("results");

                for (int i = 0; i < names.length(); i++){
                    namesArray[i] = names.getJSONObject(i).getString("name");
                    System.out.println(names.getJSONObject(i).getString("name"));
                    resturantOne.setText(namesArray[0]);
                    resturantTwo.setText(namesArray[1]);
                    resturantThree.setText(namesArray[2]);
                    resturantFour.setText(namesArray[3]);
                    resturantFive.setText(namesArray[4]);
                }
                //System.out.println(jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
