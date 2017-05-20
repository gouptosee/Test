package test.testlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by admin on 2017/5/19.
 */

public class TestClass {

    public static String TAG = "TestClass";

    public View showT(final Context ctx){
        FrameLayout frameLayout = new FrameLayout(ctx);
        frameLayout.setBackgroundColor(Color.parseColor("#000000"));
        TextView tv = new TextView(ctx);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int with = ctx.getResources().getDisplayMetrics().widthPixels;
        params1.width = with;
        params1.height = 250;
        params1.leftMargin = 125;
        tv.setLayoutParams(params1);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(20,50,20,50);
        tv.setTextColor(Color.parseColor("#ffffffff"));
        tv.setText("TextClass");
        tv.setFocusable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ctx,"TV  Click",Toast.LENGTH_SHORT).show();
                TextView tv = (TextView) v;
                if(!tv.getText().toString().contains("Click")){
                    tv.setText("Text Click");
                }else {
                    tv.setText("TextClass");
                }
            }
        });

        CirclView circlView = new CirclView(ctx);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.width = 40;
        params2.height = 40;
        params2.gravity = Gravity.RIGHT|Gravity.TOP;
        circlView.setLayoutParams(params2);
        circlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideT(ctx, (View) v.getParent());
            }
        });



        tv.setLayoutParams(params1);
        tv.postInvalidate();
        frameLayout.addView(tv);
        frameLayout.addView(circlView);


        WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int flag = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL   ;
//        int flag = 0;
        int type = WindowManager.LayoutParams.TYPE_TOAST ;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,type,flag, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.BOTTOM;


        loadImage(ctx,frameLayout);

        frameLayout.requestFocus();
        manager.addView(frameLayout,params);

        return frameLayout;

    }

    public void hideT(Context ctx,View view){
        try {
            if (view != null) {
                WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
                manager.removeView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loadImage(final Context ctx,FrameLayout parent) {
        try {
        ImageView iv = new ImageView(ctx);
        FrameLayout.LayoutParams params  = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        params.gravity = Gravity.LEFT|Gravity.CENTER_VERTICAL;
        iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        iv.setLayoutParams(params);
        getBitmap(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,"Image Click",Toast.LENGTH_SHORT).show();
            }
        });
        parent.addView(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void getBitmap(final ImageView iv) throws Exception{

        final String path ="https://ubmcmm.baidustatic.com/media/v1/0f000PHTmuKhFYwAov0ogf.gif";
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {

                Bitmap bitmap = null;
                try {
                    URL mUrl = new URL(path);
                    InputStream is = mUrl.openStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                iv.setImageBitmap(bitmap);
            }
        }.execute("");



    }

}
