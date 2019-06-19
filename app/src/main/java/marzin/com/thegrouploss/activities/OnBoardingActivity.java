package marzin.com.thegrouploss.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import marzin.com.thegrouploss.R;
import marzin.com.thegrouploss.fragments.onBoardingFragment;
import marzin.com.thegrouploss.utils.SectionStatePagerAdapter;

/**
 * Created by coolm_000 on 4/10/2018.
 */

public class OnBoardingActivity extends FragmentActivity {

    private final String TAG = "Onboarding Activity";
    // Vars
    private SectionStatePagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        mSectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        //setup Pager
        setupViewPager(mViewPager);
    }

    public void setupViewPager(ViewPager viewPager){
        SectionStatePagerAdapter adapter= new SectionStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new onBoardingFragment(),"Onboarding");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);

    }
}
