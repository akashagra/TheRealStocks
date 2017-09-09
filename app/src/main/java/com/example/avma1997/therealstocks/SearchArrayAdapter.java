package com.example.avma1997.therealstocks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Avma1997 on 7/10/2017.
 */

public class SearchArrayAdapter extends ArrayAdapter<Stock> {
    ArrayList<Stock> stocklist;
    Context context;


    public SearchArrayAdapter(Context context, ArrayList<Stock> stocklist) {
        super(context, 0);
        this.context = context;
        this.stocklist = stocklist ;

    }
    public int getCount() {
        return stocklist.size();
    }

    static class SearchViewHolder {

        TextView symbol;
        TextView name;


       SearchViewHolder(TextView symbol, TextView name) {

            this.symbol=symbol;
            this.name=name;



        }

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item, null);

            TextView symbol = (TextView) convertView.findViewById(R.id.symbols);
            TextView name = (TextView) convertView.findViewById(R.id.name);


           SearchArrayAdapter.SearchViewHolder searchViewHolder = new SearchArrayAdapter.SearchViewHolder(symbol,name);
            convertView.setTag(searchViewHolder);

        }
        Stock s = stocklist.get(position);

        SearchArrayAdapter.SearchViewHolder searchViewHolder = (SearchArrayAdapter.SearchViewHolder) convertView.getTag();

        searchViewHolder.symbol.setText(s.symbol);
        searchViewHolder.name.setText(s.name);

        return convertView;
    }



}






