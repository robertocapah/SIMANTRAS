package shp.kalbecallplanaedp.ResponseDataJson.downloadtMappingArea;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("ltMappingArea")
	private List<LtMappingAreaItem> ltMappingArea;

	public void setLtMappingArea(List<LtMappingAreaItem> ltMappingArea){
		this.ltMappingArea = ltMappingArea;
	}

	public List<LtMappingAreaItem> getLtMappingArea(){
		return ltMappingArea;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltMappingArea = '" + ltMappingArea + '\'' + 
			"}";
		}
}