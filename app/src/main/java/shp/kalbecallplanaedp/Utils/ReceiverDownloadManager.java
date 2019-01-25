package shp.kalbecallplanaedp.Utils;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;

import shp.kalbecallplanaedp.Common.tAkuisisiDetail;
import shp.kalbecallplanaedp.Common.tInfoProgramDetail;
import shp.kalbecallplanaedp.Common.tRealisasiVisitPlan;
import shp.kalbecallplanaedp.Data.clsHardCode;
import shp.kalbecallplanaedp.R;
import shp.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import shp.kalbecallplanaedp.Repo.tInfoProgramDetailRepo;
import shp.kalbecallplanaedp.Repo.tRealisasiVisitPlanRepo;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickFile;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.SnackBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Dewi Oktaviani on 11/7/2018.
 */

public class ReceiverDownloadManager {
    private List<Long> longList;
    private CoordinatorLayout coordinatorLayout;
    private List<Long> listAll;
    Snackbar snackbar;
    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }

    public ReceiverDownloadManager(List<Long> longList) {
        this.longList = longList;
    }

//    private void deleteMediaStorageDirtemp (){
//        File mediaStorageDir = new File(new clsHardCode().txtFolderDownload + File.separator);
//        if (mediaStorageDir.exists()){
//            if (mediaStorageDir.isDirectory()){
//                for (File currentFile : mediaStorageDir.listFiles()){
//                    currentFile.delete();
//                }
//            }
//            mediaStorageDir.delete();
//        }
//    }


}
