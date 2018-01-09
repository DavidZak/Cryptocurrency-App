package com.example.mradmin.cryptocurrencyapp.util;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.mradmin.rxjavatestproject.R;

/**
 * Created by yks-11 on 12/28/17.
 */

public class Util {

    public static void setPercentChangeColor (TextView textView, double value) {
        if (value > 0) {
            textView.setTextColor(Color.parseColor("#08b97c"));
        } else if (value < 0) {
            textView.setTextColor(Color.parseColor("#ff6060"));
        } else {
            textView.setTextColor(Color.parseColor("#8c8c8c"));
        }
    }

    public static void setPercentChangeColorWidgetViews (RemoteViews views, int id, double value) {
        if (value > 0) {
            views.setTextColor(id, Color.parseColor("#08b97c"));
        } else if (value < 0) {
            views.setTextColor(id, Color.parseColor("#ff6060"));
        } else {
            views.setTextColor(id, Color.parseColor("#8c8c8c"));
        }
    }

    public static void setImg (ImageView imageView, double value) {
        if (value > 0) {

            imageView.setVisibility(View.VISIBLE);

            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_up));

        } else if (value < 0) {

            imageView.setVisibility(View.VISIBLE);

            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_down));

        } else {

            imageView.setVisibility(View.GONE);

        }
    }

}
