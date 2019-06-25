package com.kalbenutritionals.simantra.Data.ResponseDataJson.getDataApprover;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseGetApprover{

	@SerializedName("data")
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetApprover{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}