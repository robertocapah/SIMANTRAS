package shp.template.ResponseDataJson.downloadtProgramVisit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("tProgramVisit")
	private List<TProgramVisitItem> tProgramVisit;

	@SerializedName("tProgramVisitSubActivity")
	private List<TProgramVisitSubActivityItem> tProgramVisitSubActivity;

	@SerializedName("realisasiData")
	private List<RealisasiDataItem> realisasiData;

	public void setTProgramVisit(List<TProgramVisitItem> tProgramVisit){
		this.tProgramVisit = tProgramVisit;
	}

	public List<TProgramVisitItem> getTProgramVisit(){
		return tProgramVisit;
	}

	public void setTProgramVisitSubActivity(List<TProgramVisitSubActivityItem> tProgramVisitSubActivity){
		this.tProgramVisitSubActivity = tProgramVisitSubActivity;
	}

	public List<TProgramVisitSubActivityItem> getTProgramVisitSubActivity(){
		return tProgramVisitSubActivity;
	}

	public void setRealisasiData(List<RealisasiDataItem> realisasiData){
		this.realisasiData = realisasiData;
	}

	public List<RealisasiDataItem> getRealisasiData(){
		return realisasiData;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"tProgramVisit = '" + tProgramVisit + '\'' + 
			",tProgramVisitSubActivity = '" + tProgramVisitSubActivity + '\'' + 
			",realisasiData = '" + realisasiData + '\'' + 
			"}";
		}
}