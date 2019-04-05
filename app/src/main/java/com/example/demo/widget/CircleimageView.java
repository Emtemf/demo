package com.example.demo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.example.demo.R;


public class CircleimageView extends de.hdodenhof.circleimageview.CircleImageView {
    public CircleimageView(Context context) {
        super(context);
    }

    public CircleimageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleimageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setBorderColor(getResources().getColor(R.color.colorAccent));
        } else {
            setBorderColor(Color.WHITE);
        }
    }
}