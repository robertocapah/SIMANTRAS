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
import shp.template.Common.mUserLogin;
import shp.template.Data.DatabaseHelper;
import shp.template.Data.DatabaseManager;
import shp.template.Data.VolleyResponseListener;
import shp.template.Data.VolleyUtils;
import shp.template.Data.clsHardCode;
import shp.template.Model.clsListItemAdapter;
import shp.template.R;
import shp.template.Repo.clsTokenRepo;
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        pDialog = new ProgressDialog(getContext(),ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);


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
