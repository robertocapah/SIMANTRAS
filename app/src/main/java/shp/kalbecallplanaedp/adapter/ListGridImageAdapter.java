package shp.kalbecallplanaedp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import shp.kalbecallplanaedp.Model.clsListImageAdapter;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Utils.Tools;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

public class ListGridImageAdapter extends BaseAdapter {
    private Context mContext;
    List<clsListImageAdapter> mAppList;
    private OnItemClickListener mOnItemClickListener;

    ListGridImageAdapter(Context mContext, List<clsListImageAdapter> mAppList) {
        this.mContext = mContext;
        this.mAppList = mAppList;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, clsListImageAdapter obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
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
        final clsListImageAdapter item = mAppList.get(position);
        if (convertView==null){
            convertView = View.inflate(mContext, R.layout.item_grid_image_single_line, null);
            new ViewHolder(convertView);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        new Tools().displayImageOriginal(mContext, holder.image_akuisisi, item.getBlobImg());
        holder.image_akuisisi.setColorFilter(null);

        holder.image_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v, item, position);
                }
            }
        });

        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder {

        public ImageView image_akuisisi, image_trash;
        public View lyt_parent;

        public ViewHolder(View view) {
            image_akuisisi = (ImageView) view.findViewById(R.id.image_grid_akuisisi);
            image_trash = (ImageView) view.findViewById(R.id.delete_image_grid_akuisisi);

//            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
            view.setTag(this);
        }
    }

}
