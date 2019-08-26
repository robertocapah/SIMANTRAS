package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.Images;

import java.util.ArrayList;
import java.util.List;

public class AdapterImageSliderCustomPathUrl extends PagerAdapter {
    private Activity act;
    private List<Images> items = new ArrayList<>();
    Context context;
    private AdapterImageSliderCustomPathUrl.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Images obj);
    }

    public void setOnItemClickListener(AdapterImageSliderCustomPathUrl.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // constructor
    public AdapterImageSliderCustomPathUrl(Activity activity, List<Images> items) {
        this.act = activity;
        this.items = items;
        this.context = activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    public Images getItem(int pos) {
        return items.get(pos);
    }

    public void setItems(List<Images> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Images o = items.get(position);
        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_slider_image, container, false);

        final LinearLayout layout_dots = v.findViewById(R.id.layout_dots);
        ImageView image = (ImageView) v.findViewById(R.id.image);
        MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
        if (o.imgLink != null && !o.imgLink.equals("")) {
            o.brief = "url";
        } else {
            o.brief = "path";
            o.imgLink = o.imgPath;
        }
        ClsTools.displayImageOriginalUrlCustom(act, image, o.image, o.imgLink);
        lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, o);
                }
            }
        });
        ViewPager vp = (ViewPager) container;

        vp.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
