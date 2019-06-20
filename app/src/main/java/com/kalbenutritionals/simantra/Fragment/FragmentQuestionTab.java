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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterListBasic;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ResponseGetQuestion;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.DeviceInfo;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasic;
import com.kalbenutritionals.simantra.ViewModel.VmTJawabanUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ImageView image_shipping, image_payment, image_confirm;
    private TextView tv_shipping, tv_payment, tv_confirm, tv_next;
    private int idx_state = 0;
    private MaterialRippleLayout btnNext;
    Context context;
    Map<String, File> listMap = new HashMap<>();

    private enum State {
        CHECKING,
        LOADING,
        FINISH
    }

    private Gson gson;
    State[] array_state = new State[]{State.CHECKING, State.LOADING, State.FINISH};

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
        image_shipping = (ImageView) v.findViewById(R.id.image_shipping);
        image_payment = (ImageView) v.findViewById(R.id.image_payment);
        image_confirm = (ImageView) v.findViewById(R.id.image_confirm);
        tv_next = (TextView) v.findViewById(R.id.tvNext);
        tv_shipping = (TextView) v.findViewById(R.id.tv_shipping);
        tv_payment = (TextView) v.findViewById(R.id.tv_payment);
        tv_confirm = (TextView) v.findViewById(R.id.tv_confirm);
        btnNext = (MaterialRippleLayout) v.findViewById(R.id.lyt_next);

        image_payment.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);
        image_confirm.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);
        String myValue;
        if (this.getArguments() != null) {
            myValue = this.getArguments().getString(ClsHardCode.txtMessage);
            String noSPM = this.getArguments().getString(ClsHardCode.txtNoSPM);
            if(myValue!=null && myValue.equals(ClsHardCode.txtBundleKeyBarcodeLoad)){
                idx_state = 1;
                BLHelper.savePreference(context,ClsHardCode.txtNoSPMActive,noSPM);
            }
        }
        (btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_state == array_state.length - 1) {
                    TransactionClosed();
                } else {
                    if (array_state[idx_state].name().equalsIgnoreCase(State.CHECKING.name())) {
                        FragmentDetailInfoChecker myFragment = (FragmentDetailInfoChecker) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//                        boolean validBody = myFragment.validateAnswHeader();
                        btnNext.setClickable(false);
                        List<VmTJawabanUser> validMandatoryList = myFragment.validateAll();
//                        boolean validFooter = myFragment.validateAnswFooter();
//                        List<VmTJawabanUser> tJawabanList = myFragment.saveData();

                        for (VmTJawabanUser jawab :
                                validMandatoryList) {
                            if (jawab.isBolHavePhoto()) {
                                List<VmTJawabanUser.imageModel> models = jawab.getDtImageModels();
                                for (VmTJawabanUser.imageModel model :
                                        models) {

                                    File file = new File(model.imgPath);
                                    if (file.exists()){
                                        listMap.put(String.valueOf(jawab.getIntmJawabanId()),file);
                                    }

                                    /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                                    byte[] b = baos.toByteArray();
                                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                                    String a = encodedImage;*/
                                }



                            }
                            }
//                        boolean isRejected = myFragment.statusRejected;
//                        isRejected = false;
                        boolean validFooter = true;
//                        validMandatory = true;
                        if (validFooter&&validMandatoryList.size()>0) {
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.setContentView(R.layout.alert_validation);
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            dialog.setTitle("Warning");
                            Button btnSave = (Button) dialog.findViewById(R.id.btnSaveToDraft);
                            Button btnProceed = (Button) dialog.findViewById(R.id.btnProceed);
                            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitleAlert);
                            RecyclerView rvView = (RecyclerView) dialog.findViewById(R.id.lvPoin);
                            List<VmAdapterBasic> basic = myFragment.ListRejection;
                            if (basic.size()>0) {
//                                dialog.setTitle("Are yo");
                                tvTitle.setText("Are you sure these item not qualified?");
//                                basic.addAll(basic);
                                AdapterListBasic lv = new AdapterListBasic(getActivity(), basic);
                                rvView.setAdapter(lv);
                                rvView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                // if button is clicked, close the custom dialog
                                btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                btnProceed.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        idx_state++;
                                        displayFragment(array_state[idx_state]);
                                        dialog.dismiss();
                                        btnNext.setClickable(false);
                                    }
                                });

                                dialog.show();
                                dialog.getWindow().setAttributes(lp);
                            } else {
                                //disini dialog alert kalo semua terpenuhi
                                tvTitle.setText("All mandatory conditions have been fulfilled");
                                dialog.show();
                                rvView.setVisibility(View.GONE);
                                dialog.getWindow().setAttributes(lp);
                                btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                btnProceed.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pushTransaction(dialog);
                                    }
                                });
