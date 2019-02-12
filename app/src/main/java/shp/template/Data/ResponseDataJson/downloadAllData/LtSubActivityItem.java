package shp.template.Data.ResponseDataJson.downloadAllData;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LtSubActivityItem{

	@SerializedName("txtDescription")
	private String txtDescription;

	@SerializedName("bitActive")
	private boolean bitActive;

	@SerializedName("intActivityId")
	private int intActivityId;

	@SerializedName("intSubActivityId")
	private int intSubActivityId;

	@SerializedName("txtTitle")
	private String txtTitle;

	public void setTxtDescription(String txtDescription){
		this.txtDescription = txtDescription;
	}

	public String getTxtDescription(){
		return txtDescription;
	}

	public void setBitActive(boolean bitActive){
		this.bitActive = bitActive;
	}

	public boolean isBitActive(){
		return bitActive;
	}

	public void setIntActivityId(int intActivityId){
		this.intActivityId = intActivityId;
	}

	public int getIntActivityId(){
		return intActivityId;
	}

	public void setIntSubActivityId(int intSubActivityId){
		this.intSubActivityId = intSubActivityId;
	}

	public int getIntSubActivityId(){
		return intSubActivityId;
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
			"LtSubActivityItem{" + 
			"txtDescription = '" + txtDescription + '\'' + 
			",bitActive = '" + bitActive + '\'' + 
			",intActivityId = '" + intActivityId + '\'' + 
			",intSubActivityId = '" + intSubActivityId + '\'' + 
			",txtTitle = '" + txtTitle + '\'' + 
			"}";
		}
}