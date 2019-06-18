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

	@SerializedName("INT_FILL_HDR_ID")
	private int iNTFILLHDRID;

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

	public void setINTFILLHDRID(int iNTFILLHDRID){
		this.iNTFILLHDRID = iNTFILLHDRID;
	}

	public int getINTFILLHDRID(){
		return iNTFILLHDRID;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetQuestion{" + 
			"result = '" + result + '\'' + 
			",data = '" + data + '\'' + 
			",iNT_FILL_HDR_ID = '" + iNTFILLHDRID + '\'' + 
			"}";
		}
}