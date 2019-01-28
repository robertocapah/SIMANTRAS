package shp.kalbecallplanaedp.Fragment;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shp.kalbecallplanaedp.BL.clsHelperBL;
import shp.kalbecallplanaedp.BL.clsMainBL;
import shp.kalbecallplanaedp.Common.VMDownloadFile;
import shp.kalbecallplanaedp.Common.clsToken;
import shp.kalbecallplanaedp.Common.mActivity;
import shp.kalbecallplanaedp.Common.mApotek;
import shp.kalbecallplanaedp.Common.mDokter;
import shp.kalbecallplanaedp.Common.mFileAttachment;
import shp.kalbecallplanaedp.Common.mSubActivity;
import shp.kalbecallplanaedp.Common.mSubSubActivity;
import shp.kalbecallplanaedp.Common.mTypeSubSubActivity;
import shp.kalbecallplanaedp.Common.mUserLogin;
import shp.kalbecallplanaedp.Common.mUserMappingArea;
import shp.kalbecallplanaedp.Common.tAkuisisiDetail;
import shp.kalbecallplanaedp.Common.tAkuisisiHeader;
import shp.kalbecallplanaedp.Common.tInfoProgramDetail;
import shp.kalbecallplanaedp.Common.tInfoProgramHeader;
import shp.kalbecallplanaedp.Common.tMaintenanceDetail;
import shp.kalbecallplanaedp.Common.tMaintenanceHeader;
import shp.kalbecallplanaedp.Common.tNotification;
import shp.kalbecallplanaedp.Common.tProgramVisit;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivity;
import shp.kalbecallplanaedp.Common.tProgramVisitSubActivityAttachment;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.VolleyResponseListener;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.MainMenu;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Repo.clsTokenRepo;
import shp.kalbecallplanaedp.Repo.mActivityRepo;
import shp.kalbecallplanaedp.Repo.mApotekRepo;
import shp.kalbecallplanaedp.Repo.mDokterRepo;
import shp.kalbecallplanaedp.Repo.mFileAttachmentRepo;
import shp.kalbecallplanaedp.Repo.mSubActivityRepo;
import shp.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import shp.kalbecallplanaedp.Repo.mTypeSubSubActivityRepo;
import shp.kalbecallplanaedp.Repo.mUserLoginRepo;
import shp.kalbecallplanaedp.Repo.mUserMappingAreaRepo;
import shp.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import shp.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import shp.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import shp.kalbecallplanaedp.Repo.tInfoProgramHeaderRepo;
import shp.kalbecallplanaedp.Repo.tMaintenanceDetailRepo;
import shp.kalbecallplanaedp.Repo.tMaintenanceHeaderRepo;
import shp.kalbecallplanaedp.Repo.tNotificationRepo;
import shp.kalbecallplanaedp.Repo.tProgramVisitRepo;
import shp.kalbecallplanaedp.Repo.tProgramVisitSubActivityAttachmentRepo;
import shp.kalbecallplanaedp.Repo.tProgramVisitSubActivityRepo;
import shp.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import shp.kalbecallplanaedp.ResponseDataJson.downloadAllData.DownloadAllData;
import shp.kalbecallplanaedp.ResponseDataJson.downloadApotek.DownlaodApotek;
import shp.kalbecallplanaedp.ResponseDataJson.downloadApotekAEDP.DownlaodApotekAep;
import shp.kalbecallplanaedp.ResponseDataJson.downloadDokter.DownloadDokter;
import shp.kalbecallplanaedp.ResponseDataJson.downloadDoterAedp.DownloadDokterAEDP;
import shp.kalbecallplanaedp.ResponseDataJson.downloadSubActivity.DownloadSubActivity;
import shp.kalbecallplanaedp.ResponseDataJson.downloadSubActivityDetail.DownloadSubActivityDetail;
import shp.kalbecallplanaedp.ResponseDataJson.downloadTRealisasi.DownloadTRealisasi;
import shp.kalbecallplanaedp.ResponseDataJson.downloadmActivity.DownloadmActivity;
import shp.kalbecallplanaedp.ResponseDataJson.downloadtAkuisisi.DownloadtAkuisisi;
import shp.kalbecallplanaedp.ResponseDataJson.downloadtInfoProgram.DownloadtInfoProgram;
import shp.kalbecallplanaedp.ResponseDataJson.downloadtMaintenance.DownloadtMaintenance;
import shp.kalbecallplanaedp.ResponseDataJson.downloadtMappingArea.DownloadtMappingArea;
import shp.kalbecallplanaedp.ResponseDataJson.downloadtProgramVisit.DownloadtProgramVisit;
import shp.kalbecallplanaedp.ResponseDataJson.loginMobileApps.LoginMobileApps;
import shp.kalbecallplanaedp.Utils.IOBackPressed;
import shp.kalbecallplanaedp.Utils.LongThread;
import shp.kalbecallplanaedp.Utils.ReceiverDownloadManager;
import shp.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickFile;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.kalbe.mobiledevknlibs.library.badgeall.viewbadger.ShortcutBadgeException;
import com.kalbe.mobiledevknlibs.library.badgeall.viewbadger.ShortcutBadger;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentDownloadData extends Fragment implements Handler.Callback{
    View v;

    ArrayAdapter<String> dataAdapter;
    List<String> itemList = new ArrayList<>();
    private LinearLayout ln_download_all, ln_branch_downlaod, ln_download_apotek,ln_download_dokter, ln_download_activity,ln_download_subactivity,ln_download_subsub_activity, ln_download_realisasi, ln_download_akuisisi, ln_download_maintenance, ln_download_infoprogram;
    private  TextView tv_download_branch, tv_download_apotek,tv_download_dokter, tv_download_activity, tv_download_subactivity, tv_download_subsubactivity, tv_download_typesubsubactivity, tv_download_realisasi, tv_download_akuisisi, tv_download_maintenance, tv_download_infoprogram;
    private TextView tv_count_apotek,tv_count_branch,tv_count_dokter, tv_count_download_activity,tv_count_download_subactivity, tv_count_download_subsubactivity, tv_count_realisasi, tv_count_akuisisi, tv_count_maintenance, tv_count_infoprogram;
    private Gson gson;
    List<clsToken> dataToken;
    clsTokenRepo tokenRepo;
    mUserLoginRepo loginRepo;
    List<mActivity> dataListActivity = new ArrayList<>();
    List<mSubActivity> dataListSubActivity = new ArrayList<>();
    List<mSubSubActivity> dataListSubSubActivity = new ArrayList<>();
    List<mApotek> dataListApotek = new ArrayList<>();
    List<mDokter> dataListDokter = new ArrayList<>();
    List<mUserMappingArea> dataListArea = new ArrayList<>();
    List<tProgramVisit> dataListProgramVist = new ArrayList<>();
    List<tProgramVisitSubActivity> dataListProgramVisitSubActivity = new ArrayList<>();
    List<tProgramVisitSubActivityAttachment> dataListProgramVisitAttachment = new ArrayList<>();
    List<tRealisasiVisitPlan> dataListRealisasi = new ArrayList<>();
    List<tAkuisisiHeader> dataListAkuisisi = new ArrayList<>();
    List<tMaintenanceHeader> dataListMaintenance = new ArrayList<>();
    List<tInfoProgramHeader> dataLIstInfoProgram = new ArrayList<>();
    mActivityRepo dtActivityrepo;
    mApotekRepo apotekRepo;
    mDokterRepo dokterRepo;
    mTypeSubSubActivityRepo dtRepoTypeSubSubActivity;
    mSubActivityRepo dtRepoSubActivity;
    mSubSubActivityRepo dtRepoSubSubActivity;
    mUserMappingAreaRepo dtRepoArea;
    tProgramVisitRepo dtRepoProgramVisit;
    tProgramVisitSubActivityRepo dtRepoProgramVisitSubActivity;
    //    tProgramVisitSubActivityAttachmentRepo dtRepoProVisitAttch;
    tRealisasiVisitPlanRepo dtRepoRealisasi;
    tAkuisisiHeaderRepo dtRepoAkuisisiHeader;
    tAkuisisiDetailRepo dtRepoAkuisisiDetail;
    tNotificationRepo dtRepoNotification;
    tMaintenanceHeaderRepo dtRepoMaintenanceHeader;
    tMaintenanceDetailRepo dtRepoMaintenanceDetail;
    tInfoProgramHeaderRepo dtRepoInfoProgHeader;
    tInfoProgramDetailRepo dtRepoInfoProgDetail;
    mFileAttachmentRepo dtFileRepo;
    List<Long> listId = new ArrayList<>();
    List<VMDownloadFile> vmList = new ArrayList<>();
    CoordinatorLayout cl;
    private String i_View ="Fragment";
    int curCount = 0;
    float totalCount;
    ThreadPoolExecutor executor;
    ProgressDialog progress;
    boolean isFromDownloadAll = false;
    boolean isAllowNotification = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_download_data, container, false);
//        cl = (CoordinatorLayout) v.findViewById(R.id.cons_sb);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        ln_download_all = (LinearLayout)v.findViewById(R.id.ln_download_all);
        ln_branch_downlaod = (LinearLayout) v.findViewById(R.id.ln_branch_download);
        ln_download_apotek = (LinearLayout) v.findViewById(R.id.ln_download_apotek);
        ln_download_dokter = (LinearLayout)v.findViewById(R.id.ln_download_dokter);
        ln_download_activity = (LinearLayout)v.findViewById(R.id.ln_download_activity);
        ln_download_subactivity = (LinearLayout)v.findViewById(R.id.ln_download_subactivity);
        ln_download_subsub_activity = (LinearLayout)v.findViewById(R.id.ln_download_subsub_activity);
        ln_download_realisasi = (LinearLayout)v.findViewById(R.id.ln_download_realisasi);
        ln_download_akuisisi = (LinearLayout)v.findViewById(R.id.ln_download_akuisisi);
        ln_download_maintenance = (LinearLayout)v.findViewById(R.id.ln_download_maintenance);
        ln_download_infoprogram = (LinearLayout)v.findViewById(R.id.ln_download_infoprogram);
//        ln_download_typesub_activity = (LinearLayout)v.findViewById(R.id.ln_download_typesub_activity);

        /*nama download*/
        tv_download_branch = (TextView) v.findViewById(R.id.tv_download_branch);
        tv_download_apotek = (TextView) v.findViewById(R.id.tv_download_apotek);
        tv_download_dokter = (TextView)v.findViewById(R.id.tv_download_dokter);
        tv_download_activity = (TextView)v.findViewById(R.id.tv_download_activity);
        tv_download_subactivity = (TextView)v.findViewById(R.id.tv_download_subactivity);
        tv_download_subsubactivity = (TextView)v.findViewById(R.id.tv_download_subsubactivity);
        tv_download_realisasi = (TextView)v.findViewById(R.id.tv_download_realisasi);
        tv_download_akuisisi = (TextView)v.findViewById(R.id.tv_download_akuisisi);
        tv_download_maintenance = (TextView)v.findViewById(R.id.tv_download_maintenance);
        tv_download_infoprogram = (TextView)v.findViewById(R.id.tv_download_infoprogram);
//        tv_download_typesubsubactivity = (TextView)v.findViewById(R.id.tv_download_typesubsubactivity);

        /*count*/
        tv_count_branch = (TextView)v.findViewById(R.id.tv_count_branch);
        tv_count_apotek = (TextView)v.findViewById(R.id.tv_count_apotek);
        tv_count_dokter = (TextView)v.findViewById(R.id.tv_count_dokter);
        tv_count_download_activity = (TextView) v.findViewById(R.id.tv_count_download_activity);
        tv_count_download_subactivity = (TextView) v.findViewById(R.id.tv_count_download_subactivity);
        tv_count_download_subsubactivity = (TextView) v.findViewById(R.id.tv_count_download_subsubactivity);
        tv_count_realisasi = (TextView)v.findViewById(R.id.tv_count_realisasi);
        tv_count_akuisisi = (TextView)v.findViewById(R.id.tv_count_akuisisi);
        tv_count_maintenance = (TextView)v.findViewById(R.id.tv_count_maintenance);
        tv_count_infoprogram = (TextView)v.findViewById(R.id.tv_count_infoprogram);
