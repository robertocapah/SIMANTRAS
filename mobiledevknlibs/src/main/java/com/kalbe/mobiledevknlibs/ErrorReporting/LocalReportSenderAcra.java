package com.kalbe.mobiledevknlibs.ErrorReporting;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.kalbe.mobiledevknlibs.DeviceInformation.DeviceInformation;
import com.kalbe.mobiledevknlibs.DeviceInformation.ModelDevice;
import com.kalbe.mobiledevknlibs.PermissionChecker.PermissionChecker;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dewi Oktaviani on 1/9/2018.
 */

public class LocalReportSenderAcra implements ReportSender{

    private final Map<ReportField, String> mMapping = new HashMap<ReportField, String>();
    private FileWriter crashReport;
    static Date date = new Date();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    static SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd");
    ModelReport report;
    static String fileName;

    public LocalReportSenderAcra(String path, ModelReport report) {
        // the destination
        this.report = report;
        fileName = "log_" + dateFormat.format(date)+".txt";
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
    @Override
    public void send(Context context, CrashReportData errorContent) throws ReportSenderException {
        final Map<String, String> finalReport = remap(errorContent);
        BufferedWriter buf = new BufferedWriter(crashReport);
        try {
            if(buf!=null){
                Set<Map.Entry<String, String>> set = finalReport.entrySet();
                Iterator<Map.Entry<String, String>> i = set.iterator();
//                if (buf.)
                buf.append("").append("\n");
                while (i.hasNext()) {
                    Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
                buf.append("[" + me.getKey() + "] = " + "'"+me.getValue()+"'").append("\n");
//                    buf.append("[" + me.getKey() + "]=" + me.getValue());
                }

//            ModelDevice modelDevice = DeviceInformation.getDeviceInformation();
//            if (modelDevice != null){
//                buf.append("\n").append("Device name :"+ modelDevice.getDevice() );
//                buf.append("\n").append("Model name :"+ modelDevice.getModel() );
//                buf.append("\n").append("OS Version :"+ modelDevice.getOsVersion() );
//                buf.append("\n").append("Product :"+ modelDevice.getProduct() );
//                buf.append("\n").append("SDK Version :"+ modelDevice.getVersionSDK() );
//            }

                if (report != null){
                    buf.append("\n").append("User id :"+ report.getIntUserID() );
                    buf.append("\n").append("User name :"+ report.getTxtUserName() );
                    buf.append("\n").append("Role id :"+ report.getIntRoleID() );
                    buf.append("\n").append("Role name :"+ report.getTxtRoleName() );
                    buf.append("\n").append("Device name :"+ report.getDevice() );
                    buf.append("\n").append("Model name :"+ report.getModel() );
                    buf.append("\n").append("OS Version :"+ report.getOsVersion() );
                    buf.append("\n").append("Product :"+ report.getProduct() );
                    buf.append("\n").append("SDK Version :"+ report.getVersionSDK() );
                }
                buf.append("\n").append("----------------*****----------------");
                buf.append("\n").append("\n");
                buf.flush();
//                buf.close();
            }
        } catch (IOException e) {
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//            try {
//                buf.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
            Log.e("TAG", "IO ERROR", e);
        }finally {
            if (buf!=null){
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static ModelError getModelError( ){
        if (fileName!=null){
            ModelError modelError = new ModelError();
            modelError.set_dtDate(dateFormats.format(date));
            modelError.set_txtFileName(fileName);
            return modelError;
        }else {
            return null;
        }
    }
}
