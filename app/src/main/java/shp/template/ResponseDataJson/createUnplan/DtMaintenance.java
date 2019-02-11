package shp.template.ResponseDataJson.createUnplan;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DtMaintenance{

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
			"DtMaintenance{" + 
			"ltMaintenanceHeader = '" + ltMaintenanceHeader + '\'' + 
			",ltMaintenanceDetail = '" + ltMaintenanceDetail + '\'' + 
			"}";
		}
}