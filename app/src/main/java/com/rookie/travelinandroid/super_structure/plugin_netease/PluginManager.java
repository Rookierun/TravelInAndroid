package com.rookie.travelinandroid.super_structure.plugin_netease;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static PluginManager instance;
    private final Context context;
    private DexClassLoader dexClassLoader;
    private Resources resources;

    private PluginManager(Context context) {
        this.context = context;
    }

    public static PluginManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(context);
                }
            }
        }
        return instance;
    }

    public String getPluginDir() {
        File file = getPluginFile();
        return file.getAbsolutePath();
    }

    private File getPluginFile() {
        return new File(context.getCacheDir() + File.separator + "plugin.apk");
    }

    public void loadPlugin() {
        if (!getPluginFile().exists()) {
            return;
        }
        //1。加载Class
        String optimizedDir = context.getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath();
        dexClassLoader = new DexClassLoader(
                getPluginDir(),
                optimizedDir,
                null,
                context.getClassLoader()
        );
        //2.加载资源文件
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, getPluginDir());
            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
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

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }
}
