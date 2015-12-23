package com.huan.background.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.huan.background.R;
import com.huan.background.view.FloatingBackgroundView;

/**
 * Use to hold floating view
 *
 * Created by barryjiang on 2015/12/23.
 */
public class BackGroundService extends Service {

    private static final String             TAG = "BackGroundService";

    public static final String             BROADCAST_CONFIGCHANGED = "android.intent.action.CONFIGURATION_CHANGED";
    public static final String             BROADCAST_TURN_ON_BACKGROUND = "com.huan.background.action.TURN_ON_BACKGROUND";
    public static final String             BROADCAST_TURN_OFF_BACKGROUND = "com.huan.background.action.TURN_OFF_BACKGROUND";
    public static final String             BROADCAST_CHANGE_BACKGROUND = "com.huan.background.action.CHANGE_BACKGROUND";

    public static final String              INTENT_KEY_BACKGROUND_DRAW_ID = "com.huan.background.BACKGROUND_DRAW_ID";

    private FloatingBackgroundView          mFloatingBackgroundView;
    private int                             mCurrentOrientation;

    public void onCreate(){
        super.onCreate();

    }

    public int onStartCommand (Intent intent, int flags, int startId){

        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_CONFIGCHANGED);
        filter.addAction(BROADCAST_CHANGE_BACKGROUND);
        filter.addAction(BROADCAST_TURN_ON_BACKGROUND);
        filter.addAction(BROADCAST_TURN_OFF_BACKGROUND);
        registerReceiver(mBroadcastReceiver, filter);

        mFloatingBackgroundView = new FloatingBackgroundView(this);

        mCurrentOrientation = getResources().getConfiguration().orientation;
        return START_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onDestroy(){
        unregisterReceiver(mBroadcastReceiver);
    }



    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent myIntent) {

            if ( myIntent.getAction().equals(BROADCAST_CONFIGCHANGED) ) {
                Log.d(TAG, "received->" + BROADCAST_CONFIGCHANGED);

                int current = getResources().getConfiguration().orientation;
                if(current == mCurrentOrientation){
                    Log.d(TAG,"Need not change! XD");
                }
                else {
                    WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                    Display currentDisplay = windowManager.getDefaultDisplay();
                    int screenWidth = currentDisplay.getWidth();
                    int screenHeight = currentDisplay.getHeight();
                    mFloatingBackgroundView.setBackgroundSize(screenWidth,screenHeight);
                    mCurrentOrientation = current;
                }
            }else if(myIntent.getAction().equals(BROADCAST_TURN_ON_BACKGROUND)){
                mFloatingBackgroundView.turnOn();
            }else if(myIntent.getAction().equals(BROADCAST_TURN_OFF_BACKGROUND)){
                mFloatingBackgroundView.turnOff();
            }else if(myIntent.getAction().equals(BROADCAST_CHANGE_BACKGROUND)){
                mFloatingBackgroundView.changeBackgroundDrawable(myIntent.getIntExtra(INTENT_KEY_BACKGROUND_DRAW_ID, 0));
            }
        }
    };
}
