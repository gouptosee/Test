package com.baidu.ui.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2017/7/29.
 */

public class Utils {
    public static String getDexPath(Context ctx){
        File dexPath = new File(ctx.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath() + "/test.dex");
        return dexPath.getAbsolutePath();
    }

    public static void copyAssert(Context ctx,String name,String des){
        try {
            InputStream is = ctx.getAssets().open(name);
            File file = new File(des);
            if(!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(des);
            byte data[] = new byte[1024];
            int len =0;
            while ((len = is.read(data))!=-1){
                fos.write(data,0,len);
            }
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //...
        }
    }
}
