package shp.template.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import shp.template.BL.clsActivity;
import shp.template.BL.clsMainBL;
import shp.template.Common.mActivity;
import shp.template.Common.mSubActivity;
import shp.template.Common.mSubSubActivity;
import shp.template.Common.mUserLogin;
import shp.template.Common.tAkuisisiDetail;
import shp.template.Common.tAkuisisiHeader;
import shp.template.Common.tProgramVisitSubActivity;
import shp.template.Common.tRealisasiVisitPlan;
import shp.template.Data.clsHardCode;
import shp.template.ImageViewerActivity;
import shp.template.Model.clsListImageAdapter;
import shp.template.R;
import shp.template.Repo.mActivityRepo;
import shp.template.Repo.mSubActivityRepo;
import shp.template.Repo.mSubSubActivityRepo;
import shp.template.Repo.tAkuisisiDetailRepo;
import shp.template.Repo.tAkuisisiHeaderRepo;
import shp.template.Repo.tProgramVisitSubActivityRepo;
import shp.template.Repo.tRealisasiVisitPlanRepo;
import shp.template.Utils.IOBackPressed;
import shp.template.Utils.SpacingItemDecoration;
import shp.template.Utils.Tools;
import shp.template.adapter.RecyclerGridImageAdapter;
import com.kalbe.mobiledevknlibs.AlertDialog.CustomDatePicker;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by Dewi Oktaviani on 10/9/2018.
 */

public class FragmentAddAkuisisi extends Fragment implements IOBackPressed{

    private RecyclerView lv_akuisisi;
    private RecyclerGridImageAdapter adapter;
    private AppCompatSpinner spnAddSubAkuisisi;
    TextView etDtExpired;
    List<clsListImageAdapter> listImage = new ArrayList<>();
    private static final int CAMERA_CAPTURE_IMAGE1_REQUEST_CODE = 100;
    private String fileName, selectedSubAkuisisi;
    private String ZOOM_IMAGE = "zoom image";
    private String ZOOM_DIRECTORY = "zoom directory";
    private String SUB_SUB_ACTIVITY = "sub sub activity";
    View v;
    public List<String> NamaTab = new ArrayList<>();
    public HashMap<String, Integer> MapTab = new HashMap<>();
    String txtSubSubActivity;
    TextInputEditText etNoDoc;
    tAkuisisiDetailRepo dtDetailRepo;
    tAkuisisiHeaderRepo dtHeaderRepo;
    mUserLogin dtUserLogin;
    tAkuisisiHeader dtHeader = new tAkuisisiHeader();
    tAkuisisiDetail dtDetail = new tAkuisisiDetail();
    List<tAkuisisiDetail> listDetail = new ArrayList<>();
    mSubActivity _mSubActivity;
    List<mSubSubActivity> _mSubSubActivity;
    mActivity _mActivity;
    mSubSubActivityRepo subSubActivityRepo;
    mSubActivityRepo subActivityRepo;
    tRealisasiVisitPlanRepo absenRepo;
    mActivityRepo activityRepo;
    tProgramVisitSubActivity dataPlan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_akuisisi, container, false);
        lv_akuisisi = (RecyclerView) v.findViewById(R.id.lv_akuisisi);
        spnAddSubAkuisisi = (AppCompatSpinner) v.findViewById(R.id.spn_add_sub_akuisisi);
