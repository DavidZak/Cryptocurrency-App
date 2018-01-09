package com.example.mradmin.cryptocurrencyapp.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mradmin.cryptocurrencyapp.custom_ui.HideShowScrollListener;
import com.example.mradmin.cryptocurrencyapp.view.adapter.MainAdapter;
import com.example.mradmin.cryptocurrencyapp.MainApplication;
import com.example.mradmin.rxjavatestproject.R;
import com.example.mradmin.cryptocurrencyapp.model.CryptoEntity;

import java.util.List;

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

    private LinearLayoutManager linearLayoutManagerMain;
    private Parcelable recyclerViewOffset;

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


        //image button update-----------
        imageButtonUpdateInfo.setOnClickListener(view -> getCryptoInfo());
        //---------------------
    }

    @Override
    protected void onStart() {

        super.onStart();

        getCryptoInfo();

    }

    private void arrayListInit(List<CryptoEntity> result) {

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

        MainAdapter mainAdapter = new MainAdapter(result, clickListener);
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

    private void getCryptoInfo() {

        progressBarMain.setVisibility(View.VISIBLE);

        MainApplication.getCryptoAPI().getCryptoInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            arrayListInit(result);

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
