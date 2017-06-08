package test.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2017/5/22.
 */

public class CircleTest extends View {
    public CircleTest(Context context) {
        super(context);
    }

    public CircleTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int size = 80;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.RED);
        drawable.setStroke(15,Color.BLUE);
        drawable.setShape(GradientDrawable.OVAL);
        setBackground(drawable);
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,p);
    }
}
