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
 * Created by Avma1997 on 7/9/2017.
 */

public class StocksAsyncTask extends AsyncTask<String, Void, ArrayList<Stock>> {
    OnDownloadCompleteListener mListener;

    void setOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
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
            ArrayList<Stock> stocklist = new ArrayList<>();
            JSONObject stocksJson = new JSONObject(str);
            JSONObject query = stocksJson.getJSONObject("query");
            int count=query.getInt("count");
            Log.i("mytag",count+"");
            JSONObject results = query.getJSONObject("results");
            if(count==1) {

                JSONObject item=results.getJSONObject("quote") ;
                String symbol=item.getString("symbol");
                String lasttradedprice = item.getString("LastTradePriceOnly");
                String marketcap=item.getString("MarketCapitalization");
                String open=item.getString("Open");
                String close=item.getString("PreviousClose");
                String dividend=item.getString("DividendShare");
                String volume=item.getString("Volume");
                String oneyeartargetprice=item.getString("OneyrTargetPrice");
                Log.i("price",lasttradedprice+"");
                String change=item.getString("Change");
                String percentchange=item.getString("ChangeinPercent");
                String name="";

                Stock s = new Stock(symbol,name,lasttradedprice,change,percentchange,open,close,oneyeartargetprice,volume,dividend,marketcap);
                stocklist.add(s);
                return stocklist;





            }



            JSONArray quote =  results.getJSONArray("quote");




            for (int i = 0; i < quote.length(); i++) {
                JSONObject postJson = (JSONObject) quote.get(i);
                String symbol=postJson.getString("symbol");
                String lasttradedprice = postJson.getString("LastTradePriceOnly");
                String marketcap=postJson.getString("MarketCapitalization");
                String open=postJson.getString("Open");
                String close=postJson.getString("PreviousClose");
                String dividend=postJson.getString("DividendShare");
                String volume=postJson.getString("Volume");
                String oneyeartargetprice=postJson.getString("OneyrTargetPrice");
                Log.i("price",lasttradedprice+"");
                String change=postJson.getString("Change");
                String percentchange=postJson.getString("ChangeinPercent");
                String name="";
                Stock s = new Stock(symbol,name,lasttradedprice,change,percentchange,open,close,oneyeartargetprice,volume,dividend,marketcap);


                stocklist.add(s);


            }

            return stocklist;


        } catch (JSONException e) {

        }
        return null;

    }


    protected void onPostExecute(ArrayList<Stock> stocklist) {
        super.onPostExecute(stocklist);
        if (mListener != null) {
            mListener.onDownloadComplete(stocklist);
        }
    }
}

 interface OnDownloadCompleteListener {

    void onDownloadComplete(ArrayList<Stock> stocklist);

}

