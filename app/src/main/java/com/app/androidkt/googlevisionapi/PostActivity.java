package com.app.androidkt.googlevisionapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        String my_url = "http://52.78.159.170:3000/recommand/music";// Replace this with your own url
        //String my_data = "Hello my First Request Without any library";
        String json = "{\"light\":1,\"bright\":1,\"refreshing\":1}";
        String my_data = json;


  /*      try {

            JSONObject obj = new JSONObject(json);

            Log.d("My App", obj.toString());

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
*/
        Log.d("superdroid","here 1");


        new MyHttpRequestTask().execute(my_url,my_data);
        Log.d("superdroid","here 2");

    }

    private class MyHttpRequestTask extends AsyncTask<String,Integer,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("superdroid","here onpost Execute");

        }

        @Override
        protected String doInBackground(String... params) {
            String my_url = params[0];
            String my_data = params[1];
            try {
                Log.d("superdroid","here 1");
                URL url = new URL(my_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                // setting the  Request Method Type
                httpURLConnection.setRequestMethod("POST");
                // adding the headers for request
                httpURLConnection.setRequestProperty("Content-Type", "application/json");


   /*             try{


                    // to write tha data in our request
                    OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStreamWriter.write(my_data);
                    outputStreamWriter.flush();
                    outputStreamWriter.close();

                    // to log the response code of your request
                    Log.d("superdroid", "MyHttpRequestTask doInBackground : " +httpURLConnection.getResponseCode());
                    // to log the response message from your server after you have tried the request.
                    Log.d("superdroid", "MyHttpRequestTask doInBackground : " +httpURLConnection.getResponseMessage());
*/

                    try {


                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setChunkedStreamingMode(0);

                        OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
                        streamWriter.write(my_data);
                        streamWriter.flush();
                        streamWriter.close();

                        int status = httpURLConnection.getResponseCode();
                        if (status == 200){
                            InputStream in = httpURLConnection.getInputStream();

                            InputStreamReader inputStreamReader = new InputStreamReader(in);

                            int dataToRead = inputStreamReader.read();
                            String actualOutput = "";
                            while (dataToRead != -1) {
                                char current = (char) dataToRead;
                                dataToRead = inputStreamReader.read();
                                actualOutput = actualOutput + current;
                            }

                            Log.d("superduper", "MyHttpRequestTask doInBackground actualOutput : " +actualOutput);
                        }
                        Log.d("superduper", "MyHttpRequestTask doInBackground : " +httpURLConnection.getResponseCode());
                        Log.d("superduper", "MyHttpRequestTask doInBackground : " +httpURLConnection.getResponseMessage());

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    // this is done so that there are no open connections left when this task is going to complete
                    httpURLConnection.disconnect();
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }
}