//                                idx_state++;
//                                displayFragment(array_state[idx_state]);
//                                btnNext.setClickable(false);
                            }
                            btnNext.setClickable(true);
                        } else {
//                            Toast.makeText(getActivity().getApplicationContext(),"Mandatory Question must be filled out !",Toast.LENGTH_SHORT).show();
                            new ToastCustom().showToasty(context, myFragment.txtMsg, 2);
                            btnNext.setClickable(true);
                        }
                    } else if (array_state[idx_state].name().equalsIgnoreCase(State.LOADING.name())) {
                        FragmentLoading myFragment = (FragmentLoading) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                        if (!myFragment.isFinished) {
                            new ToastCustom().showToasty(context, "Please finish timer first", 4);
                        } else {
                            idx_state++;
                            displayFragment(array_state[idx_state]);
                        }
                    } else {
                        idx_state++;
                        displayFragment(array_state[idx_state]);
                    }
                }
            }
        });

        (v.findViewById(R.id.lyt_previous)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_state < 1) return;
                idx_state--;
                displayFragment(array_state[idx_state]);
            }
        });
        displayFragment(array_state[idx_state]);

        return v;
    }

    private void pushTransaction(final Dialog dialog) {
        FragmentDetailInfoChecker myFragment = (FragmentDetailInfoChecker) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        String txtLink = new ClsHardCode().linkSetTransactionList;
        JSONArray data = myFragment.getDataTransaction();
        JSONObject object = new JSONObject();
        DeviceInfo dataDevice = new BLHelper().getDeviceInfo();
        JSONObject deviceInfo = new BLHelper().getDataTransaksiJsonObjCommon(context,dataDevice);
        try {
            object.put("dataJawaban",data);
            object.put("deviceInfo",deviceInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        JSONObject obj = new BLHelper().getDataTransaksiJson(context, data);

        new FastNetworkingUtils().FNRequestUploadListImage(getActivity(), txtLink, object.toString(),listMap, "Processing SPM","transaktion", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    idx_state++;
                    displayFragment(array_state[idx_state]);
                    dialog.dismiss();
                    btnNext.setClickable(false);
                }
            }

            @Override
            public void onError(ANError error) {

            }
            /*@Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    idx_state++;
                    displayFragment(array_state[idx_state]);
                    dialog.dismiss();
                    btnNext.setClickable(false);
                }
            }

            @Override
            public void onError(ANError error) {
                int a = 1;
                idx_state++;
                displayFragment(array_state[idx_state]);
                dialog.dismiss();
                btnNext.setClickable(false);
            }*/
        });
    }

    private void TransactionClosed() {
        FragmentSPMSearch fragmentSPMSearch = new FragmentSPMSearch();
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
            tv_shipping.setTextColor(getResources().getColor(R.color.grey_90));
            image_shipping.clearColorFilter();
            tv_next.setText("Start Loading");
        } else if (state.name().equalsIgnoreCase(State.LOADING.name())) {
            FRAGMENT_TAG = "Loading";
            fragment = new FragmentLoading();

            if (this.getArguments()!=null){
                String myValue = this.getArguments().getString(ClsHardCode.txtMessage);
                String noSPM = this.getArguments().getString(ClsHardCode.txtNoSPM);
                int intStatus = this.getArguments().getInt(ClsHardCode.txtStatusLoading);
                Bundle bundle = new Bundle();
                bundle.putString(ClsHardCode.txtMessage, myValue);
                bundle.putString(ClsHardCode.txtNoSPM, noSPM);
                bundle.putInt(ClsHardCode.txtStatusLoading,intStatus);
                fragment.setArguments(bundle);
            }

            line_first.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_shipping.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_payment.clearColorFilter();
            tv_payment.setTextColor(getResources().getColor(R.color.grey_90));
            tv_next.setText("Finish");

        } else if (state.name().equalsIgnoreCase(State.FINISH.name())) {
            FRAGMENT_TAG = "Finish";
            fragment = new FragmentLoadingFinish();
            line_second.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            image_payment.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            image_confirm.clearColorFilter();
            tv_confirm.setTextColor(getResources().getColor(R.color.grey_90));
            tv_next.setText("Close");
        }

        if (fragment == null) return;
        fragmentTransaction.replace(R.id.frame_content, fragment, FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void refreshStepTitle() {
        tv_shipping.setTextColor(getResources().getColor(R.color.grey_20));
        tv_payment.setTextColor(getResources().getColor(R.color.grey_20));
        tv_confirm.setTextColor(getResources().getColor(R.color.grey_20));
    }
}
