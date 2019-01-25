package shp.kalbecallplanaedp.ResponseDataJson.createUnplan;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LtMaintenanceDetailItem{

	@SerializedName("txtNoDocument")
	private String txtNoDocument;

	@SerializedName("txtMaintenanceDetailId")
	private String txtMaintenanceDetailId;

	@SerializedName("txtMaintenanceHeaderId")
	private String txtMaintenanceHeaderId;

	@SerializedName("intSubDetailActivityId")
	private int intSubDetailActivityId;

	public void setTxtNoDocument(String txtNoDocument){
		this.txtNoDocument = txtNoDocument;
	}

	public String getTxtNoDocument(){
		return txtNoDocument;
	}

	public void setTxtMaintenanceDetailId(String txtMaintenanceDetailId){
		this.txtMaintenanceDetailId = txtMaintenanceDetailId;
	}

	public String getTxtMaintenanceDetailId(){
		return txtMaintenanceDetailId;
	}

	public void setTxtMaintenanceHeaderId(String txtMaintenanceHeaderId){
		this.txtMaintenanceHeaderId = txtMaintenanceHeaderId;
	}

	public String getTxtMaintenanceHeaderId(){
		return txtMaintenanceHeaderId;
	}

	public void setIntSubDetailActivityId(int intSubDetailActivityId){
		this.intSubDetailActivityId = intSubDetailActivityId;
	}

	public int getIntSubDetailActivityId(){
		return intSubDetailActivityId;
	}

	@Override
 	public String toString(){
		return 
			"LtMaintenanceDetailItem{" + 
			"txtNoDocument = '" + txtNoDocument + '\'' + 
			",txtMaintenanceDetailId = '" + txtMaintenanceDetailId + '\'' + 
			",txtMaintenanceHeaderId = '" + txtMaintenanceHeaderId + '\'' + 
			",intSubDetailActivityId = '" + intSubDetailActivityId + '\'' + 
			"}";
		}
}