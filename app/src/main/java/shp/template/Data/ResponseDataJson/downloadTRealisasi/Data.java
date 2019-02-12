package shp.template.Data.ResponseDataJson.downloadTRealisasi;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("realisasiData")
	private List<RealisasiDataItem> realisasiData;

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
			"realisasiData = '" + realisasiData + '\'' + 
			"}";
		}
}