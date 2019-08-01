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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.kalbenutritionals.simantra.CustomView.Utils.ClsTools;
import com.kalbenutritionals.simantra.CustomView.Utils.ImageCompression;
import com.kalbenutritionals.simantra.CustomView.Utils.OnReceivedData;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Common.ClsImages;
import com.kalbenutritionals.simantra.Database.Common.ClsTDataRejection;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.EnumStatusDisposisi;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsImages;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsTDataRejection;
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
import com.kalbenutritionals.simantra.ViewModel.VmTJawabanUserHeader;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
    @BindView(R.id.rvHeader)
    RecyclerView rvHeader;
    @BindView(R.id.rvBody)
    RecyclerView rvBody;
//    @BindView(R.id.rvFooter)
//    RecyclerView rvFooter;
    @BindView(R.id.btnValidate)
    Button btnValidate;
    boolean statusValid = false;
    boolean statusRejected = false;
    String txtMsg = "";
    AdapterExpandableList mAdapterHeader;
    AdapterExpandableList mAdapterBody;
//    AdapterExpandableList mAdapterFooter;
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
    List<VmListItemAdapterPertanyaan> ltDataPertanyaanHeader = new ArrayList<>();
    List<VmListItemAdapterPertanyaan> ltDataPertanyaanBody = new ArrayList<>();
//    List<VmListItemAdapterPertanyaan> ltDataPertanyaanFooter = new ArrayList<>();
    List<VmListAnswerView> ListAnswerViewHeader = new ArrayList<>();
    List<VmListAnswerView> ListAnswerViewBody = new ArrayList<>();
    List<VmListAnswerView> ListAnswerViewFooter = new ArrayList<>();
    //    List<VmBasicListView> ltDataPIC = new ArrayList<>();
    public List<VmAdapterBasic> ListRejection = new ArrayList<>();
    @BindView(R.id.btnWarning)
    Button btnWarning;
    @BindView(R.id.lnWarning)
    LinearLayout lnWarning;
    private LinearLayout linearLayout;
    Context context;
    VmTJawabanUserHeader jawabanListFinal = new VmTJawabanUserHeader();

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
        rvHeader.setNestedScrollingEnabled(false);
        rvBody.setNestedScrollingEnabled(false);
//        rvFooter.setNestedScrollingEnabled(false);
        try {
            List<ClsTDataRejection> ltReject = new RepoClsTDataRejection(context).findAll();
            if (ltReject.size()==0){
                lnWarning.setVisibility(View.GONE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFullscreen();
            }
        });
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
//        btTogglePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleSectionPic(btTogglePic);
//            }
//        });
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        generateDat();
    }

    private void showDialogFullscreen() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogFullscreenFragment newFragment = new DialogFullscreenFragment();
