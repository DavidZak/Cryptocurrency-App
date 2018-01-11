package com.example.mradmin.cryptocurrencyapp.util;

import com.example.mradmin.cryptocurrencyapp.model.CryptoEntity;

import java.util.Comparator;

/**
 * Created by yks-11 on 1/11/18.
 */

public class CryptoEntityComparator {

    public static class CryptoEntityComparatorName implements Comparator<CryptoEntity> {

        @Override
        public int compare(CryptoEntity o1, CryptoEntity o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    public static class CryptoEntityComparatorSupply implements Comparator<CryptoEntity> {

        @Override
        public int compare(CryptoEntity o1, CryptoEntity o2) {
            return o1.getMax_supply() > o2.getMax_supply() ? -1 : o1.getMax_supply() == o2.getMax_supply() ? 0 : 1;
        }
    }

    public static class CryptoEntityComparatorPrice implements Comparator<CryptoEntity> {

        @Override
        public int compare(CryptoEntity o1, CryptoEntity o2) {
            return o1.getPriceUSD() > o2.getPriceUSD() ? -1 : o1.getPriceUSD() == o2.getPriceUSD() ? 0 : 1;
        }
    }

    public static class CryptoEntityComparatorDayChange implements Comparator<CryptoEntity> {

        @Override
        public int compare(CryptoEntity o1, CryptoEntity o2) {
            return o1.getPercentChange24H() > o2.getPercentChange24H() ? -1 : o1.getPercentChange24H() == o2.getPercentChange24H() ? 0 : 1;
        }
    }

}
