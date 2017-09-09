package com.example.avma1997.therealstocks;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Avma1997 on 7/10/2017.
 */

public class SearchActivity extends AppCompatActivity implements OnDownloadCompListener {
    SearchView sv;
    ArrayList<Stock> stocklist;
    ListView stockListView;
    SearchArrayAdapter searchadapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        stockListView = (ListView) findViewById(R.id.stocksListView);

        stocklist = new ArrayList<>();

        searchadapter = new SearchArrayAdapter(this, stocklist);


        stockListView.setAdapter(searchadapter);
        stockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ID = getIntent().getIntExtra("add", -1);
                Stock s = stocklist.get(position);
                String a = s.symbol;
                String b=s.name;
                if(ID==-1) {
                    Intent i = new Intent(SearchActivity.this, ResultActivity.class);
                    i.putExtra("result", a);
                    i.putExtra("name", b);
                    startActivity(i);
                }
                else{

                    Intent i=new Intent();
                    i.putExtra("ticker",a);
                    setResult(RESULT_OK, i);
                    finish();

                }

            }
        });


        sv = (SearchView) findViewById(R.id.search_view);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String url = "https://stocksearchapi.com/api/?search_text=" + newText + "&api_key=15d184737470c74888c164776a171aa055ffa8ec";
                SearchAsyncTask searchAsyncTask = new SearchAsyncTask();
                searchAsyncTask.execute(url);
                searchAsyncTask.setOnDownloadCompListener(SearchActivity.this);
                return false;
            }
        });
    }

    public void onDownloadComp(ArrayList<Stock> stocklist) {
        if (stocklist == null)
            return;
        this.stocklist.clear();
        this.stocklist.addAll(stocklist);
        //   for(int i = 0; i < stocklist.size(); i++){
        //     this.stocklist.add(stocklist.get(i));
        // }
        searchadapter.notifyDataSetChanged();


    }


}

