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
import java.util.Locale;
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

        String id = readStringElement(jsonObject, KEY_ID);
        String name = readStringElement(jsonObject, KEY_NAME);
        String symbol = readStringElement(jsonObject, KEY_SYMBOL);
        Long rank = readLongElement(jsonObject, KEY_RANK);
        Double priceUSD = readDoubleElement(jsonObject, KEY_PRICE_USD);
        Double priceBTC = readDoubleElement(jsonObject, KEY_PRICE_BTC);
        Double percentChange1H = readDoubleElement(jsonObject, KEY_PERCENT_1H);
        Double percentChange24H = readDoubleElement(jsonObject, KEY_PERCENT_24H);
        Double percentChange7D = readDoubleElement(jsonObject, KEY_PERCENT_7D);
        Long lastUpdated = readLongElement(jsonObject, KEY_LAST_UPDATED);

        // Read the dynamic parameters object.
        final Double priceConverted = readPriceConverted(jsonObject);

        CryptoEntity result = new CryptoEntity();

        if (id != null)
            result.setId(id);

        if (name != null)
            result.setName(name);

        if (symbol != null)
            result.setSymbol(symbol);

        if (rank != null)
            result.setRank(rank);

        if (priceUSD != null)
            result.setPriceUSD(priceUSD);

        if (priceBTC != null)
            result.setPriceBTC(priceBTC);

        if (percentChange1H != null)
            result.setPercentChange1H(percentChange1H);

        if (percentChange24H != null)
            result.setPercentChange24H(percentChange24H);

        if (percentChange7D != null)
            result.setPercentChange7D(percentChange7D);

        if (lastUpdated != null)
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
            if (priceElement != null && !priceElement.isJsonNull()) {

                final double priceConverted = priceElement.getAsDouble();
                return priceConverted;
            }
        }
        return null;
    }

    @Nullable
    private Double readDoubleElement(@NonNull final JsonObject jsonObject, String name) {
        final JsonElement element = jsonObject.get(name);
        if (element != null && !element.isJsonNull()) {

            final double value = element.getAsDouble();
            return value;
        }
        return null;
    }

    @Nullable
    private String readStringElement(@NonNull final JsonObject jsonObject, String name) {
        final JsonElement element = jsonObject.get(name);
        if (element != null && !element.isJsonNull()) {

            final String value = element.getAsString();
            return value;
        }
        return null;
    }

    @Nullable
    private Long readLongElement(@NonNull final JsonObject jsonObject, String name) {
        final JsonElement element = jsonObject.get(name);
        if (element != null && !element.isJsonNull()) {

            final long value = element.getAsLong();
            return value;
        }
        return null;
    }

}
