package shp.template.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shp.template.Model.clsMaintenance;
import shp.template.R;
import shp.template.Utils.Tools;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/5/2018.
 */

public class AdapterListMaintenance extends BaseAdapter{
    private Context mContext;
    List<clsMaintenance> mAppLists;
    private onItemClickListener mOnItemClickListener;

    public AdapterListMaintenance(Context mContext, List<clsMaintenance> mAppList) {
        this.mContext = mContext;
        this.mAppLists = mAppList;
    }
    public void setOnItemClickListener(final onItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(View view, final clsMaintenance obj, int position);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final clsMaintenance item = mAppLists.get(position);
        if (convertView==null){
            convertView = View.inflate(mContext, R.layout.cardlist_maintenance, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
        holder.tv_outlet_name_info.setText(item.getTxtTittle());
        holder.tv_desc_infoprogram.setText(item.getTxtSubTittle());
        holder.image_letter.setText(item.getTxtImgName());
        holder.tv_status_main.setText(item.getTxtStatus());
        holder.tv_status_main.setTextColor(mContext.getResources().getColor(item.getInColorStatus()));
//        if (item.getIntStatus()==new clsHardCode().Draft){
//            holder.tv_edit_main.setVisibility(View.VISIBLE);
//        }else {
            holder.tv_edit_main.setVisibility(View.GONE);
//        }
        holder.ln_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v, item, position);
                }
            }
        });
        displayImage(holder, item);
        return convertView;
    }

    class ViewHolder {

        public TextView tv_outlet_name_info, image_letter, tv_desc_infoprogram, tv_status_main, tv_edit_main;
        public ImageView image;
        public RelativeLayout lyt_image;
        LinearLayout lnDesc, ln_maintenance;

        public ViewHolder(View view) {
            tv_desc_infoprogram = (TextView) view.findViewById(R.id.tv_desc_main);
            tv_outlet_name_info = (TextView) view.findViewById(R.id.tv_outlet_name_main);
            tv_status_main = (TextView)view.findViewById(R.id.tv_status_main);
            tv_edit_main = (TextView)view.findViewById(R.id.tv_edit_main);
            image_letter = (TextView) view.findViewById(R.id.image_letter_main);
            image = (ImageView) view.findViewById(R.id.image_main);
            lyt_image = (RelativeLayout) view.findViewById(R.id.lyt_image_main);
            lnDesc = (LinearLayout) view.findViewById(R.id.ln_desc_main);
            ln_maintenance = (LinearLayout)view.findViewById(R.id.ln_maintenance);
            view.setTag(this);
        }
    }

    private void displayImage(ViewHolder holder, clsMaintenance inbox) {
        if (inbox.getIntImgView() != null) {
            new Tools().displayImageRound(mContext, holder.image, inbox.getIntImgView());
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.shape_circle);
            holder.image.setColorFilter(mContext.getResources().getColor(inbox.getIntColor()));
            holder.image_letter.setVisibility(View.VISIBLE);
        }
    }
}
