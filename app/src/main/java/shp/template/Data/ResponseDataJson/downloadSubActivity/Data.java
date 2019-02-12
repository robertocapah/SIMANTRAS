package shp.template.Data.ResponseDataJson.downloadSubActivity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("ltSubActivity")
	private List<LtSubActivityItem> ltSubActivity;

	public void setLtSubActivity(List<LtSubActivityItem> ltSubActivity){
		this.ltSubActivity = ltSubActivity;
	}

	public List<LtSubActivityItem> getLtSubActivity(){
		return ltSubActivity;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltSubActivity = '" + ltSubActivity + '\'' + 
			"}";
		}
}