package com.huan.background.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.huan.background.R;
import com.huan.background.view.FloatingBackgroundView;

public class MainActivity extends Activity {

    private     Button                  mbtnStart;

    private     FloatingBackgroundView  mFloatingBackgroundView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFloatingBackgroundView = (FloatingBackgroundView)findViewById(R.id.floatingBackground);

        mbtnStart = (Button) findViewById(R.id.buttonStartFloatingBackground);
        mbtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFloatingBackgroundView.turnOn();
                Intent i= new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFloatingBackgroundView.turnOff();
    }
}
