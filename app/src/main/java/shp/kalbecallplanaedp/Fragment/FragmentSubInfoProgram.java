package shp.kalbecallplanaedp.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ListView;

import shp.kalbecallplanaedp.Common.mApotek;
import shp.kalbecallplanaedp.Common.mDokter;
import shp.kalbecallplanaedp.Common.mFileAttachment;
import shp.kalbecallplanaedp.Common.tInfoProgramDetail;
import shp.kalbecallplanaedp.Common.tInfoProgramHeader;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.ImageViewerActivity;
import shp.kalbecallplanaedp.Model.clsInfoProgram;
import shp.kalbecallplanaedp.Model.clsListItemAdapter;
import shp.kalbecallplanaedp.PDFViewer;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Repo.mApotekRepo;
import shp.kalbecallplanaedp.Repo.mDokterRepo;
import shp.kalbecallplanaedp.Repo.mFileAttachmentRepo;
import shp.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import shp.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import shp.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import shp.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import shp.kalbecallplanaedp.adapter.AdapterListInfoProgram;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Dewi Oktaviani on 11/1/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentSubInfoProgram extends Fragment {
    View v;
    tInfoProgramHeader header;
    tInfoProgramHeaderRepo headerRepo;
    tInfoProgramDetail detail;
    List<tInfoProgramDetail> listDetail;
    tInfoProgramDetailRepo detailRepo;
    int intSubSubActivity;
    AdapterListInfoProgram adapter;
    ListView listView;
    CheckBox checkBox;
    private String PDF_View = "pdf viewer";
    private String ZOOM_IMAGE_INFO ="zoom image info program";
    private static List<clsInfoProgram> itemAdapterList0 = new ArrayList<>();
    private static List<clsInfoProgram> itemAdapterList1 = new ArrayList<>();
    private static List<clsInfoProgram> itemAdapterList2 = new ArrayList<>();
    mDokter dokter;
    String strName;
    int index;
    boolean isFromHistory;
    SwipeRefreshLayout swpInfo;
    tRealisasiVisitPlan dtCheckinActive;


    public FragmentSubInfoProgram(tInfoProgramHeader header, int intSubSubActivity, int index, boolean isFromHistory, tRealisasiVisitPlan dtCheckinActive){
        this.header = header;
        this.intSubSubActivity = intSubSubActivity;
        this.index = index;
        this.isFromHistory = isFromHistory;
        this.dtCheckinActive = dtCheckinActive;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_infoprogram, container, false);
        listView = (ListView) v.findViewById(R.id.lv_infoprogram);
        swpInfo = (SwipeRefreshLayout)v.findViewById(R.id.swpInfo);

        detailRepo = new tInfoProgramDetailRepo(getContext());
        headerRepo = new tInfoProgramHeaderRepo(getContext());


        loadData();

        swpInfo.setOnRefreshListener(refreshListener);
        swpInfo.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                swpInfo.setEnabled(firstVisibleItem==0);
            }
        });
        return v;
    }

    public void loadData(){
        if (index==0){
            itemAdapterList0.clear();
        }else if (index==1){
            itemAdapterList1.clear();
        }else {
            itemAdapterList2.clear();
        }


        if (header!=null){
            try {
//                if (header.getIntActivityId()==new clsHardCode().VisitDokter){
//                    dokter  = new mDokterRepo(getContext()).findBytxtId(header.getIntDokterId());
//                    if (dokter.getTxtLastName()!=null){
//                        strName = dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
//                    }else {
//                        strName = dokter.getTxtFirstName();
//                    }
//                }else {
//                    strName = new mApotekRepo(getContext()).findBytxtId(header.getIntApotekId()).getTxtName();
//                }
                if (header.getIntActivityId()==new clsHardCode().VisitDokter){
                    dokter  = new mDokterRepo(getContext()).findBytxtId(header.getIntDokterId());
                    if (dokter!=null){
                        if (dokter.getTxtLastName()!=null){
                            strName = dokter.getTxtFirstName() + " " + dokter.getTxtLastName();
                        }else {
                            strName = dokter.getTxtFirstName();
                        }
                    }else {
                        strName = dtCheckinActive.getTxtDokterName();
                    }
                }else {
                    strName = dtCheckinActive.getTxtApotekName();
                }

                listDetail = (List<tInfoProgramDetail>) detailRepo.findByHeaderIdandSubsubId(header.getTxtHeaderId(), intSubSubActivity);
                if (listDetail!=null){
                    if (listDetail.size()>0){
                        boolean validAll = false;
                        boolean validFile= false;
                        boolean validDesc = false;
                        for (tInfoProgramDetail data : listDetail){
                            clsInfoProgram itemAdapter = new clsInfoProgram();
                            mFileAttachment attach = (mFileAttachment) new mFileAttachmentRepo(getContext()).findById(data.getIntFileAttachmentId());
                            itemAdapter.setTxtId(data.getTxtDetailId());
                            itemAdapter.setIntFileId(data.getIntFileAttachmentId());
                            itemAdapter.setTxtTittle(strName); //nama dokter substring(0,1)
                            itemAdapter.setIntColor(R.color.purple_600);
                            itemAdapter.setTxtImgName((strName.substring(0,1)).toUpperCase());
                            itemAdapter.setChecked(data.isBoolFlagChecklist());
                            itemAdapter.setFromHistory(isFromHistory);

                            if (data.getIntFlagPush()==new clsHardCode().Draft){
                                itemAdapter.setTxtStatus("Draft");
                                itemAdapter.setIntColorStatus(R.color.grey_60);
                            }else if (data.getIntFlagPush()==new clsHardCode().Save){
                                itemAdapter.setTxtStatus("Submit");
                                itemAdapter.setIntColorStatus(R.color.red_200);
                            }else if (data.getIntFlagPush()==new clsHardCode().Sync){
                                itemAdapter.setTxtStatus("Sync");
                                itemAdapter.setIntColorStatus(R.color.green_300);
                            }
                            if ((attach.getDescription()!=null&& (attach.getTxtFileName()!=null))){
                                if (!attach.getDescription().equals("")&&!attach.getTxtFileName().equals("")){
                                    validAll = true;
                                }
                            }
                            if (attach.getDescription()!=null&&validAll==false){
                                if (!attach.getDescription().equals("")){
                                    validDesc = true;
                                }
                            }if (attach.getTxtFileName()!=null&&validAll==false){
                                if (!attach.getTxtFileName().equals("")){
                                    validFile = true;
                                }
                            }

                            if (validAll){
                                itemAdapter.setTxtSubTittle(attach.getDescription());
                                itemAdapter.setTxtDesc(attach.getTxtFileName());
                                itemAdapter.setTxtFileName(attach.getTxtFileName());
                                itemAdapter.setIntFlagContent(new clsHardCode().AllInfo);
                            }
                            if (validDesc){
                                itemAdapter.setTxtSubTittle(attach.getDescription());
                                itemAdapter.setTxtDesc("");
                                itemAdapter.setTxtFileName("");
                                itemAdapter.setIntFlagContent(new clsHardCode().OnlyDesc);
                            }

                            if (validFile){
                                itemAdapter.setTxtSubTittle("");
                                itemAdapter.setTxtDesc(attach.getTxtFileName());
                                itemAdapter.setTxtFileName(attach.getTxtFileName());
                                itemAdapter.setIntFlagContent(new clsHardCode().OnlyFile);
                            }

                            if (index==0){
                                itemAdapterList0.add(itemAdapter);
                            }else if (index==1){
                                itemAdapterList1.add(itemAdapter);
                            }else {
                                itemAdapterList2.add(itemAdapter);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (index==0){
            adapter = new AdapterListInfoProgram(getContext(), itemAdapterList0);
        }else if (index==1){
            adapter = new AdapterListInfoProgram(getContext(), itemAdapterList1);
        }else {
            adapter = new AdapterListInfoProgram(getContext(), itemAdapterList2);
        }

        listView.setAdapter(adapter);
        listView.setDivider(null);
        adapter.setOnItemClickListener(new AdapterListInfoProgram.onItemClickListener() {
            @Override
            public void onItemClick(View view, clsInfoProgram obj, int position) {
//                ToastCustom.showToasty(getActivity(),"anyeong",4);
                String fileExtension = (obj.getTxtFileName()).substring((obj.getTxtFileName()).lastIndexOf("."));
                try {
                    mFileAttachment attach = (mFileAttachment) new mFileAttachmentRepo(getContext()).findById(obj.getIntFileId());
                    if (attach.getBlobFile()==null){
                        new ToastCustom().showToasty(getContext(),"Please download file info program...",4);
                    }else {
                        if (fileExtension.equals(".jpg")){
                            Intent intent = new Intent(getContext(), ImageViewerActivity.class);
                            intent.putExtra(ZOOM_IMAGE_INFO, obj.getIntFileId());
                            startActivity(intent);
                        }else if (fileExtension.equals(".pdf")){
                            Intent intent1 = new Intent(getContext(), PDFViewer.class);
                            intent1.putExtra(PDF_View, obj.getIntFileId());
                            startActivity(intent1);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        adapter.setOnCheckboxClickListener(new AdapterListInfoProgram.onCheckboxClickListener() {
            @Override
            public void onItemClick(final View view, final clsInfoProgram obj, int position) {

                if (view!=null){
                    checkBox = (CheckBox) view;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Confirm");
                builder.setMessage("Are You sure? (it can't be undo)");

                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            detail = detailRepo.findByDetailId(obj.getTxtId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        saveData();
                        checkBox.setChecked(true);
                        checkBox.setEnabled(false);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkBox.setChecked(false);
                        checkBox.setEnabled(true);
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
                alert.setCancelable(false);

            }
        });
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData();
//            adapter.notifyDataSetChanged();
            swpInfo.setRefreshing(false);
        }
    };


    public void saveData(){
        try {
            tInfoProgramHeader dtHeader = header;
            dtHeader.setIntFlagPush(new clsHardCode().Save);
            headerRepo.createOrUpdate(dtHeader);
            DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            tInfoProgramDetail data = detail;
//            data.setTxtHeaderId(detail.getTxtHeaderId());
//            data.setTxtDetailId(detail.getTxtDetailId());
//            data.setIntSubDetailActivityId(detail.getIntSubDetailActivityId());
//            data.setTxtFileName(detail.getTxtFileName());
//            data.setBlobFile(detail.getBlobFile());
            data.setBoolFlagChecklist(checkBox.isChecked());
            data.setDtChecklist(dateTimeFormat.format(cal.getTime()));
            data.setIntFlagPush(new clsHardCode().Save);
//            data.setDescription(data.getDescription());
            detailRepo.createOrUpdate(data);
            loadData();
            new ToastCustom().showToasty(getContext(),"Saved",1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getRandomColor(){
        Random rnd = new Random();
//        return Color.argb(255, rnd.nextInt(50), rnd.nextInt(50), rnd.nextInt(50));
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
