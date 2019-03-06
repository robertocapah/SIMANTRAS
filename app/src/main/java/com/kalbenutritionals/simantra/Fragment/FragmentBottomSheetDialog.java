package com.kalbenutritionals.simantra.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kalbenutritionals.simantra.BL.BLMain;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterListSearch;
import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;
import com.kalbenutritionals.simantra.CustomView.Utils.IOBackPressed;
import com.kalbenutritionals.simantra.CustomView.Utils.OnReceivedData;
import com.kalbenutritionals.simantra.CustomView.Utils.ViewAnimation;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getMasterDokter.GetDataMasterNew;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepoclsToken;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.VmSearch;

public class FragmentBottomSheetDialog extends BottomSheetDialogFragment implements IOBackPressed {

    Boolean isFirstTime;
    private BottomSheetBehavior mBehavior;
    private LinearLayout lyt_profile, lnEmpty;
    AdapterListSearch adapter;
    OnReceivedData receivedData;
    List<VmSearch> itemAdapterList = new ArrayList<>();
    ListView listView;
    AppCompatAutoCompleteTextView autoCompleteTextView;
    private Handler handler;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    List<ClsToken> dataToken;
    RepoclsToken tokenRepo;
    private Gson gson;
    CardView cv_new_dokter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet_dialog, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        listView = (ListView) view.findViewById(R.id.lv_unplan);
        lnEmpty = (LinearLayout)view.findViewById(R.id.ln_empty);
        autoCompleteTextView = view.findViewById(R.id.auto_complete_edit_text);
        cv_new_dokter = (CardView)view.findViewById(R.id.cv_new_dokter);
        ((View) view.findViewById(R.id.lyt_spacer)).setMinimumHeight(ClsTools.getScreenHeight() / 3);
        final LinearLayout lyt_progress = (LinearLayout) view.findViewById(R.id.lyt_progress);
        lyt_progress.setVisibility(View.GONE);
        lyt_progress.setAlpha(1.0f);
        isFirstTime = true;
        lnEmpty.setVisibility(View.GONE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
//                    hideView(lyt_profile);
//                }
//                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
//                    showView(lyt_profile, getActionBarSize());
//                }
//
//                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
//                    dismiss();
//                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        // Add back button listener
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                // getAction to make sure this doesn't double fire
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    // Your code here
                    if (isFirstTime){
                        ViewAnimation.fadeOut(lyt_progress);
                    }
                    receivedData.onDataReceived(false, "Select One", false);
                    dialog.dismiss();
                    return true; // Capture onKey
                }
                return false; // Don't capture
            }
        });

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    itemAdapterList.clear();
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    lnEmpty.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())&&autoCompleteTextView.getText().toString().length()>2) {
                        isFirstTime = false;
                        downloadDokter(autoCompleteTextView.getText().toString(), lyt_progress, cv_new_dokter);
                    }
                    return true;
                }
                return false;
            }
        });

        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    itemAdapterList.clear();
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                    lnEmpty.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())&&autoCompleteTextView.getText().toString().length()>2) {
                        isFirstTime = false;
                        downloadDokter(autoCompleteTextView.getText().toString(), lyt_progress, cv_new_dokter);
                    }
                }
                return false;
            }
        });

        adapter = new AdapterListSearch(getContext(), itemAdapterList);
        listView.setAdapter(adapter);
        listView.setDivider(null);
