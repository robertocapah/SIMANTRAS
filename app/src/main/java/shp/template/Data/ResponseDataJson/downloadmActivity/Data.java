package shp.template.Data.ResponseDataJson.downloadmActivity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("LtActivity")
	private List<LtActivityItem> ltActivity;

	public void setLtActivity(List<LtActivityItem> ltActivity){
		this.ltActivity = ltActivity;
	}

	public List<LtActivityItem> getLtActivity(){
		return ltActivity;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltActivity = '" + ltActivity + '\'' + 
			"}";
		}
}