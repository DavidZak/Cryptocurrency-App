package com.example.mradmin.cryptocurrencyapp.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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

    public double getVolume_usd_24() {
        return volume_usd_24;
    }

    public void setVolume_usd_24(double volume_usd_24) {
        this.volume_usd_24 = volume_usd_24;
    }

    public double getMarket_cap_usd() {
        return market_cap_usd;
    }

    public void setMarket_cap_usd(double market_cap_usd) {
        this.market_cap_usd = market_cap_usd;
    }

    public double getAvailable_supply() {
        return available_supply;
    }

    public void setAvailable_supply(double available_supply) {
        this.available_supply = available_supply;
    }

    public double getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(double total_supply) {
        this.total_supply = total_supply;
    }

    public double getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(double max_supply) {
        this.max_supply = max_supply;
    }

    @SerializedName("24h_volume_usd")
    private double volume_usd_24;
    private double market_cap_usd;
    private double available_supply;
    private double total_supply;
    private double max_supply;
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

    public CryptoEntity(String id, String name, String symbol, long rank, double price_usd, double price_btc, double volume_usd_24, double market_cap_usd, double available_supply, double total_supply, double max_supply, double percent_change_1h, double percent_change_24h, double percent_change_7d, long last_updated) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
        this.price_usd = price_usd;
        this.price_btc = price_btc;
        this.volume_usd_24 = volume_usd_24;
        this.market_cap_usd = market_cap_usd;
        this.available_supply = available_supply;
        this.total_supply = total_supply;
        this.max_supply = max_supply;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.last_updated = last_updated;
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
