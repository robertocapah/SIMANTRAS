package shp.template.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import shp.template.Common.mActivity;
import shp.template.Common.tAkuisisiHeader;
import shp.template.Common.tInfoProgramDetail;
import shp.template.Common.tInfoProgramHeader;
import shp.template.Common.tMaintenanceDetail;
import shp.template.Common.tMaintenanceHeader;
import shp.template.Common.tProgramVisit;
import shp.template.Common.tProgramVisitSubActivity;
import shp.template.Common.tRealisasiVisitPlan;
import shp.template.Data.clsHardCode;
import shp.template.Model.clsItemGroupNotifAdapter;
import shp.template.Model.clsListItemAdapter;
import shp.template.Model.clsMaintenance;
import shp.template.R;
import shp.template.Repo.mActivityRepo;
import shp.template.Repo.tAkuisisiHeaderRepo;
import shp.template.Repo.tInfoProgramDetailRepo;
import shp.template.Repo.tInfoProgramHeaderRepo;
import shp.template.Repo.tMaintenanceDetailRepo;
import shp.template.Repo.tMaintenanceHeaderRepo;
import shp.template.Repo.tProgramVisitRepo;
import shp.template.Repo.tProgramVisitSubActivityRepo;
import shp.template.Repo.tRealisasiVisitPlanRepo;
import shp.template.Utils.Tools;
import shp.template.adapter.AdapterListHistory;
import shp.template.adapter.ExpandableListAdapterNotif;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/13/2018.
 */

public class FragmentHistory extends Fragment {
    View v;
    private static List<clsMaintenance> swipeListPlan = new ArrayList<>();
//    private static List<String> listDataHeader = new ArrayList<>();
    AdapterListHistory adapter;
    ExpandableListView mExpandableListView;
    ExpandableListAdapterNotif mExpandableListAdapter;
    private static List<clsItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private static HashMap<clsItemGroupNotifAdapter, List<clsListItemAdapter>> listDataChild = new HashMap<>();
//    private static HashMap<String, List<clsListItemAdapter>> listDataChild = new HashMap<>();
    List<tRealisasiVisitPlan> listRealisasi;
    List<tProgramVisit> listVisitHeader;
    List<tProgramVisitSubActivity> listVisitDetail;
    tRealisasiVisitPlanRepo repoRealisasi;
    tProgramVisitRepo repoProgramVisit;
    tProgramVisitSubActivityRepo repoProgramVisitSubActivity;
    mActivityRepo repoActivity;
    LinearLayout lnEmpty;
    ListView listView;
    private String DT_CALL_PLAN = "dtCallPlan";
    private String DT_REALISASI = "Realisasi id";
    private String DT_HISTORY = "dari history";

