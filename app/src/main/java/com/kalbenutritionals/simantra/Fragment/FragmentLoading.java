package com.kalbenutritionals.simantra.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.kalbenutritionals.simantra.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentLoading extends Fragment {
    View v;
    Unbinder unbinder;
    @BindView(R.id.view_ticktock_countup)
    TickTockView viewTicktockCountup;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvTimerLoad)
    TextView tvTimerLoad;
    Handler handler;
    int Seconds, Hours, Minutes, MinutesTime, MilliSeconds ;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L,TimeBuff2 ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loading, container, false);
        unbinder = ButterKnife.bind(this, v);
        handler = new Handler() ;
        final SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        viewTicktockCountup = (TickTockView) v.findViewById(R.id.view_ticktock_countup);
        if (viewTicktockCountup != null) {
            viewTicktockCountup.setOnTickListener(new TickTockView.OnTickListener() {

                Date date = new Date();
                @Override
                public String getText(long timeRemaining) {
                    date.setTime(System.currentTimeMillis());
                    return format.format(date);
                }
            });
        }
        Date date = new Date(System.currentTimeMillis());
        tvStartTime.setText(format.format(date));

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);


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

            Hours = MinutesTime /60;

            MilliSeconds = (int) (UpdateTime % 1000);
            if(tvTimerLoad!=null){
                tvTimerLoad.setText(String.format("%d",Hours)+":" + String.format("%d", Minutes) + ":"
                        + String.format("%02d", Seconds));
            }else{
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
        if (viewTicktockCountup != null) {
            viewTicktockCountup.start(c2);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        viewTicktockCountup.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        handler.removeCallbacks(runnable);
    }
}
