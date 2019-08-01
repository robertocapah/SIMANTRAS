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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class FragmentLoading extends Fragment {
    View v;
    Unbinder unbinder;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvResultTime)
    TextView tvResultTime;
    Handler handler;
    int Seconds, Hours, Minutes, MinutesTime, MilliSeconds;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L, TimeBuff2;
    @BindView(R.id.btnStart)
    Button btnStart;
    @BindView(R.id.spin_kit)
    SpinKitView spinKit;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.ivDone)
    ImageView ivDone;
    public boolean isFinished = false;
    Context context;
    int intStatusLoading = 0;
    @BindView(R.id.tvFinishTime)
    TextView tvFinishTime;
    @BindView(R.id.tv_label_result)
    TextView tvLabelResult;
    @BindView(R.id.btnSkip)
    Button btnSkip;
    private Gson gson;
    List<ClsmUserLogin> dataLogin = null;
    RepomUserLogin loginRepo;
    String txtUser = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loading, container, false);
        unbinder = ButterKnife.bind(this, v);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        handler = new Handler();
        spinKit.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
        context = getActivity().getApplicationContext();
        tvResultTime.setVisibility(View.GONE);
        tvLabelResult.setVisibility(View.GONE);
        try {
            loginRepo = new RepomUserLogin(context);
            dataLogin = (List<ClsmUserLogin>) loginRepo.findAll();
            if (dataLogin.size() > 0) {
                txtUser = dataLogin.get(0).getTxtUserName();
            }
        } catch (Exception ex) {

        }


        /*viewTicktockCountup = (TickTockView) v.findViewById(R.id.view_ticktock_countup);
        if (viewTicktockCountup != null) {
            viewTicktockCountup.setOnTickListener(new TickTockView.OnTickListener() {

                Date date = new Date();
                @Override
                public String getText(long timeRemaining) {
                    date.setTime(System.currentTimeMillis());
                    return format.format(date);
                }
            });
        }*/
        String myValue;
        if (this.getArguments() != null) {
            myValue = this.getArguments().getString(ClsHardCode.txtMessage);
            String noSPM = this.getArguments().getString(ClsHardCode.txtNoSPM);
            int intDesc = this.getArguments().getInt(ClsHardCode.intDesc, 99);
            if (intDesc == 2){

            }else if(intDesc ==3){
                timeContinue();
            }
            if (myValue != null && myValue.equals(ClsHardCode.txtBundleKeyBarcodeLoad)) {
                String spmActive = BLHelper.getPreference(context, ClsHardCode.SP_NoSPMActive);
                if (spmActive.equals(noSPM)) {
                    int status = this.getArguments().getInt(ClsHardCode.txtStatusLoading);
                    if (status == 0) {//status buat baca barcode untuk mengarahkan ke halaman load
                        startingTime();
                    } else if (status == 1) {
                        finishingTime();
                    }
                }
            }
        }

        return v;
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            MinutesTime = Seconds / 60;
            Minutes = Minutes % 60;
            Seconds = Seconds % 60;
            Hours = MinutesTime / 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            if (tvResultTime != null) {
                tvResultTime.setText(String.format("%d", Hours) + ":" + String.format("%d", Minutes) + ":"
                        + String.format("%02d", Seconds));
            } else {
                handler.removeCallbacks(runnable);
            }


            handler.postDelayed(this, 0);
        }

    };

    private void TransactionClosed() {
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
        String strLinkAPI = new ClsHardCode().linksetUnlockTransaksi;
        Date date = new Date(System.currentTimeMillis());
        final String time = format.format(date);
        String txtHeaderId = BLHelper.getPreference(context, ClsHardCode.SP_INT_HEADER_ID);
        int intHeaderId = Integer.parseInt(txtHeaderId);
        int intStatus = EnumTime.FinishLoading.getIdStatus();
        String txtStatus = EnumTime.FinishLoading.name();
        JSONObject resJson = new BLHelper().getJsonParamSetTime(time, context, dataLogin.get(0).getIntUserID(), intHeaderId, intStatus, txtStatus,1,"");
        new FastNetworkingUtils().FNRequestPostData(getActivity(), strLinkAPI, resJson, "Switching Transaction, please wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    if (model.getResult().isStatus()) {
                        Bundle arguments2 = new Bundle();
                        arguments2.putInt(ClsHardCode.TXT_STATUS_MENU, ClsHardCode.INT_CHECKER);
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

    private void scanBarcode() {
//        IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
        Intent intent = new Intent(getActivity(), FullScannerFragmentActivity.class);
        intent.putExtra(ClsHardCode.txtMessage, ClsHardCode.txtBundleKeyBarcodeLoad);
        intent.putExtra(ClsHardCode.txtStatusLoading, intStatusLoading);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
//        viewTicktockCountup.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        handler.removeCallbacks(runnable);
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
                        intStatusLoading = 0;
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

    public void timeContinue() {
        String startT = BLHelper.getPreference(context, ClsHardCode.SP_STARTTIME_CHECKER);
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
        int intStatus = EnumTime.StartLoading.getIdStatus();
        String txtStatus = EnumTime.StartLoading.name();
        String strLinkAPI = new ClsHardCode().linksetTimeStatusTransaksiMobile;
        JSONObject resJson = new BLHelper().getJsonParamSetTime(time, context, dataLogin.get(0).getIntUserID(), intHeaderId, intStatus, txtStatus,1,"");

        new FastNetworkingUtils().FNRequestPostData(getActivity(), strLinkAPI, resJson, "Changing status, please wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    if(model.getResult().isStatus()){
                        spinKit.setVisibility(View.VISIBLE);
                        tvStartTime.setText(time);
                        BLHelper.savePreference(context, ClsHardCode.SP_STARTTIME_CHECKER, time);
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

    public void finishingTime() {
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
        final Date dateFinish = new Date(System.currentTimeMillis());
        final String timeFinish = format.format(dateFinish);

        String strLinkAPI = new ClsHardCode().linksetTimeStatusTransaksiMobile;
        JSONObject jData = new JSONObject();
        String txtHeaderId = BLHelper.getPreference(context, ClsHardCode.SP_INT_HEADER_ID);
        int intHeaderId = Integer.parseInt(txtHeaderId);
        int intStatus = EnumTime.FinishLoading.getIdStatus();
        String txtStatus = EnumTime.FinishLoading.name();
        JSONObject resJson = new BLHelper().getJsonParamSetTime(timeFinish, context, dataLogin.get(0).getIntUserID(), intHeaderId, intStatus, txtStatus,1,"");

        new FastNetworkingUtils().FNRequestPostData(getActivity(), strLinkAPI, resJson, "Changing status, please wait", new InterfaceFastNetworking() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseGetQuestion model = gson.fromJson(response.toString(), ResponseGetQuestion.class);
                if (model.getResult() != null) {
                    if (model.getResult().isStatus()){
                        BLHelper.savePreference(context, ClsHardCode.SP_FINISHTIME_CHECKER, timeFinish);
//        handler.removeCallbacks(runnable);
                        String startT = BLHelper.getPreference(context, ClsHardCode.SP_STARTTIME_CHECKER);
                        String selisih = new BLHelper().getDataDurationString(startT,timeFinish);
                        tvResultTime.setText(selisih);
                        tvResultTime.setVisibility(View.VISIBLE);
                        tvLabelResult.setVisibility(View.VISIBLE);
                        tvStartTime.setText(startT);
                        tvFinishTime.setText(timeFinish);
                        btnFinish.setText("Finished");
                        ivDone.setVisibility(View.VISIBLE);
                        spinKit.setVisibility(View.GONE);
                        btnStart.setVisibility(View.GONE);
                        btnFinish.setClickable(false);
                        isFinished = true;
                        btnSkip.setVisibility(View.GONE);
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

    @OnClick({R.id.btnFinish, R.id.btnSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnFinish:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder
                        .setCancelable(false)
                        .setMessage("Are sure to finish loading process ?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intStatusLoading = 1;
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
                break;
            case R.id.btnSkip:
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

                break;
        }
    }
}
