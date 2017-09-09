package com.example.avma1997.therealstocks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Avma1997 on 7/10/2017.
 */

public class ResultActivity extends AppCompatActivity implements OnDownloadCompleteListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        String a=getIntent().getStringExtra("result");
        String url="https://query.yahooapis.com/v1/public/yql?q=select+%2A+from+yahoo.finance.quotes+where+symbol+in+%28%22"+a+"%22%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback";
        StocksAsyncTask stockAsyncTask = new StocksAsyncTask();
        stockAsyncTask.execute(url);
        stockAsyncTask.setOnDownloadCompleteListener(ResultActivity.this);
    }
    
    public void onDownloadComplete(ArrayList<Stock> stocklist) {
        Stock s=stocklist.get(0);
        TextView sym= (TextView)findViewById(R.id.sym);
        sym.setText(s.symbol);
        TextView names=(TextView)findViewById(R.id.names);
       String name=getIntent().getStringExtra("name");
        if(name==null)
        {name="";}
        names.setText(name);
        TextView ltv= (TextView)findViewById(R.id.lasttradedvalue);
        ltv.setText(s.lasttradedprice);
        TextView change=(TextView)findViewById(R.id.changes);
        change.setText(s.change);
        TextView pchange= (TextView)findViewById(R.id.percentchanges);
        pchange.setText(s.changeinpercent);
        TextView open=(TextView)findViewById(R.id.dayopen);
        open.setText("OPEN  "+s.dayopen);
        TextView close= (TextView)findViewById(R.id.dayclose);
        close.setText("CLOSE  "+s.dayclose);
        TextView otp=(TextView)findViewById(R.id.oneyeartargetprice);
        otp.setText("ONE Y TARGET PRICE  "+s.oneyeartargetprice);
        TextView marketcap= (TextView)findViewById(R.id.marketcap);
        marketcap.setText("MARKET CAP  "+s.marketcap);
        TextView vol=(TextView)findViewById(R.id.volume);
        vol.setText("VOLUME  "+s.volume);
        TextView dividend= (TextView)findViewById(R.id.dividend);
        dividend.setText("DIVIDEND  "+s.dividend);

        
    }
}