//        tv_count_download_typesubsubactivity = (TextView)v.findViewById(R.id.tv_count_download_typesubsubactivity);


        loginRepo = new mUserLoginRepo(getContext());
        dtActivityrepo = new mActivityRepo(getContext());
        dtRepoTypeSubSubActivity = new mTypeSubSubActivityRepo(getContext());
        dtRepoSubActivity= new mSubActivityRepo(getContext());
        dtRepoSubSubActivity= new mSubSubActivityRepo(getContext());
        dokterRepo = new mDokterRepo(getContext());
        apotekRepo = new mApotekRepo(getContext());
        dtRepoArea = new mUserMappingAreaRepo(getContext());
        dtRepoProgramVisit = new tProgramVisitRepo(getContext());
        dtRepoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
//        dtRepoProVisitAttch = new tProgramVisitSubActivityAttachmentRepo(getContext());
        dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        dtRepoAkuisisiHeader = new tAkuisisiHeaderRepo(getContext());
        dtRepoAkuisisiDetail = new tAkuisisiDetailRepo(getContext());
        dtRepoNotification = new tNotificationRepo(getContext());
        dtRepoMaintenanceHeader = new tMaintenanceHeaderRepo(getContext());
        dtRepoMaintenanceDetail = new tMaintenanceDetailRepo(getContext());
        dtRepoInfoProgHeader = new tInfoProgramHeaderRepo(getContext());
        dtRepoInfoProgDetail = new tInfoProgramDetailRepo(getContext());
        dtFileRepo = new mFileAttachmentRepo(getContext());

        try {
            dataListArea = (List<mUserMappingArea>) dtRepoArea.findAll();
            if (dataListArea!=null){
                tv_count_branch.setText(String.valueOf(dataListArea.size()));
            }
            dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
            if (dataListActivity!=null){
                tv_count_download_activity.setText(String.valueOf(dataListActivity.size()));
            }
            dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
            if (dataListSubActivity!=null){
                tv_count_download_subactivity.setText(String.valueOf(dataListSubActivity.size()));
            }

            dataListSubSubActivity = (List<mSubSubActivity>) dtRepoSubSubActivity.findAll();
            if (dataListSubSubActivity!=null){
                tv_count_download_subsubactivity.setText(String.valueOf(dataListSubSubActivity.size()));
            }

            dataListApotek = (List<mApotek>) apotekRepo.findAll();
            if (dataListApotek!=null){
                tv_count_apotek.setText(String.valueOf(dataListApotek.size()));
            }

            dataListDokter = (List<mDokter>) dokterRepo.findAll();
            if (dataListDokter!=null){
                tv_count_dokter.setText(String.valueOf(dataListDokter.size()));
            }

            dataListProgramVist = (List<tProgramVisit>) dtRepoProgramVisit.findAll();
            if (dataListProgramVist!=null){

            }

//            dataListRealisasi = (List<tRealisasiVisitPlan>) dtRepoRealisasi.findAll();
//            if (dataListRealisasi!=null){
//                tv_count_realisasi.setText(String.valueOf(dataListRealisasi.size()));
//            }

            dataListProgramVisitSubActivity = (List<tProgramVisitSubActivity>)dtRepoProgramVisitSubActivity.findAll();
            if (dataListProgramVisitSubActivity!=null){
                tv_count_realisasi.setText(String.valueOf(dataListProgramVisitSubActivity.size()));
            }

            dataListAkuisisi = (List<tAkuisisiHeader>) dtRepoAkuisisiHeader.findAll();
            if (dataListAkuisisi!=null){
                tv_count_akuisisi.setText(String.valueOf(dataListAkuisisi.size()));
            }

            dataListMaintenance = (List<tMaintenanceHeader>) dtRepoMaintenanceHeader.findAll();
            if (dataListMaintenance!=null){
                tv_count_maintenance.setText(String.valueOf(dataListMaintenance.size()));
            }

            dataLIstInfoProgram = (List<tInfoProgramHeader>) dtRepoInfoProgHeader.findAll();
            if (dataLIstInfoProgram!=null){
                tv_count_infoprogram.setText(String.valueOf(dataLIstInfoProgram.size()));
            }
//            dataListTypeSubSubActivity = (List<mTypeSubSubActivity>) dtRepoTypeSubSubActivity.findAll();
//            if (dataListTypeSubSubActivity!=null){
//                tv_count_download_typesubsubactivity.setText(String.valueOf(dataListTypeSubSubActivity.size()));
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final boolean isDataReady = new clsMainBL().isDataReady(getContext());
        ln_download_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDokter(true);
//                downloadAllData();
            }
        });

        ln_branch_downlaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListArea = (List<mUserMappingArea>) dtRepoArea.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListArea!=null){
                    if (dataListArea.size()>0){
                        for (mUserMappingArea data : dataListArea){
                            itemList.add(String.valueOf(data.getIntUserMappingAreaId()) + " - " + data.getTxtKecamatanID());
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_branch_downlaod, tv_download_branch, "mUserMappingArea");
            }
        });

        ln_download_apotek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListApotek = (List<mApotek>) apotekRepo.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListApotek!=null){
                    if (dataListApotek.size()>0){
                        for (mApotek data : dataListApotek){
                            itemList.add(String.valueOf(data.getTxtCode()) + " - " + data.getTxtName());
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_apotek, tv_download_apotek, "mApotek");
            }
        });

        ln_download_dokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListDokter = (List<mDokter>) dokterRepo.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListDokter!=null){
                    if (dataListDokter.size()>0){
                        for (mDokter data : dataListDokter){
                            if (data.getTxtLastName()!=null){
                                itemList.add(String.valueOf(data.getTxtId()) + " - " + data.getTxtFirstName() + " " + data.getTxtLastName());
                            }else {
                                itemList.add(String.valueOf(data.getTxtId()) + " - " + data.getTxtFirstName());
                            }

                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_dokter, tv_download_dokter, "mDokter");
            }
        });
        ln_download_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListActivity!=null){
                    if (dataListActivity.size()>0){
                        for (mActivity data : dataListActivity){
                            itemList.add(String.valueOf(data.getIntActivityId()) + " - " + data.getTxtName());
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_activity, tv_download_activity, "mActivity");
            }
        });

        ln_download_subactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataListSubActivity!=null){
                    if (dataListSubActivity.size()>0){
                        for (mSubActivity data : dataListSubActivity){
                            mActivity activity = null;
                            try {
                                activity = (mActivity) dtActivityrepo.findById(data.getIntActivityid());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            itemList.add(String.valueOf(data.getIntSubActivityid()) + ". " + activity.getTxtName() + " - " + data.getTxtName());
                        }
                    }else {
                        itemList.add(" - ");
                    }
                }
                onButtonOnClick(ln_download_subactivity, tv_download_subactivity, "mSubActivity");
            }
        });

        ln_download_subsub_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                try {
                    dataListSubSubActivity = (List<mSubSubActivity>) dtRepoSubSubActivity.findAll();
                    if (dataListSubSubActivity!=null){
                        if (dataListSubSubActivity.size()>0){
                            for (mSubSubActivity data : dataListSubSubActivity){
                                mSubActivity _mSubActivity = (mSubActivity) dtRepoSubActivity.findById(data.getIntSubActivityid());
                                mActivity _mActivity = (mActivity) dtActivityrepo.findById(_mSubActivity.getIntActivityid());
                                itemList.add(String.valueOf(data.getIntSubSubActivityid()) + ". " + _mActivity.getTxtName() + " - " + _mSubActivity.getTxtName() + " - " + data.getTxtName());
                            }
                        }else {
                            itemList.add(" - ");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                onButtonOnClick(ln_download_subsub_activity, tv_download_subsubactivity, "mSubSubActivity");
            }
        });

        ln_download_realisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataReady){
                    itemList.clear();
                    try {
                        dataListProgramVisitSubActivity = (List<tProgramVisitSubActivity>) dtRepoProgramVisitSubActivity.findAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (dataListProgramVisitSubActivity!=null){
                        if (dataListProgramVisitSubActivity.size()>0){
                            int index = 0;
                            for (tProgramVisitSubActivity data : dataListProgramVisitSubActivity){
                                index++;
                                mActivity activity = null;
                                try {
                                    activity = (mActivity) dtActivityrepo.findById(data.getIntActivityId());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                if (data.getIntActivityId()==new clsHardCode().VisitDokter){
                                    itemList.add(String.valueOf(index)+ " - " + activity.getTxtName() + ", " + data.getTxtDokterName());
                                }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
                                    itemList.add(String.valueOf(index)+ " - " + activity.getTxtName() + ", " + data.getTxtApotekName());
                                }else {
                                    itemList.add(String.valueOf(index)+ " - " + activity.getTxtName());
                                }
                            }
                        }else {
                            itemList.add(" - ");
                        }
                    }
                    onButtonOnClick(ln_download_realisasi, tv_download_realisasi, "tRealisasiVisitPlan");
                }else {
                    new ToastCustom().showToasty(getContext(),"Please download all data master",4);
                }
            }
        });


        ln_download_akuisisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataReady){
                    itemList.clear();
                    List<tRealisasiVisitPlan> dtListRealisasi = new ArrayList<>();
                    try {
                        dataListAkuisisi = (List<tAkuisisiHeader>) dtRepoAkuisisiHeader.findAll();
                        dtListRealisasi = (List<tRealisasiVisitPlan>) dtRepoRealisasi.findAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (dtListRealisasi.size()>0){
                        if (dataListAkuisisi!=null){
                            if (dataListAkuisisi.size()>0){
                                int index = 0;
                                for (tAkuisisiHeader data : dataListAkuisisi){
                                    index++;
                                    String name = null;
                                    try {
                                        if (data.getIntDokterId()!=null){
                                            if (!data.getIntDokterId().equals("null")){
//                                            name = "Dokter " + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName();
                                                name = "Dokter " + dtRepoRealisasi.findBytxtDokterId(data.getIntDokterId()).getTxtDokterName();
                                            }else if (data.getIntApotekID()!=null){
                                                if (!data.getIntApotekID().equals("null")){
//                                                name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                                    name = dtRepoRealisasi.findBytxtApotekId(data.getIntApotekID()).getTxtApotekName();
                                                }
                                            }
                                        }else if (data.getIntApotekID()!=null){
                                            if (!data.getIntApotekID().equals("null")){
                                                name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                            }
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    itemList.add(String.valueOf(index) + " - Akuisisi - " + name);
                                }
                            }else {
                                itemList.add(" - ");
                            }
                        }
                        onButtonOnClick(ln_download_akuisisi, tv_download_akuisisi, "tAkuisisiHeader");
                    }else {
                        new ToastCustom().showToasty(getContext(),"Please download data realisasi call plan",4);
                    }
                }else {
                    new ToastCustom().showToasty(getContext(),"Please download all data master",4);
                }
            }
        });

        ln_download_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataReady){
                    itemList.clear();
                    List<tRealisasiVisitPlan> dtListRealisasi = new ArrayList<>();
                    try {
                        dataListMaintenance = (List<tMaintenanceHeader>) dtRepoMaintenanceHeader.findAll();
                        dtListRealisasi = (List<tRealisasiVisitPlan>) dtRepoRealisasi.findAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (dtListRealisasi.size()>0){
                        if (dataListMaintenance!=null){
                            if (dataListMaintenance.size()>0){
                                int index = 0;
                                for (tMaintenanceHeader data : dataListMaintenance){
                                    index++;
                                    String name = null;
                                    try {
                                        if (data.getIntActivityId()==new clsHardCode().VisitDokter){
                                            name = "Dokter " + dtRepoRealisasi.findBytxtDokterId(data.getIntDokterId()).getTxtDokterName();
//                                        name = "Dokter " + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName();
                                        }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
                                            name = dtRepoRealisasi.findBytxtApotekId(data.getIntApotekID()).getTxtApotekName();
//                                        name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    itemList.add(String.valueOf(index) + " - Maintenance - " + name);
                                }
                            }else {
                                itemList.add(" - ");
                            }
                        }
                        onButtonOnClick(ln_download_maintenance, tv_download_maintenance, "tMaintenanceHeader");
                    }else {
                        new ToastCustom().showToasty(getContext(),"Please download data realisasi call plan",4);
                    }
                }else {
                    new ToastCustom().showToasty(getContext(),"Please download all data master",4);
                }
            }
        });

        ln_download_infoprogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataReady){
                    itemList.clear();
                    List<tRealisasiVisitPlan> dtListRealisasi = new ArrayList<>();
                    try {
                        dataLIstInfoProgram = (List<tInfoProgramHeader>) dtRepoInfoProgHeader.findAll();
                        dtListRealisasi = (List<tRealisasiVisitPlan>) dtRepoRealisasi.findAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (dtListRealisasi.size()>0){
                        if (dataLIstInfoProgram!=null){
                            if (dataLIstInfoProgram.size()>0){
                                int index = 0;
                                for (tInfoProgramHeader data : dataLIstInfoProgram){
                                    index++;
                                    String name = null;
                                    try {
                                        if (data.getIntActivityId()==new clsHardCode().VisitDokter){
                                            name = "Dokter " + dtRepoRealisasi.findBytxtDokterId(data.getIntDokterId()).getTxtDokterName();
//                                        name = "Dokter " + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName();
                                        }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
                                            name = dtRepoRealisasi.findBytxtApotekId(data.getIntApotekId()).getTxtApotekName();
//                                        name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    itemList.add(String.valueOf(index) + " - Info Program - " + name);
                                }
                            }else {
                                itemList.add(" - ");
                            }
                        }
                        onButtonOnClick(ln_download_infoprogram, tv_download_infoprogram, "tInfoProgramHeader");
                    }else {
                        new ToastCustom().showToasty(getContext(),"Please download data realisasi call plan",4);
                    }
                }else {
                    new ToastCustom().showToasty(getContext(),"Please download all data master",4);
                }
            }
        });

        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

        executor = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
        return v;
    }

    private  void onButtonOnClick(LinearLayout ln_click, TextView tv_click, final String txtDownlaod){
        RelativeLayout rl_download = (RelativeLayout) ln_click.getChildAt(0);
        ImageView img_download = (ImageView) rl_download.getChildAt(0);
        int color = setButtonColor(txtDownlaod);
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//            color = ImageViewCompat.getImageTintList(img_download).getDefaultColor();
//        }else {
//            ColorDrawable drawable = (ColorDrawable) img_download.getBackground();
//            color = drawable.getColor();
//        ColorStateList clr = ImageViewCompat.getImageTintList(img_download);
//            Drawable background = img_download.getBackground();
//            if (background instanceof ColorDrawable) {
//                color = ((ColorDrawable) background).getColor();
//                // Use color here
//            }
//        }

        String item =  tv_click.getText().toString();

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        AppBarLayout appBarLayout = (AppBarLayout) dialog.findViewById(R.id.appbar_download);
        final AppCompatSpinner spnDownload = (AppCompatSpinner) dialog.findViewById(R.id.spnDownload);
        final  TextView tv_download_title = (TextView) dialog.findViewById(R.id.tv_title_download);
        final  Button btn_download = (Button) dialog.findViewById(R.id.btn_download);
//        Drawable drawable = getResources().getDrawable(R.drawable.btn_rounded_green_300);
//        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        btn_download.setBackgroundColor(getResources().getColor(color));
        appBarLayout.setBackgroundColor(getResources().getColor(R.color.green_300));
        tv_download_title.setText(item);

        dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,itemList);
