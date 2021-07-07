package com.rookie.travelinandroid.super_ui.animator;

import android.animation.TypeEvaluator;

public class ProvinceTypeEvaluator implements TypeEvaluator<String> {
    @Override
    public String evaluate(float fraction, String startValue, String endValue) {
        ProvinceUtil.init();
        int startIndex = ProvinceUtil.cityList.indexOf(startValue);
        int endIndex = ProvinceUtil.cityList.indexOf(endValue);
        int index = (int) ((endIndex - startIndex) * fraction + startIndex);
        String s = ProvinceUtil.cityList.get(index);
        return s;
    }
}
