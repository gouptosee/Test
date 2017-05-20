package test.testlib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

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

}
