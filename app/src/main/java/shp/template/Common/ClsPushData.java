package shp.template.Common;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class ClsPushData {
    private ClsDataJson dataJson;
    private HashMap<String, byte[]> FileUpload;
    private List<String> FileName;
    private boolean isDataNull;
    private ClsDataError dataError;
    public ClsDataJson getDataJson() {
        return dataJson;
    }
    public void setDataJson(ClsDataJson dataJson) {
        this.dataJson = dataJson;
    }

    public HashMap<String, byte[]> getFileUpload() {
        return FileUpload;
    }

    public void setFileUpload(HashMap<String, byte[]> fileUpload) {
        FileUpload = fileUpload;
    }

    public List<String> getFileName() {
        return FileName;
    }

    public void setFileName(List<String> fileName) {
        FileName = fileName;
    }

    public boolean isDataNull() {
        return isDataNull;
    }

    public void setDataNull(boolean dataNull) {
        isDataNull = dataNull;
    }

    public ClsDataError getDataError() {
        return dataError;
    }

    public void setDataError(ClsDataError dataError) {
        this.dataError = dataError;
    }
}
