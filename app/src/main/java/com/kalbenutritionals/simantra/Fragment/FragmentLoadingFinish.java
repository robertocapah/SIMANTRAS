package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentLoadingFinish extends Fragment {
    View v;
    Unbinder unbinder;
    Context context;
    @BindView(R.id.tvScanTime)
    TextView tvScanTime;
    @BindView(R.id.tvLoadingStart)
    TextView tvLoadingStart;
    @BindView(R.id.tvLoadingEnd)
    TextView tvLoadingEnd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loading_finish, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity().getApplicationContext();
        String scanTime = BLHelper.getPreference(context, ClsHardCode.ScanTime);
        String loadTimeStart = BLHelper.getPreference(context, ClsHardCode.StartTime);
        String LoadTimeFinish = BLHelper.getPreference(context, ClsHardCode.EndTime);

        tvScanTime.setText(scanTime);
        tvLoadingStart.setText(loadTimeStart);
        tvLoadingEnd.setText(LoadTimeFinish);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
