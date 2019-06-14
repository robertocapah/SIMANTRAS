package com.kalbenutritionals.simantra.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterExpandableListNotif;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterHistoricalTransaction;
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
    private List<VmItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private HashMap<VmItemGroupNotifAdapter, List<VmListItemAdapter>> listDataChild = new HashMap<>();
    AdapterHistoricalTransaction mExpandableListAdapter;
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
            adapterData.setTxtTittle("PT Bangun Persada"+i);
            adapterData.setTxtSubTittle("12 Desc 2019 "+i);
            listDataHeader.add(adapterData);
            List<VmListItemAdapter> adapterDetail = new ArrayList<>();
            for (int j = 0; j<3; j++){
                VmListItemAdapter adapterDetailData =  new VmListItemAdapter();
                adapterDetailData.setBoolSection(true);
                adapterDetailData.setDrwImg(getActivity().getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));
                adapterDetailData.setTxtDate("B 1234 KC "+j);
                adapterDetailData.setTxtDesc("Description Roberto "+j);
                adapterDetailData.setTxtId(j+"");
                adapterDetailData.setTxtImgName("Roberto "+j);
                adapterDetailData.setTxtTittle("KNS17090048 "+j);
                adapterDetailData.setTxtSubTittle("Tj. Priok - Sunter Karya"+j );
                adapterDetail.add(adapterDetailData);
            }

            listDataChild.put(adapterData,adapterDetail);

        }
        if(listDataHeader.size()>0){
            coordinatorLytNoItem.setVisibility(View.GONE);
        }
        mExpandableListAdapter = new AdapterHistoricalTransaction(getActivity(), listDataHeader, listDataChild);
        expTransaction.setAdapter(mExpandableListAdapter);
        expTransaction.setEmptyView(v.findViewById(R.id.ln_empty));

        expTransaction.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle data = new Bundle();

//                data.putString( DT_CALL_PLAN , listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTxtId());
                FragmentTransactionDetail fragmentTransactionDetail = new FragmentTransactionDetail();
//                fragmentTransactionDetail.setArguments(data);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragmentTransactionDetail);
                fragmentTransaction.commit();
                return false;
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
