package shp.kalbecallplanaedp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import shp.kalbecallplanaedp.BL.clsMainBL;
import shp.kalbecallplanaedp.Common.mActivity;
import shp.kalbecallplanaedp.Common.tProgramVisit;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.Model.clsListItemAdapter;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Repo.mActivityRepo;
import shp.kalbecallplanaedp.Repo.tProgramVisitRepo;
import shp.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import shp.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import shp.kalbecallplanaedp.adapter.ExpandableListAdapter;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/2/2018.
 */

public class FragmentListCallPlan extends Fragment{

    View v;
    ExpandableListView mExpandableListView;
    ExpandableListAdapter mExpandableListAdapter;
    private FloatingActionButton fab;
    private static List<clsListItemAdapter> swipeListPlan = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListUnplan = new ArrayList<>();
    private static List<String> listDataHeader = new ArrayList<>();
    private static HashMap<String, List<clsListItemAdapter>> listDataChild = new HashMap<>();
    private Toolbar toolbar;
    clsMainBL _clsMainBL=null;
    private String DT_CALL_PLAN = "dtCallPlan";
    List<tRealisasiVisitPlan> listRealisasi;
    List<tProgramVisit> listVisitHeader;
    List<tProgramVisitSubActivity> listVisitDetail;
    tRealisasiVisitPlanRepo repoRealisasi;
    tProgramVisitRepo repoProgramVisit;
    tProgramVisitSubActivityRepo repoProgramVisitSubActivity;
    mActivityRepo repoActivity;

    public FragmentListCallPlan(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_callplan_fragment, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_add_unplan);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        repoProgramVisit = new tProgramVisitRepo(getContext());
        repoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        repoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
        repoActivity = new mActivityRepo(getContext());
        final tRealisasiVisitPlan dataCheckinActive = (tRealisasiVisitPlan) repoRealisasi.getDataCheckinActive();
//        try {
//            if (repoProgramVisit.isExistProgramVisit(getContext())){
//                fab.setVisibility(View.VISIBLE);
//            }else {
//                fab.setVisibility(View.GONE);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        listDataHeader.clear();
        listDataChild.clear();
        swipeListPlan.clear();
        swipeListUnplan.clear();

        try {
            listVisitDetail = (List<tProgramVisitSubActivity>) repoProgramVisitSubActivity.findAllNew();
//            listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.findAll();
            if (listVisitDetail!=null){
                if (listVisitDetail.size()>0){
                    for (tProgramVisitSubActivity data : listVisitDetail){
                        tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) repoRealisasi.findBytxtPlanId(data.getTxtProgramVisitSubActivityId());
                        if (dtRealisasi.getIntStatusRealisasi()== new clsHardCode().VisitPlan){
                            clsListItemAdapter swpItem =  new clsListItemAdapter();
                            if (data.getIntType()==new clsHardCode().Plan){
                                mActivity dtActivity = null;
                                try {
                                    dtActivity = (mActivity) repoActivity.findById(data.getIntActivityId());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                if (dtActivity!=null){
                                    swpItem.setTxtTittle(dtActivity.getTxtName());
                                    if (dtActivity.getIntActivityId()==1){
                                        swpItem.setTxtSubTittle("Visit Doctor " + data.getTxtDokterName());
                                    }else if (dtActivity.getIntActivityId()==2){
                                        swpItem.setTxtSubTittle("Visit " + data.getTxtApotekName());
                                    }else {
                                        if (data.getTxtNotes()!=null)
                                            swpItem.setTxtSubTittle(data.getTxtNotes());
                                    }
                                }else {
                                    swpItem.setTxtTittle("");
                                    swpItem.setTxtSubTittle("");
                                }
                                swpItem.setTxtDate(parseDate(data.getDtStart()));
                                swpItem.setIntColor(R.color.purple_600);
                                swpItem.setBoolSection(false);
                                swpItem.setTxtImgName("PL");
                                swpItem.setTxtId(data.getTxtProgramVisitSubActivityId());
                                swipeListPlan.add(swpItem);
                            }else if (data.getIntType()==new clsHardCode().UnPlan){
                                mActivity dtActivity = null;
                                try {
                                    dtActivity = (mActivity) repoActivity.findById(data.getIntActivityId());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                if (dtActivity!=null){
                                    swpItem.setTxtTittle(dtActivity.getTxtName());
                                    if (dtActivity.getIntActivityId()==new clsHardCode().VisitDokter){
                                        swpItem.setTxtSubTittle("Visit Doctor " + data.getTxtDokterName());
                                    }else if (dtActivity.getIntActivityId()==new clsHardCode().VisitApotek){
                                        swpItem.setTxtSubTittle("Visit " + data.getTxtApotekName());
                                    }else {
                                        if (data.getTxtNotes()!=null)
                                            swpItem.setTxtSubTittle(data.getTxtNotes());
                                    }
                                }else {
                                    swpItem.setTxtTittle("");
                                    swpItem.setTxtSubTittle("");
                                }
                                swpItem.setTxtDate(parseDate(data.getDtStart()));
                                swpItem.setIntColor(R.color.blue_500);
                                swpItem.setBoolSection(false);
                                swpItem.setTxtImgName("NP");
                                swpItem.setTxtId(data.getTxtProgramVisitSubActivityId());
                                swipeListUnplan.add(swpItem);
                            }
                        }
                    }

                    listDataHeader.add("Plan (" + String.valueOf(swipeListPlan.size()) + ")");
                    listDataHeader.add("Unplan (" + String.valueOf(swipeListUnplan.size()) + ")");
                    listDataChild.put(listDataHeader.get(0), swipeListPlan);
                    listDataChild.put(listDataHeader.get(1), swipeListUnplan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        mExpandableListAdapter = new shp.kalbecallplanaedp.adapter.ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (dataCheckinActive==null){
                    Bundle data = new Bundle();

                    data.putString( DT_CALL_PLAN , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
                    FragmentCallPlan fragmentCallPlan = new FragmentCallPlan();
                    fragmentCallPlan.setArguments(data);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragmentCallPlan);
                    fragmentTransaction.commit();
                }else {
                    new ToastCustom().showToasty(getContext(), "Please checkout if you want to checkin again", 4);
                }

                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataCheckinActive==null){
                    toolbar.setTitle("Add Call Plan Unplan");

                    FragmentAddUnplan fragmentAddUnplan = new FragmentAddUnplan();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragmentAddUnplan);
                    fragmentTransaction.commit();
                }else {
                    new ToastCustom().showToasty(getContext(), "Please checkout if you want to add unplan", 4);
                }

            }
        });
        return v;
    }

    private String parseDate(String dateExp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

}
