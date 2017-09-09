package com.example.avma1997.therealstocks;

/**
 * Created by Avma1997 on 7/9/2017.
 */

public class Stock {
    String symbol;
    String name;
    String lasttradedprice;
    String change;
    String changeinpercent;
    String dayopen;
    String dayclose;
    String oneyeartargetprice;
    String volume;
    String dividend;
    String marketcap;

    public Stock(String symbol, String name, String lasttradedprice, String change, String changeinpercent, String dayopen, String dayclose, String oneyeartargetprice, String volume, String dividend, String marketcap) {
        this.symbol = symbol;
        this.name = name;
        this.lasttradedprice = lasttradedprice;
        this.change = change;
        this.changeinpercent = changeinpercent;
        this.dayopen = dayopen;
        this.dayclose = dayclose;
        this.oneyeartargetprice = oneyeartargetprice;
        this.volume = volume;
        this.dividend = dividend;
        this.marketcap = marketcap;
    }
}