//        etDtExpired = (TextInputEditText) v.findViewById(R.id.et_exp_date);
        etDtExpired = (TextView) v.findViewById(R.id.tv_exp_date);

        etNoDoc = (TextInputEditText) v.findViewById(R.id.et_no_doc);

        absenRepo = new tRealisasiVisitPlanRepo(getContext());
        dtDetailRepo = new tAkuisisiDetailRepo(getContext());
        dtHeaderRepo = new tAkuisisiHeaderRepo(getContext());
        dtUserLogin = new clsMainBL().getUserLogin(getContext());
        lv_akuisisi.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        lv_akuisisi.addItemDecoration(new SpacingItemDecoration(2, new Tools().dpToPx(getActivity(), 3), true));
        lv_akuisisi.setHasFixedSize(true);
        final tRealisasiVisitPlan dataCheckinActive = absenRepo.getDataCheckinActive();
        try {
           dataPlan = (tProgramVisitSubActivity) new tProgramVisitSubActivityRepo(getContext()).findBytxtId(dataCheckinActive.getTxtProgramVisitSubActivityId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        subSubActivityRepo = new mSubSubActivityRepo(getContext());
        try {
            if (dataPlan.getIntActivityId()==1){
                List<Integer> listSubSubActivityId = dtHeaderRepo.getIntSubSubActivityId(dataPlan.getIntActivityId(), dataPlan.getTxtDokterId());
                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityIdAndTypeId(listSubSubActivityId,1, 1);
//                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityIdAndTypeId(1, 1);
            }else if (dataPlan.getIntActivityId()==2){
                List<Integer> listSubSubActivityId = dtHeaderRepo.getIntSubSubActivityId(dataPlan.getIntActivityId(), dataPlan.getTxtApotekId());
//                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityIdAndTypeId(4, 1);
                _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityIdAndTypeId(listSubSubActivityId,4, 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        NamaTab.clear();
        MapTab.clear();
        NamaTab.add("Select One");
        MapTab.put("Select One", 0);
        if (_mSubSubActivity!=null&&_mSubSubActivity.size()>0){
            for (int i = 0; i < _mSubSubActivity.size(); i++){
                NamaTab.add(_mSubSubActivity.get(i).getTxtName());
                MapTab.put(_mSubSubActivity.get(i).getTxtName(), _mSubSubActivity.get(i).getIntSubSubActivityid());
            }
        }

        // Initializing an ArrayAdapter with initial text like select one
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item, NamaTab){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spnAddSubAkuisisi.setAdapter(spinnerArrayAdapter);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            txtSubSubActivity = bundle.getString(SUB_SUB_ACTIVITY);
            int position = spinnerArrayAdapter.getPosition(txtSubSubActivity);
            spnAddSubAkuisisi.setSelection(position);
            setAdapterAkusisi();
        }

        // attaching data adapter to spinner
        spnAddSubAkuisisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSubAkuisisi = spnAddSubAkuisisi.getSelectedItem().toString();
                txtSubSubActivity = selectedSubAkuisisi;
                onItemSpinnerSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                new ToastCustom().showToasty(getActivity(),"Please select Sub Akuisisi",4);
                // put code here
            }
        });
        final FabSpeedDial fabSpeedDial = (FabSpeedDial) v.findViewById(R.id.fabView);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch(menuItem.getItemId()){

                    case R.id.action_add:
                        if (MapTab.get(spnAddSubAkuisisi.getSelectedItem())==0){
                            new ToastCustom().showToasty(getContext(), "Please select type of akuisisi", 4);
                        }else {
                            if (dtHeader==null){
                                tAkuisisiHeader dt = new tAkuisisiHeader();
                                dt.setTxtHeaderId(new clsActivity().GenerateGuid());
                                dt.setDtExpiredDate(parseDateTime(etDtExpired.getText().toString()));
                                dt.setTxtNoDoc(etNoDoc.getText().toString());
                                dt.setIntFlagPush(new clsHardCode().Draft);
                                dt.setIntSubSubActivityId(MapTab.get(txtSubSubActivity));
                                dt.setIntUserId(dtUserLogin.getIntUserID());
                                dt.setIntRoleId(dtUserLogin.getIntRoleID());
                                dt.setIntAreaId(dataPlan.getTxtAreaId());
                                if (dataPlan.getIntActivityId()==new clsHardCode().VisitDokter){
                                    dt.setIntDokterId(dataCheckinActive.getTxtDokterId());
                                }else if (dataPlan.getIntActivityId()==new clsHardCode().VisitApotek){
                                    dt.setIntApotekID(dataCheckinActive.getTxtApotekId());
                                }
                                dt.setTxtRealisasiVisitId(dataCheckinActive.getTxtRealisasiVisitId());
                                dt.setIntFlagShow(new clsHardCode().Draft);
                                dt.setTxtUserName("");
                                try {
                                    dtHeaderRepo.createOrUpdate(dt);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                dt.setIntSubSubActivityTypeId(new clsHardCode().TypeFoto);

                            }
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                            fileName = "temp_akuisisi" + timeStamp;
                           new PickImage().CaptureImage(getActivity(), new clsHardCode().txtFolderAkuisisi, fileName,CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);
                        }
                        return true;

                    case R.id.action_save:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle("Confirm");
                        builder.setMessage("Are You sure?");

                        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if (lv_akuisisi.getChildCount()==0){
                                    new ToastCustom().showToasty(getContext(), "Please take at least one picture", 4);
                                }else if (etDtExpired.getText().toString().equals("")){
                                    new ToastCustom().showToasty(getContext(), "Please select the date", 4);
                                }else if (etNoDoc.getText().toString().equals("")){
                                    new ToastCustom().showToasty(getContext(), "Please fill number of document", 4);
                                }else if (MapTab.get(spnAddSubAkuisisi.getSelectedItem())==0){
                                    new ToastCustom().showToasty(getContext(), "Please select type of akuisisi", 4);
                                }else {
                                    tAkuisisiHeader dt = new tAkuisisiHeader();
                                    dt.setTxtHeaderId(dtHeader.getTxtHeaderId());
//                            dt.setDtExpiredDate(etDtExpired.getText().toString());
                                    dt.setDtExpiredDate(parseDateTime(etDtExpired.getText().toString()));
                                    dt.setTxtNoDoc(etNoDoc.getText().toString());
                                    dt.setIntFlagPush(new clsHardCode().Save);
                                    dt.setTxtUserName("");
                                    dt.setIntSubSubActivityId(MapTab.get(txtSubSubActivity));
                                    dt.setIntUserId(dtUserLogin.getIntUserID());
                                    dt.setIntRoleId(dtUserLogin.getIntRoleID());
                                    dt.setIntAreaId(dataPlan.getTxtAreaId());
                                    if (dataPlan.getIntActivityId()==1){
                                        dt.setIntDokterId(dataCheckinActive.getTxtDokterId());
                                    }else if (dataPlan.getIntActivityId()==2){
                                        dt.setIntApotekID(dataCheckinActive.getTxtApotekId());
                                    }
                                    dt.setTxtRealisasiVisitId(dataCheckinActive.getTxtRealisasiVisitId());
                                    dt.setIntFlagShow(new clsHardCode().Save);
                                    dt.setIntSubSubActivityTypeId(new clsHardCode().TypeFoto);
                                    try {
                                        dtHeaderRepo.createOrUpdate(dt);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    new ToastCustom().showToasty(getContext(), "Saved", 1);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(SUB_SUB_ACTIVITY, txtSubSubActivity);
                                    new Tools().intentFragmentSetArgument(FragmentAkuisisi.class, "Akuisisi", getContext(), bundle);

                                }
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;

                    default:
                        return false;
                }
            }
        });

