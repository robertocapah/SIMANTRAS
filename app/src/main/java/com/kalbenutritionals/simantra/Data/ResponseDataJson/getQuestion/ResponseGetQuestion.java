package com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseGetQuestion{

	@SerializedName("result")
	private Result result;

	@SerializedName("data")
	private List<DataItem> data;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetQuestion{" + 
			"result = '" + result + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}