package shp.template.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shp.template.Common.tMaintenanceHeader;
import shp.template.R;

import java.util.List;

/**
 * Created by ASUS on 01/11/2018.
 */

public class AdapterListNOrder extends RecyclerView.Adapter<AdapterListNOrder.ViewHolder> {
    private Context ctx;
    private List<tMaintenanceHeader> items;
    private OnClickListener onClickListener = null;
    private SparseBooleanArray selected_items;
    private int current_selected_idx = -1;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_norder, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnClickListener {
        void onItemClick(View view, tMaintenanceHeader obj, int pos);

        void onItemLongClick(View view, tMaintenanceHeader obj, int pos);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView category, activityDesc, statusVerify, date, image_letter;
        public ImageView image;
        public RelativeLayout lyt_checked, lyt_image;
        public View lyt_parent;

        public ViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.Category);
            activityDesc = (TextView) view.findViewById(R.id.ActivityDescription);
            statusVerify = (TextView) view.findViewById(R.id.statusVerify);
            date = (TextView) view.findViewById(R.id.dateRange);
            image_letter = (TextView) view.findViewById(R.id.image_letter);
            image = (ImageView) view.findViewById(R.id.image);
            lyt_checked = (RelativeLayout) view.findViewById(R.id.lyt_checked);
            lyt_image = (RelativeLayout) view.findViewById(R.id.lyt_image);
            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
        }
    }
}
