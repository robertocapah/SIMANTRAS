package shp.template.Fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import shp.template.Common.mActivity;
import shp.template.Common.mApotek;
import shp.template.Common.mDokter;
import shp.template.Common.mSubSubActivity;
import shp.template.Common.tNotification;
import shp.template.Common.tRealisasiVisitPlan;
import shp.template.Data.clsHardCode;
import shp.template.Model.clsItemGroupNotifAdapter;
import shp.template.Model.clsListItemAdapter;
import shp.template.R;
import shp.template.Repo.mActivityRepo;
import shp.template.Repo.mApotekRepo;
import shp.template.Repo.mDokterRepo;
import shp.template.Repo.mSubSubActivityRepo;
import shp.template.Repo.tNotificationRepo;
import shp.template.Repo.tRealisasiVisitPlanRepo;
import shp.template.adapter.ExpandableListAdapterNotif;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by ASUS on 01/11/2018.
 */
public class FragmentNotification extends Fragment {
    View v;
    ExpandableListView mExpandableListView;
    ExpandableListAdapterNotif mExpandableListAdapter;
    private static List<clsItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private static HashMap<clsItemGroupNotifAdapter, List<clsListItemAdapter>> listDataChild = new HashMap<>();
    tNotificationRepo notificationRepo;
    List<tNotification> notificationList = new ArrayList<>();
    List<tNotification> listChild = new ArrayList<>();
    String nama;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_n_order, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);

        listDataHeader.clear();
        listDataChild.clear();
        notificationRepo = new tNotificationRepo(getContext());
        try {
            notificationList = (List<tNotification>) notificationRepo.findOutletId();
            if (notificationList!=null){

                if (notificationList.size()>0){
                    int index = 0;
                    for (tNotification data : notificationList){
                        clsItemGroupNotifAdapter itemAdapter = new clsItemGroupNotifAdapter();
                        mActivity activity = (mActivity) new mActivityRepo(getContext()).findById(data.getIntActivityId());
                        itemAdapter.setTxtTittle(activity.getTxtName());
                        if (data.getIntActivityId()==new clsHardCode().VisitDokter){
                            mDokter dokter = new mDokterRepo(getContext()).findBytxtId(data.getIntDokterId());
                            if (dokter==null){
                                tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) new tRealisasiVisitPlanRepo(getContext()).findBytxtDokterId(data.getIntDokterId());
                                nama = dtRealisasi.getTxtDokterName();
                            }else {
                                if (dokter.getTxtLastName()!=null){
                                    if (!dokter.getTxtLastName().equals("null")){
                                        nama = dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                                    }
                                }else {
                                    nama = dokter.getTxtFirstName();
                                }
                            }

                            listChild = (List<tNotification>) notificationRepo.findByOutletId(data.getIntDokterId(), data.getIntActivityId());
                            itemAdapter.setTxtSubTittle("Dokter " + nama + "  (" + String.valueOf(listChild.size())+ ")");
                        }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
                            mApotek apotek = new mApotekRepo(getContext()).findBytxtId(data.getIntApotekId());
                            if (apotek==null){
                                tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) new tRealisasiVisitPlanRepo(getContext()).findBytxtApotekId(data.getIntApotekId());
                                nama = dtRealisasi.getTxtApotekName();
                            }else {
                                nama = apotek.getTxtName();
                            }
                            listChild = (List<tNotification>) notificationRepo.findByOutletId(data.getIntApotekId(), data.getIntActivityId());
                            itemAdapter.setTxtSubTittle(nama + "  (" + String.valueOf(listChild.size())+ ")");
                        }
                        itemAdapter.setTxtImgName((nama.substring(0,1)).toUpperCase());
                        itemAdapter.setIntColor(getResources().getColor(R.color.pink_400));
                        listDataHeader.add(itemAdapter);

                        List<clsListItemAdapter> listChildAdapter = new ArrayList<>();
                        for (tNotification child : listChild){
                            mSubSubActivity subSubActivity = (mSubSubActivity)new mSubSubActivityRepo(getContext()).findById(child.getIntSubDetailActivityId()) ;
                            clsListItemAdapter itemAdapter1 = new clsListItemAdapter();
                            itemAdapter1.setTxtTittle(subSubActivity.getTxtName());
                            itemAdapter1.setTxtSubTittle(child.getTxtNoDoc());
                            itemAdapter1.setTxtDate(parseDate(child.getDtExpired()));
                            listChildAdapter.add(itemAdapter1);
                        }
                        listDataChild.put(listDataHeader.get(index), listChildAdapter);
                        index++;
                    }
                }
                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
                ShortcutBadger.removeCountOrThrow(getActivity().getApplicationContext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ShortcutBadgeException e) {
            e.printStackTrace();
        }

        mExpandableListAdapter = new ExpandableListAdapterNotif(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
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

//    @Override
//    public void onResume() {
//        super.onResume();
//    }
}
