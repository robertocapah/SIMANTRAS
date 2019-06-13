package com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ListDatIsianItem{

	@SerializedName("INT_DATA_DTL_ID")
	private int iNTDATADTLID;

	@SerializedName("INT_VALUE_ID")
	private int iNTVALUEID;

	@SerializedName("TXT_MAP_COL")
	private String tXTMAPCOL;

	@SerializedName("INT_DATA_VERS_ID")
	private int iNTDATAVERSID;

	@SerializedName("TXT_VALUE")
	private String tXTVALUE;

	@SerializedName("TXT_DATA_DTL_ID")
	private String tXTDATADTLID;

	@SerializedName("BIT_FREE_TEXT")
	private String bITFREETEXT;

	public void setINTDATADTLID(int iNTDATADTLID){
		this.iNTDATADTLID = iNTDATADTLID;
	}

	public int getINTDATADTLID(){
		return iNTDATADTLID;
	}

	public void setINTVALUEID(int iNTVALUEID){
		this.iNTVALUEID = iNTVALUEID;
	}

	public int getINTVALUEID(){
		return iNTVALUEID;
	}

	public void setTXTMAPCOL(String tXTMAPCOL){
		this.tXTMAPCOL = tXTMAPCOL;
	}

	public String getTXTMAPCOL(){
		return tXTMAPCOL;
	}

	public void setINTDATAVERSID(int iNTDATAVERSID){
		this.iNTDATAVERSID = iNTDATAVERSID;
	}

	public int getINTDATAVERSID(){
		return iNTDATAVERSID;
	}

	public void setTXTVALUE(String tXTVALUE){
		this.tXTVALUE = tXTVALUE;
	}

	public String getTXTVALUE(){
		return tXTVALUE;
	}

	public void setTXTDATADTLID(String tXTDATADTLID){
		this.tXTDATADTLID = tXTDATADTLID;
	}

	public String getTXTDATADTLID(){
		return tXTDATADTLID;
	}

	public void setBITFREETEXT(String bITFREETEXT){
		this.bITFREETEXT = bITFREETEXT;
	}

	public String getBITFREETEXT(){
		return bITFREETEXT;
	}

	@Override
 	public String toString(){
		return 
			"ListDatIsianItem{" + 
			"iNT_DATA_DTL_ID = '" + iNTDATADTLID + '\'' + 
			",iNT_VALUE_ID = '" + iNTVALUEID + '\'' + 
			",tXT_MAP_COL = '" + tXTMAPCOL + '\'' + 
			",iNT_DATA_VERS_ID = '" + iNTDATAVERSID + '\'' + 
			",tXT_VALUE = '" + tXTVALUE + '\'' + 
			",tXT_DATA_DTL_ID = '" + tXTDATADTLID + '\'' + 
			",bIT_FREE_TEXT = '" + bITFREETEXT + '\'' + 
			"}";
		}
}