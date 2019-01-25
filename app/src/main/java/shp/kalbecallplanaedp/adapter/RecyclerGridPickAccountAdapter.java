package shp.kalbecallplanaedp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import shp.kalbecallplanaedp.R;

import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/11/2018.
 */

public class RecyclerGridPickAccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    List<String> mAppList;
    private OnItemClickListener mOnNameClickListener;
    private OnImageClickListener mOnImageClickListener;
    private OnImageTrashClickListener mOnImageTrashClickListener;
    private List<Integer> drawable;

    public RecyclerGridPickAccountAdapter(Context context, List<String> mAppList, List<Integer> drawable) {
        this.mContext = mContext;
        this.mAppList = mAppList;
        this.drawable = drawable;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, String obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnNameClickListener = mItemClickListener;
    }

    public interface OnImageClickListener{
        void onItemClick(View view, final String obj, int position);
    }

    public void setOnImageClickListener(final OnImageClickListener mOnImageClickListener) {
        this.mOnImageClickListener = mOnImageClickListener;
    }

    public interface OnImageTrashClickListener{
        void onItemClick(View view, final String obj, int position);
    }

    public void setOnImageTrashClickListener(final OnImageTrashClickListener mOnImageTrashClickListener) {
        this.mOnImageTrashClickListener = mOnImageTrashClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_app, parent, false);
        int height = parent.getMeasuredHeight() / 4;
        convertView.setMinimumHeight(height);
        vh = new ViewHolder(convertView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            ViewHolder viewHolder = (ViewHolder)holder;
            final String item = mAppList.get(position);
//            Tools.displayImageOriginal(mContext, viewHolder.image_akuisisi, item.getBlobImg());
            viewHolder.iv_icon.setColorFilter(null);
            viewHolder.tv_name.setText(item);
            viewHolder.ln_pick_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnNameClickListener != null){
                        mOnNameClickListener.onItemClick(v, item, position);
                    }
                }
            });

            viewHolder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnImageClickListener != null){
                        mOnImageClickListener.onItemClick(v, item, position);
                    }
                }
            });

            viewHolder.iv_trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnImageTrashClickListener != null){
                        mOnImageTrashClickListener.onItemClick(v, item, position);
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

        ImageView iv_icon, iv_trash;
        TextView tv_name;
        LinearLayout ln_pick_account;

        public ViewHolder(View view) {
            super(view);
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            iv_trash = (ImageView) view.findViewById(R.id.iv_trash);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            ln_pick_account = (LinearLayout)view.findViewById(R.id.ln_pick_account);

//            iv_icon.setVisibility(View.GONE);
            view.setTag(this);
//            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
        }
    }
}
