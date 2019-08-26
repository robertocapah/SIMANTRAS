package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbenutritionals.simantra.ActivityWebView;
import com.kalbenutritionals.simantra.CustomView.Utils.OnReceivedData;
import com.kalbenutritionals.simantra.CustomView.Utils.SpacingItemDecoration;
import com.kalbenutritionals.simantra.CustomView.Utils.setDataChecklist;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Fragment.FragmentDetailInfoChecker;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;
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
import static com.kalbenutritionals.simantra.Fragment.FragmentDetailInfoChecker.GLOBAL_PICK_PICTURE_POSITION_ID;

/**
 * Created by Roberto on 4/4/2019.
 */

public class AdapterExpandableList extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements setDataChecklist {

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
    LinearLayout linearLayout;
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
    public AdapterExpandableList(){

    }
    public AdapterExpandableList(Context context, List<VmListItemAdapterPertanyaan> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView image;
//        public TextView txtPertanyaan;
        public ImageButton bt_expand;
        public View lyt_expand;
        public LinearLayout lyt_parent, ln_error_msg, ln_mandatory;
        private LinearLayout lyt_pertayaan, ll_jawaban1;
//        private LinearLayout ll_reason;
        public TextView tvErrorMesage;
        public TextView tvBtnInformation;
//        public EditText etReason;

        public OriginalViewHolder(View v) {
            super(v);
//            image = (ImageView) v.findViewById(R.id.image);
//            txtPertanyaan = (TextView) v.findViewById(R.id.txtPertanyaan);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (LinearLayout) v.findViewById(R.id.lyt_parent);
            tvErrorMesage = (TextView) v.findViewById(R.id.warning_text);
            tvBtnInformation = (TextView) v.findViewById(R.id.tvBtnInformation);
            ln_error_msg = (LinearLayout) v.findViewById(R.id.ln_error_msg);
            ln_mandatory = (LinearLayout) v.findViewById(R.id.ln_mandatory);
            if(lyt_pertayaan== null)lyt_pertayaan = (LinearLayout) v.findViewById(R.id.ll_pertanyaan1);
//            ll_reason = (LinearLayout) v.findViewById(R.id.ll_reason);
//            etReason = (EditText) v.findViewById(R.id.etReason);

            if (ll_jawaban1 == null)ll_jawaban1 = (LinearLayout) v.findViewById(R.id.ll_jawaban1);

        }
    }

    private void selectImage(final int id, int position, final int imgId, String txtImageName) {
        FragmentDetailInfoChecker.CAMERA_REQUEST_QUESTION = id;
        FragmentDetailInfoChecker.GLOBAL_PICK_PICTURE_ID = imgId;
        FragmentDetailInfoChecker.GLOBAL_PICK_PICTURE_QUEST_ID = position;
        String filename = "tmp_act"+txtImageName;

        FragmentDetailInfoChecker.uriImage = new UriData().getOutputMediaImageUri(ctx, new ClsHardCode().txtFolderDataQuest, filename);
        FragmentDetailInfoChecker.imgName = filename;
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
    }
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

