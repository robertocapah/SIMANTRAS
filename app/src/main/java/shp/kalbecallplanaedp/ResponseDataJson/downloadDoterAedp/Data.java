package shp.kalbecallplanaedp.ResponseDataJson.downloadDoterAedp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("records")
	private List<RecordsItem> records;

	public void setRecords(List<RecordsItem> records){
		this.records = records;
	}

	public List<RecordsItem> getRecords(){
		return records;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"records = '" + records + '\'' + 
			"}";
		}
}