package com.example.mradmin.cryptocurrencyapp.custom_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.mradmin.cryptocurrencyapp.R;

/**
 * Created by yks-11 on 1/10/18.
 */

public class SortTypesLayout extends LinearLayout {

    public SortTypesLayout(Context context) {
        super(context);
    }

    public SortTypesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sort_type_view, this, true);
    }
}
