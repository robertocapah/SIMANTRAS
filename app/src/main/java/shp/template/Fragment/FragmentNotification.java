package shp.template.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shp.template.CustomView.Adapter.AdapterExpandableListNotif;
import shp.template.R;
import shp.template.ViewModel.VmItemGroupNotifAdapter;
import shp.template.ViewModel.VmListItemAdapter;

/**
 * Created by ASUS on 01/11/2018.
 */
public class FragmentNotification extends Fragment {
    View v;
    ExpandableListView mExpandableListView;
    AdapterExpandableListNotif mExpandableListAdapter;
    private static List<VmItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private static HashMap<VmItemGroupNotifAdapter, List<VmListItemAdapter>> listDataChild = new HashMap<>();

    Unbinder unbinder;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.brief)
    TextView brief;
    @BindView(R.id.ln_empty)
    LinearLayout lnEmpty;
    @BindView(R.id.exp_lv_call_plan)
    ExpandableListView expLvCallPlan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_n_order, container, false);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.exp_lv_call_plan);
        unbinder = ButterKnife.bind(this, v);
        listDataHeader.clear();
        listDataChild.clear();


        mExpandableListAdapter = new AdapterExpandableListNotif(getActivity(), listDataHeader, listDataChild);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
        return v;
    }

    private String parseDate(String dateExp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }
}
