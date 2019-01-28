package shp.kalbecallplanaedp.Fragment;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import shp.kalbecallplanaedp.Common.mApotek;
import shp.kalbecallplanaedp.Common.mDokter;
import shp.kalbecallplanaedp.Common.tAkuisisiDetail;
import shp.kalbecallplanaedp.Common.tAkuisisiHeader;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.ImageViewerActivity;
import shp.kalbecallplanaedp.Model.Image;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Repo.mApotekRepo;
import shp.kalbecallplanaedp.Repo.mDokterRepo;
import shp.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import shp.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import shp.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImageCustom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/5/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentSubAkuisisi extends Fragment {
    View v;
    private ListView mListView;
    //    private String txtSubAkuisisi;
    private int intTypeSubSubId;
    //    int intSubSubId;
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private String ZOOM_IMAGE = "zoom image";
    private String ZOOM_DIRECTORY = "zoom directory";
    private List<tAkuisisiDetail> dtDetail = new ArrayList<>();
    private tAkuisisiHeader dtHeader;
    //    private tAkuisisiHeader dtHeader = new tAkuisisiHeader();
    tAkuisisiDetailRepo detailRepo;
    tAkuisisiHeaderRepo headerRepo;
    private TextView tvNoDoc, tvExpDate, tvOutlet, tvUserName, tvDesc_akuisisi, tv_image, tv_status_info, tv_status_Akusisi;
    ImageView image;
    LinearLayout ln_resgistrasi, ln_image, ln_emptyAkuisisi;
    FloatingActionButton fab;
    mDokterRepo dokterRepo;
    mDokter dokter;
    mApotek apotek;
    mApotekRepo apotekRepo;
    SwipeRefreshLayout swpAkusisi;
    tRealisasiVisitPlan dtCheckinActive;

    public FragmentSubAkuisisi(tAkuisisiHeader dtHeader, int intTypeSubSubId, FloatingActionButton fab, tRealisasiVisitPlan dtCheckinActive){
        this.dtHeader = dtHeader;
        this.intTypeSubSubId = intTypeSubSubId;
        this.fab = fab;
        this.dtCheckinActive = dtCheckinActive;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_akuisisi, container, false);
        layout_dots = (LinearLayout) v.findViewById(R.id.layout_dots);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager_subakuisisi);
        tvNoDoc = (TextView) v.findViewById(R.id.title_subakuisisi);
        tvExpDate = (TextView) v.findViewById(R.id.tv_exp_date_sub);
        tv_status_info = (TextView)v.findViewById(R.id.tv_status_info);
        tv_status_Akusisi = (TextView)v.findViewById(R.id.tv_status_Akusisi);
        ln_image = (LinearLayout) v.findViewById(R.id.ln_image_sub_akuisisi);
        ln_resgistrasi = (LinearLayout) v.findViewById(R.id.ln_resgistrasi_sub_akuisisi);
        ln_emptyAkuisisi = (LinearLayout)v.findViewById(R.id.ln_emptyAkuisisi);
        tvOutlet = (TextView)v.findViewById(R.id.tv_nama_outlet_akuisisi);
        tvUserName = (TextView)v.findViewById(R.id.tv_username_akuisisi);
        tvDesc_akuisisi = (TextView) v.findViewById(R.id.tvDesc_akuisisi);
        tv_image = (TextView) v.findViewById(R.id.image_letter_akuisisi);
        image = (ImageView)v.findViewById(R.id.image_akuisisi);
        swpAkusisi = (SwipeRefreshLayout)v.findViewById(R.id.swpAkusisi);
        swpAkusisi.setOnRefreshListener(refreshListener);
        swpAkusisi.setColorSchemeColors(
//                    getResources().getColor(android.R.color.holo_blue_bright),
//                    getResources().getColor(android.R.color.holo_green_light),
//                    getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );

        headerRepo = new tAkuisisiHeaderRepo(getContext());
        detailRepo = new tAkuisisiDetailRepo(getContext());
        dokterRepo = new mDokterRepo(getContext());
        apotekRepo = new mApotekRepo(getContext());

        loadData();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                enableDisableSwipeRefresh( state == ViewPager.SCROLL_STATE_IDLE );
            }
        });



        adapterImageSlider.setOnItemClickListener(new AdapterImageSlider.OnItemClickListener() {
            @Override
            public void onItemClick(View view, tAkuisisiDetail obj) {
                Intent intent1 = new Intent(getContext(), ImageViewerActivity.class);
//                    intent1.putExtra(ZOOM_DIRECTORY, new clsHardCode().txtFolderAkuisisi);
                intent1.putExtra(ZOOM_IMAGE, obj.getTxtDetailId());
                startActivity(intent1);
            }
        });
        return v;
    }

    private void enableDisableSwipeRefresh(boolean enable) {
        if (swpAkusisi != null) {
            swpAkusisi.setEnabled(enable);
        }
    }

    private void loadData(){
        try {
            if (dtHeader==null){
                ln_emptyAkuisisi.setVisibility(View.VISIBLE);
                ln_resgistrasi.setVisibility(View.GONE);
                ln_image.setVisibility(View.GONE);
                tvDesc_akuisisi.setText("Please download or create data Akuisisi");
            }else {
                dtDetail = (List<tAkuisisiDetail>) detailRepo.findByHeaderId(dtHeader.getTxtHeaderId());
                if (intTypeSubSubId ==new clsHardCode().TypeFoto){
                    if (dtDetail.size()>0){
                        if (isDetailNotReady(dtDetail)){
                            ln_emptyAkuisisi.setVisibility(View.VISIBLE);
                            ln_resgistrasi.setVisibility(View.GONE);
                            ln_image.setVisibility(View.GONE);
                            tvDesc_akuisisi.setText("Please download File Akuisisi");
                        }else {
                            ln_resgistrasi.setVisibility(View.GONE);
                            ln_emptyAkuisisi.setVisibility(View.GONE);
                            ln_image.setVisibility(View.VISIBLE);
                            tvNoDoc.setText(dtHeader.getTxtNoDoc());
                            tvExpDate.setText(parseDate(dtHeader.getDtExpiredDate()));
                        }
                        if (dtHeader.getIntFlagPush()==new clsHardCode().Save){
                            tv_status_info.setText("Submit");
                            tv_status_info.setTextColor(getContext().getResources().getColor(R.color.red_200));
                        }else if (dtHeader.getIntFlagPush()==new clsHardCode().Sync){
                            tv_status_info.setText("Sync");
                            tv_status_info.setTextColor(getContext().getResources().getColor(R.color.green_300));
                        }

                    }else {
                        ln_emptyAkuisisi.setVisibility(View.VISIBLE);
                        ln_resgistrasi.setVisibility(View.GONE);
                        ln_image.setVisibility(View.GONE);
                        tvDesc_akuisisi.setText("Please download File Akuisisi");
                    }
                }else if (intTypeSubSubId==new clsHardCode().TypeText){
                    ln_image.setVisibility(View.GONE);
                    ln_emptyAkuisisi.setVisibility(View.GONE);
                    ln_resgistrasi.setVisibility(View.VISIBLE);
                    boolean isDokter = false;
                    if (dtHeader.getIntApotekID()!=null){
                        if (!dtHeader.getIntApotekID().equals("null")){
                            isDokter = false;
                        }else {
                            isDokter = true;
                        }
                    }else if (dtHeader.getIntDokterId()!=null){
                        if (!dtHeader.getIntDokterId().equals("null")){
                            isDokter = true;
                        }else {
                            isDokter = false;
                        }
                    }

                    if (isDokter){
                        dokter = (mDokter) dokterRepo.findBytxtId(dtHeader.getIntDokterId());
                        if (dokter!=null){
                            if (dokter.getTxtLastName()!=null){
                                tvOutlet.setText("Doctor : "+ dokter.getTxtFirstName() + " " + dokter.getTxtLastName());
                            }else {
                                tvOutlet.setText("Doctor : "+ dokter.getTxtFirstName());
                            }
                        }else {
                            tvOutlet.setText("Doctor : "+ dtCheckinActive.getTxtDokterName());
                        }

                    }else {
//                        apotek = (mApotek) apotekRepo.findBytxtId(dtHeader.getIntApotekID());
//                        if (apotek != null) {
//
//                        }else {
//                            tvOutlet.setText("Pharmacy : "+apotek.getTxtName());
//                        }
                        tvOutlet.setText("Pharmacy : "+ dtCheckinActive.getTxtApotekName());
//                            tv_image.setText((dtHeader.getTxtUserName().substring(0,1)).toUpperCase());
                    }
                    tvUserName.setText(dtHeader.getTxtUserName());
                    image.setColorFilter(getContext().getResources().getColor(R.color.red_400));
                    if (dtHeader.getIntFlagPush()==new clsHardCode().Save){
                        tv_status_Akusisi.setText("Submit");
                        tv_status_Akusisi.setTextColor(getContext().getResources().getColor(R.color.red_200));
                    }else if (dtHeader.getIntFlagPush()==new clsHardCode().Sync){
                        tv_status_Akusisi.setText("Sync");
                        tv_status_Akusisi.setTextColor(getContext().getResources().getColor(R.color.green_300));
                    }
                    image.setImageResource(R.drawable.shape_circle);
                    tv_image.setText((dtHeader.getTxtUserName().substring(0,1)).toUpperCase());
                }
            }

//                dtHeader = (tAkuisisiHeader) headerRepo.findBySubSubId(intSubSubId, new clsHardCode().Save);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapterImageSlider = new AdapterImageSlider(getActivity(), dtDetail);
        adapterImageSlider.setItems(dtDetail);
        viewPager.setAdapter(adapterImageSlider);

        // displaying selected image first
        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);

    }
    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getActivity());
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(ContextCompat.getColor(getActivity(), R.color.green_300), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey_20), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<tAkuisisiDetail> items;

        private OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, tAkuisisiDetail obj);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<tAkuisisiDetail> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public tAkuisisiDetail getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<tAkuisisiDetail> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final tAkuisisiDetail dt = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = (ImageView) v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
            new Tools().displayImageOriginal(act, image, dt.getTxtImg());
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, dt);
                    }
                }
            });
            ((ViewPager) container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }

    private boolean isDetailNotReady(List<tAkuisisiDetail>dtDetailAkuisisi){
        boolean valid = false;
        for (tAkuisisiDetail data: dtDetailAkuisisi){
            if (data.getTxtImg()==null){
                valid = true;
                break;
            }
        }
        return valid;
    }

    private String parseDate(String dateExp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            if (dateExp!=null&& dateExp!="")
                date = sdf.parse(dateExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return "";
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            int iterator = customViewPager.getCurrentItem();
//            customViewPager.setAdapter(adapter);
//            customViewPager.setCurrentItem(iterator);
            if (dtHeader!=null){
                try {
                    dtHeader = (tAkuisisiHeader) headerRepo.findByHeaderId(dtHeader.getTxtHeaderId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                loadData();
            }
            swpAkusisi.setRefreshing(false);
        }
    };
}
