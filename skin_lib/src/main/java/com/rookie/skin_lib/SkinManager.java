package com.rookie.skin_lib;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SkinManager {
    private static SkinManager instance;
    private Application application;
    private Resources appResources;
    private Resources skinResources;
    private String skinPackageName;
    private boolean isDefaultSkin;
    private static final String ADD_ASSET_PATH = "addAssetPath";
    private Map<String, SkinCache> skinCacheMap;

    public static SkinManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("请在application中调用init方法后在使用");
        }
        return instance;
    }

    private SkinManager(Application application) {
        this.application = application;
        appResources = application.getResources();
        skinCacheMap = new HashMap<>();
    }

    public static void init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }

    }

    /**
     * 将皮肤包中的资源加载到app中
     *
     * @param skinPath
     */
    public void loadSkinResource(String skinPath) {
        if (TextUtils.isEmpty(skinPath)) {
            return;
        }
        try {
            if (skinCacheMap != null && skinCacheMap.containsKey(skinPath)) {
                SkinCache skinCache = skinCacheMap.get(skinPath);
                if (skinCache != null) {
                    skinResources = skinCache.getSkinResource();
                    skinPackageName = skinCache.getPackageName();
                    return;
                }
            }
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod(ADD_ASSET_PATH, String.class);
            addAssetPath.setAccessible(true);
            addAssetPath.invoke(assetManager, skinPath);
            skinResources = new Resources(assetManager, appResources.getDisplayMetrics(), appResources.getConfiguration());
            PackageInfo packageArchiveInfo = application.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            if (packageArchiveInfo != null) {
                skinPackageName = packageArchiveInfo.packageName;
            }
            isDefaultSkin = TextUtils.isEmpty(skinPackageName);
            if (!isDefaultSkin && skinCacheMap != null && !skinCacheMap.containsKey(skinPath)) {
                skinCacheMap.put(skinPath, new SkinCache(skinResources, skinPackageName));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private int getSkinResourceIds(int resourceId) {
        if (isDefaultSkin) return resourceId;
        if (skinResources == null) return resourceId;
        String resourceEntryName = appResources.getResourceEntryName(resourceId);
        String resourceTypeName = appResources.getResourceTypeName(resourceId);
        int skinResourceId = skinResources.getIdentifier(resourceEntryName, resourceTypeName, skinPackageName);
        isDefaultSkin = skinResourceId <= 0;
        return isDefaultSkin ? resourceId : skinResourceId;
    }

    public boolean isDefaultSkin() {
        return isDefaultSkin;
    }

    /**
     * 获取color
     *
     * @param resourceId
     * @return
     */
    public int getColor(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getColor(ids) : skinResources.getColor(ids);
    }

    public ColorStateList getColorStateList(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getColorStateList(ids) : skinResources.getColorStateList(ids);
    }

    public Drawable getDrawableOrMipmap(int resourceId) {
        return isDefaultSkin ? appResources.getDrawable(getSkinResourceIds(resourceId)) : skinResources.getDrawable(getSkinResourceIds(resourceId));
    }

    public String getString(int resourceId) {
        return isDefaultSkin ? appResources.getString(getSkinResourceIds(resourceId)) : skinResources.getString(getSkinResourceIds(resourceId));
    }

    public Object getBgOrSrc(int resourceId) {
        String resourceTypeName = appResources.getResourceTypeName(resourceId);
        switch (resourceTypeName) {
            case "color":
                return getColor(resourceId);
            case "mipmap":
            case "drawable":
                return getDrawableOrMipmap(resourceId);
        }
        return null;
    }
}
