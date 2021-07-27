package com.rookie.skin_lib;

import android.content.res.Resources;

public class SkinCache {
    public SkinCache(Resources skinResource, String packageName) {
        this.skinResource = skinResource;
        this.packageName = packageName;
    }

    private Resources skinResource;
    private String packageName;

    public Resources getSkinResource() {
        return skinResource;
    }

    public void setSkinResource(Resources skinResource) {
        this.skinResource = skinResource;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
