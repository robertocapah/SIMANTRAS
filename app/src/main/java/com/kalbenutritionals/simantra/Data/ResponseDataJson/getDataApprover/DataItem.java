package com.kalbenutritionals.simantra.Data.ResponseDataJson.getDataApprover;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("TXT_SPM_NO")
	private String tXTSPMNO;

	@SerializedName("TXT_PERIODE")
	private String tXTPERIODE;

	@SerializedName("TXT_ORG_CODE")
	private String tXTORGCODE;

	@SerializedName("INT_STATUS_ID")
	private String iNTSTATUSID;

	@SerializedName("INT_FILL_HDR_ID")
	private String iNTFILLHDRID;

	@SerializedName("INT_FORM_VERS_ID")
	private String iNTFORMVERSID;

	@SerializedName("INT_ORG_ID")
	private String iNTORGID;

	@SerializedName("BIT_ACTIVE")
	private String bITACTIVE;

	public void setTXTSPMNO(String tXTSPMNO){
		this.tXTSPMNO = tXTSPMNO;
	}

	public String getTXTSPMNO(){
		return tXTSPMNO;
	}

	public void setTXTPERIODE(String tXTPERIODE){
		this.tXTPERIODE = tXTPERIODE;
	}

	public String getTXTPERIODE(){
		return tXTPERIODE;
	}

	public void setTXTORGCODE(String tXTORGCODE){
		this.tXTORGCODE = tXTORGCODE;
	}

	public String getTXTORGCODE(){
		return tXTORGCODE;
	}

	public void setINTSTATUSID(String iNTSTATUSID){
		this.iNTSTATUSID = iNTSTATUSID;
	}

	public String getINTSTATUSID(){
		return iNTSTATUSID;
	}

	public void setINTFILLHDRID(String iNTFILLHDRID){
		this.iNTFILLHDRID = iNTFILLHDRID;
	}

	public String getINTFILLHDRID(){
		return iNTFILLHDRID;
	}

	public void setINTFORMVERSID(String iNTFORMVERSID){
		this.iNTFORMVERSID = iNTFORMVERSID;
	}

	public String getINTFORMVERSID(){
		return iNTFORMVERSID;
	}

	public void setINTORGID(String iNTORGID){
		this.iNTORGID = iNTORGID;
	}

	public String getINTORGID(){
		return iNTORGID;
	}

	public void setBITACTIVE(String bITACTIVE){
		this.bITACTIVE = bITACTIVE;
	}

	public String getBITACTIVE(){
		return bITACTIVE;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"tXT_SPM_NO = '" + tXTSPMNO + '\'' + 
			",tXT_PERIODE = '" + tXTPERIODE + '\'' + 
			",tXT_ORG_CODE = '" + tXTORGCODE + '\'' + 
			",iNT_STATUS_ID = '" + iNTSTATUSID + '\'' + 
			",iNT_FILL_HDR_ID = '" + iNTFILLHDRID + '\'' + 
			",iNT_FORM_VERS_ID = '" + iNTFORMVERSID + '\'' + 
			",iNT_ORG_ID = '" + iNTORGID + '\'' + 
			",bIT_ACTIVE = '" + bITACTIVE + '\'' + 
			"}";
		}
}