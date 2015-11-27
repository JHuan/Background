package com.huan.background.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.OutlineContainer;

import java.util.List;

/**
 * A adapter of MainActivity
 * Created by barryjiang on 2015/11/26.
 */
public class MainAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{

    private int[]           arrResId;
    private JazzyViewPager  mJazzy;
    private Context         mContext;

    private int             mCuurentDrawableResID;

    public MainAdapter(int[] arrResId,JazzyViewPager viewPager,Context context){
        this.arrResId = arrResId;
        mJazzy = viewPager;
        mContext = context;
        mCuurentDrawableResID = arrResId[0];
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView backGroundView = new ImageView(mContext);
        backGroundView.setImageResource(arrResId[position]);
        container.addView(backGroundView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mJazzy.setObjectForPosition(backGroundView, position);
        return backGroundView;
    }

    @Override
    public int getCount() {
        return arrResId !=null ? arrResId.length : 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCuurentDrawableResID = arrResId[position];
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.removeView(mJazzy.findViewFromObject(position));
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (view instanceof OutlineContainer) {
            return ((OutlineContainer) view).getChildAt(0) == object;
        } else {
            return view == object;
        }
    }

    public int getCurrentDrawResID(){
        return mCuurentDrawableResID;
    }
}
