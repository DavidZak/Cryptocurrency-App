package com.example.mradmin.rxjavatestproject.util;

import android.graphics.Color;
import android.widget.TextView;

/**
 * Created by yks-11 on 12/28/17.
 */

public class Util {

    public static void setPercentChangeColor(TextView textView, double value) {
        if (value > 0) {
            textView.setTextColor(Color.parseColor("#08b97c"));
        } else if (value < 0) {
            textView.setTextColor(Color.parseColor("#ff6060"));
        } else {
            textView.setTextColor(Color.parseColor("#8c8c8c"));
        }
    }

}
