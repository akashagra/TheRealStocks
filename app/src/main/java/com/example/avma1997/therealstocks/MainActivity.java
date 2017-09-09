package com.example.avma1997.therealstocks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDownloadCompleteListener {
    ArrayList<Stock> stocklist;
    ListView stockListView;
    StockArrayAdapter stockadapter;
    String urlString;
    String url;
    String item;
    int count = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("the_real_stocks",MODE_PRIVATE);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                i.putExtra("add", 5);

                startActivityForResult(i, 1);
            }
        });
        stockListView = (ListView) findViewById(R.id.stockListView);
        stockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Stock s = stocklist.get(position);
                String a = s.symbol;
                Intent i = new Intent(MainActivity.this, ResultActivity.class);
                i.putExtra("result", a);
                startActivity(i);

            }
        });
        stocklist = new ArrayList<>();

        stockadapter = new StockArrayAdapter(this, stocklist);


        stockListView.setAdapter(stockadapter);
        urlString = "https://query.yahooapis.com/v1/public/yql?q=select+%2A+from+yahoo.finance.quotes+where+symbol+in+";
        url = "%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback";
        item=  sharedPreferences.getString("item",null);
        count=sharedPreferences.getInt("count",0);
        if(item !=null)
        {
            String result=urlString+item+url;
            fetchStocks(result);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("the_real_stocks",MODE_PRIVATE);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                i.putExtra("add", 5);

                startActivityForResult(i, 1);
            }
        });
        stockListView = (ListView) findViewById(R.id.stockListView);
        stockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Stock s = stocklist.get(position);
                String a = s.symbol;
                Intent i = new Intent(MainActivity.this, ResultActivity.class);
                i.putExtra("result", a);
                startActivity(i);

            }
        });
        stocklist = new ArrayList<>();

        stockadapter = new StockArrayAdapter(this, stocklist);


        stockListView.setAdapter(stockadapter);
        urlString = "https://query.yahooapis.com/v1/public/yql?q=select+%2A+from+yahoo.finance.quotes+where+symbol+in+";
        url = "%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback";
        item=  sharedPreferences.getString("item",null);
        count=sharedPreferences.getInt("count",0);
        if(item !=null)
        {
            String result=urlString+item+url;
            fetchStocks(result);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchStocks(String urlString) {
//String urlString="https://query.yahooapis.com/v1/public/yql?q=select+%2A+from+yahoo.finance.quotes+where+symbol+in+%28%22MSFT%22%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback";
//       String t="%28%22YHOO%22%2C%22AAPL%22%2C%22GOOG%22%2C%22MSFT%22%29";

        StocksAsyncTask stockAsyncTask = new StocksAsyncTask();
        stockAsyncTask.execute(urlString);
        stockAsyncTask.setOnDownloadCompleteListener(this);

    }

    @Override
    public void onDownloadComplete(ArrayList<Stock> stocklist) {
        this.stocklist.clear();
        this.stocklist.addAll(stocklist);
        //   for(int i = 0; i < stocklist.size(); i++){
        //     this.stocklist.add(stocklist.get(i));
        // }
        stockadapter.notifyDataSetChanged();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String a = data.getStringExtra("ticker");
                if (count == 0) {
                    item = "%28%22" + a + "%22";

                    String result = urlString + item + url;
                    fetchStocks(result);
                    count++;

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("item",item);
                    editor.putInt("count",count);
                    editor.commit();

                } else {
                    item = item + "%2C%22" + a + "%22";
                    String result = urlString + item + url;
                    Log.i("result",result);
                    fetchStocks(result);
                    count++;
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("item",item);
                    editor.putInt("count",count);
                    editor.commit();

                }


            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}
