package shp.kalbecallplanaedp.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import shp.kalbecallplanaedp.Common.mDokter;
import shp.kalbecallplanaedp.Common.tMaintenanceDetail;
import shp.kalbecallplanaedp.Common.tMaintenanceHeader;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.Model.clsMaintenance;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Repo.mApotekRepo;
import shp.kalbecallplanaedp.Repo.mDokterRepo;
import shp.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import shp.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import shp.kalbecallplanaedp.adapter.AdapterListMaintenance;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentSubMaintenance extends Fragment {
    View v;
    tMaintenanceHeader header;
    tMaintenanceHeaderRepo headerRepo;
    List<tMaintenanceDetail> listDetail;
    List<String> listHeaderId;
    tMaintenanceDetailRepo detailRepo;
    int intSubSubActivity;
    AdapterListMaintenance adapter;
    ListView listView;
    private static List<clsMaintenance> itemAdapterList0 = new ArrayList<>();
    private static List<clsMaintenance> itemAdapterList1 = new ArrayList<>();
    mDokter dokter;
    String strName;
    int index;
    LinearLayout lnEmpty;
    SwipeRefreshLayout swpInfo;
    String txtSubSubActivity;
    tRealisasiVisitPlan dtCheckinActive;
    tProgramVisitSubActivity dataPlan;

    public FragmentSubMaintenance(List<String> listHeaderId, tMaintenanceHeader header, int intSubSubActivity, int index, String txtSubSubActivity, tRealisasiVisitPlan dtCheckinActive,  tProgramVisitSubActivity dataPlan){
        this.header = header;
        this.intSubSubActivity = intSubSubActivity;
        this.index = index;
        this.listHeaderId = listHeaderId;
        this.txtSubSubActivity = txtSubSubActivity;
        this.dtCheckinActive = dtCheckinActive;
        this.dataPlan = dataPlan;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_infoprogram, container, false);
        listView = (ListView) v.findViewById(R.id.lv_infoprogram);
        lnEmpty = (LinearLayout)v.findViewById(R.id.ln_emptyMain);
        swpInfo = (SwipeRefreshLayout)v.findViewById(R.id.swpInfo);

        detailRepo = new tMaintenanceDetailRepo(getContext());
        headerRepo = new tMaintenanceHeaderRepo(getContext());

        loadData();

        swpInfo.setOnRefreshListener(refreshListener);
        swpInfo.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                swpInfo.setEnabled(firstVisibleItem==0);
            }
        });

        return v;
    }

    private void loadData(){
        if (index==0){
            itemAdapterList0.clear();
        }else if (index==1){
            itemAdapterList1.clear();
        }


        if (header!=null){
            try {
                if (header.getIntActivityId()==new clsHardCode().VisitDokter){
                    dokter  = new mDokterRepo(getContext()).findBytxtId(header.getIntDokterId());
                    if (dokter.getTxtLastName()!=null){
                        strName = dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                    }else {
                        strName = dokter.getTxtFirstName();
                    }
                }else {
                    strName = new mApotekRepo(getContext()).findBytxtId(header.getIntApotekID()).getTxtName();
                }

                listDetail = (List<tMaintenanceDetail>) detailRepo.findByListHeaderIdandSubsubId(listHeaderId, intSubSubActivity);
                if (listDetail!=null){
                    if (listDetail.size()>0){
                        for (tMaintenanceDetail data : listDetail){
                            clsMaintenance itemAdapter = new clsMaintenance();
                            itemAdapter.setTxtId(data.getTxtDetailId());
                            itemAdapter.setTxtTittle(strName); //nama dokter substring(0,1)
                            itemAdapter.setTxtImgName((data.getTxtNoDoc().substring(0,1)).toUpperCase());
                            itemAdapter.setTxtSubTittle(data.getTxtNoDoc());
                            itemAdapter.setIntStatus(data.getIntFlagPush());
                            if (data.getIntFlagPush()==new clsHardCode().Save){
                                itemAdapter.setTxtStatus("Submit");
                                itemAdapter.setInColorStatus(R.color.red_200);
                            }else if (data.getIntFlagPush()==new clsHardCode().Sync){
                                itemAdapter.setTxtStatus("Sync");
                                itemAdapter.setInColorStatus(R.color.green_300);
                            }else if (data.getIntFlagPush()==new clsHardCode().Draft){
                                itemAdapter.setTxtStatus("Draft");
                                itemAdapter.setInColorStatus(R.color.grey_60);
                            }
                            if (index==0){
                                itemAdapter.setIntColor(R.color.amber_700);
                                itemAdapterList0.add(itemAdapter);
                            }else if (index==1){
                                itemAdapter.setIntColor(R.color.lime_600);
                                itemAdapterList1.add(itemAdapter);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (index==0){
            adapter = new AdapterListMaintenance(getContext(), itemAdapterList0);
        }else if (index==1){
            adapter = new AdapterListMaintenance(getContext(), itemAdapterList1);
        }

        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setEmptyView(lnEmpty);
//        new ToastCustom().showToasty(getActivity(),"haii...",1);
        adapter.setOnItemClickListener(new AdapterListMaintenance.onItemClickListener() {
            @Override
            public void onItemClick(View view, clsMaintenance obj, int position) {
                try {
                    tMaintenanceDetail detail = new tMaintenanceDetailRepo(getContext()).findByDetailId(obj.getTxtId());
                    if (detail.getIntFlagPush()==new clsHardCode().Draft){
                        new FragementMaintenance().showCustomDialog(true, getActivity(), txtSubSubActivity, intSubSubActivity, dtCheckinActive, dataPlan, detail);
                    }else if (detail.getIntFlagPush()== new clsHardCode().Save){
                        new ToastCustom().showToasty(getActivity(),"Already Submit...",4);
                    }else if (detail.getIntFlagPush()== new clsHardCode().Sync){
                        new ToastCustom().showToasty(getActivity(),"Already Sync...",1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData();
//            adapter.notifyDataSetChanged();
            swpInfo.setRefreshing(false);
        }
    };


}
