package com.example.mradmin.cryptocurrencyapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mradmin.cryptocurrencyapp.MainApplication;
import com.example.mradmin.cryptocurrencyapp.model.CryptoEntity;
import com.example.mradmin.cryptocurrencyapp.view.adapter.WidgetSettingsAdapter;
import com.example.mradmin.cryptocurrencyapp.widget.SimpleWidget;
import com.example.mradmin.cryptocurrencyapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WidgetSettingsActivity extends AppCompatActivity {


    @BindView(R.id.widget_settings_toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerViewCurrencies)
    RecyclerView recyclerViewCurrencies;
    @BindView(R.id.imageButtonSave)
    ImageButton imageButtonSave;

    private LinearLayoutManager linearLayoutManagerCurrencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_settings);

        ButterKnife.bind(this);

        //For toolbar------
        setSupportActionBar(toolbar);
        //-----------

    }

    @Override
    protected void onStart() {

        super.onStart();

        getCryptoInfo();

    }

    private void arrayListInit(List<CryptoEntity> result) {

        WidgetSettingsAdapter.RecyclerViewClickListener clickListener = (view, position) -> {
            Toast.makeText(this, "Position " + position, Toast.LENGTH_SHORT).show();

            final CryptoEntity cryptoEntity = result.get(position);
            String id = cryptoEntity.getId();
            if (!id.isEmpty() && id != null) {
                Intent intent = new Intent(this, SimpleWidget.class);
                intent.putExtra("crypto_id", id);

                Map<String, String> keyPairs = new HashMap<>();
                keyPairs.put("crypto_id", id);
                MainApplication.setSharedPreferences(MainApplication.getDataSharedPreferences(), keyPairs);

                //startActivity(intent);
                sendBroadcast(intent);
            }
        };

        WidgetSettingsAdapter mainAdapter = new WidgetSettingsAdapter(result, clickListener);
        recyclerViewCurrencies.setAdapter(mainAdapter);
        linearLayoutManagerCurrencies = new LinearLayoutManager(this);
        recyclerViewCurrencies.setHasFixedSize(true);
        recyclerViewCurrencies.setItemViewCacheSize(20);
        recyclerViewCurrencies.setDrawingCacheEnabled(true);
        recyclerViewCurrencies.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewCurrencies.setLayoutManager(linearLayoutManagerCurrencies);
    }

    private void getCryptoInfo() {

        MainApplication.getCryptoAPI().getCryptoInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            arrayListInit(result);
                        }
                        , Throwable::printStackTrace);
    }
}
