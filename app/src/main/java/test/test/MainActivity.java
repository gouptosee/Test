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
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


import dalvik.system.DexClassLoader;

/**
 * 步骤 ：
 * 1.创建一个libary 定义相关的类或方法
 * 2.将library打包成jar
 *     studio右侧gradle选择library -->> assemble
 * 3.将library jar 打包成dex
 *     cmd  进入build-tools
 *     dx --dex --output=f:\testlib.dex f:\classes.ja
 *     output后面依次是输出路径和源文件jar路径
 * 4.将testlib放在 设备  /Test/testlib.dex 下
 *     实际开发都有自己的固定路径,dex可以直接放进去或者从网络下载过来
 * 5.将dex写入 设备的 getDir()目录下
 *     这是必须的,默认其他路径下都是无法加载的,和实际的读写权限有关，并不仅限于Manifest中的权限
 * 6.通过类加载器装载dex文件 以加载相关的Class
 *      类装载器需要传两个路径
 *      第一个是dex文件的绝对路径
 *      第二个是存放解压后的dex文件路径
 *      最好用两个文件夹分别存放
 * 7.通过反射 从Class中获取相关的方法
 *     这里注意静态方法和非静态方法的区别
 *     静态方法invoke不需要类对象
 *     public方法invoke需要装载类对象
 *
 *
 * SDK开发很大程度上依赖于反射
 * 有时候为了减少体积,会采用shell和core分开的形式
 * 用户导入shell,shell去网络获取core
 * 通过接口信息获取所有core需要操作的方法和参数
 * 然后通过类加载器加载 通过反射获取方法并加载相应的初始化或回收方法
 *
 *
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "MainActivity";
    Button add, delete,show;
    int a = 10;
    int b = 20;
    String dexPath;
    String optPath ;
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
