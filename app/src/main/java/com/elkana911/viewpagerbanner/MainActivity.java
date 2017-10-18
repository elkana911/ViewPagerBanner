package com.elkana911.viewpagerbanner;

import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentBanner.OnFragmentInteractionListener{

    private Handler mBannerHandler = new Handler();
    public static final int DELAY_BANNER = 2000;
    private Runnable mRunnableBanner;
    private int currentBannerIndex;

    MyPageAdapter pageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onStart() {
        super.onStart();

        mRunnableBanner = new Runnable()
        {
            @Override
            public void run()
            {
                if (currentBannerIndex >= 3) {
                    currentBannerIndex = 0;
                }
                viewPager.setCurrentItem(currentBannerIndex++, true);
                mBannerHandler.postDelayed(mRunnableBanner, DELAY_BANNER );
            }
        };

        mBannerHandler.postDelayed(mRunnableBanner, DELAY_BANNER );
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBannerHandler.removeCallbacks(mRunnableBanner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(FragmentBanner.newInstance("http://www.imaging-resource.com/PRODS/nikon-d3300/ZYDSC_0314-600.JPG", "eric"));
        fList.add(FragmentBanner.newInstance("http://www.imaging-resource.com/PRODS/nikon-d3300/ZYDSC_0335-600.JPG", "elkana"));
        fList.add(FragmentBanner.newInstance("http://www.imaging-resource.com/PRODS/nikon-d3300/ZYDSC_0226-600.JPG", "tarigan"));

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fList);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOffscreenPageLimit(fList.size());  // to avoid createview execute twice
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}
