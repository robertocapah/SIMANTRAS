package shp.kalbecallplanaedp.ResponseDataJson.downloadAllData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataActivity{

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
			"DataActivity{" + 
			"ltActivity = '" + ltActivity + '\'' + 
			"}";
		}
}