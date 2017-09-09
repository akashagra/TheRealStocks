package com.example.avma1997.therealstocks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Avma1997 on 7/9/2017.
 */

public class StockArrayAdapter extends ArrayAdapter<Stock> {
    ArrayList<Stock> stocklist;
    Context context;


    public StockArrayAdapter(Context context, ArrayList<Stock> stocklist) {
        super(context, 0);
        this.context = context;
        this.stocklist = stocklist ;

    }
    public int getCount() {
        return stocklist.size();
    }

    static class StocksViewHolder {

        TextView symbol;
        TextView name;
        TextView lasttradedprice;
        TextView change;
        TextView percentagechange;        


        StocksViewHolder(TextView symbol, TextView name, TextView lasttradedprice,TextView change,TextView percentagechange) {
            
            this.symbol=symbol;
            this.name=name;
            this.lasttradedprice=lasttradedprice;
            this.change=change;
            this.percentagechange=percentagechange;
            
            
           
        }

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

            TextView symbol = (TextView) convertView.findViewById(R.id.symbol);
            TextView name = (TextView) convertView.findViewById(R.id.title);
            TextView lasttradedprice = (TextView) convertView.findViewById(R.id.tradedprice);
            TextView change = (TextView) convertView.findViewById(R.id.change);
            TextView percentagechange = (TextView) convertView.findViewById(R.id.percentagechange);

            StocksViewHolder stocksViewHolder = new StocksViewHolder(symbol,name,lasttradedprice,change,percentagechange);
            convertView.setTag(stocksViewHolder);

        }
        Stock s = stocklist.get(position);

        StocksViewHolder stocksViewHolder = (StocksViewHolder) convertView.getTag();

        stocksViewHolder.symbol.setText(s.symbol);
        stocksViewHolder.name.setText(s.name);
        stocksViewHolder.lasttradedprice.setText(s.lasttradedprice + "");
        stocksViewHolder.change.setText(s.change+ "");
        stocksViewHolder.percentagechange.setText(s.changeinpercent+"");



        return convertView;
    }



}
