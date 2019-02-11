package shp.template.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import shp.template.Data.clsHardCode;
import shp.template.Model.clsItemGroupNotifAdapter;
import shp.template.Model.clsListItemAdapter;
import shp.template.Model.clsMaintenance;
import shp.template.R;

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
