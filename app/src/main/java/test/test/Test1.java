package test.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by admin on 2017/5/19.
 */

public class Test1 {

    public static void main(String args[]) throws Exception {


        Class<?> mClass = Class.forName("test.test.ClassTest");
        Constructor constructor = mClass.getDeclaredConstructor(String.class,String.class);
        Method getName = mClass.getDeclaredMethod("getName");
        Method setName = mClass.getDeclaredMethod("setName",String.class);
        Object object = constructor.newInstance("张三","12");
        System.out.println(getName.invoke(object).toString());
        setName.invoke(object,"李四");
        System.out.println(getName.invoke(object).toString());

        Method getAge = mClass.getDeclaredMethod("getAge");
        Method setAge = mClass.getDeclaredMethod("setAge",String.class);
        setAge.setAccessible(true);
        System.out.println(getAge.invoke(object).toString());
        setAge.invoke(object,"15");
        System.out.println(getAge.invoke(object).toString());

    }
}
