package shp.template.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shp.template.Data.clsHardCode;
import shp.template.Model.clsInfoProgram;
import shp.template.R;
import shp.template.Utils.Tools;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

public class AdapterListInfoProgram extends BaseAdapter {
    private Context mContext;
    List<clsInfoProgram> mAppList;
    private onItemClickListener mOnItemClickListener;
    private onCheckboxClickListener mOnCheckboxClickListener;

    public AdapterListInfoProgram(Context mContext, List<clsInfoProgram> mAppList) {
        this.mContext = mContext;
        this.mAppList = mAppList;
    }

    public void setOnCheckboxClickListener(final onCheckboxClickListener mOnCheckboxClickListener) {
        this.mOnCheckboxClickListener = mOnCheckboxClickListener;
    }

    public interface onCheckboxClickListener{
        void onItemClick(View view, final clsInfoProgram obj, int position);
    }

    public void setOnItemClickListener(final onItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(View view, final clsInfoProgram obj, int position);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final clsInfoProgram item = mAppList.get(position);
        if (convertView==null){
            convertView = View.inflate(mContext, R.layout.cardlist_infoprogram, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
        if (item.isFromHistory()){
            holder.cb_done_info.setEnabled(false);
            if (item.isChecked()){
                holder.cb_done_info.setChecked(true);
            }else {
                holder.cb_done_info.setEnabled(true);
            }
        }else {
            if (item.isChecked()){
                holder.cb_done_info.setChecked(true);
                holder.cb_done_info.setEnabled(false);
            }else {
                holder.cb_done_info.setEnabled(true);
                holder.cb_done_info.setChecked(false);
            }
        }

        if (item.getIntFlagContent()==new clsHardCode().AllInfo){
            holder.lnFile.setVisibility(View.VISIBLE);
            holder.lnDesc.setVisibility(View.VISIBLE);
        }else if (item.getIntFlagContent()==new clsHardCode().OnlyDesc){
            holder.lnDesc.setVisibility(View.VISIBLE);
            holder.lnFile.setVisibility(View.GONE);
        }else if (item.getIntFlagContent()==new clsHardCode().OnlyFile){
            holder.lnFile.setVisibility(View.VISIBLE);
            holder.lnDesc.setVisibility(View.GONE);
        }
        holder.tv_outlet_name_info.setText(item.getTxtTittle());
        holder.tv_desc_infoprogram.setText(item.getTxtSubTittle());
        holder.tv_file_attach_info.setText(item.getTxtDesc());
        holder.image_letter.setText(item.getTxtImgName());
        holder.tv_status_info.setText(item.getTxtStatus());
        holder.tv_status_info.setTextColor(mContext.getResources().getColor(item.getIntColorStatus()));
        holder.tv_file_attach_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v, item, position);
                }
            }
        });

        holder.cb_done_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCheckboxClickListener != null){
                    mOnCheckboxClickListener.onItemClick(v, item, position);
                }
            }
        });
        displayImage(holder, item);
        return convertView;
    }

    class ViewHolder {

        public TextView tv_outlet_name_info, image_letter, tv_desc_infoprogram, tv_file_attach_info, tv_status_info;
        public ImageView image;
        public RelativeLayout lyt_image;
        public CheckBox cb_done_info;
        LinearLayout lnDesc, lnFile;

        public ViewHolder(View view) {
            tv_file_attach_info = (TextView) view.findViewById(R.id.tv_file_attach_info);
            tv_desc_infoprogram = (TextView) view.findViewById(R.id.tv_desc_infoprogram);
            tv_outlet_name_info = (TextView) view.findViewById(R.id.tv_outlet_name_info);
            tv_status_info = (TextView)view.findViewById(R.id.tv_status_info);
            image_letter = (TextView) view.findViewById(R.id.image_letter_info);
            image = (ImageView) view.findViewById(R.id.image_info);
            lyt_image = (RelativeLayout) view.findViewById(R.id.lyt_image_info);
            cb_done_info = (CheckBox) view.findViewById(R.id.cb_done_info);
            lnDesc = (LinearLayout) view.findViewById(R.id.ln_desc_info);
            lnFile = (LinearLayout)view.findViewById(R.id.ln_file_info);
            view.setTag(this);
        }
    }

    private void displayImage(ViewHolder holder, clsInfoProgram inbox) {
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
