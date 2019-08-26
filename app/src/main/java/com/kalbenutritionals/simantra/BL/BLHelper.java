package com.kalbenutritionals.simantra.BL;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.pdf.PdfDocument;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.facebook.shimmer.ShimmerFrameLayout;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.kalbe.mobiledevknlibs.DeviceInformation.DeviceInformation;
import com.kalbe.mobiledevknlibs.DeviceInformation.ModelDevice;
import com.kalbenutritionals.simantra.CustomView.Adapter.AdapterListBasic;
import com.kalbenutritionals.simantra.CustomView.Adapter.LineItemDecoration;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.DataIssue;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.DataItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.DataVehichleFix;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.DataVehichleFixImage;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.DataVehichleIssue;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ListDatFormIssueItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ListDatImageItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ListDatIsianItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ListDatVehichleIssueImageItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ResponseGetQuestion;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.TimeDataItem;
import com.kalbenutritionals.simantra.Database.Common.ClsDataError;
import com.kalbenutritionals.simantra.Database.Common.ClsDataJson;
import com.kalbenutritionals.simantra.Database.Common.ClsImages;
import com.kalbenutritionals.simantra.Database.Common.ClsOrganisation;
import com.kalbenutritionals.simantra.Database.Common.ClsPushData;
import com.kalbenutritionals.simantra.Database.Common.ClsTConfigHelper;
import com.kalbenutritionals.simantra.Database.Common.ClsTDataRejection;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmCounterData;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;
import com.kalbenutritionals.simantra.Database.EnumCounterData;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsImages;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsOrganisation;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsTConfigHelper;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsTDataRejection;
import com.kalbenutritionals.simantra.Database.Repo.RepomConfig;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepotLogError;
import com.kalbenutritionals.simantra.Database.Repo.RepomCounterData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.Network.FastNetworking.InterfaceFastNetworking;
import com.kalbenutritionals.simantra.R;
import com.kalbenutritionals.simantra.ViewModel.DeviceInfo;
import com.kalbenutritionals.simantra.ViewModel.UserRequest;
import com.kalbenutritionals.simantra.ViewModel.VMRequestData;
import com.kalbenutritionals.simantra.ViewModel.VMRequestDataSPM;
import com.kalbenutritionals.simantra.ViewModel.VMTransaksiChecker;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasic;
import com.kalbenutritionals.simantra.ViewModel.VmAdapterBasicIssue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

public class BLHelper {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;
    String access_token, clientId = "";
    List<ClsToken> dataToken;
    ClsHardCode hardCode = new ClsHardCode();


