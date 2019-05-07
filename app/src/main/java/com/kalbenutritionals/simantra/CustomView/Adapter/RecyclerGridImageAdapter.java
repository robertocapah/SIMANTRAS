package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.VmListImageAdapter;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

public class RecyclerGridImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    List<VmListImageAdapter> mAppList;
    private OnImageClickListener mOnImageClickListener;
//    View convertView;

    public RecyclerGridImageAdapter(Context mContext, List<VmListImageAdapter> mAppList) {
        this.mContext = mContext;
        this.mAppList = mAppList;
    }

    public interface OnImageClickListener {
        void onItemClick(View view, final VmListImageAdapter obj, int position);
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
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final VmListImageAdapter item = mAppList.get(position);
            if (item.getBmpImage() != null) {
                Bitmap mybitmap = Bitmap.createScaledBitmap(item.getBmpImage(), 400, 500, true);
                viewHolder.imageView.setImageBitmap(mybitmap);
                viewHolder.lyt_parent.setBackgroundColor(mContext.getResources().getColor(R.color.grey_3));
            }

//            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mOnImageClickListener != null) {
//                        mOnImageClickListener.onItemClick(v, item, position);
//                    }
//                }
//            });

            viewHolder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnImageClickListener != null) {
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

    class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout lyt_parent;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            lyt_parent = (LinearLayout) view.findViewById(R.id.lyt_parent);
            imageView = (ImageView) view.findViewById(R.id.img_transporter);
        }
    }
}
