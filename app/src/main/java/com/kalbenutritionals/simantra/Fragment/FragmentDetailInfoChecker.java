package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterExpandableList;
import com.kalbenutritionals.simantra.CustomView.Adapter.LineItemDecoration;
import com.kalbenutritionals.simantra.CustomView.Utils.OnReceivedData;
import com.kalbenutritionals.simantra.CustomView.Utils.ViewAnimation;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.Jawaban;
import com.kalbenutritionals.simantra.ViewModel.VmListAnswerView;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapterPertanyaan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentDetailInfoChecker extends Fragment implements OnReceivedData {
    View v;
    Unbinder unbinder;
    @BindView(R.id.bt_toggle_pic)
    ImageButton btTogglePic;
    @BindView(R.id.lyt_expand_passenger)
    LinearLayout lytExpandPassenger;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.bt_toggle_optional)
    ImageButton btToggleOptional;
    @BindView(R.id.bt_toggle_mandatory)
    ImageButton btToggleMandatory;
    @BindView(R.id.lyt_expand_optional)
    LinearLayout lytExpandOptional;
    @BindView(R.id.lyt_expand_mandatory)
    LinearLayout lytExpandMandatory;
    @BindView(R.id.bt_hide_optional)
    Button btHideOptional;
    @BindView(R.id.bt_hide_mandatory)
    Button btHideMandatory;
    @BindView(R.id.rvOptional)
    RecyclerView rvOptional;
    @BindView(R.id.rvMandatory)
    RecyclerView rvMandatory;
    @BindView(R.id.btnValidate)
    Button btnValidate;
    static AdapterExpandableList mAdapter;
    List<VmListItemAdapterPertanyaan> items;
    static List<View> listAnswer = new ArrayList<>();

    public static int GLOBAL_PICK_PICTURE_ID = 1;
    public static int GLOBAL_PICK_PICTURE_QUEST_ID = 1;
    HashMap<Integer, View> HMPertanyaan1 = new HashMap<Integer, View>();
    HashMap<String, String> HMPertanyaan2 = new HashMap<String, String>();
    HashMap<String, String> HMJawaban = new HashMap<String, String>();
    public static Uri uriImage, selectedImage;
    public static int CAMERA_REQUEST_QUESTION = 1;
    static List<VmListItemAdapterPertanyaan> ltDataPertanyaan = new ArrayList<>();
    static List<VmListAnswerView> ListAnswerView = new ArrayList<>();
    private LinearLayout linearLayout;
    Context context;

    public FragmentDetailInfoChecker() {

    }

    public static FragmentDetailInfoChecker newInstance() {
        FragmentDetailInfoChecker fragment = new FragmentDetailInfoChecker();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_transaksi_transpoter, container, false);
        unbinder = ButterKnife.bind(this, v);
        context = getActivity().getApplicationContext();
        rvOptional.setNestedScrollingEnabled(false);
        rvMandatory.setNestedScrollingEnabled(false);
        btToggleOptional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionOptional(btToggleOptional);
            }
        });
        btToggleMandatory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionMandatory(btToggleMandatory);
            }
        });
        btHideOptional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionOptional(btToggleOptional);

            }
        });
        btHideMandatory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionMandatory(btToggleMandatory);
            }
        });
        btTogglePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionPic(btTogglePic);
            }
        });
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        generateDat();
    }

    private void toggleSectionOptional(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            /*ViewAnimation.expand(lytExpandOptional, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    ClsTools.nestedScrollTo(nestedScrollView, lytExpandOptional);
                }
            });*/
            lytExpandOptional.setVisibility(View.VISIBLE);
        } else {
//            ViewAnimation.collapse(lytExpandOptional);
            lytExpandOptional.setVisibility(View.GONE);
        }
    }

    private void toggleSectionMandatory(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            /*ViewAnimation.expand(lytExpandMandatory, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    ClsTools.nestedScrollTo(nestedScrollView, lytExpandMandatory);
                }
            });*/
            lytExpandMandatory.setVisibility(View.VISIBLE);
        } else {
//            ViewAnimation.collapse(lytExpandMandatory);
            lytExpandMandatory.setVisibility(View.GONE);
        }
    }

    private void toggleSectionPic(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lytExpandPassenger, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
//                    ClsTools.nestedScrollTo(nestedScrollView, lytExpandPassenger);
                }
            });
        } else {
            ViewAnimation.collapse(lytExpandPassenger);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    private void getDataValidate() {
        int intItemCount = mAdapter.getItemCount();

    }

    public void generateDat() {
        rvOptional.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOptional.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvOptional.setHasFixedSize(true);

//        List<Social> items = getData(this);

        ltDataPertanyaan = getData(getActivity());
        toggleSectionOptional(btToggleOptional);
        //set data and list adapter
//        CustomAdapter mAdapter = new CustomAdapter(getActivity(), mItems);
        mAdapter = new AdapterExpandableList(getActivity(), ltDataPertanyaan);
        rvOptional.setAdapter(mAdapter);
        mAdapter.sendData(FragmentDetailInfoChecker.this);
    }

    public List<VmListItemAdapterPertanyaan> getData(Context ctx) {
        items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.social_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.social_names);


        try {
            List<ClsmPertanyaan> pertanyaans = new RepomPertanyaan(context).findAll();
            for (ClsmPertanyaan pertanyaan :
                    pertanyaans) {

                VmListItemAdapterPertanyaan obj = new VmListItemAdapterPertanyaan();
                obj.id = pertanyaan.getIntPertanyaanId();
                obj.image = 1;
                obj.txtPertanyaan = pertanyaan.getTxtPertanyaan();
                obj.bitImage = pertanyaan.isBolHavePhoto();
                obj.bitValid = true;
                obj.message = "";
                obj.bolHaveAnswer = pertanyaan.isBolHaveAnswer();
                obj.jenisPertanyaan = pertanyaan.getIntJenisPertanyaanId();
                List<ClsmJawaban> jawabans1 = new RepomJawaban(context).findByHeader(pertanyaan.getIntPertanyaanId());
                List<Jawaban> jawabans = new ArrayList<>();

                for (ClsmJawaban jawaban :
                        jawabans1) {
                    Jawaban jwbn = new Jawaban();
                    jwbn.idPertanyaan = jawaban.getIdPertanyaan();
                    jwbn.idJawaban = jawaban.getIdJawaban();
                    jwbn.jawaban = jawaban.getTxtJawaban();
                    jwbn.bitChoosen = jawaban.isBitChoosen();
                    jawabans.add(jwbn);
                }
                obj.jawabans = jawabans;
                items.add(obj);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*for (int i = 0; i < 4; i++) {
            Jawaban jwbn = new Jawaban();
            jwbn.idPertanyaan = i;
            jwbn.idJawaban = "idJawaban" + i;
            jwbn.jawaban = "jawaban" + i;
            jawabans.add(jwbn);
        }*/
        /*for (int i = 1; i < 8; i++) {
            VmListItemAdapterPertanyaan obj = new VmListItemAdapterPertanyaan();
            obj.id = i * 12;
            obj.image = drw_arr.getResourceId(i, -1);
            obj.txtPertanyaan = name_arr[i];
            obj.bitImage = 1;
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            obj.jawabans = jawabans;
            obj.bitValid = true;
            obj.message = "";
            if (i > 3) {
                obj.jenisPertanyaan = i - 3;
            } else {
                obj.jenisPertanyaan = i;
            }
            if (i % 2 == 0) {
                obj.bitImage = 1;
            } else {
                obj.bitImage = 0;
            }

            items.add(obj);
        }*/
        return items;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnValidate)
    public void onViewClicked() {
        boolean bolValid = false;
        LinearLayout ln = (LinearLayout)rvOptional.getChildAt(0).findViewById(ListAnswerView.get(0).getIntPertanyaanId()*24);
        int size = ln.getChildCount();
        for (int i = 0; i < size; i++){
            View nextChild = ln.getChildAt(i);
            if (nextChild instanceof EditText) {
                EditText editText = (EditText) nextChild;
                String text = editText.getText().toString();
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
//                if (editText.getText().toString().trim().equals("")) {
//                    ltDataPertanyaan.get(position).bitValid = false;
//                    ltDataPertanyaan.get(position).message = "Please fill this text ...";
//                } else {
//                    ltDataPertanyaan.get(position).bitValid = true;
//                }
            }
        }
        for (int i = 0; i < ListAnswerView.size(); i++) {
            int intPertanyaanId = ListAnswerView.get(i).getIntPertanyaanId();
            int position = ListAnswerView.get(i).getIntPosition();
            View viewAnswer = ListAnswerView.get(i).getVwJawaban();
            /*for (VmListItemAdapterPertanyaan pertanyaan :
                    ltDataPertanyaan) {*/
            if (viewAnswer instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) viewAnswer;
                if (layout == (linearLayout = (LinearLayout) viewAnswer)) {
                    int count = 0;
                    for (int x = 0; x < linearLayout.getChildCount(); x++) {
                        View nextChild = linearLayout.getChildAt(x);
                        int intNextChildId = nextChild.getId();
                        if (nextChild instanceof CheckBox) {
                            CheckBox checkBox = (CheckBox) nextChild;
                            if (checkBox.isChecked()) {
                                ltDataPertanyaan.get(position).jawabans.get(x).bitChoosen = true;
                                count++;
                            }else{
                                ltDataPertanyaan.get(position).jawabans.get(x).bitChoosen = false;
                            }
                        }
                        if (count == 0){
                            ltDataPertanyaan.get(position).bitValid = false;
                            ltDataPertanyaan.get(position).message = "Checkbox at least 1 option ...";
                        }
                        if (nextChild instanceof RadioGroup) {
                            RadioGroup radioGroup = (RadioGroup) nextChild;
                            if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                                ltDataPertanyaan.get(position).bitValid = false;
                                ltDataPertanyaan.get(position).message = "Choose 1 option ...";
                            } else {
                                ltDataPertanyaan.get(position).bitValid = true;
                            }
                        }
                        if (nextChild instanceof EditText) {
                            EditText editText = (EditText) nextChild;
                            if (editText.getText().toString().trim().equals("")) {
                                ltDataPertanyaan.get(position).bitValid = false;
                                ltDataPertanyaan.get(position).message = "Please fill this text ...";
                            } else {
                                ltDataPertanyaan.get(position).bitValid = true;
                            }
                        }
                        if (nextChild instanceof ImageView) {
                            ImageView imageView = (ImageView) nextChild;
                            String jenisPertanyaan = "";
                            if (ltDataPertanyaan.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox){
                                jenisPertanyaan = "Please fill text ";
                            }else if (ltDataPertanyaan.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton){
                                jenisPertanyaan = "Choose 1 option ";
                            }else if(ltDataPertanyaan.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox){
                                jenisPertanyaan = "Checkbox at least 1 option ";
                            }

                            if (ltDataPertanyaan.get(position).path == null && ltDataPertanyaan.get(position).bitValid == false) {
                                ltDataPertanyaan.get(position).bitValid = false;
                                ltDataPertanyaan.get(position).message = jenisPertanyaan +" and take a picture...";
                            } else if (ltDataPertanyaan.get(position).path == null && ltDataPertanyaan.get(position).bitValid == true) {
                                ltDataPertanyaan.get(position).bitValid = false;
                                ltDataPertanyaan.get(position).message = "Please take a picture...";
                            } else if (ltDataPertanyaan.get(position).path != null && ltDataPertanyaan.get(position).bitValid == false) {
                                ltDataPertanyaan.get(position).bitValid = false;
                                ltDataPertanyaan.get(position).message = jenisPertanyaan;
                            } else {
                                ltDataPertanyaan.get(position).bitValid = true;
                            }
                        }
                    }

                }

            }
            /*if (viewAnswer instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) viewAnswer;
                if (layout == (linearLayout = (LinearLayout) viewAnswer)) {
                    for (int x = 0; x < linearLayout.getChildCount(); x++) {
                        View nextChild = linearLayout.getChildAt(x);
                        if (nextChild instanceof RadioGroup) {
                            RadioGroup radioGroup = (RadioGroup) nextChild;
                            if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                                String msg = "Please select one...";
                                bolValid = false;
                            }
                        }
                    }
                }

            }
            if (viewAnswer instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) viewAnswer;
                if (layout == (linearLayout = (LinearLayout) viewAnswer)) {
                    for (int x = 0; x < linearLayout.getChildCount(); x++) {
                        View nextChild = linearLayout.getChildAt(x);
                        if (nextChild instanceof EditText) {
                            EditText editText = (EditText) nextChild;
                            if (editText.getText().toString().trim().equals("")) {
                                String msg = "Please fill thid field...";
                                bolValid = false;
                            }
                        }
                    }
                }

            }
            if (viewAnswer instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) viewAnswer;
                if (layout == (linearLayout = (LinearLayout) viewAnswer)) {
                    for (int x = 0; x < linearLayout.getChildCount(); x++) {
                        View nextChild = linearLayout.getChildAt(x);
                        if (nextChild instanceof EditText) {
                            EditText editText = (EditText) nextChild;
                            if (editText.getText().toString().trim().equals("")) {
                                String msg = "Please fill thid field...";
                                bolValid = false;

                            }
                        }
                        if (nextChild instanceof ImageView) {
                            ImageView imageView = (ImageView) nextChild;
                            if (nextChild.getResources() == null) {
                                String msg = "Please take a picture...";
                                bolValid = false;
                            }
                        }
                    }
                }


            }*/
//            }
        }
        mAdapter.notifyDataSetChanged();
        rvOptional.setAdapter(mAdapter);
    }
        /*if (listAnswer.size() > 0) {
            for (int i = 0; i < listAnswer.size(); i++) {
                VmListItemAdapterPertanyaan pertanyaan = HMPertanyaan1.get()

                if (listAnswer.get(i) instanceof ImageView) {

                }
            }
        }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_QUESTION) {
            if (resultCode == -1) {
                for (int i = 0; i < ListAnswerView.size(); i++) {
                    View view = ListAnswerView.get(i).getVwJawaban();
                    if (view instanceof LinearLayout) {
                        LinearLayout lyt = (LinearLayout) view;
                        for (int x = 0; x < lyt.getChildCount(); x++) {
                            View nextChild = lyt.getChildAt(x);
                            if (nextChild instanceof ImageView) {
                                ImageView imageView = (ImageView) nextChild;
                                if (imageView.getId() == GLOBAL_PICK_PICTURE_ID) {
                                    try {
//                                Bitmap thePic = BitmapFactory.decodeFile(uriImage.getPath());
                                        Bitmap thePic = new PickImage().decodeStreamReturnBitmap(AdapterExpandableList.ctx, uriImage);
                                        new PickImage().previewCapturedImage(imageView, thePic, 400, 500);
                                        ltDataPertanyaan.get(GLOBAL_PICK_PICTURE_QUEST_ID).path = uriImage;
                                        ltDataPertanyaan.get(GLOBAL_PICK_PICTURE_QUEST_ID).bitmap = thePic;
                                    } catch (Exception ex) {
                                        String a = ex.getMessage();
                                    }

                                }
                            }
                        }
                    }
                    /*if (listAnswer.get(i) instanceof ImageView) {
                        ImageView imageView = (ImageView) listAnswer.get(i);
                        if (imageView.getId() == GLOBAL_PICK_PICTURE_ID) {
                            try {
//                                Bitmap thePic = BitmapFactory.decodeFile(uriImage.getPath());
                                Bitmap thePic = new PickImage().decodeStreamReturnBitmap(AdapterExpandableList.ctx, uriImage);
                                new PickImage().previewCapturedImage(imageView, thePic, 400, 500);
                            } catch (Exception ex) {
                                String a = ex.getMessage();
                            }

                        }
                    }*/
                }
            }
        }
    }

    @Override
    public void onDataTransportReceived(List<View> listAnswer, HashMap<Integer, View> HMPertanyaan1, List<VmListAnswerView> ListAnswerView) {
        this.listAnswer = listAnswer;
        this.HMPertanyaan1 = HMPertanyaan1;
//        this.ListAnswerView = new ArrayList<>();
        this.ListAnswerView = ListAnswerView;

    }
}
