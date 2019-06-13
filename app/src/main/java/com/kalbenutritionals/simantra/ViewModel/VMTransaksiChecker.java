package com.kalbenutritionals.simantra.ViewModel;

import java.util.List;

public class VMTransaksiChecker {
    public DeviceInfo deviceInfo;
    public DatatTransaksi datatTransaksi;
    public DatatTransaksiDetail datatTransaksiDetail;
    public DataTransaksiDetailImage dataTransaksiDetailImage;

    public DatatTransaksiDetail getDatatTransaksiDetail() {
        return datatTransaksiDetail;
    }

    public void setDatatTransaksiDetail(DatatTransaksiDetail datatTransaksiDetail) {
        this.datatTransaksiDetail = datatTransaksiDetail;
    }

    public DataTransaksiDetailImage getDataTransaksiDetailImage() {
        return dataTransaksiDetailImage;
    }

    public void setDataTransaksiDetailImage(DataTransaksiDetailImage dataTransaksiDetailImage) {
        this.dataTransaksiDetailImage = dataTransaksiDetailImage;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public DatatTransaksi getDatatTransaksi() {
        return datatTransaksi;
    }

    public void setDatatTransaksi(DatatTransaksi datatTransaksi) {
        this.datatTransaksi = datatTransaksi;
    }

    public class DatatTransaksi {
        private String INT_FILL_HDR_ID;
        private String TXT_PERIODE;
        private String INT_FORM_VERS_ID;
        private String INT_ORG_ID;
        private String TXT_ORG_CODE;
        private String INT_STATUS_ID;
        private String BIT_ACTIVE;
        private String TXT_SPM_NO;
        private DatatTransaksiDetail DataDetail;

        public DatatTransaksiDetail getDataDetail() {
            return DataDetail;
        }

        public void setDataDetail(DatatTransaksiDetail dataDetail) {
            DataDetail = dataDetail;
        }

        public String getTXT_SPM_NO() {
            return TXT_SPM_NO;
        }

        public void setTXT_SPM_NO(String TXT_SPM_NO) {
            this.TXT_SPM_NO = TXT_SPM_NO;
        }

        public String getINT_FILL_HDR_ID() {
            return INT_FILL_HDR_ID;
        }

        public void setINT_FILL_HDR_ID(String INT_FILL_HDR_ID) {
            this.INT_FILL_HDR_ID = INT_FILL_HDR_ID;
        }

        public String getTXT_PERIODE() {
            return TXT_PERIODE;
        }

        public void setTXT_PERIODE(String TXT_PERIODE) {
            this.TXT_PERIODE = TXT_PERIODE;
        }

        public String getINT_FORM_VERS_ID() {
            return INT_FORM_VERS_ID;
        }

        public void setINT_FORM_VERS_ID(String INT_FORM_VERS_ID) {
            this.INT_FORM_VERS_ID = INT_FORM_VERS_ID;
        }

        public String getINT_ORG_ID() {
            return INT_ORG_ID;
        }

        public void setINT_ORG_ID(String INT_ORG_ID) {
            this.INT_ORG_ID = INT_ORG_ID;
        }

        public String getTXT_ORG_CODE() {
            return TXT_ORG_CODE;
        }

        public void setTXT_ORG_CODE(String TXT_ORG_CODE) {
            this.TXT_ORG_CODE = TXT_ORG_CODE;
        }

        public String getINT_STATUS_ID() {
            return INT_STATUS_ID;
        }

        public void setINT_STATUS_ID(String INT_STATUS_ID) {
            this.INT_STATUS_ID = INT_STATUS_ID;
        }

        public String getBIT_ACTIVE() {
            return BIT_ACTIVE;
        }

        public void setBIT_ACTIVE(String BIT_ACTIVE) {
            this.BIT_ACTIVE = BIT_ACTIVE;
        }
    }
    public class DatatTransaksiDetail {
        private String INT_FILL_DTL_ID;
        private String INT_FILL_HDR_ID;
        private String INT_FORM_DTL_ID;
        private String INT_VALUE_ID;
        private String TXT_VALUE;
        private List<DataTransaksiDetailImage> Detail_Image;

        public List<DataTransaksiDetailImage> getDetail_Image() {
            return Detail_Image;
        }

        public void setDetail_Image(List<DataTransaksiDetailImage> detail_Image) {
            Detail_Image = detail_Image;
        }

        public String getINT_FILL_DTL_ID() {
            return INT_FILL_DTL_ID;
        }

        public void setINT_FILL_DTL_ID(String INT_FILL_DTL_ID) {
            this.INT_FILL_DTL_ID = INT_FILL_DTL_ID;
        }

        public String getINT_FILL_HDR_ID() {
            return INT_FILL_HDR_ID;
        }

        public void setINT_FILL_HDR_ID(String INT_FILL_HDR_ID) {
            this.INT_FILL_HDR_ID = INT_FILL_HDR_ID;
        }

        public String getINT_FORM_DTL_ID() {
            return INT_FORM_DTL_ID;
        }

        public void setINT_FORM_DTL_ID(String INT_FORM_DTL_ID) {
            this.INT_FORM_DTL_ID = INT_FORM_DTL_ID;
        }

        public String getINT_VALUE_ID() {
            return INT_VALUE_ID;
        }

        public void setINT_VALUE_ID(String INT_VALUE_ID) {
            this.INT_VALUE_ID = INT_VALUE_ID;
        }

        public String getTXT_VALUE() {
            return TXT_VALUE;
        }

        public void setTXT_VALUE(String TXT_VALUE) {
            this.TXT_VALUE = TXT_VALUE;
        }
    }
    public class DataTransaksiDetailImage{
        private String INT_FILL_DTL_IMG_ID;
        private String INT_FILL_DTL_ID;
        private String TXT_ORI_FILE_NAME;
        private String TXT_CONTENT_TYPE;

        public String getINT_FILL_DTL_IMG_ID() {
            return INT_FILL_DTL_IMG_ID;
        }

        public void setINT_FILL_DTL_IMG_ID(String INT_FILL_DTL_IMG_ID) {
            this.INT_FILL_DTL_IMG_ID = INT_FILL_DTL_IMG_ID;
        }

        public String getINT_FILL_DTL_ID() {
            return INT_FILL_DTL_ID;
        }

        public void setINT_FILL_DTL_ID(String INT_FILL_DTL_ID) {
            this.INT_FILL_DTL_ID = INT_FILL_DTL_ID;
        }

        public String getTXT_ORI_FILE_NAME() {
            return TXT_ORI_FILE_NAME;
        }

        public void setTXT_ORI_FILE_NAME(String TXT_ORI_FILE_NAME) {
            this.TXT_ORI_FILE_NAME = TXT_ORI_FILE_NAME;
        }

        public String getTXT_CONTENT_TYPE() {
            return TXT_CONTENT_TYPE;
        }

        public void setTXT_CONTENT_TYPE(String TXT_CONTENT_TYPE) {
            this.TXT_CONTENT_TYPE = TXT_CONTENT_TYPE;
        }
    }
}
