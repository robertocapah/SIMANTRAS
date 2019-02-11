package shp.template.ResponseDataJson.downloadtAkuisisi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

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
			"Data{" + 
			"akuisisiDetail = '" + akuisisiDetail + '\'' + 
			",akuisisiHeader = '" + akuisisiHeader + '\'' + 
			"}";
		}
}