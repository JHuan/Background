package com.huan.background.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.*;
import android.widget.RelativeLayout;
import com.huan.background.R;

/**
 * 悬浮窗背景
 * Created by barryjiang on 2015/10/30.
 */
public class FloatingBackgroundView extends RelativeLayout {

    private WindowManager                   mWindowsManager;
    private View                            mRootView;
    WindowManager.LayoutParams              wmParams;

    public FloatingBackgroundView(Context context) {
        super(context);
        initView(context);
    }


    public FloatingBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FloatingBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {

        mRootView = LayoutInflater.from(context).inflate(R.layout.view_floating_background, null);
        //At first should be invisible
        mRootView.setVisibility(INVISIBLE);
        initFloatingWindow(context);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void initFloatingWindow(Context context) {

        mWindowsManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmParams= new WindowManager.LayoutParams();

        if(Build.VERSION.SDK_INT>=19){
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;

            //set Window flag
            wmParams.flags =
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        }
        else {
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                    | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        }

        wmParams.format = PixelFormat.TRANSPARENT;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;

        wmParams.x = 0;
        wmParams.y = 0;


        Display currentDisplay = mWindowsManager.getDefaultDisplay();
        int screenWidth = currentDisplay.getWidth();
        int screenHeight = currentDisplay.getHeight();

        wmParams.width = screenWidth;
        wmParams.height = screenHeight;

        mWindowsManager.addView(mRootView,wmParams);

    }


    /**
     * Change background's color
     * @param colorVal color value
     */
    public void changeBackgroundColor(int colorVal){

    }

    /**
     * turn on Background
     */
    public void turnOn(){

        mRootView.setVisibility(VISIBLE);
    }

    /**
     * turn off Background
     */
    public void turnOff(){

        mRootView.setVisibility(INVISIBLE);
    }


    /**
     * Floating background is On?
     * @return
     */
    public boolean isOn(){
        return mRootView.getVisibility()==VISIBLE;
    }

}
