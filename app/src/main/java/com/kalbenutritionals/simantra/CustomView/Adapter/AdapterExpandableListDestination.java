package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;
import com.kalbenutritionals.simantra.CustomView.Utils.OnReceivedData;
import com.kalbenutritionals.simantra.CustomView.Utils.SpacingItemDecoration;
import com.kalbenutritionals.simantra.CustomView.Utils.setDataChecklist;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Fragment.FragmentDetailInfoChecker;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.Jawaban;
import com.kalbenutritionals.simantra.ViewModel.VmListAnswerView;
import com.kalbenutritionals.simantra.ViewModel.VmListImageAdapter;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapterPertanyaan;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

/**
 * Created by Roberto on 4/4/2019.
 */

public class AdapterExpandableListDestination extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements setDataChecklist {

    private List<VmListItemAdapterPertanyaan> items = new ArrayList<>();

    public VmListItemAdapterPertanyaan p;
    public static Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private int CAMERA_REQUEST_QUESTION = 818;
    final int SELECT_FILE_QUESTION = 919;
    private final String IMAGE_DIRECTORY_NAME = "VmImage Transporter";
    private final String TAG_UPLOAD_FOTO_PROFILE = "UPLOAD_FOTO";
    List<View> listAnswer = new ArrayList<View>();
    View v;
    TextView textView;
    CheckBox checkBox;
    RadioButton radioButton;
    LinearLayout linearLayout;
    RadioGroup radioGroup;
    ImageView imageView;
    OnReceivedData receivedData;

    HashMap<Integer, View> HMPertanyaan1 = new HashMap<Integer, View>();
    HashMap<String, String> HMPertanyaan2 = new HashMap<String, String>();
    HashMap<String, String> HMJawaban = new HashMap<String, String>();
    List<VmListAnswerView> ListAnswerView = new ArrayList<>();

    @Override
    public void submit() {

    }

