package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapterPertanyaan;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/4/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    // child data in format of header title, child title
    private List<VmListItemAdapterPertanyaan> _listDataChild;
    public LinearLayout lyt_pertayaan, ll_jawaban1;
    private AdapterExpandableList.OnItemClickListener mOnItemClickListener;
    public ExpandableListAdapter(Context mContex, List<VmListItemAdapterPertanyaan> _listDataChild){
        this._context = mContex;
        this._listDataChild = _listDataChild;
    }
    @Override
    public int getGroupCount() {
        return this._listDataChild.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataChild.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(childPosition);
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
//        String title = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_section,null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
//        holder.txtSection.setText(title);
//        holder.txtSection.setTypeface(null, Typeface.BOLD);
//        if (isExpanded){
//            holder.imgArrow.setImageResource(R.mipmap.ic_arrow_up);
//        }else {
//            holder.imgArrow.setImageResource(R.mipmap.ic_arrow_black);
//        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final VmListItemAdapterPertanyaan child = (VmListItemAdapterPertanyaan) getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_expand, null);
//            convertView = View.inflate(mContext, R.layout.item_inbox, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
//        holder..setText("hahahahaha");
//        holder.email.setText(child.getTxtSubTittle());
//        holder.message.setText(inbox.message);
//        holder.date.setText(child.getTxtDate());
//        holder.image_letter.setText(child.getTxtImgName());
//        displayImage(holder, child);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {

        public ImageButton bt_expand;
        public View lyt_expand;
        public View lyt_parent;

        public ViewHolder(View view) {
            bt_expand = (ImageButton) view.findViewById(R.id.bt_expand);
            lyt_expand = (View) view.findViewById(R.id.lyt_expand);
            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
            if(lyt_pertayaan== null)lyt_pertayaan = (LinearLayout) view.findViewById(R.id.ll_pertanyaan1);
            if (ll_jawaban1 == null)ll_jawaban1 = (LinearLayout) view.findViewById(R.id.ll_jawaban1);
            view.setTag(this);
        }
    }

    private void displayImage(ViewHolder holder, VmListItemAdapterPertanyaan inbox) {
        /*if (inbox.getIntImgView() != null) {
            new Tools().displayImageRound(_context, holder.image, inbox.getIntImgView());
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.shape_circle);
            holder.image.setColorFilter(_context.getResources().getColor(inbox.getIntColor()));
            holder.image_letter.setVisibility(View.VISIBLE);
        }*/
    }
}
