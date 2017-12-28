package com.example.mradmin.rxjavatestproject.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mradmin.rxjavatestproject.MainApplication;
import com.example.mradmin.rxjavatestproject.R;
import com.example.mradmin.rxjavatestproject.model.CryptoEntity;
import com.example.mradmin.rxjavatestproject.util.LastSeen;
import com.example.mradmin.rxjavatestproject.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CryptoDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.detailProgressBar) ProgressBar progressBarDetail;
    @BindView(R.id.collapsingToolbarDetail) CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.imageViewCoinIcon)ImageView coinImage;

    @BindView(R.id.textViewCoinCostUSD) TextView coinCostUSD;
    @BindView(R.id.textViewCoinCostBTC) TextView coinCostBTC;
    @BindView(R.id.textViewCoinChange1H) TextView change1H;
    @BindView(R.id.textViewCoinChange24H) TextView change24H;
    @BindView(R.id.textViewCoinChange7D) TextView change7D;
    @BindView(R.id.textViewLastUpdated) TextView lastUpdatedTextView;

    private String cryptoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        cryptoId = getIntent().getExtras().getString("crypto_id");
        if (!cryptoId.isEmpty() && cryptoId != null) {
            getCryptoDetail();
        }
    }

    private void initData (List<CryptoEntity> cryptoEntities) {

        if (cryptoEntities.size() > 1 || cryptoEntities.size() < 1) {
            return;
        }

        CryptoEntity cryptoEntity = cryptoEntities.get(0);
        collapsingToolbarLayout.setTitle(cryptoEntity.getName() + " | " + cryptoEntity.getSymbol());

        String imgPath = "file:///android_asset/icons/" + cryptoEntity.getSymbol().toLowerCase() + ".png";
        Picasso.with(this)
                .load(imgPath)
                .error(R.drawable.ic_no_icon)
                .resize(128, 128)
                .into(coinImage);

        coinCostUSD.setText(String.valueOf(cryptoEntity.getPriceUSD()) + " $");
        coinCostBTC.setText(String.valueOf(cryptoEntity.getPriceBTC()) + " BTC");

        change1H.setText(String.valueOf(cryptoEntity.getPercentChange1H()) + " %");
        change24H.setText(String.valueOf(cryptoEntity.getPercentChange24H()) + " %");
        change7D.setText(String.valueOf(cryptoEntity.getPercentChange7D()) + " %");

        Util.setPercentChangeColor(change1H, cryptoEntity.getPercentChange1H());
        Util.setPercentChangeColor(change24H, cryptoEntity.getPercentChange24H());
        Util.setPercentChangeColor(change7D, cryptoEntity.getPercentChange7D());

        long lastUpdated = cryptoEntity.getLastUpdated();

        LastSeen timeSinceAgo = new LastSeen();
        String lastSeen = timeSinceAgo.getFullStringDate(lastUpdated);

        lastUpdatedTextView.setText(lastSeen);
    }

    private void getCryptoDetail () {

        progressBarDetail.setVisibility(View.VISIBLE);

        MainApplication.getCryptoAPI().getCryptoDetail(cryptoId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            initData(result);

                            progressBarDetail.setVisibility(View.GONE);
                        }
                        , Throwable::printStackTrace);
    }
}
