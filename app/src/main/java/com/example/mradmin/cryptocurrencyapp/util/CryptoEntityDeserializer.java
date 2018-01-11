package com.example.mradmin.cryptocurrencyapp.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mradmin.cryptocurrencyapp.model.CryptoEntity;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yks-11 on 1/11/18.
 */

public class CryptoEntityDeserializer implements JsonDeserializer<CryptoEntity> {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SYMBOL = "symbol";
    private static final String KEY_RANK = "rank";
    private static final String KEY_PRICE_USD = "price_usd";
    private static final String KEY_PRICE_BTC = "price_btc";
    private static final String KEY_PERCENT_1H = "percent_change_1h";
    private static final String KEY_PERCENT_24H = "percent_change_24h";
    private static final String KEY_PERCENT_7D = "percent_change_7d";
    private static final String KEY_LAST_UPDATED = "last_updated";

    @Override
    public CryptoEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        // Read simple String values.
        final String id = jsonObject.get(KEY_ID).getAsString();
        final String name = jsonObject.get(KEY_NAME).getAsString();
        final String symbol = jsonObject.get(KEY_SYMBOL).getAsString();
        final long rank = jsonObject.get(KEY_RANK).getAsLong();
        final double priceUSD = jsonObject.get(KEY_PRICE_USD).getAsDouble();
        final double priceBTC = jsonObject.get(KEY_PRICE_BTC).getAsDouble();
        final double percentChange1H = jsonObject.get(KEY_PERCENT_1H).getAsDouble();
        final double percentChange24H = jsonObject.get(KEY_PERCENT_24H).getAsDouble();
        final double percentChange7D = jsonObject.get(KEY_PERCENT_7D).getAsDouble();
        final long lastUpdated = jsonObject.get(KEY_LAST_UPDATED).getAsLong();

        // Read the dynamic parameters object.
        final Double priceConverted = readPriceConverted(jsonObject);

        CryptoEntity result = new CryptoEntity();
        result.setId(id);
        result.setName(name);
        result.setSymbol(symbol);
        result.setRank(rank);
        result.setPriceUSD(priceUSD);
        result.setPriceBTC(priceBTC);
        result.setPercentChange1H(percentChange1H);
        result.setPercentChange24H(percentChange24H);
        result.setPercentChange7D(percentChange7D);
        result.setLastUpdated(lastUpdated);

        if (priceConverted != null)
            result.setPrice_converted(priceConverted);

        return result;
    }

    @Nullable
    private Double readPriceConverted(@NonNull final JsonObject jsonObject) {
        for (String currency: Constants.CONVERT_CURRENCIES) {
            String price = "price_" + currency.toLowerCase();

            final JsonElement priceElement = jsonObject.get(price);
            if (priceElement != null) {

                final double priceConverted = priceElement.getAsDouble();
                return priceConverted;
            }
        }
        return null;
    }

}
