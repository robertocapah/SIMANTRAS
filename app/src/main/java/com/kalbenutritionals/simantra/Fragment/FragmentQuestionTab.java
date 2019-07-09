package com.kalbenutritionals.simantra.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterListBasic;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ResponseGetQuestion;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.responsePushTransaksi.ResponsePushTransaksi;
import com.kalbenutritionals.simantra.Database.Repo.EnumTime;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.DeviceInfo;
import com.kalbenutritionals.simantra.ViewModel.UserRequest;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasic;
import com.kalbenutritionals.simantra.ViewModel.VmTJawabanUser;
import com.kalbenutritionals.simantra.ViewModel.VmTJawabanUserDetail;
import com.kalbenutritionals.simantra.ViewModel.VmTJawabanUserHeader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Unbinder;

public class FragmentQuestionTab extends Fragment {

    public FragmentQuestionTab() {
    }

    public static FragmentQuestionTab newInstance() {
        FragmentQuestionTab fragmentQuestionTab = new FragmentQuestionTab();
        return fragmentQuestionTab;
    }

    View v;
    String FRAGMENT_TAG = "";
    private View line_first, line_second;
    private ImageView image_left, image_midle, image_right;
    private TextView tvlineone, tvlinetwo, tvlinethree, tv_next, tvLabelTitle;
    private int idx_state = 0;
    private MaterialRippleLayout btnNext;
    Context context;
    Map<String, File> listMap = new HashMap<>();
    int intIsValidator;
    Unbinder unbinder;
    FragmentDetailInfoChecker myFragment;
    private enum State {
        CHECKING,
        LOADING,
        FINISH,
        UNLOADING,
        FINISHUNLOADING
    }

