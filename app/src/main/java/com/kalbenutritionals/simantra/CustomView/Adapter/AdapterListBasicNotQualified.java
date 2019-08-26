package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.Images;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasic;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasicNotQualified;
import com.kalbenutritionals.simantra.ViewModel.VmTJawabanUserDetail;

import java.util.ArrayList;
import java.util.List;

public class AdapterListBasicNotQualified extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VmAdapterBasicNotQualified> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, VmAdapterBasicNotQualified obj, int position, String txtReason);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListBasicNotQualified(Context context, List<VmAdapterBasicNotQualified> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            desc = (TextView) v.findViewById(R.id.description);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_basic_not_qualified, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            final VmAdapterBasicNotQualified p = items.get(position);
            view.title.setText(p.getTitle());
//            view.desc.setText(p.getSubtitle());
            /*view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });*/
            if (p.getTxtReason() != null && !p.getTxtReason().equals("")) {
                view.desc.setText(p.getTxtReason());
                view.desc.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bg_rounded_normal_black));
                view.desc.setTextColor(Color.BLACK);
            }
            view.desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(ctx);
                    dialog.setContentView(R.layout.editext);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.setTitle("Warning");
                    Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
                    final EditText etReason = (EditText) dialog.findViewById(R.id.etReason);
                    Activity activity = (Activity) ctx;
                    AdapterImageSlider adapterImageSlider = new AdapterImageSlider(activity, new ArrayList<Images>());
                    final ViewPager viewPager = (ViewPager) dialog.findViewById(R.id.pagerNotQualified);
                    List<Images> itemsImageNQ = new ArrayList<>();
                    for (VmTJawabanUserDetail.imageModel mdl : p.getDtImages()) {
                        Images obj = new Images();
                        obj.imgLink = mdl.imgLink;
                        obj.imgPath = mdl.imgPath;
                        itemsImageNQ.add(obj);
                    }
                    adapterImageSlider = new AdapterImageSlider(activity, itemsImageNQ);
                    adapterImageSlider.setItems(itemsImageNQ);
                    viewPager.setAdapter(adapterImageSlider);
                    viewPager.setCurrentItem(0);
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etReason.getText().toString().trim().equals("")) {
                                Toast.makeText(ctx, "Fill message first", Toast.LENGTH_SHORT).show();
                            } else {
                                String txtReason = etReason.getText().toString();
                                if (mOnItemClickListener != null) {
                                    mOnItemClickListener.onItemClick(v, items.get(position), position, txtReason);
                                    dialog.dismiss();
                                }
                            }
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}