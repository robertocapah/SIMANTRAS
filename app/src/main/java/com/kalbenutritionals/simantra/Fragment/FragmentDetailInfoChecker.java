package com.kalbenutritionals.simantra.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbenutritionals.simantra.BL.BLActivity;
import com.kalbenutritionals.simantra.BL.BLHelper;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterExpandableList;
import com.kalbenutritionals.simantra.CustomView.Adapter.LineItemDecoration;
import com.kalbenutritionals.simantra.CustomView.Utils.OnReceivedData;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.Jawaban;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasic;
import com.kalbenutritionals.simantra.ViewModel.VmImageContainer;
import com.kalbenutritionals.simantra.ViewModel.VmListAnswerView;
import com.kalbenutritionals.simantra.ViewModel.VmListItemAdapterPertanyaan;
import com.kalbenutritionals.simantra.ViewModel.VmTJawabanUser;
import com.kalbenutritionals.simantra.ViewModel.VmTJawabanUserDetail;

import org.json.JSONArray;

import java.io.File;
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
    boolean statusValid = false;
    boolean statusRejected = false;
    String txtMsg = "";
    AdapterExpandableList mAdapterOptional;
    AdapterExpandableList mAdapterMandatory;
    AdapterExpandableList mAdapterFooter;
    List<VmListItemAdapterPertanyaan> items;

    public static int GLOBAL_PICK_PICTURE_ID = 1;
    public static int GLOBAL_PICK_PICTURE_QUEST_ID = 1;
    public static int GLOBAL_PICK_PICTURE_POSITION_ID = 1;
    HashMap<Integer, View> HMPertanyaan1 = new HashMap<Integer, View>();
    HashMap<String, String> HMPertanyaan2 = new HashMap<String, String>();
    HashMap<String, String> HMJawaban = new HashMap<String, String>();
    public static Uri uriImage;
    public static String imgName;
    public static int CAMERA_REQUEST_QUESTION = 1;
    List<VmListItemAdapterPertanyaan> ltDataPertanyaanOptional = new ArrayList<>();
    List<VmListItemAdapterPertanyaan> ltDataPertanyaanMandatory = new ArrayList<>();
    List<VmListItemAdapterPertanyaan> ltDataPertanyaanFooter = new ArrayList<>();
    List<VmListAnswerView> ListAnswerViewOptional = new ArrayList<>();
    List<VmListAnswerView> ListAnswerViewMandatory = new ArrayList<>();
    List<VmListAnswerView> ListAnswerViewFooter = new ArrayList<>();
    //    List<VmBasicListView> ltDataPIC = new ArrayList<>();
    public List<VmAdapterBasic> ListRejection = new ArrayList<>();
    @BindView(R.id.rvFooter)
    RecyclerView rvFooter;
    private LinearLayout linearLayout;
    Context context;
    List<VmTJawabanUser> jawabanListFinal = new ArrayList<>();
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
        rvFooter.setNestedScrollingEnabled(false);

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
//            ViewAnimation.expand(lytExpandPassenger, new ViewAnimation.AnimListener() {
//                @Override
//                public void onFinish() {
////                    ClsTools.nestedScrollTo(nestedScrollView, lytExpandPassenger);
//                    lytExpandPassenger.setVisibility(View.VISIBLE);
//                }
//            });
            lytExpandPassenger.setVisibility(View.VISIBLE);
        } else {
//            ViewAnimation.collapse(lytExpandPassenger);
            lytExpandPassenger.setVisibility(View.GONE);
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
        int intItemCount = mAdapterOptional.getItemCount();

    }

    public void generateDat() {
        rvOptional.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOptional.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvOptional.setHasFixedSize(true);
        rvMandatory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMandatory.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvMandatory.setHasFixedSize(true);
        rvFooter.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFooter.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvFooter.setHasFixedSize(true);

//        List<Social> items = getData(this);

        ltDataPertanyaanOptional = getData(getActivity(), ClsHardCode.HEADER);
        ltDataPertanyaanMandatory = getData(getActivity(), ClsHardCode.BODY);
        ltDataPertanyaanFooter = getData(getActivity(), ClsHardCode.FOOTER);

        toggleSectionOptional(btToggleOptional);
        toggleSectionMandatory(btToggleMandatory);
        toggleSectionPic(btTogglePic);
        //set data and list adapter
//        CustomAdapter mAdapterOptional = new CustomAdapter(getActivity(), mItems);
        mAdapterOptional = new AdapterExpandableList(getActivity(), ltDataPertanyaanOptional);
        mAdapterMandatory = new AdapterExpandableList(getActivity(), ltDataPertanyaanMandatory);
        mAdapterFooter = new AdapterExpandableList(getActivity(), ltDataPertanyaanFooter);

        rvOptional.setAdapter(mAdapterOptional);
        rvMandatory.setAdapter(mAdapterMandatory);
        rvFooter.setAdapter(mAdapterFooter);

        mAdapterOptional.sendData(FragmentDetailInfoChecker.this);
        mAdapterMandatory.sendData(FragmentDetailInfoChecker.this);
        mAdapterFooter.sendData(FragmentDetailInfoChecker.this);
    }

    public List<VmListItemAdapterPertanyaan> getData(Context ctx, int jenis) {
        items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.social_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.social_names);

        try {
            List<ClsmPertanyaan> pertanyaans = new ArrayList<>();
            if (jenis == ClsHardCode.HEADER) {
                pertanyaans = new RepomPertanyaan(context).findQuestion(ClsHardCode.HEADER);
            } else if (jenis == ClsHardCode.BODY) {
                pertanyaans = new RepomPertanyaan(context).findQuestion(ClsHardCode.BODY);
            }else if (jenis == ClsHardCode.FOOTER) {
                pertanyaans = new RepomPertanyaan(context).findQuestion(ClsHardCode.FOOTER);
            }
            items = new ArrayList<>();
            for (ClsmPertanyaan pertanyaan :
                    pertanyaans) {

                VmListItemAdapterPertanyaan obj = new VmListItemAdapterPertanyaan();
                obj.id = pertanyaan.getIntPertanyaanId();
                obj.image = 1;
                obj.txtPertanyaan = pertanyaan.getTxtPertanyaan();
                obj.bitImage = pertanyaan.isBolHavePhoto();
                if (obj.bitImage) {
                    obj.countImage = 4;
                }

                obj.bitValid = true;
                obj.message = "";
                obj.bolHaveAnswer = pertanyaan.isBolHaveAnswer();
                obj.jenisPertanyaan = pertanyaan.getIntJenisPertanyaanId();
                obj.intValidateId = pertanyaan.getIntValidateID();
                obj.intPositionId = pertanyaan.getIntLocationDocsId();
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

                List<VmImageContainer> containerList = new ArrayList<>();
                for (int i = 0; i < pertanyaan.getIntPhotoNeeded(); i++) {
                    VmImageContainer imageContainer = new VmImageContainer();
                    imageContainer.setPosition(i);
                    imageContainer.setPath(null);
                    imageContainer.setBitmap(null);
                    containerList.add(imageContainer);
                }
                obj.listImage = containerList;
                items.add(obj);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    LinearLayout ln;

    @OnClick(R.id.btnValidate)
    public void onViewClicked() {

    }
    public List<VmTJawabanUser> validateAll(){
        jawabanListFinal = new ArrayList<>();
//        boolean validOptional = validateOptionals();
        boolean validMandatory = validateMandatory();
        boolean validFooter =validateFooter();
        if (validMandatory&&validFooter) {
            statusValid = true;
            jawabanListFinal = saveData();
            mAdapterMandatory.notifyDataSetChanged();
            rvMandatory.setAdapter(mAdapterMandatory);

//            mAdapterOptional.notifyDataSetChanged();
//            rvOptional.setAdapter(mAdapterOptional);

            mAdapterFooter.notifyDataSetChanged();
            rvFooter.setAdapter(mAdapterFooter);
//            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
        } else {
            if (!validMandatory){
                txtMsg = "Please take all picture at mandatory conditions !";
            }else if (!validateFooter()){
                txtMsg = "Mandatory Question must be filled out !";
            }
            statusValid = false;
            mAdapterMandatory.notifyDataSetChanged();
            rvMandatory.setAdapter(mAdapterMandatory);

//            mAdapterOptional.notifyDataSetChanged();
//            rvOptional.setAdapter(mAdapterOptional);

            mAdapterFooter.notifyDataSetChanged();
            rvFooter.setAdapter(mAdapterFooter);
        }

        return jawabanListFinal;
    }

    private boolean validateOptionals(){
        boolean bolValid = true;
        for (int i = 0; i < ListAnswerViewOptional.size(); i++) {
            List<Jawaban> jawabans = new ArrayList<>();
            int intPertanyaanId = ListAnswerViewOptional.get(i).getIntPertanyaanId();
            int position = ListAnswerViewOptional.get(i).getIntPosition();
            if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 21);
            }

            int count = 0;
            for (int x = 0; x < ln.getChildCount(); x++) {
                View nextChild = ln.getChildAt(x);
                int intNextChildId = nextChild.getId();
                if (nextChild instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) nextChild;
                    if (checkBox.isChecked()) {
                        ltDataPertanyaanOptional.get(position).jawabans.get(x).bitChoosen = true;
                        count++;
                    } else {
                        ltDataPertanyaanOptional.get(position).jawabans.get(x).bitChoosen = false;
                    }
                }
                if (count == 0 && ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    bolValid = false;
//                    ltDataPertanyaanOptional.get(position).bitValid = false;
//                    ltDataPertanyaanOptional.get(position).message = "Checkbox at least 1 option";
                } else if (count > 0 && ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    bolValid = true;
                    ltDataPertanyaanOptional.get(position).bitValid = true;
                    ltDataPertanyaanOptional.get(position).message = "";
                }
                if (nextChild instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) nextChild;
                    if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                        bolValid = false;
//                        ltDataPertanyaanOptional.get(position).bitValid = false;
//                        ltDataPertanyaanOptional.get(position).message = "Choose 1 option";
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanOptional.get(position).jawabans) {
                            ltDataPertanyaanOptional.get(position).jawabans.get(index).bitChoosen = false;
                            index++;
                        }
                    } else {
                        bolValid = true;
                        ltDataPertanyaanOptional.get(position).bitValid = true;
                        ltDataPertanyaanOptional.get(position).message = "";
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanOptional.get(position).jawabans) {
                            if (jwb.idJawaban == posisi) {
                                ltDataPertanyaanOptional.get(position).jawabans.get(index).bitChoosen = true;
                            } else {
                                ltDataPertanyaanOptional.get(position).jawabans.get(index).bitChoosen = false;
                            }
                            index++;
                        }