//        final ArrayAdapter<String> array = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, itemList);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spnDownload.setAdapter(dataAdapter);
        spnDownload.setSelection(0);

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDataResult(txtDownlaod);
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private int setButtonColor(String txtDownlaod) {
        int color = 0;
        if (txtDownlaod.equals("mActivity")){
            color = R.color.blue_300;
        }else if (txtDownlaod.equals("mSubActivity")){
            color = R.color.red_100;
        } else if (txtDownlaod.equals("mSubSubActivity")){
            color = R.color.green_A700;
        } else if (txtDownlaod.equals("mApotek")){
            color = R.color.indigo_400;
        } else if (txtDownlaod.equals("mDokter")){
            color = R.color.red_300;
        } else if (txtDownlaod.equals("mUserMappingArea")){
            color = R.color.orange_400;
        }else if (txtDownlaod.equals("tRealisasiVisitPlan")){
            color = R.color.red_300;
        }else if (txtDownlaod.equals("tAkuisisiHeader")){
            color = R.color.light_green_500;
        }else if (txtDownlaod.equals("tMaintenanceHeader")){
            color = R.color.orange_400;
        }else if (txtDownlaod.equals("tInfoProgramHeader")){
            color = R.color.indigo_400;
        }
        return color;
    }

    private void displayDataResult(String txtDownlaod) {
        boolean isDataReady = new clsMainBL().isDataReady(getContext());
        dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
        List<tRealisasiVisitPlan> dtListRealisasi = new ArrayList<>();
        try {
            dtListRealisasi = (List<tRealisasiVisitPlan>) dtRepoRealisasi.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (txtDownlaod.equals("mActivity")){
            downloadActivity();
        }else if (txtDownlaod.equals("mSubActivity")){
            try {
                dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dataListActivity.size()>0){
                downloadSubActivity();
            }else {
                new ToastCustom().showToasty(getContext(),"Please download data activity",4);
            }

        } else if (txtDownlaod.equals("mSubSubActivity")){
            try {
                dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dataListSubActivity.size()>0){
                downloadSubSubActivity();
            }else {
                new ToastCustom().showToasty(getContext(),"Please download data sub activity",4);
            }

        } else if (txtDownlaod.equals("mApotek")){
            downloadApotek(false);
        } else if (txtDownlaod.equals("mDokter")){
            downloadDokter(false);
        } else if (txtDownlaod.equals("mUserMappingArea")){
            downloadArea();
        }else{
            if (isDataReady){
                if (txtDownlaod.equals("tRealisasiVisitPlan")){
                    downloadtCallPlan();
                }else if (dtListRealisasi.size()>0){
                    if (txtDownlaod.equals("tAkuisisiHeader")){
                        downloadtAkuisisi();
                    }else if (txtDownlaod.equals("tMaintenanceHeader")){
                        downloadtMaintenace();
                    }else if (txtDownlaod.equals("tInfoProgramHeader")){
                        downloadtInfoProgram();
                    }
                }else {
                    new ToastCustom().showToasty(getContext(),"Please download data realisasi call plan",4);
                }
            }else {
                new ToastCustom().showToasty(getContext(),"Please download all data master",4);
            }
        }


    }

    private JSONObject ParamDownloadMaster(){
        JSONObject jsonObject = new JSONObject();
        try {
            mUserLogin data = new clsMainBL().getUserLogin(getContext());
            jsonObject.put("userId", data.getIntUserID());
            jsonObject.put("intRoleId", data.getIntRoleID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void downloadAllData() {
        String strLinkAPI = new clsHardCode().linkDownloadAll;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                new ToastCustom().showToasty(getContext(),message,4);
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadAllData model = gson.fromJson(jsonObject.toString(), DownloadAllData.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){

                            listId.clear();
                            vmList.clear();
//                            downloadApotek(true);
//                            downloadDokter(true);
                            if (model.getData().getDataMappingArea().getLtMappingArea()!=null){
                                if (model.getData().getDataMappingArea().getLtMappingArea().size()>0){
                                    dtRepoArea = new mUserMappingAreaRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataMappingArea().getLtMappingArea().size(); i++){
                                        mUserMappingArea data = new mUserMappingArea();
                                        data.setIntUserMappingAreaId(model.getData().getDataMappingArea().getLtMappingArea().get(i).getIntUserMappingAreaId());
                                        data.setIntUserId(model.getData().getDataMappingArea().getLtMappingArea().get(i).getIntUserId());
                                        data.setTxtKecamatanID(model.getData().getDataMappingArea().getLtMappingArea().get(i).getTxtKecamatanID());
                                        dtRepoArea.createOrUpdate(data);
                                    }
                                }
                                dataListArea = (List<mUserMappingArea>) dtRepoArea.findAll();
                                tv_count_branch.setText(String.valueOf(dataListArea.size()));
                            }

                            if (model.getData().getDataActivity().getLtActivity()!=null){
                                if (model.getData().getDataActivity().getLtActivity().size()>0){
                                    dtActivityrepo = new mActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataActivity().getLtActivity().size(); i++){
                                        mActivity data = new mActivity();
                                        data.setIntActivityId(model.getData().getDataActivity().getLtActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getDataActivity().getLtActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataActivity().getLtActivity().get(i).getTxtDescription());
                                        dtActivityrepo.createOrUpdate(data);
                                    }
                                }
                                dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
                                tv_count_download_activity.setText(String.valueOf(dataListActivity.size()));
                            }

                            if (model.getData().getDataSubActivity().getLtSubActivity()!=null){
                                if (model.getData().getDataSubActivity().getLtSubActivity().size()>0){
                                    dtRepoSubActivity = new mSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataSubActivity().getLtSubActivity().size(); i++){
                                        mSubActivity data = new mSubActivity();
                                        data.setIntSubActivityid(model.getData().getDataSubActivity().getLtSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityid(model.getData().getDataSubActivity().getLtSubActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getDataSubActivity().getLtSubActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataSubActivity().getLtSubActivity().get(i).getTxtDescription());
                                        dtRepoSubActivity.createOrUpdate(data);
                                    }
                                }
                                dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
                                tv_count_download_subactivity.setText(String.valueOf(dataListSubActivity.size()));
                            }

                            if (model.getData().getDataSubActivityDetail().getLtSubActivityDetailData()!=null){
                                if (model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().size()>0){
                                    dtRepoSubSubActivity = new mSubSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().size(); i++){
                                        mSubSubActivity data = new mSubSubActivity();
                                        data.setIntSubActivityid(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getIntSubActivityId());
                                        data.setIntSubSubActivityid(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId());
                                        data.setTxtName(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getTxtDescription());
                                        data.setIntType(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().get(i).getIntFlag());
                                        dtRepoSubSubActivity.createOrUpdate(data);
                                    }
                                }
                                dataListSubSubActivity = (List<mSubSubActivity>) dtRepoSubSubActivity.findAll();
                                tv_count_download_subsubactivity.setText(String.valueOf(model.getData().getDataSubActivityDetail().getLtSubActivityDetailData().size()));
                            }

                            if (model.getData().getDatatProgramVisitDetail().getTProgramVisit()!=null){
                                if (model.getData().getDatatProgramVisitDetail().getTProgramVisit().size()>0){
                                    dtRepoProgramVisit = new tProgramVisitRepo(getContext());
                                    for (int i = 0; i <model.getData().getDatatProgramVisitDetail().getTProgramVisit().size(); i++){
                                        tProgramVisit data = new tProgramVisit();
                                        data.setTxtProgramVisitId(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getTxtProgramVisitId());
                                        data.setIntUserId(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getIntRoleId());
                                        data.setDtStart(parseDate(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getDtStart()));
                                        data.setDtEnd(parseDate(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getDtEnd()));
                                        data.setIntStatus(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getIntStatus());
                                        data.setIntType(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getIntType());
                                        data.setTxtNotes(model.getData().getDatatProgramVisitDetail().getTProgramVisit().get(i).getTxtNotes());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoProgramVisit.createOrUpdate(data);
                                    }
                                }
                            }
                            if (model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity()!=null){
                                if (model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().size()>0){
                                    dtRepoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().size(); i++){
                                        tProgramVisitSubActivity data = new tProgramVisitSubActivity();
                                        data.setTxtProgramVisitSubActivityId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtProgramVisitSubActivityId());
                                        data.setTxtProgramVisitId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtProgramVisitId());
                                        data.setTxtApotekId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtApotekId());
                                        data.setTxtApotekName(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtApotekName());
                                        data.setIntType(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getIntFlag());
                                        data.setTxtAreaId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtAreaId());
                                        data.setTxtAreaName(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtAreaName());
                                        data.setTxtDokterId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtDokterId());
                                        data.setTxtDokterName(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtDokterName());
                                        data.setIntSubActivityId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityId(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getIntActivityId());
                                        data.setDtStart(parseDate(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getDtStart()));
                                        data.setDtEnd(parseDate(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getDtEnd()));
                                        data.setTxtNotes(model.getData().getDatatProgramVisitDetail().getTProgramVisitSubActivity().get(i).getTxtDescription());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoProgramVisitSubActivity.createOrUpdate(data);
                                    }
                                }
                            }
                            if (model.getData().getDatatProgramVisitDetail().getRealisasiData()!=null){
                                if (model.getData().getDatatProgramVisitDetail().getRealisasiData().size()>0){
                                    dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
                                    for (int i = 0; i <model.getData().getDatatProgramVisitDetail().getRealisasiData().size(); i++){
                                        tRealisasiVisitPlan data = new tRealisasiVisitPlan();
                                        data.setTxtProgramVisitSubActivityId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtProgramVisitSubActivityId());
                                        data.setTxtRealisasiVisitId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtRealisasiVisitId());
                                        data.setIntRoleID(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getIntRoleId());
                                        data.setTxtDokterId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtDokterId());
                                        data.setIntUserId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getIntUserId());
                                        data.setTxtDokterName(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtNamaDokter());
                                        data.setTxtApotekId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtApotekId());
                                        data.setTxtApotekName(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtNamaApotek());
                                        data.setDtCheckIn(parseDate(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getDtCheckin()));
                                        data.setDtCheckOut(parseDate(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getDtChekout()));
                                        data.setDtDateRealisasi(parseDate(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getDtDateRealisasi()));
                                        data.setDtDatePlan(parseDate(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getDtDatePlan()));
                                        data.setIntNumberRealisasi(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getIntRealisasiNumber());
                                        data.setIntStatusRealisasi(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getIntRealisasiStatus());
                                        data.setTxtAcc(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtAccurasi());
                                        data.setTxtLong(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtLongitude());
                                        data.setTxtLat(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtLatitude());
                                        data.setTxtImgName1(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Name());
                                        data.setTxtImgName2(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Name());
