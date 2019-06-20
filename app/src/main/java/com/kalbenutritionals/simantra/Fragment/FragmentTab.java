package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentTab extends Fragment {
    private ViewPager mPager;
    private SectionsPagerAdapter viewPagerAdapter;
    private TabLayout tab_layout;
    View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tabs_icon_light,container,false);
        initComponent();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initComponent() {
        mPager = (ViewPager) v.findViewById(R.id.view_pager);
        tab_layout = (TabLayout) v.findViewById(R.id.tab_layout);
        setupViewPager(mPager);

        tab_layout.setupWithViewPager(mPager);

        tab_layout.getTabAt(0).setIcon(R.drawable.ic_question_answer_black_24dp);
        tab_layout.getTabAt(1).setIcon(R.drawable.ic_directions_car_black_24dp);

        // set icon color pre-selected
        tab_layout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.deep_orange_500), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Questioner Checker");
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(viewPagerAdapter.getTitle(tab.getPosition()));
                tab.getIcon().setColorFilter(getResources().getColor(R.color.deep_orange_500), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        String myValue, noSPM;
        int statusLoading;
        Bundle bundle = new Bundle();
        if (this.getArguments() != null) {
            myValue = this.getArguments().getString(ClsHardCode.txtMessage);
            noSPM = this.getArguments().getString(ClsHardCode.txtNoSPM);
            statusLoading = this.getArguments().getInt(ClsHardCode.txtStatusLoading);
            bundle.putString(ClsHardCode.txtMessage, myValue);
            bundle.putString(ClsHardCode.txtNoSPM, noSPM);
            bundle.putInt(ClsHardCode.txtStatusLoading, statusLoading);
        }
//        viewPagerAdapter.addFragment(FragmentDetailInfoChecker.newInstance(), "Questioner Checker");    // index 0
        Fragment fragmentQuestionTab = FragmentQuestionTab.newInstance();
        fragmentQuestionTab.setArguments(bundle);
        viewPagerAdapter.addFragment(fragmentQuestionTab, "Questioner Checker");    // index 0
        viewPagerAdapter.addFragment(FragmentDestinationDetail.newInstance(), "General Information");   // index 1
        viewPager.setAdapter(viewPagerAdapter);
    }
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public String getTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
