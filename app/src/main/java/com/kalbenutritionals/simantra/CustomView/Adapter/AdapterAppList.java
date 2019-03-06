package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapter;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/2/2018.
 */

public class AdapterAppList extends BaseAdapter{
    private Context mContext;
    List<VmListItemAdapter> mAppList;

    public AdapterAppList(Context mContext, List<VmListItemAdapter> mAppList){
        this.mContext = mContext;
        this.mAppList = mAppList;
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final VmListItemAdapter inbox = mAppList.get(position);
        if (convertView==null){
            if (inbox.isBoolSection()==true){
                convertView = View.inflate(mContext, R.layout.item_section, null);
                new ViewHolder(convertView);
            }else {
                convertView = View.inflate(mContext, R.layout.item_inbox, null);
                new ViewHolder(convertView);
            }
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (inbox.isBoolSection()==true){
            holder.txtSection.setText(inbox.getTxtTittle());
        }else {
            // displaying text view data
            holder.from.setText(inbox.getTxtTittle());
            holder.email.setText(inbox.getTxtSubTittle());
//        holder.message.setText(inbox.message);
            holder.date.setText(inbox.getTxtDate());
            holder.image_letter.setText(inbox.getTxtImgName());
            displayImage(holder, inbox);
        }
        return convertView;
    }

    class ViewHolder {

        public TextView from, email, message, date, image_letter, txtSection;
        public ImageView image;
        public RelativeLayout lyt_checked, lyt_image;
        public View lyt_parent;

        public ViewHolder(View view) {
            from = (TextView) view.findViewById(R.id.from);
            email = (TextView) view.findViewById(R.id.tvEmail);
            txtSection = (TextView) view.findViewById(R.id.title_section);
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

    private void displayImage(ViewHolder holder, VmListItemAdapter inbox) {
        if (inbox.getIntImgView() != null) {
            new ClsTools().displayImageRound(mContext, holder.image, inbox.getIntImgView());
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.shape_circle);
            holder.image.setColorFilter(inbox.getIntColor());
            holder.image_letter.setVisibility(View.VISIBLE);
        }
    }

//    private void toggleCheckedIcon(AdapterListInbox.ViewHolder holder, int position) {
//        if (selected_items.get(position, false)) {
//            holder.lyt_image.setVisibility(View.GONE);
//            holder.lyt_checked.setVisibility(View.VISIBLE);
//            if (current_selected_idx == position) resetCurrentIndex();
//        } else {
//            holder.lyt_checked.setVisibility(View.GONE);
//            holder.lyt_image.setVisibility(View.VISIBLE);
//            if (current_selected_idx == position) resetCurrentIndex();
//        }
//    }
}