//        listView.setEmptyView(null);

        adapter.setOnItemClickListener(new AdapterListSearch.onItemClickListener() {
            @Override
            public void onItemClick(View view, VmSearch obj, int position) {
                receivedData.onDataReceived(true, obj.getTxtTittle(), false);
                dialog.dismiss();
            }
        });
        cv_new_dokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivedData.onDataReceived(true, "", true);
                dialog.dismiss();
            }
        });
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public boolean onBackPressed() {

        return true;
    }

    public void sendData(OnReceivedData receivedData){
        this.receivedData = receivedData;
    }

    private JSONObject ParamDownloadMaster(){
        JSONObject jsonObject = new JSONObject();
        try {
            ClsmUserLogin data = new BLMain().getUserLogin(getContext());
            jsonObject.put("userId", data.getIntUserID());
            jsonObject.put("intRoleId", data.getIntRoleID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void downloadDokter(String txtKeyword, LinearLayout linearLayout, CardView cvNewDokter) {
        String strLinkAPI = new ClsHardCode().linkGetDataMasterNew;
        JSONObject resJson = new JSONObject();
        JSONObject resJsonData = new JSONObject();
        try {
            resJsonData.put("intTypeVisit", "1");
            resJsonData.put("txtTextKeyword", txtKeyword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            tokenRepo = new RepoclsToken(getContext());
            dataToken = (List<ClsToken>) tokenRepo.findAll();
            resJson.put("dataUser", ParamDownloadMaster());
            resJson.put("dataMaster", resJsonData);
            resJson.put("device_info", new ClsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();

        new FastNetworkingUtils().FNRequestPostDataSearch(getActivity(), strLinkAPI, resJson, linearLayout, cvNewDokter, new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                Intent res = null;
                if (response != null) {
                    GetDataMasterNew model = gson.fromJson(response.toString(), GetDataMasterNew.class);
                    boolean txtStatus = model.getResult().isStatus();
                    String txtMessage = model.getResult().getMessage();

                    if (txtStatus == true){
//                            dokterRepo = new mDokterRepo(getContext());
                        if (model.getIntTypeVisit()==1){
                            itemAdapterList.clear();
                            if (model.getDataDokter()!=null){
                                if (model.getDataDokter().size()>0){
                                    for (int i = 0; i < model.getDataDokter().size(); i++){
                                        VmSearch itemAdapter = new VmSearch();
                                        itemAdapter.setTxtId(model.getDataDokter().get(i).getId());
                                        if (model.getDataDokter().get(i).getLastname()!=null){
                                            itemAdapter.setTxtTittle(model.getDataDokter().get(i).getFirstname() + " " + model.getDataDokter().get(i).getLastname());
                                        }else {
                                            itemAdapter.setTxtTittle(model.getDataDokter().get(i).getFirstname());
                                        }
                                        itemAdapter.setTxtImgName(model.getDataDokter().get(i).getFirstname().toUpperCase().substring(0,1));
                                        itemAdapter.setTxtSubTittle(model.getDataDokter().get(i).getFirstname());
                                        itemAdapter.setTxtStatus(model.getDataDokter().get(i).getGender());
                                        itemAdapter.setInColorStatus(R.color.red_400);
                                        itemAdapter.setIntColor(R.color.green_300);
                                        itemAdapterList.add(itemAdapter);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
//                                autoCompleteTextView.setAdapter(adapter);
                            listView.setAdapter(adapter);
                            lnEmpty.setVisibility(View.GONE);
                        }
                        Log.d("Data info", "Success Download");

                    } else {
                        lnEmpty.setVisibility(View.VISIBLE);
//                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                    }
                }
            }

            @Override
            public void onError(ANError error) {
                new ToastCustom().showToasty(getContext(),error.getErrorDetail(),4);
            }
        });

        /*new VolleyUtils().volleyGetDataMaster(getActivity(), strLinkAPI, mRequestBody, linearLayout, cvNewDokter, new InterfaceVolleyResponseListener() {
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
                        GetDataMasterNew model = gson.fromJson(jsonObject.toString(), GetDataMasterNew.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();

                        if (txtStatus == true){
//                            dokterRepo = new mDokterRepo(getContext());
                            if (model.getIntTypeVisit()==1){
                                itemAdapterList.clear();
                                if (model.getDataDokter()!=null){
                                    if (model.getDataDokter().size()>0){
                                        for (int i = 0; i < model.getDataDokter().size(); i++){
                                            VmSearch itemAdapter = new VmSearch();
                                            itemAdapter.setTxtId(model.getDataDokter().get(i).getId());
                                            if (model.getDataDokter().get(i).getLastname()!=null){
                                                itemAdapter.setTxtTittle(model.getDataDokter().get(i).getFirstname() + " " + model.getDataDokter().get(i).getLastname());
                                            }else {
                                                itemAdapter.setTxtTittle(model.getDataDokter().get(i).getFirstname());
                                            }
                                            itemAdapter.setTxtImgName(model.getDataDokter().get(i).getFirstname().toUpperCase().substring(0,1));
                                            itemAdapter.setTxtSubTittle(model.getDataDokter().get(i).getFirstname());
                                            itemAdapter.setTxtStatus(model.getDataDokter().get(i).getGender());
                                            itemAdapter.setInColorStatus(R.color.red_400);
                                            itemAdapter.setIntColor(R.color.green_300);
                                            itemAdapterList.add(itemAdapter);
                                        }
                                    }
                                }
                                adapter.notifyDataSetChanged();
//                                autoCompleteTextView.setAdapter(adapter);
                                listView.setAdapter(adapter);
                                lnEmpty.setVisibility(View.GONE);
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            lnEmpty.setVisibility(View.VISIBLE);
//                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
    }
}
