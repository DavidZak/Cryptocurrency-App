package com.example.mradmin.cryptocurrencyapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mradmin.cryptocurrencyapp.custom_ui.HideShowScrollListener;
import com.example.mradmin.cryptocurrencyapp.custom_ui.SortTypesLayout;
import com.example.mradmin.cryptocurrencyapp.util.Constants;
import com.example.mradmin.cryptocurrencyapp.util.CryptoEntityComparator;
import com.example.mradmin.cryptocurrencyapp.util.SortTypesEnum;
import com.example.mradmin.cryptocurrencyapp.view.adapter.MainAdapter;
import com.example.mradmin.cryptocurrencyapp.MainApplication;
import com.example.mradmin.cryptocurrencyapp.R;
import com.example.mradmin.cryptocurrencyapp.model.CryptoEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerViewMain) RecyclerView recyclerViewMain;
    @BindView(R.id.imageButtonUpdateInfo) ImageButton imageButtonUpdateInfo;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.mainProgressBar) ProgressBar progressBarMain;
    @BindView(R.id.imageButtonSort) ImageButton imageButtonSort;
    @BindView(R.id.sortTypesCustomLayout) SortTypesLayout sortTypesLayout;

    @BindView(R.id.textViewSortTypeName) TextView textViewSortTypeName;
    @BindView(R.id.textViewSortTypePrice) TextView textViewSortTypePrice;
    @BindView(R.id.textViewSortTypePercent) TextView textViewSortTypePercent;
    @BindView(R.id.textViewCloseSortTypes) TextView textViewCloseSortTypes;
    @BindView(R.id.textViewSelectConvertCurrency) TextView textViewConvertCurrency;


    private LinearLayoutManager linearLayoutManagerMain;
    private Parcelable recyclerViewOffset;


    private SortTypesEnum sortTypesEnum;
    private String convertValue = "USD";

    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //For toolbar------
        setSupportActionBar(toolbar);
        //-----------

        fab.setOnClickListener(view -> recyclerViewMain.smoothScrollToPosition(0));

        recyclerViewOffset = HideShowScrollListener.recyclerViewOffset;

        recyclerViewMain.addOnScrollListener(
                new HideShowScrollListener() {

                    @Override
                    public void onHide() {
                        fab.hide();
                    }

                    @Override
                    public void onShow() {
                        fab.show();
                    }
                });
        //-------------

        sortTypesEnum = SortTypesEnum.DEFAULT;

        //image button update-----------
        imageButtonUpdateInfo.setOnClickListener(view -> getCryptoInfo(sortTypesEnum, convertValue));
        //---------------------

        //for convert currency textView-------------
        textViewConvertCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConvertCurrenciesDialog();
            }
        });
        //-----------

        sortTypesLayout.setVisibility(View.GONE);
        imageButtonSort.setOnClickListener(view -> {
            if (sortTypesLayout.getVisibility() == View.VISIBLE) {
                sortTypesLayout.setVisibility(View.GONE);
            } else {
                sortTypesLayout.setVisibility(View.VISIBLE);
            }
        });

        //for sort textViews
        textViewSortTypeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortTypesEnum = SortTypesEnum.NAME;
                getCryptoInfo(sortTypesEnum, convertValue);
                sortTypesLayout.setVisibility(View.GONE);
            }
        });
        textViewSortTypePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortTypesEnum = SortTypesEnum.PRICE;
                getCryptoInfo(sortTypesEnum, convertValue);
                sortTypesLayout.setVisibility(View.GONE);
            }
        });
        textViewSortTypePercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortTypesEnum = SortTypesEnum.PERCENT;
                getCryptoInfo(sortTypesEnum, convertValue);
                sortTypesLayout.setVisibility(View.GONE);
            }
        });

        textViewCloseSortTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortTypesLayout.setVisibility(View.GONE);
            }
        });

        getCryptoInfo(sortTypesEnum, convertValue);
    }

    private void showConvertCurrenciesDialog(){

        AlertDialog.Builder myDialog =
                new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
        myDialog.setTitle("Select Currency For Converting");
        myDialog.setItems(Constants.CONVERT_CURRENCIES, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = Constants.CONVERT_CURRENCIES[which];
                Toast.makeText(MainActivity.this,
                        item, Toast.LENGTH_LONG).show();
                convertValue = item;

                MainAdapter.convertedCurrencyName = item;

                textViewConvertCurrency.setText(item);

                getCryptoInfo(sortTypesEnum, convertValue);

            }});

        myDialog.show();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onPause() {

        super.onPause();

        sortTypesLayout.setVisibility(View.GONE);
    }

    private void arrayListInit(List<CryptoEntity> result, SortTypesEnum sortTypesEnum) {

        MainAdapter.RecyclerViewClickListener clickListener = (view, position) -> {
            Toast.makeText(this, "Position " + position, Toast.LENGTH_SHORT).show();

            final CryptoEntity cryptoEntity = result.get(position);
            String id = cryptoEntity.getId();
            if (!id.isEmpty() && id != null) {
                Intent detailIntent = new Intent(this, CryptoDetailActivity.class);
                detailIntent.putExtra("crypto_id", id);
                startActivity(detailIntent);
            }
        };

        if (result.size() > 0){
            switch (sortTypesEnum) {
                case DEFAULT: break;
                case NAME:Collections.sort(result, new CryptoEntityComparator.CryptoEntityComparatorName()); break;
                case PRICE:Collections.sort(result, new CryptoEntityComparator.CryptoEntityComparatorPrice()); break;
                case PERCENT:Collections.sort(result, new CryptoEntityComparator.CryptoEntityComparatorDayChange()); break;
            }
        }

        mainAdapter = new MainAdapter(result, clickListener);
        mainAdapter.setConvertCurrencyName(convertValue);
        recyclerViewMain.setAdapter(mainAdapter);
        linearLayoutManagerMain = new LinearLayoutManager(this);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setItemViewCacheSize(20);
        recyclerViewMain.setDrawingCacheEnabled(true);
        recyclerViewMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewMain.setLayoutManager(linearLayoutManagerMain);

        recyclerViewOffset = HideShowScrollListener.recyclerViewOffset;
        recyclerViewMain.getLayoutManager().onRestoreInstanceState(recyclerViewOffset);
    }

    private void getCryptoInfo(SortTypesEnum sortTypesEnum, String convertValue) {

        progressBarMain.setVisibility(View.VISIBLE);

        MainApplication.getCryptoAPI().getCryptoInfo(convertValue)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            arrayListInit(result, sortTypesEnum);

                            progressBarMain.setVisibility(View.GONE);
                        }
                        , Throwable::printStackTrace);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_widget_currency) {

            startActivity(new Intent(this, WidgetSettingsActivity.class));

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