    public FragmentHistory(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_n_order, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
//        listView = (ListView) v.findViewById(R.id.lv_infoprogram);
//        lnEmpty = (LinearLayout)v.findViewById(R.id.ln_emptyMain);
        repoProgramVisit = new tProgramVisitRepo(getContext());
        repoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        repoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
        repoActivity = new mActivityRepo(getContext());
//        swipeListPlan.clear();
//        try {
//            listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.getAllRealisasi();
//            if (listRealisasi!=null){
//                if (listRealisasi.size()>0){
//                    for (tRealisasiVisitPlan dataRealisasi : listRealisasi){
//                        tProgramVisitSubActivity data = (tProgramVisitSubActivity)repoProgramVisitSubActivity.findBytxtId(dataRealisasi.getTxtProgramVisitSubActivityId());
//                        clsMaintenance swpItem =  new clsMaintenance();
//                        mActivity dtActivity = null;
//                        try {
//                            dtActivity = (mActivity) repoActivity.findById(data.getIntActivityId());
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                        if (dtActivity!=null){
//
//                            if (dtActivity.getIntActivityId()==1){
//                                swpItem.setTxtTittle(dtActivity.getTxtName() + " : " + data.getTxtDokterName()) ;
////                                    swpItem.setTxtSubTittle("Visit Doctor " + );
//                            }else if (dtActivity.getIntActivityId()==2){
////                                    swpItem.setTxtSubTittle("Visit " + data.getTxtApotekName());
//                                swpItem.setTxtTittle(dtActivity.getTxtName() + " : " + data.getTxtApotekName()) ;
//                            }else {
////                                    if (data.getTxtNotes()!=null)
////                                        swpItem.setTxtSubTittle(data.getTxtNotes());
//                                swpItem.setTxtTittle(dtActivity.getTxtName());
//                            }
//                        }else {
//                            swpItem.setTxtTittle("");
////                            swpItem.setTxtSubTittle("");
//                        }
//                        swpItem.setTxtId(dataRealisasi.getTxtRealisasiVisitId());
//                        swpItem.setIntImgView(data.getIntActivityId());
//                        swipeListPlan.add(swpItem);
//                        clsMaintenance swpItem1 =  new clsMaintenance();
//                        swpItem1.setTxtId(dataRealisasi.getTxtRealisasiVisitId());
//                        swpItem1.setIntImgView(data.getIntActivityId());
//                        swipeListPlan.add(swpItem1);
//                    }
//                    adapter = new AdapterListHistory(getContext(), swipeListPlan);
//                    listView.setAdapter(adapter);
//                    listView.setDivider(null);
//                    listView.setEmptyView(lnEmpty);
//
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        listDataHeader.clear();
        listDataChild.clear();
        try {
            listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.getAllRealisasi();
            if (listRealisasi!=null){
                if (listRealisasi.size()>0){
                    int index = 0;
                    for (tRealisasiVisitPlan data : listRealisasi){
                        clsItemGroupNotifAdapter itemAdapter = new clsItemGroupNotifAdapter();
                        tProgramVisitSubActivity dataVisit = (tProgramVisitSubActivity)repoProgramVisitSubActivity.findBytxtId(data.getTxtProgramVisitSubActivityId());
                        mActivity activity = (mActivity) new mActivityRepo(getContext()).findById(dataVisit.getIntActivityId());

                        itemAdapter.setTxtTittle(activity.getTxtName());
                        if (dataVisit.getIntActivityId()==new clsHardCode().VisitDokter){
                            itemAdapter.setTxtSubTittle("Dokter " + dataVisit.getTxtDokterName());
                        }else if (dataVisit.getIntActivityId()==new clsHardCode().VisitApotek){
                            itemAdapter.setTxtSubTittle(dataVisit.getTxtApotekName());
                        }
                        if (dataVisit.getIntType()==new clsHardCode().Plan){
                            itemAdapter.setTxtImgName("PL");
                            itemAdapter.setIntColor(getResources().getColor(R.color.pink_400));
                        }else if (dataVisit.getIntType()==new clsHardCode().UnPlan){
                            itemAdapter.setTxtImgName("NP");
                            itemAdapter.setIntColor(getResources().getColor(R.color.green_400));
                        }
                        listDataHeader.add(itemAdapter);
                        List<clsListItemAdapter> listChildAdapter = new ArrayList<>();
                        //data realisasi
                        clsListItemAdapter itemAdapter2 = new clsListItemAdapter();
                        itemAdapter2.setTxtId(data.getTxtProgramVisitSubActivityId());
                        if (dataVisit.getIntActivityId()==new clsHardCode().VisitApotek||dataVisit.getIntActivityId()==new clsHardCode().VisitDokter){
                            itemAdapter2.setTxtTittle("Realisasi Visit");
                        }else {
                            itemAdapter2.setTxtTittle("Realisasi" + activity.getTxtName());
                        }
                        itemAdapter2.setTxtDate("More");
                        listChildAdapter.add(itemAdapter2);


                        List<tAkuisisiHeader> listAkuisisi = (List<tAkuisisiHeader>) new tAkuisisiHeaderRepo(getContext()).findByRealisasi(data.getTxtRealisasiVisitId());
                        if (listAkuisisi!=null){
                            if (listAkuisisi.size()>0){
                                clsListItemAdapter itemAdapter1 = new clsListItemAdapter();
                                itemAdapter1.setTxtId(data.getTxtRealisasiVisitId());
                                itemAdapter1.setTxtTittle("Akuisisi (" + String.valueOf(listAkuisisi.size()) + ")");
//                                itemAdapter1.setTxtSubTittle(child.getTxtNoDoc());
                                itemAdapter1.setTxtDate("More");
                                listChildAdapter.add(itemAdapter1);
                            }
                        }
                        tMaintenanceHeader maintenance = (tMaintenanceHeader) new tMaintenanceHeaderRepo(getContext()).findByRealisasiId(data.getTxtRealisasiVisitId()) ;
                        if (maintenance!=null){
                            List<tMaintenanceDetail> maintenanceList = (List<tMaintenanceDetail>) new tMaintenanceDetailRepo(getContext()).findByHeaderId(maintenance.getTxtHeaderId());
                            if (maintenanceList!=null){
                                if (maintenanceList.size()>0){
                                    clsListItemAdapter itemAdapter1 = new clsListItemAdapter();
                                    itemAdapter1.setTxtId(data.getTxtRealisasiVisitId());
                                    itemAdapter1.setTxtTittle("Maintenance (" + String.valueOf(maintenanceList.size()) + ")");
//                                itemAdapter1.setTxtSubTittle(child.getTxtNoDoc());
                                    itemAdapter1.setTxtDate("More");
                                    listChildAdapter.add(itemAdapter1);
                                }
                            }
                        }


//                        List<tInfoProgramHeader> infoProgramList = (List<tInfoProgramHeader>) new tInfoProgramHeaderRepo(getContext()).findbyListRealisasiId(data.getTxtRealisasiVisitId());
                        tInfoProgramHeader infoProgramHeader = (tInfoProgramHeader) new tInfoProgramHeaderRepo(getContext()).findbyRealisasiId(data.getTxtRealisasiVisitId());
                        if (infoProgramHeader!=null){
                            List<tInfoProgramDetail> infoProgramList = (List<tInfoProgramDetail>) new tInfoProgramDetailRepo(getContext()).findByHeaderId(infoProgramHeader.getTxtHeaderId());
                            if (infoProgramList!=null){
                                if (infoProgramList.size()>0){
                                    clsListItemAdapter itemAdapter1 = new clsListItemAdapter();
                                    itemAdapter1.setTxtTittle("Info Program (" + String.valueOf(infoProgramList.size()) + ")");
                                    itemAdapter1.setTxtId(data.getTxtRealisasiVisitId());
//                                itemAdapter1.setTxtSubTittle(child.getTxtNoDoc());
                                    itemAdapter1.setTxtDate("More");
                                    listChildAdapter.add(itemAdapter1);
                                }
                            }
                        }


                        listDataChild.put(listDataHeader.get(index), listChildAdapter);
                        index++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mExpandableListAdapter = new ExpandableListAdapterNotif(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle data = new Bundle();
                if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtTittle().contains("Akuisisi")){
                    data.putString( DT_REALISASI , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
                    new Tools().intentFragmentSetArgument(FragmentAkuisisi.class, "Akuisisi", getContext(), data);
                }else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtTittle().contains("Maintenance")){
                    data.putString( DT_REALISASI , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
                    new Tools().intentFragmentSetArgument(FragementMaintenance.class, "Maintenance", getContext(), data);
                }else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtTittle().contains("Info Program")){
                    data.putString( DT_REALISASI , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
                    new Tools().intentFragmentSetArgument(FragementInfoProgram.class, "Info Program", getContext(), data);
                }else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtTittle().contains("Realisasi")){
                    data.putString( DT_CALL_PLAN , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
                    data.putString(DT_HISTORY, "History");
                    new Tools().intentFragmentSetArgument(FragmentCallPlan.class, "Call Plan", getContext(), data);
                }
                return false;
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
