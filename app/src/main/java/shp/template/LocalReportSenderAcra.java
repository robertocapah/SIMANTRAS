package shp.template;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import shp.template.Common.ClsmUserLogin;
import shp.template.Common.ClstLogError;
import shp.template.Data.ClsHardCode;
import shp.template.Repo.RepotLogError;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickFile;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;

import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dewi Oktaviani on 1/9/2018.
 */

public class LocalReportSenderAcra implements ReportSender {

    private final Map<ReportField, String> mMapping = new HashMap<ReportField, String>();
    private FileWriter crashReport;
    Date date = new Date();
    static SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String fileName;
    String path;
    ClsmUserLogin dtLogin;
    ClstLogError logError;
    Context mContext;
    public LocalReportSenderAcra(Context mContext, String path, ClsmUserLogin dtLogin, ClstLogError logError, String fileName) {
        // the destination
        this.mContext = mContext;
        this.dtLogin = dtLogin;
        this.logError = logError;
        this.fileName = fileName;
        this.path = path + fileName;
        File logFile = new File(path, fileName);
        crashReport = null;
        try {
            crashReport = new FileWriter(logFile, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isNull(String aString) {
        return aString == null || ACRAConstants.NULL_VALUE.equals(aString);
    }
    private Map<String, String> remap(Map<ReportField, String> report) {

        ReportField[] fields = ACRA.getConfig().customReportContent();
        if (fields.length == 0) {
            fields = ACRAConstants.DEFAULT_REPORT_FIELDS;
        }

        final Map<String, String> finalReport = new HashMap<String, String>(
                report.size());
        for (ReportField field : fields) {
            if (mMapping == null || mMapping.get(field) == null) {
                finalReport.put(field.toString(), report.get(field));
            } else {
                finalReport.put(mMapping.get(field), report.get(field));
            }
        }
        return finalReport;
    }
//    @Override
//    public void send(Context context, CrashReportData errorContent) throws ReportSenderException {
//        final Map<String, String> finalReport = remap(errorContent);
//
//        try {
////            ClsmUserLogin dtUserLogin = new BLMain().getUserLogin(context);
//            BufferedWriter buf = new BufferedWriter(crashReport);
//
//            Set<Map.Entry<String, String>> set = finalReport.entrySet();
//            Iterator<Map.Entry<String, String>> i = set.iterator();
//            buf.append("").append("\n");
//
//            while (i.hasNext()) {
//                Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
//                buf.append("[" + me.getKey() + "] = " + "'"+me.getValue()+"'").append("\n");
//            }
//

//            if (dtLogin!=null){
//                buf.append("\n").append("User id :"+ dtLogin.getIntUserID() );
//                buf.append("\n").append("User name :"+ dtLogin.getTxtUserName() );
//                buf.append("\n").append("Role id :"+ dtLogin.getIntRoleID() );
//                buf.append("\n").append("Role name :"+ dtLogin.getTxtRoleName() );
//            }
//            buf.append("\n").append("----------------*****----------------");
//            buf.append("\n").append("\n");
//            buf.flush();
//            buf.close();
//
//            if (dtLogin!=null){
//                logError.setTxtUserId(String.valueOf(dtLogin.getIntUserID()));
//            }
//            Uri uriPath = UriData.getOutputMediaUriFolder(mContext, path);
//            byte[] file = PickFile.getByteArrayFileToSave(uriPath, mContext);
//            logError.setTxtDeviceName(android.os.Build.DEVICE);
//            logError.setTxtOs(System.getProperty("os.version"));
//            logError.setTxtFileName(fileName);
//            logError.setDtDateLog(dateFormats.format(new Date()));
//            logError.setBlobImg(file);
//            logError.setIntFlagPush(new ClsHardCode().Save);
//            new RepotLogError(mContext).createOrUpdate(logError);
//        } catch (IOException e) {
//            Log.e("TAG", "IO ERROR", e);
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void send(Context context, CrashReportData errorContent) throws ReportSenderException {


        final Map<String, String> finalReport = remap(errorContent);

        try {
            BufferedWriter buf = new BufferedWriter(crashReport);

            Set<Map.Entry<String, String>> set = finalReport.entrySet();
            Iterator<Map.Entry<String, String>> i = set.iterator();
            buf.append("").append("\n");

            while (i.hasNext()) {
                Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
                buf.append("[" + me.getKey() + "] = " + "'"+me.getValue()+"'").append("\n");
            }
            if (android.os.Build.DEVICE != null){
                buf.append("\n").append("[Device_Name] = ").append(android.os.Build.DEVICE);
                logError.setTxtDeviceName(android.os.Build.DEVICE);
            }
            if (android.os.Build.MODEL != null){
                buf.append("\n").append("[Model_Name] = ").append(android.os.Build.MODEL);
            }
            if(android.os.Build.PRODUCT != null){
                buf.append("\n").append("[Product_Name] = ").append(android.os.Build.PRODUCT);
            }
            if (android.os.Build.VERSION.SDK!=null){
                buf.append("\n").append("[SDK] = ").append(android.os.Build.VERSION.SDK);
            }
            if (System.getProperty("os.version") != null){
                logError.setTxtOs(System.getProperty("os.version"));
                buf.append("\n").append("[OS] = ").append(System.getProperty("os.version"));
            }

            if (dtLogin!=null){
                buf.append("\n").append("User id :"+ dtLogin.getIntUserID() );
                buf.append("\n").append("User name :"+ dtLogin.getTxtUserName() );
                buf.append("\n").append("Role id :"+ dtLogin.getIntRoleID() );
                buf.append("\n").append("Role name :"+ dtLogin.getTxtRoleName() );
            }
            buf.append("\n").append("----------------*****----------------");
            buf.append("\n").append("\n");
            buf.flush();
            buf.close();

            if (dtLogin!=null){
                logError.setTxtUserId(String.valueOf(dtLogin.getIntUserID()));
            }
            Uri uriPath = new UriData().getOutputMediaUriFolder(mContext, path);
            byte[] file = new PickFile().getByteArrayFileToSave(uriPath, mContext);
            logError.setTxtFileName(fileName);
            logError.setDtDateLog(dateFormats.format(new Date()));
            logError.setBlobImg(file);
            logError.setIntFlagPush(new ClsHardCode().Save);
            new RepotLogError(mContext).createOrUpdate(logError);
        } catch (IOException e) {
            Log.e("TAG", "IO ERROR", e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