//                        ltDataPertanyaanOptional.get(position).jawabans.get(x).idJawaban = radioGroup.getCheckedRadioButtonId();
                    }
                }
                if (nextChild instanceof EditText) {
                    EditText editText = (EditText) nextChild;
                    if (editText.getText().toString().trim().equals("")) {
                        bolValid = false;
//                        ltDataPertanyaanOptional.get(position).bitValid = false;
//                        ltDataPertanyaanOptional.get(position).message = "Please fill this text";

                    } else {
                        bolValid = true;
                        ltDataPertanyaanOptional.get(position).bitValid = true;
                        ltDataPertanyaanOptional.get(position).message = "";
                    }
                    Jawaban jwbn = new Jawaban();
                    jwbn.idPertanyaan = ltDataPertanyaanOptional.get(position).id;
                    jwbn.idJawaban = 0;
                    jwbn.jawaban = editText.getText().toString();
                    jwbn.bitChoosen = false;
                    jawabans.add(jwbn);
                    ltDataPertanyaanOptional.get(position).jawabans = jawabans;
                }
                if (nextChild instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) nextChild;
                    boolean bolValidImg = true;
                    for (VmImageContainer data : ltDataPertanyaanOptional.get(position).listImage) {
                        if (data.getPath() == null) {
                            bolValid = false;
                            bolValidImg = false;
                        }
                    }

                    if (bolValidImg == false) {
                        ltDataPertanyaanOptional.get(position).bitValid = false;
                        ltDataPertanyaanOptional.get(position).message = "Please take all picture...";
                    }
                    else {
                        ltDataPertanyaanOptional.get(position).bitValid = true;
                        ltDataPertanyaanOptional.get(position).message = "";
                        bolValid = true;
                    }
                }

                if (nextChild instanceof ImageView) {
                    ImageView imageView = (ImageView) nextChild;
                    if (ltDataPertanyaanOptional.get(position).path == null) {
                        bolValid = false;
                    }
                    if (bolValid == false) {
                        ltDataPertanyaanOptional.get(position).bitValid = false;
                        ltDataPertanyaanOptional.get(position).message = "Please take all picture...";
                    }
                    else {
                        ltDataPertanyaanOptional.get(position).bitValid = true;
                        ltDataPertanyaanOptional.get(position).message = "";
                        bolValid = true;
                    }
                }
            }
        }
        return bolValid;
    }

    private boolean validateMandatory(){
        boolean bolValid = true;
        ListRejection = new ArrayList<>();
        for (int i = 0; i < ListAnswerViewMandatory.size(); i++) {
            List<Jawaban> jawabans = new ArrayList<>();
            int intPertanyaanId = ListAnswerViewMandatory.get(i).getIntPertanyaanId();
            int position = ListAnswerViewMandatory.get(i).getIntPosition();
            if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 21);
            }

            int count = 0;
            int countAll = 0;
            for (int x = 0; x < ln.getChildCount(); x++) {
                View nextChild = ln.getChildAt(x);
                int intNextChildId = nextChild.getId();
                if (nextChild instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) nextChild;
                    countAll++;
                    if (checkBox.isChecked()) {
                        ltDataPertanyaanMandatory.get(position).jawabans.get(x).bitChoosen = true;
                        count++;
                    } else {
                        ltDataPertanyaanMandatory.get(position).jawabans.get(x).bitChoosen = false;
                        jawabanCheckbox = jawabanCheckbox+ ltDataPertanyaanMandatory.get(position).jawabans.get(x).jawaban+", ";
                    }
                }

                if (nextChild instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) nextChild;
                    if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanMandatory.get(position).jawabans) {
                            ltDataPertanyaanMandatory.get(position).jawabans.get(index).bitChoosen = false;
                            index++;
                        }
                    } else {
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanMandatory.get(position).jawabans) {
                            if (jwb.idJawaban == posisi) {
                                ltDataPertanyaanMandatory.get(position).jawabans.get(index).bitChoosen = true;
                            } else {
                                ltDataPertanyaanMandatory.get(position).jawabans.get(index).bitChoosen = false;
                            }
                            index++;
                        }
                    }
                }
                if (nextChild instanceof EditText) {
                    EditText editText = (EditText) nextChild;
//                    if (editText.getText().toString().trim().equals("")) {
//                        bolValid = false;
////                        ltDataPertanyaanMandatory.get(position).bitValid = false;
//                        ltDataPertanyaanMandatory.get(position).message = "Please fill this text";
//
//                    } else {
//                        bolValid = true;
//                        ltDataPertanyaanMandatory.get(position).bitValid = true;
//                        ltDataPertanyaanMandatory.get(position).message = "";
//                    }
                    Jawaban jwbn = new Jawaban();
                    jwbn.idPertanyaan = ltDataPertanyaanMandatory.get(position).id;
                    jwbn.idJawaban = 0;
                    jwbn.jawaban = editText.getText().toString();
                    jwbn.bitChoosen = false;
                    jawabans.add(jwbn);
                    ltDataPertanyaanMandatory.get(position).jawabans = jawabans;
                }
                if (nextChild instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) nextChild;
                    boolean bolValidImg = true;
                    for (VmImageContainer data : ltDataPertanyaanMandatory.get(position).listImage) {
                        if (data.getPath() == null) {
                            bolValidImg = false;
                        }
                    }

                    if (bolValidImg == false) {
                        bolValid = false;
                        ltDataPertanyaanMandatory.get(position).bitValid = false;
                        ltDataPertanyaanMandatory.get(position).message = " Please take all picture...";
                    }
                    else {
                        ltDataPertanyaanMandatory.get(position).bitValid = true;
                        ltDataPertanyaanMandatory.get(position).message = "";
                        bolValid = true;
                    }
                }


            }

        }
        return bolValid;
    }

    private boolean validateFooter(){
        boolean bolValid = true;
        for (int i = 0; i < ListAnswerViewFooter.size(); i++) {
            List<Jawaban> jawabans = new ArrayList<>();
            int intPertanyaanId = ListAnswerViewFooter.get(i).getIntPertanyaanId();
            int position = ListAnswerViewFooter.get(i).getIntPosition();
            if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 21);
            }

            int count = 0;
            for (int x = 0; x < ln.getChildCount(); x++) {
                View nextChild = ln.getChildAt(x);
                int intNextChildId = nextChild.getId();
                if (nextChild instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) nextChild;
                    if (checkBox.isChecked()) {
                        ltDataPertanyaanFooter.get(position).jawabans.get(x).bitChoosen = true;
                        count++;
                    } else {
                        ltDataPertanyaanFooter.get(position).jawabans.get(x).bitChoosen = false;
                    }
                }
                if (count == 0 && ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    bolValid = false;
                    ltDataPertanyaanFooter.get(position).bitValid = false;
                    ltDataPertanyaanFooter.get(position).message = "Checkbox at least 1 option";
                } else if (count > 0 && ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    bolValid = true;
                    ltDataPertanyaanFooter.get(position).bitValid = true;
                    ltDataPertanyaanFooter.get(position).message = "";
                }
                if (nextChild instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) nextChild;
                    if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                        bolValid = false;
                        ltDataPertanyaanFooter.get(position).bitValid = false;
                        ltDataPertanyaanFooter.get(position).message = "Choose 1 option";
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanFooter.get(position).jawabans) {
                            ltDataPertanyaanFooter.get(position).jawabans.get(index).bitChoosen = false;
                            index++;
                        }
                    } else {
                        bolValid = true;
                        ltDataPertanyaanFooter.get(position).bitValid = true;
                        ltDataPertanyaanFooter.get(position).message = "";
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanFooter.get(position).jawabans) {
                            if (jwb.idJawaban == posisi) {
                                ltDataPertanyaanFooter.get(position).jawabans.get(index).bitChoosen = true;
                            } else {
                                ltDataPertanyaanFooter.get(position).jawabans.get(index).bitChoosen = false;
                            }
                            index++;
                        }
