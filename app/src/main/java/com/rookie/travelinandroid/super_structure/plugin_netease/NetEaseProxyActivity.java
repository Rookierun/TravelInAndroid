package com.rookie.travelinandroid.super_structure.plugin_netease;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.rookie.plugin_standard.ActivityInterface;
import com.rookie.travelinandroid.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.Nullable;

public class NetEaseProxyActivity extends Activity {
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(this).getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getResources();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        String className = getIntent().getStringExtra("className");
        try {
            Class<?> aClass = getClassLoader().loadClass(className);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            Object o = constructor.newInstance(new Object[]{});
//            Object o = aClass.newInstance();
            if (o instanceof ActivityInterface) {
                ActivityInterface activityInterface = (ActivityInterface) o;
                activityInterface.insertAppContext(NetEaseProxyActivity.this);
                Bundle bundle = new Bundle();
                bundle.putString("info", "宿主代理Activity传过来的信息");
                activityInterface.onCreate(bundle);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

}