    public interface OnItemClickListener {
        void onItemClick(View view, VmListItemAdapterPertanyaan obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
    public AdapterExpandableListDestination(){

    }
    public AdapterExpandableListDestination(Context context, List<VmListItemAdapterPertanyaan> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView image;
//        public TextView txtPertanyaan;
        public ImageButton bt_expand;
        public View lyt_expand;
        public LinearLayout lyt_parent, ln_error_msg;
        private LinearLayout lyt_pertayaan, ll_jawaban1;
        public TextView tvErrorMesage;

        public OriginalViewHolder(View v) {
            super(v);
//            image = (ImageView) v.findViewById(R.id.image);
//            txtPertanyaan = (TextView) v.findViewById(R.id.txtPertanyaan);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (LinearLayout) v.findViewById(R.id.lyt_parent);
            tvErrorMesage = (TextView) v.findViewById(R.id.warning_text);
            ln_error_msg = (LinearLayout) v.findViewById(R.id.ln_error_msg);
            if(lyt_pertayaan== null)lyt_pertayaan = (LinearLayout) v.findViewById(R.id.ll_pertanyaan1);

            if (ll_jawaban1 == null)ll_jawaban1 = (LinearLayout) v.findViewById(R.id.ll_jawaban1);



        }
    }

    /*private void selectImageProfile(final int id, int position, final int imgId) {
        FragmentDetailInfoChecker.CAMERA_REQUEST_QUESTION = id;
        FragmentDetailInfoChecker.GLOBAL_PICK_PICTURE_ID = imgId;
        FragmentDetailInfoChecker.GLOBAL_PICK_PICTURE_QUEST_ID = position;
        String filename = "tmp_act"+id;
        FragmentDetailInfoChecker.uriImage = new UriData().getOutputMediaImageUri(ctx, new ClsHardCode().txtFolderDataQuest, filename);
        new PickImage().CaptureImage(ctx, new ClsHardCode().txtFolderDataQuest, filename, FragmentDetailInfoChecker.CAMERA_REQUEST_QUESTION);
//        final CharSequence[] items = {"Ambil Foto", "Pilih dari Galeri",
//                "Batal"};
//        AlertDialogs.Builder builder = new AlertDialogs.Builder(ctx);
//        builder.setTitle("Add Photo");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                boolean result = PermissionChecker.Utility.checkPermission(ctx);
//                if (items[item].equals("Ambil Foto")) {
////                    FragmentDetailInfoChecker.uriImage = getOutputMediaImageUri(ctx, new ClsHardCode().txtFolderDataQuest, "tmp_act");
//
//                } else if (items[item].equals("Pilih dari Galeri")) {
//                    if (result)
//                        galleryIntentProfile();
//                } else if (items[item].equals("Batal")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
    }*/
    private void galleryIntentProfile() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Activity activity = (Activity) ctx;
        activity.startActivityForResult(pickPhoto, SELECT_FILE_QUESTION);//one can be replaced with any action code
    }
    public Uri getOutputMediaImageUri(Context context, String folderName, String fileName) {
        return Uri.fromFile(getOutputMediaFile());
    }
    private File getOutputMediaFile() {
        // External sdcard location

        File mediaStorageDir = new File(new ClsHardCode().txtFolderData + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file txtPertanyaan
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "tmp_act" + ".png");
        return mediaFile;
    }
    private  void generateQ(VmListItemAdapterPertanyaan pa, OriginalViewHolder holder,final int position){
        if (pa.bitValid){
            holder.ln_error_msg.setVisibility(View.GONE);
        }else{
            holder.ln_error_msg.setVisibility(View.VISIBLE);
            holder.tvErrorMesage.setText(pa.message);
        }
        if(pa!=null && pa.jenisPertanyaan==ClsHardCode.JenisPertanyaanCheckBox){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);

            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity =  Gravity.CENTER_VERTICAL;
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setId(pa.id*21);

            if (pa.bolHaveAnswer){
                List<Jawaban> jawabans = pa.jawabans;

                for (Jawaban j :
                        jawabans) {
                    CheckBox checkBox = new CheckBox(ctx);
                    checkBox.setText(j.jawaban);
                    checkBox.setId(j.idJawaban);
                    if (j.bitChoosen){
                        checkBox.setChecked(true);
                    }else{
                        checkBox.setChecked(false);
                    }
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
////                            if (position==items.size()-1){
////                                receivedData.onDataTransportReceived(listAnswer, HMPertanyaan1, ListAnswerView);
////                            }
//                            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
//                        }
//                    });

                    linearLayout.addView(checkBox);
                }
            }


            if (pa.bitImage){
                TextView tvPicture = new TextView(ctx);
                tvPicture.setText("Upload foto");
                tvPicture.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                tvPicture.setGravity(Gravity.CENTER);
                linearLayout.addView(tvPicture);
                RecyclerView rcImage = new RecyclerView(v.getContext());
                rcImage.setId(pa.id*15);
                if (pa.listImage.size()>1){
                    rcImage.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
                    rcImage.addItemDecoration(new SpacingItemDecoration(2, new ClsTools().dpToPx(v.getContext(), 1), false));
                }else {
                    rcImage.setLayoutManager(new GridLayoutManager(v.getContext(), 1));
                    rcImage.addItemDecoration(new SpacingItemDecoration(1, new ClsTools().dpToPx(v.getContext(), 1), false));
                }
                rcImage.setHasFixedSize(true);
                rcImage.setNestedScrollingEnabled(false);
                final int position2 = position;
                List<VmListImageAdapter> listImage = new ArrayList<>();
                for (int i = 0; i < pa.listImage.size(); i++){
                    VmListImageAdapter imageAdapter = new VmListImageAdapter();
                    imageAdapter.setIntId((pa.id*15)+pa.listImage.get(i).getPosition());
                    imageAdapter.setBmpImage(pa.listImage.get(i).getBitmap());
                    imageAdapter.setIntPosition(pa.listImage.get(i).getPosition());
                    listImage.add(imageAdapter);
                }

                RecyclerGridImageAdapter adapter = new RecyclerGridImageAdapter(v.getContext(), listImage);
                rcImage.setAdapter(adapter);
                final int paId = pa.id*15;
                adapter.setOnImageClickListener(new RecyclerGridImageAdapter.OnImageClickListener() {
                    @Override
                    public void onItemClick(View view, VmListImageAdapter obj, int position) {
//                        selectImageProfile(paId,position2, position);
                    }
                });
                linearLayout.addView(rcImage);
            }
            holder.ll_jawaban1.addView(linearLayout);
        }else if(pa!=null && pa.jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);

            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity =  Gravity.CENTER_VERTICAL;
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setId(pa.id*22);

