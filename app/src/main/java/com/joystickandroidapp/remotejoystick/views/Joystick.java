package com.joystickandroidapp.remotejoystick.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class Joystick extends View {
    public double initCenterX;
    public double initCenterY;
    public double movingX = 0;
    public double movingY = 0;
    public double radius = 170;
    public JoystickListener joystickListener;

    public Joystick(Context context) {
        super(context);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /* the function draw the joystick(circle) */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initCenterX = getWidth() / 2;
        initCenterY = getHeight() / 2;
        double centerX = initCenterX + this.movingX;
        double centerY = initCenterY + this.movingY;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawPaint(paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle((float)centerX, (float)centerY, (float)radius, paint);
    }

    /* the function react every touch and move the joystick accordingly  */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                this.movingX = x - (getWidth() / 2);
                this.movingY = y - (getHeight() / 2);
                if (joystickListener!= null) {
                    joystickListener.onMoved((float)this.movingX, (float)this.movingY);
                }
                // draw the joystick in the new position
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                this.movingX = 0;
                this.movingY = 0;
                if (joystickListener!= null) {
                    joystickListener.onMoved(0, 0);
                }
                // draw the joystick in the new position
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

}

/* functional interface */
interface JoystickListener {
    void onMoved(float x, float y);
}

