package com.example.mradmin.rxjavatestproject;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CoinViewHolder> {

    private List<CryptoEntity> coinsList;

    public MainAdapter(List<CryptoEntity> coinsList) {

        this.coinsList = coinsList;

    }

    @Override
    public MainAdapter.CoinViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coin_row_layout, parent, false);

        return new CoinViewHolder(v);
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

        setPercentChangeColor(holder.change1H, change1H);
        setPercentChangeColor(holder.change24H, change24H);
        setPercentChangeColor(holder.change7D, change7D);

        long lastUpdated = cryptoEntity.getLastUpdated();

        LastSeen timeSinceAgo = new LastSeen();
        String lastSeen = timeSinceAgo.getTimeAgo(lastUpdated);

        holder.lastUpdated.setText(lastSeen);
    }

    private void setPercentChangeColor(TextView textView, double value) {
        if (value > 0) {
            textView.setTextColor(Color.parseColor("#08b97c"));
        } else if (value < 0) {
            textView.setTextColor(Color.parseColor("#ff6060"));
        } else {
            textView.setTextColor(Color.parseColor("#8c8c8c"));
        }
    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder {

        public View view;

        public CircleImageView coinImage;

        public TextView coinName;
        public TextView coinSymbol;
        public TextView coinCost;
        public TextView change1H;
        public TextView change24H;
        public TextView change7D;
        public TextView lastUpdated;

        public CoinViewHolder(View itemView) {
            super(itemView);

            view = itemView;

            coinImage = itemView.findViewById(R.id.imageViewCoinIcon);

            coinName = itemView.findViewById(R.id.textViewCoinName);
            coinSymbol = itemView.findViewById(R.id.textViewCoinSymbol);
            coinCost = itemView.findViewById(R.id.textViewCoinCost);
            change1H = itemView.findViewById(R.id.textViewCoinChange1H);
            change24H = itemView.findViewById(R.id.textViewCoinChange24H);
            change7D = itemView.findViewById(R.id.textViewCoinChange7D);
            lastUpdated = itemView.findViewById(R.id.textViewLastUpdated);

        }

    }
}
