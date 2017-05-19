package test.testlib;

/**
 * Created by admin on 2017/5/19.
 */

public class TestLib {

    public int add(int a ,int  b){
        return a + b;
    }

    public int delete(int a ,int b){
        return a - b;
    }

    public int multiply(int a,int b){
        return a * b;

    }

    public int div(int a,int b){
        return a/b;
    }


    public static String toast(){
        return "这是Lib返回的Toast";
    }
}
