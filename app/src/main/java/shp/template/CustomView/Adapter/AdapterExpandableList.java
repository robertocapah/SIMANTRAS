package shp.template.CustomView.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shp.template.Model.ClsListItemAdapter;
import shp.template.R;
import shp.template.CustomView.Utils.ClsTools;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/4/2018.
 */

public class AdapterExpandableList extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<ClsListItemAdapter>> _listDataChild;

    public AdapterExpandableList(Context mContex, List<String> _listDataHeader, HashMap<String, List<ClsListItemAdapter>> _listDataChild){
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
        String title = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_section,null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
        holder.txtSection.setText(title);
        holder.txtSection.setTypeface(null, Typeface.BOLD);
        if (isExpanded){
            holder.imgArrow.setImageResource(R.mipmap.ic_arrow_up);
        }else {
            holder.imgArrow.setImageResource(R.mipmap.ic_arrow_black);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ClsListItemAdapter child = (ClsListItemAdapter) getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_inbox, null);
//            convertView = View.inflate(mContext, R.layout.item_inbox, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
        holder.from.setText(child.getTxtTittle());
        holder.email.setText(child.getTxtSubTittle());
//        holder.message.setText(inbox.message);
        holder.date.setText(child.getTxtDate());
        holder.image_letter.setText(child.getTxtImgName());
        displayImage(holder, child);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {

        public TextView from, email, message, date, image_letter, txtSection;
        public ImageView image, imgArrow;
        public RelativeLayout lyt_checked, lyt_image;
        public View lyt_parent;

        public ViewHolder(View view) {
            from = (TextView) view.findViewById(R.id.from);
            email = (TextView) view.findViewById(R.id.email);
            txtSection = (TextView) view.findViewById(R.id.title_section);
            imgArrow = (ImageView) view.findViewById(R.id.bt_expand);
//            message = (TextView) view.findViewById(R.id.message);
            date = (TextView) view.findViewById(R.id.date);
            image_letter = (TextView) view.findViewById(R.id.image_letter);
            image = (ImageView) view.findViewById(R.id.image);
            lyt_checked = (RelativeLayout) view.findViewById(R.id.lyt_checked);
            lyt_image = (RelativeLayout) view.findViewById(R.id.lyt_image);
//            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
            view.setTag(this);
        }
    }

    private void displayImage(ViewHolder holder, ClsListItemAdapter inbox) {
        if (inbox.getIntImgView() != null) {
            new ClsTools().displayImageRound(_context, holder.image, inbox.getIntImgView());
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.shape_circle);
            holder.image.setColorFilter(_context.getResources().getColor(inbox.getIntColor()));
            holder.image_letter.setVisibility(View.VISIBLE);
        }
    }
}