//        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogFullscreenFragment.CallbackResult() {
            @Override
            public void sendResult(int requestCode, Object obj) {
                /*if (requestCode == DIALOG_QUEST_CODE) {
                    displayDataResult((Event) obj);
                }*/
            }
        });
    }

    private void toggleSectionOptional(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            lytExpandOptional.setVisibility(View.VISIBLE);
        } else {
            lytExpandOptional.setVisibility(View.GONE);
        }
    }

    private void toggleSectionMandatory(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            lytExpandMandatory.setVisibility(View.VISIBLE);
        } else {
            lytExpandMandatory.setVisibility(View.GONE);
        }
    }

    /*private void toggleSectionPic(View view) {
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
    }*/

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
        int intItemCount = mAdapterHeader.getItemCount();

    }

    public void generateDat() {
        rvHeader.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHeader.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvHeader.setHasFixedSize(true);
        rvBody.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBody.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvBody.setHasFixedSize(true);
        /*rvFooter.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFooter.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvFooter.setHasFixedSize(true);*/

//        List<Social> items = getData(this);

        ltDataPertanyaanHeader = getData(getActivity(), ClsHardCode.HEADER);
        ltDataPertanyaanBody = getData(getActivity(), ClsHardCode.BODY);
//        ltDataPertanyaanFooter = getData(getActivity(), ClsHardCode.FOOTER);

        toggleSectionOptional(btToggleOptional);
        toggleSectionMandatory(btToggleMandatory);
//        toggleSectionPic(btTogglePic);
        //set data and list adapter
//        CustomAdapter mAdapterHeader = new CustomAdapter(getActivity(), mItems);
        mAdapterHeader = new AdapterExpandableList(getActivity(), ltDataPertanyaanHeader);
        mAdapterBody = new AdapterExpandableList(getActivity(), ltDataPertanyaanBody);
//        mAdapterFooter = new AdapterExpandableList(getActivity(), ltDataPertanyaanFooter);

        rvHeader.setAdapter(mAdapterHeader);
        rvBody.setAdapter(mAdapterBody);
//        rvFooter.setAdapter(mAdapterFooter);

        mAdapterHeader.sendData(FragmentDetailInfoChecker.this);
        mAdapterBody.sendData(FragmentDetailInfoChecker.this);
//        mAdapterFooter.sendData(FragmentDetailInfoChecker.this);
    }

    public List<VmListItemAdapterPertanyaan> getData(Context ctx, int jenis) {
        items = new ArrayList<>();
        try {
            List<ClsmPertanyaan> pertanyaans = new ArrayList<>();
            if (jenis == ClsHardCode.HEADER) {
                pertanyaans = new RepomPertanyaan(context).findQuestion(ClsHardCode.HEADER);
            } else if (jenis == ClsHardCode.BODY) {
                pertanyaans = new RepomPertanyaan(context).findQuestion(ClsHardCode.BODY);
            } /*else if (jenis == ClsHardCode.FOOTER) {
                pertanyaans = new RepomPertanyaan(context).findQuestion(ClsHardCode.FOOTER);
            }*/
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
                obj.txtMetodePemeriksaan = pertanyaan.getTxtMetodePemeriksaan();
                obj.bolHaveAnswer = pertanyaan.isBolHaveAnswer();
                obj.jenisPertanyaan = pertanyaan.getIntJenisPertanyaanId();
                obj.intValidateId = pertanyaan.getIntValidateID();
                obj.intPositionId = pertanyaan.getIntLocationDocsId();
                obj.intFillDtlId = pertanyaan.getIntFillDetailId();
                obj.txtMetodePemeriksaan = pertanyaan.getTxtMetodePemeriksaan();
                obj.txtReason = pertanyaan.getTxtReason();
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
                    List<ClsImages> dataImages = new RepoClsImages(context).findByFillDtlId(pertanyaan.getIntFillDetailId());
                    VmImageContainer imageContainer = new VmImageContainer();
                    imageContainer.setPosition(i);
                    imageContainer.setPath(null);
                    imageContainer.setBitmap(null);
                    if (dataImages != null && dataImages.size() > 0) {
                        try {
                            imageContainer.setTxtLink(dataImages.get(i).txtLinkImages);
                        } catch (Exception ex) {
                            imageContainer.setTxtLink(null);
                        }
                    } else {
                        imageContainer.setTxtLink(null);
                    }
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

    public VmTJawabanUserHeader validateAll(int intFlagPush) {
        jawabanListFinal = new VmTJawabanUserHeader();
        boolean validOptional = validateHeader();
        boolean validMandatory = validateBody();
//        boolean validFooter = validateFooter();
//        if (validMandatory && validFooter) {
        if (validMandatory) {
            statusValid = true;
            jawabanListFinal = saveData();
            mAdapterBody.notifyDataSetChanged();
            rvBody.setAdapter(mAdapterBody);

            mAdapterHeader.notifyDataSetChanged();
            rvHeader.setAdapter(mAdapterHeader);

//            mAdapterFooter.notifyDataSetChanged();
//            rvFooter.setAdapter(mAdapterFooter);
//            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
        } else {
            if (intFlagPush == ClsHardCode.PUSHDATA) {
                if (!validMandatory || !validOptional/* || !validFooter*/) {
                    txtMsg = "All question must be filled out";
                    Toast.makeText(context, txtMsg, Toast.LENGTH_SHORT).show();
                }/*else if (!validateFooter()){
                txtMsg = "Mandatory Question must be filled out !";
                Toast.makeText(context,txtMsg,Toast.LENGTH_SHORT).show();
            }*/
                statusValid = false;
                mAdapterBody.notifyDataSetChanged();
                rvBody.setAdapter(mAdapterBody);

                mAdapterHeader.notifyDataSetChanged();
                rvHeader.setAdapter(mAdapterHeader);

//                mAdapterFooter.notifyDataSetChanged();
//                rvFooter.setAdapter(mAdapterFooter);
            }

        }

        return jawabanListFinal;
    }

    private boolean validateHeader() {
        if (lytExpandOptional.getVisibility() == View.GONE){
            toggleSectionOptional(btToggleOptional);
        }
        boolean bolValid = true;
        for (int i = 0; i < ListAnswerViewHeader.size(); i++) {
            List<Jawaban> jawabans = new ArrayList<>();
            int intPertanyaanId = ListAnswerViewHeader.get(i).getIntPertanyaanId();
            int position = ListAnswerViewHeader.get(i).getIntPosition();
            int intValidateId = ListAnswerViewHeader.get(i).getType();
            if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 21);
            }

            int count = 0;
            for (int x = 0; x < ln.getChildCount(); x++) {
                View nextChild = ln.getChildAt(x);
                int intNextChildId = nextChild.getId();
                if (nextChild instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) nextChild;
                    if (checkBox.isChecked()) {
                        ltDataPertanyaanHeader.get(position).jawabans.get(x).bitChoosen = true;
                        count++;
                    } else {
                        ltDataPertanyaanHeader.get(position).jawabans.get(x).bitChoosen = false;
                    }
                }
                if (count == 0 && ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    bolValid = false;
//                    ltDataPertanyaanHeader.get(position).bitValid = false;
//                    ltDataPertanyaanHeader.get(position).message = "Checkbox at least 1 option";
                } else if (count > 0 && ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    bolValid = true;
                    ltDataPertanyaanHeader.get(position).bitValid = true;
                    ltDataPertanyaanHeader.get(position).message = "";
                }
                if (nextChild instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) nextChild;
                    if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                        bolValid = false;
                        ltDataPertanyaanHeader.get(position).bitValid = false;
                        ltDataPertanyaanHeader.get(position).message = "Choose 1 option";
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanHeader.get(position).jawabans) {
                            ltDataPertanyaanHeader.get(position).jawabans.get(index).bitChoosen = false;
                            index++;
                        }
                    } else {
                        bolValid = true;
                        ltDataPertanyaanHeader.get(position).bitValid = true;
                        ltDataPertanyaanHeader.get(position).message = "";
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanHeader.get(position).jawabans) {
                            if (jwb.idJawaban == posisi) {
                                ltDataPertanyaanHeader.get(position).jawabans.get(index).bitChoosen = true;
                            } else {
                                ltDataPertanyaanHeader.get(position).jawabans.get(index).bitChoosen = false;
                                if (posisi == 2){
                                    if (jwb.jawaban.trim().equals("")){
                                        ltDataPertanyaanHeader.get(position).bitValid = true;
                                        ltDataPertanyaanHeader.get(position).message = "";
                                    }
                                }
                            }
                            index++;
                        }
