package com.kalbenutritionals.simantra.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterImageSlider;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterListBasic;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.Images;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentApprover extends Fragment {
    Unbinder unbinder;
    AdapterListBasic adapterBasic;
    private static List<VmAdapterBasic> listData = new ArrayList<>();
    private View parent_view;
    private AdapterImageSlider adapterImageSlider;
    View v;
    Context context;
    @BindView(R.id.bg_image)
    ImageView bgImage;
    @BindView(R.id.coordinator_lyt_no_item)
    CoordinatorLayout coordinatorLytNoItem;
    @BindView(R.id.recyclerViewListBasic)
    RecyclerView recyclerViewListBasic;

    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private Runnable runnable = null;
    private Handler handler = new Handler();
    private static int[] array_image_place = {
            R.drawable.img_social_android,
            R.drawable.img_social_behance
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_approver, container, false);
        unbinder = ButterKnife.bind(this, v);
        parent_view = getActivity().findViewById(android.R.id.content);
        context = getActivity().getApplicationContext();
        listData.clear();
        listData.clear();
        List<String> imgLink = new ArrayList<>();
        imgLink.add("http://www.livescience.com/images/i/000/065/149/original/bananas.jpg");
        imgLink.add("http://www.livescience.com/images/i/000/065/149/original/bananas.jpg");
        for (int i = 0; i < 5; i++) {
            VmAdapterBasic adapterData = new VmAdapterBasic();
            adapterData.setTxtLinkImage("http://www.livescience.com/images/i/000/065/149/original/bananas.jpg");
            adapterData.setTitle("Title " + i);
            adapterData.setSubtitle("Description " + i);
            listData.add(adapterData);
        }
        if (0 > 0) {
            coordinatorLytNoItem.setVisibility(View.GONE);
            recyclerViewListBasic.setVisibility(View.VISIBLE);
        } else {
            recyclerViewListBasic.setVisibility(View.VISIBLE);
            coordinatorLytNoItem.setVisibility(View.GONE);
        }
        adapterBasic = new AdapterListBasic(context, listData);
        recyclerViewListBasic.setAdapter(adapterBasic);
        recyclerViewListBasic.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBasic.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, VmAdapterBasic obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.getTitle() + " clicked", Snackbar.LENGTH_SHORT).show();
                showDialogPolygon(obj, position);
            }
        });
        return v;
    }

    private void showDialogPolygon(VmAdapterBasic objDt, final int position) {

        final Dialog dialogMain = new Dialog(getActivity());
        dialogMain.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogMain.setContentView(R.layout.dialog_header_polygon);
        adapterImageSlider = new AdapterImageSlider(getActivity(), new ArrayList<Images>());
        viewPager = dialogMain.findViewById(R.id.pager);
//        layout_dots = dialog.findViewById(R.id.layout_dots);
        final List<Images> items = new ArrayList<>();
        for (int i = 0; i < array_image_place.length; i++) {
            Images obj = new Images();
            obj.image = array_image_place[i];
            obj.imageDrw = getResources().getDrawable(obj.image);
            obj.imgLink = "http://cdn2.tstatic.net/aceh/foto/bank/images/tidur_20160102_105659.jpg";
            items.add(obj);
        }

        adapterImageSlider.setItems(items);
        viewPager.setAdapter(adapterImageSlider);
        viewPager.setCurrentItem(0);
//        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
//        ((TextView) dialog.findViewById(R.id.title)).setText(items.get(0).name);
//        ((TextView) dialog.findViewById(R.id.brief)).setText(items.get(0).brief);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
//                ((TextView) dialog.findViewById(R.id.title)).setText(items.get(pos).name);
//                ((TextView) dialog.findViewById(R.id.brief)).setText(items.get(pos).brief);
//                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        startAutoSlider(adapterImageSlider.getCount());

        dialogMain.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogMain.setCancelable(true);

        ((Button) dialogMain.findViewById(R.id.bt_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Button Join Clicked", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listData.remove(position);
                        adapterBasic.notifyDataSetChanged();
                        dialog.dismiss();
                        dialogMain.dismiss();
                        toastIconSuccess();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        ((Button) dialogMain.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button Decline Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        dialogMain.show();
    }



    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void toastIconSuccess() {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText("Success!");
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));

        toast.setView(custom_view);
        toast.show();
    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(context);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 0, 10, 0);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            dots[i].setColorFilter(ContextCompat.getColor(context, R.color.grey_40), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
            dots[current].setColorFilter(ContextCompat.getColor(context, R.color.grey_40), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            finish();
        } else {
            Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
