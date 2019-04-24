package com.kalbenutritionals.simantra.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.kalbenutritionals.simantra.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentLoading extends Fragment {
    View v;
    TickTockView view_ticktock_countup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loading,container,false);
        view_ticktock_countup = (TickTockView) v.findViewById(R.id.view_ticktock_countup);
        if (view_ticktock_countup != null) {
            view_ticktock_countup.setOnTickListener(new TickTockView.OnTickListener() {
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                Date date = new Date();
                @Override
                public String getText(long timeRemaining) {
                    date.setTime(System.currentTimeMillis());
                    return format.format(date);
                }
            });
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Calendar c2= Calendar.getInstance();
        c2.add(Calendar.HOUR, 20);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        if (view_ticktock_countup != null) {
            view_ticktock_countup.start(c2);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        view_ticktock_countup.stop();
    }
}
