package shp.template.Data.ResponseDataJson.downloadAllData;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class DataVMActivityItem{

	@SerializedName("txtDescription")
	private String txtDescription;

	@SerializedName("intActivityId")
	private int intActivityId;

	@SerializedName("intGroupActivityId")
	private int intGroupActivityId;

	@SerializedName("txtTitle")
	private String txtTitle;

	public void setTxtDescription(String txtDescription){
		this.txtDescription = txtDescription;
	}

	public String getTxtDescription(){
		return txtDescription;
	}

	public void setIntActivityId(int intActivityId){
		this.intActivityId = intActivityId;
	}

	public int getIntActivityId(){
		return intActivityId;
	}

	public void setIntGroupActivityId(int intGroupActivityId){
		this.intGroupActivityId = intGroupActivityId;
	}

	public int getIntGroupActivityId(){
		return intGroupActivityId;
	}

	public void setTxtTitle(String txtTitle){
		this.txtTitle = txtTitle;
	}

	public String getTxtTitle(){
		return txtTitle;
	}

	@Override
 	public String toString(){
		return 
			"DataVMActivityItem{" + 
			"txtDescription = '" + txtDescription + '\'' + 
			",intActivityId = '" + intActivityId + '\'' + 
			",intGroupActivityId = '" + intGroupActivityId + '\'' + 
			",txtTitle = '" + txtTitle + '\'' + 
			"}";
		}
}