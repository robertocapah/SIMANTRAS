package com.kalbenutritionals.simantra.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kalbenutritionals.simantra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dewi.oktaviani on 13/06/2019.
 */

public class FragmentTransactionDetail extends Fragment {
//    @BindView(R.id.coordinator_lyt_no_item)
//    CoordinatorLayout coordinatorLytNoItem;
//    @BindView(R.id.exp_transaction)
//    ExpandableListView expTransaction;

    Unbinder unbinder;
    View v;;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_transaction_detail, container, false);
        unbinder = ButterKnife.bind(this, v);
        //detail pemeriksaan

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