//                                        byte[] file1 = getLogoImage(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Link());
//                                        if (file1!=null){
//                                            data.setBlobImg1(file1);
//                                        }else {
//                                            data.setBlobImg1(null);
//                                        }
                                        if (model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Link()!=null){
                                            if (model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Link().length()>0){
                                                VMDownloadFile file = new VMDownloadFile();
                                                file.setLink(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Link());
                                                file.setGroupDownload(new clsHardCode().REALISASI_SATU);
                                                file.setIndex(i+1);
                                                file.setTxtId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtRealisasiVisitId());
                                                vmList.add(file);
                                            }
//                                            downloadFile(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage1Link(),"Realisasi Pertama", model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtRealisasiVisitId(), i+1);
                                        }

//                                        byte[] file2 = getLogoImage(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Link());
//                                        if (file2!=null){
//                                            data.setBlobImg2(file2);
//                                        }else {
//                                            data.setBlobImg2(null);
//                                        }
                                        if (model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Link()!=null){
                                            if (model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Link().length()>0){
                                                VMDownloadFile file = new VMDownloadFile();
                                                file.setLink(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Link());
                                                file.setGroupDownload(new clsHardCode().REALISASI_DUA);
                                                file.setIndex(i+1);
                                                file.setTxtId(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtRealisasiVisitId());
                                                vmList.add(file);
                                            }
//                                            downloadFile(model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtImage2Link(),"Realisasi Kedua", model.getData().getDatatProgramVisitDetail().getRealisasiData().get(i).getTxtRealisasiVisitId(), i+1);
                                        }
                                        dtRepoRealisasi.createOrUpdate(data);
                                    }
                                }
                                dataListProgramVisitSubActivity = (List<tProgramVisitSubActivity>) dtRepoProgramVisitSubActivity.findAll();
                                tv_count_realisasi.setText(String.valueOf(dataListProgramVisitSubActivity.size()));
                            }

                            if (model.getData().getDataAkuisisiData().getAkuisisiHeader()!=null){
                                if (model.getData().getDataAkuisisiData().getAkuisisiHeader().size()>0){
                                    dtRepoAkuisisiHeader = new tAkuisisiHeaderRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataAkuisisiData().getAkuisisiHeader().size(); i++){
                                        tAkuisisiHeader data = new tAkuisisiHeader();
                                        data.setTxtHeaderId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtAkuisisiHeaderId());
                                        data.setIntSubSubActivityId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntSubDetailActivityId());
                                        data.setIntSubSubActivityTypeId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntFlag());
                                        data.setTxtNoDoc(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtNoDoc());
                                        data.setDtExpiredDate(parseDate(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getDtExpiredDate()));
                                        data.setIntUserId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtDokterId());
                                        data.setIntApotekID(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtApotekId());
                                        data.setIntAreaId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getIntAreaId());
                                        data.setTxtUserName(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtUserName());
                                        data.setTxtRealisasiVisitId(model.getData().getDataAkuisisiData().getAkuisisiHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        data.setIntFlagShow(new clsHardCode().Save);
                                        dtRepoAkuisisiHeader.createOrUpdate(data);
                                    }
                                }
                                dataListAkuisisi = (List<tAkuisisiHeader>) dtRepoAkuisisiHeader.findAll();
                                tv_count_akuisisi.setText(String.valueOf(dataListAkuisisi.size()));
                            }

                            if (model.getData().getDataAkuisisiData().getAkuisisiDetail()!=null){
                                if (model.getData().getDataAkuisisiData().getAkuisisiDetail().size()>0){
                                    dtRepoAkuisisiDetail = new tAkuisisiDetailRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataAkuisisiData().getAkuisisiDetail().size(); i ++){
                                        tAkuisisiDetail data = new tAkuisisiDetail();
                                        data.setTxtHeaderId(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtAkuisisiHeaderId());
                                        data.setTxtDetailId(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtAkuisisiDetailId());
                                        data.setTxtImgName(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtImageName());
//                                        byte[] file = getLogoImage(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtLink());
//                                        if (file!=null){
//                                            data.setTxtImg(file);
//                                        }else {
//                                            data.setTxtImg(null);
//                                        }

                                        if (model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtLink()!=null){
                                            if (model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtLink().length()>0){
                                                VMDownloadFile file = new VMDownloadFile();
                                                file.setLink(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtLink());
                                                file.setGroupDownload(new clsHardCode().AKUISISI);
                                                file.setIndex(i+1);
                                                file.setTxtId(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtAkuisisiDetailId());
                                                vmList.add(file);
                                            }
//                                            downloadFile(model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtLink(),"Akuisisi", model.getData().getDataAkuisisiData().getAkuisisiDetail().get(i).getTxtAkuisisiDetailId(), i+1);
                                        }
                                        dtRepoAkuisisiDetail.createOrUpdate(data);
                                    }
                                }
                            }

                            if (model.getData().getDataNotification().getNotification()!=null){
                                if (model.getData().getDataNotification().getNotification().size()>0){
                                    dtRepoNotification = new tNotificationRepo(getContext());
                                    for (int i=0; i < model.getData().getDataNotification().getNotification().size(); i++){
                                        tNotification data = new tNotification();
                                        data.setIntHeaderAkuisisiId(model.getData().getDataNotification().getNotification().get(i).getTxtAkuisisiHeaderId());
                                        data.setIntActivityId(model.getData().getDataNotification().getNotification().get(i).getIntActivityId());
                                        data.setIntDokterId(model.getData().getDataNotification().getNotification().get(i).getTxtDokterId());
                                        data.setIntApotekId(model.getData().getDataNotification().getNotification().get(i).getTxtApotekId());
                                        data.setDtExpired(parseDate(model.getData().getDataNotification().getNotification().get(i).getDtExpiredDate()));
                                        data.setIntSubDetailActivityId(model.getData().getDataNotification().getNotification().get(i).getIntSubDetailActivityId());
                                        data.setTxtNoDoc(model.getData().getDataNotification().getNotification().get(i).getTxtNoDoc());
                                        dtRepoNotification.createOrUpdate(data);
                                    }
                                }
                            }



                            if (model.getData().getDataMaintenanceData().getLtMaintenanceHeader()!=null){
                                if (model.getData().getDataMaintenanceData().getLtMaintenanceHeader().size()>0){
                                    dtRepoMaintenanceHeader = new tMaintenanceHeaderRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataMaintenanceData().getLtMaintenanceHeader().size(); i++){
                                        tMaintenanceHeader data = new tMaintenanceHeader();
                                        data.setTxtHeaderId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getTxtMaintenanceHeaderId());
                                        data.setTxtRealisasiVisitId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntActivityId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntActivityId());
                                        data.setIntUserId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntDokterId());
                                        data.setIntApotekID(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntApotekId());
                                        data.setIntAreaId(model.getData().getDataMaintenanceData().getLtMaintenanceHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoMaintenanceHeader.createOrUpdate(data);
                                    }
                                }

                                dataListMaintenance = (List<tMaintenanceHeader>) dtRepoMaintenanceHeader.findAll();
                                tv_count_maintenance.setText(String.valueOf(dataListMaintenance.size()));
                            }

                            if (model.getData().getDataMaintenanceData().getLtMaintenanceDetail()!=null){
                                if (model.getData().getDataMaintenanceData().getLtMaintenanceDetail().size()>0){
                                    dtRepoMaintenanceDetail = new tMaintenanceDetailRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataMaintenanceData().getLtMaintenanceDetail().size(); i ++){
                                        tMaintenanceDetail data = new tMaintenanceDetail();
                                        data.setTxtDetailId(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getTxtMaintenanceDetailId());
                                        data.setTxtHeaderId(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getTxtMaintenanceHeaderId());
                                        data.setIntSubDetailActivityId(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getIntSubDetailActivityId());
                                        data.setTxtNoDoc(model.getData().getDataMaintenanceData().getLtMaintenanceDetail().get(i).getTxtNoDocument());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoMaintenanceDetail.createOrUpdate(data);
                                    }
                                }
                            }

                            if (model.getData().getDataInfoProgram().getLtInfoHeader()!=null){
                                if (model.getData().getDataInfoProgram().getLtInfoHeader().size()>0){
                                    dtRepoInfoProgHeader = new tInfoProgramHeaderRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataInfoProgram().getLtInfoHeader().size(); i++){
                                        tInfoProgramHeader data = new tInfoProgramHeader();
                                        data.setTxtHeaderId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getTxtInfoProgramHeaderId());
                                        data.setTxtRealisasiVisitId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntActivityId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntActivityId());
                                        data.setIntUserId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntDokterId());
                                        data.setIntApotekId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntApotekId());
                                        data.setIntAreaId(model.getData().getDataInfoProgram().getLtInfoHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Draft);
                                        dtRepoInfoProgHeader.createOrUpdate(data);
                                    }
                                }
                                dataLIstInfoProgram = (List<tInfoProgramHeader>) dtRepoInfoProgHeader.findAll();
                                tv_count_infoprogram.setText(String.valueOf(dataLIstInfoProgram.size()));
                            }

                            if (model.getData().getDataInfoProgram().getLtInfoDetail()!=null){
                                if (model.getData().getDataInfoProgram().getLtInfoDetail().size()>0){
                                    dtRepoInfoProgDetail = new tInfoProgramDetailRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataInfoProgram().getLtInfoDetail().size(); i++){
                                        tInfoProgramDetail data = new tInfoProgramDetail();
                                        data.setTxtHeaderId(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtInfoProgramHeaderId());
                                        data.setTxtDetailId(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtInfoProgramDetailId());
                                        data.setIntSubDetailActivityId(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getIntSubDetailActivityId());
                                        data.setIntFileAttachmentId(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getIntFileAttachmentId());
                                        data.setBoolFlagChecklist(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).isBitCheck());
                                        data.setDtChecklist(parseDate(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getDtDateChecklist()));
                                        dtRepoInfoProgDetail.createOrUpdate(data);
                                    }
                                }
                            }

                            if (model.getData().getDataInfoProgram().getLtInfoAttachment()!=null){
                                if (model.getData().getDataInfoProgram().getLtInfoAttachment().size()>0){
                                    dtFileRepo = new mFileAttachmentRepo(getContext());
                                    for (int i = 0; i < model.getData().getDataInfoProgram().getLtInfoAttachment().size(); i++){
                                        mFileAttachment data = new mFileAttachment();
                                        data.setIntFileAttachmentId(model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).getIntFileAttachmentId());
//                                        data.setIntSubDetailActivityId(model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).get());
//                                        byte[] file = getLogoImage(model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt());
//                                        if (file!=null){
//                                            data.setBlobFile(file);
//                                        }
                                        data.setTxtFileName(model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).getTxtfilename());
                                        data.setDescription(model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).getTxtDescription());
                                        dtFileRepo.createOrUpdate(data);
                                        if (model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt()!=null){
                                            if (model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt().length()>0){
                                                VMDownloadFile file = new VMDownloadFile();
                                                file.setLink(model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt());
                                                file.setGroupDownload(new clsHardCode().INFO_PROGRAM);
                                                file.setTxtId(String.valueOf(model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).getIntFileAttachmentId()));
                                                vmList.add(file);
                                            }