//                        ltDataPertanyaanHeader.get(position).jawabans.get(x).idJawaban = radioGroup.getCheckedRadioButtonId();
                    }
                }
                if (nextChild instanceof EditText) {
                    EditText editText = (EditText) nextChild;
                    if (editText.getText().toString().trim().equals("")) {
                        bolValid = false;
//                        ltDataPertanyaanHeader.get(position).bitValid = false;
//                        ltDataPertanyaanHeader.get(position).message = "Please fill this text";

                    } else {
                        bolValid = true;
                        ltDataPertanyaanHeader.get(position).bitValid = true;
                        ltDataPertanyaanHeader.get(position).message = "";
                    }
                    Jawaban jwbn = new Jawaban();
                    jwbn.idPertanyaan = ltDataPertanyaanHeader.get(position).id;
                    jwbn.idJawaban = 0;
                    jwbn.jawaban = editText.getText().toString();
                    jwbn.bitChoosen = false;
                    jawabans.add(jwbn);
                    ltDataPertanyaanHeader.get(position).jawabans = jawabans;
                }
                if (nextChild instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) nextChild;
                    boolean bolValidImg = true;
                    for (VmImageContainer data : ltDataPertanyaanHeader.get(position).listImage) {
                        if (data.getPath() == null && data.getTxtLink()== null && intValidateId == ClsHardCode.MANDATORY) {
                            bolValid = false;
                            bolValidImg = false;
                        }
                    }

                    if (!bolValidImg && intValidateId == ClsHardCode.MANDATORY) {
                        ltDataPertanyaanHeader.get(position).bitValid = false;
                        ltDataPertanyaanHeader.get(position).message = "Please take all picture...";
                    } else {
                        if(ltDataPertanyaanBody.get(position).bitValid){
//                            ltDataPertanyaanHeader.get(position).bitValid = true;
                            bolValid = true;
                        }
                    }
                }

                /*if (nextChild instanceof ImageView) {
                    ImageView imageView = (ImageView) nextChild;
                    if (ltDataPertanyaanHeader.get(position).path == null) {
                        bolValid = false;
                    }
                    if (!bolValid) {
                        ltDataPertanyaanHeader.get(position).bitValid = false;
                        ltDataPertanyaanHeader.get(position).message = "Please take all picture...";
                    } else {
                        ltDataPertanyaanHeader.get(position).bitValid = true;
                        ltDataPertanyaanHeader.get(position).message = "";
                        bolValid = true;
                    }
                }*/
            }
        }
        return bolValid;
    }

    private boolean validateBody() {
        if (lytExpandMandatory.getVisibility() == View.GONE){
            toggleSectionMandatory(btToggleMandatory);
        }
        boolean bolValid = true;
        ListRejection = new ArrayList<>();
        for (int i = 0; i < ListAnswerViewBody.size(); i++) {
            List<Jawaban> jawabans = new ArrayList<>();
            int intPertanyaanId = ListAnswerViewBody.get(i).getIntPertanyaanId();
            int position = ListAnswerViewBody.get(i).getIntPosition();
            int intValidateId = ListAnswerViewBody.get(i).getType();
            if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 21);
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
                        ltDataPertanyaanBody.get(position).jawabans.get(x).bitChoosen = true;
                        count++;
                    } else {
                        ltDataPertanyaanBody.get(position).jawabans.get(x).bitChoosen = false;
                        jawabanCheckbox = jawabanCheckbox + ltDataPertanyaanBody.get(position).jawabans.get(x).jawaban + ", ";
                    }
                }

                if (nextChild instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) nextChild;
                    if ((radioGroup.getCheckedRadioButtonId() == -1)) {
                        /*int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanBody.get(position).jawabans) {
                            ltDataPertanyaanBody.get(position).jawabans.get(index).bitChoosen = false;
                            index++;
                        }*/
                        bolValid = false;
                        ltDataPertanyaanBody.get(position).bitValid = false;
                        ltDataPertanyaanBody.get(position).message = "Choose 1 option";
                        int index = 0;
                        for (Jawaban jwb : ltDataPertanyaanBody.get(position).jawabans) {
                            ltDataPertanyaanBody.get(position).jawabans.get(index).bitChoosen = false;
                            index++;
                        }
                    } else {
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        int index = 0;
                        ltDataPertanyaanBody.get(position).bitValid = true;
                        for (Jawaban jwb : ltDataPertanyaanBody.get(position).jawabans) {
                            if (jwb.idJawaban == posisi) {
                                ltDataPertanyaanBody.get(position).jawabans.get(index).bitChoosen = true;
                            } else {
                                ltDataPertanyaanBody.get(position).jawabans.get(index).bitChoosen = false;
                            }
                            index++;
                        }
                    }
                }
                if (nextChild instanceof EditText) {
                    EditText editText = (EditText) nextChild;
//                    if (editText.getText().toString().trim().equals("")) {
//                        bolValid = false;
////                        ltDataPertanyaanBody.get(position).bitValid = false;
//                        ltDataPertanyaanBody.get(position).message = "Please fill this text";
//
//                    } else {
//                        bolValid = true;
//                        ltDataPertanyaanBody.get(position).bitValid = true;
//                        ltDataPertanyaanBody.get(position).message = "";
//                    }
                    Jawaban jwbn = new Jawaban();
                    jwbn.idPertanyaan = ltDataPertanyaanBody.get(position).id;
                    jwbn.idJawaban = 0;
                    jwbn.jawaban = editText.getText().toString();
                    jwbn.bitChoosen = false;
                    jawabans.add(jwbn);
                    ltDataPertanyaanBody.get(position).jawabans = jawabans;
                }
                if (nextChild instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) nextChild;
                    boolean bolValidImg = true;
                    for (VmImageContainer data : ltDataPertanyaanBody.get(position).listImage) {
                        if (data.getPath() == null && data.getTxtLink() == null && intValidateId == ClsHardCode.MANDATORY) {
                            bolValidImg = false;
                        }
                    }

                    if (!bolValidImg && intValidateId == ClsHardCode.MANDATORY) {
                        bolValid = false;
                        ltDataPertanyaanBody.get(position).bitValid = false;
                        ltDataPertanyaanBody.get(position).message = " Please take all picture...";
                    } else {
                        if(ltDataPertanyaanBody.get(position).bitValid && bolValid){
                            ltDataPertanyaanBody.get(position).bitValid = true;
                            bolValid = true;
                        }

                    }
                }


            }

        }
        return bolValid;
    }

    /*private boolean validateFooter() {
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
    }*/

    String jawabanCheckbox = "";

    public JSONObject getDataTransaction(int FlagPush, String txtReason) {
        //jika nambah jawaban dari basic
        /*List<VmTJawabanUser> jawabanUser= jawabanListFinal.getListJawabanUser();
        try {
            List<ClsmPertanyaan> pertanyaans = new RepomPertanyaan(context).findQuestionGeneralInfoAll();
            for (ClsmPertanyaan per : pertanyaans) {
                List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(per.getIntPertanyaanId());
                List<VmTJawabanUserDetail> ltDetail = new ArrayList<>();
                for (ClsmJawaban j :
                        jawabans) {
                    String jawaban = j.getTxtJawaban();
                    VmTJawabanUserDetail detail = new VmTJawabanUserDetail();
                    detail.setIntmJawabanId(j.getIdJawaban());
                    detail.setQualified(true);
                    ltDetail.add(detail);
                }
                VmTJawabanUser tJawabanUser = new VmTJawabanUser();
                tJawabanUser.setBolHaveAnswer(true);
                tJawabanUser.setBolHavePhoto(false);
                tJawabanUser.setIntPertanyaanId(per.getIntPertanyaanId());
                tJawabanUser.setIntTypePertanyaanId(per.getIntJenisPertanyaanId());
                tJawabanUser.setJawabanUserDetailList(ltDetail);
                jawabanUser.add(tJawabanUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        jawabanListFinal.setListJawabanUser(jawabanUser);*/
        if (FlagPush == ClsHardCode.SAVE) {
            jawabanListFinal = saveData();
            jawabanListFinal.setIntStatusDisposisi(0);
        }else{
            jawabanListFinal.setTxtLoadingMessage(txtReason);
        }
//        String HeaderID = BLHelper.getPreference(context,ClsHardCode.SP_INT_HEADER_ID);
//        jawabanListFinal.setIntFillHeaderId(Integer.parseInt(HeaderID));
        JSONObject json = new BLHelper().getDataTransaksiJsonObjCommon(context, jawabanListFinal);
        return json;
    }

    public VmTJawabanUserHeader saveData() {
        VmTJawabanUserHeader tJawabanUserHeader = new VmTJawabanUserHeader();
        List<VmTJawabanUser> tJawabanList = new ArrayList<>();
        boolean bolVariance = true;
        int count = 0;
        for (int i = 0; i < ListAnswerViewHeader.size(); i++) {
            try {
                int intPertanyaanId = ListAnswerViewHeader.get(i).getIntPertanyaanId();
                int position = ListAnswerViewHeader.get(i).getIntPosition();
                ClsmPertanyaan pertanyaans = (ClsmPertanyaan) new RepomPertanyaan(context).findById(intPertanyaanId);
                VmTJawabanUser tJawaban = new VmTJawabanUser();
                tJawaban.setTxtTransJawabanId(new BLActivity().GenerateGuid());
                tJawaban.setIntPertanyaanId(intPertanyaanId);
                tJawaban.setIntTypePertanyaanId(pertanyaans.getIntJenisPertanyaanId());
                tJawaban.setBolHavePhoto(pertanyaans.isBolHavePhoto());
                tJawaban.setBolHaveAnswer(pertanyaans.isBolHaveAnswer());
                tJawabanUserHeader.setIntFillHeaderId(pertanyaans.getIntFillHeaderId());

                if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                    int a = ListAnswerViewHeader.get(i).getIntPertanyaanId();
                    int b = rvHeader.getChildCount();
                    ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 24);
                } else if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                    ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 22);
                } else if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 21);
                }
                String jawabanFinal = "";
                Boolean boolJawabanFinal = true;
                List<VmTJawabanUserDetail> listJawaban = new ArrayList<>();
                for (int x = 0; x < ln.getChildCount(); x++) {
                    boolean isImage = true;
                    List<VmTJawabanUserDetail.imageModel> listImage = new ArrayList<>();
                    VmTJawabanUserDetail dtJawaban = new VmTJawabanUserDetail();
                    View nextChild = ln.getChildAt(x);
                    if (nextChild instanceof CheckBox) {
                        isImage = false;
                        CheckBox checkBox = (CheckBox) nextChild;
//                        if (checkBox.isChecked()) {
//
//                        }
                        dtJawaban.setIntmJawabanId(checkBox.getId());
                        dtJawaban.setTxtJawaban(checkBox.getText().toString());
                        dtJawaban.setQualified(checkBox.isChecked());
                        if (!checkBox.isChecked()) {
                            count++;
                            bolVariance = false;
                            if (jawabanFinal.equals("")) {
                                jawabanFinal = String.valueOf(count) + ". " + checkBox.getText().toString();
                            } else {
                                jawabanFinal += "\n" + String.valueOf(count) + ". " + checkBox.getText().toString();
                            }
                        }
                    }

                    if (nextChild instanceof RadioGroup) {
                        isImage = false;
                        RadioGroup radioGroup = (RadioGroup) nextChild;
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        RadioButton rb = (RadioButton) rvHeader.getChildAt(i).findViewById(posisi);
                        if (posisi > -1) {
                            String txtJawaban = rb.getText().toString();
                            dtJawaban.setIntmJawabanId(posisi);
                            dtJawaban.setTxtJawaban(txtJawaban);
                            dtJawaban.setQualified(rb.isChecked());
                            if (txtJawaban.equals("Yes")) {
                                dtJawaban.setQualified(true);
                            } else {
                                dtJawaban.setQualified(false);
                                jawabanFinal = txtJawaban;
                                boolJawabanFinal = false;
                                bolVariance = false;
                            }
                        }/*else {
                            dtJawaban.setIntmJawabanId(0);
                            dtJawaban.setTxtJawaban(null);
                            dtJawaban.setQualified(false);
                        }*/

                    }
                    if (nextChild instanceof EditText) {
                        isImage = false;
                        EditText editText = (EditText) nextChild;
                        dtJawaban.setIntmJawabanId(0);
                        dtJawaban.setTxtJawaban(editText.getText().toString());
                        if (editText.getText().toString().length() > 0) {
                            dtJawaban.setQualified(true);
                        } else {
                            dtJawaban.setQualified(false);
                        }
                    }
                    if (listJawaban.size() == 0) {
                        if (pertanyaans.isBolHavePhoto()) {
                            for (int z = 0; z < ltDataPertanyaanHeader.get(position).listImage.size(); z++) {
                                if (ltDataPertanyaanHeader.get(position).listImage.get(z).getPath() != null) {

                                    String dirA = ltDataPertanyaanHeader.get(position).listImage.get(z).getPath().getPath();
                                    dirA = dirA.replaceAll(".+Android", "");
                                    String dir = Environment.getExternalStorageDirectory() + "/Android/" + dirA;
                                    File file = new File(dir);
                                    String imageName = ltDataPertanyaanHeader.get(position).listImage.get(z).getImgName();
                                    List<ClsImages> images = new RepoClsImages(context).findByFillDtlId(pertanyaans.getIntFillDetailId());
                                    int imgId = 0;
                                    if (images.size() > 0) {
                                        try {
                                            imgId = images.get(z).getIdImage();
                                        } catch (Exception ex) {

                                        }
                                    }
                                    VmTJawabanUserDetail.imageModel dtImage = new VmTJawabanUserDetail().new imageModel(imgId, imageName, dir);

                                    listImage.add(dtImage);
                                }
                            }
                        }

                        dtJawaban.setDtImageModels(listImage);
                    }
                    if (!isImage) {
                        listJawaban.add(dtJawaban);
                    }
                }
                tJawaban.setJawabanUserDetailList(listJawaban);
                tJawabanList.add(tJawaban);

                if (!boolJawabanFinal && ltDataPertanyaanHeader.get(position).intValidateId == 3) {
                    VmAdapterBasic data = new VmAdapterBasic();
                    data.setTitle(ltDataPertanyaanHeader.get(position).txtPertanyaan);
                    data.setSubtitle(jawabanFinal);
                    ListRejection.add(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < ListAnswerViewBody.size(); i++) {
            try {
                int intPertanyaanId = ListAnswerViewBody.get(i).getIntPertanyaanId();
                int position = ListAnswerViewBody.get(i).getIntPosition();
                ClsmPertanyaan pertanyaans = (ClsmPertanyaan) new RepomPertanyaan(context).findById(intPertanyaanId);
                VmTJawabanUser tJawaban = new VmTJawabanUser();
                tJawaban.setTxtTransJawabanId(new BLActivity().GenerateGuid());
                tJawaban.setIntPertanyaanId(intPertanyaanId);
                tJawaban.setIntTypePertanyaanId(pertanyaans.getIntJenisPertanyaanId());
                tJawaban.setBolHavePhoto(pertanyaans.isBolHavePhoto());
                tJawaban.setBolHaveAnswer(pertanyaans.isBolHaveAnswer());
//                if (pertanyaans.isBolHavePhoto()) {
//                    for (int x = 0; x < ltDataPertanyaanBody.get(position).listImage.size(); x++) {
//                        if (ltDataPertanyaanBody.get(position).listImage.get(x).getPath()!= null){
//
//                            String dirA =ltDataPertanyaanBody.get(position).listImage.get(x).getPath().getPath();
//                            dirA = dirA.replaceAll(".+Android", "");
//                            String dir = Environment.getExternalStorageDirectory()+"/Android/"+dirA;
//                            File file = new File(dir);
//                            String imageName = ltDataPertanyaanBody.get(position).listImage.get(x).getImgName();
//                            VmTJawabanUser.imageModel dtImage = new VmTJawabanUser().new imageModel(imageName,dir);
//                            listImage.add(dtImage);
//                        }
//                    }
//                }
//                tJawaban.setDtImageModels(listImage);

                if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                    ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 24);
                } else if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                    ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 22);
                } else if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 21);
                }
                String jawabanFinal = "";
                Boolean boolJawabanFinal = true;
                List<VmTJawabanUserDetail> listJawaban = new ArrayList<>();
                for (int x = 0; x < ln.getChildCount(); x++) {
                    List<VmTJawabanUserDetail.imageModel> listImage = new ArrayList<>();
                    VmTJawabanUserDetail dtJawaban = new VmTJawabanUserDetail();
                    boolean isImage = true;
                    View nextChild = ln.getChildAt(x);
                    if (nextChild instanceof CheckBox) {
                        isImage = false;
                        CheckBox checkBox = (CheckBox) nextChild;
                        dtJawaban.setIntmJawabanId(checkBox.getId());
                        dtJawaban.setTxtJawaban(checkBox.getText().toString());
                        dtJawaban.setQualified(checkBox.isChecked());
                        if (!checkBox.isChecked()) {
                            count++;
                            bolVariance = false;
                            if (jawabanFinal.equals("")) {
                                jawabanFinal = String.valueOf(count) + ". " + checkBox.getText().toString();
                            } else {
                                jawabanFinal += "\n" + String.valueOf(count) + ". " + checkBox.getText().toString();
                            }
                        }
                    }

                    if (nextChild instanceof RadioGroup) {
                        isImage = false;
                        RadioGroup radioGroup = (RadioGroup) nextChild;
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        RadioButton rb = (RadioButton) rvBody.getChildAt(i).findViewById(posisi);
                        if (posisi > -1) {
                            String txtJawaban = rb.getText().toString();
                            dtJawaban.setIntmJawabanId(posisi);
                            dtJawaban.setTxtJawaban(txtJawaban);

                            if (txtJawaban.equals("Yes")) {
                                dtJawaban.setQualified(true);
                            } else {
                                dtJawaban.setQualified(false);
                                jawabanFinal = txtJawaban;
                                boolJawabanFinal = false;
                                bolVariance = false;
                            }
                        }/*else {
                            dtJawaban.setIntmJawabanId(0);
                            dtJawaban.setTxtJawaban(null);
                            dtJawaban.setQualified(false);
                            jawabanFinal = rb.getText().toString();
                            boolJawabanFinal = false;
                        }*/

                    }
                    if (nextChild instanceof EditText) {
                        isImage = false;
                        EditText editText = (EditText) nextChild;
                        dtJawaban.setIntmJawabanId(0);
                        dtJawaban.setTxtJawaban(editText.getText().toString());
                        if (editText.getText().toString().length() > 0) {
                            dtJawaban.setQualified(true);
                        } else {
                            dtJawaban.setQualified(false);
                            bolVariance = false;
                        }
                    }

                    if (listJawaban.size() == 0) {
                        if (pertanyaans.isBolHavePhoto()) {
                            for (int z = 0; z < ltDataPertanyaanBody.get(position).listImage.size(); z++) {
                                if (ltDataPertanyaanBody.get(position).listImage.get(z).getPath() != null) {

                                    String dirA = ltDataPertanyaanBody.get(position).listImage.get(z).getPath().getPath();
                                    dirA = dirA.replaceAll(".+Android", "");
                                    String dir = Environment.getExternalStorageDirectory() + "/Android/" + dirA;
                                    File file = new File(dir);
                                    String imageName = ltDataPertanyaanBody.get(position).listImage.get(z).getImgName();
                                    List<ClsImages> images = new RepoClsImages(context).findByFillDtlId(pertanyaans.getIntFillDetailId());
                                    int imgId = 0;
                                    if (images.size() > 0) {
                                        try {
                                            imgId = images.get(z).getIdImage();
                                        } catch (Exception ex) {

                                        }
                                    }
                                    VmTJawabanUserDetail.imageModel dtImage = new VmTJawabanUserDetail().new imageModel(imgId, imageName, dir);

                                    listImage.add(dtImage);
                                }
                            }
                        }

                        dtJawaban.setDtImageModels(listImage);
                    }
                    if (!isImage) {
                        listJawaban.add(dtJawaban);
                    }
                }
                tJawaban.setJawabanUserDetailList(listJawaban);
                tJawabanList.add(tJawaban);
                /*if (!jawabanFinal.equals("")){
                    VmAdapterBasic data = new VmAdapterBasic();
                    data.setTitle(ltDataPertanyaanBody.get(position).txtPertanyaan);
                    data.setSubtitle(jawabanFinal);
                    ListRejection.add(data);
                }*/
                if (!boolJawabanFinal && ltDataPertanyaanBody.get(position).intValidateId == 3) {
                    VmAdapterBasic data = new VmAdapterBasic();
                    data.setTitle(ltDataPertanyaanBody.get(position).txtPertanyaan);
                    data.setSubtitle(jawabanFinal);
                    ListRejection.add(data);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        /*for (int i = 0; i < ListAnswerViewFooter.size(); i++) {
            try {
                int intPertanyaanId = ListAnswerViewFooter.get(i).getIntPertanyaanId();
                int position = ListAnswerViewFooter.get(i).getIntPosition();
                ClsmPertanyaan pertanyaans = (ClsmPertanyaan) new RepomPertanyaan(context).findById(intPertanyaanId);
                VmTJawabanUser tJawaban = new VmTJawabanUser();
                tJawaban.setTxtTransJawabanId(new BLActivity().GenerateGuid());
                tJawaban.setIntPertanyaanId(intPertanyaanId);
                tJawaban.setIntTypePertanyaanId(pertanyaans.getIntJenisPertanyaanId());
                tJawaban.setBolHavePhoto(pertanyaans.isBolHavePhoto());
                tJawaban.setBolHaveAnswer(pertanyaans.isBolHaveAnswer());

                if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                    ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 24);
                } else if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                    ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 22);
                } else if (ltDataPertanyaanFooter.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                    ln = (LinearLayout) rvFooter.getChildAt(i).findViewById(ListAnswerViewFooter.get(i).getIntPertanyaanId() * 21);
                }

                List<VmTJawabanUserDetail> listJawaban = new ArrayList<>();
                String jawabanFinal = "";
                Boolean boolJawabanFinal = true;
                for (int x = 0; x < ln.getChildCount(); x++) {
                    List<VmTJawabanUserDetail.imageModel> listImage = new ArrayList<>();
                    VmTJawabanUserDetail dtJawaban = new VmTJawabanUserDetail();
                    boolean isImage = true;
                    View nextChild = ln.getChildAt(x);
                    if (nextChild instanceof CheckBox) {
                        isImage = false;
                        CheckBox checkBox = (CheckBox) nextChild;
//                        if (checkBox.isChecked()) {
//
//                        }
                        dtJawaban.setIntmJawabanId(checkBox.getId());
                        dtJawaban.setTxtJawaban(checkBox.getText().toString());
                        dtJawaban.setQualified(checkBox.isChecked());
                        if (!checkBox.isChecked()) {
                            count++;
                            bolVariance = false;
                            if (jawabanFinal.equals("")) {
                                jawabanFinal = String.valueOf(count) + ". " + checkBox.getText().toString();
                            } else {
                                jawabanFinal += "\n" + String.valueOf(count) + ". " + checkBox.getText().toString();
                            }
                        }
                    }

                    if (nextChild instanceof RadioGroup) {
                        isImage = false;
                        RadioGroup radioGroup = (RadioGroup) nextChild;
                        int posisi = radioGroup.getCheckedRadioButtonId();
                        RadioButton rb = (RadioButton) rvFooter.getChildAt(i).findViewById(posisi);
                        if (rb != null && posisi > -1) {
                           *//* String txtJawaban = rb.getText().toString();
                            dtJawaban.setIntmJawabanId(posisi);
                            dtJawaban.setTxtJawaban(txtJawaban);
                            dtJawaban.setQualified(true);*//*
                            String txtJawaban = rb.getText().toString();
                            dtJawaban.setIntmJawabanId(posisi);
                            dtJawaban.setTxtJawaban(txtJawaban);
                            dtJawaban.setQualified(rb.isChecked());
                            if (txtJawaban.equals("Yes")) {
                                dtJawaban.setQualified(true);
                            } else {
                                dtJawaban.setQualified(false);
                                jawabanFinal = txtJawaban;
                                boolJawabanFinal = false;
                                bolVariance = false;
                            }
                        }*//*else {
                            dtJawaban.setIntmJawabanId(0);
                            dtJawaban.setTxtJawaban(null);
                            dtJawaban.setQualified(false);
                        }*//*
                    }
                    if (nextChild instanceof EditText) {
                        isImage = false;
                        EditText editText = (EditText) nextChild;
                        dtJawaban.setIntmJawabanId(0);
                        dtJawaban.setTxtJawaban(editText.getText().toString());
                        if (editText.getText().toString().length() > 0) {
                            dtJawaban.setQualified(true);
                        } else {
                            dtJawaban.setQualified(false);
                            bolVariance = false;
                        }
                    }
                    if (listJawaban.size() == 0) {
                        if (pertanyaans.isBolHavePhoto()) {
                            for (int z = 0; z < ltDataPertanyaanFooter.get(position).listImage.size(); z++) {
                                if (ltDataPertanyaanFooter.get(position).listImage.get(z).getPath() != null) {

                                    String dirA = ltDataPertanyaanFooter.get(position).listImage.get(z).getPath().getPath();
                                    dirA = dirA.replaceAll(".+Android", "");
                                    String dir = Environment.getExternalStorageDirectory() + "/Android/" + dirA;
                                    File file = new File(dir);
                                    String imageName = ltDataPertanyaanFooter.get(position).listImage.get(z).getImgName();
                                    List<ClsImages> images = new RepoClsImages(context).findByFillDtlId(pertanyaans.getIntValueId());
                                    int imgId = 0;
                                    if (images.size() > 0) {
                                        try {
                                            imgId = images.get(z).getIdImage();
                                        } catch (Exception ex) {

                                        }
                                    }
                                    VmTJawabanUserDetail.imageModel dtImage = new VmTJawabanUserDetail().new imageModel(imgId, imageName, dir);

                                    listImage.add(dtImage);
                                }
                            }
                        }

                        dtJawaban.setDtImageModels(listImage);
                    }

                    if (!isImage) {
                        listJawaban.add(dtJawaban);
                    }
                }
                tJawaban.setJawabanUserDetailList(listJawaban);
                tJawabanList.add(tJawaban);
                if (!boolJawabanFinal && ltDataPertanyaanFooter.get(position).intValidateId == 3) {
                    VmAdapterBasic data = new VmAdapterBasic();
                    data.setTitle(ltDataPertanyaanFooter.get(position).txtPertanyaan);
                    data.setSubtitle(jawabanFinal);
                    ListRejection.add(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }*/

        if (ListRejection.size() == 0) {
            if (!bolVariance) {
                tJawabanUserHeader.setIntStatusDisposisi(EnumStatusDisposisi.AcceptWithVariance.getIdStatus());
            } else {
                tJawabanUserHeader.setIntStatusDisposisi(EnumStatusDisposisi.Accept.getIdStatus());
            }
        } else if (ListRejection.size() > 0) {
            tJawabanUserHeader.setIntStatusDisposisi(EnumStatusDisposisi.Reject.getIdStatus());
        }
        tJawabanUserHeader.setListJawabanUser(tJawabanList);
        return tJawabanUserHeader;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_QUESTION) {
            if (resultCode == -1) {
                if (GLOBAL_PICK_PICTURE_POSITION_ID == ClsHardCode.HEADER) {
                    setOnActivityResultOptional();
                } else if (GLOBAL_PICK_PICTURE_POSITION_ID == ClsHardCode.BODY) {
                    setOnActivityResultMandatory();
                } /*else if (GLOBAL_PICK_PICTURE_POSITION_ID == ClsHardCode.FOOTER) {
                    setOnActivityResultFooter();
                }*/

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void setOnActivityResultOptional() {
        for (int i = 0; i < rvHeader.getChildCount(); i++) {
            int position = ListAnswerViewHeader.get(i).getIntPosition();
            if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanHeader.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvHeader.getChildAt(i).findViewById(ListAnswerViewHeader.get(i).getIntPertanyaanId() * 21);
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
                            String dir = new ClsTools().getRealPath(uriImage);
                            new ImageCompression(context).compressImage(dir);
                            new PickImage().previewCapturedImage(imageView, thePic, 400, 500);
                            ltDataPertanyaanHeader.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setPath(uriImage);
                            ltDataPertanyaanHeader.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setImgName(imgName);
                            ltDataPertanyaanHeader.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setBitmap(thePic);
//                                            ltDataPertanyaanHeader.get(GLOBAL_PICK_PICTURE_QUEST_ID).path = uriImage;
//                                            ltDataPertanyaanHeader.get(GLOBAL_PICK_PICTURE_QUEST_ID).bitmap = thePic;
                        } catch (Exception ex) {
                            String a = ex.getMessage();
                        }
                    }
                }
            }
        }
    }

    public void setOnActivityResultMandatory() {
        for (int i = 0; i < rvBody.getChildCount(); i++) {
            int position = ListAnswerViewBody.get(i).getIntPosition();
            if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanTextBox) {
                ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 24);
            } else if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanRadioButton) {
                ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 22);
            } else if (ltDataPertanyaanBody.get(position).jenisPertanyaan == ClsHardCode.JenisPertanyaanCheckBox) {
                ln = (LinearLayout) rvBody.getChildAt(i).findViewById(ListAnswerViewBody.get(i).getIntPertanyaanId() * 21);
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

                            String dir = new ClsTools().getRealPath(uriImage);
                            new ImageCompression(context).compressImage(dir);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            thePic.compress(Bitmap.CompressFormat.JPEG, 100, stream);


                            byte[] imageInByte = stream.toByteArray();
                            long lengthbmp = imageInByte.length;
                            long fileSizeInKB = lengthbmp / 1024;
                            // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                            long fileSizeInMB = fileSizeInKB / 1024;
                            new PickImage().previewCapturedImage(imageView, thePic, 400, 500);
                            ltDataPertanyaanBody.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setPath(uriImage);
                            ltDataPertanyaanBody.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setBitmap(thePic);
                            ltDataPertanyaanBody.get(GLOBAL_PICK_PICTURE_QUEST_ID).listImage.get(GLOBAL_PICK_PICTURE_ID).setImgName(imgName);
//                                            ltDataPertanyaanBody.get(GLOBAL_PICK_PICTURE_QUEST_ID).path = uriImage;
//                                            ltDataPertanyaanBody.get(GLOBAL_PICK_PICTURE_QUEST_ID).bitmap = thePic;
                        } catch (Exception ex) {
                            String a = ex.getMessage();
                        }
                    }
                }
            }
        }
    }

    /*public void setOnActivityResultFooter() {
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
    }*/

    @Override
    public void onDataTransportReceived
            (List<View> listAnswer, HashMap<Integer, View> HMPertanyaan1, List<VmListAnswerView> ListAnswerView, int intPositionID) {
//        this.listAnswer = listAnswer;
        this.HMPertanyaan1 = HMPertanyaan1;
//        this.ListAnswerViewHeader = new ArrayList<>();
        if (intPositionID == ClsHardCode.HEADER) {
            this.ListAnswerViewHeader = ListAnswerView;
        } else if (intPositionID == ClsHardCode.BODY) {
            this.ListAnswerViewBody = ListAnswerView;
        } /*else if (intPositionID == ClsHardCode.FOOTER) {
            this.ListAnswerViewFooter = ListAnswerView;
        }*/
    }

}
