package shp.template.Data.ResponseDataJson.createUnplan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("dtAkuisisi")
	private DtAkuisisi dtAkuisisi;

	@SerializedName("dtMaintenance")
	private DtMaintenance dtMaintenance;

	@SerializedName("dataHeader")
	private DataHeader dataHeader;

	@SerializedName("dtNotification")
	private List<DtNotificationItem> dtNotification;

	public void setDtAkuisisi(DtAkuisisi dtAkuisisi){
		this.dtAkuisisi = dtAkuisisi;
	}

	public DtAkuisisi getDtAkuisisi(){
		return dtAkuisisi;
	}

	public void setDtMaintenance(DtMaintenance dtMaintenance){
		this.dtMaintenance = dtMaintenance;
	}

	public DtMaintenance getDtMaintenance(){
		return dtMaintenance;
	}

	public void setDataHeader(DataHeader dataHeader){
		this.dataHeader = dataHeader;
	}

	public DataHeader getDataHeader(){
		return dataHeader;
	}

	public void setDtNotification(List<DtNotificationItem> dtNotification){
		this.dtNotification = dtNotification;
	}

	public List<DtNotificationItem> getDtNotification(){
		return dtNotification;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"dtAkuisisi = '" + dtAkuisisi + '\'' + 
			",dtMaintenance = '" + dtMaintenance + '\'' + 
			",dataHeader = '" + dataHeader + '\'' + 
			",dtNotification = '" + dtNotification + '\'' + 
			"}";
		}
}