//                        ltDataPertanyaanFooter.get(position).jawabans.get(x).idJawaban = radioGroup.getCheckedRadioButtonId();
                    }
                }
                if (nextChild instanceof EditText) {
                    EditText editText = (EditText) nextChild;
                    if (editText.getText().toString().trim().equals("")) {
                        bolValid = false;
                        ltDataPertanyaanFooter.get(position).bitValid = false;
                        ltDataPertanyaanFooter.get(position).message = "Please fill this text";

                    } else {
                        bolValid = true;
                        ltDataPertanyaanFooter.get(position).bitValid = true;
                        ltDataPertanyaanFooter.get(position).message = "";
                    }
                    Jawaban jwbn = new Jawaban();
                    jwbn.idPertanyaan = ltDataPertanyaanFooter.get(position).id;
                    jwbn.idJawaban = 0;
                    jwbn.jawaban = editText.getText().toString();
                    jwbn.bitChoosen = false;
                    jawabans.add(jwbn);
                    ltDataPertanyaanFooter.get(position).jawabans = jawabans;
                }
                if (nextChild instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) nextChild;
                    boolean bolValidImg = true;
                    for (VmImageContainer data : ltDataPertanyaanFooter.get(position).listImage) {
                        if (data.getPath() == null) {
                            bolValid = false;
                            bolValidImg = false;
                        }
                    }

                    if (bolValidImg == false && ltDataPertanyaanFooter.get(position).bitValid == false) {
                        ltDataPertanyaanFooter.get(position).bitValid = false;
                        ltDataPertanyaanFooter.get(position).message = ltDataPertanyaanFooter.get(position).message + " and take all picture...";
                    } else if (bolValidImg == false && ltDataPertanyaanFooter.get(position).bitValid == true) {
                        ltDataPertanyaanFooter.get(position).bitValid = false;
                        ltDataPertanyaanFooter.get(position).message = "Please take all picture...";
                    } else if (bolValidImg == true && ltDataPertanyaanFooter.get(position).bitValid == false) {
                        ltDataPertanyaanFooter.get(position).bitValid = false;
                        ltDataPertanyaanFooter.get(position).message = ltDataPertanyaanFooter.get(position).message + "...";
                    } else {
                        ltDataPertanyaanFooter.get(position).bitValid = true;
                        ltDataPertanyaanFooter.get(position).message = "";
                        bolValid = true;
                    }
                }

                if (nextChild instanceof ImageView) {
                    ImageView imageView = (ImageView) nextChild;
                    if (ltDataPertanyaanFooter.get(position).path == null) {
                        bolValid = false;
                    }
                    if (ltDataPertanyaanFooter.get(position).path == null && ltDataPertanyaanFooter.get(position).bitValid == false) {
                        ltDataPertanyaanFooter.get(position).bitValid = false;
                        ltDataPertanyaanFooter.get(position).message = ltDataPertanyaanFooter.get(position).message + " and take a picture...";
                    } else if (ltDataPertanyaanFooter.get(position).path == null && ltDataPertanyaanFooter.get(position).bitValid == true) {
                        ltDataPertanyaanFooter.get(position).bitValid = false;
                        ltDataPertanyaanFooter.get(position).message = "Please take a picture...";
                    } else if (ltDataPertanyaanFooter.get(position).path != null && ltDataPertanyaanFooter.get(position).bitValid == false) {
                        ltDataPertanyaanFooter.get(position).bitValid = false;
                        ltDataPertanyaanFooter.get(position).message = ltDataPertanyaanFooter.get(position).message + "...";
                    } else {
                        ltDataPertanyaanFooter.get(position).bitValid = true;
                        ltDataPertanyaanFooter.get(position).message = "";
                        bolValid = true;
                    }
                }
            }
        }
        return bolValid;
    }

    String jawabanCheckbox = "";

    public JSONArray getDataTransaction() {
        JSONArray json = new BLHelper().getDataTransaksiJsonArrayCommon(context,jawabanListFinal);
        return json;
    }
    public List<VmTJawabanUser> saveData() {
        List<VmTJawabanUser> tJawabanList = new ArrayList<>();
        for (int i = 0; i < ListAnswerViewOptional.size(); i++) {
            try {
                int intPertanyaanId = ListAnswerViewOptional.get(i).getIntPertanyaanId(); 
                int position = ListAnswerViewOptional.get(i).getIntPosition();
                ClsmPertanyaan pertanyaans = (ClsmPertanyaan) new RepomPertanyaan(context).findById(intPertanyaanId);
                VmTJawabanUser tJawaban = new VmTJawabanUser();
                List<VmTJawabanUser.imageModel> listImage = new ArrayList<>();
                tJawaban.setTxtTransJawabanId(new BLActivity().GenerateGuid());
                tJawaban.setIntPertanyaanId(intPertanyaanId);
                tJawaban.setIntTypePertanyaanId(pertanyaans.getIntJenisPertanyaanId());
                tJawaban.setBolHavePhoto(pertanyaans.isBolHavePhoto());
                tJawaban.setBolHaveAnswer(pertanyaans.isBolHaveAnswer());
                tJawaban.setIntFillHeaderId(pertanyaans.getIntFillHeaderId());

                if (pertanyaans.isBolHavePhoto()) {
                    for (int x = 0; x < ltDataPertanyaanOptional.get(position).listImage.size(); x++) {
                        if (ltDataPertanyaanOptional.get(position).listImage.get(x).getPath()!= null){

                            String dirA =ltDataPertanyaanOptional.get(position).listImage.get(x).getPath().getPath();
                            dirA = dirA.replaceAll(".+Android", "");
                            String dir = Environment.getExternalStorageDirectory()+"/Android/"+dirA;
                            File file = new File(dir);
                            String imageName = ltDataPertanyaanOptional.get(position).listImage.get(x).getImgName();
                            VmTJawabanUser.imageModel dtImage = new VmTJawabanUser().new imageModel(imageName,dir);

                            listImage.add(dtImage);
                        }
                    }
                }

                tJawaban.setDtImageModels(listImage);

                if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                    int a= ListAnswerViewOptional.get(i).getIntPertanyaanId();
                    int b = rvOptional.getChildCount();
                    ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 24);
                } else if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                    ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 22);
                } else if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 21);
                }
                int count = 0;
                List<VmTJawabanUserDetail> listJawaban = new ArrayList<>();
                for (int x = 0; x < ln.getChildCount(); x++) {
                    VmTJawabanUserDetail dtJawaban = new VmTJawabanUserDetail();
                    View nextChild = ln.getChildAt(x);
                    if (nextChild instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) nextChild;
//                        if (checkBox.isChecked()) {
//
//                        }
                        dtJawaban.setIntmJawabanId(checkBox.getId());
                        dtJawaban.setTxtJawaban(checkBox.getText().toString());
                        dtJawaban.setQualified(checkBox.isChecked());
                    }

                    if (nextChild instanceof RadioGroup) {
                        RadioGroup radioGroup = (RadioGroup) nextChild;
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        RadioButton rb = (RadioButton) rvOptional.getChildAt(i).findViewById(posisi);
                        if(posisi>-1){
                            String txtJawaban = rb.getText().toString();
                            dtJawaban.setIntmJawabanId(posisi);
                            dtJawaban.setTxtJawaban(txtJawaban);
                        }

                    }
                    if (nextChild instanceof EditText) {
                        EditText editText = (EditText) nextChild;
                        dtJawaban.setIntmJawabanId(0);
                        dtJawaban.setTxtJawaban(editText.getText().toString());
                    }
                    listJawaban.add(dtJawaban);
                }
                tJawaban.setJawabanUserDetailList(listJawaban);
                tJawabanList.add(tJawaban);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < ListAnswerViewMandatory.size(); i++) {
            try {
                int intPertanyaanId = ListAnswerViewMandatory.get(i).getIntPertanyaanId();
                int position = ListAnswerViewMandatory.get(i).getIntPosition();
                ClsmPertanyaan pertanyaans = (ClsmPertanyaan) new RepomPertanyaan(context).findById(intPertanyaanId);
                VmTJawabanUser tJawaban = new VmTJawabanUser();
                List<VmTJawabanUser.imageModel> listImage = new ArrayList<>();
                tJawaban.setTxtTransJawabanId(new BLActivity().GenerateGuid());
                tJawaban.setIntPertanyaanId(intPertanyaanId);
                tJawaban.setIntTypePertanyaanId(pertanyaans.getIntJenisPertanyaanId());
                tJawaban.setBolHavePhoto(pertanyaans.isBolHavePhoto());
                tJawaban.setBolHaveAnswer(pertanyaans.isBolHaveAnswer());
                tJawaban.setIntFillHeaderId(pertanyaans.getIntFillHeaderId());
                if (pertanyaans.isBolHavePhoto()) {
                    for (int x = 0; x < ltDataPertanyaanMandatory.get(position).listImage.size(); x++) {
                        if (ltDataPertanyaanMandatory.get(position).listImage.get(x).getPath()!= null){

                            String dirA =ltDataPertanyaanMandatory.get(position).listImage.get(x).getPath().getPath();
                            dirA = dirA.replaceAll(".+Android", "");
                            String dir = Environment.getExternalStorageDirectory()+"/Android/"+dirA;
                            File file = new File(dir);
                            String imageName = ltDataPertanyaanMandatory.get(position).listImage.get(x).getImgName();
                            VmTJawabanUser.imageModel dtImage = new VmTJawabanUser().new imageModel(imageName,dir);
                            listImage.add(dtImage);
                        }
                    }
                }
                tJawaban.setDtImageModels(listImage);

                if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                    ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 24);
                } else if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                    ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 22);
                } else if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 21);
                }
                int count = 0;
                String jawabanFinal = "";
                List<VmTJawabanUserDetail> listJawaban = new ArrayList<>();
                for (int x = 0; x < ln.getChildCount(); x++) {
                    VmTJawabanUserDetail dtJawaban = new VmTJawabanUserDetail();
                    View nextChild = ln.getChildAt(x);
                    if (nextChild instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) nextChild;
                        if (checkBox.isChecked()) {
                            dtJawaban.setIntmJawabanId(checkBox.getId());
                            dtJawaban.setTxtJawaban(checkBox.getText().toString());
                        }else {
                            count ++;
                            if (jawabanFinal.equals("")){
                                jawabanFinal = String.valueOf(count) + ". " + checkBox.getText().toString();
                            }else {
                                jawabanFinal += "\n" + String.valueOf(count) + ". " + checkBox.getText().toString();
                            }
                        }
                    }

                    if (nextChild instanceof RadioGroup) {
                        RadioGroup radioGroup = (RadioGroup) nextChild;
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        RadioButton rb = (RadioButton) rvMandatory.getChildAt(i).findViewById(posisi);
                        if(posisi>-1){
                            String txtJawaban = rb.getText().toString();
                            dtJawaban.setIntmJawabanId(posisi);
                            dtJawaban.setTxtJawaban(txtJawaban);
                        }

                    }
                    if (nextChild instanceof EditText) {
                        EditText editText = (EditText) nextChild;
                        dtJawaban.setIntmJawabanId(0);
                        dtJawaban.setTxtJawaban(editText.getText().toString());
                    }
                    listJawaban.add(dtJawaban);
                }
                tJawaban.setJawabanUserDetailList(listJawaban);
                tJawabanList.add(tJawaban);
                if (!jawabanFinal.equals("")){
                    VmAdapterBasic data = new VmAdapterBasic();
                    data.setTitle(ltDataPertanyaanMandatory.get(position).txtPertanyaan);
                    data.setSubtitle(jawabanFinal);
                    ListRejection.add(data);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        for (int i = 0; i < ListAnswerViewFooter.size(); i++) {
            try {
                int intPertanyaanId = ListAnswerViewFooter.get(i).getIntPertanyaanId();
                int position = ListAnswerViewFooter.get(i).getIntPosition();
                ClsmPertanyaan pertanyaans = (ClsmPertanyaan) new RepomPertanyaan(context).findById(intPertanyaanId);
                VmTJawabanUser tJawaban = new VmTJawabanUser();
                List<VmTJawabanUser.imageModel> listImage = new ArrayList<>();
                tJawaban.setTxtTransJawabanId(new BLActivity().GenerateGuid());
                tJawaban.setIntPertanyaanId(intPertanyaanId);
                tJawaban.setIntTypePertanyaanId(pertanyaans.getIntJenisPertanyaanId());
                tJawaban.setBolHavePhoto(pertanyaans.isBolHavePhoto());
                tJawaban.setBolHaveAnswer(pertanyaans.isBolHaveAnswer());
                tJawaban.setIntFillHeaderId(pertanyaans.getIntFillHeaderId());

                if (pertanyaans.isBolHavePhoto()) {
                    for (int x = 0; x < ltDataPertanyaanFooter.get(position).listImage.size(); x++) {
                        if (ltDataPertanyaanFooter.get(position).listImage.get(x).getPath()!= null){
                            String dirA =ltDataPertanyaanFooter.get(position).listImage.get(x).getPath().getPath();
                            dirA = dirA.replaceAll(".+Android", "");
                            String dir = Environment.getExternalStorageDirectory()+"/Android/"+dirA;
                            String imageName = ltDataPertanyaanFooter.get(position).listImage.get(x).getImgName();
                            VmTJawabanUser.imageModel dtImage = new VmTJawabanUser().new imageModel(imageName,dir);
                            listImage.add(dtImage);
                        }
                    }
                }

                tJawaban.setDtImageModels(listImage);

                if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                    ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 24);
                } else if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                    ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 22);
                } else if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 21);
                }

                List<VmTJawabanUserDetail> listJawaban = new ArrayList<>();
                for (int x = 0; x < ln.getChildCount(); x++) {
                    VmTJawabanUserDetail dtJawaban = new VmTJawabanUserDetail();
                    View nextChild = ln.getChildAt(x);
                    if (nextChild instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) nextChild;
                        if (checkBox.isChecked()) {
                            dtJawaban.setIntmJawabanId(checkBox.getId());
                            dtJawaban.setTxtJawaban(checkBox.getText().toString());
                        }
                    }

                    if (nextChild instanceof RadioGroup) {
                        RadioGroup radioGroup = (RadioGroup) nextChild;
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        RadioButton rb = (RadioButton) rvFooter.getChildAt(i).findViewById(posisi);
                        if(rb != null){
                            String txtJawaban = rb.getText().toString();
                            dtJawaban.setIntmJawabanId(posisi);
                            dtJawaban.setTxtJawaban(txtJawaban);
                        }
                    }
                    if (nextChild instanceof EditText) {
                        EditText editText = (EditText) nextChild;
                        dtJawaban.setIntmJawabanId(0);
                        dtJawaban.setTxtJawaban(editText.getText().toString());
                    }
                    listJawaban.add(dtJawaban);
                }
                tJawaban.setJawabanUserDetailList(listJawaban);
                tJawabanList.add(tJawaban);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return tJawabanList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_QUESTION) {
            if (resultCode == -1) {
                if (GLOBAL_PICK_PICTURE_POSITION_ID == ClsHardCode.HEADER){
                    setOnActivityResultOptional();
                }else if(GLOBAL_PICK_PICTURE_POSITION_ID == ClsHardCode.BODY){
                    setOnActivityResultMandatory();
                }else if(GLOBAL_PICK_PICTURE_POSITION_ID == ClsHardCode.FOOTER){
                    setOnActivityResultFooter();
                }

            }
        }
    }
    public void setOnActivityResultOptional(){
        for (int i = 0; i < rvOptional.getChildCount(); i++) {
            int position = ListAnswerViewOptional.get(i).getIntPosition();
            if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanOptional.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvOptional.getChildAt(i).findViewById(ListAnswerViewOptional.get(i).getIntPertanyaanId() * 21);
            }

            for (int x = 0; x < ln.getChildCount(); x++) {
                View nextChild = ln.getChildAt(x);
                if (nextChild instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) nextChild;
                    if (recyclerView.getId() == CAMERA_REQUEST_QUESTION) {
                        LinearLayout lnRc = (LinearLayout) recyclerView.getChildAt(GLOBAL_PICK_PICTURE_ID);
                        lnRc.setBackgroundColor(getContext().getResources().getColor(R.color.grey_3));
                        View child = lnRc.getChildAt(0);
                        ImageView imageView = (ImageView) child;
                        try {
                            Bitmap thePic = new PickImage().decodeStreamReturnBitmap(AdapterExpandableList.ctx, uriImage);
                            new PickImage().previewCapturedImage(imageView, thePic, 400, 500);
                            ltDataPertanyaanOptional.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setPath(uriImage);
                            ltDataPertanyaanOptional.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setImgName(imgName);
                            ltDataPertanyaanOptional.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setBitmap(thePic);
//                                            ltDataPertanyaanOptional.get(GLOBAL_PICK_PICTURE_QUEST_ID).path = uriImage;
//                                            ltDataPertanyaanOptional.get(GLOBAL_PICK_PICTURE_QUEST_ID).bitmap = thePic;
                        } catch (Exception ex) {
                            String a = ex.getMessage();
                        }
                    }
                }
            }
        }
    }
    public void setOnActivityResultMandatory(){
        for (int i = 0; i < rvMandatory.getChildCount(); i++) {
            int position = ListAnswerViewMandatory.get(i).getIntPosition();
            if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanMandatory.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvMandatory.getChildAt(i).findViewById(ListAnswerViewMandatory.get(i).getIntPertanyaanId() * 21);
            }

            for (int x = 0; x < ln.getChildCount(); x++) {
                View nextChild = ln.getChildAt(x);
                if (nextChild instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) nextChild;
                    if (recyclerView.getId() == CAMERA_REQUEST_QUESTION) {
                        LinearLayout lnRc = (LinearLayout) recyclerView.getChildAt(GLOBAL_PICK_PICTURE_ID);
                        lnRc.setBackgroundColor(getContext().getResources().getColor(R.color.grey_3));
                        View child = lnRc.getChildAt(0);
                        ImageView imageView = (ImageView) child;
                        try {
                            Bitmap thePic = new PickImage().decodeStreamReturnBitmap(AdapterExpandableList.ctx, uriImage);
                            new PickImage().previewCapturedImage(imageView, thePic, 400, 500);
                            ltDataPertanyaanMandatory.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setPath(uriImage);
                            ltDataPertanyaanMandatory.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setBitmap(thePic);
                            ltDataPertanyaanMandatory.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setImgName(imgName);
//                                            ltDataPertanyaanMandatory.get(GLOBAL_PICK_PICTURE_QUEST_ID).path = uriImage;
//                                            ltDataPertanyaanMandatory.get(GLOBAL_PICK_PICTURE_QUEST_ID).bitmap = thePic;
                        } catch (Exception ex) {
                            String a = ex.getMessage();
                        }
                    }
                }
            }
        }
    }
    public void setOnActivityResultFooter(){
        for (int i = 0; i < rvFooter.getChildCount(); i++) {
            int position = ListAnswerViewFooter.get(i).getIntPosition();
            if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 21);
            }

            for (int x = 0; x < ln.getChildCount(); x++) {
                View nextChild = ln.getChildAt(x);
                if (nextChild instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) nextChild;
                    if (recyclerView.getId() == CAMERA_REQUEST_QUESTION) {
                        LinearLayout lnRc = (LinearLayout) recyclerView.getChildAt(GLOBAL_PICK_PICTURE_ID);
                        lnRc.setBackgroundColor(getContext().getResources().getColor(R.color.grey_3));
                        View child = lnRc.getChildAt(0);
                        ImageView imageView = (ImageView) child;
                        try {
                            Bitmap thePic = new PickImage().decodeStreamReturnBitmap(AdapterExpandableList.ctx, uriImage);
                            new PickImage().previewCapturedImage(imageView, thePic, 400, 500);
                            ltDataPertanyaanFooter.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setPath(uriImage);
                            ltDataPertanyaanFooter.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setBitmap(thePic);
                            ltDataPertanyaanFooter.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setImgName(imgName);
//                                            ltDataPertanyaanFooter.get(GLOBAL_PICK_PICTURE_QUEST_ID).path = uriImage;
//                                            ltDataPertanyaanFooter.get(GLOBAL_PICK_PICTURE_QUEST_ID).bitmap = thePic;
                        } catch (Exception ex) {
                            String a = ex.getMessage();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDataTransportReceived
            (List<View> listAnswer, HashMap<Integer, View> HMPertanyaan1, List<VmListAnswerView> ListAnswerView, int intPositionID) {
//        this.listAnswer = listAnswer;
        this.HMPertanyaan1 = HMPertanyaan1;
//        this.ListAnswerViewOptional = new ArrayList<>();
        if (intPositionID == ClsHardCode.HEADER) {
            this.ListAnswerViewOptional = ListAnswerView;
        } else if (intPositionID == ClsHardCode.BODY) {
            this.ListAnswerViewMandatory = ListAnswerView;
        }else if (intPositionID == ClsHardCode.FOOTER) {
            this.ListAnswerViewFooter = ListAnswerView;
        }
    }

}
