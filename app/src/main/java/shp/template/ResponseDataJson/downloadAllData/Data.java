package shp.template.ResponseDataJson.downloadAllData;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("dataSubActivityDetail")
	private DataSubActivityDetail dataSubActivityDetail;

	@SerializedName("dataSubActivity")
	private DataSubActivity dataSubActivity;

	@SerializedName("dataMappingArea")
	private DataMappingArea dataMappingArea;

	@SerializedName("dataMaintenanceData")
	private DataMaintenanceData dataMaintenanceData;

	@SerializedName("dataInfoProgram")
	private DataInfoProgram dataInfoProgram;

	@SerializedName("dataNotification")
	private DataNotification dataNotification;

	@SerializedName("dataActivity")
	private DataActivity dataActivity;

	@SerializedName("datatProgramVisitDetail_")
	private DatatProgramVisitDetail datatProgramVisitDetail;

	@SerializedName("dataAkuisisiData")
	private DataAkuisisiData dataAkuisisiData;

	public void setDataSubActivityDetail(DataSubActivityDetail dataSubActivityDetail){
		this.dataSubActivityDetail = dataSubActivityDetail;
	}

	public DataSubActivityDetail getDataSubActivityDetail(){
		return dataSubActivityDetail;
	}

	public void setDataSubActivity(DataSubActivity dataSubActivity){
		this.dataSubActivity = dataSubActivity;
	}

	public DataSubActivity getDataSubActivity(){
		return dataSubActivity;
	}

	public void setDataMappingArea(DataMappingArea dataMappingArea){
		this.dataMappingArea = dataMappingArea;
	}

	public DataMappingArea getDataMappingArea(){
		return dataMappingArea;
	}

	public void setDataMaintenanceData(DataMaintenanceData dataMaintenanceData){
		this.dataMaintenanceData = dataMaintenanceData;
	}

	public DataMaintenanceData getDataMaintenanceData(){
		return dataMaintenanceData;
	}

	public void setDataInfoProgram(DataInfoProgram dataInfoProgram){
		this.dataInfoProgram = dataInfoProgram;
	}

	public DataInfoProgram getDataInfoProgram(){
		return dataInfoProgram;
	}

	public void setDataNotification(DataNotification dataNotification){
		this.dataNotification = dataNotification;
	}

	public DataNotification getDataNotification(){
		return dataNotification;
	}

	public void setDataActivity(DataActivity dataActivity){
		this.dataActivity = dataActivity;
	}

	public DataActivity getDataActivity(){
		return dataActivity;
	}

	public void setDatatProgramVisitDetail(DatatProgramVisitDetail datatProgramVisitDetail){
		this.datatProgramVisitDetail = datatProgramVisitDetail;
	}

	public DatatProgramVisitDetail getDatatProgramVisitDetail(){
		return datatProgramVisitDetail;
	}

	public void setDataAkuisisiData(DataAkuisisiData dataAkuisisiData){
		this.dataAkuisisiData = dataAkuisisiData;
	}

	public DataAkuisisiData getDataAkuisisiData(){
		return dataAkuisisiData;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"dataSubActivityDetail = '" + dataSubActivityDetail + '\'' + 
			",dataSubActivity = '" + dataSubActivity + '\'' + 
			",dataMappingArea = '" + dataMappingArea + '\'' + 
			",dataMaintenanceData = '" + dataMaintenanceData + '\'' + 
			",dataInfoProgram = '" + dataInfoProgram + '\'' + 
			",dataNotification = '" + dataNotification + '\'' + 
			",dataActivity = '" + dataActivity + '\'' + 
			",datatProgramVisitDetail_ = '" + datatProgramVisitDetail + '\'' + 
			",dataAkuisisiData = '" + dataAkuisisiData + '\'' + 
			"}";
		}
}