    private void findViewId(VmListItemAdapterPertanyaan pa, OriginalViewHolder holder, int postition){

        if(pa!=null && pa.jenisPertanyaan==ClsHardCode.JenisPertanyaanCheckBox){
            int size = holder.ll_jawaban1.getChildCount();
            for (int x = 0; x < holder.ll_jawaban1.getChildCount(); x++) {
                View nextChild = holder.ll_jawaban1.getChildAt(x);
                if (nextChild instanceof LinearLayout) {
                    linearLayout = (LinearLayout) nextChild;
                    linearLayout = (LinearLayout) v.findViewById(pa.id*21);
                }
            }
//            listAnswer.add(linearLayout);
            VmListAnswerView vmListAnswerView = new VmListAnswerView();
            vmListAnswerView.setIntPertanyaanId(pa.id);
//            vmListAnswerView.setVwJawaban(listAnswer.get(postition));
            vmListAnswerView.setIntPosition(postition);
            vmListAnswerView.setType(pa.intValidateId);
            ListAnswerView.add(vmListAnswerView);
        }else if(pa!=null && pa.jenisPertanyaan==ClsHardCode.JenisPertanyaanRadioButton){
            int size = holder.ll_jawaban1.getChildCount();
            for (int x = 0; x < holder.ll_jawaban1.getChildCount(); x++) {
                View nextChild = holder.ll_jawaban1.getChildAt(x);
                  if (nextChild instanceof LinearLayout) {
                    linearLayout = (LinearLayout) nextChild;
                    linearLayout = (LinearLayout) v.findViewById(pa.id*22);
                }
            }
//            listAnswer.add(linearLayout);
            VmListAnswerView vmListAnswerView = new VmListAnswerView();
            vmListAnswerView.setIntPertanyaanId(pa.id);
//            vmListAnswerView.setVwJawaban(listAnswer.get(postition));
            vmListAnswerView.setIntPosition(postition);
            vmListAnswerView.setType(pa.intValidateId);
            vmListAnswerView.setTxtReason(pa.txtReason);
            ListAnswerView.add(vmListAnswerView);
        }/*else if(pa!=null && pa.jenisPertanyaan==ClsHardCode.JenisPertanyaanTextBox){
            int size = holder.ll_jawaban1.getChildCount();
            for (int x = 0; x < holder.ll_jawaban1.getChildCount(); x++) {
                View nextChild = holder.ll_jawaban1.getChildAt(x);
                if (nextChild instanceof LinearLayout) {
                    linearLayout = (LinearLayout) nextChild;
                    linearLayout = (LinearLayout) v.findViewById(pa.id*23);
                }
            }
            listAnswer.add(linearLayout);
            HMPertanyaan1.put(pa.id,listAnswer.get(postition));
            VmListAnswerView vmListAnswerView = new VmListAnswerView();
            vmListAnswerView.setIntPertanyaanId(pa.id);
            vmListAnswerView.setVwJawaban(listAnswer.get(postition));
            vmListAnswerView.setIntPosition(postition);
            ListAnswerView.add(vmListAnswerView);
        }*/else if(pa!=null && pa.jenisPertanyaan==ClsHardCode.JenisPertanyaanTextBox){
            int size = holder.ll_jawaban1.getChildCount();
            for (int x = 0; x < holder.ll_jawaban1.getChildCount(); x++) {
                View nextChild = holder.ll_jawaban1.getChildAt(x);
                if (nextChild instanceof LinearLayout) {
                    linearLayout = (LinearLayout) nextChild;
                    linearLayout = (LinearLayout) v.findViewById(pa.id*24);
                }
                if (nextChild instanceof ImageView){
                    linearLayout = (LinearLayout) nextChild;
                    linearLayout = (LinearLayout) v.findViewById(pa.id*25);
                }
            }
//             listAnswer.add(linearLayout);
            VmListAnswerView vmListAnswerView = new VmListAnswerView();
            vmListAnswerView.setIntPertanyaanId(pa.id);
//            vmListAnswerView.setVwJawaban(listAnswer.get(postition));
            vmListAnswerView.setIntPosition(postition);
            vmListAnswerView.setType(pa.intValidateId);
            ListAnswerView.add(vmListAnswerView);

        }
//        listAnswer.add();
    }
    private  void generateQ(final VmListItemAdapterPertanyaan pa, final OriginalViewHolder holder, final int position){
        holder.tvBtnInformation.setVisibility(View.VISIBLE);
        holder.tvBtnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                builder.setTitle("Metode Pemeriksaan");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setMessage(Html.fromHtml(pa.txtMetodePemeriksaan, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    builder.setMessage(Html.fromHtml(pa.txtMetodePemeriksaan));
                }


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();*/
                Intent i = new Intent(ctx, ActivityWebView.class);
                i.putExtra("content",pa.txtMetodePemeriksaan);
                ctx.startActivity(i);

            }
        });
        if (pa.bitValid){
            holder.ln_error_msg.setVisibility(View.GONE);
        }else{
            holder.ln_error_msg.setVisibility(View.VISIBLE);
            holder.tvErrorMesage.setText(pa.message);
        }
        if (pa.intValidateId == ClsHardCode.MANDATORY){
            holder.ln_mandatory.setVisibility(View.VISIBLE);
        }else{
            holder.ln_mandatory.setVisibility(View.GONE);
        }
        if(pa!=null && pa.jenisPertanyaan==ClsHardCode.JenisPertanyaanCheckBox){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);

            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
                    imageAdapter.setTxtLinkImage(pa.listImage.get(i).getTxtLink());
                    listImage.add(imageAdapter);
                }

                RecyclerGridImageAdapter adapter = new RecyclerGridImageAdapter(v.getContext(), listImage);
                rcImage.setAdapter(adapter);
                final int paId = pa.id*15;
                adapter.setOnImageClickListener(new RecyclerGridImageAdapter.OnImageClickListener() {
                    @Override
                    public void onItemClick(View view, VmListImageAdapter obj, int position) {
                        selectImage(paId,position2, position, String.valueOf(pa.intFillHdrId)+String.valueOf(pa.id)+position);
                        GLOBAL_PICK_PICTURE_POSITION_ID = p.intPositionId;
                    }
                });
                linearLayout.addView(rcImage);
            }
            holder.ll_jawaban1.addView(linearLayout);
        }
        else if(pa!=null && pa.jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);

            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity =  Gravity.CENTER_VERTICAL;
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setId(pa.id*22);
            final RadioGroup rg = new RadioGroup(ctx); //create the RadioGroup
            RadioButton rb = new RadioButton(ctx);
            if (pa.bolHaveAnswer){
                List<Jawaban> jawabans  = pa.jawabans;
//                    List<ClsmJawaban> jawabans = new RepomJawaban(ctx).findByHeader(pa.id);
                rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
                rg.setGravity(Gravity.CENTER);
                rg.setId(pa.id*12);
                rg.clearCheck();
                for (final Jawaban j :
                        jawabans) {
                    rb = new RadioButton(ctx);
                    rb.setText(j.jawaban);
                    rb.setId(j.idJawaban);

                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.WRAP_CONTENT,
                            RadioGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(10, 10, 100, 15);
                    rb.setLayoutParams(params);
                    rb.setChecked(j.bitChoosen);
                    rb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(v, items.get(position), position);
                            }
                        }
                    });
                    if (j.bitChoosen){
                        if(rb.getText().equals("No")){
//                            holder.ll_reason.setVisibility(View.VISIBLE);
//                            holder.etReason.setText(pa.txtReason);
                        }else{
//                            holder.ll_reason.setVisibility(View.GONE);
                        }
                    }
                    rg.addView(rb);
                    ClsTools.setMargins(rg,5,20,5,20);

                }

                ClsTools.setMargins(linearLayout,5,20,5,20);
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
                    imageAdapter.setTxtLinkImage(pa.listImage.get(i).getTxtLink());
                    listImage.add(imageAdapter);
                }

                RecyclerGridImageAdapter adapter = new RecyclerGridImageAdapter(v.getContext(), listImage);
                rcImage.setAdapter(adapter);
                final int paId = pa.id*15;
                adapter.setOnImageClickListener(new RecyclerGridImageAdapter.OnImageClickListener() {
                    @Override
                    public void onItemClick(View view, VmListImageAdapter obj, int position) {
                        view.setFocusable(true);
                        selectImage(paId,position2, position,String.valueOf(pa.intFillHdrId)+String.valueOf(pa.id)+position);
                        GLOBAL_PICK_PICTURE_POSITION_ID = p.intPositionId;
                    }
                });
                linearLayout.addView(rcImage);

            }
            /*DisplayMetrics displayMetrics = new DisplayMetrics();
            Activity activity = (Activity) ctx;
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels - 60;
            int heigth = displayMetrics.heightPixels / 10;
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(width, heigth);
            layoutParams2.gravity = Gravity.CENTER_HORIZONTAL;
            layoutParams2.setMargins(100,10,100,10);
            final EditText etTest = new EditText(ctx);
            etTest.setText(pa.txtReason);
            etTest.setHint("Please fill the reason...");
            etTest.setId(pa.id*14);
            etTest.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            etTest.setSingleLine(false);
//            etTest.setEms(10);
            etTest.setGravity(Gravity.TOP);
//            etTest.setPadding(20,0,20,0);
//            etTest.setBackgroundResource(R.drawable.bg_edtext);
            etTest.setLayoutParams(layoutParams2);
            linearLayout.addView(etTest);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    rg.setFocusable(false);
                    if (checkedId==2){
                        etTest.setVisibility(View.VISIBLE);
                        rg.setFocusable(true);
                    }else{
                        etTest.setVisibility(View.GONE);
                        rg.setFocusable(true);
                    }

                }
            });*/
            /*if(rg.getCheckedRadioButtonId()==-1){
                int selectedId = rg.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton rd = (RadioButton)v.findViewById(selectedId);
                if (rd!=null){
                    String txt = rd.getText().toString();
                    if (txt.equals("No")){
                        etTest.setVisibility(View.GONE);
                    }else{
                        etTest.setVisibility(View.VISIBLE);
                    }
                }else{
                    etTest.setVisibility(View.GONE);
                }
            }*/
            holder.ll_jawaban1.addView(linearLayout);

        }else if(pa!=null && pa.jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);
            CheckBox checkBoxSesuai = new CheckBox(ctx);
            if (pa.intPositionId==2 && pa.intValidateId ==1){

                checkBoxSesuai.setText("Data Sesuai");
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.weight = 1.0f;
                checkBoxSesuai.setLayoutParams(layoutParams);
                checkBoxSesuai.setChecked(true);

                holder.lyt_pertayaan.addView(checkBoxSesuai);
            }


            final List<Jawaban> jawabans  = pa.jawabans;
            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
            if (pa.intPositionId==2 && pa.intValidateId ==1){
                final KeyListener variable = etTest.getKeyListener();
                etTest.setKeyListener(null);
                checkBoxSesuai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if (jawabans.size()>0){
                                etTest.setText(jawabans.get(0).jawaban);
                                etTest.setKeyListener(null);
                            }
                        }else{
                            etTest.setKeyListener(variable);
                        }
                    }
                });
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
                        selectImage(paId,position2, position,String.valueOf(pa.intFillHdrId)+String.valueOf(pa.id)+position);
                        GLOBAL_PICK_PICTURE_POSITION_ID = p.intPositionId;
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

        }else if(pa!=null && pa.jenisPertanyaan == ClsHardCode.JenisPertanyaanTextView){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);


            List<Jawaban> jawabans  = pa.jawabans;
            LinearLayout linearLayout = new LinearLayout(ctx);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
            final TextView tvTest = new EditText(ctx);
            tvTest.setText("");
            tvTest.setHint("Please fill...");
            tvTest.setId(pa.id*14);
            tvTest.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            tvTest.setSingleLine(false);
//            etTest.setEms(10);
            tvTest.setGravity(Gravity.TOP);
            tvTest.setBackgroundResource(R.drawable.bg_edtext);
            tvTest.setLayoutParams(layoutParams2);
            if (jawabans.size()>0){
                tvTest.setText(jawabans.get(0).jawaban);
            }
            linearLayout.addView(tvTest);
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
             findViewId(p, view,position);
             if (position==items.size()-1){
                 receivedData.onDataTransportReceived(listAnswer, HMPertanyaan1, ListAnswerView, p.intPositionId);
                 ListAnswerView = new ArrayList<>();
             }
            /*view.lyt_parent.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface updateMainClass{

    }
}
