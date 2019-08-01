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
    @BindView(R.id.tvDurationStart)
    TextView tvDurationStart;
    @BindView(R.id.tvDurationFinish)
    TextView tvDurationFinish;
    int intIsValidator;
    @BindView(R.id.tvLabelLoadStart2)
    TextView tvLabelLoadStart2;
    @BindView(R.id.tvLabelLoadFinish2)
    TextView tvLabelLoadFinish2;
    @BindView(R.id.tvLabelloadStart)
    TextView tvLabelloadStart;
    @BindView(R.id.tvLabelloadFinish)
    TextView tvLabelloadFinish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loading_finish, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity().getApplicationContext();
        String scanTime = BLHelper.getPreference(context, ClsHardCode.SP_SCAN_TIME);
        String loadTimeStart = BLHelper.getPreference(context, ClsHardCode.SP_STARTTIME_CHECKER);
        String loadTimeFinish = BLHelper.getPreference(context, ClsHardCode.SP_FINISHTIME_CHECKER);
        String txtDurationStart = new BLHelper().getDataDurationString(scanTime, loadTimeStart);
        String txtDurationFinish = new BLHelper().getDataDurationString(loadTimeStart, loadTimeFinish);

        String scanTimeUnloading = BLHelper.getPreference(context, ClsHardCode.SP_SCANTIME_UNLOADING);
        String loadTimeStartUnloading = BLHelper.getPreference(context, ClsHardCode.SP_STARTTIME_UNLOADING);
        String loadTimeFinishUnloading = BLHelper.getPreference(context, ClsHardCode.SP_FINISHTIME_UNLOADING);
        String txtDurationStartUnloading = new BLHelper().getDataDurationString(scanTimeUnloading, loadTimeStartUnloading);
        String txtDurationFinishUnloading = new BLHelper().getDataDurationString(loadTimeStartUnloading, loadTimeFinishUnloading);


        if (this.getArguments() != null) {
            intIsValidator = this.getArguments().getInt(ClsHardCode.intIsValidator, 88);
        }
        if (intIsValidator == ClsHardCode.INT_VALIDATOR) {
            tvScanTime.setText(scanTimeUnloading);
            tvLoadingStart.setText(loadTimeStartUnloading);
            tvLoadingEnd.setText(loadTimeFinishUnloading);
            tvDurationStart.setText(txtDurationStartUnloading);
            tvDurationFinish.setText(txtDurationFinishUnloading);

            tvLabelLoadFinish2.setText("Unloading Finish");
            tvLabelloadFinish.setText("Unloading Finish");
            tvLabelloadStart.setText("Unloading Start");
            tvLabelLoadStart2.setText("Unloading Start");
        } else {
            tvScanTime.setText(scanTime);
            tvLoadingStart.setText(loadTimeStart);
            tvLoadingEnd.setText(loadTimeFinish);
            tvDurationStart.setText(txtDurationStart);
            tvDurationFinish.setText(txtDurationFinish);
        }


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
