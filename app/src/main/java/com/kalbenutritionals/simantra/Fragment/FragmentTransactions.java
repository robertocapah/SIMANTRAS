package com.kalbenutritionals.simantra.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterExpandableListNotif;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.VmItemGroupNotifAdapter;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentTransactions extends Fragment {
    Unbinder unbinder;
    private static List<VmItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private static HashMap<VmItemGroupNotifAdapter, List<VmListItemAdapter>> listDataChild = new HashMap<>();
    AdapterExpandableListNotif mExpandableListAdapter;
    View v;
    @BindView(R.id.coordinator_lyt_no_item)
    CoordinatorLayout coordinatorLytNoItem;
    @BindView(R.id.exp_transaction)
    ExpandableListView expTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_transaction, container, false);
        unbinder = ButterKnife.bind(this, v);
        List<String> imgLink = new ArrayList<>();
        imgLink.add("http://www.livescience.com/images/i/000/065/149/original/bananas.jpg");
        imgLink.add("http://www.livescience.com/images/i/000/065/149/original/bananas.jpg");
        for (int i = 0; i<5;i++){
            VmItemGroupNotifAdapter adapterData = new VmItemGroupNotifAdapter();
            adapterData.setIntTransaksiId(i);
            adapterData.setIntColor(Color.GREEN);
            adapterData.setTxtDescTransaksi("Transaksi desc"+i);
            adapterData.setTxtImgName("Banana");
            adapterData.setTxtLinkImage(imgLink);
            adapterData.setTxtTittle("Title trans "+i);
            adapterData.setTxtSubTittle("sub title trans "+i);
            listDataHeader.add(adapterData);
            List<VmListItemAdapter> adapterDetail = new ArrayList<>();
            for (int j = 0; j<3; j++){
                VmListItemAdapter adapterDetailData =  new VmListItemAdapter();
                adapterDetailData.setBoolSection(true);
                adapterDetailData.setDrwImg(getActivity().getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));
                adapterDetailData.setTxtDate("12/02/2019 "+j);
                adapterDetailData.setTxtDesc("Description Roberto "+j);
                adapterDetailData.setTxtId(j+"");
                adapterDetailData.setTxtImgName("Roberto "+j);
                adapterDetailData.setTxtTittle("Title "+j);
                adapterDetailData.setTxtSubTittle("SubTitle "+j );
                adapterDetail.add(adapterDetailData);
            }

            listDataChild.put(adapterData,adapterDetail);

        }
        if(listDataHeader.size()>0){
            coordinatorLytNoItem.setVisibility(View.GONE);
        }
        mExpandableListAdapter = new AdapterExpandableListNotif(getActivity(), listDataHeader, listDataChild);
        expTransaction.setAdapter(mExpandableListAdapter);
        expTransaction.setEmptyView(v.findViewById(R.id.ln_empty));
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
