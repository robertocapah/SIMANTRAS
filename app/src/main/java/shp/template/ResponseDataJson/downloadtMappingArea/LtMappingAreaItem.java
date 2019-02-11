package shp.template.ResponseDataJson.downloadtMappingArea;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LtMappingAreaItem{

	@SerializedName("intUserId")
	private int intUserId;

	@SerializedName("bitActive")
	private boolean bitActive;

	@SerializedName("txtKecamatanID")
	private String txtKecamatanID;

	@SerializedName("intUserMappingAreaId")
	private int intUserMappingAreaId;

	public void setIntUserId(int intUserId){
		this.intUserId = intUserId;
	}

	public int getIntUserId(){
		return intUserId;
	}

	public void setBitActive(boolean bitActive){
		this.bitActive = bitActive;
	}

	public boolean isBitActive(){
		return bitActive;
	}

	public void setTxtKecamatanID(String txtKecamatanID){
		this.txtKecamatanID = txtKecamatanID;
	}

	public String getTxtKecamatanID(){
		return txtKecamatanID;
	}

	public void setIntUserMappingAreaId(int intUserMappingAreaId){
		this.intUserMappingAreaId = intUserMappingAreaId;
	}

	public int getIntUserMappingAreaId(){
		return intUserMappingAreaId;
	}

	@Override
 	public String toString(){
		return 
			"LtMappingAreaItem{" + 
			"intUserId = '" + intUserId + '\'' + 
			",bitActive = '" + bitActive + '\'' + 
			",txtKecamatanID = '" + txtKecamatanID + '\'' + 
			",intUserMappingAreaId = '" + intUserMappingAreaId + '\'' + 
			"}";
		}
}