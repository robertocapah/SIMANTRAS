package com.kalbenutritionals.simantra.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kalbenutritionals.simantra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentLoadingFinish extends Fragment {
    View v;
    Unbinder unbinder;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvExcalationTime)
    TextView tvExcalationTime;
    @BindView(R.id.tvLoadingTime)
    TextView tvLoadingTime;
    @BindView(R.id.tvDurationStart)
    TextView tvDurationStart;
    @BindView(R.id.tvDurationExca)
    TextView tvDurationExca;
    @BindView(R.id.tvDurationLoading)
    TextView tvDurationLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loading_finish, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
