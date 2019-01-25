package shp.kalbecallplanaedp.ResponseDataJson.downloadtMaintenance;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LtMaintenanceHeaderItem{

	@SerializedName("intUserId")
	private int intUserId;

	@SerializedName("intRoleId")
	private int intRoleId;

	@SerializedName("intDokterId")
	private String intDokterId;

	@SerializedName("intAreaId")
	private String intAreaId;

	@SerializedName("intApotekId")
	private String intApotekId;

	@SerializedName("intActivityId")
	private int intActivityId;

	@SerializedName("txtRealisasiVisitId")
	private String txtRealisasiVisitId;

	@SerializedName("txtMaintenanceHeaderId")
	private String txtMaintenanceHeaderId;

	public void setIntUserId(int intUserId){
		this.intUserId = intUserId;
	}

	public int getIntUserId(){
		return intUserId;
	}

	public void setIntRoleId(int intRoleId){
		this.intRoleId = intRoleId;
	}

	public int getIntRoleId(){
		return intRoleId;
	}

	public void setIntDokterId(String intDokterId){
		this.intDokterId = intDokterId;
	}

	public String getIntDokterId(){
		return intDokterId;
	}

	public void setIntAreaId(String intAreaId){
		this.intAreaId = intAreaId;
	}

	public String getIntAreaId(){
		return intAreaId;
	}

	public void setIntApotekId(String intApotekId){
		this.intApotekId = intApotekId;
	}

	public String getIntApotekId(){
		return intApotekId;
	}

	public void setIntActivityId(int intActivityId){
		this.intActivityId = intActivityId;
	}

	public int getIntActivityId(){
		return intActivityId;
	}

	public void setTxtRealisasiVisitId(String txtRealisasiVisitId){
		this.txtRealisasiVisitId = txtRealisasiVisitId;
	}

	public String getTxtRealisasiVisitId(){
		return txtRealisasiVisitId;
	}

	public void setTxtMaintenanceHeaderId(String txtMaintenanceHeaderId){
		this.txtMaintenanceHeaderId = txtMaintenanceHeaderId;
	}

	public String getTxtMaintenanceHeaderId(){
		return txtMaintenanceHeaderId;
	}

	@Override
 	public String toString(){
		return 
			"LtMaintenanceHeaderItem{" + 
			"intUserId = '" + intUserId + '\'' + 
			",intRoleId = '" + intRoleId + '\'' + 
			",intDokterId = '" + intDokterId + '\'' + 
			",intAreaId = '" + intAreaId + '\'' + 
			",intApotekId = '" + intApotekId + '\'' + 
			",intActivityId = '" + intActivityId + '\'' + 
			",txtRealisasiVisitId = '" + txtRealisasiVisitId + '\'' + 
			",txtMaintenanceHeaderId = '" + txtMaintenanceHeaderId + '\'' + 
			"}";
		}
}