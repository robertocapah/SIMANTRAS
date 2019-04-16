package com.kalbenutritionals.simantra.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kalbenutritionals.simantra.CustomView.Utils.OnReceivedData;
import com.kalbenutritionals.simantra.R;

/**
 * Created by dewi.oktaviani on 25/02/2019.
 */

public class FragmentSearch extends Fragment{
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);

        FragmentBottomSheetDialog fragment = new FragmentBottomSheetDialog();
        fragment.setCancelable(false);
//        fragment.sendData(FragmentSearch.this);
        fragment.show(getActivity().getSupportFragmentManager(), fragment.getTag());

        return v;
    }

//    @Override
//    public void onDataReceived(boolean isChecked, String Name, boolean isNew) {
//        Toast.makeText(getContext(), Name, Toast.LENGTH_SHORT).show();
//    }
}
