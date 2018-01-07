package com.example.mradmin.rxjavatestproject.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mradmin.rxjavatestproject.R;
import com.example.mradmin.rxjavatestproject.model.CryptoEntity;
import com.example.mradmin.rxjavatestproject.util.LastSeen;
import com.example.mradmin.rxjavatestproject.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mrAdmin on 07.01.2018.
 */

public class WidgetSettingsAdapter extends RecyclerView.Adapter<WidgetSettingsAdapter.CurrencyViewHolder> {

    private RecyclerViewClickListener listener;
    private List<CryptoEntity> coinsList;

    public WidgetSettingsAdapter(List<CryptoEntity> coinsList, RecyclerViewClickListener listener) {

        this.listener = listener;
        this.coinsList = coinsList;

    }

    @Override
    public WidgetSettingsAdapter.CurrencyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coin_row_layout, parent, false);

        return new CurrencyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final CurrencyViewHolder holder, final int position) {

        final CryptoEntity cryptoEntity = coinsList.get(position);

        holder.coinName.setText(cryptoEntity.getName());

    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener listener;

        public View view;

        public TextView coinName;

        public CurrencyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            view = itemView;

            coinName = itemView.findViewById(R.id.textViewCoinName);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
