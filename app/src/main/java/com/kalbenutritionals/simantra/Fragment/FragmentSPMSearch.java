package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.Result;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ResponseGetQuestion;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.FullScannerFragmentActivity;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class FragmentSPMSearch extends Fragment implements ZXingScannerView.ResultHandler {
    View v;
    @BindView(R.id.etSpmNumber)
    EditText etSpmNumber;
    @BindView(R.id.btnGo)
    Button btnGo;
    Unbinder unbinder;
    @BindView(R.id.ivScanBarcode)
    ImageView ivScanBarcode;
    Context context;
    @BindView(R.id.bt_toggle_input)
    ImageButton btToggleInput;
    private Gson gson;
    int intStatus = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_scan_barcode, container, false);
        unbinder = ButterKnife.bind(this, v);
        if (getActivity() != null) {
            context = getActivity().getApplicationContext();
        }

        //is there any way that I can stay in your eyes
        Bundle arguments = getArguments();
        if (arguments != null) {
            intStatus = arguments.getInt(ClsHardCode.TXT_STATUS_MENU, 88);
        }
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.etSpmNumber, R.id.btnGo, R.id.ivScanBarcode})
    public void onViewClicked(View view) {
        if (getActivity() != null) {
            BLHelper.hideKeyboard(getActivity());
        }
        switch (view.getId()) {
            case R.id.etSpmNumber:
                break;
            case R.id.btnGo:
                if (etSpmNumber.getText().toString().trim().equals("")){
                    Toast.makeText(context,"Fill Document Number",Toast.LENGTH_SHORT).show();
                }else{
                    goToInfoChecker();
                }

                /*if(intStatus == ClsHardCode.INT_CHECKER){
                    goToInfoChecker();
                }else if (intStatus == ClsHardCode.INT_VALIDATOR){
                    goToValidatorUnLoading();
                }*/
                break;
            case R.id.ivScanBarcode:
                scanBarcode();
                break;
        }
    }

    private void goToInfoChecker() {
       /* FragmentDetailInfoChecker infoCheckerFragment = new FragmentDetailInfoChecker();
        FragmentTransactions fragmentTransactionInfoChecker = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionInfoChecker.replace(R.id.frame, infoCheckerFragment);
        fragmentTransactionInfoChecker.commit();*/
        String txtLink = new ClsHardCode().linkGetListFormByOrg;
        final String noQr = etSpmNumber.getText().toString().trim();
        final int intType = ClsHardCode.INT_DOCNUMB;
        int intFillHdrId = 60;
        JSONObject obj = new BLHelper().getDataRequestDataSPM(context,intType, noQr, intStatus, intFillHdrId);//validator = 2

        new FastNetworkingUtils().FNRequestPostData(getActivity(), txtLink, obj, "Processing SPM", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    if (model.getResult().isStatus()) {
                        Bundle bundle;
                        new BLHelper().GenerateData(getActivity().getApplicationContext(), model);
                        if (model.getINTDESC() < 1) {
                            bundle = new Bundle();
                            bundle.putInt(ClsHardCode.intIsValidator, intStatus);
                            new RepomPertanyaan(context).deleteAllData();
                            BLHelper.savePreference(context, "spm", noQr);// simpen spm yang lagi aktif
                            final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
                            Date dateScan = new Date(System.currentTimeMillis());
                            String timeScan = format.format(dateScan);
                            BLHelper.savePreference(context, ClsHardCode.ScanTime, timeScan); //save waktu scan
                            FragmentTab fragmentTab = new FragmentTab();
                            fragmentTab.setArguments(bundle);
                            FragmentTransaction fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                            fragmentTransactionTab.commit();
                        } else {
                            switch (model.getINTDESC()) {
                                case 1: //ini jika sudah scan blm validate lalu scan lagi
                                    bundle = new Bundle();
                                    bundle.putInt(ClsHardCode.intIsValidator, intStatus);

                                    new RepomPertanyaan(context).deleteAllData();
                                    BLHelper.savePreference(context, "spm", noQr);// simpen spm yang lagi aktif
                                    final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
                                    Date dateScan = new Date(System.currentTimeMillis());
                                    String timeScan = format.format(dateScan);
                                    BLHelper.savePreference(context, ClsHardCode.ScanTime, timeScan); //save waktu scan
                                    FragmentTab fragmentTab = new FragmentTab();
                                    fragmentTab.setArguments(bundle);
                                    FragmentTransaction fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                                    fragmentTransactionTab.commit();
                                    break;
                                case 2: // ini jika sudah scan dan validasi
                                    bundle = new Bundle();
                                    bundle.putInt(ClsHardCode.intIsValidator, intStatus);
                                    bundle.putString(ClsHardCode.txtMessage, new ClsHardCode().txtBundleKeyBarcode);
                                    bundle.putString(ClsHardCode.txtNoSPM, noQr);
                                    bundle.putInt(ClsHardCode.intDesc, model.getINTDESC());
                                    new BLHelper().saveDataTimeFromApi(model, context);
                                    fragmentTab = new FragmentTab();
                                    fragmentTab.setArguments(bundle);
                                    fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                                    fragmentTransactionTab.commit();
                                    break;
                                case 3: // ini jika sudah scan dan validasi dan start timer
                                    bundle = new Bundle();
                                    bundle.putInt(ClsHardCode.intIsValidator, intStatus);
                                    bundle.putString(ClsHardCode.txtMessage, new ClsHardCode().txtBundleKeyBarcode);
                                    bundle.putString(ClsHardCode.txtNoSPM, noQr);
                                    bundle.putInt(ClsHardCode.intDesc, model.getINTDESC());
                                    new BLHelper().saveDataTimeFromApi(model, context);
                                    fragmentTab = new FragmentTab();
                                    fragmentTab.setArguments(bundle);
                                    fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                                    fragmentTransactionTab.commit();
                                    break;
                                case 4: // ini jika sudah scan dan validasi dan start timer
                                    bundle = new Bundle();
                                    bundle.putInt(ClsHardCode.intIsValidator, intStatus);
                                    bundle.putString(ClsHardCode.txtMessage, new ClsHardCode().txtBundleKeyBarcode);
                                    bundle.putString(ClsHardCode.txtNoSPM, noQr);
                                    bundle.putInt(ClsHardCode.intDesc, model.getINTDESC());
                                    new BLHelper().saveDataTimeFromApi(model, context);
                                    fragmentTab = new FragmentTab();
                                    fragmentTab.setArguments(bundle);
                                    fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                                    fragmentTransactionTab.commit();
                                    break;
                                case 5: // ini jika sudah scan dan validasi dan start timer
                                    bundle = new Bundle();
                                    bundle.putInt(ClsHardCode.intIsValidator, intStatus);
                                    bundle.putString(ClsHardCode.txtMessage, new ClsHardCode().txtBundleKeyBarcode);
                                    bundle.putString(ClsHardCode.txtNoSPM, noQr);
                                    bundle.putInt(ClsHardCode.intDesc, model.getINTDESC());
                                    new BLHelper().saveDataTimeFromApi(model, context);
                                    fragmentTab = new FragmentTab();
                                    fragmentTab.setArguments(bundle);
                                    fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                                    fragmentTransactionTab.commit();
                                    break;
                                case 6: // ini jika sudah scan dan validasi dan start timer
                                    bundle = new Bundle();
                                    bundle.putInt(ClsHardCode.intIsValidator, intStatus);
                                    bundle.putString(ClsHardCode.txtMessage, new ClsHardCode().txtBundleKeyBarcode);
                                    bundle.putString(ClsHardCode.txtNoSPM, noQr);
                                    bundle.putInt(ClsHardCode.intDesc, model.getINTDESC());
                                    new BLHelper().saveDataTimeFromApi(model, context);
                                    fragmentTab = new FragmentTab();
                                    fragmentTab.setArguments(bundle);
                                    fragmentTransactionTab = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransactionTab.replace(R.id.frame, fragmentTab);
                                    fragmentTransactionTab.commit();
                                    break;
                            }
                        }


                    } else {
                        Toast.makeText(context, model.getResult().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(ANError error) {
                int a = 1;
            }
        });
    }

    /*private void goToValidatorUnLoading() {
     *//* FragmentDetailInfoChecker infoCheckerFragment = new FragmentDetailInfoChecker();
        FragmentTransactions fragmentTransactionInfoChecker = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionInfoChecker.replace(R.id.frame, infoCheckerFragment);
        fragmentTransactionInfoChecker.commit();*//*
        String txtLink = new ClsHardCode().linkGetListFormByOrg;
        JSONObject obj = new BLHelper().getDataRequestDataSPM(context,"KNS17090047");

        new FastNetworkingUtils().FNRequestPostData(getActivity(), txtLink, obj, "Processing SPM", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if(model.getResult()!=null){
                    if ( model.getResult().isStatus()){
//                        new RepomPertanyaan(context).deleteAllData();
//                        GenerateData(getActivity().getApplicationContext(),model);
                        FragmentValidator fragmentValidator = new FragmentValidator();
                        FragmentTransaction fragmentTransactionValidator = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransactionValidator.replace(R.id.frame, fragmentValidator);
                        fragmentTransactionValidator.commit();
                    }else{
                        Toast.makeText(context,model.getResult().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(ANError error) {
                int a = 1;
            }
        });
    }*/
    private void scanBarcode() {
//        IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
        Intent intent = new Intent(getActivity(), FullScannerFragmentActivity.class);
        intent.putExtra(ClsHardCode.txtMessage, ClsHardCode.txtBundleKeyBarcode);
        intent.putExtra(ClsHardCode.TXT_STATUS_MENU, intStatus);
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int a = 1;
        /*IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }*/
    }

    @Override
    public void handleResult(Result result) {
        int a = 1;
    }

    @OnClick(R.id.bt_toggle_input)
    public void onViewClicked() {
        etSpmNumber.setText("");
    }
}
