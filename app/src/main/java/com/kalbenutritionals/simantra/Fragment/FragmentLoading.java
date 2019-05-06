package com.kalbenutritionals.simantra.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.kalbenutritionals.simantra.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentLoading extends Fragment {
    View v;
    Unbinder unbinder;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvTimerLoad)
    TextView tvTimerLoad;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loading, container, false);
        unbinder = ButterKnife.bind(this, v);
        handler = new Handler();
        spinKit.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
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
            if (tvTimerLoad != null) {
                tvTimerLoad.setText(String.format("%d", Hours) + ":" + String.format("%d", Minutes) + ":"
                        + String.format("%02d", Seconds));
            } else {
                handler.removeCallbacks(runnable);
            }


            handler.postDelayed(this, 0);
        }

    };

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
                        final SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                        spinKit.setVisibility(View.VISIBLE);
                        handler.postDelayed(runnable, 0);
                        Date date = new Date(System.currentTimeMillis());
                        tvStartTime.setText(format.format(date));
                        btnStart.setText("Processing ...");
                        btnStart.setClickable(false);
                        StartTime = SystemClock.uptimeMillis();
                        btnFinish.setVisibility(View.VISIBLE);
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

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder
                .setCancelable(false)
                .setMessage("Are sure to finish loading process ?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.removeCallbacks(runnable);
                        btnFinish.setText("Finished");
                        ivDone.setVisibility(View.VISIBLE);
                        spinKit.setVisibility(View.GONE);
                        btnStart.setVisibility(View.GONE);
                        btnFinish.setClickable(false);
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
}
