package shp.template.ResponseDataJson.downloadAllData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataAkuisisiData{

	@SerializedName("akuisisiDetail")
	private List<AkuisisiDetailItem> akuisisiDetail;

	@SerializedName("akuisisiHeader")
	private List<AkuisisiHeaderItem> akuisisiHeader;

	public void setAkuisisiDetail(List<AkuisisiDetailItem> akuisisiDetail){
		this.akuisisiDetail = akuisisiDetail;
	}

	public List<AkuisisiDetailItem> getAkuisisiDetail(){
		return akuisisiDetail;
	}

	public void setAkuisisiHeader(List<AkuisisiHeaderItem> akuisisiHeader){
		this.akuisisiHeader = akuisisiHeader;
	}

	public List<AkuisisiHeaderItem> getAkuisisiHeader(){
		return akuisisiHeader;
	}

	@Override
 	public String toString(){
		return 
			"DataAkuisisiData{" + 
			"akuisisiDetail = '" + akuisisiDetail + '\'' + 
			",akuisisiHeader = '" + akuisisiHeader + '\'' + 
			"}";
		}
}