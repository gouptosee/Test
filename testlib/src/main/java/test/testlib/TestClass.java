package test.testlib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

/**
 * Created by admin on 2017/5/19.
 */

public class TestClass {

    public static String TAG = "TestClass";

    public View showT(final Context ctx){
        FrameLayout frameLayout = new FrameLayout(ctx);
        TextView tv = new TextView(ctx);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int with = ctx.getResources().getDisplayMetrics().widthPixels;
        Log.d(TAG,"width ------- >>>>  "+with);
        params1.width = with;
        tv.setLayoutParams(params1);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(20,50,20,50);
        tv.setBackgroundColor(Color.parseColor("#000000"));
        tv.setTextColor(Color.parseColor("#ffffffff"));
        tv.setText("TextClass");
        tv.setFocusable(true);
        Log.d(TAG,"width  after------- >>>>  "+ tv.getLayoutParams().width);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,"TV  Click",Toast.LENGTH_SHORT).show();
                TextView tv = (TextView) v;
                tv.setText("Text Click");
            }
        });

        tv.setLayoutParams(params1);
        tv.postInvalidate();
        frameLayout.addView(tv);


        WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int flag = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL   ;
//        int flag = 0;
        int type = WindowManager.LayoutParams.TYPE_TOAST ;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,type,flag, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.BOTTOM;

        manager.addView(frameLayout,params);
        return frameLayout;

    }

    public void hideT(Context ctx,View view){
        WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        manager.removeView(view);
    }

}