//                                            downloadFile(model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtFileLinkEncrypt(),"Info Program", model.getData().getDataInfoProgram().getLtInfoDetail().get(i).getTxtInfoProgramDetailId(), i+1);
                                        }
                                    }
                                }
                            }


//                            if (ltVMDownload.size()>0){
//                                for (VMDownloadFile f : ltVMDownload) {
//                                    downloadFile(f.getLink(),f.getGroupDownload(), f.getTxtId(), f.getIndex());
//                                }
//                            }
//                            List<tNotification> notificationList = (List<tNotification>)  new tNotificationRepo(getContext()).findOutletId();
//                            if (notificationList!=null){
//                                if (notificationList.size()>0){
//                                    createNotification(notificationList.size());
//                                }
//                            }

                            Log.d("Data info", "Success Download");
                            isFromDownloadAll = true;
                            if (vmList.size()>0){
                                isAllowNotification = true;
                                downlaodFileNew(vmList, getActivity().getApplicationContext());
                            }else {
                                List<tNotification> notificationList = null;
                                try {
                                    notificationList = (List<tNotification>)  new tNotificationRepo(getContext()).findOutletId();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                checkMenu();
                                if (notificationList!=null){
                                    if (notificationList.size()>0){
                                        createNotification(notificationList.size());
                                    }
                                }
                            }
                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadArea() {
        String strLinkAPI = new clsHardCode().linkDownloadArea;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadtMappingArea model = gson.fromJson(jsonObject.toString(), DownloadtMappingArea.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtMappingArea()!=null){
                                if (model.getData().getLtMappingArea().size()>0){
                                    dtRepoArea = new mUserMappingAreaRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtMappingArea().size(); i++){
                                        mUserMappingArea data = new mUserMappingArea();
                                        data.setIntUserMappingAreaId(model.getData().getLtMappingArea().get(i).getIntUserMappingAreaId());
                                        data.setIntUserId(model.getData().getLtMappingArea().get(i).getIntUserId());
                                        data.setTxtKecamatanID(model.getData().getLtMappingArea().get(i).getTxtKecamatanID());

                                        dtRepoArea.createOrUpdate(data);
//                                        itemList.add(String.valueOf(model.getData().getLtMappingArea().get(i).getIntUserMappingAreaId()) + " - " + model.getData().getLtMappingArea().get(i).getTxtKecamatanID());
                                    }
                                }
                                dataListArea = (List<mUserMappingArea>) dtRepoArea.findAll();
                                if (dataListArea!=null){
                                    if (dataListArea.size()>0){
                                        for (mUserMappingArea data : dataListArea){
                                            itemList.add(String.valueOf(data.getIntUserMappingAreaId()) + " - " + data.getTxtKecamatanID());
                                        }
                                    }else {
                                        itemList.add(" - ");
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_branch.setText(String.valueOf(dataListArea.size()));
                            }
                            Log.d("Data info", "Success Download");
                            checkMenu();
                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadActivity() {
        String strLinkAPI = new clsHardCode().linkmActivity;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadmActivity model = gson.fromJson(jsonObject.toString(), DownloadmActivity.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtActivity()!=null){
                                if (model.getData().getLtActivity().size()>0){
                                    dtActivityrepo = new mActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtActivity().size(); i++){
                                        mActivity data = new mActivity();
                                        data.setIntActivityId(model.getData().getLtActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getLtActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getLtActivity().get(i).getTxtDescription());
                                        dtActivityrepo.createOrUpdate(data);
//                                        itemList.add(String.valueOf(model.getData().getLtActivity().get(i).getIntActivityId()) + " - " + model.getData().getLtActivity().get(i).getTxtTitle());
                                    }
                                }
                                dataListActivity = (List<mActivity>) dtActivityrepo.findAll();
                                if (dataListActivity!=null){
                                    if (dataListActivity.size()>0){
                                        for (mActivity data : dataListActivity){
                                            itemList.add(String.valueOf(data.getIntActivityId()) + " - " + data.getTxtName());
                                        }
                                    }else {
                                        itemList.add(" - ");
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_activity.setText(String.valueOf(dataListActivity.size()));
                            }
                            Log.d("Data info", "Success Download");
                            checkMenu();
                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadSubActivity() {
        String strLinkAPI = new clsHardCode().linkSubActivity;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadSubActivity model = gson.fromJson(jsonObject.toString(), DownloadSubActivity.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtSubActivity()!=null){
                                if (model.getData().getLtSubActivity().size()>0){
                                    dtRepoSubActivity = new mSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtSubActivity().size(); i++){
                                        mSubActivity data = new mSubActivity();
                                        data.setIntSubActivityid(model.getData().getLtSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityid(model.getData().getLtSubActivity().get(i).getIntActivityId());
                                        data.setTxtName(model.getData().getLtSubActivity().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getLtSubActivity().get(i).getTxtDescription());
                                        dtRepoSubActivity.createOrUpdate(data);
//                                        mActivity activity = (mActivity) dtActivityrepo.findById(model.getData().getLtSubActivity().get(i).getIntActivityId());
//                                        itemList.add(String.valueOf(1+i) + ". " + activity.getTxtName() + " - " + model.getData().getLtSubActivity().get(i).getTxtTitle());
                                    }
                                }
                                dataListSubActivity = (List<mSubActivity>) dtRepoSubActivity.findAll();
                                if (dataListSubActivity!=null){
                                    if (dataListSubActivity.size()>0){
                                        for (mSubActivity data : dataListSubActivity){
                                            mActivity activity = (mActivity) dtActivityrepo.findById(data.getIntActivityid());
                                            itemList.add(String.valueOf(data.getIntSubActivityid()) + ". " + activity.getTxtName() + " - " + data.getTxtName());
                                        }
                                    }else {
                                        itemList.add(" - ");
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_subactivity.setText(String.valueOf(dataListSubActivity.size()));
                            }
                            Log.d("Data info", "Success Download");
                            checkMenu();
                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadSubSubActivity() {
        String strLinkAPI = new clsHardCode().linkSubSubActivity;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadSubActivityDetail model = gson.fromJson(jsonObject.toString(), DownloadSubActivityDetail.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtSubActivityDetailData()!=null){
                                if (model.getData().getLtSubActivityDetailData().size()>0){
                                    dtRepoSubSubActivity = new mSubSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtSubActivityDetailData().size(); i++){
                                        mSubSubActivity data = new mSubSubActivity();
                                        data.setIntSubActivityid(model.getData().getLtSubActivityDetailData().get(i).getIntSubActivityId());
                                        data.setIntSubSubActivityid(model.getData().getLtSubActivityDetailData().get(i).getIntSubDetailActivityId());
                                        data.setTxtName(model.getData().getLtSubActivityDetailData().get(i).getTxtTitle());
                                        data.setTxtDesc(model.getData().getLtSubActivityDetailData().get(i).getTxtDescription());
                                        data.setIntType(model.getData().getLtSubActivityDetailData().get(i).getIntFlag());
                                        dtRepoSubSubActivity.createOrUpdate(data);
                                    }
                                }
                                dataListSubSubActivity = (List<mSubSubActivity>) dtRepoSubSubActivity.findAll();
                                if (dataListSubSubActivity!=null){
                                    if (dataListSubSubActivity.size()>0){
                                        for (mSubSubActivity data : dataListSubSubActivity){
                                            mSubActivity _mSubActivity = (mSubActivity) dtRepoSubActivity.findById(data.getIntSubActivityid());
                                            mActivity _mActivity = (mActivity) dtActivityrepo.findById(_mSubActivity.getIntActivityid());
                                            itemList.add(String.valueOf(data.getIntSubSubActivityid()) + ". " + _mActivity.getTxtName() + " - " + _mSubActivity.getTxtName() + " - " + data.getTxtName());
                                        }
                                    }else {
                                        itemList.add(" - ");
                                    }
                                }
                                dataAdapter.notifyDataSetChanged();
                                tv_count_download_subsubactivity.setText(String.valueOf(dataListSubSubActivity.size()));
                            }
                            Log.d("Data info", "Success Download");
                            checkMenu();
                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadApotek(final boolean isFromDownloadAll) {
        String strLinkAPI = new clsHardCode().linkApotekAedp;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadDataget(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                new ToastCustom().showToasty(getContext(),message,4);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownlaodApotekAep model = gson.fromJson(jsonObject.toString(), DownlaodApotekAep.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();

                        if (txtStatus == true){
                            apotekRepo = new mApotekRepo(getContext());
                            if (model.getData()!=null){
                                itemList.clear();
                                if (model.getData().getRecords().size()>0){
                                    for (int i = 0; i < model.getData().getRecords().size(); i++){
                                        mApotek data = new mApotek();
                                        data.setTxtCode(model.getData().getRecords().get(i).getCode());
                                        data.setTxtName(model.getData().getRecords().get(i).getName());
                                        data.setTxtKecId(model.getData().getRecords().get(i).getKecId());
                                        data.setTxtKecName(model.getData().getRecords().get(i).getKecName());

                                        apotekRepo.createOrUpdate(data);
                                    }
                                }
                                dataListApotek = (List<mApotek>) apotekRepo.findAll();
                                if (dataListApotek!=null){
                                    if (dataListApotek.size()>0){
                                        for (mApotek data : dataListApotek){
                                            itemList.add(String.valueOf(data.getTxtCode()) + " - " + data.getTxtName());
                                        }
                                    }else {
                                        itemList.add(" - ");
                                    }
                                }
                                boolean isDataReady = new clsMainBL().isDataReady(getContext());
                                if (!isFromDownloadAll){
                                    dataAdapter.notifyDataSetChanged();
                                    checkMenu();
                                }else {
                                    downloadAllData();
                                }
//                                else if (!isDataReady||!checkMenu()){
//                                    checkMenu();
//                                }
                                tv_count_apotek.setText(String.valueOf(dataListApotek.size()));
                            }
                            Log.d("Data info", "Success Download");
                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadDokter(final boolean isFromDownloadAll) {
        String strLinkAPI = new clsHardCode().linkDokterAedp;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
//        String strLinkAPI = new clsHardCode().linkDokter;
        new clsHelperBL().volleyDownloadDataget(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            //        new clsHelperBL().volleyDownloadDataKLB(getActivity(), strLinkAPI, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                new ToastCustom().showToasty(getContext(),message,4);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadDokterAEDP model = gson.fromJson(jsonObject.toString(), DownloadDokterAEDP.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();

                        if (txtStatus == true){
                            dokterRepo = new mDokterRepo(getContext());
                            if (model.getData()!=null){
                                itemList.clear();
                                if (model.getData().getRecords().size()>0){
                                    for (int i = 0; i < model.getData().getRecords().size(); i++){
                                        mDokter data = new mDokter();
                                        data.setTxtId(model.getData().getRecords().get(i).getId());
                                        data.setTxtFirstName(model.getData().getRecords().get(i).getFirstname());
                                        data.setTxtLastName(model.getData().getRecords().get(i).getLastname());
                                        data.setTxtGender(model.getData().getRecords().get(i).getGender());
                                        data.setTxtSpecialist(model.getData().getRecords().get(i).getSpecialist());
                                        data.setTxtType(model.getData().getRecords().get(i).getType());

                                        dokterRepo.createOrUpdate(data);
                                    }
                                }

                                dataListDokter = (List<mDokter>) dokterRepo.findAll();
                                if (dataListDokter!=null){
                                    if (dataListDokter.size()>0){
                                        for (mDokter data : dataListDokter){
                                            if (data.getTxtLastName()!=null){
                                                if (!data.getTxtLastName().equals("null")){
                                                    itemList.add(String.valueOf(data.getTxtId()) + " - " + data.getTxtFirstName() + " " + data.getTxtLastName());
                                                }else {
                                                    itemList.add(String.valueOf(data.getTxtId()) + " - " + data.getTxtFirstName());
                                                }
                                            }else {
                                                itemList.add(String.valueOf(data.getTxtId()) + " - " + data.getTxtFirstName());
                                            }

                                        }
                                    }else {
                                        itemList.add(" - ");
                                    }
                                }
                                boolean isDataReady = new clsMainBL().isDataReady(getContext());
                                if (!isFromDownloadAll){
                                    dataAdapter.notifyDataSetChanged();
                                    checkMenu();
                                } else {
                                    downloadApotek(true);
                                }
//                                else if (!isDataReady||!checkMenu()){
//                                    checkMenu();
//                                }
                                tv_count_dokter.setText(String.valueOf(model.getData().getRecords().size()));

                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadtCallPlan() {
        String strLinkAPI = new clsHardCode().linkProgramVisit;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadtProgramVisit model = gson.fromJson(jsonObject.toString(), DownloadtProgramVisit.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            listId.clear();
                            vmList.clear();
                            if (model.getData().getTProgramVisit()!=null){
                                if (model.getData().getTProgramVisit().size()>0){
                                    dtRepoProgramVisit = new tProgramVisitRepo(getContext());
                                    for (int i = 0; i <model.getData().getTProgramVisit().size(); i++){
                                        tProgramVisit data = new tProgramVisit();
                                        data.setTxtProgramVisitId(model.getData().getTProgramVisit().get(i).getTxtProgramVisitId());
                                        data.setIntUserId(model.getData().getTProgramVisit().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getTProgramVisit().get(i).getIntRoleId());
                                        data.setDtStart(parseDate(model.getData().getTProgramVisit().get(i).getDtStart()));
                                        data.setDtEnd(parseDate(model.getData().getTProgramVisit().get(i).getDtEnd()));
                                        data.setIntStatus(model.getData().getTProgramVisit().get(i).getIntStatus());
                                        data.setIntType(model.getData().getTProgramVisit().get(i).getIntType());
                                        data.setTxtNotes(model.getData().getTProgramVisit().get(i).getTxtNotes());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoProgramVisit.createOrUpdate(data);
                                    }
                                }
                            }
                            if (model.getData().getTProgramVisitSubActivity()!=null){
                                if (model.getData().getTProgramVisitSubActivity().size()>0){
                                    dtRepoProgramVisitSubActivity = new tProgramVisitSubActivityRepo(getContext());
                                    for (int i = 0; i <model.getData().getTProgramVisitSubActivity().size(); i++){
                                        tProgramVisitSubActivity data = new tProgramVisitSubActivity();
                                        data.setTxtProgramVisitSubActivityId(model.getData().getTProgramVisitSubActivity().get(i).getTxtProgramVisitSubActivityId());
                                        data.setTxtProgramVisitId(model.getData().getTProgramVisitSubActivity().get(i).getTxtProgramVisitId());
                                        data.setTxtApotekId(model.getData().getTProgramVisitSubActivity().get(i).getTxtApotekId());
                                        data.setTxtApotekName(model.getData().getTProgramVisitSubActivity().get(i).getTxtApotekName());
                                        data.setIntType(model.getData().getTProgramVisitSubActivity().get(i).getIntFlag());
                                        data.setTxtAreaId(model.getData().getTProgramVisitSubActivity().get(i).getTxtAreaId());
                                        data.setTxtAreaName(model.getData().getTProgramVisitSubActivity().get(i).getTxtAreaName());
                                        data.setTxtDokterId(model.getData().getTProgramVisitSubActivity().get(i).getTxtDokterId());
                                        data.setTxtDokterName(model.getData().getTProgramVisitSubActivity().get(i).getTxtDokterName());
                                        data.setIntSubActivityId(model.getData().getTProgramVisitSubActivity().get(i).getIntSubActivityId());
                                        data.setIntActivityId(model.getData().getTProgramVisitSubActivity().get(i).getIntActivityId());
                                        data.setDtStart(parseDate(model.getData().getTProgramVisitSubActivity().get(i).getDtStart()));
                                        data.setDtEnd(parseDate(model.getData().getTProgramVisitSubActivity().get(i).getDtEnd()));
                                        data.setTxtNotes(model.getData().getTProgramVisitSubActivity().get(i).getTxtDescription());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoProgramVisitSubActivity.createOrUpdate(data);
                                    }
                                }
                            }
                            if (model.getData().getRealisasiData()!=null){
                                if (model.getData().getRealisasiData().size()>0){
                                    dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
                                    for (int i = 0; i <model.getData().getRealisasiData().size(); i++){
                                        tRealisasiVisitPlan data = new tRealisasiVisitPlan();
                                        data.setTxtProgramVisitSubActivityId(model.getData().getRealisasiData().get(i).getTxtProgramVisitSubActivityId());
                                        data.setTxtRealisasiVisitId(model.getData().getRealisasiData().get(i).getTxtRealisasiVisitId());
                                        data.setIntRoleID(model.getData().getRealisasiData().get(i).getIntRoleId());
                                        data.setTxtDokterId(model.getData().getRealisasiData().get(i).getTxtDokterId());
                                        data.setIntUserId(model.getData().getRealisasiData().get(i).getIntUserId());
                                        data.setTxtDokterName(model.getData().getRealisasiData().get(i).getTxtNamaDokter());
                                        data.setTxtApotekId(model.getData().getRealisasiData().get(i).getTxtApotekId());
                                        data.setTxtApotekName(model.getData().getRealisasiData().get(i).getTxtNamaApotek());
                                        data.setDtCheckIn(parseDate(model.getData().getRealisasiData().get(i).getDtCheckin()));
                                        data.setDtCheckOut(parseDate(model.getData().getRealisasiData().get(i).getDtChekout()));
                                        data.setDtDateRealisasi(parseDate(model.getData().getRealisasiData().get(i).getDtDateRealisasi()));
                                        data.setDtDatePlan(parseDate(model.getData().getRealisasiData().get(i).getDtDatePlan()));
                                        data.setIntNumberRealisasi(model.getData().getRealisasiData().get(i).getIntRealisasiNumber());
                                        data.setIntStatusRealisasi(model.getData().getRealisasiData().get(i).getIntRealisasiStatus());
                                        data.setTxtAcc(model.getData().getRealisasiData().get(i).getTxtAccurasi());
                                        data.setTxtLong(model.getData().getRealisasiData().get(i).getTxtLongitude());
                                        data.setTxtLat(model.getData().getRealisasiData().get(i).getTxtLatitude());
                                        data.setTxtImgName1(model.getData().getRealisasiData().get(i).getTxtImage1Name());
                                        data.setTxtImgName2(model.getData().getRealisasiData().get(i).getTxtImage2Name());
                                        VMDownloadFile vmData = new VMDownloadFile();
                                        vmData.setGroupDownload(new clsHardCode().REALISASI_DUA);
                                        vmData.setLink(model.getData().getRealisasiData().get(i).getTxtImage2Link());
                                        vmData.setTxtId(model.getData().getRealisasiData().get(i).getTxtRealisasiVisitId());
                                        vmList.add(vmData);

                                        VMDownloadFile vmData1 = new VMDownloadFile();
                                        vmData1.setGroupDownload(new clsHardCode().REALISASI_SATU);
                                        vmData1.setLink(model.getData().getRealisasiData().get(i).getTxtImage1Link());
                                        vmData1.setTxtId(model.getData().getRealisasiData().get(i).getTxtRealisasiVisitId());
                                        vmList.add(vmData1);
                                        dtRepoRealisasi.createOrUpdate(data);
                                    }
                                }
                                dataListProgramVisitSubActivity = (List<tProgramVisitSubActivity>) dtRepoProgramVisitSubActivity.findAll();
                                int index = 0;
                                if (dataListProgramVisitSubActivity.size()>0){
                                    for (tProgramVisitSubActivity data : dataListProgramVisitSubActivity){
                                        index++;
                                        mActivity activity = (mActivity) dtActivityrepo.findById(data.getIntActivityId());
                                        if (data.getIntActivityId()==new clsHardCode().VisitDokter){
                                            itemList.add(String.valueOf(index)+ " - " + activity.getTxtName() + ", " + data.getTxtDokterName());
                                        }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
                                            itemList.add(String.valueOf(index)+ " - " + activity.getTxtName() + ", " + data.getTxtApotekName());
                                        }else {
                                            itemList.add(String.valueOf(index)+ " - " + activity.getTxtName());
                                        }

                                    }
                                }else {
                                    itemList.add(" - ");
                                }
                                tv_count_realisasi.setText(String.valueOf(dataListProgramVisitSubActivity.size()));
                                dataAdapter.notifyDataSetChanged();
                            }
                            Log.d("Data info", "Success Download");
                            isFromDownloadAll = false;
                            isAllowNotification = false;
                            downlaodFileNew(vmList, getActivity().getApplicationContext());

                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadtAkuisisi() {
        String strLinkAPI = new clsHardCode().linkAkuisisi;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadtAkuisisi model = gson.fromJson(jsonObject.toString(), DownloadtAkuisisi.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            listId.clear();
                            vmList.clear();
                            if (model.getData().getAkuisisiHeader()!=null){
                                if (model.getData().getAkuisisiHeader().size()>0){
                                    dtRepoAkuisisiHeader = new tAkuisisiHeaderRepo(getContext());
                                    dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
                                    for (int i = 0; i <model.getData().getAkuisisiHeader().size(); i++){
                                        tAkuisisiHeader data = new tAkuisisiHeader();
                                        data.setTxtHeaderId(model.getData().getAkuisisiHeader().get(i).getTxtAkuisisiHeaderId());
                                        data.setIntSubSubActivityId(model.getData().getAkuisisiHeader().get(i).getIntSubDetailActivityId());
                                        data.setIntSubSubActivityTypeId(model.getData().getAkuisisiHeader().get(i).getIntFlag());
                                        data.setTxtNoDoc(model.getData().getAkuisisiHeader().get(i).getTxtNoDoc());
                                        data.setDtExpiredDate(model.getData().getAkuisisiHeader().get(i).getDtExpiredDate());
                                        data.setIntUserId(model.getData().getAkuisisiHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getAkuisisiHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getAkuisisiHeader().get(i).getTxtDokterId());
                                        data.setIntApotekID(model.getData().getAkuisisiHeader().get(i).getTxtApotekId());
                                        data.setIntAreaId(model.getData().getAkuisisiHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        data.setTxtRealisasiVisitId(model.getData().getAkuisisiHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntFlagShow(new clsHardCode().Save);
                                        data.setTxtUserName(model.getData().getAkuisisiHeader().get(i).getTxtUserName());
                                        dtRepoAkuisisiHeader.createOrUpdate(data);
                                    }

                                    dataListAkuisisi = (List<tAkuisisiHeader>) dtRepoAkuisisiHeader.findAll();
                                    if (dataListAkuisisi!=null){
                                        if (dataListAkuisisi.size()>0){
                                            int index = 0;
                                            for (tAkuisisiHeader data : dataListAkuisisi){
                                                index++;
                                                String name = null;
                                                try {
                                                    if (data.getIntDokterId()!=null){
                                                        if (!data.getIntDokterId().equals("null")){
//                                                            name = "Dokter " + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName();
                                                            name = "Dokter " + dtRepoRealisasi.findBytxtDokterId(data.getIntDokterId()).getTxtDokterName();
                                                        }else if (data.getIntApotekID()!=null){
                                                            if (!data.getIntApotekID().equals("null")){
                                                                name = dtRepoRealisasi.findBytxtApotekId(data.getIntApotekID()).getTxtApotekName();
//                                                                name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                                            }
                                                        }
                                                    }else if (data.getIntApotekID()!=null){
                                                        if (!data.getIntApotekID().equals("null")){
                                                            name = dtRepoRealisasi.findBytxtApotekId(data.getIntApotekID()).getTxtApotekName();
                                                        }
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                itemList.add(String.valueOf(index) + " - Akuisisi - " + name);
                                            }
                                        }else {
                                            itemList.add(" - ");
                                        }
                                    }
                                    tv_count_akuisisi.setText(String.valueOf(dataListAkuisisi.size()));
                                }
                                dataAdapter.notifyDataSetChanged();

                                if (model.getData().getAkuisisiDetail()!=null){
                                    if (model.getData().getAkuisisiDetail().size()>0){
                                        dtRepoAkuisisiDetail = new tAkuisisiDetailRepo(getContext());
                                        for (int i = 0; i < model.getData().getAkuisisiDetail().size(); i++){
                                            tAkuisisiDetail data = new tAkuisisiDetail();
                                            data.setTxtHeaderId(model.getData().getAkuisisiDetail().get(i).getTxtAkuisisiHeaderId());
                                            data.setTxtDetailId(model.getData().getAkuisisiDetail().get(i).getTxtAkuisisiDetailId());
                                            data.setTxtImgName(model.getData().getAkuisisiDetail().get(i).getTxtImageName());
//                                            if (getLogoImage(model.getData().getAkuisisiDetail().get(i).getTxtImagePath())!=null){
//                                                data.setTxtImg(getLogoImage(model.getData().getAkuisisiDetail().get(i).getTxtImagePath()));
//                                            }
                                            if (model.getData().getAkuisisiDetail().get(i).getTxtLink()!=null &&model.getData().getAkuisisiDetail().get(i).getTxtLink().length()>0){
                                                VMDownloadFile vmData = new VMDownloadFile();
                                                vmData.setGroupDownload(new clsHardCode().AKUISISI);
                                                vmData.setLink(model.getData().getAkuisisiDetail().get(i).getTxtLink());
                                                vmData.setTxtId(model.getData().getAkuisisiDetail().get(i).getTxtAkuisisiDetailId());
                                                vmList.add(vmData);
//                                                downloadFile(model.getData().getAkuisisiDetail().get(i).getTxtLink(),"Akuisisi", model.getData().getAkuisisiDetail().get(i).getTxtAkuisisiDetailId(), i+1);

                                            }
//                                            byte[] file = getLogoImage((model.getData().getAkuisisiDetail().get(i).getTxtLink()));
//                                            if (file!=null){
//                                                data.setTxtImg(file);
//                                            }
                                            dtRepoAkuisisiDetail.createOrUpdate(data);
                                        }
                                    }
                                }

                                if (model.getNotificationData()!=null){
                                    if (model.getNotificationData().size()>0){
                                        dtRepoNotification = new tNotificationRepo(getContext());
                                        for (int i = 0; i < model.getNotificationData().size(); i++){
                                            tNotification data = new tNotification();
                                            data.setIntHeaderAkuisisiId(model.getNotificationData().get(i).getTxtAkuisisiHeaderId());
                                            data.setIntActivityId(model.getNotificationData().get(i).getIntActivityId());
                                            data.setIntDokterId(model.getNotificationData().get(i).getTxtDokterId());
                                            data.setIntApotekId(model.getNotificationData().get(i).getTxtApotekId());
                                            data.setDtExpired(parseDate(model.getNotificationData().get(i).getDtExpiredDate()));
                                            data.setIntSubDetailActivityId(model.getNotificationData().get(i).getIntSubDetailActivityId());
                                            data.setTxtNoDoc(model.getNotificationData().get(i).getTxtNoDoc());
                                            dtRepoNotification.createOrUpdate(data);
                                        }
//                                        List<tNotification> notificationList = (List<tNotification>)  new tNotificationRepo(getContext()).findOutletId();
//                                        createNotification(notificationList.size());
                                    }
                                }
                            }
                            Log.d("Data info", "Success Download");
                            isFromDownloadAll = false;
                            isAllowNotification = true;
                            downlaodFileNew(vmList, getActivity().getApplicationContext());
                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void downloadtMaintenace() {
        String strLinkAPI = new clsHardCode().linkMaintenance;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadtMaintenance model = gson.fromJson(jsonObject.toString(), DownloadtMaintenance.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            if (model.getData().getLtMaintenanceHeader()!=null){
                                if (model.getData().getLtMaintenanceHeader().size()>0){
                                    dtRepoMaintenanceHeader = new tMaintenanceHeaderRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtMaintenanceHeader().size(); i++){
                                        tMaintenanceHeader data = new tMaintenanceHeader();
                                        data.setTxtHeaderId(model.getData().getLtMaintenanceHeader().get(i).getTxtMaintenanceHeaderId());
                                        data.setTxtRealisasiVisitId(model.getData().getLtMaintenanceHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntActivityId(model.getData().getLtMaintenanceHeader().get(i).getIntActivityId());
                                        data.setIntUserId(model.getData().getLtMaintenanceHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getLtMaintenanceHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getLtMaintenanceHeader().get(i).getIntDokterId());
                                        data.setIntApotekID(model.getData().getLtMaintenanceHeader().get(i).getIntApotekId());
                                        data.setIntAreaId(model.getData().getLtMaintenanceHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Sync);
                                        dtRepoMaintenanceHeader.createOrUpdate(data);
//                                        String name ="";
//                                        if (model.getData().getLtMaintenanceHeader().get(i).getIntActivityId()==new clsHardCode().VisitDokter){
//                                            name = "Dokter " + dokterRepo.findBytxtId(model.getData().getLtMaintenanceHeader().get(i).getIntDokterId()).getTxtFirstName() + " " +dokterRepo.findBytxtId(model.getData().getLtMaintenanceHeader().get(i).getIntDokterId()).getTxtLastName();
//                                        }else {
//                                            name = apotekRepo.findBytxtId(model.getData().getLtMaintenanceHeader().get(i).getIntApotekId()).getTxtName();
//                                        }
//                                        itemList.add(String.valueOf(index) + " - Maintenace - " + name);
                                    }
                                    dataListMaintenance = (List<tMaintenanceHeader>) dtRepoMaintenanceHeader.findAll();
                                    dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
                                    if (dataListMaintenance!=null){
                                        if (dataListMaintenance.size()>0){
                                            int index = 0;
                                            for (tMaintenanceHeader data : dataListMaintenance){
                                                index++;
                                                String name = null;
                                                try {
                                                    if (data.getIntActivityId()==new clsHardCode().VisitDokter){
//                                                        name = "Dokter " + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName();
                                                        name = "Dokter " + dtRepoRealisasi.findBytxtDokterId(data.getIntDokterId()).getTxtDokterName();
                                                    }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
//                                                        name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                                        name = dtRepoRealisasi.findBytxtApotekId(data.getIntApotekID()).getTxtApotekName();
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                itemList.add(String.valueOf(index) + " - Maintenance - " + name);
                                            }
                                        }else {
                                            itemList.add(" - ");
                                        }
                                    }
                                    tv_count_maintenance.setText(String.valueOf(dataListMaintenance.size()));
                                }
                                dataAdapter.notifyDataSetChanged();

                                if (model.getData().getLtMaintenanceDetail()!=null){
                                    if (model.getData().getLtMaintenanceDetail().size()>0){
                                        dtRepoMaintenanceDetail = new tMaintenanceDetailRepo(getContext());
                                        for (int i = 0; i < model.getData().getLtMaintenanceDetail().size(); i++){
                                            tMaintenanceDetail data = new tMaintenanceDetail();
                                            data.setTxtHeaderId(model.getData().getLtMaintenanceDetail().get(i).getTxtMaintenanceHeaderId());
                                            data.setTxtDetailId(model.getData().getLtMaintenanceDetail().get(i).getTxtMaintenanceDetailId());
                                            data.setIntSubDetailActivityId(model.getData().getLtMaintenanceDetail().get(i).getIntSubDetailActivityId());
                                            data.setTxtNoDoc(model.getData().getLtMaintenanceDetail().get(i).getTxtNoDocument());
                                            data.setIntFlagPush(new clsHardCode().Sync);
                                            dtRepoMaintenanceDetail.createOrUpdate(data);
                                        }
                                    }
                                }
                            }
                            Log.d("Data info", "Success Download");

                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void downloadtInfoProgram() {
        String strLinkAPI = new clsHardCode().linkInfoProgram;
        JSONObject resJson = new JSONObject();
        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            resJson.put("data", ParamDownloadMaster());
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
        new clsHelperBL().volleyDownloadData(getActivity(), strLinkAPI, mRequestBody, "Please Wait....", new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        DownloadtInfoProgram model = gson.fromJson(jsonObject.toString(), DownloadtInfoProgram.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();
                        if (txtStatus == true){
                            itemList.clear();
                            listId.clear();
                            vmList.clear();
                            if (model.getData().getLtInfoHeader()!=null){
                                if (model.getData().getLtInfoHeader().size()>0){
                                    dtRepoInfoProgHeader = new tInfoProgramHeaderRepo(getContext());
                                    for (int i = 0; i <model.getData().getLtInfoHeader().size(); i++){
                                        tInfoProgramHeader data = new tInfoProgramHeader();
                                        data.setTxtHeaderId(model.getData().getLtInfoHeader().get(i).getTxtInfoProgramHeaderId());
                                        data.setTxtRealisasiVisitId(model.getData().getLtInfoHeader().get(i).getTxtRealisasiVisitId());
                                        data.setIntActivityId(model.getData().getLtInfoHeader().get(i).getIntActivityId());
                                        data.setIntUserId(model.getData().getLtInfoHeader().get(i).getIntUserId());
                                        data.setIntRoleId(model.getData().getLtInfoHeader().get(i).getIntRoleId());
                                        data.setIntDokterId(model.getData().getLtInfoHeader().get(i).getIntDokterId());
                                        data.setIntApotekId(model.getData().getLtInfoHeader().get(i).getIntApotekId());
                                        data.setIntAreaId(model.getData().getLtInfoHeader().get(i).getIntAreaId());
                                        data.setIntFlagPush(new clsHardCode().Draft);
                                        dtRepoInfoProgHeader.createOrUpdate(data);
                                    }
                                    dataLIstInfoProgram = (List<tInfoProgramHeader>) dtRepoInfoProgHeader.findAll();
                                    dtRepoRealisasi = new tRealisasiVisitPlanRepo(getContext());
                                    if (dataLIstInfoProgram!=null){
                                        if (dataLIstInfoProgram.size()>0){
                                            int index = 0;
                                            for (tInfoProgramHeader data : dataLIstInfoProgram){
                                                index++;
                                                String name = null;
                                                try {
                                                    if (data.getIntActivityId()==new clsHardCode().VisitDokter){
//                                                        name = "Dokter " + dokterRepo.findBytxtId(data.getIntDokterId()).getTxtFirstName();
                                                        name = "Dokter " + dtRepoRealisasi.findBytxtDokterId(data.getIntDokterId()).getTxtDokterName();
                                                    }else if (data.getIntActivityId()==new clsHardCode().VisitApotek){
//                                                        name = apotekRepo.findBytxtId(data.getIntApotekID()).getTxtName();
                                                        name = dtRepoRealisasi.findBytxtApotekId(data.getIntApotekId()).getTxtApotekName();
                                                    }
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                itemList.add(String.valueOf(index) + " - Info Program - " + name);
                                            }
                                        }else {
                                            itemList.add(" - ");
                                        }
                                    }
                                    tv_count_infoprogram.setText(String.valueOf(dataLIstInfoProgram.size()));
                                }
                                dataAdapter.notifyDataSetChanged();

                                dtRepoInfoProgDetail = new tInfoProgramDetailRepo(getContext());
                                if (model.getData().getLtInfoDetail()!=null){
                                    if (model.getData().getLtInfoDetail().size()>0){
                                        for (int i = 0; i < model.getData().getLtInfoDetail().size(); i++){
                                            tInfoProgramDetail detail = dtRepoInfoProgDetail.findByDetailId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramDetailId());
                                            if (detail != null) {
                                                if (detail.isBoolFlagChecklist()==false){
                                                    tInfoProgramDetail data = new tInfoProgramDetail();
                                                    data.setTxtHeaderId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramHeaderId());
                                                    data.setTxtDetailId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramDetailId());
                                                    data.setIntSubDetailActivityId(model.getData().getLtInfoDetail().get(i).getIntSubDetailActivityId());
                                                    data.setBoolFlagChecklist(model.getData().getLtInfoDetail().get(i).isBitCheck());
                                                    data.setDtChecklist(parseDate(model.getData().getLtInfoDetail().get(i).getDtDateChecklist()));
                                                    data.setIntFileAttachmentId(model.getData().getLtInfoDetail().get(i).getIntFileAttachmentId());
                                                    dtRepoInfoProgDetail.createOrUpdate(data);
                                                }
                                            }else {
                                                tInfoProgramDetail data = new tInfoProgramDetail();
                                                data.setTxtHeaderId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramHeaderId());
                                                data.setTxtDetailId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramDetailId());
                                                data.setIntSubDetailActivityId(model.getData().getLtInfoDetail().get(i).getIntSubDetailActivityId());
                                                data.setBoolFlagChecklist(model.getData().getLtInfoDetail().get(i).isBitCheck());
                                                data.setDtChecklist(parseDate(model.getData().getLtInfoDetail().get(i).getDtDateChecklist()));
                                                data.setIntFileAttachmentId(model.getData().getLtInfoDetail().get(i).getIntFileAttachmentId());
                                                dtRepoInfoProgDetail.createOrUpdate(data);
                                            }


//                                                    if (programDetailList.get(j).getTxtDetailId()!=model.getData().getLtInfoDetail().get(i).getTxtInfoProgramDetailId()){
//
//                                                    }
                                        }
//                                        List<tInfoProgramDetail> programDetailList = (List<tInfoProgramDetail>) dtRepoInfoProgDetail.findByisChecked();
//                                        if (programDetailList.size()>0){
//
//                                        }else {
//                                            for (int i = 0; i < model.getData().getLtInfoDetail().size(); i++){
//                                                tInfoProgramDetail data = new tInfoProgramDetail();
//                                                data.setTxtHeaderId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramHeaderId());
//                                                data.setTxtDetailId(model.getData().getLtInfoDetail().get(i).getTxtInfoProgramDetailId());
//                                                data.setIntSubDetailActivityId(model.getData().getLtInfoDetail().get(i).getIntSubDetailActivityId());
//                                                data.setBoolFlagChecklist(model.getData().getLtInfoDetail().get(i).isBitCheck());
//                                                data.setDtChecklist(parseDate(model.getData().getLtInfoDetail().get(i).getDtDateChecklist()));
//                                                data.setIntFileAttachmentId(model.getData().getLtInfoDetail().get(i).getIntFileAttachmentId());
//                                                dtRepoInfoProgDetail.createOrUpdate(data);
//                                            }
//                                        }
                                    }
                                }

                                if (model.getData().getLtInfoAttachment()!=null){
                                    if (model.getData().getLtInfoAttachment().size()>0){
                                        dtFileRepo = new mFileAttachmentRepo(getContext());
//                                        int intCountFile = model.getData().getLtInfoAttachment().size();
                                        for (int i = 0; i < model.getData().getLtInfoAttachment().size(); i++){
                                            mFileAttachment data = new mFileAttachment();
                                            data.setIntFileAttachmentId(model.getData().getLtInfoAttachment().get(i).getIntFileAttachmentId());
//                                        data.setIntSubDetailActivityId(model.getData().getDataInfoProgram().getLtInfoAttachment().get(i).get());
                                            data.setTxtFileName(model.getData().getLtInfoAttachment().get(i).getTxtfilename());
                                            data.setDescription(model.getData().getLtInfoAttachment().get(i).getTxtDescription());
                                            dtFileRepo.createOrUpdate(data);
                                            if (model.getData().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt()!=null &&model.getData().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt().length()>0){
//                                            downloadFile(model.getData().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt(),"Info Program", String.valueOf(model.getData().getLtInfoAttachment().get(i).getIntFileAttachmentId()), i+1);
//                                               byte[] file = getLogoImage((model.getData().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt()));
//                                               if (file!=null){
//                                                   data.setBlobFile(file);
//                                               }
                                                VMDownloadFile vmData = new VMDownloadFile();
                                                vmData.setGroupDownload(new clsHardCode().INFO_PROGRAM);
                                                vmData.setLink(model.getData().getLtInfoAttachment().get(i).getTxtFileLinkEncrypt());
                                                vmData.setTxtId(String.valueOf(model.getData().getLtInfoAttachment().get(i).getIntFileAttachmentId()));
                                                vmList.add(vmData);
                                            }
//                                            intCountFile =-1;
                                            dtFileRepo.createOrUpdate(data);
                                        }
//                                        if (intCountFile==0){
//                                            ToastCustom.showToasty(getContext(),"selesai",4);
//                                        }
                                    }
                                }

                            }
                            Log.d("Data info", "Success Download");
                            isFromDownloadAll = false;
                            isAllowNotification = false;
                            downlaodFileNew(vmList, getActivity().getApplicationContext());

                        } else {
                            new ToastCustom().showToasty(getContext(),txtMessage,4);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String parseDate(String dateParse){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date date = null;
        try {
            if (dateParse!=null&& dateParse!="")
                date = sdf.parse(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return null;
        }
    }

    private byte[] getLogoImage(String url) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            String contentType = ucon.getHeaderField("Content-Type");
            boolean image = contentType.startsWith("image/");
            boolean text = contentType.startsWith("application/");

//            if (image||text) {
//                InputStream is = ucon.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is);
//                int length =  ucon.getContentLength();
//                ByteArrayBuffer baf = new ByteArrayBuffer(length);
//                int current;
//                while ((current = bis.read()) != -1) {
//                    baf.append((byte) current);
//                }
//
//                return baf.toByteArray();
//            } else
            if (image||text){
                byte[] data = null;
                InputStream is = ucon.getInputStream();
                int length =  ucon.getContentLength();
                data = new byte[length];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = is.read(data)) != -1) {
                    output.write(data, 0, bytesRead);
                }
                return output.toByteArray();
            }
            else {
                return null;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }

    private void downlaodFileNew(List<VMDownloadFile> listVm, Context context){
        curCount = 0;
        totalCount = listVm.size();
        if (listVm.size()>0){
            progress=new ProgressDialog(getContext());
            progress.setMessage("Downloading file....");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setMax(100);
            progress.setProgress(0);
            progress.show();
            int index = 0;
            for (VMDownloadFile data : listVm){
                executor.execute(new LongThread(context, index, data, new Handler(this)));
                index++;
            }
        }
    }
    private boolean checkMenu(){
        boolean isDataReady = new clsMainBL().isDataReady(getContext());
        if (isDataReady){
            new ToastCustom().showToasty(getContext(),"Success Download",1);
            Intent myIntent = new Intent(getContext(), MainMenu.class);
            startActivity(myIntent);
            getActivity().finish();
            return true;
        }
        return false;
    }


    private void downloadFile(String link, String groupDownload, String txtId, int index){
        DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(link));
        String file = (link.substring(link.lastIndexOf("/")));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading " + groupDownload + String.valueOf(index));
        request.setDescription(txtId);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Download/"  + file.substring(1, file.length()));

        long enqueue = dm.enqueue(request);
        listId.add(enqueue);

    }

//    public BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
//                long downloadId = intent.getLongExtra(
//                        DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
//                DownloadManager.Query query = new DownloadManager.Query();
//                query.setFilterById(downloadId);
//                if (downloadId!=-1){
//                    Cursor c = dm.query(query);
//                    if (c.moveToFirst()) {
//                        int columnIndex = c
//                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
//                        if (DownloadManager.STATUS_SUCCESSFUL == c
//                                .getInt(columnIndex)) {
//
//                            String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//                            String title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
//                            String txtId = c.getString(c.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
//                            try {
//                                byte[] file = PickFile.getByteArrayFileToSave(Uri.parse(uriString), context);
//                                if (title.contains("Info Program")){
//                                    mFileAttachment data = (mFileAttachment) new mFileAttachmentRepo(context).findById(Integer.parseInt(txtId));
//                                    data.setBlobFile(file);
//                                    new mFileAttachmentRepo(context).createOrUpdate(data);
//                                }else if (title.contains("Akuisisi")){
//                                    tAkuisisiDetail data = new tAkuisisiDetailRepo(context).findByDetailId(txtId);
//                                    data.setTxtImg(file);
//                                    new tAkuisisiDetailRepo(context).createOrUpdate(data);
//                                }else if (title.contains("Realisasi Pertama")){
//                                    tRealisasiVisitPlan data = new tRealisasiVisitPlanRepo(context).findBytxtId(txtId);
//                                    data.setBlobImg1(file);
//                                    new tRealisasiVisitPlanRepo(context).createOrUpdate(data);
//                                }else if (title.contains("Realisasi Kedua")){
//                                    tRealisasiVisitPlan data = new tRealisasiVisitPlanRepo(context).findBytxtId(txtId);
//                                    data.setBlobImg2(file);
//                                    new tRealisasiVisitPlanRepo(context).createOrUpdate(data);
//                                }
//                                if (id==downloadId){
////                                  editor = preferences.edit();
//                                    editor.remove(txtId);
//                                    editor.commit();
//                                }
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//
//                            if (preferences.getAll().size()==0){
//                                createNotificationDonwloadComplete(context);
//                            }
////                            if (listId!=null){
////                                listId.remove(downloadId);
////                                if (listId.isEmpty()){
////                                    createNotificationDonwloadComplete(context);
////                                }
////                            }
//
//                        }
//                    }
//                }
//
//            }
//        }
//    };


    public void createNotification(int size){
        Intent i = new Intent(getContext(), MainMenu.class);
        i.putExtra(i_View, "FragmentNotification");
//        int icon = R.drawable.ic_notif;
//        String tickerText = "hello";
//        long when = System.currentTimeMillis();
//        Notification tnotification = new Notification.Builder(getActivity())
//                .setContentIntent(pendingIntent)
//                .setContentTitle("haii")
//                .setContentText("tes")
//                .setSmallIcon(icon)
//                .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(),
//                        R.mipmap.ic_launcher))
//                .setWhen(when)
//                .setTicker(tickerText)
//                .setPriority(Notification.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .setNumber(1)
//                .setDefaults(Notification.DEFAULT_ALL | Notification.FLAG_SHOW_LIGHTS | Notification.PRIORITY_DEFAULT)
//                .build();
//        NotificationManager tnotificationManager=(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//        tnotification.flags |= Notification.FLAG_AUTO_CANCEL;
//        tnotification.defaults=Notification.DEFAULT_ALL;
//        tnotificationManager.notify(1993,tnotification);
//        try {
//            ShortcutBadger.applyCountOrThrow(getActivity(), size);
//        } catch (ShortcutBadgeException e) {
//            e.printStackTrace();
//        }

        try {
            me.leolin.shortcutbadger.ShortcutBadger.applyCountOrThrow(getActivity(), size);
        } catch (me.leolin.shortcutbadger.ShortcutBadgeException e) {
            e.printStackTrace();
        }


        String CHANNEL_ID = "kalbenutritionals_channel";
        CharSequence name = "kalbenutritionals_channel";
        String Description = "kalbenutritionals channel";

        int NOTIFICATION_ID = 234;

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(MainMenu.class);
        stackBuilder.addNextIntent(i);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setContentTitle("Document Expired")
//                .setContentText("Please check on notifications menu")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("There are some documents will expire!"))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .setNumber(size)
                .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(),
                        R.mipmap.ic_launcher))
                .setColor(getContext().getResources().getColor(R.color.green_300));
//                .setColor(getResources().getColor(android.R.color.holo_red_dark));
//                .addAction(R.drawable.ic_launcher_foreground, "Call", resultPendingIntent)
//                .addAction(R.drawable.ic_launcher_foreground, "More", resultPendingIntent)
//                .addAction(R.drawable.ic_launcher_foreground, "And more", resultPendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(mChannel);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

        }else {
            Notification note = builder.build();
            note.defaults |= Notification.DEFAULT_VIBRATE;
            note.defaults |= Notification.DEFAULT_SOUND;
            if (notificationManager != null) {

                notificationManager.notify(NOTIFICATION_ID, note);
            }
        }

    }

    public void createNotificationDonwloadComplete(Context context){
        String CHANNEL_ID = "kalbenutritionals_channel";
        CharSequence name = "kalbenutritionals_channel";
        String Description = "kalbenutritionals channel";

        int NOTIFICATION_ID = 1992;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Download Completed")
//                .setContentText("Please check on notifications menu")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("All file have been downloaded!"))
                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setColor(context.getResources().getColor(R.color.green_300));
//                .setColor(context.getResources().getColor(android.R.color.holo_red_dark));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(mChannel);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

        }else {
            Notification note = builder.build();
            note.defaults |= Notification.DEFAULT_VIBRATE;
            note.defaults |= Notification.DEFAULT_SOUND;
            if (notificationManager != null) {

                notificationManager.notify(NOTIFICATION_ID, note);
            }
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        curCount++;
        float per = (curCount / totalCount) * 100;
        progress.setIndeterminate(false);
        progress.setMax(100);
        progress.setProgress((int) per);
//        Toast.makeText(getContext(), "hahaha", Toast.LENGTH_SHORT).show();
        if (curCount == (int)totalCount){
            progress.dismiss();

            List<tNotification> notificationList = null;
            try {
                notificationList = (List<tNotification>)  new tNotificationRepo(getContext()).findOutletId();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (isFromDownloadAll){
                checkMenu();
            }

            if (notificationList!=null){
                if (notificationList.size()>0 && isAllowNotification){
                    createNotification(notificationList.size());
                }
            }
        }


//        if (per < 100)
//            tvStatus.setText("Downloaded [" + curCount + "/" + (int)totalCount + "]");
//        else
//            tvStatus.setText("All images downloaded.");
        return true;
    }
}
