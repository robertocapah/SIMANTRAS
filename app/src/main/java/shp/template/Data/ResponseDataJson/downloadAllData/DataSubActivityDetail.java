package shp.template.Data.ResponseDataJson.downloadAllData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataSubActivityDetail{

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
			"DataSubActivityDetail{" + 
			"ltSubActivityDetailData = '" + ltSubActivityDetailData + '\'' + 
			"}";
		}
}