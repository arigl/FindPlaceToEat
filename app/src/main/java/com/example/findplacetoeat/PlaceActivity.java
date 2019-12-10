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
    String zip;
    String linkFood;

    String[] namesArray;
    String[] openArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            zip= null;
        } else {
            zip= "" + extras.getString("zipcode");
        }
        zip= "" + extras.getString("zipcode");
        System.out.println("ZIP = " + zip);

        generateFood();



        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resturantOne.setText("");
                resturantTwo.setText("");
                resturantThree.setText("");
                resturantFour.setText("");
                resturantFive.setText("");
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
            linkFood = "Chinese";
            PresentedImage = R.mipmap.chinese;
            //link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+"chinese"+"+in+"+"92869"+"&key=AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";

        }
        if(category == 1){
            categoryFood = "Italian";
            linkFood = "pasta";
            PresentedImage = R.mipmap.italian;
            //link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+"italian"+"+in+"+"92869"+"&key=AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";

        }
        if(category == 2){
            categoryFood = "American";
            linkFood = "burgers";
            PresentedImage = R.mipmap.american;
            //link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+"American"+"+in+"+"92869"+"&key=AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";

        }
        if(category == 3){
            categoryFood = "Indian";
            linkFood = "Indian";
            PresentedImage = R.mipmap.indian;
            //link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+"Indian"+"+in+"+"92869"+"&key=AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";

        }

        ((TextView)findViewById(R.id.text1)).setText("You are eating: " + categoryFood);
        //resturantLabel.setText("Here are some resturants in your area: ");
        ((ImageView)findViewById(R.id.typeimg)).setImageDrawable(getResources().getDrawable(PresentedImage));

        //zip = getIntent().getStringExtra("zipcode");
        //String s = getIntent().get("android.text") + "";

        //link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + categoryFood +"in+[" + OpenList.getStringExtra("zipcode") + "]&key=[" + apiKey + "]";
        //link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+categoryFood+"+in+"+zip+"&key=AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";
        //link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+linkFood+"+in+"+"92869"+"&key=AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";
        link = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+linkFood+"+in+"+zip+"&key=AIzaSyBQa6Ff8Xe0R6Tjdb9ScLwQD-Hpn3uZZfg";



        System.out.println(link);
        new JsonTask().execute(link);
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
                JSONArray names = jResult.getJSONArray("results");
                //JSONArray hours = jResult.getJSONArray("opening_hours");


                for (int i = 0; i < names.length(); i++){
                    namesArray[i] = names.getJSONObject(i).getString("name");
                    //openArray[i] = hoursy.getJSONObject(i).getString("open_now");
                    //System.out.println(names.getJSONObject(i).getString("name"));
                    System.out.println(namesArray[i] + ": " + i);
                    //System.out.println(openArray[i] + ": " + i);
                    /*
                    else{
                        resturantOne.setText(namesArray[0] + "");
                        resturantTwo.setText(namesArray[1] + "");
                        resturantThree.setText(namesArray[2] + "");
                        resturantFour.setText(namesArray[3] + "");
                        resturantFive.setText(namesArray[4] + "");
                    }
                    */
                }


                //System.out.println(jsonObject.getString("name"));
            } catch (JSONException e) {
                //e.printStackTrace();
            }

            //if (namesArray[0] != null && namesArray[0].contains(zip + "")){
              //  System.out.println("empty");
            //}

            resturantOne.setText(namesArray[0] + "");
            resturantTwo.setText(namesArray[1] + "");
            resturantThree.setText(namesArray[2] + "");
            resturantFour.setText(namesArray[3] + "");
            resturantFive.setText(namesArray[4] + "");


            if (namesArray[0] == "null" || namesArray[0] == null){
                resturantOne.setText("No results found");
                resturantTwo.setText("Check google maps for more information!");
                resturantThree.setText("");
                resturantFour.setText("");
                resturantFive.setText("");
                //generateFood();
            }

            if (namesArray[0] == "The Grand Terrace"){
                resturantOne.setText("No results found");
                resturantTwo.setText("Check google maps for more information!");
                resturantThree.setText("");
                resturantFour.setText("");
                resturantFive.setText("");
                //generateFood();
            }
            try{
                if (namesArray[0].contains("92869")){
                    resturantOne.setText("No results found");
                    resturantTwo.setText("Check google maps for more information!");
                    resturantThree.setText("");
                    resturantFour.setText("");
                    resturantFive.setText("");
                }
            }catch(Exception ex){
                System.out.println("index 0 is null");
            }

        }
    }
}
