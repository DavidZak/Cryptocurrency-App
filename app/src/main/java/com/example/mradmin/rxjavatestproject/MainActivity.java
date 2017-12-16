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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMain;
    private LinearLayoutManager linearLayoutManagerMain;
    private List<CryptoEntity> cryptoEntityList = new ArrayList<>();

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

        //Setting recycler view----------
        recyclerViewMain = findViewById(R.id.recyclerViewMain);
        MainAdapter mainAdapter = new MainAdapter(cryptoEntityList);
        recyclerViewMain.setAdapter(mainAdapter);
        linearLayoutManagerMain = new LinearLayoutManager(this);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(linearLayoutManagerMain);

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

    private void getCryptoInfo() {

        MainApplication.getCryptoAPI().getCryptoInfo().enqueue(new Callback<List<CryptoEntity>>() {
            @Override
            public void onResponse(Call<List<CryptoEntity>> call, Response<List<CryptoEntity>> response) {

                if (response.isSuccessful()) {

                    List<CryptoEntity> cryptoEntities = response.body();

                    cryptoEntityList = cryptoEntities;

                    MainAdapter mainAdapter = new MainAdapter(cryptoEntityList);

                    recyclerViewMain.setAdapter(mainAdapter);
                    mainAdapter.notifyDataSetChanged();

                    recyclerViewOffset = HideShowScrollListener.recyclerViewOffset;
                    recyclerViewMain.getLayoutManager().onRestoreInstanceState(recyclerViewOffset);

                }

            }

            @Override
            public void onFailure(Call<List<CryptoEntity>> call, Throwable t) {

            }
        });

    }
}
