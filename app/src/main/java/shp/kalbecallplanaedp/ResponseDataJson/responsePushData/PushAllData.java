package shp.kalbecallplanaedp.ResponseDataJson.responsePushData;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class PushAllData{

	@SerializedName("result")
	private Result result;

	@SerializedName("Data")
	private Data data;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"PushAllData{" + 
			"result = '" + result + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}