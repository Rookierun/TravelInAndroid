package com.rookie.skin_lib.model;

import android.content.res.TypedArray;
import android.util.SparseIntArray;

public class AttrsBean {
    private SparseIntArray resourceMap;
    private static final int DEFAULT_VALUE = -1;

    public AttrsBean() {
        resourceMap = new SparseIntArray();
    }

    public void saveViewResource(TypedArray typedArray, int[] styleable) {
        if (typedArray == null) {
            return;
        }
        if (resourceMap == null) {
            resourceMap = new SparseIntArray();
        }
        for (int i = 0; i < typedArray.length(); i++) {
            int key = styleable[i];
            int resourceId = typedArray.getResourceId(i, DEFAULT_VALUE);
            resourceMap.put(key, resourceId);
        }
    }

    public int getViewResource(int styleable) {
        return resourceMap != null ?
                resourceMap.get(styleable) : -1;
    }
}
