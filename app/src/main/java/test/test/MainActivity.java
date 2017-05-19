package test.test;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigInteger;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "MainActivity";
    Button add, delete,show;
    int a = 10;
    int b = 20;
    String dexPath = Environment.getExternalStorageDirectory() + "/Test/test.jar";
    String optPath = Environment.getExternalStorageDirectory() + "/Test/test";
    DexClassLoader loader;
    Method add_m, delete_m,show_m;
    Class<?> mClass;
    Object mObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.checkPermission(this);
        add = (Button) findViewById(R.id.add);
        delete = (Button) findViewById(R.id.del);
        show = (Button) findViewById(R.id.show);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        show.setOnClickListener(this);
        dexPath = getDir("dex",MODE_PRIVATE).getAbsolutePath() +"/test.dex";
        optPath = getDir("opt",MODE_PRIVATE).getAbsolutePath();
       try {
           if(new File(dexPath).exists()&&new File(dexPath).isDirectory()) new File(dexPath).delete();
           if(!new File(dexPath).exists())  Log.i(TAG," "+  new File(dexPath).createNewFile());
           if(!new File(optPath).exists())  Log.i(TAG,"" +  new File(optPath).mkdirs());
       }catch (Exception e){

       }


        witreToDex(new File(Environment.getExternalStorageDirectory() + "/Test/testlib.dex"),new File(dexPath));
        long length = new File(dexPath).length();
        boolean isFile  =new File(dexPath).isFile();

        loadFis(new File(dexPath));

        Log.d(TAG,new File(dexPath).getAbsolutePath() +"   " + new File(dexPath).exists()+"   " + new File(dexPath).isFile() +"    " + new File(dexPath).length() );
        loader = new DexClassLoader(dexPath, optPath, null, ClassLoader.getSystemClassLoader());
        try {
            mClass = loader.loadClass("test.testlib.TestLib");
            Constructor<?> constructor = mClass.getConstructor();
            mObject = constructor.newInstance();
            add_m = mClass.getDeclaredMethod("add", int.class, int.class);
            delete_m = mClass.getDeclaredMethod("delete", int.class, int.class);
            show_m = mClass.getDeclaredMethod("toast");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        try {


            switch (v.getId()) {
                case R.id.add:
                    Toast.makeText(this, a + " + " + b + " = " + add_m.invoke(mObject, a, b), Toast.LENGTH_LONG).show();
                    break;
                case R.id.del:
                    Toast.makeText(this, a + " - " + b + " = " + delete_m.invoke(mObject, a, b), Toast.LENGTH_LONG).show();

                    break;
                case R.id.show:
                    Toast.makeText(this,show_m.invoke(null) +"",Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }


    private void witreToDex(File from,File to){
        boolean a = from.exists();
        if(from!=null&&to!=null&&from.exists()){
            if(!to.exists()) to.mkdirs();
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(from);
                fos = new FileOutputStream(to);
                byte data[] = new byte[1024];
                int len = 0;
                while ((len = fis.read(data))!=-1){
                    fos.write(data,0,len);
                }
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if(fis !=null) fis.close();
                    if(fos !=null) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void loadFis(File file){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte data[] = new byte[500];
            int len;
            while ((len = fis.read(data))!=-1){
                Log.d(TAG,"length      " + len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis!=null) try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
