package com.example.mradmin.rxjavatestproject.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mradmin.rxjavatestproject.util.LastSeen;
import com.example.mradmin.rxjavatestproject.R;
import com.example.mradmin.rxjavatestproject.model.CryptoEntity;
import com.example.mradmin.rxjavatestproject.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CoinViewHolder> {

    private RecyclerViewClickListener listener;
    private List<CryptoEntity> coinsList;

    public MainAdapter(List<CryptoEntity> coinsList, RecyclerViewClickListener listener) {

        this.listener = listener;
        this.coinsList = coinsList;

    }

    @Override
    public MainAdapter.CoinViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coin_row_layout, parent, false);

        return new CoinViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final CoinViewHolder holder, final int position) {
        final CryptoEntity cryptoEntity = coinsList.get(position);

        holder.coinName.setText(cryptoEntity.getName());
        holder.coinSymbol.setText(cryptoEntity.getSymbol());
        holder.coinCost.setText(String.valueOf(cryptoEntity.getPriceUSD()) + " $");

        double change1H = cryptoEntity.getPercentChange1H();
        double change24H = cryptoEntity.getPercentChange24H();
        double change7D = cryptoEntity.getPercentChange7D();

        holder.change1H.setText(String.valueOf(change1H) + " %");
        holder.change24H.setText(String.valueOf(change24H) + " %");
        holder.change7D.setText(String.valueOf(change7D) + " %");

        Util.setPercentChangeColor(holder.change1H, change1H);
        Util.setPercentChangeColor(holder.change24H, change24H);
        Util.setPercentChangeColor(holder.change7D, change7D);

        Util.setImg(holder.img1H, change1H);
        Util.setImg(holder.img24H, change24H);
        Util.setImg(holder.img7D, change7D);

        long lastUpdated = cryptoEntity.getLastUpdated();

        LastSeen timeSinceAgo = new LastSeen();
        String lastSeen = timeSinceAgo.getFullStringDate(lastUpdated);

        holder.lastUpdated.setText(lastSeen);

        String imgPath = "file:///android_asset/icons/" + cryptoEntity.getSymbol().toLowerCase() + ".png";

        Picasso.with(holder.view.getContext())
                .load(imgPath)
                .error(R.drawable.ic_no_icon)
                .resize(64, 64)
                .into(holder.coinImage);
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener listener;

        public View view;

        public ImageView coinImage;

        public TextView coinName;
        public TextView coinSymbol;
        public TextView coinCost;
        public TextView change1H;
        public TextView change24H;
        public TextView change7D;
        public TextView lastUpdated;

        public ImageView img1H;
        public ImageView img24H;
        public ImageView img7D;

        public CoinViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            view = itemView;

            coinImage = itemView.findViewById(R.id.imageViewCoinIcon);

            coinName = itemView.findViewById(R.id.textViewCoinName);
            coinSymbol = itemView.findViewById(R.id.textViewCoinSymbol);
            coinCost = itemView.findViewById(R.id.textViewCoinCost);
            change1H = itemView.findViewById(R.id.textViewCoinChange1H);
            change24H = itemView.findViewById(R.id.textViewCoinChange24H);
            change7D = itemView.findViewById(R.id.textViewCoinChange7D);
            lastUpdated = itemView.findViewById(R.id.textViewLastUpdated);

            img1H = itemView.findViewById(R.id.img1H);
            img24H = itemView.findViewById(R.id.img24H);
            img7D = itemView.findViewById(R.id.img7D);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