    private Gson gson;
    State[] array_state = new State[]{State.CHECKING, State.LOADING, State.FINISH, State.UNLOADING, State.FINISHUNLOADING};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.checker_step, container, false);
        context = getActivity().getApplicationContext();
        line_first = (View) v.findViewById(R.id.line_first);
        line_second = (View) v.findViewById(R.id.line_second);
        image_left = (ImageView) v.findViewById(R.id.image_left);
        image_midle = (ImageView) v.findViewById(R.id.image_midle);
        image_right = (ImageView) v.findViewById(R.id.image_right);

        tvLabelTitle = (TextView) v.findViewById(R.id.tvLabelTitle);
        tv_next = (TextView) v.findViewById(R.id.tvNext);
        tvlineone = (TextView) v.findViewById(R.id.tvlineone);
        tvlinetwo = (TextView) v.findViewById(R.id.tvlinetwo);
        tvlinethree = (TextView) v.findViewById(R.id.tvlinethree);
        btnNext = (MaterialRippleLayout) v.findViewById(R.id.lyt_next);

        image_midle.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);
        image_right.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);

        myFragment = (FragmentDetailInfoChecker) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

        String myValue;
        int intDesc;
        if (this.getArguments() != null) {
            myValue = this.getArguments().getString(ClsHardCode.txtMessage);
            String noSPM = this.getArguments().getString(ClsHardCode.txtNoSPM);
            intIsValidator = this.getArguments().getInt(ClsHardCode.intIsValidator, 88);
            intDesc = this.getArguments().getInt(ClsHardCode.intDesc, 99);
            if (intIsValidator == 2) {
                idx_state = 3;
                tvLabelTitle.setText("Validator Unloading");
                tvlineone.setText("Scan");
                tvlinetwo.setText("Unloading");
                tv_next.setText("Finish");
                /*switch (intDesc){
                    case 4:
                        idx_state = 4;
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                }*/
            } else {
                if (intDesc == 2 || intDesc == 3) {
                    idx_state = 1;
                }
            }
            if (myValue != null && myValue.equals(ClsHardCode.txtBundleKeyBarcodeLoad) && intIsValidator != 2) {
                idx_state = 1;
            }
            BLHelper.savePreference(context, ClsHardCode.txtNoSPMActive, noSPM);
        }
        (btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_state == array_state.length - 1 || array_state[idx_state].name().equalsIgnoreCase(State.FINISH.name())) {
                    TransactionClosed();
                } else {
                    if (array_state[idx_state].name().equalsIgnoreCase(State.CHECKING.name())) {
                        FragmentDetailInfoChecker myFragment = (FragmentDetailInfoChecker) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//                        boolean validBody = myFragment.validateAnswHeader();
                        btnNext.setClickable(false);
                        VmTJawabanUserHeader validMandatoryList = myFragment.validateAll(ClsHardCode.PUSHDATA);
//                        boolean validFooter = myFragment.validateAnswFooter();
//                        List<VmTJawabanUser> tJawabanList = myFragment.saveData();

                        if (validMandatoryList.getListJawabanUser() != null) {
                            for (VmTJawabanUser jawab :
                                    validMandatoryList.getListJawabanUser()) {
                                if (jawab.isBolHavePhoto()) {
                                    List<VmTJawabanUserDetail.imageModel> models = jawab.getJawabanUserDetailList().get(0).getDtImageModels();
                                    int index = 1;
                                    for (VmTJawabanUserDetail.imageModel model :
                                            models) {

                                        File file = new File(model.imgPath);
                                        if (file.exists()) {
                                            listMap.put(String.valueOf(jawab.getIntPertanyaanId()) + "-" + model.imgName + String.valueOf(index) + "-", file);
                                            index++;
                                        }

                                    /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                                    byte[] b = baos.toByteArray();
                                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                                    String a = encodedImage;*/
                                    }


                                }
                            }


//                            boolean validFooter = true;
//                        validMandatory = true;
                            if (validMandatoryList.getListJawabanUser().size() > 0) {
                                final Dialog dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.alert_validation);
                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                dialog.setTitle("Warning");
                                Button btnProceed = (Button) dialog.findViewById(R.id.btnProceed);
                                NestedScrollView nested_scroll_view_alert = (NestedScrollView) dialog.findViewById(R.id.nested_scroll_view_alert);
                                TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitleAlert);
                                RecyclerView rvView = (RecyclerView) dialog.findViewById(R.id.lvPoin);
                                nested_scroll_view_alert.fullScroll(ScrollView.FOCUS_UP);
                                List<VmAdapterBasic> basic = myFragment.ListRejection;
                                if (basic.size() > 0) {
//                                dialog.setTitle("Are yo");
                                    tvTitle.setText("Are you sure these item not qualified?");
//                                basic.addAll(basic);
                                    AdapterListBasic lv = new AdapterListBasic(getActivity(), basic);
                                    rvView.setAdapter(lv);
                                    rvView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    // if button is clicked, close the custom dialog

                                    btnProceed.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pushTransaction(dialog,ClsHardCode.PUSHDATA);
                                        }
                                    });

                                    dialog.show();
                                    nested_scroll_view_alert.smoothScrollTo(0,0);
                                    dialog.getWindow().setAttributes(lp);
                                } else {
                                    //disini dialog alert kalo semua terpenuhi
                                    tvTitle.setText("All mandatory conditions have been fulfilled");
                                    dialog.show();
                                    rvView.setVisibility(View.GONE);
                                    dialog.getWindow().setAttributes(lp);

                                    btnProceed.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            pushTransaction(dialog,ClsHardCode.PUSHDATA);
                                        }
                                    });
                                }
                                btnNext.setClickable(true);
                            } else {
//                            Toast.makeText(getActivity().getApplicationContext(),"Mandatory Question must be filled out !",Toast.LENGTH_SHORT).show();
                                new ToastCustom().showToasty(context, myFragment.txtMsg, 2);
                                btnNext.setClickable(true);
                            }
                        }

//                        boolean isRejected = myFragment.statusRejected;
//                        isRejected = false;

                    } else if (array_state[idx_state].name().equalsIgnoreCase(State.LOADING.name())) {
                        FragmentLoading myFragment = (FragmentLoading) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                        if (!myFragment.isFinished) {
                            new ToastCustom().showToasty(context, "Please finish timer first", 4);
                        } else {
                            idx_state++;
                            displayFragment(array_state[idx_state]);
                        }
                    } else if (array_state[idx_state].name().equalsIgnoreCase(State.FINISH.name())) {
                        idx_state++;
                        displayFragment(array_state[idx_state]);
                    } else if (array_state[idx_state].name().equalsIgnoreCase(State.UNLOADING.name())) {
                        idx_state++;
                        displayFragment(array_state[idx_state]);
                    } else if (array_state[idx_state].name().equalsIgnoreCase(State.FINISHUNLOADING.name())) {
                        idx_state++;
                        displayFragment(array_state[idx_state]);
                    }
                }
            }
        });

        (v.findViewById(R.id.lyt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (idx_state < 1) return;
//                idx_state--;
//                displayFragment(array_state[idx_state]);
                myFragment = (FragmentDetailInfoChecker) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                VmTJawabanUserHeader validMandatoryList = myFragment.saveData();

                if (validMandatoryList.getListJawabanUser() != null) {
                    for (VmTJawabanUser jawab :
                            validMandatoryList.getListJawabanUser()) {
                        if (jawab.isBolHavePhoto()) {
                            List<VmTJawabanUserDetail.imageModel> models = jawab.getJawabanUserDetailList().get(0).getDtImageModels();
                            int index = 1;
                            for (VmTJawabanUserDetail.imageModel model :
                                    models) {
                                File file = new File(model.imgPath);
                                if (file.exists()) {
                                    listMap.put(String.valueOf(jawab.getIntPertanyaanId()) + "-" + model.imgName + String.valueOf(index) + "-", file);
                                    index++;
                                }
                            }


                        }
                    }
                }

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.alert_validation);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.setTitle("Warning");

                Button btnProceed = (Button) dialog.findViewById(R.id.btnProceed);
                TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitleAlert);
                TextView titleConfirm = (TextView) dialog.findViewById(R.id.titleConfirm);
                RecyclerView rvView = (RecyclerView) dialog.findViewById(R.id.lvPoin);
                rvView.setVisibility(View.GONE);
                titleConfirm.setVisibility(View.GONE);
                btnProceed.setText("Save");
