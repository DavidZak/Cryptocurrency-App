package com.example.mradmin.cryptocurrencyapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mradmin.cryptocurrencyapp.util.LastSeen;
import com.example.mradmin.cryptocurrencyapp.MainApplication;
import com.example.mradmin.cryptocurrencyapp.R;
import com.example.mradmin.cryptocurrencyapp.model.CryptoEntity;
import com.example.mradmin.cryptocurrencyapp.util.Util;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @BindView(R.id.fab) FloatingActionButton fabUpdate;

    @BindView(R.id.lineChart) LineChart lineChart;

    private String cryptoId;

    private List<Entry> chartEntries = new ArrayList<>();

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_detail);
        ButterKnife.bind(this);

        getCryptoInfo();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        collapsingToolbarLayout.setTitle("...");

        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCryptoInfo();
            }
        });

    }

    private void getCryptoInfo() {
        cryptoId = getIntent().getExtras().getString("crypto_id");

        if (cryptoId == null) {
            cryptoId = MainApplication.getDataSharedPreferences().getString("crypto_id", "");
        }

        if (!cryptoId.isEmpty() && cryptoId != null) {
            getCryptoDetail(cryptoId);
        }
    }

    private void initChart(double value) {

        //chartEntries.add(new Entry(chartEntries.size(), (float) value));
        for (int i=0;i< 100;i++) {
            int n = random.nextInt(1000);
            chartEntries.add(new Entry(i, n));
        }

        LineDataSet dataSet = new LineDataSet(chartEntries, "Currency value in USD"); // add entries to dataset

        dataSet.setHighlightEnabled(true); // allow highlighting for DataSet

        // set this to false to disable the drawing of highlight indicator (lines)
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setHighLightColor(getResources().getColor(R.color.colorAccent));
        // and more...
        dataSet.setCubicIntensity(0.5f);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(getResources().getDrawable(R.drawable.line_chart_background));
        dataSet.setValueTextColor(Color.WHITE);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis rightAxis = lineChart.getAxisRight();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        leftAxis.setTextSize(12f);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        rightAxis.setTextSize(12f);
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setDrawAxisLine(true);
        rightAxis.setDrawGridLines(false);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        LineData lineData = new LineData(dataSet);
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.setGridBackgroundColor(getResources().getColor(R.color.grey));
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
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
                .resize(64, 64)
                .into(coinImage);

        coinCostUSD.setText(String.valueOf(cryptoEntity.getPriceUSD()));
        coinCostBTC.setText(String.valueOf(cryptoEntity.getPriceBTC()));

        change1H.setText(String.valueOf(cryptoEntity.getPercentChange1H()) + "%");
        change24H.setText(String.valueOf(cryptoEntity.getPercentChange24H()) + "%");
        change7D.setText(String.valueOf(cryptoEntity.getPercentChange7D()) + "%");

        Util.setPercentChangeColor(change1H, cryptoEntity.getPercentChange1H());
        Util.setPercentChangeColor(change24H, cryptoEntity.getPercentChange24H());
        Util.setPercentChangeColor(change7D, cryptoEntity.getPercentChange7D());

        long lastUpdated = cryptoEntity.getLastUpdated();

        LastSeen timeSinceAgo = new LastSeen();
        String lastSeen = timeSinceAgo.getFullStringDate(lastUpdated);

        lastUpdatedTextView.setText(lastSeen);

        //for chart --- -----
        initChart(cryptoEntity.getPriceUSD());
    }

    private void getCryptoDetail (String cryptoId) {

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
