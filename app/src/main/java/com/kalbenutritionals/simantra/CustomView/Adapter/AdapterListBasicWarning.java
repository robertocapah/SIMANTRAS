package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsImages;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.Images;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasic;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasicIssue;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterListBasicWarning extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VmAdapterBasicIssue> items = new ArrayList<>();
    Activity activity;
    List<Images> itemsImagePu;
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    AdapterImageSlider adapterImageSlider;

    public interface OnItemClickListener {
        void onItemClick(View view, VmAdapterBasicIssue obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListBasicWarning(Activity activity, Context context, List<VmAdapterBasicIssue> items) {
        this.items = items;
        this.ctx = context;
        this.activity = activity;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView title, reason;
        public TextView desc;
        public View lyt_parent;
        public Button btnIssue, btnFixed;

        public OriginalViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            desc = (TextView) v.findViewById(R.id.description);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            btnIssue = (Button) v.findViewById(R.id.btnIssue);
            btnFixed = (Button) v.findViewById(R.id.btnFixed);
            reason = (TextView) v.findViewById(R.id.reason);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_basic_warning, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            final VmAdapterBasicIssue p = items.get(position);
            view.title.setText(p.getIssue());
            view.desc.setText(p.getIssueReason());
            view.reason.setText(p.getFixReason());
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
            view.btnFixed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImage(activity, p, position, ClsHardCode.INT_IMAGE_FIX);
                }
            });
            view.btnIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImage(activity, p, position,ClsHardCode.INT_IMAGE_ISSUE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void showDialogImageCenter() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_image_center);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
    }
    public void showImage(Activity context,final VmAdapterBasicIssue p, int position, int intType) {
        List<ClsImages> linkImages = new ArrayList<>();

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
//        dialog.setContentView(R.layout.dialog_pager_image);
        dialog.setContentView(R.layout.dialog_image_center);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("Pick a resource");
        final TextView ppPertanyaan = dialog.findViewById(R.id.ppPertanyaan);
        final TextView ppJawaban = dialog.findViewById(R.id.ppJawaban);
        ImageButton ppBtnClosed = dialog.findViewById(R.id.ppBtnClosed);
        ImageView ivCheklis = dialog.findViewById(R.id.ivCheklis);
        final LinearLayout layout_dots = dialog.findViewById(R.id.layout_dots);
        ppBtnClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (intType == ClsHardCode.INT_IMAGE_FIX){
            linkImages = p.getTxtLinkImageFixed();
            if (linkImages.size()>0){
                String[] separated = linkImages.get(0).getTxtDesc().split(":");
                if(separated.length>1){
                    ppPertanyaan.setText(separated[1]);
                    ppJawaban.setText(separated[0]);
                }
            }
            ivCheklis.setImageResource(R.drawable.ic_check_green_24dp);
        }else{
            linkImages = p.getTxtLinkImageIssue();
            ppPertanyaan.setText(p.getIssue());
            ppJawaban.setText(p.getJawaban());
        }
        ViewPager viewPager = (ViewPager) dialog.findViewById(R.id.pager);
        itemsImagePu = new ArrayList<>();
        for (ClsImages img :
                linkImages) {
            Images obj = new Images();
            obj.imgLink = img.txtLinkImages;
            obj.brief = img.txtDesc;
            itemsImagePu.add(obj);
        }
        adapterImageSlider = new AdapterImageSlider(context, itemsImagePu);
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
        viewPager.setAdapter(adapterImageSlider);
        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                addBottomDots(layout_dots, adapterImageSlider.getCount(), position);
                Images img = adapterImageSlider.getItem(position);
                String[] separated = img.brief.split(":");
                if(separated.length>1){
                    ppPertanyaan.setText(separated[1]);
                    ppJawaban.setText(separated[0]);
                }
            }
        });
        dialog.show();
    }
    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(ctx);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 0, 10, 0);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            dots[i].setColorFilter(ContextCompat.getColor(ctx, R.color.grey_40), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
            dots[current].setColorFilter(ContextCompat.getColor(ctx, R.color.grey_40), PorterDuff.Mode.SRC_ATOP);
        }
    }
}