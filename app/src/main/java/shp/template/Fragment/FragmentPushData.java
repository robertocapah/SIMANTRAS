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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shp.template.ActivitySplash;
import shp.template.BL.BLHelper;
import shp.template.BL.BLMain;
import shp.template.CustomView.Adapter.AdapterExpandableList;
import shp.template.Data.ClsHardCode;
import shp.template.Data.ResponseDataJson.loginMobileApps.LoginMobileApps;
import shp.template.Database.Common.ClsPushData;
import shp.template.Database.Common.ClsToken;
import shp.template.Database.Common.ClsmUserLogin;
import shp.template.Database.DatabaseHelper;
import shp.template.Database.DatabaseManager;
import shp.template.Database.Repo.RepoclsToken;
import shp.template.Database.Repo.RepomUserLogin;
import shp.template.Network.Volley.InterfaceVolleyResponseListener;
import shp.template.Network.Volley.VolleyUtils;
import shp.template.R;
import shp.template.Service.ServiceNative;
import shp.template.ViewModel.VmListItemAdapter;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentPushData extends Fragment {
    View v;
    ExpandableListView mExpandableListView;
    AdapterExpandableList mAdapterExpandableList;
    private static List<VmListItemAdapter> swipeListPlan = new ArrayList<>();
    private static List<VmListItemAdapter> swipeListUnplan = new ArrayList<>();
    private static List<VmListItemAdapter> swipeListAkuisisi = new ArrayList<>();
    private static List<VmListItemAdapter> swipeListMaintenance = new ArrayList<>();
    private static List<VmListItemAdapter> swipeListInfoProgram = new ArrayList<>();
    private static List<String> listDataHeader = new ArrayList<>();
    private static HashMap<String, List<VmListItemAdapter>> listDataChild = new HashMap<>();


    FloatingActionButton button_push_data;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.brief)
    TextView brief;
    @BindView(R.id.ln_empty)
    LinearLayout lnEmpty;
    @BindView(R.id.exp_lv_call_plan)
    ExpandableListView expLvCallPlan;
    @BindView(R.id.button_push_data)
    FloatingActionButton buttonPushData;
    //    Button btn_push_error;
    private Gson gson;
    ProgressDialog pDialog;
    String myValue;
    List<ClsToken> dataToken;
    RepoclsToken tokenRepo;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_push_data, container, false);
        unbinder = ButterKnife.bind(this, v);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
        button_push_data = (FloatingActionButton) v.findViewById(R.id.button_push_data);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        pDialog = new ProgressDialog(getContext(), ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);


        if (this.getArguments() != null) {
            myValue = this.getArguments().getString("message");
            getContext().stopService(new Intent(getContext(), ServiceNative.class));
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
        final ClsPushData dtJson = new BLHelper().pushData(versionName, getContext());
        if (dtJson == null) {
        } else {
            String linkPushData = new ClsHardCode().linkPushData;
            new VolleyUtils().makeJsonObjectRequestPushData(getContext(), linkPushData, dtJson, pDialog, new InterfaceVolleyResponseListener() {
                @Override
                public void onError(String message) {
                    new ToastCustom().showToasty(getContext(), message, 4);
//                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {
                    if (response != null) {
                        JSONObject jsonObject = null;

                    } else {
                        new ToastCustom().showToasty(getContext(), strErrorMsg, 4);
                        pDialog.dismiss();
                    }
                }
            });
        }
    }

    public void setListData() {
        listDataHeader.clear();
        listDataChild.clear();
        swipeListPlan.clear();
        swipeListUnplan.clear();
        swipeListAkuisisi.clear();
        swipeListInfoProgram.clear();
        swipeListMaintenance.clear();


        mAdapterExpandableList = new AdapterExpandableList(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mAdapterExpandableList);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
//    mExpandableListView.addFooterView(btn_push_error);
    }

    private void logout() {
        String strLinkAPI = new ClsHardCode().linkLogout;
        JSONObject resJson = new JSONObject();
        ClsmUserLogin dtLogin = null;
        try {
            dtLogin = new RepomUserLogin(getContext()).getUserLogin(getContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject dataJson = new JSONObject();


        try {
            dataJson.put("GuiId", dtLogin.getTxtGuID());
            tokenRepo = new RepoclsToken(getContext());
            dataToken = (List<ClsToken>) tokenRepo.findAll();
            resJson.put("data", dataJson);
            resJson.put("device_info", new ClsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();

        new VolleyUtils().volleyLogin(getActivity(), strLinkAPI, mRequestBody, "Logout....", false, new InterfaceVolleyResponseListener() {
            @Override
            public void onError(String message) {
                new ToastCustom().showToasty(getContext(), message, 4);
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

                        if (txtStatus == true) {

                            getActivity().stopService(new Intent(getContext(), ServiceNative.class));
                            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
                            new BLMain().deleteMediaStorage();
                            clearData();

                            Log.d("Data info", "logout Success");

                        } else {
                            new ToastCustom().showToasty(getContext(), txtMessage, 4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void clearData() {
        Intent intent = new Intent(getContext(), ActivitySplash.class);
        DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
        helper.clearDataAfterLogout();
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
//        final ClsPushData dtJson = new BLHelper().pushDataError(versionName, getContext());
//        if (dtJson == null){
//        }else {
//            String linkPushData = new ClsHardCode().linkPushDataError;
//            new VolleyUtils().makeJsonObjectRequestPushError(getContext(), linkPushData, dtJson, pDialog, new InterfaceVolleyResponseListener() {
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
//                                            new RepotLogError(getContext()).delete(dtJson.getDataError().getListOfDatatLogError().get(i));
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
