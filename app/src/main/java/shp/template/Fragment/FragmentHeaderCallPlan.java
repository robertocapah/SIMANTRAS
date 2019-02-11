package shp.template.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import shp.template.R;
import shp.template.Utils.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/15/2018.
 */

public class FragmentHeaderCallPlan extends Fragment {
    View v;
    private CustomViewPager customViewPager;
    TabLayout tabLayout;
    private String FRAG_VIEW = "Fragment view";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_header_callplan, container, false);
        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_maintenance);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout_maintenance);

        setupViewPager(customViewPager);
        tabLayout.setupWithViewPager(customViewPager);
        allotEachTabWithEqualWidth();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getString(FRAG_VIEW).equals("Plan")){
                customViewPager.setCurrentItem(0);
            }else if (bundle.getString(FRAG_VIEW).equals("Realisasi")){
                customViewPager.setCurrentItem(1);
            }
        }
        return v;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentListCallPlan(), "Plan");
        adapter.addFragment(new FragmentHistory(), "Realisasi");

        viewPager.setAdapter(adapter);
    }

    private void allotEachTabWithEqualWidth() {

        ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);
        if (tabLayout.getTabCount()>1){
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                View tab = slidingTabStrip.getChildAt(i);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
                layoutParams.weight = 1;
                tab.setLayoutParams(layoutParams);
            }
        }else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
