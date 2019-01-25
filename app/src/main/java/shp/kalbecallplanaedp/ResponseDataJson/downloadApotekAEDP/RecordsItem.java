package shp.kalbecallplanaedp.ResponseDataJson.downloadApotekAEDP;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class RecordsItem{

	@SerializedName("code")
	private String code;

	@SerializedName("kecId")
	private String kecId;

	@SerializedName("name")
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
			"RecordsItem{" + 
			"code = '" + code + '\'' + 
			",kecId = '" + kecId + '\'' + 
			",name = '" + name + '\'' + 
			",kecName = '" + kecName + '\'' + 
			"}";
		}
}