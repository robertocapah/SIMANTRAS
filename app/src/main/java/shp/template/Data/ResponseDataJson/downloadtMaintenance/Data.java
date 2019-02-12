package shp.template.Data.ResponseDataJson.downloadtMaintenance;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("LtMaintenanceHeader")
	private List<LtMaintenanceHeaderItem> ltMaintenanceHeader;

	@SerializedName("LtMaintenanceDetail")
	private List<LtMaintenanceDetailItem> ltMaintenanceDetail;

	public void setLtMaintenanceHeader(List<LtMaintenanceHeaderItem> ltMaintenanceHeader){
		this.ltMaintenanceHeader = ltMaintenanceHeader;
	}

	public List<LtMaintenanceHeaderItem> getLtMaintenanceHeader(){
		return ltMaintenanceHeader;
	}

	public void setLtMaintenanceDetail(List<LtMaintenanceDetailItem> ltMaintenanceDetail){
		this.ltMaintenanceDetail = ltMaintenanceDetail;
	}

	public List<LtMaintenanceDetailItem> getLtMaintenanceDetail(){
		return ltMaintenanceDetail;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltMaintenanceHeader = '" + ltMaintenanceHeader + '\'' + 
			",ltMaintenanceDetail = '" + ltMaintenanceDetail + '\'' + 
			"}";
		}
}