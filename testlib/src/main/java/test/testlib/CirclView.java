package test.testlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by admin on 2017/5/20.
 */

public class CirclView extends ImageView {
    public CirclView(Context context) {
        super(context);
    }

    public CirclView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CirclView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private int mWidth;
    private int mHeight;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth/2,mHeight/2,mWidth/2,paint);

        canvas.save();
        canvas.rotate(45,mWidth/2,mHeight/2);
        Paint paint1 = new Paint();
        paint1.setColor(Color.parseColor("#ff0000"));
        paint1.setStrokeWidth(5);
        paint1.setStyle(Paint.Style.STROKE);
        canvas.drawLine(2,mHeight/2,mWidth-2,mHeight/2,paint1);
        canvas.save();
        canvas.rotate(90,mWidth/2,mHeight/2);
        canvas.drawLine(2,mHeight/2,mWidth-2,mHeight/2,paint1);

        canvas.restore();
    }
}