//                                dialog.setTitle("Are yo");
                tvTitle.setText("Do you want to save changes?");
//                                basic.addAll(basic);
                rvView.setLayoutManager(new LinearLayoutManager(getActivity()));
                // if button is clicked, close the custom dialog
                btnProceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pushTransaction(dialog, ClsHardCode.SAVE);
                    }
                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
        });
        displayFragment(array_state[idx_state]);

        return v;
    }

    private void pushTransaction(final Dialog dialog,final int FlagPush) {

        String txtLink = new ClsHardCode().linkSetTransactionList;
        myFragment = (FragmentDetailInfoChecker) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        JSONObject data = myFragment.getDataTransaction(FlagPush);
        JSONObject object = new JSONObject();
        DeviceInfo dataDevice = new BLHelper().getDeviceInfo();
        JSONObject deviceInfo = new BLHelper().getDataTransaksiJsonObjCommon(context, dataDevice);
        JSONObject dataUserLogin = new BLHelper().getUserLoginDataJson(context);
        try {
            object.put("dataJawaban", data);
            object.put("deviceInfo", deviceInfo);
            object.put("dataUser", dataUserLogin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String objects = object.toString();
//        JSONObject obj = new BLHelper().getDataTransaksiJson(context, data);

        new FastNetworkingUtils().FNRequestUploadListImage(getActivity(), txtLink, object.toString(), listMap, "Processing SPM", "transaktion", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponsePushTransaksi model = gson.fromJson(response.toString(), ResponsePushTransaksi.class);
                if (model.getResult() != null) {
                    if (model.getResult().isStatus()) {
                        if (FlagPush == ClsHardCode.SAVE){
                            new ToastCustom().showToasty(context,"Data Saved",1);
                            dialog.dismiss();
                        }else{

                        if (model.getResult().isStatusReject()) {
                                dialog.dismiss();
                                new ToastCustom().showToasty(context,model.getResult().getMessage().toString(),3);
                                Bundle arguments2 = new Bundle();
                                arguments2.putInt(ClsHardCode.TXT_STATUS_MENU, ClsHardCode.INT_CHECKER);
                                FragmentSPMSearch fragmentSPMSearch = new FragmentSPMSearch();
                                fragmentSPMSearch.setArguments(arguments2);
                                FragmentTransaction fragmentTransactionSPMSearch = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransactionSPMSearch.replace(R.id.frame, fragmentSPMSearch);
                                fragmentTransactionSPMSearch.commit();

                            } else {
                                idx_state++;
                                displayFragment(array_state[idx_state]);
                                dialog.dismiss();
                                btnNext.setClickable(false);
                                updateDataTimeAfterCheckFisik();
                            }
                        }


                    } else {
                        Toast.makeText(context, "server problem", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(ANError error) {

            }

        });
    }

    public void updateDataTimeAfterCheckFisik() {
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
        final Date dateFinish = new Date(System.currentTimeMillis());
        final String timeIdle = format.format(dateFinish);
        UserRequest userLogin = new BLHelper().getUserInfo(context);
        String strLinkAPI = new ClsHardCode().linksetTimeStatusTransaksiMobile;
        JSONObject jData = new JSONObject();
        String txtHeaderId = BLHelper.getPreference(context, ClsHardCode.INT_HEADER_ID);
        int intHeaderId = Integer.parseInt(txtHeaderId);
        int intStatus = EnumTime.CheckingFinish.getIdStatus();
        String txtStatus = EnumTime.CheckingFinish.name();
        JSONObject resJson = new BLHelper().getJsonParamSetTime(timeIdle, context, userLogin.intUserId, intHeaderId, intStatus, txtStatus, 2, "");

        new FastNetworkingUtils().FNRequestPostData(getActivity(), strLinkAPI, resJson, "Changing status, please wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    BLHelper.savePreference(context, ClsHardCode.IdleTime, timeIdle);
                }
            }

            @Override
            public void onError(ANError error) {
                Toast.makeText(context, error.getErrorBody().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void TransactionClosed() {
        Bundle arguments = new Bundle();
        arguments.putInt(ClsHardCode.TXT_STATUS_MENU, ClsHardCode.INT_CHECKER);
        FragmentSPMSearch fragmentSPMSearch = new FragmentSPMSearch();
        fragmentSPMSearch.setArguments(arguments);
        FragmentTransaction fragmentTransactionSPMSearch = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionSPMSearch.replace(R.id.frame, fragmentSPMSearch);
        fragmentTransactionSPMSearch.commit();
    }

    private void displayFragment(State state) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        refreshStepTitle();

        if (state.name().equalsIgnoreCase(State.CHECKING.name())) {
//            fragment = new FragmentPertanyaan();
            FRAGMENT_TAG = "Checker";
            fragment = new FragmentDetailInfoChecker();
            tvlineone.setTextColor(getResources().getColor(R.color.grey_90));
            image_left.clearColorFilter();
            tv_next.setText("Start Loading");
        } else if (state.name().equalsIgnoreCase(State.LOADING.name())) {
            FRAGMENT_TAG = "Loading";
            fragment = new FragmentLoading();
            if (this.getArguments() != null) {
                String myValue = this.getArguments().getString(ClsHardCode.txtMessage);
                String noSPM = this.getArguments().getString(ClsHardCode.txtNoSPM);
                int intStatus = this.getArguments().getInt(ClsHardCode.txtStatusLoading);
                int intDesc = this.getArguments().getInt(ClsHardCode.intDesc, 99);
                Bundle bundle = new Bundle();
                bundle.putString(ClsHardCode.txtMessage, myValue);
                bundle.putString(ClsHardCode.txtNoSPM, noSPM);
                bundle.putInt(ClsHardCode.txtStatusLoading, intStatus);
                bundle.putInt(ClsHardCode.intDesc, intDesc);
                fragment.setArguments(bundle);
            }

            line_first.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_left.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_midle.clearColorFilter();
            tvlinetwo.setTextColor(getResources().getColor(R.color.grey_90));
            tv_next.setText("Finish");

        } else if (state.name().equalsIgnoreCase(State.FINISH.name())) {
            FRAGMENT_TAG = "Finish";
            fragment = new FragmentLoadingFinish();
            line_second.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_midle.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_right.clearColorFilter();
            tvlinethree.setTextColor(getResources().getColor(R.color.grey_90));
            tv_next.setText("Close");
        } else if (state.name().equalsIgnoreCase(State.UNLOADING.name())) {
            FRAGMENT_TAG = "Unloading";
            fragment = new FragmentValidator();
            if (this.getArguments() != null) {
                String myValue = this.getArguments().getString(ClsHardCode.txtMessage);
                String noSPM = this.getArguments().getString(ClsHardCode.txtNoSPM);
                int intStatus = this.getArguments().getInt(ClsHardCode.txtStatusLoading);
                int intDesc = this.getArguments().getInt(ClsHardCode.intDesc, 99);
                Bundle bundle = new Bundle();
                bundle.putString(ClsHardCode.txtMessage, myValue);
                bundle.putString(ClsHardCode.txtNoSPM, noSPM);
                bundle.putInt(ClsHardCode.txtStatusLoading, intStatus);
                bundle.putInt(ClsHardCode.intDesc, intDesc);
                fragment.setArguments(bundle);
            }
            line_first.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_left.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_midle.clearColorFilter();
            tvlinetwo.setTextColor(getResources().getColor(R.color.grey_90));
            tv_next.setText("Finish");
        } else if (state.name().equalsIgnoreCase(State.FINISHUNLOADING.name())) {
            FRAGMENT_TAG = "FinishUnloading";
            fragment = new FragmentLoadingFinish();
            Bundle bundle = new Bundle();
            bundle.putInt(ClsHardCode.intIsValidator, intIsValidator);
            line_second.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_midle.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_right.clearColorFilter();
            tvlinethree.setTextColor(getResources().getColor(R.color.grey_90));
            tv_next.setText("Close");
        }

        if (fragment == null) return;
        fragmentTransaction.replace(R.id.frame_content, fragment, FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void refreshStepTitle() {
        tvlineone.setTextColor(getResources().getColor(R.color.grey_20));
        tvlinetwo.setTextColor(getResources().getColor(R.color.grey_20));
        tvlinethree.setTextColor(getResources().getColor(R.color.grey_20));
    }
}