            if (pa.bolHaveAnswer){
                List<Jawaban> jawabans  = pa.jawabans;
//                    List<ClsmJawaban> jawabans = new RepomJawaban(ctx).findByHeader(pa.id);
                RadioGroup rg = new RadioGroup(ctx); //create the RadioGroup
                rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
                rg.setId(pa.id*12);
                rg.clearCheck();

                for (Jawaban j :
                        jawabans) {
                    RadioButton rb = new RadioButton(ctx);
                    rb = new RadioButton(ctx);
                    rb.setText(j.jawaban);
                    rb.setId(j.idJawaban);
                    rb.setChecked(j.bitChoosen);
                    rg.addView(rb);
                    ClsTools.setMargins(rg,5,10,5,10);
                }

                linearLayout.addView(rg);
            }

            if (pa.bitImage){
                TextView tvPicture = new TextView(ctx);
                tvPicture.setText("Upload foto");
                tvPicture.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                tvPicture.setGravity(Gravity.CENTER);
                linearLayout.addView(tvPicture);
                RecyclerView rcImage = new RecyclerView(v.getContext());
                rcImage.setId(pa.id*15);
                if (pa.listImage.size()>1){
                    rcImage.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
                    rcImage.addItemDecoration(new SpacingItemDecoration(2, new ClsTools().dpToPx(v.getContext(), 1), false));
                }else {
                    rcImage.setLayoutManager(new GridLayoutManager(v.getContext(), 1));
                    rcImage.addItemDecoration(new SpacingItemDecoration(1, new ClsTools().dpToPx(v.getContext(), 1), false));
                }
                rcImage.setHasFixedSize(true);
                rcImage.setNestedScrollingEnabled(false);
                final int position2 = position;
                List<VmListImageAdapter> listImage = new ArrayList<>();
                for (int i = 0; i < pa.listImage.size(); i++){
                    VmListImageAdapter imageAdapter = new VmListImageAdapter();
                    imageAdapter.setIntId((pa.id*15)+pa.listImage.get(i).getPosition());
                    imageAdapter.setBmpImage(pa.listImage.get(i).getBitmap());
                    imageAdapter.setIntPosition(pa.listImage.get(i).getPosition());
                    listImage.add(imageAdapter);
                }

                RecyclerGridImageAdapter adapter = new RecyclerGridImageAdapter(v.getContext(), listImage);
                rcImage.setAdapter(adapter);
                final int paId = pa.id*15;
                adapter.setOnImageClickListener(new RecyclerGridImageAdapter.OnImageClickListener() {
                    @Override
                    public void onItemClick(View view, VmListImageAdapter obj, int position) {
//                        selectImageProfile(paId,position2, position);
                    }
                });
                linearLayout.addView(rcImage);

            }
            holder.ll_jawaban1.addView(linearLayout);

        }else if(pa!=null && pa.jenisPertanyaan == ClsHardCode.JenisPertanyaanTextView){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);

            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setId(pa.id*23);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            Activity activity = (Activity) ctx;
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels - 60;
            int heigth = displayMetrics.heightPixels / 15;
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(width, heigth);
            layoutParams2.setMargins(10,0,0,0);
            layoutParams2.gravity = Gravity.CENTER_HORIZONTAL;
            final TextView etTest = new TextView(ctx);
            if (pa.jawabans.size()>0){
                etTest.setText(pa.jawabans.get(0).jawaban);
            }
            etTest.setId(pa.id*13);
            etTest.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            etTest.setSingleLine(false);
//            etTest.setEms(10);
            etTest.setGravity(Gravity.TOP);
            etTest.setBackgroundResource(R.drawable.bg_edtext);
            etTest.setLayoutParams(layoutParams2);

            linearLayout.addView(etTest);

