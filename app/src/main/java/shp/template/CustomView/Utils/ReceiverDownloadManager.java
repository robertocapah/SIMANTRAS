package shp.template.CustomView.Utils;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import java.util.List;

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
