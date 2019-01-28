package shp.kalbecallplanaedp.Fragment;

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

import shp.kalbecallplanaedp.BL.clsMainBL;
import shp.kalbecallplanaedp.Common.mActivity;
import shp.kalbecallplanaedp.Common.mSubSubActivity;
import shp.kalbecallplanaedp.Common.tInfoProgramHeader;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.HomeFragment;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Repo.mActivityRepo;
import shp.kalbecallplanaedp.Repo.mSubActivityRepo;
import shp.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import shp.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import shp.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import shp.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import shp.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import shp.kalbecallplanaedp.Utils.CustomTablayout;
import shp.kalbecallplanaedp.Utils.CustomViewPager;
import shp.kalbecallplanaedp.Utils.IOBackPressed;
import shp.kalbecallplanaedp.Utils.Tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static shp.kalbecallplanaedp.Utils.CustomTablayout.getScreenSize;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

public class FragementInfoProgram extends Fragment implements IOBackPressed{
    View v;
    private CustomViewPager customViewPager;
    TabLayout tabLayout;
    List<mSubSubActivity> _mSubSubActivity;
    mActivity _mActivity;
    mSubSubActivityRepo subSubActivityRepo;
    mSubActivityRepo subActivityRepo;
    mActivityRepo activityRepo;
    tRealisasiVisitPlan dtCheckinActive;
//    tProgramVisitSubActivity dataPlan;
    tInfoProgramHeader dtHeader;
    tInfoProgramDetailRepo detailRepo;
    LinearLayout lnEmpty;
    private String DT_REALISASI = "Realisasi id";
    String txtRealisasiId;
    boolean valid = false;
    private String FRAG_VIEW = "Fragment view";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_infoprogram, container, false);

        lnEmpty = (LinearLayout)v.findViewById(R.id.ln_emptyInfo);
        customViewPager = (CustomViewPager) v.findViewById(R.id.view_pager_InfoProgram);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout_infoprogram);

        Bundle data = this.getArguments();
        if (data != null) {
            txtRealisasiId = data.getString(DT_REALISASI);
        }
        if (txtRealisasiId!=null && !txtRealisasiId.equals("")){
            valid = true;
        }

        if (valid){
            try {
                dtCheckinActive = new tRealisasiVisitPlanRepo(getContext()).findBytxtId(txtRealisasiId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            dtCheckinActive = new clsMainBL().getDataCheckinActive(getContext());
        }

//        try {
//            dataPlan = (tProgramVisitSubActivity) new tProgramVisitSubActivityRepo(getContext()).findBytxtId(dtCheckinActive.getTxtProgramVisitSubActivityId());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        try {
//            if (dataPlan.getIntActivityId()==new clsHardCode().VisitDokter){
//                dtHeader = (tInfoProgramHeader) new tInfoProgramHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtDokterId(),dataPlan.getIntActivityId());
//            }else if (dataPlan.getIntActivityId()==new clsHardCode().VisitApotek){
//                dtHeader = (tInfoProgramHeader) new tInfoProgramHeaderRepo(getContext()).findByOutletId(dtCheckinActive.getTxtApotekId(), dataPlan.getIntActivityId());
//            }
            dtHeader = (tInfoProgramHeader) new tInfoProgramHeaderRepo(getContext()).findbyRealisasiId(dtCheckinActive.getTxtRealisasiVisitId());

//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        detailRepo = new tInfoProgramDetailRepo(getContext());
        if (dtHeader!=null){
            _mSubSubActivity = detailRepo.getIntSubSubActivityId(getContext(), dtHeader.getTxtHeaderId());
        }

//        _mSubSubActivity = new ArrayList<>();
        subSubActivityRepo = new mSubSubActivityRepo(getContext());
//        try {
//            if (dataPlan.getIntActivityId()==1){
//                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(3);
//            }else if (dataPlan.getIntActivityId()==2){
//                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityId(6);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        setupViewPager(customViewPager);
        tabLayout.setupWithViewPager(customViewPager);
        allotEachTabWithEqualWidth();
        customViewPager.setCurrentItem(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                customViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        if (_mSubSubActivity!=null){
            if (_mSubSubActivity.size()>0){
                for (int i =0; i < _mSubSubActivity.size(); i++){
                    adapter.addFragment(new FragmentSubInfoProgram(dtHeader, _mSubSubActivity.get(i).getIntSubSubActivityid(), i, valid, dtCheckinActive), _mSubSubActivity.get(i).getTxtName());
                }
                lnEmpty.setVisibility(View.GONE);
            }else {
                lnEmpty.setVisibility(View.VISIBLE);
            }
        }else {
            lnEmpty.setVisibility(View.VISIBLE);
        }
//        adapter.addFragment(new FragmentSubInfoProgram(dtHeader, _mSubSubActivity.get(0).getIntSubSubActivityid(), 0), _mSubSubActivity.get(0).getTxtName());

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
//            int[] wh = getScreenSize(getContext());
//            tabLayout.widt(wh[0]);
        }

    }

    @Override
    public boolean onBackPressed() {
        if (valid){
//           new Tools().intentFragment(FragmentHistory.class, "History", getContext());
//            return true;
            Bundle bundle = new Bundle();
            bundle.putString(FRAG_VIEW, "Realisasi");
            new Tools().intentFragmentSetArgument(FragmentHeaderCallPlan.class, "Call Plan", getContext(), bundle);
            return true;
        }else {
            new Tools().intentFragment(HomeFragment.class, "Home", getContext());
            return true;
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
