package com.kalbenutritionals.simantra;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.kalbenutritionals.simantra.Database.Common.ClsmUserLogin;
import com.kalbenutritionals.simantra.Database.Common.ClstLogError;
import com.kalbenutritionals.simantra.Data.ClsHardCode;
import com.kalbenutritionals.simantra.Database.Repo.RepomUserLogin;
import com.kalbenutritionals.simantra.Database.Repo.RepotLogError;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dewi Oktaviani on 11/8/2018.
 */

@ReportsCrashes(
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
//        resDialogText = R.string.crash_dialog_text,
//        resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
//        resDialogTitle = R.string.crash_dialog_title, // optional. default is your application txtPertanyaan
//        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. When defined, adds a user text field input with this text resource as a label
//        resDialogOkToast = R.string.crash_dialog_ok_toast, // optional. displays a Toast message when the user accepts to send a report.

        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PHONE_MODEL,
                ReportField.BRAND,
                ReportField.CUSTOM_DATA,
                ReportField.INITIAL_CONFIGURATION,
                ReportField.CRASH_CONFIGURATION,
                ReportField.USER_CRASH_DATE,
                ReportField.STACK_TRACE,
                ReportField.LOGCAT,
                ReportField.USER_COMMENT,
                ReportField.DEVICE_ID,
                ReportField.FILE_PATH}
)
public class MyApplicationTemplate extends Application {
    private Context mContext;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String fileName = "log_"+dateFormat.format(date)+".txt";
    @Override
    public void onCreate() {

        mContext = getApplicationContext();
        ACRA.init(this);
        ClsmUserLogin dtUserLogin = null;
        try {
            dtUserLogin = new RepomUserLogin(mContext).getUserLogin(mContext);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        List<ClstLogError> dataError = new RepotLogError(mContext).getAllPushData();
        ClstLogError logError = new ClstLogError();
        /*if (dataError!=null){
            if (dataError.size()>0){
                boolean exist = false;
                String txtGuiId = "";
                for (ClstLogError data : dataError){
                    if (data.getTxtFileName().equals(fileName)){
                        txtGuiId = data.getTxtGuiId();
                        exist = true;
                        break;
                    }
                }

                if (exist){
                    logError.setTxtGuiId(txtGuiId);
                }else {
                    logError.setTxtGuiId(GenerateGuid());
                }
            }else {
                logError.setTxtGuiId(GenerateGuid());
            }
        }else {
            logError.setTxtGuiId(GenerateGuid());
        }*/
        new UriData().getOutputFolder(new ClsHardCode().txtFolderTemp);
        String txtPath = new ClsHardCode().txtFolderTemp;
//        ACRA.getErrorReporter().handleSilentException(new RuntimeException("whatever I want"));
        ACRA.getErrorReporter().setReportSender(new LocalReportSenderAcra(mContext,txtPath, dtUserLogin, logError, fileName));
//        ACRA.getErrorReporter().checkReportsOnApplicationStart();
        super.onCreate();
    }


    public String GenerateGuid(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
