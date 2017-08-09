package com.baidu.ui.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;

import com.baidu.ui.PluginActivity;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

/**
 * Created by admin on 2017/7/29.
 */

public class PluginManager {

    private static String TAG = PluginManager.class.getSimpleName();
    private static PluginManager instance;
    private static DexClassLoader mClassLoader;
    private static Context mCtx;
    private static int i =0;

    public static PluginManager newInstance(Context ctx){
        if(instance == null)
            instance = new PluginManager(ctx);
        return instance;
    }

    private PluginManager(Context ctx){
        mCtx = ctx;
    }

    public static void init(Context ctx){
        mCtx = ctx;
        Log.d(TAG, "替换之前系统的classLoader");
        showClassLoader();
        try {
            String cachePath = mCtx.getCacheDir().getAbsolutePath();
            String apkPath =  Utils.getDexPath(mCtx);
            mClassLoader = new DexClassLoader(apkPath, cachePath,cachePath, mCtx.getClassLoader());
            loadApkClassLoader(mClassLoader);

            Intent intent = new Intent(mCtx,PluginActivity.class);
            mCtx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "替换之后系统的classLoader");
        showClassLoader();
    }

    public static void loadApkClassLoader(DexClassLoader loader) {
        try {
            Object currentActivityThread  = ReflectHelper.invokeMethod("android.app.ActivityThread", "currentActivityThread", new Class[] {},new Object[] {});
            String packageName = mCtx.getPackageName();
            HashMap mpackages = (HashMap) ReflectHelper.getField("android.app.ActivityThread", "mPackages", currentActivityThread);
            WeakReference wr= (WeakReference)mpackages.get(packageName);
            Log.e(TAG, "mClassLoader:" + wr.get());
            ReflectHelper.setField("android.app.LoadedApk", "mClassLoader", wr.get(), loader);
            Log.e(TAG, "load:" + loader);
        } catch (Exception e) {
            Log.e(TAG, "load apk classloader error:" + Log.getStackTraceString(e));
        }
    }

    /**
     * 打印系统的classLoader
     */
    public static void showClassLoader() {
        ClassLoader classLoader = mCtx.getClassLoader();
        if (classLoader != null){
            Log.i(TAG, "[onCreate] classLoader " + i + " : " + classLoader.toString());
            while (classLoader.getParent()!=null){
                classLoader = classLoader.getParent();
                Log.i(TAG,"[onCreate] classLoader " + i + " : " + classLoader.toString());
                i++;
            }
        }
    }

}
