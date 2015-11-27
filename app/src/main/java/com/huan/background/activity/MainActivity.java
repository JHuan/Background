package com.huan.background.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.huan.background.R;
import com.huan.background.adapter.MainAdapter;
import com.huan.background.view.FloatingBackgroundView;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

import java.util.Arrays;

public class MainActivity extends Activity {

    private final static int[] BACKDROUND_DRAWABLE_ID =
            new int[]{R.drawable.background,R.drawable.background2,R.drawable.background3,R.drawable.background4};

    public final static float           DEAFAULT_ALAPHA = 0.7f;

    private     Button                  mbtnStart;

    private     FloatingBackgroundView  mFloatingBackgroundView;

    private     JazzyViewPager          mJazzy;
    private     MainAdapter             mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFloatingBackgroundView = (FloatingBackgroundView)findViewById(R.id.floatingBackground);

        mbtnStart = (Button) findViewById(R.id.buttonStartFloatingBackground);
        mbtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int resId = mAdapter.getCurrentDrawResID();
                mFloatingBackgroundView.changeBackgroundDrawable(resId);
                mFloatingBackgroundView.turnOn();
                Intent i= new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                startActivity(i);
            }
        });

        setupJazziness(JazzyViewPager.TransitionEffect.Tablet);

    }

    private void setupJazziness(JazzyViewPager.TransitionEffect effect) {
        mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
        mJazzy.setTransitionEffect(effect);

        mAdapter = new MainAdapter(BACKDROUND_DRAWABLE_ID, mJazzy,this);
        mJazzy.setAdapter(mAdapter);
        mJazzy.setOnPageChangeListener(mAdapter);
        mJazzy.setPageMargin(30);
        mJazzy.setAlpha(DEAFAULT_ALAPHA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFloatingBackgroundView.turnOff();
    }
}
