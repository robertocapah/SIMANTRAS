package com.kalbenutritionals.simantra.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterListBasicWarning;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasic;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasicIssue;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Roberto on 7/11/2019
 */
public class DialogFullscreenFragment extends DialogFragment {
    public CallbackResult callbackResult;
    @BindView(R.id.bt_close)
    ImageButton btClose;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.rvIssue)
    RecyclerView rvIssue;
    private int request_code = 0;
    Unbinder unbinder;
    AdapterListBasicWarning adapterListBasicWarning;
    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }
    Context context;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_event, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity().getApplicationContext();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        List<VmAdapterBasicIssue> listData = new ArrayList<>();
        listData = new BLHelper().getDataRejectListIssue(context);
        adapterListBasicWarning = new AdapterListBasicWarning(getActivity(),context,listData);
        rvIssue.setAdapter(adapterListBasicWarning);
        rvIssue.setLayoutManager(new LinearLayoutManager(context));
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void sendDataResult() {
        /*Event event = new Event();
        event.email = tv_email.getText().toString();
        event.name = et_name.getText().toString();
        event.location = et_location.getText().toString();
        event.from = spn_from_date.getText().toString() + " (" + spn_from_time.getText().toString() + ")";
        event.to = spn_to_date.getText().toString() + " (" + spn_to_time.getText().toString() + ")";
        event.is_allday = cb_allday.isChecked();
        event.timezone = spn_timezone.getSelectedItem().toString();*/
        Object event = "";
        if (callbackResult != null) {
            callbackResult.sendResult(request_code, event);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface CallbackResult {
        void sendResult(int requestCode, Object obj);
    }
}
