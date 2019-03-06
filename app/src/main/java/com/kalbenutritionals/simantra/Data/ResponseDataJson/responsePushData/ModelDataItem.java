package com.kalbenutritionals.simantra.Data.ResponseDataJson.responsePushData;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ModelDataItem{

	@SerializedName("modDetail")
	private ModDetail modDetail;

	@SerializedName("modStatus")
	private boolean modStatus;

	@SerializedName("modName")
	private String modName;

	public void setModDetail(ModDetail modDetail){
		this.modDetail = modDetail;
	}

	public ModDetail getModDetail(){
		return modDetail;
	}

	public void setModStatus(boolean modStatus){
		this.modStatus = modStatus;
	}

	public boolean isModStatus(){
		return modStatus;
	}

	public void setModName(String modName){
		this.modName = modName;
	}

	public String getModName(){
		return modName;
	}

	@Override
 	public String toString(){
		return 
			"ModelDataItem{" + 
			"modDetail = '" + modDetail + '\'' + 
			",modStatus = '" + modStatus + '\'' + 
			",modName = '" + modName + '\'' + 
			"}";
		}
}