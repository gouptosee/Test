package test.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import dalvik.system.DexClassLoader;

/**
 * Created by admin on 2017/5/19.
 * NoUse This can not success
 */

public class Test2 {

    public static void main(String args[]) throws Exception {
//        String dexPath = "F:/classes.jar";
//        String direct = "F:/opt";
//        DexClassLoader loader = new DexClassLoader(dexPath,direct,null,ClassLoader.getSystemClassLoader());
//        Class<?> mClass =loader.loadClass("test.testlib.TestLib");
//        Method add = mClass.getDeclaredMethod("add",int.class,int.class);
//        System.out.println(add.invoke(1,2));



//        loadBitmap();
//        copyFile();
        copyText();
    }


    private static void loadBitmap() throws Exception {
        String path ="https://ubmcmm.baidustatic.com/media/v1/0f000PHTmuKhFYwAov0ogf.gif";
        File file = new File("F:/aaa.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        URL url = new URL(path);
        URLConnection conn = url.openConnection();
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.connect();
        InputStream is = conn.getInputStream();
        byte data[] = new byte[1024];
        int len;
        while ((len = is.read(data))!=-1){
            fos.write(data,0,len);
        }
        fos.flush();

        is.close();
        fos.close();


    }

    private static void copyFile(){
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            File output = new File("F:/output.exe");
            File input = new File("F:/Git-2.12.2.2-64-bit.exe");
            bis = new BufferedInputStream(new FileInputStream(input));
            bos = new BufferedOutputStream(new FileOutputStream(output));

            int len = 0;
            byte data[] = new byte[2048];
            while ((len = bis.read(data))!=-1){
                bos.write(data,0,len);
            }
            bos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bis.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyText(){
        BufferedReader br = null;
        BufferedWriter bw = null;

        File output = new File("F:/output.txt");
        File input = new File("F:/新建文本文档.txt");

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(input),"GBK"));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),"GBK"));

            String length = null;
            while ((length = br.readLine())!=null){
                bw.write(length);
                bw.newLine();
//
            }
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
