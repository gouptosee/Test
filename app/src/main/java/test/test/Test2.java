package test.test;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by admin on 2017/5/19.
 * NoUse This can not success
 */

public class Test2 {

    public static void main(String args[]) throws Exception {
        String dexPath = "F:/classes.jar";
        String direct = "F:/opt";
        DexClassLoader loader = new DexClassLoader(dexPath,direct,null,ClassLoader.getSystemClassLoader());
        Class<?> mClass =loader.loadClass("test.testlib.TestLib");
        Method add = mClass.getDeclaredMethod("add",int.class,int.class);
        System.out.println(add.invoke(1,2));
    }
}
