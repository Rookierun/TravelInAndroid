package com.rookie.travelinandroid.super_structure.hook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rookie.travelinandroid.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookTestActivity extends AppCompatActivity {

    private View.OnClickListener originalClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_test);
        Button btn = (Button) findViewById(R.id.btn);
        originalClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HookTestActivity.this, ((Button) v).getText(), Toast.LENGTH_LONG).show();
            }
        };
        btn.setOnClickListener(originalClickListener);
        try {
//            hook_Version1(btn);
            hook_Version2(btn);
        } catch (Exception e) {
            Log.e("test", "hook view onClick 失败");
        }
    }

    /**
     * 这样写会ANR
     * ANR的原因是死循环了： return method.invoke(proxy, args);
     * 这个版本的思路是：用setOnclickListener方法将onClickListener替换
     *
     * @param button
     * @throws Exception
     */
    private void hook_Version1(Button button) throws Exception {
        //先反射拿到View.setOnClickListener方法
        Class<?> viewClass = Class.forName("android.view.View");
        Method setOnClickListenerMethod = viewClass.getMethod("setOnClickListener", View.OnClickListener.class);
        Object instance = Proxy.newProxyInstance(getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.e("test", "hook到了onClick方法..." + method.getName());
                Button fakeBtn = new Button(HookTestActivity.this);
                fakeBtn.setText("text of fake Btn");
                return method.invoke(originalClickListener, fakeBtn);
            }
        });
        //反射将系统的onClickListener替换为上面代理生成的instance,
        setOnClickListenerMethod.invoke(button, instance);//1
//        button.setOnClickListener((View.OnClickListener) instance);//该方法同1
    }

    /**
     * 思路：
     * 替换mListenerInfo.mOnClickListener字段
     * 调用链：View->View.ListenerInfo->mOnClickListener
     * Class->Method->Field->get/set
     *
     * @param button
     * @throws Exception
     */
    private void hook_Version2(Button button) throws Exception {
        Class<?> viewClass = Class.forName("android.view.View");
        //先拿到ListenerInfo
        Method getListenerInfoMethod = viewClass.getDeclaredMethod("getListenerInfo");
        getListenerInfoMethod.setAccessible(true);
        //拿到listenerInfo对象
        Object listenerInfoObj = getListenerInfoMethod.invoke(button);

        Class<?> listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
        Field mOnClickListenerField = listenerInfoClass.getField("mOnClickListener");
        Object originalListener = mOnClickListenerField.get(listenerInfoObj);

        Object instance = Proxy.newProxyInstance(getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.e("test", "hook到了onClick方法..." + method.getName());
                Button fakeBtn = new Button(HookTestActivity.this);
                fakeBtn.setText("text of fake Btn");
                return method.invoke(originalListener, fakeBtn);
            }
        });
        mOnClickListenerField.set(listenerInfoObj, instance);
    }
}