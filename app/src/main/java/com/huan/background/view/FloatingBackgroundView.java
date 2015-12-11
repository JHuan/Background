package com.huan.background.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.huan.background.R;
import com.huan.background.activity.MainActivity;

/**
 * Floating background view!
 * Created by barryjiang on 2015/10/30.
 */
public class FloatingBackgroundView extends RelativeLayout {

    private static final String             TAG = "FloatingBackgroundView";
    private static final String             BCAST_CONFIGCHANGED = "android.intent.action.CONFIGURATION_CHANGED";

    private WindowManager                   mWindowsManager;
    private View                            mRootView;
    private WindowManager.LayoutParams      wmParams;

    private ImageView                       mImageBackGround;

    private Context                         mContext;
    private int                             mCurrentOrientation;
    private int                             mCurrentScreenWidth;
    private int                             mCurrentScreenHeight;
    

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

        mContext = context;

        mRootView = LayoutInflater.from(context).inflate(R.layout.view_floating_background, null);
        mImageBackGround = (ImageView) mRootView.findViewById(R.id.imageBackGround);

        mImageBackGround.setAlpha(MainActivity.DEAFAULT_ALAPHA);

        //At first should be invisible
        mRootView.setVisibility(INVISIBLE);
        initFloatingWindow(context);

        mCurrentOrientation = context.getResources().getConfiguration().orientation;

        IntentFilter filter = new IntentFilter();
        filter.addAction(BCAST_CONFIGCHANGED);
        mContext.registerReceiver(mBroadcastReceiver, filter);

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
        wmParams.y = 0 - getStatusBarHeight(context);


        Display currentDisplay = mWindowsManager.getDefaultDisplay();
        int screenWidth = currentDisplay.getWidth();
        int screenHeight = currentDisplay.getHeight();

        mCurrentScreenWidth = screenWidth;
        mCurrentScreenHeight = screenHeight;

        wmParams.width = screenWidth;
        wmParams.height = screenHeight;

        mWindowsManager.addView(mRootView,wmParams);

    }


    /**
     * Change background's drawable
     * @param drawableResID Resource ID of drawable
     */
    public void changeBackgroundDrawable(int drawableResID){
        mImageBackGround.setImageResource(drawableResID);
    }

    /**
     * turn on Background
     */
    public void turnOn(){
        //register a configuration listener
        mRootView.setVisibility(VISIBLE);
    }

    /**
     * turn off Background
     */
    public void turnOff(){

        mRootView.setVisibility(INVISIBLE);
    }

    /**
     *  Change the background view's size
     * @param width
     * @param height
     */
    private void setBackgroundSize(int width,int height){
        if(wmParams!=null && mWindowsManager!=null){
            wmParams.width = width;
            wmParams.height = height;
            mWindowsManager.updateViewLayout(mRootView,wmParams);
        }
    }


    /**
     * Floating background is On?
     * @return
     */
    public boolean isOn(){
        return mRootView.getVisibility()==VISIBLE;
    }

    /**
     * Get the status bar's height
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context){
        int statusBarHeight=0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     *  Must call it when activity finishs
     */
    public void release(){
        mContext.unregisterReceiver(mBroadcastReceiver);
    }


    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent myIntent) {

            if ( myIntent.getAction().equals( BCAST_CONFIGCHANGED ) ) {

                Log.d(TAG, "received->" + BCAST_CONFIGCHANGED);

                int current = getResources().getConfiguration().orientation;
                if(current == mCurrentOrientation){
                   Log.d(TAG,"Need not change! XD");
                }
                else {
                    setBackgroundSize(wmParams.height,wmParams.width);
                    mCurrentOrientation = current;
                }
            }
        }
    };

}