            holder.ll_jawaban1.addView(linearLayout);
            pa = new VmListItemAdapterPertanyaan();

        }else if(pa!=null && pa.jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);

            List<Jawaban> jawabans  = pa.jawabans;
            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setId(pa.id*24);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            Activity activity = (Activity) ctx;
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels - 60;
            int heigth = displayMetrics.heightPixels / 10;
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(width, heigth);
            layoutParams2.gravity = Gravity.CENTER_HORIZONTAL;
            final EditText etTest = new EditText(ctx);
            etTest.setText("");
            etTest.setHint("Please fill...");
            etTest.setId(pa.id*14);
            etTest.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            etTest.setSingleLine(false);
//            etTest.setEms(10);
            etTest.setGravity(Gravity.TOP);
            etTest.setBackgroundResource(R.drawable.bg_edtext);
            etTest.setLayoutParams(layoutParams2);
            if (jawabans.size()>0){
                etTest.setText(jawabans.get(0).jawaban);
            }

            linearLayout.addView(etTest);
            if (pa.bitImage){
                TextView tvPicture = new TextView(ctx);
                tvPicture.setText("Upload foto");
                tvPicture.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                tvPicture.setGravity(Gravity.CENTER);
                linearLayout.addView(tvPicture);
                View v = new ImageView(ctx);

                RecyclerView rcImage = new RecyclerView(v.getContext());
                rcImage.setId(pa.id*15);
                if (pa.listImage.size()>1){
                    rcImage.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
                    rcImage.addItemDecoration(new SpacingItemDecoration(2, new ClsTools().dpToPx(v.getContext(), 1), false));
                }else {
                    rcImage.setLayoutManager(new GridLayoutManager(v.getContext(), 1));
                    rcImage.addItemDecoration(new SpacingItemDecoration(1, new ClsTools().dpToPx(v.getContext(), 1), false));
                }
                rcImage.setHasFixedSize(true);
                rcImage.setNestedScrollingEnabled(false);
                final int position2 = position;
                List<VmListImageAdapter> listImage = new ArrayList<>();
                for (int i = 0; i < pa.listImage.size(); i++){
                    VmListImageAdapter imageAdapter = new VmListImageAdapter();
                    imageAdapter.setIntId((pa.id*15)+pa.listImage.get(i).getPosition());
                    imageAdapter.setBmpImage(pa.listImage.get(i).getBitmap());
                    imageAdapter.setIntPosition(pa.listImage.get(i).getPosition());
                    listImage.add(imageAdapter);
                }

                RecyclerGridImageAdapter adapter = new RecyclerGridImageAdapter(v.getContext(), listImage);
                rcImage.setAdapter(adapter);
                final int paId = pa.id*15;
                adapter.setOnImageClickListener(new RecyclerGridImageAdapter.OnImageClickListener() {
                    @Override
                    public void onItemClick(View view, VmListImageAdapter obj, int position) {
//                        selectImageProfile(paId,position2, position);
                    }
                });
//                ImageView image;
//                image = new ImageView(v.getContext());
//                image.setId(pa.id*15);
//                image.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));
//
//                if(pa.bitmap!=null){
//                    Bitmap mybitmap = Bitmap.createScaledBitmap(pa.bitmap, 400, 500, true);
//                    image.setImageBitmap(mybitmap);
//                }
                linearLayout.addView(rcImage);
            }
            holder.ll_jawaban1.addView(linearLayout);

        }

        /*View v = new View(ctx);
        v.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                5
        ));
        ClsTools.setMargins(v,5,10,5,10);
        v.setBackgroundColor(Color.parseColor("#000000"));
        holder.lyt_parent.addView(v);
*/
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;
             p = items.get(position);
             generateQ( p,view,position);
//             findViewId(p, view,position);
             /*if (position==items.size()-1){
                 receivedData.onDataTransportReceived(listAnswer, HMPertanyaan1, ListAnswerView);
                 ListAnswerView = new ArrayList<>();
             }
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });*/


        }
    }
    public void sendData(OnReceivedData receivedData){
        this.receivedData = receivedData;
    }
    public void updateData(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public interface updateMainClass{

    }
}
