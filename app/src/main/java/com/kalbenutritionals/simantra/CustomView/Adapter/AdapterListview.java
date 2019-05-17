package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.VmBasicListView;

import java.util.List;

public class AdapterListview extends BaseAdapter {
    private Context ctx;
    List<VmBasicListView> mAppLists;
    public AdapterListview(Context ctx, List<VmBasicListView> mAppLists) {
        this.ctx = ctx;
        this.mAppLists = mAppLists;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        VmBasicListView item = mAppLists.get(position);
        if (convertView==null){
            convertView = View.inflate(ctx, R.layout.item_list_pic, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        // displaying text view data
        holder.tvNumber.setText(item.getNumber());
        holder.txtNamaPIC.setText(item.getTitle());
        holder.txtJabatanPIC.setText(item.getSubtitle());
        return convertView;
    }
    class ViewHolder {

        public TextView tvNumber, txtNamaPIC, txtJabatanPIC, tvDesc;

        public ViewHolder(View view) {
            tvNumber = (TextView) view.findViewById(R.id.txtNumberPIC);
            txtNamaPIC = (TextView) view.findViewById(R.id.txtNamaPIC);
            txtJabatanPIC = (TextView) view.findViewById(R.id.txtJabatanPIC);
            view.setTag(this);
        }
    }
}
