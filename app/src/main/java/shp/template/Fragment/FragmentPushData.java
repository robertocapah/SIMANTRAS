package shp.template.Fragment;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shp.template.BL.clsHelperBL;
import shp.template.BL.clsMainBL;
import shp.template.Common.clsPushData;
import shp.template.Common.clsToken;
import shp.template.Common.mActivity;
import shp.template.Common.mApotek;
import shp.template.Common.mDokter;
import shp.template.Common.mSubSubActivity;
import shp.template.Common.mUserLogin;
import shp.template.Common.tAkuisisiDetail;
import shp.template.Common.tAkuisisiHeader;
import shp.template.Common.tInfoProgramDetail;
import shp.template.Common.tInfoProgramHeader;
import shp.template.Common.tMaintenanceDetail;
import shp.template.Common.tMaintenanceHeader;
import shp.template.Common.tProgramVisit;
import shp.template.Common.tProgramVisitSubActivity;
import shp.template.Common.tRealisasiVisitPlan;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;
import shp.template.Data.VolleyResponseListener;
import shp.template.Data.VolleyUtils;
import shp.template.Data.clsHardCode;
import shp.template.Model.clsListItemAdapter;
import shp.template.R;
import shp.template.Repo.clsTokenRepo;
import shp.template.Repo.mActivityRepo;
import shp.template.Repo.mApotekRepo;
import shp.template.Repo.mDokterRepo;
import shp.template.Repo.mSubSubActivityRepo;
import shp.template.Repo.tAkuisisiDetailRepo;
import shp.template.Repo.tAkuisisiHeaderRepo;
import shp.template.Repo.tInfoProgramDetailRepo;
import shp.template.Repo.tInfoProgramHeaderRepo;
import shp.template.Repo.tMaintenanceDetailRepo;
import shp.template.Repo.tMaintenanceHeaderRepo;
import shp.template.Repo.tProgramVisitRepo;
import shp.template.Repo.tProgramVisitSubActivityRepo;
import shp.template.Repo.tRealisasiVisitPlanRepo;
import shp.template.ResponseDataJson.loginMobileApps.LoginMobileApps;
import shp.template.ResponseDataJson.responsePushData.ResponsePushData;
import shp.template.Service.MyServiceNative;
import shp.template.SplashActivity;
import shp.template.adapter.ExpandableListAdapter;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentPushData extends Fragment{
    View v;
    ExpandableListView mExpandableListView;
    ExpandableListAdapter mExpandableListAdapter;
    private static List<clsListItemAdapter> swipeListPlan = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListUnplan = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListAkuisisi = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListMaintenance = new ArrayList<>();
    private static List<clsListItemAdapter> swipeListInfoProgram = new ArrayList<>();
    private static List<String> listDataHeader = new ArrayList<>();
    private static HashMap<String, List<clsListItemAdapter>> listDataChild = new HashMap<>();
    List<tRealisasiVisitPlan> listRealisasi;
    List<tProgramVisit> listVisitHeader;
    List<tProgramVisitSubActivity> listVisitDetail;
    List<tAkuisisiHeader> listAkuisisiHeader;
    List<tAkuisisiDetail> listAkuisisiDetail;
    List<tInfoProgramHeader> listInfoHeader;
    List<tInfoProgramDetail> listInfoDetail;
    List<tMaintenanceHeader> listMainHeader;
    List<tMaintenanceDetail> listMainDetail;
    tAkuisisiHeaderRepo akuisisiHeaderRepo;
    tAkuisisiDetailRepo akuisisiDetailRepo;
    tMaintenanceHeaderRepo maintenanceHeaderRepo;
    tMaintenanceDetailRepo maintenanceDetailRepo;
    tInfoProgramHeaderRepo infoProgramHeaderRepo;
    tInfoProgramDetailRepo infoProgramDetailRepo;
    tRealisasiVisitPlanRepo repoRealisasi;
    tProgramVisitRepo repoProgramVisit;
    tProgramVisitSubActivityRepo repoProgramVisitSubActivity;
    mActivityRepo repoActivity;
    mActivity dtActivity;
    mDokterRepo dokterRepo;
    mApotekRepo apotekRepo;

    FloatingActionButton button_push_data;
    //    Button btn_push_error;
    private Gson gson;
    ProgressDialog pDialog;
    String myValue;
    List<clsToken> dataToken;
    clsTokenRepo tokenRepo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_push_data, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
        button_push_data = (FloatingActionButton)v.findViewById(R.id.button_push_data);
//        btn_push_error = (Button) v.findViewById(R.id.btn_push_error);
//        btn_push_error = new Button(getContext());
//        btn_push_error.setText("Push data error");
//        btn_push_error.setId(1993);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        btn_push_error.setLayoutParams(params);
//        btn_push_error.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
//        btn_push_error.setPadding(10, 10, 10, 10);
////        btn_push_error.setBackgroundColor(getContext().getResources().getColor(R.color.white));
//        btn_push_error.setTextColor(getContext().getResources().getColor(R.color.red_A400));
//        GradientDrawable shape =  new GradientDrawable();
//        shape.setCornerRadius(20);
//        shape.setColor(getContext().getResources().getColor(R.color.white));
//        btn_push_error.setBackground(shape);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        pDialog = new ProgressDialog(getContext(),ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);

        repoProgramVisit = new tProgramVisitRepo(getContext());
        repoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        repoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
        repoActivity = new mActivityRepo(getContext());
        akuisisiDetailRepo = new tAkuisisiDetailRepo(getContext());
        akuisisiHeaderRepo = new tAkuisisiHeaderRepo(getContext());
        maintenanceDetailRepo = new tMaintenanceDetailRepo(getContext());
        maintenanceHeaderRepo = new tMaintenanceHeaderRepo(getContext());
        infoProgramDetailRepo = new tInfoProgramDetailRepo(getContext());
        infoProgramHeaderRepo = new tInfoProgramHeaderRepo(getContext());
        apotekRepo = new mApotekRepo(getContext());
        dokterRepo = new mDokterRepo(getContext());

//        tLogErrorRepo _tLogErrorRepo = new tLogErrorRepo(getContext());
//        List<tLogError> ListOfDataError = _tLogErrorRepo.getAllPushData();
//        if (ListOfDataError!=null){
//            if (ListOfDataError.size()>0){
//                btn_push_error.setVisibility(View.VISIBLE);
//            }else {
//                btn_push_error.setVisibility(View.GONE);
//            }
//        }
        if(this.getArguments()!=null){
            myValue = this.getArguments().getString("message");
            getContext().stopService(new Intent(getContext(), MyServiceNative.class));
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        }


        setListData();
//        ListData();
        button_push_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pushData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

//        btn_push_error.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    pushDataError();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        return v;
    }

    private void pushData() throws JSONException {
        pDialog.setMessage("Push your data....");
        pDialog.setCancelable(false);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        pDialog.show();
        String versionName = "";
        try {
            versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        final clsPushData dtJson = new clsHelperBL().pushData(versionName, getContext());
        if (dtJson == null){
        }else {
            String linkPushData = new clsHardCode().linkPushData;
            new VolleyUtils().makeJsonObjectRequestPushData(getContext(), linkPushData, dtJson, pDialog, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    new ToastCustom().showToasty(getContext(),message,4);
//                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {
                    if (response!=null){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            ResponsePushData model = gson.fromJson(jsonObject.toString(), ResponsePushData.class);
                            boolean isStatus = model.getResult().isStatus();
                            String txtMessage = model.getResult().getMessage();
                            String txtMethod = model.getResult().getMethodName();
                            if (isStatus==true){
                                if (model.getData().getModelData()!=null){
                                    if (model.getData().getModelData().size()>0){
                                        new  clsHelperBL().SavePushData(getContext(), dtJson.getDataJson(), model);
                                    }
                                }
                                setListData();
                                new ToastCustom().showToasty(getContext(),"Success Push Data",1);

                                if (myValue!=null){
                                    if (myValue.equals("notMainMenu")){
                                        //logout
                                        logout();
                                    }
                                }
                            }else {
                                new ToastCustom().showToasty(getContext(),txtMessage, 4);
                            }

                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        new ToastCustom().showToasty(getContext(),strErrorMsg,4);
                        pDialog.dismiss();
                    }
                }
            });
        }
    }

    public void setListData(){
        listDataHeader.clear();
        listDataChild.clear();
        swipeListPlan.clear();
        swipeListUnplan.clear();
        swipeListAkuisisi.clear();
        swipeListInfoProgram.clear();
        swipeListMaintenance.clear();

        try {
            listVisitDetail = (List<tProgramVisitSubActivity>) repoProgramVisitSubActivity.getAllPushData();
//        listRealisasi = (List<tRealisasiVisitPlan>) repoRealisasi.getAllPushData();
            if (listVisitDetail!=null){
                if (listVisitDetail.size()>0){
                    for (tProgramVisitSubActivity data : listVisitDetail){
//                    tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) repoRealisasi.findBytxtPlanId(data.getTxtProgramVisitSubActivityId());
                        clsListItemAdapter swpItem =  new clsListItemAdapter();
                        mDokter dokter;
                        mApotek apotek;
                        String name = null;
                        dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                        if (data.getIntActivityId()==new clsHardCode().VisitDokter){
//                            dokter = dokterRepo.findBytxtId(data.getTxtDokterId());
//                            if (!dokter.getTxtLastName().equals("null")&&dokter.getTxtLastName()!=null){
//                                name = "Visit Doctor " + dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
//                            }else {
//                                name = "Visit Doctor " + dokter.getTxtFirstName();
//                            }
                            name = "Visit Doctor " + data.getTxtDokterName();

                            swpItem.setTxtImgName((data.getTxtDokterName().substring(0,1)).toUpperCase());
                            swpItem.setTxtSubTittle(name);
                        }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
//                            apotek = apotekRepo.findBytxtId(data.getTxtApotekId());
//                            name = "Visit " + apotek.getTxtName();
                            name = "Visit " + data.getTxtApotekName();
                            swpItem.setTxtImgName((data.getTxtApotekName().substring(0,1)).toUpperCase());
                            swpItem.setTxtSubTittle(name);
                        }else {
                            swpItem.setTxtSubTittle("");
                            swpItem.setTxtImgName((dtActivity.getTxtName().substring(0,1)).toUpperCase());
                        }
                        swpItem.setTxtTittle(dtActivity.getTxtName());
                        swpItem.setIntColor(R.color.purple_600);
                        swpItem.setBoolSection(false);
                        swpItem.setTxtId(data.getTxtProgramVisitSubActivityId());
                        swipeListPlan.add(swpItem);
                    }
                }
            }
            try {
                listAkuisisiHeader = akuisisiHeaderRepo.getAllPushData();
                if (listAkuisisiHeader!=null){
                    if (listAkuisisiHeader.size()>0){
                        for (tAkuisisiHeader data : listAkuisisiHeader){
                            clsListItemAdapter swpItem =  new clsListItemAdapter();
                            String name = null;
                            mDokter dokter;
                            mApotek apotek;
                            boolean isDokter = false;
                            if (data.getIntDokterId()!=null){
                                if (!data.getIntDokterId().equals("null")){
                                    isDokter = true;
                                }else {
                                    isDokter = false;
                                }
                            }else if (data.getIntApotekID()!=null){
                                if (!data.getIntApotekID().equals("null")){
                                    isDokter = false;
                                }else {
                                    isDokter = true;
                                }
                            }
                            tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) repoRealisasi.findBytxtId(data.getTxtRealisasiVisitId());
                            if (isDokter){
                                dtActivity =  (mActivity) repoActivity.findById(new clsHardCode().VisitDokter);
                                dokter = dokterRepo.findBytxtId(data.getIntDokterId());
//                                name = "Visit Doctor" + dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                                name = "Visit Doctor" + dtRealisasi.getTxtDokterName();
                                swpItem.setTxtImgName((dtRealisasi.getTxtDokterName().substring(0,1)).toUpperCase());
                            }else {
                                dtActivity =  (mActivity) repoActivity.findById(new clsHardCode().VisitApotek);
                                apotek = apotekRepo.findBytxtId(data.getIntApotekID());
//                                name = "Visit " + apotek.getTxtName();
                                name = "Visit " + dtRealisasi.getTxtApotekName();
                                swpItem.setTxtImgName((dtRealisasi.getTxtApotekName().substring(0,1)).toUpperCase());
                            }
                            listAkuisisiDetail = (List<tAkuisisiDetail>) akuisisiDetailRepo.findByHeaderId(data.getTxtHeaderId());
                            if (listAkuisisiDetail!=null){
                                mSubSubActivity subDetailActivity = (mSubSubActivity) new mSubSubActivityRepo(getContext()).findById(data.getIntSubSubActivityId());
                                swpItem.setTxtDate(subDetailActivity.getTxtName() + " (" +String.valueOf(listAkuisisiDetail.size())+ ")");
                            }
                            swpItem.setTxtTittle(dtActivity.getTxtName());
                            swpItem.setTxtSubTittle(name);
                            swpItem.setIntColor(R.color.lime_600);
                            swpItem.setBoolSection(false);
                            swpItem.setTxtId(data.getTxtHeaderId());
                            swipeListAkuisisi.add(swpItem);
                        }
                    }
                }

                listMainHeader = maintenanceHeaderRepo.getAllPushData();
                if (listMainHeader!=null){
                    if (listMainHeader.size()>0){
                        for (tMaintenanceHeader data : listMainHeader){
                            listMainDetail = (List<tMaintenanceDetail>) maintenanceDetailRepo.findByHeaderPushId(data.getTxtHeaderId());
                            if (listMainDetail!=null){
                                if (listMainDetail.size()>0){
                                    clsListItemAdapter swpItem =  new clsListItemAdapter();
                                    String name = null;
                                    mDokter dokter;
                                    mApotek apotek;
                                    tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) repoRealisasi.findBytxtId(data.getTxtRealisasiVisitId());
                                    if (data.getIntActivityId()==new clsHardCode().VisitDokter){
                                        if (!data.getIntDokterId().equals("null")){
                                            dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                                            dokter = dokterRepo.findBytxtId(data.getIntDokterId());
//                                            name = "Visit Doctor " + dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                                            name = "Visit Doctor " + dtRealisasi.getTxtDokterName();
                                            swpItem.setTxtImgName((dtRealisasi.getTxtDokterName().substring(0,1)).toUpperCase());
                                        }
                                    }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
                                        dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                                        apotek = apotekRepo.findBytxtId(data.getIntApotekID());
//                                        name = "Visit " + apotek.getTxtName();
                                        name = "Visit " + dtRealisasi.getTxtApotekName();
                                        swpItem.setTxtImgName((dtRealisasi.getTxtApotekName().substring(0,1)).toUpperCase());
                                    }

                                    mSubSubActivity subDetailActivity = (mSubSubActivity) new mSubSubActivityRepo(getContext()).findById(listMainDetail.get(0).getIntSubDetailActivityId());
//                            swpItem.setTxtDate(String.valueOf(listMainDetail.size()));
                                    swpItem.setTxtDate(subDetailActivity.getTxtName() + " (" +String.valueOf(listMainDetail.size())+ ")");

                                    swpItem.setTxtTittle(dtActivity.getTxtName());
                                    swpItem.setTxtSubTittle(name);
                                    swpItem.setIntColor(R.color.pink_600);
                                    swpItem.setBoolSection(false);
                                    swpItem.setTxtId(data.getTxtHeaderId());
                                    swipeListMaintenance.add(swpItem);
                                }
                            }
                        }
                    }
                }

                listInfoHeader = infoProgramHeaderRepo.getAllPushData();
                if (listInfoHeader!=null){
                    if (listInfoHeader.size()>0){
                        for (tInfoProgramHeader data : listInfoHeader){
                            clsListItemAdapter swpItem =  new clsListItemAdapter();
                            String name = null;
                            mDokter dokter;
                            mApotek apotek;
                            tRealisasiVisitPlan dtRealisasi = (tRealisasiVisitPlan) repoRealisasi.findBytxtId(data.getTxtRealisasiVisitId());
                            if (data.getIntActivityId()==new clsHardCode().VisitDokter){
                                dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                                dokter = dokterRepo.findBytxtId(data.getIntDokterId());
//                                name = "Visit Doctor " + dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                                name = "Visit Doctor " + dtRealisasi.getTxtDokterName();
                                swpItem.setTxtImgName((dtRealisasi.getTxtDokterName().substring(0,1)).toUpperCase());
                            }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
                                dtActivity =  (mActivity) repoActivity.findById(data.getIntActivityId());
                                apotek = apotekRepo.findBytxtId(data.getIntApotekId());
//                                name = "Visit " + apotek.getTxtName();
                                name = "Visit " + dtRealisasi.getTxtApotekName();
                                swpItem.setTxtImgName((dtRealisasi.getTxtApotekName().substring(0,1)).toUpperCase());
                            }
                            listInfoDetail = (List<tInfoProgramDetail>) infoProgramDetailRepo.findByHeaderPushId(data.getTxtHeaderId());
                            if (listInfoDetail!=null){
                                mSubSubActivity subDetailActivity = (mSubSubActivity) new mSubSubActivityRepo(getContext()).findById(listInfoDetail.get(0).getIntSubDetailActivityId());
                                swpItem.setTxtDate(subDetailActivity.getTxtName() + " (" +String.valueOf(listInfoDetail.size())+ ")");
//                            swpItem.setTxtDate(String.valueOf(listInfoDetail.size()));
                            }
                            swpItem.setTxtTittle(dtActivity.getTxtName());
                            swpItem.setTxtSubTittle(name);
                            swpItem.setIntColor(R.color.green_500);
                            swpItem.setBoolSection(false);
                            swpItem.setTxtId(data.getTxtHeaderId());
                            swipeListInfoProgram.add(swpItem);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            listDataHeader.add("Plan (" + String.valueOf(swipeListPlan.size()) + ")");
//        listDataHeader.add("Unplan (" + String.valueOf(swipeListUnplan.size()) + ")");
            listDataHeader.add("Akuisisi (" + String.valueOf(swipeListAkuisisi.size()) + ")");
            listDataHeader.add("Maintenance (" + String.valueOf(swipeListMaintenance.size()) + ")");
            listDataHeader.add("Info Program (" + String.valueOf(swipeListInfoProgram.size()) + ")");
            listDataChild.put(listDataHeader.get(0), swipeListPlan);
//        listDataChild.put(listDataHeader.get(1), swipeListUnplan);
            listDataChild.put(listDataHeader.get(1), swipeListAkuisisi);
            listDataChild.put(listDataHeader.get(2), swipeListMaintenance);
            listDataChild.put(listDataHeader.get(3), swipeListInfoProgram);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        mExpandableListAdapter = new shp.template.adapter.ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
//    mExpandableListView.addFooterView(btn_push_error);
    }

    private void logout() {
        String strLinkAPI = new clsHardCode().linkLogout;
        JSONObject resJson = new JSONObject();
        mUserLogin dtLogin = new clsMainBL().getUserLogin(getContext());
        JSONObject dataJson = new JSONObject();


        try {
            dataJson.put("GuiId", dtLogin.getTxtGuID() );
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", dataJson);
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();

        new clsHelperBL().volleyLogin(getActivity(), strLinkAPI, mRequestBody, "Logout....",false, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                new ToastCustom().showToasty(getContext(),message,4);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        LoginMobileApps model = gson.fromJson(jsonObject.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();

                        if (txtStatus == true){

                            getActivity().stopService(new Intent(getContext(), MyServiceNative.class));
                            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            new clsMainBL().deleteMediaStorage();
                            clearData();

                            Log.d("Data info", "logout Success");

                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void clearData() {
        Intent intent = new Intent(getContext(), SplashActivity.class);
        DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
        helper.clearDataAfterLogout();
        getActivity().finish();
        startActivity(intent);
    }

//    private void pushDataError() throws JSONException {
////        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
////        pDialog.setTitleText("Pushing Data");
////        pDialog.setTitle("Pushing Your data");
//        pDialog.setMessage("Push your data....");
//        pDialog.setCancelable(false);
//        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//
//            }
//        });
//        pDialog.show();
//        String versionName = "";
//        try {
//            versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e2) {
//            // TODO Auto-generated catch block
//            e2.printStackTrace();
//        }
//        final clsPushData dtJson = new clsHelperBL().pushDataError(versionName, getContext());
//        if (dtJson == null){
//        }else {
//            String linkPushData = new clsHardCode().linkPushDataError;
//            new VolleyUtils().makeJsonObjectRequestPushError(getContext(), linkPushData, dtJson, pDialog, new VolleyResponseListener() {
//                @Override
//                public void onError(String message) {
//                    ToastCustom.showToasty(getContext(),message,4);
////                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                    pDialog.dismiss();
//                }
//
//                @Override
//                public void onResponse(String response, Boolean status, String strErrorMsg) {
//                    if (response!=null){
//                        JSONObject jsonObject = null;
//                        try {
//                            jsonObject = new JSONObject(response);
//                            PushLogError model = gson.fromJson(jsonObject.toString(), PushLogError.class);
//                            boolean isStatus = model.getResult().isStatus();
//                            String txtMessage = model.getResult().getMessage();
//                            String txtMethod = model.getResult().getMethodName();
//                            if (isStatus==true){
//                                if (dtJson.getDataError().getListOfDatatLogError()!=null){
//                                    if (dtJson.getDataError().getListOfDatatLogError().size()>0){
//                                        for (int i = 0; i < dtJson.getDataError().getListOfDatatLogError().size(); i++){
//                                            new tLogErrorRepo(getContext()).delete(dtJson.getDataError().getListOfDatatLogError().get(i));
//                                        }
//                                    }
//                                }
//                                btn_push_error.setVisibility(View.GONE);
//                                ToastCustom.showToasty(getContext(),"Success Push Data",1);
//
//                                if (myValue!=null){
//                                    if (myValue.equals("notMainMenu")){
//                                        //logout
//                                        logout();
//                                    }
//                                }
//                            }else {
//                                btn_push_error.setVisibility(View.VISIBLE);
//                                ToastCustom.showToasty(getContext(),txtMessage, 4);
//                            }
//
//                            pDialog.dismiss();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }else {
//                        ToastCustom.showToasty(getContext(),strErrorMsg,4);
//                        pDialog.dismiss();
//                    }
//                }
//            });
//        }
//    }
}