//        //set bundle
        Calendar c = Calendar.getInstance();
        final Bundle args = new Bundle();
        args.putInt(CustomDatePicker.YEAR, c.get(Calendar.YEAR));
        args.putInt(CustomDatePicker.MONTH, c.get(Calendar.MONTH));
        args.putInt(CustomDatePicker.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        args.putLong(CustomDatePicker.DATE_MIN, c.getTimeInMillis());
        //set hint for date
        etDtExpired.setHint("Select Date");
//        CustomDatePicker.showHint(etDtExpired, args, CustomDatePicker.format.standard1);
        etDtExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clsDatePicker.showDatePicker(getContext(), etDtExpired, "Expired Date", CustomDatePicker.format.standard1, args, android.R.style.Widget.DeviceDefault.Light.Spinner);
             CustomDatePicker.showDatePicker(getContext(), etDtExpired, "Expired Date", CustomDatePicker.format.standard1, args);
            }
        });

        return v;
    }

    private String parseDateTime(String dateExp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            if (dateExp!=null&& dateExp!="")
            date = dateFormat.parse(dateExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return sdf.format(date);
        }else {
            return "";
        }
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

    private void setAdapterAkusisi(){
        List<tAkuisisiDetail> listDetail = null;
        try {
            if (dataPlan.getIntActivityId()==1){
                dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubIdAndDokterId(MapTab.get(txtSubSubActivity), dataPlan.getTxtDokterId(), new clsHardCode().Draft);
            }else if (dataPlan.getIntActivityId()==2){
                dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubIdAndApotekId(MapTab.get(txtSubSubActivity), dataPlan.getTxtApotekId(), new clsHardCode().Draft);
            }
//            dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubId(MapTab.get(txtSubSubActivity), new clsHardCode().Draft);
            if (dtHeader!=null){
                etDtExpired.setText(parseDate(dtHeader.getDtExpiredDate()));
                etNoDoc.setText(dtHeader.getTxtNoDoc());
                listDetail =  (List<tAkuisisiDetail>) dtDetailRepo.findByHeaderId(dtHeader.getTxtHeaderId());
            }else {
                etDtExpired.setText("");
                etNoDoc.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listImage.clear();
        if (listDetail !=null){
            if (listDetail.size()>0){
                for (tAkuisisiDetail dt : listDetail){
                    clsListImageAdapter data = new clsListImageAdapter();
                    data.setTxtId(dt.getTxtDetailId());
                    data.setBlobImg(dt.getTxtImg());
                    data.setTxtImgName(dt.getTxtImgName());
                    listImage.add(data);
                }
            }
        }
        adapter = new RecyclerGridImageAdapter(getActivity(), listImage);
        lv_akuisisi.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final clsListImageAdapter obj, int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirm");
                builder.setMessage("Are You sure to delete ?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            tAkuisisiDetail dt = (tAkuisisiDetail) dtDetailRepo.findByDetailId(obj.getTxtId());
                            dtDetailRepo.delete(dt);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listImage.remove(obj);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        adapter.setOnImageClickListener(new RecyclerGridImageAdapter.OnImageClickListener() {
            @Override
            public void onItemClick(View view, clsListImageAdapter obj, int position) {
                Intent intent1 = new Intent(getContext(), ImageViewerActivity.class);
//                intent1.putExtra(ZOOM_DIRECTORY, new clsHardCode().txtFolderAkuisisi);
                intent1.putExtra(ZOOM_IMAGE, obj.getTxtId());
                startActivity(intent1);
            }
        });
    }

    private void onItemSpinnerSelected(){
        List<tAkuisisiDetail> listDetail = null;
        try {
            if (dataPlan.getIntActivityId()==1){
                dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubIdAndDokterId(MapTab.get(txtSubSubActivity), dataPlan.getTxtDokterId(), new clsHardCode().Draft);
            }else if (dataPlan.getIntActivityId()==2){
                dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubIdAndApotekId(MapTab.get(txtSubSubActivity), dataPlan.getTxtApotekId(), new clsHardCode().Draft);
            }
//            dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubId(MapTab.get(txtSubSubActivity), new clsHardCode().Draft);
            if (dtHeader!=null){
//                etDtExpired.setText(dtHeader.getDtExpiredDate());
                etDtExpired.setText(parseDate(dtHeader.getDtExpiredDate()));
                etNoDoc.setText(dtHeader.getTxtNoDoc());
                listDetail =  (List<tAkuisisiDetail>) dtDetailRepo.findByHeaderId(dtHeader.getTxtHeaderId());
            }else {
                etDtExpired.setText("");
                etNoDoc.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listImage.clear();
        if (listDetail !=null){
            if (listDetail.size()>0){
                for (tAkuisisiDetail dt : listDetail){
                    clsListImageAdapter data = new clsListImageAdapter();
                    data.setTxtId(dt.getTxtDetailId());
                    data.setBlobImg(dt.getTxtImg());
                    data.setTxtImgName(dt.getTxtImgName());
                    listImage.add(data);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA_CAPTURE_IMAGE1_REQUEST_CODE){
            if (resultCode==-1){
                Uri uri = new UriData().getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderAkuisisi, fileName);
                //get byte array
                byte[] save = new PickImage().getByteImageToSaveRotate(getContext(), uri);
//                new PickImage().decodeByteArraytoImageFile(save, new clsHardCode().txtFolderAkuisisi, "testing");
                try {
                    if (dataPlan.getIntActivityId()==1){
                        dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubIdAndDokterId(MapTab.get(txtSubSubActivity), dataPlan.getTxtDokterId(), new clsHardCode().Draft);
                    }else if (dataPlan.getIntActivityId()==2){
                        dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubIdAndApotekId(MapTab.get(txtSubSubActivity), dataPlan.getTxtApotekId(), new clsHardCode().Draft);
                    }
//                    dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubId(MapTab.get(txtSubSubActivity), new clsHardCode().Draft);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tAkuisisiDetail dt = new tAkuisisiDetail();
                dt.setTxtDetailId(new clsActivity().GenerateGuid());
                dt.setTxtHeaderId(dtHeader.getTxtHeaderId());
                dt.setTxtImg(save);
                dt.setTxtImgName(fileName);
                try {
                    dtDetailRepo.createOrUpdate(dt);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                clsListImageAdapter dtImg = new clsListImageAdapter();
                dtImg.setTxtId(dt.getTxtDetailId());
                dtImg.setBlobImg(dt.getTxtImg());
                dtImg.setTxtImgName(fileName);
                listImage.add(dtImg);
                adapter.notifyDataSetChanged();
            }else if (resultCode == 0) {
                new clsMainActivity().showCustomToast(getContext(), "User canceled photo", false);
            } else {
                new clsMainActivity().showCustomToast(getContext(), "Something error", false);
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString(SUB_SUB_ACTIVITY, txtSubSubActivity);
        new Tools().intentFragmentSetArgument(FragmentAkuisisi.class, "Akuisisi", getContext(), bundle);
        return true;
    }

}