    public JSONObject getUserLoginDataJson(Context context) {
        UserRequest userRequest = new UserRequest();
        userRequest = getUserInfo(context);
        JSONObject obj = null;
        if (userRequest != null) {
            Gson gson = new Gson();
            String json = gson.toJson(userRequest);
            try {
                obj = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static void savePreference(Context context, String key, String value) {
        editor = context.getSharedPreferences(ClsHardCode.TXT_SHARED_PREF_KEY, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreference(Context context, String key) {
        String result = "";
        mSharedPreferences = context.getSharedPreferences(ClsHardCode.TXT_SHARED_PREF_KEY,
                Context.MODE_PRIVATE);
        result = mSharedPreferences.getString(key, "");
        return result;
    }
    public static void clearDataSharePreference(Context context){
        mSharedPreferences = context.getSharedPreferences(ClsHardCode.TXT_SHARED_PREF_KEY,
                Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        editor.remove(ClsHardCode.SP_SCAN_TIME);
        editor.remove(ClsHardCode.SP_CHECKING_FINISH);
        editor.remove(ClsHardCode.SP_STARTTIME_CHECKER);
        editor.remove(ClsHardCode.SP_FINISHTIME_CHECKER);
        editor.remove(ClsHardCode.SP_SCANTIME_UNLOADING);
        editor.remove(ClsHardCode.SP_STARTTIME_UNLOADING);
        editor.remove(ClsHardCode.SP_FINISHTIME_UNLOADING);
        editor.commit();
    }

    public void setupFormats(ArrayList<Integer> mSelectedIndices, ZXingScannerView mScannerView) {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if (mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for (int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for (int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }
        if (mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    public String getGreetings(String name) {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String greetings = "";
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetings = "Good Morning ";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greetings = "Good Afternoon ";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greetings = "Good Evening ";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            greetings = "Good Night ";
        }
        return greetings;
    }

    /**
     * Used to scroll to the given view.
     *
     * @param scrollViewParent Parent ScrollView
     * @param view             View to which we need to scroll.
     */
    public void scrollToView(final NestedScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    /**
     * Used to get deep child offset.
     * <p/>
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumulated Offset.
     */
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    public DeviceInfo getDeviceInfo() {
        DeviceInfo data = new DeviceInfo();
        try {
            ModelDevice model = DeviceInformation.getDeviceInformation();
            data.setDevice(model.getDevice());
            data.setModel(model.getModel());
            data.setOs_version(model.getOsVersion());
            data.setProduct(model.getProduct());
            data.setVersion_sdk(model.getVersionSDK());
        } catch (Exception ex) {

        }
        return data;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public JSONObject getDataRequestCommon(Context context) {
        VMRequestData data = new VMRequestData();
        DeviceInfo dataDevice = new BLHelper().getDeviceInfo();
        UserRequest userData = new BLHelper().getUserInfo(context);
        data.setData(userData);
        data.setDevice_info(dataDevice);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONObject getDataRequestDataSPM(Context context, int type, String SPMNumber, int intIsValidator, int intFillHdrId) {
        VMRequestDataSPM data = new VMRequestDataSPM();
        DeviceInfo dataDevice = new BLHelper().getDeviceInfo();
        VMRequestDataSPM.UserRequestSPM userData = new BLHelper().getUserInfoSPM(context, type, SPMNumber, intIsValidator, intFillHdrId);
        data.setData(userData);
        data.setDevice_info(dataDevice);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public JSONObject getDataRequestDataSPM_OLD(Context context, int type, String SPMNumber, int intIsValidator, int intFillHdrId) {
        VMRequestDataSPM data = new VMRequestDataSPM();
        DeviceInfo dataDevice = new BLHelper().getDeviceInfo();
        VMRequestDataSPM.UserRequestSPM userData = new BLHelper().getUserInfoSPM(context, type, SPMNumber, intIsValidator, intFillHdrId);
        data.setData(userData);
        data.setDevice_info(dataDevice);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONObject getDataTransaksiJson(Context context, VMTransaksiChecker.DatatTransaksi transaksiData) {
        VMTransaksiChecker data = new VMTransaksiChecker();
        DeviceInfo dataDevice = new BLHelper().getDeviceInfo();
        data.setDatatTransaksi(transaksiData);
        data.setDeviceInfo(dataDevice);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONArray getDataTransaksiJsonArrayCommon(Context context, Object transaksiData) {
        Gson gson = new Gson();
        String json = gson.toJson(transaksiData);
        JSONArray obj = null;
        try {
            obj = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONObject getDataTransaksiJsonObjCommon(Context context, Object transaksiData) {
        Gson gson = new Gson();
        String json = gson.toJson(transaksiData);
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void updateTokenFirebase(final Context context) {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("KNMobileDev");
        String txtLink = new ClsHardCode().linkGetUpdateToken;
        JSONObject obj = getDataRequestCommon(context);
        new FastNetworkingUtils().FNRequestPostDataUpdateToken(context, txtLink, obj, "", new InterfaceFastNetworking() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ANError error) {
                Toast.makeText(context, "refresh token failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public UserRequest getUserInfo(Context context) {
        UserRequest data = new UserRequest();
        try {
            ClsmUserLogin userLogin = new RepomUserLogin(context).getUserLogin(context);
            data.setIntOrgID(userLogin.getOrgId());
            data.setIntRoleId(userLogin.getIntRoleID());
            data.setPassword(userLogin.getPassword());
            data.setTxtNameApp(ClsHardCode.nameApp);
            data.setUsername(userLogin.getTxtUserName());
            data.setTxtUserToken(userLogin.getToken());
            data.setIntUserId(userLogin.getIntUserID());
        } catch (Exception ex) {

        }
        return data;
    }

    public String getDataDurationString(String startTime, String finishTime) {
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
        String selisih = "";
        try {
            Date dateStart = format.parse(startTime);
            Date dateFinish = format.parse(finishTime);
            long mills = dateFinish.getTime() - dateStart.getTime();
            int days = (int) (mills / (1000 * 60 * 60 * 24));
            int hours = (int) ((mills - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
//            int min = (int) (mills - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
//            int hours2 = (int) mills / (1000 * 60 * 60);
            int mins = (int) (mills / (1000 * 60)) % 60;
            if (days>0){
                selisih = days+" day(s)\n"+hours + " hours : " + mins + " minutes";
            }else{
                selisih = hours + " hours : " + mins + " minutes";
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return selisih;
    }

    public void popUpRejectedData(Activity context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_rejected_vehicle);
        dialog.setCancelable(true);
        dialog.setTitle("Pick a resource");
        Button btnClose = dialog.findViewById(R.id.btnClose);
        RecyclerView recyclerView = dialog.findViewById(R.id.rvDataRejection);
        TextView tv_no_doc = dialog.findViewById(R.id.tv_no_doc);
        TextView tv_no_pol = dialog.findViewById(R.id.tv_no_pol);
        TextView tvDriverName = dialog.findViewById(R.id.tvDriverName);
        TextView tvKeraniName = dialog.findViewById(R.id.tvKeraniName);
        TextView tvOutletFrom = dialog.findViewById(R.id.tvOutletFrom);
        TextView tvOutletTo = dialog.findViewById(R.id.tvOutletTo);

        try {
            List<ClsTConfigHelper> ltConfig = new RepoClsTConfigHelper(context).findAll();
            for (ClsTConfigHelper data :
                    ltConfig) {
                if (data.getTxtKey().equals(ClsHardCode.VEHICLE_NUMBER)) {
                    tv_no_pol.setText(data.getTxtValue());
                } else if (data.getTxtKey().equals(ClsHardCode.DRIVER_NAME)) {
                    tvDriverName.setText(data.getTxtValue());
                } else if (data.getTxtKey().equals(ClsHardCode.KERANI_NAME)) {
                    tvKeraniName.setText(data.getTxtValue());
                } else if (data.getTxtKey().equals(ClsHardCode.TXT_SHIP_FROM)) {
                    tvOutletFrom.setText(data.getTxtValue());
                } else if (data.getTxtKey().equals(ClsHardCode.TXT_SHIP_TO)) {
                    tvOutletTo.setText(data.getTxtValue());
                } else if (data.getTxtKey().equals(ClsHardCode.TXT_SPM_NO)) {
                    tv_no_doc.setText(data.getTxtValue());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<VmAdapterBasic> listData = new ArrayList<>();
        listData = getDataRejectList(context);
        AdapterListBasic adapterBasic;
        adapterBasic = new AdapterListBasic(context, listData);
        recyclerView.setAdapter(adapterBasic);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new LineItemDecoration(context, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public List<VmAdapterBasic> getDataRejectList(Context context) {
        List<VmAdapterBasic> ltData = new ArrayList<>();
        try {
            List<ClsTDataRejection> dtRejectiions = new RepoClsTDataRejection(context).findAll();
            for (ClsTDataRejection dtReject :
                    dtRejectiions) {
                VmAdapterBasic dt = new VmAdapterBasic();
                dt.setTitle(dtReject.getTxtPertanyaan());
                dt.setSubtitle(dtReject.getTxtJawaban());
                ltData.add(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ltData;
    }
    public List<VmAdapterBasicIssue> getDataRejectListIssue(Context context) {
        List<VmAdapterBasicIssue> ltData = new ArrayList<>();
        try {
            List<ClsTDataRejection> dtRejectiions = new RepoClsTDataRejection(context).findAll();
            for (ClsTDataRejection dtReject :
                    dtRejectiions) {
                VmAdapterBasicIssue dt = new VmAdapterBasicIssue();
                dt.setIssue(dtReject.getTxtPertanyaan());
                dt.setJawaban(dtReject.getTxtJawaban());
                dt.setIssueReason(dtReject.getTxtIssueReason());
                dt.setFixReason(dtReject.getTxtReason());
                List<ClsImages> ltImageIssue = new RepoClsImages(context).findLinkIssuesByDtlId(ClsHardCode.INT_IMAGE_ISSUE,dtReject.getIntFillDtlId());
                dt.setTxtLinkImageIssue(ltImageIssue);
                dt.setFixReason(dtReject.getTxtReason());
                List<ClsImages> ltImageFix = new RepoClsImages(context).findLinkIssuesByDtlId(ClsHardCode.INT_IMAGE_FIX,dtReject.getIntFillDtlId());
                dt.setTxtLinkImageFixed(ltImageFix);
                ltData.add(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ltData;
    }
    public static String getVersionApp(Activity a){
        String vers = "";
        try {
            vers = a.getPackageManager().getPackageInfo(a.getPackageName(), 0).versionName + " \u00a9 KN-IT";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return vers;
    }
    public static String getLinkApp(Activity activity){
        String link= "";

        String linkOri = new RepomConfig(activity.getApplicationContext()).APIToken;
        try {
            URL u = new URL(linkOri);
            link = u.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return link;
    }
    public void GenerateData(Context context, ResponseGetQuestion model) {
        BLHelper.savePreference(context, ClsHardCode.SP_INT_HEADER_ID, String.valueOf(model.getINTFILLHDRID()));
        BLHelper.savePreference(context, ClsHardCode.SP_QRCodeActive, model.getTxtQRCode());
        DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
        helper.clearDataPertanyaanJawaban();
        List<DataItem> datas = model.getData();
        if (datas!=null){
            for (DataItem data :
                    datas) {
                ClsmPertanyaan pertanyaan = new ClsmPertanyaan();
                pertanyaan.setIntPertanyaanId(data.getINTFORMDTLID());
                pertanyaan.setIntFillHeaderId(model.getINTFILLHDRID());
                pertanyaan.setTxtLoadingMessage(model.getTxtLoadingMessage());
                pertanyaan.setIntJenisPertanyaanId(data.getINTTYPEID());
                pertanyaan.setTxtPertanyaan(data.getTXTFORMNAME());
                pertanyaan.setIntLocationDocsId(data.getINTPOSITIONID());
                pertanyaan.setIntSeq(Integer.parseInt(data.getINTSEQ()));
                pertanyaan.setIntValidateID(data.getINTVALIDATEID());
                pertanyaan.setIntFillDetailId(data.getINTFILLDTLID());
                pertanyaan.setTxtMapCol(data.getTXTMAPCOL());
                pertanyaan.setTxtMetodePemeriksaan(data.getTXT_METODE_PEMERIKSAAN());
                if (data.getBITIMG().equals("1")) {
                    pertanyaan.setBolHavePhoto(true);
                    pertanyaan.setIntPhotoNeeded(Integer.parseInt(data.getINTIMGNEED()));
                } else {
                    pertanyaan.setBolHavePhoto(false);
                }
                if (data.getBITDATA().equals("1")) {
                    pertanyaan.setBolHaveAnswer(true);
                }
                if (data.getListDatIsian() != null) {
                    List<ListDatIsianItem> lisDataIsian = data.getListDatIsian();
                    for (ListDatIsianItem jwb :
                            lisDataIsian) {
                        ClsmJawaban clsmJawaban = new ClsmJawaban();
                        clsmJawaban.setBitActive(true);
                        clsmJawaban.setTxtIdJawaban(jwb.getTXTDATADTLID());
                        clsmJawaban.setIdJawaban(jwb.getINTVALUEID());
                        clsmJawaban.setIdPertanyaan(data.getINTFORMDTLID());
                        if (jwb.getINTVALUEID()==data.getINTVALUEID()){
                            clsmJawaban.setBitChoosen(true);
                        } else {
                            clsmJawaban.setBitChoosen(false);
                        }
                        clsmJawaban.setTxtMapCol(jwb.getTXTMAPCOL());
                        if (jwb.getTXTVALUE().equals("null")) {
                            clsmJawaban.setTxtJawaban("");
                        } else {
                            clsmJawaban.setTxtJawaban(jwb.getTXTVALUE());
                        }

                        try {
                            new RepomJawaban(context).createOrUpdate(clsmJawaban);
                        } catch (Exception ex) {
                            ex.getMessage();
                        }
                    }

                } else {
                    pertanyaan.setBolHaveAnswer(false);
                }
                if (data.getListDatImage() != null) {
                    List<ListDatImageItem> lisDataImage = data.getListDatImage();
                    for (ListDatImageItem imageItem :
                            lisDataImage) {
                        ClsImages images = new ClsImages();
                        images.setIdPertanyaan(pertanyaan.getIntPertanyaanId());
                        images.setIntFillDetailId(pertanyaan.getIntFillDetailId());
                        images.setTxtLinkImages(imageItem.getTXTLINK());
                        images.setTxtDesc(imageItem.getTXTFILENAME());
                        images.setIdImage(imageItem.getINTFILLDTLIMGID());
                        try {
                            new RepoClsImages(context).createOrUpdate(images);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    new RepomPertanyaan(context).createOrUpdate(pertanyaan);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (model.getDataIssue() != null) {
            DataIssue issue = model.getDataIssue();
            try {
                ClsTConfigHelper generalInfo = new ClsTConfigHelper();
                generalInfo.setTxtKey(ClsHardCode.DRIVER_NAME);
                generalInfo.setTxtValue(issue.getDRIVERNAME());
                new RepoClsTConfigHelper(context).createOrUpdate(generalInfo);

                generalInfo = new ClsTConfigHelper();
                generalInfo.setTxtKey(ClsHardCode.KERANI_NAME);
                generalInfo.setTxtValue(issue.getKERANINAME());
                new RepoClsTConfigHelper(context).createOrUpdate(generalInfo);

                generalInfo = new ClsTConfigHelper();
                generalInfo.setTxtKey(ClsHardCode.TXT_SPM_NO);
                generalInfo.setTxtValue(issue.getTXTSPMNO());
                new RepoClsTConfigHelper(context).createOrUpdate(generalInfo);

                generalInfo = new ClsTConfigHelper();
                generalInfo.setTxtKey(ClsHardCode.VEHICLE_NUMBER);
                generalInfo.setTxtValue(issue.getTXTVEHICLENO());
                new RepoClsTConfigHelper(context).createOrUpdate(generalInfo);

                generalInfo = new ClsTConfigHelper();
                generalInfo.setTxtKey(ClsHardCode.TXT_SHIP_FROM);
                generalInfo.setTxtValue(issue.getSHIPFROM());
                new RepoClsTConfigHelper(context).createOrUpdate(generalInfo);

                generalInfo = new ClsTConfigHelper();
                generalInfo.setTxtKey(ClsHardCode.TXT_SHIP_TO);
                generalInfo.setTxtValue(issue.getSHIPTO());
                new RepoClsTConfigHelper(context).createOrUpdate(generalInfo);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            List<ListDatFormIssueItem> datFormIssueItems = model.getDataIssue().getListDatFormIssue();
            if(datFormIssueItems!=null){
                for (ListDatFormIssueItem item :
                        datFormIssueItems) {

                    List<ListDatImageItem> dataDatImageItems = item.getListDatImage();
                    if (dataDatImageItems!=null){
                        for (ListDatImageItem img:
                                dataDatImageItems) {
                            ClsImages newImages = new ClsImages();
                            newImages.setIntFillDetailId(item.getINTFILLDTLID());
                            newImages.setTxtLinkImages(img.getTXTLINK());
                            newImages.setIntFlag(ClsHardCode.INT_IMAGE_ISSUE);
                            try {
                                new RepoClsImages(context).createOrUpdate(newImages);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    String Reason ="";
                    DataVehichleIssue dataVehichleIssues = item.getDataVehichleIssue();
                    if (dataVehichleIssues != null) {
                        List<ListDatVehichleIssueImageItem> listDatVehichleIssueImageItems = dataVehichleIssues.getListDatVehichleIssueImage();
                        DataVehichleFix dataVehichleFix = dataVehichleIssues.getDataVehichleFix();
                        ClsTDataRejection dataReject = new ClsTDataRejection();
                        dataReject.setTxtPertanyaan(item.getTXTFORMNAME());
                        dataReject.setIntValueId(item.getINTVALUEID());
                        dataReject.setTxtIssueReason(item.getTxtReason());
                        dataReject.setIntFillDtlId(item.getINTFILLDTLID());
                        dataReject.setTxtReason(dataVehichleFix.getTXT_REASON());
                        List<ListDatIsianItem> datIsianItems = item.getListDatIsian();
                        if (datIsianItems!=null){
                            for (ListDatIsianItem isian :
                                    datIsianItems) {
                                if (isian.getINTVALUEID() == item.getINTVALUEID()) {
                                    dataReject.setTxtJawaban(isian.getTXTVALUE());
                                    try {
                                        new RepoClsTDataRejection(context).createOrUpdate(dataReject);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        if (dataVehichleFix != null) {
                            List<DataVehichleFixImage> dataVehichleFixImages = dataVehichleFix.getListDatVehichleFixImage();
                            if(dataVehichleFixImages!=null){
                                for (DataVehichleFixImage img :
                                        dataVehichleFixImages) {
                                    ClsImages newImages = new ClsImages();
                                    newImages.setIntFillDetailId(item.getINTFILLDTLID());
                                    newImages.setTxtLinkImages(img.getTXT_PATH());
                                    newImages.setTxtDesc(img.getTXT_FILENAME());
                                    newImages.setIntFlag(ClsHardCode.INT_IMAGE_FIX);
                                    try {
                                        new RepoClsImages(context).createOrUpdate(newImages);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
                    }

                }
            }
        }
    }
    public void stopShimmerEffect(ShimmerFrameLayout shimmerViewContainer){
        shimmerViewContainer.setVisibility(View.GONE);
        shimmerViewContainer.stopShimmer();
    }
    public static void displayImageOriginalUrlThumnail(Context ctx, ImageView img, String url) {
        try {
            Glide.with(ctx).load(url).override(400, 500)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_arrow_drop)
                    .into(img);
        } catch (Exception e) {
        }
    }

    public void saveDataTimeFromApi(ResponseGetQuestion model, Context context) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final SimpleDateFormat formatNew = new SimpleDateFormat(ClsHardCode.FormatTime);
        for (TimeDataItem data :
                model.getTimeData()) {
            if (data.getINTDESC() == 1) {
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.SP_SCAN_TIME, time);
            } else if (data.getINTDESC() == 2) {
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.SP_CHECKING_FINISH, time);
            } else if (data.getINTDESC() == 3) {
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.SP_STARTTIME_CHECKER, time);
            } else if (data.getINTDESC() == 4) {
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.SP_FINISHTIME_CHECKER, time);
            } else if (data.getINTDESC() == 5) {
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.SP_SCANTIME_UNLOADING, time);
            } else if (data.getINTDESC() == 6) {
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.SP_STARTTIME_UNLOADING, time);
            }
        }
    }

    public JSONObject getJsonParamSetTime(String time, Context context, int intUserId, int intHeaderId, int intStatus, String txtStatus, int intStatusProgress, String txtMessage) {
        JSONObject jData = new JSONObject();
        JSONObject resJson = new JSONObject();

        try {
            jData.put("intHeaderId", intHeaderId);
            jData.put("txtTime", time);
            jData.put("intStatus", intStatus);
            jData.put("txtStatus", txtStatus);
            jData.put("intStatusProgress", intStatusProgress);
            jData.put("txtUserId", intUserId);
            jData.put("txtMessage", txtMessage);
            resJson.put("data", jData);
            resJson.put("device_info", new ClsHardCode().pDeviceInfo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resJson;
    }

    public VMRequestDataSPM.UserRequestSPM getUserInfoSPM(Context context, int type, String SPMNumber, int intIsValidator, int intFillHdrId) {
        VMRequestDataSPM.UserRequestSPM data = new VMRequestDataSPM().new UserRequestSPM();
        try {
            ClsmUserLogin userLogin = new RepomUserLogin(context).getUserLogin(context);
            data.setIntOrgID(userLogin.getOrgId());
            data.setIntRoleId(userLogin.getIntRoleID());
            data.setTxtNameApp(ClsHardCode.nameApp);
            data.setUsername(userLogin.getTxtUserName());
            data.setIntUserId(userLogin.getIntUserID());
            data.setTxtNoSPM(SPMNumber);
            data.setIntFillHdrID(intFillHdrId);
            data.setIntIsValidator(intIsValidator);
            data.setType(type);
            List<ClsOrganisation> ltOrg = new RepoClsOrganisation(context).findAll();
            data.setLtOrganisation(ltOrg);
        } catch (Exception ex) {

        }
        return data;
    }

    public String getNestedInfo(Context context, String txtCode) {
        String noDoc = "";

        try {
            if (txtCode.equals(ClsHardCode.TXT_DEFAULT)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.TXT_DEFAULT);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.TXT_CREATION_DATE)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.TXT_CREATION_DATE);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.TXT_VEHICLE_TYPE)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.TXT_VEHICLE_TYPE);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.TXT_SPM_NO)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.TXT_SPM_NO);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.TXT_ITEM_TYPE)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.TXT_ITEM_TYPE);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.TXT_FIND_DETAIL_HCD)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.TXT_FIND_DETAIL_HCD);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.TXT_EXPEDITION_NAME)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.TXT_EXPEDITION_NAME);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.TXT_PLAN_DELIVERY_DATE)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.TXT_PLAN_DELIVERY_DATE);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.DRIVER_NAME)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.DRIVER_NAME);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.KERANI_NAME)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.KERANI_NAME);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            } else if (txtCode.equals(ClsHardCode.VEHICLE_NUMBER)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.VEHICLE_NUMBER);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noDoc;
    }

    public String getNestedInfoDetail(Context context, String txtCode, String txtChildCode) {
        String noDoc = "";
        List<ClsmPertanyaan> pert = null;
        try {
            pert = new RepomPertanyaan(context).findQuestionGeneralInfo(txtCode);
            if (pert.size() > 0) {
                List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeadertoFindDetail(pert.get(0).getIntPertanyaanId(), txtChildCode);
                if (jawabans.size() > 0) {
                    noDoc = jawabans.get(0).getTxtJawaban();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noDoc;
    }

    public ClsPushData pushData(String versionName, Context context) {
        ClsPushData dtclsPushData = new ClsPushData();
        ClsDataJson dtPush = new ClsDataJson();
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        List<Boolean> isDataNull = new ArrayList<>();
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context) > 0) {
            ClsmUserLogin dataLogin = null;
            try {
                dataLogin = new RepomUserLogin(context).getUserLogin(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dtPush.setDtLogin(dataLogin.getDtLogIn());
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                RepomCounterData _mCounterDataRepo = new RepomCounterData(context);
                ClsmCounterData _clsmCounterData = new ClsmCounterData();
                _clsmCounterData.setIntId(EnumCounterData.MonitorScedule.getIdCounterData());
                _clsmCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _clsmCounterData.setTxtName("Monitor Service");
                _clsmCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_clsmCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }


//            List<ClstLogError>

            FileUpload = new HashMap<>();
            dtPush.setFromUnplan(false);

//            if ()
        } else {
            dtPush = null;
        }
        dtclsPushData.setDataJson(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }

    public boolean printPDF(Context context) {
        // open a new document
        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("Images.pdf"));
            document.open();

            Image image1 = Image.getInstance("watermark.png");
            document.add(image1);


            String imageUrl = "http://resepcaramemasak.info/wp-content/uploads/2018/02/resep-bajigur.jpg";

            Image image2 = Image.getInstance(new URL(imageUrl));
            document.add(image2);
            document.add(new Paragraph("A Hello World PDF document."));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void createPDF(View view) {
//output file path
        String outpath = Environment.getExternalStorageDirectory() + "/mypdf.pdf";
//reference to EditText
//create document object
        PdfDocument pdfDoc = new PdfDocument();
        Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
//        PdfWriter writer = PdfWriter.getInstance(doc, out);
//        PdfContentByte cb = writer.getDirectContent();
        try {

//create pdf writer instance
            PdfWriter.getInstance(doc, new FileOutputStream(outpath));
//open the document for writing
            doc.open();
//add paragraph to the document

            doc.add(new LineSeparator());
            doc.addHeader("Test Name", "Test Content");
            doc.add(new Paragraph("HelloWorld"));
            doc.addAuthor("Roberto");
            doc.add(new Chunk());
            doc.add(new DottedLineSeparator());
//close the document
            doc.close();

        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ClsPushData pushDataError(String versionName, Context context) {
        ClsPushData dtclsPushData = new ClsPushData();
        ClsDataError dtPush = new ClsDataError();
        RepomUserLogin loginRepo = new RepomUserLogin(context);
        HashMap<String, byte[]> FileUpload = null;
        List<String> FileName = new ArrayList<>();
        if (loginRepo.getContactCount(context) > 0) {
            ClsmUserLogin dataLogin = null;
            try {
                dataLogin = new RepomUserLogin(context).getUserLogin(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dtPush.setTxtVersionApp(versionName);
            dtPush.setTxtUserId(String.valueOf(dataLogin.getIntUserID()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                RepomCounterData _mCounterDataRepo = new RepomCounterData(context);
                ClsmCounterData _clsmCounterData = new ClsmCounterData();
                _clsmCounterData.setIntId(EnumCounterData.MonitorScedule.getIdCounterData());
                _clsmCounterData.setTxtDescription("value menunjukan waktu terakhir menjalankan services");
                _clsmCounterData.setTxtName("Monitor Service");
                _clsmCounterData.setTxtValue(dateFormat.format(calendar.getTime()));
                _mCounterDataRepo.createOrUpdate(_clsmCounterData);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            RepotLogError _repotLogError = new RepotLogError(context);
//            List<ClstLogError> ListOfDataError = _repotLogError.getAllPushData();
/*
            FileUpload = new HashMap<>();
            if (ListOfDataError!=null){
                dtPush.setListOfDatatLogError(ListOfDataError);
                for (ClstLogError data : ListOfDataError){
                    if (data.getBlobImg()!=null){
                        FileName.add(data.getTxtGuiId());
                        FileUpload.put(data.getTxtGuiId(), data.getBlobImg());
                    }
                }
            }*/

        } else {
            dtPush = null;
        }
        dtclsPushData.setDataError(dtPush);
        dtclsPushData.setFileName(FileName);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }


}


