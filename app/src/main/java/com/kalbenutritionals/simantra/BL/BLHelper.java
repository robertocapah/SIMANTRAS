package com.kalbenutritionals.simantra.BL;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.kalbe.mobiledevknlibs.DeviceInformation.DeviceInformation;
import com.kalbe.mobiledevknlibs.DeviceInformation.ModelDevice;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.DataItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ListDatImageItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ListDatIsianItem;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.ResponseGetQuestion;
import com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion.TimeDataItem;
import com.kalbenutritionals.simantra.Database.Common.ClsDataError;
import com.kalbenutritionals.simantra.Database.Common.ClsDataJson;
import com.kalbenutritionals.simantra.Database.Common.ClsImages;
import com.kalbenutritionals.simantra.Database.Common.ClsPushData;
import com.kalbenutritionals.simantra.Database.Common.ClsToken;
import com.kalbenutritionals.simantra.Database.Common.ClsmCounterData;
import com.kalbenutritionals.simantra.Database.Common.ClsmJawaban;
import com.kalbenutritionals.simantra.Database.Common.ClsmPertanyaan;
import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.DatabaseHelper;
import com.kalbenutritionals.simantra.Database.DatabaseManager;
import com.kalbenutritionals.simantra.Database.EnumCounterData;
import com.kalbenutritionals.simantra.Database.Repo.EnumTime;
import com.kalbenutritionals.simantra.Database.Repo.RepoClsImages;
import com.kalbenutritionals.simantra.Database.Repo.RepomJawaban;
import com.kalbenutritionals.simantra.Database.Repo.RepomPertanyaan;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepotLogError;
import com.kalbenutritionals.simantra.Database.Repo.RepomCounterData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public JSONObject getDataRequestDataSPM(Context context,int type, String SPMNumber, int intIsValidator, int intFillHdrId) {
        VMRequestDataSPM data = new VMRequestDataSPM();
        DeviceInfo dataDevice = new BLHelper().getDeviceInfo();
        VMRequestDataSPM.UserRequestSPM userData = new BLHelper().getUserInfoSPM(context,type, SPMNumber, intIsValidator, intFillHdrId);
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
    public String getDataDurationString(String startTime, String finishTime){
        final SimpleDateFormat format = new SimpleDateFormat(ClsHardCode.FormatTime);
        String selisih = "";
        try {
            Date dateStart = format.parse(startTime);
            Date dateFinish = format.parse(finishTime);
            long mills = dateFinish.getTime() - dateStart.getTime();
            int days = (int) (mills / (1000 * 60 * 60 * 24));
            int hours = (int) ((mills - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (mills - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

            int hours2 = (int) mills / (1000 * 60 * 60);
            int mins = (int) (mills / (1000 * 60)) % 60;
            selisih = hours + " hours : " + mins + " minutes";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return selisih;
    }
    public void GenerateData(Context context, ResponseGetQuestion model){
        BLHelper.savePreference(context,ClsHardCode.INT_HEADER_ID,String.valueOf(model.getINTFILLHDRID()));
        DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
        helper.clearDataPertanyaanJawaban();
        List<DataItem> datas =  model.getData();
        for (DataItem data :
                datas) {
            ClsmPertanyaan pertanyaan = new ClsmPertanyaan();
            pertanyaan.setIntPertanyaanId(data.getINTFORMDTLID());
            pertanyaan.setIntFillHeaderId(model.getINTFILLHDRID());
            pertanyaan.setIntJenisPertanyaanId(data.getINTTYPEID());
            pertanyaan.setTxtPertanyaan(data.getTXTFORMNAME());
            pertanyaan.setIntLocationDocsId(data.getINTPOSITIONID());
            pertanyaan.setIntSeq(Integer.parseInt(data.getINTSEQ()));
            pertanyaan.setIntValidateID(data.getINTVALIDATEID());
            pertanyaan.setIntFillDetailId(data.getINTFILLDTLID());
            pertanyaan.setTxtMapCol(data.getTXTMAPCOL());
            if (data.getBITIMG().equals("1")){
                pertanyaan.setBolHavePhoto(true);
                pertanyaan.setIntPhotoNeeded(Integer.parseInt(data.getINTIMGNEED()));
            }else{
                pertanyaan.setBolHavePhoto(false);
            }
            if(data.getBITDATA().equals("1")){
                pertanyaan.setBolHaveAnswer(true);
            }
            if (data.getListDatIsian()!= null){
                List<ListDatIsianItem> lisDataIsian = data.getListDatIsian();
                for (ListDatIsianItem jwb :
                        lisDataIsian) {
                    ClsmJawaban clsmJawaban = new ClsmJawaban();
                    clsmJawaban.setBitActive(true);
                    clsmJawaban.setTxtIdJawaban(jwb.getTXTDATADTLID());
                    clsmJawaban.setIdJawaban(jwb.getINTDATADTLID());
                    clsmJawaban.setIdPertanyaan(data.getINTFORMDTLID());
                    if(jwb.getTXTDATADTLID().equals(String.valueOf(data.getINTVALUEID()))){
                        clsmJawaban.setBitChoosen(true);
                    }else{
                        clsmJawaban.setBitChoosen(false);
                    }
                    clsmJawaban.setTxtMapCol(jwb.getTXTMAPCOL());
                    if(jwb.getTXTVALUE().equals("null")){
                        clsmJawaban.setTxtJawaban("");
                    }else{
                        clsmJawaban.setTxtJawaban(jwb.getTXTVALUE());
                    }

                    try{
                        new RepomJawaban(context).createOrUpdate(clsmJawaban);
                    }catch (Exception ex){
                        ex.getMessage();
                    }
                }

            }else{
                pertanyaan.setBolHaveAnswer(false);
            }
            if (data.getListDatImage()!=null){
                List<ListDatImageItem> lisDataImage = data.getListDatImage();
                for (ListDatImageItem imageItem :
                        lisDataImage) {
                    ClsImages images = new ClsImages();
                    images.setIdPertanyaan(pertanyaan.getIntPertanyaanId());
                    images.setIntFillDetailId(pertanyaan.getIntFillDetailId());
                    images.setTxtLinkImages(imageItem.getTXTLINK());
                    try {
                        new RepoClsImages(context).createOrUpdate(images);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            try{
                new RepomPertanyaan(context).createOrUpdate(pertanyaan);
            }catch (Exception e){

            }
        }

    }
    public static void displayImageOriginalUrlThumnail(Context ctx, ImageView img, String url) {
        try {
            Glide.with(ctx).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop()
                    .placeholder(R.drawable.ic_file_upload_black_24dp)
                    .into(img);
        } catch (Exception e) {
        }
    }
    public void saveDataTimeFromApi(ResponseGetQuestion model, Context context){
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final SimpleDateFormat formatNew = new SimpleDateFormat(ClsHardCode.FormatTime);
        for (TimeDataItem data :
                model.getTimeData() ) {
            if (data.getINTDESC() == 1){
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.ScanTime, time);
            }else if (data.getINTDESC() == 2){
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.IdleTime, time);
            }else if(data.getINTDESC() == 3){
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.StartTime, time);
            }else if(data.getINTDESC() == 4){
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.ScanTimeUnloading, time);
            }else if(data.getINTDESC() == 5){
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.StartTimeUnloading, time);
            }else if(data.getINTDESC() == 6){
                String time = data.getDTTIME();
                try {
                    Date d = format.parse(time);
                    time = formatNew.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                BLHelper.savePreference(context, ClsHardCode.EndTimeUnloading, time);
            }
        }
    }
    public JSONObject getJsonParamSetTime(String time, Context context, int intUserId, int intHeaderId, int intStatus,String txtStatus, int intStatusProgress, String txtMessage){
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
    public VMRequestDataSPM.UserRequestSPM getUserInfoSPM(Context context,int type, String SPMNumber, int intIsValidator, int intFillHdrId) {
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
            }else if (txtCode.equals(ClsHardCode.DRIVER_NAME)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.DRIVER_NAME);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            }else if (txtCode.equals(ClsHardCode.KERANI_NAME)) {
                List<ClsmPertanyaan> pert = new RepomPertanyaan(context).findQuestionGeneralInfo(ClsHardCode.KERANI_NAME);
                if (pert.size() > 0) {
                    List<ClsmJawaban> jawabans = new RepomJawaban(context).findByHeader(pert.get(0).getIntPertanyaanId());
                    if (jawabans.size() > 0) {
                        noDoc = jawabans.get(0).getTxtJawaban();
                    }
                }
            }else if (txtCode.equals(ClsHardCode.VEHICLE_NUMBER)) {
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


