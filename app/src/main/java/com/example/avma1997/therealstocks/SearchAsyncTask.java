package com.example.avma1997.therealstocks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Avma1997 on 7/10/2017.
 */

 public class SearchAsyncTask extends AsyncTask<String, Void, ArrayList<Stock>> {

    OnDownloadCompListener mListener;

    void setOnDownloadCompListener(OnDownloadCompListener listener) {
        mListener = listener;
    }


    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected ArrayList<Stock> doInBackground(String... params) {

        String urlString = params[0];

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();

            Scanner s = new Scanner(inputStream);
            String str = "";
            while (s.hasNext()) {
                str += s.nextLine();
            }

            Log.i("FetchedString ", str);
            return parseStocks(str);

        } catch (MalformedURLException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Stock> parseStocks(String str) {

        try {
            JSONArray stocksJson = new JSONArray(str);
            ArrayList<Stock> stocklist = new ArrayList<>();

            for (int i = 0; i < stocksJson.length(); i++) {
                JSONObject postJson = (JSONObject) stocksJson.get(i);
                String name = postJson.getString("company_name");
                String symbol=postJson.getString("company_symbol");


                Stock s = new Stock(symbol,name,"","","","","","","","","");
                stocklist.add(s);


            }

            return stocklist;


        } catch (JSONException e) {

        }
        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<Stock> stocklist) {
        super.onPostExecute(stocklist);
        if (mListener != null) {
            mListener.onDownloadComp(stocklist);
        }
    }
}

interface OnDownloadCompListener {

    void onDownloadComp(ArrayList<Stock> stocklist);

}


