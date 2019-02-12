package shp.template.Data.ResponseDataJson.downloadSubActivityDetail;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LtSubActivityDetailDataItem{

	@SerializedName("txtDescription")
	private String txtDescription;

	@SerializedName("bitActive")
	private boolean bitActive;

	@SerializedName("intSubActivityId")
	private int intSubActivityId;

	@SerializedName("intFlag")
	private int intFlag;

	@SerializedName("intSubDetailActivityId")
	private int intSubDetailActivityId;

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

	public void setIntSubActivityId(int intSubActivityId){
		this.intSubActivityId = intSubActivityId;
	}

	public int getIntSubActivityId(){
		return intSubActivityId;
	}

	public void setIntFlag(int intFlag){
		this.intFlag = intFlag;
	}

	public int getIntFlag(){
		return intFlag;
	}

	public void setIntSubDetailActivityId(int intSubDetailActivityId){
		this.intSubDetailActivityId = intSubDetailActivityId;
	}

	public int getIntSubDetailActivityId(){
		return intSubDetailActivityId;
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
			"LtSubActivityDetailDataItem{" + 
			"txtDescription = '" + txtDescription + '\'' + 
			",bitActive = '" + bitActive + '\'' + 
			",intSubActivityId = '" + intSubActivityId + '\'' + 
			",intFlag = '" + intFlag + '\'' + 
			",intSubDetailActivityId = '" + intSubDetailActivityId + '\'' + 
			",txtTitle = '" + txtTitle + '\'' + 
			"}";
		}
}