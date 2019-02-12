package shp.template.Data.ResponseDataJson.downloadSubActivityDetail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("LtSubActivityDetailData")
	private List<LtSubActivityDetailDataItem> ltSubActivityDetailData;

	public void setLtSubActivityDetailData(List<LtSubActivityDetailDataItem> ltSubActivityDetailData){
		this.ltSubActivityDetailData = ltSubActivityDetailData;
	}

	public List<LtSubActivityDetailDataItem> getLtSubActivityDetailData(){
		return ltSubActivityDetailData;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltSubActivityDetailData = '" + ltSubActivityDetailData + '\'' + 
			"}";
		}
}