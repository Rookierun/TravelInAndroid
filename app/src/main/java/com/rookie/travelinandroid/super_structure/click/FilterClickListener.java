package com.rookie.travelinandroid.super_structure.click;

import android.view.View;

class FilterClickListener implements View.OnClickListener {
    boolean clickEnabled = true;
    private final View.OnClickListener listener;

    public FilterClickListener(View.OnClickListener clickListener) {
        this.listener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickEnabled) {
            listener.onClick(v);
            clickEnabled = false;
        }
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickEnabled = true;
            }
        }, 200);
    }
}
