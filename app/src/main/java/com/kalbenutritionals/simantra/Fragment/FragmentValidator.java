package com.kalbenutritionals.simantra.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.Result;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ResponseGetQuestion;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.EnumTime;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.FullScannerFragmentActivity;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class FragmentValidator extends Fragment implements ZXingScannerView.ResultHandler {

    Unbinder unbinder;
    Context context;
    View v;
    Handler handler;
    int Seconds, Hours, Minutes, MinutesTime, MilliSeconds;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    boolean isFinished = false;
    @BindView(R.id.btnStart)
    Button btnStart;
    @BindView(R.id.spin_kit)
    SpinKitView spinKit;
    @BindView(R.id.ivDone)
    ImageView ivDone;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvFinishTime)
    TextView tvFinishTime;
    @BindView(R.id.tv_label_result)
    TextView tvLabelResult;
    @BindView(R.id.tvResultTime)
    TextView tvResultTime;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.btnSkip)
    Button btnDoOtherTrans;
    private Gson gson;
    List<ClsmUserLogin> dataLogin = null;
    RepomUserLogin loginRepo;
    String txtUser = "";
    int intStatusUnloading = 0;
    String txtMessageFinal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static FragmentValidator newInstance() {
        FragmentValidator fragment = new FragmentValidator();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loading_validator, container, false);
        unbinder = ButterKnife.bind(this, v);
        handler = new Handler();
        btnFinish.setVisibility(View.GONE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        handler = new Handler();
        spinKit.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
        context = getActivity().getApplicationContext();
        tvResultTime.setVisibility(View.GONE);
        tvLabelResult.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Validator");
//        changeTimeStatusScanUnloading();
        try {
            loginRepo = new RepomUserLogin(context);
            dataLogin = (List<ClsmUserLogin>) loginRepo.findAll();
            if (dataLogin.size() > 0) {
                txtUser = dataLogin.get(0).getTxtUserName();
            }
        } catch (Exception ex) {

        }
        String myValue;
        if (this.getArguments() != null) {
            myValue = this.getArguments().getString(ClsHardCode.txtMessage);
            String noSPM = this.getArguments().getString(ClsHardCode.txtNoSPM);
            int intDesc = this.getArguments().getInt(ClsHardCode.intDesc, 99);
            if (intDesc == 6) {
                timeContinue();
            }
            if (myValue != null && myValue.equals(ClsHardCode.txtBundleKeyBarcodeLoad)) {
                String qrActive = BLHelper.getPreference(context, ClsHardCode.SP_QRCodeActive);
                if (qrActive.equals(noSPM)) {
//                    int status = this.getArguments().getInt(ClsHardCode.txtStatusLoading);
                    String txtStatus = BLHelper.getPreference(context, ClsHardCode.txtStatusUnloading);
                    if (txtStatus.equals("0")) {
                        startingTime();
                    } else if (txtStatus.equals("1")) {
                        finishingTime();
                    }
                    /*if (status == 0) {//status buat baca barcode untuk mengarahkan ke halaman load
                        startingTime();
                    } else if (status == 1) {
                        finishingTime();
                    }*/
                }
            }
        }
        btnDoOtherTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilderSkp = new AlertDialog.Builder(getActivity());
                alertDialogBuilderSkp
                        .setCancelable(false)
                        .setMessage("Are you sure want to switch transaction ?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TransactionClosed();
                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                final AlertDialog alertDSkp = alertDialogBuilderSkp.create();
                alertDSkp.show();

            }
        });
        return v;
    }

    private void TransactionClosed() {
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
        String strLinkAPI = new ClsHardCode().linksetUnlockTransaksi;
        Date date = new Date(System.currentTimeMillis());
        final String time = format.format(date);
        String txtHeaderId = BLHelper.getPreference(context, ClsHardCode.SP_INT_HEADER_ID);
        int intHeaderId = Integer.parseInt(txtHeaderId);
        int intStatus = EnumTime.FinishUnloading.getIdStatus();
        String txtStatus = EnumTime.FinishUnloading.name();
        JSONObject resJson = new BLHelper().getJsonParamSetTime(time, context, dataLogin.get(0).getIntUserID(), intHeaderId, intStatus, txtStatus, 1, "");
        new FastNetworkingUtils().FNRequestPostData(getActivity(), strLinkAPI, resJson, "Switching Transaction, please wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    if (model.getResult().isStatus()) {
                        Bundle arguments2 = new Bundle();
                        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                        toolbar.setTitle("Validator");
                        arguments2.putInt(ClsHardCode.TXT_STATUS_MENU, ClsHardCode.INT_VALIDATOR);
                        FragmentSPMSearch fragmentSPMSearch = new FragmentSPMSearch();
                        fragmentSPMSearch.setArguments(arguments2);
                        FragmentTransaction fragmentTransactionSPMSearch = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransactionSPMSearch.replace(R.id.frame, fragmentSPMSearch);
                        fragmentTransactionSPMSearch.commit();
                    }
                }
            }

            @Override
            public void onError(ANError error) {
                Toast.makeText(context, error.getErrorBody().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*public void changeTimeStatusScanUnloading(){
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
        final Date dateFinish = new Date(System.currentTimeMillis());
        final String timeFinish = format.format(dateFinish);

        String strLinkAPI = new ClsHardCode().linksetTimeStatusTransaksiMobile;
        JSONObject jData = new JSONObject();
        String txtHeaderId = BLHelper.getPreference(context, ClsHardCode.SP_INT_HEADER_ID);
        int intHeaderId = Integer.parseInt(txtHeaderId);
        int intStatus = EnumTime.ScanBarcodeUnloading.getIdStatus();
        String txtStatus = EnumTime.ScanBarcodeUnloading.name();
        JSONObject resJson = new BLHelper().getJsonParamSetTime(timeFinish, context, dataLogin.get(0).getIntUserID(), intHeaderId, intStatus, txtStatus,1,"");

        new FastNetworkingUtils().FNRequestPostData(getActivity(), strLinkAPI, resJson, "Changing status, please wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {

                }
            }

            @Override
            public void onError(ANError error) {
                Toast.makeText(context, error.getErrorBody().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    public void finishingTime() {
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
        final Date dateFinish = new Date(System.currentTimeMillis());
        final String timeFinish = format.format(dateFinish);

        String strLinkAPI = new ClsHardCode().linksetTimeStatusTransaksiMobile;
        JSONObject jData = new JSONObject();
        String txtHeaderId = BLHelper.getPreference(context, ClsHardCode.SP_INT_HEADER_ID);
        int intHeaderId = Integer.parseInt(txtHeaderId);
        int intStatus = EnumTime.FinishUnloading.getIdStatus();
        String txtStatus = EnumTime.FinishUnloading.name();
        String msgFinal = BLHelper.getPreference(context,ClsHardCode.SP_FINISH_MESSAGE_UNLOADING);
        JSONObject resJson = new BLHelper().getJsonParamSetTime(timeFinish, context, dataLogin.get(0).getIntUserID(), intHeaderId, intStatus, txtStatus, 1, msgFinal);

        new FastNetworkingUtils().FNRequestPostData(getActivity(), strLinkAPI, resJson, "Changing status, please wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    if(model.getResult().isStatus()){
                        BLHelper.savePreference(context, ClsHardCode.SP_FINISHTIME_UNLOADING, timeFinish);
//        handler.removeCallbacks(runnable);
                        String startTUnload = BLHelper.getPreference(context, ClsHardCode.SP_STARTTIME_UNLOADING);
                        String selisih = new BLHelper().getDataDurationString(startTUnload, timeFinish);
                        tvResultTime.setText(selisih);
                        tvResultTime.setVisibility(View.VISIBLE);
                        tvLabelResult.setVisibility(View.VISIBLE);
                        tvStartTime.setText(startTUnload);
                        tvFinishTime.setText(timeFinish);
                        btnFinish.setText("Finished");
                        ivDone.setVisibility(View.VISIBLE);

                        spinKit.setVisibility(View.GONE);
                        btnStart.setVisibility(View.GONE);
                        btnDoOtherTrans.setVisibility(View.GONE);
                        btnFinish.setClickable(false);
                        isFinished = true;
                    }else{
                        Toast.makeText(context,model.getResult().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onError(ANError error) {
                Toast.makeText(context, error.getErrorBody().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void timeContinue() {
        String startT = BLHelper.getPreference(context, ClsHardCode.SP_STARTTIME_UNLOADING);
        tvStartTime.setText(startT);
        btnStart.setText("Processing ...");
        spinKit.setVisibility(View.VISIBLE);
        btnStart.setClickable(false);
        StartTime = SystemClock.uptimeMillis();
        btnFinish.setVisibility(View.VISIBLE);
    }

    public void startingTime() {
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);

//        handler.postDelayed(runnable, 0);
        Date date = new Date(System.currentTimeMillis());
        final String time = format.format(date);
        String txtHeaderId = BLHelper.getPreference(context, ClsHardCode.SP_INT_HEADER_ID);
        int intHeaderId = Integer.parseInt(txtHeaderId);
        int intStatus = EnumTime.StartUnloading.getIdStatus();
        String txtStatus = EnumTime.StartUnloading.name();
        String strLinkAPI = new ClsHardCode().linksetTimeStatusTransaksiMobile;

        JSONObject resJson = new BLHelper().getJsonParamSetTime(time, context, dataLogin.get(0).getIntUserID(), intHeaderId, intStatus, txtStatus, 1, "");

        new FastNetworkingUtils().FNRequestPostData(getActivity(), strLinkAPI, resJson, "Changing status, please wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    if(model.getResult().isStatus()){
                        spinKit.setVisibility(View.VISIBLE);
                        tvStartTime.setText(time);
                        BLHelper.savePreference(context, ClsHardCode.SP_STARTTIME_UNLOADING, time);
                        btnStart.setText("Processing ...");
                        btnStart.setClickable(false);
                        StartTime = SystemClock.uptimeMillis();
                        btnFinish.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(context,model.getResult().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onError(ANError error) {
                Toast.makeText(context, error.getErrorBody().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void handleResult(Result result) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.HOUR, 20);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        /*if (viewTicktockCountup != null) {
            viewTicktockCountup.start(c2);
        }*/
    }

    @Override
    public void onStop() {
        super.onStop();
//        viewTicktockCountup.stop();
    }

    @OnClick(R.id.btnStart)
    public void onViewClicked() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder
                .setCancelable(false)
                .setMessage("Are sure to start loading process ?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BLHelper.savePreference(context, ClsHardCode.txtStatusUnloading, "0");
                        intStatusUnloading = 0;
                        scanBarcode();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    @OnClick(R.id.btnFinish)
    public void onViewBtnFinishClicked() {
        if (isFinished) {
            Bundle arguments2 = new Bundle();
            arguments2.putInt(ClsHardCode.TXT_STATUS_MENU, ClsHardCode.INT_VALIDATOR);
            FragmentSPMSearch fragmentSPMSearch2 = new FragmentSPMSearch();
            fragmentSPMSearch2.setArguments(arguments2);
            FragmentTransaction fragmentTransactionSPMSearch2 = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransactionSPMSearch2.replace(R.id.frame, fragmentSPMSearch2);
            fragmentTransactionSPMSearch2.commit();
        } else {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = this.getLayoutInflater();
            final View viewp = inflater.inflate(R.layout.alert_message_finish_unload, null);
            alertDialogBuilder.setView(viewp);
            alertDialogBuilder
                    .setCancelable(false)
                    .setMessage("Are sure to finish loading process ?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            final AlertDialog alertD = alertDialogBuilder.create();
            alertD.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button b = alertD.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // TODO Do something
                            EditText etMessage = viewp.findViewById(R.id.etMessage);
                            String msg = etMessage.getText().toString();
                            if (msg.trim().equals("")){
                                new ToastCustom().showToasty(context,"Fill message first",2);
                            }else{
                                BLHelper.savePreference(context,ClsHardCode.SP_FINISH_MESSAGE_UNLOADING,msg);
//                                txtMessageFinal = msg;
                                intStatusUnloading = 1;
                                BLHelper.savePreference(context, ClsHardCode.txtStatusUnloading, "1");
                                scanBarcode();
                            }
                        }
                    });
                }
            });
            alertD.show();

        }

    }

    private void scanBarcode() {
//        IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
        Intent intent = new Intent(getActivity(), FullScannerFragmentActivity.class);
        intent.putExtra(ClsHardCode.txtMessage, ClsHardCode.txtBundleKeyBarcodeLoad);
        intent.putExtra(ClsHardCode.txtMessageUnload, 1);
        intent.putExtra(ClsHardCode.TXT_STATUS_MENU, ClsHardCode.INT_VALIDATOR);
        intent.putExtra(ClsHardCode.txtStatusLoading, intStatusUnloading);
        startActivity(intent);
    }
}
