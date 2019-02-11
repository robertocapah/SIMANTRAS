package shp.template.ResponseDataJson.responsePushData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("ModelData")
	private List<ModelDataItem> modelData;

	public void setModelData(List<ModelDataItem> modelData){
		this.modelData = modelData;
	}

	public List<ModelDataItem> getModelData(){
		return modelData;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"modelData = '" + modelData + '\'' + 
			"}";
		}
}