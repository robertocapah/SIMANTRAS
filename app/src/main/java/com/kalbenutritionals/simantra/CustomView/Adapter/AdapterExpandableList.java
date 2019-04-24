package com.kalbenutritionals.simantra.CustomView.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.PermissionChecker.PermissionChecker;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbenutritionals.simantra.CustomView.Utils.OnReceivedData;
import com.kalbenutritionals.simantra.CustomView.Utils.ViewAnimation;
import com.kalbenutritionals.simantra.CustomView.Utils.setDataChecklist;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Fragment.FragmentDetailInfoChecker;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;
import com.kalbenutritionals.simantra.ViewModel.Jawaban;
import com.kalbenutritionals.simantra.ViewModel.VmListAnswerView;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapterPertanyaan;

import java.io.File;
import java.sql.SQLException;
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

    private void selectImageProfile(final int id, int position) {
        FragmentDetailInfoChecker.CAMERA_REQUEST_QUESTION = id;
        FragmentDetailInfoChecker.GLOBAL_PICK_PICTURE_ID = id;
        FragmentDetailInfoChecker.GLOBAL_PICK_PICTURE_QUEST_ID = position;
        final CharSequence[] items = {"Ambil Foto", "Pilih dari Galeri",
                "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionChecker.Utility.checkPermission(ctx);
                if (items[item].equals("Ambil Foto")) {
//                    FragmentDetailInfoChecker.uriImage = getOutputMediaImageUri(ctx, new ClsHardCode().txtFolderDataQuest, "tmp_act");
                    String filename = "tmp_act"+id;
                    FragmentDetailInfoChecker.uriImage = new UriData().getOutputMediaImageUri(ctx, new ClsHardCode().txtFolderDataQuest, filename);
                    new PickImage().CaptureImage(ctx, new ClsHardCode().txtFolderDataQuest, filename, FragmentDetailInfoChecker.CAMERA_REQUEST_QUESTION);
                } else if (items[item].equals("Pilih dari Galeri")) {
                    if (result)
                        galleryIntentProfile();
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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
            listAnswer.add(linearLayout);
            VmListAnswerView vmListAnswerView = new VmListAnswerView();
            vmListAnswerView.setIntPertanyaanId(pa.id);
            vmListAnswerView.setVwJawaban(listAnswer.get(postition));
            vmListAnswerView.setIntPosition(postition);
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
            listAnswer.add(linearLayout);
            VmListAnswerView vmListAnswerView = new VmListAnswerView();
            vmListAnswerView.setIntPertanyaanId(pa.id);
            vmListAnswerView.setVwJawaban(listAnswer.get(postition));
            vmListAnswerView.setIntPosition(postition);
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
            listAnswer.add(linearLayout);
            VmListAnswerView vmListAnswerView = new VmListAnswerView();
            vmListAnswerView.setIntPertanyaanId(pa.id);
            vmListAnswerView.setVwJawaban(listAnswer.get(postition));
            vmListAnswerView.setIntPosition(postition);
            ListAnswerView.add(vmListAnswerView);

        }
//        listAnswer.add();
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
                List<Jawaban> jawabans = jawabans = pa.jawabans;

                for (Jawaban j :
                        jawabans) {
                    CheckBox checkBox = new CheckBox(ctx);
                    checkBox.setText(j.jawaban);
                    checkBox.setId(pa.id*11);
                    if (j.bitChoosen){
                        checkBox.setChecked(true);
                    }else{
                        checkBox.setChecked(false);
                    }
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                            if (position==items.size()-1){
                                receivedData.onDataTransportReceived(listAnswer, HMPertanyaan1, ListAnswerView);
                            }
                            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                    linearLayout.addView(checkBox);
                }
            }


            if (pa.bitImage){
                TextView tvPicture = new TextView(ctx);
                tvPicture.setText("Upload foto");
                tvPicture.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                tvPicture.setGravity(Gravity.CENTER);
                linearLayout.addView(tvPicture);
                ImageView image;
                image = new ImageView(v.getContext());
                image.setId(pa.id*15);
                image.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));

                if(pa.bitmap!=null){
                    Bitmap mybitmap = Bitmap.createScaledBitmap(pa.bitmap, 400, 500, true);
                    image.setImageBitmap(mybitmap);
                }
                linearLayout.addView(image);
                final int paId2 = pa.id;
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImageProfile(paId2*15,position);
                    }
                });

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

            try {
                if (pa.bolHaveAnswer){
                    List<ClsmJawaban> jawabans = new RepomJawaban(ctx).findByHeader(pa.id);
                    RadioGroup rg = new RadioGroup(ctx); //create the RadioGroup
                    rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
                    rg.setId(pa.id*12);

                    for (ClsmJawaban j :
                            jawabans) {
                        RadioButton rb = new RadioButton(ctx);
                        rb = new RadioButton(ctx);
                        rb.setText(j.getTxtJawaban());
                        rb.setId(j.getIdJawaban());
                        rg.addView(rb);
                        ClsTools.setMargins(rg,5,10,5,10);
                    }
                    linearLayout.addView(rg);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }



            if (pa.bitImage){
                TextView tvPicture = new TextView(ctx);
                tvPicture.setText("Upload foto");
                tvPicture.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                tvPicture.setGravity(Gravity.CENTER);
                linearLayout.addView(tvPicture);
                ImageView image;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                params.weight = 1.0f;
                params.gravity = Gravity.CENTER;

                image = new ImageView(v.getContext());
                image.setId(pa.id*15);
                image.setLayoutParams(params);
                image.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));

                if(pa.bitmap!=null){
                    Bitmap mybitmap = Bitmap.createScaledBitmap(pa.bitmap, 400, 500, true);
                    image.setImageBitmap(mybitmap);
                }
                linearLayout.addView(image);
                final int paId2 = pa.id;
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImageProfile(paId2*15,position);
                    }
                });

            }
            holder.ll_jawaban1.addView(linearLayout);

        }/*else if(pa!=null && pa.jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox){
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
            int heigth = displayMetrics.heightPixels / 10;
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(width, heigth);
            layoutParams2.gravity = Gravity.CENTER_HORIZONTAL;
            final EditText etTest = new EditText(ctx);
            etTest.setText("");
            etTest.setHint("Please fill...");
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

        }*/else if(pa!=null && pa.jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox){
            TextView tvPertanyaan = new TextView(ctx);
            tvPertanyaan.setText(pa.txtPertanyaan);
            holder.lyt_pertayaan.addView(tvPertanyaan);


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
            linearLayout.addView(etTest);
            if (pa.bitImage){
                TextView tvPicture = new TextView(ctx);
                tvPicture.setText("Upload foto");
                tvPicture.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                tvPicture.setGravity(Gravity.CENTER);
                linearLayout.addView(tvPicture);
                View v = new ImageView(ctx);
                ImageView image;
                image = new ImageView(v.getContext());
                image.setId(pa.id*15);
                image.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));

                if(pa.bitmap!=null){
                    Bitmap mybitmap = Bitmap.createScaledBitmap(pa.bitmap, 400, 500, true);
                    image.setImageBitmap(mybitmap);
                }
                linearLayout.addView(image);
                final int paId2 = pa.id;
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImageProfile(paId2*15,position);
                    }
                });

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
             findViewId(p, view,position);
             if (position==items.size()-1){
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
            });


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
