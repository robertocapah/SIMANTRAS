package com.kalbenutritionals.simantra.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalbenutritionals.simantra.R;

public class FragmentDestinationDetail extends Fragment {
    View v ;

    public FragmentDestinationDetail(){

    }
    public static FragmentDestinationDetail newInstance(){
        FragmentDestinationDetail fragment = new FragmentDestinationDetail();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_destination_detail,container,false);
        return v;
    }
}
