package com.kalbenutritionals.simantra.Data.ResponseDataJson.getMasterDokter;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataApotekItem{

	@SerializedName("code")
	private String code;

	@SerializedName("kecId")
	private String kecId;

	@SerializedName("txtPertanyaan")
	private String name;

	@SerializedName("kecName")
	private String kecName;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setKecId(String kecId){
		this.kecId = kecId;
	}

	public String getKecId(){
		return kecId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setKecName(String kecName){
		this.kecName = kecName;
	}

	public String getKecName(){
		return kecName;
	}

	@Override
 	public String toString(){
		return 
			"DataApotekItem{" + 
			"code = '" + code + '\'' + 
			",kecId = '" + kecId + '\'' + 
			",txtPertanyaan = '" + name + '\'' +
			",kecName = '" + kecName + '\'' + 
			"}";
		}
}