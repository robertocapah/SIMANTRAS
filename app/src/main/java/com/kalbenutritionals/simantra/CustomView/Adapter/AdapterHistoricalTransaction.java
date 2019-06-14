package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.VmItemGroupNotifAdapter;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dewi.oktaviani on 13/06/2019.
 */

public class AdapterHistoricalTransaction extends BaseExpandableListAdapter {
    private Context _context;
    private List<VmItemGroupNotifAdapter> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<VmItemGroupNotifAdapter, List<VmListItemAdapter>> _listDataChild;

    public AdapterHistoricalTransaction(Context mContex, List<VmItemGroupNotifAdapter> _listDataHeader, HashMap<VmItemGroupNotifAdapter, List<VmListItemAdapter>> _listDataChild){
        this._context = mContex;
        this._listDataHeader = _listDataHeader;
        this._listDataChild = _listDataChild;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final VmItemGroupNotifAdapter group = (VmItemGroupNotifAdapter) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_group,null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
        holder.title_group.setText(group.getTxtTittle());
        holder.title_group.setTypeface(null, Typeface.BOLD);
        holder.subtitle_group.setText(group.getTxtSubTittle());
        holder.image_letter.setText(group.getTxtImgName());

        if (isExpanded){
            holder.imgArrow.setImageResource(R.mipmap.ic_arrow_up);
        }else {
            holder.imgArrow.setImageResource(R.mipmap.ic_arrow_black);
        }

        displayImage(holder, group);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final VmListItemAdapter child = (VmListItemAdapter) getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_child, null);
          new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
        holder.title_child.setText(child.getTxtTittle());
        holder.subtitle_child.setText(child.getTxtSubTittle());
        holder.date_child.setText(child.getTxtDate());

        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class ViewHolder {

        public TextView image_letter, title_group, subtitle_group, title_child, subtitle_child, date_child;
        public ImageView image, imgArrow;
        public RelativeLayout lyt_checked, lyt_image;
        public View lyt_parent;

        public ViewHolder(View view) {
            imgArrow = (ImageView) view.findViewById(R.id.bt_expand);
            image_letter = (TextView) view.findViewById(R.id.image_letter);
            image = (ImageView) view.findViewById(R.id.image);
            lyt_checked = (RelativeLayout) view.findViewById(R.id.lyt_checked);
            lyt_image = (RelativeLayout) view.findViewById(R.id.lyt_image);
            title_group = (TextView) view.findViewById(R.id.title_group);
            subtitle_group =  (TextView) view.findViewById(R.id.subtitle_group);
            title_child = (TextView)view.findViewById(R.id.title_child);
            subtitle_child = (TextView)view.findViewById(R.id.subtitle_child);
            date_child = (TextView)view.findViewById(R.id.date_child);
            view.setTag(this);
        }
    }

    private void displayImage(ViewHolder holder, VmItemGroupNotifAdapter groupNotifAdapter) {
        if (groupNotifAdapter.getTxtLinkImage() != null) {
            new ClsTools().displayImageRoundUrl(_context, holder.image, groupNotifAdapter.getTxtLinkImage().get(0));
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.shape_circle);
            holder.image.setColorFilter(groupNotifAdapter.getIntColor());
            holder.image_letter.setVisibility(View.VISIBLE);
        }
    }
}
