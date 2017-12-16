package com.example.mradmin.rxjavatestproject;

/**
 * Created by mrAdmin on 15.12.2017.
 */

public class CryptoEntity {

    private String id;
    private String name;
    private String symbol;
    private long rank;
    private double price_usd;
    private double price_btc;
    private double percent_change_1h;
    private double percent_change_24h;
    private double percent_change_7d;
    private long last_updated;

    public CryptoEntity(String id, String name, String symbol, long rank, double priceUSD, double priceBTC, double percentChange1H, double percentChange24H, double percentChange7d, long lastUpdated) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
        this.price_usd = priceUSD;
        this.price_btc = priceBTC;
        this.percent_change_1h = percentChange1H;
        this.percent_change_24h = percentChange24H;
        this.percent_change_7d = percentChange7d;
        this.last_updated = lastUpdated;
    }

    public CryptoEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public double getPriceUSD() {
        return price_usd;
    }

    public void setPriceUSD(double priceUSD) {
        this.price_usd = priceUSD;
    }

    public double getPriceBTC() {
        return price_btc;
    }

    public void setPriceBTC(double priceBTC) {
        this.price_btc = priceBTC;
    }

    public double getPercentChange1H() {
        return percent_change_1h;
    }

    public void setPercentChange1H(double percentChange1H) {
        this.percent_change_1h = percentChange1H;
    }

    public double getPercentChange24H() {
        return percent_change_24h;
    }

    public void setPercentChange24H(double percentChange24H) {
        this.percent_change_24h = percentChange24H;
    }

    public double getPercentChange7D() {
        return percent_change_7d;
    }

    public void setPercentChange7D(double percentChange7d) {
        this.percent_change_7d = percentChange7d;
    }

    public long getLastUpdated() {
        return last_updated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.last_updated = lastUpdated;
    }
}
