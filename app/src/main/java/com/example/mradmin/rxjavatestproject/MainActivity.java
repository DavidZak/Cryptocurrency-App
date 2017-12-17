package com.example.mradmin.rxjavatestproject;

import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMain;
    private LinearLayoutManager linearLayoutManagerMain;

    private ImageButton imageButtonUpdateInfo;

    private FloatingActionButton fab;
    private Parcelable recyclerViewOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //For toolbar------
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        //-----------

        recyclerViewMain = findViewById(R.id.recyclerViewMain);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewMain.smoothScrollToPosition(0);
            }
        });

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
        imageButtonUpdateInfo = findViewById(R.id.imageButtonUpdateInfo);
        imageButtonUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCryptoInfo();

            }
        });
        //---------------------

        getCryptoInfo();

    }

    private void arrayListInit(List<CryptoEntity> result){
        MainAdapter mainAdapter = new MainAdapter(result);
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
        MainApplication.getCryptoAPI().getCryptoInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> arrayListInit(result)
                        , Throwable::printStackTrace);
    }
}
