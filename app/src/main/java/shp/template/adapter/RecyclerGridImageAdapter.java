package shp.template.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import shp.template.Model.clsListImageAdapter;
import shp.template.R;
import shp.template.Utils.Tools;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

public class RecyclerGridImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    List<clsListImageAdapter> mAppList;
    private OnItemClickListener mOnItemClickListener;
    private OnImageClickListener mOnImageClickListener;

    public RecyclerGridImageAdapter(Context mContext, List<clsListImageAdapter> mAppList) {
        this.mContext = mContext;
        this.mAppList = mAppList;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, clsListImageAdapter obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public interface OnImageClickListener{
        void onItemClick(View view, final clsListImageAdapter obj, int position);
    }

    public void setOnImageClickListener(final OnImageClickListener mOnImageClickListener) {
        this.mOnImageClickListener = mOnImageClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_single_line, parent, false);
        vh = new ViewHolder(convertView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            ViewHolder viewHolder = (ViewHolder)holder;
            final clsListImageAdapter item = mAppList.get(position);
            new Tools().displayImageOriginal(mContext, viewHolder.image_akuisisi, item.getBlobImg());
            viewHolder.image_akuisisi.setColorFilter(null);

            viewHolder.image_trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(v, item, position);
                    }
                }
            });

            viewHolder.image_akuisisi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnImageClickListener != null){
                        mOnImageClickListener.onItemClick(v, item, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_akuisisi, image_trash;
        public View lyt_parent;

        public ViewHolder(View view) {
            super(view);
            image_akuisisi = (ImageView) view.findViewById(R.id.image_grid_akuisisi);
            image_trash = (ImageView) view.findViewById(R.id.delete_image_grid_akuisisi);

//            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
        }
    }
}
