package shp.kalbecallplanaedp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shp.kalbecallplanaedp.Common.mSubActivity;
import shp.kalbecallplanaedp.Common.mSubSubActivity;
import shp.kalbecallplanaedp.Common.tAkuisisiHeader;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.Model.clsItemGroupNotifAdapter;
import shp.kalbecallplanaedp.Model.clsListItemAdapter;
import shp.kalbecallplanaedp.Model.clsMaintenance;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Repo.mSubActivityRepo;
import shp.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import shp.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import shp.kalbecallplanaedp.Utils.Tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/13/2018.
 */

public class AdapterListHistory extends BaseAdapter {
    private Context mContext;
    List<clsMaintenance> mAppLists;
    private static List<clsItemGroupNotifAdapter> listDataHeader = new ArrayList<>();
    private static HashMap<clsItemGroupNotifAdapter, List<clsListItemAdapter>> listDataChild = new HashMap<>();
    ExpandableListAdapterNotif mExpandableListAdapter;

    public AdapterListHistory(Context mContext, List<clsMaintenance> mAppList) {
        this.mContext = mContext;
        this.mAppLists = mAppList;
    }

    @Override
    public int getCount() {
        return mAppLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final clsMaintenance item = mAppLists.get(position);
        if (convertView==null){
            convertView = View.inflate(mContext, R.layout.list_history, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
//        holder.tv_title_section_history.setText(item.getTxtTittle());
        listDataHeader.clear();
        listDataChild.clear();
        try {
            if (item.getIntImgView()==new clsHardCode().VisitDokter||item.getIntImgView()==new clsHardCode().VisitApotek){
                List<mSubActivity> listHeader = (List<mSubActivity>) new mSubActivityRepo(mContext).findSubActivityId(item.getIntImgView(), item.getTxtId());
                if (listHeader!=null){
                    if (listHeader.size()>0){
                        int index = 0;
                        for (mSubActivity data : listHeader){
                            clsItemGroupNotifAdapter itemAdapter = new clsItemGroupNotifAdapter();
                            itemAdapter.setTxtTittle(data.getTxtName());
                            itemAdapter.setTxtImgName((data.getTxtName().substring(0,1)).toUpperCase());
                            itemAdapter.setIntColor(mContext.getResources().getColor(R.color.pink_400));
                            listDataHeader.add(itemAdapter);

                            List<clsListItemAdapter> listChildAdapter = new ArrayList<>();
                            List<tAkuisisiHeader> akusisi = (List<tAkuisisiHeader>) new tAkuisisiHeaderRepo(mContext).findByRealisasi(item.getTxtId());
                            if (akusisi.size()>0){
                                for (tAkuisisiHeader dtAkuisisi : akusisi){
                                    mSubSubActivity subSubActivity = (mSubSubActivity)new mSubSubActivityRepo(mContext).findById(dtAkuisisi.getIntSubSubActivityId()) ;
                                    clsListItemAdapter itemAdapter1 = new clsListItemAdapter();
                                    itemAdapter1.setTxtTittle(subSubActivity.getTxtName());
                                    itemAdapter1.setTxtSubTittle(dtAkuisisi.getTxtNoDoc());
//                                    itemAdapter1.setTxtDate(parseDate(child.getDtExpired()));
                                    listChildAdapter.add(itemAdapter1);
                                }
                            }
//                            List<mSubSubActivity> subSubActivities = (List<mSubSubActivity>) new tAkuisisiHeaderRepo(mContext).getIntSubDetailActivityId(akusisi.get())
//                            for (tNotification child : listChild){
//                                mSubSubActivity subSubActivity = (mSubSubActivity)new mSubSubActivityRepo(getContext()).findById(child.getIntSubDetailActivityId()) ;
//
//                            }
                            listDataChild.put(listDataHeader.get(index), listChildAdapter);
                            index++;
                        }
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mExpandableListAdapter = new ExpandableListAdapterNotif(mContext, listDataHeader, listDataChild);
        holder.expandableListView.setAdapter(mExpandableListAdapter);
//        holder.expandableListView.
//        mExpandableListView.setEmptyView(v.findViewById(R.id.ln_empty));
        return convertView;
    }

    class ViewHolder {

        public TextView tv_title_section_history;
        public ExpandableListView expandableListView;

        public ViewHolder(View view) {
//            tv_title_section_history = (TextView) view.findViewById(R.id.title_section_history);
            expandableListView = (ExpandableListView) view.findViewById(R.id.exp_list_history);
            view.setTag(this);
        }
    }

//    private void displayImage(ViewHolder holder, clsMaintenance inbox) {
//        if (inbox.getIntImgView() != null) {
//            Tools.displayImageRound(mContext, holder.image, inbox.getIntImgView());
//            holder.image.setColorFilter(null);
//            holder.image_letter.setVisibility(View.GONE);
//        } else {
//            holder.image.setImageResource(R.drawable.shape_circle);
//            holder.image.setColorFilter(inbox.getIntColor());
//            holder.image_letter.setVisibility(View.VISIBLE);
//        }
//    }
}
