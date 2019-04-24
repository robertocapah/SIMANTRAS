package com.kalbenutritionals.simantra.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalbenutritionals.simantra.R;


public class FragmentPertanyaan extends Fragment {

    public FragmentPertanyaan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transaksi_transpoter_v2, container, false);

        return root;
    }
}