package shp.kalbecallplanaedp.Common;

/**
 * Created by Dewi Oktaviani on 12/19/2018.
 */

public class clsSpinner {
    private String txtKey;
    private String txtValue;
    private int intKey;

    public clsSpinner(String txtKey, String txtValue){
        this.txtKey = txtKey;
        this.txtValue = txtValue;
    }
    public String getTxtKey() {
        return txtKey;
    }

    public void setTxtKey(String txtKey) {
        this.txtKey = txtKey;
    }

    public String getTxtValue() {
        return txtValue;
    }

    public void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
    }

    public int getIntKey() {
        return intKey;
    }

    public void setIntKey(int intKey) {
        this.intKey = intKey;
    }
}
