package com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class TimeDataItem{

	@SerializedName("CREATED_BY")
	private int cREATEDBY;

	@SerializedName("LAST_UPDATED_BY")
	private int lASTUPDATEDBY;

	@SerializedName("INT_FILL_HDR_ID")
	private int iNTFILLHDRID;

	@SerializedName("TXT_DESC")
	private String tXTDESC;

	@SerializedName("CREATION_DATE")
	private String cREATIONDATE;

	@SerializedName("XXSHP_SMT_T_FILL_HDR")
	private Object xXSHPSMTTFILLHDR;

	@SerializedName("INT_FILL_TIME_ID")
	private int iNTFILLTIMEID;

	@SerializedName("LAST_UPDATE_LOGIN")
	private int lASTUPDATELOGIN;

	@SerializedName("INT_DESC")
	private int iNTDESC;

	@SerializedName("DT_TIME")
	private String dTTIME;

	@SerializedName("LAST_UPDATE_DATE")
	private String lASTUPDATEDATE;

	@SerializedName("INT_USER_ID")
	private int iNTUSERID;

	public void setCREATEDBY(int cREATEDBY){
		this.cREATEDBY = cREATEDBY;
	}

	public int getCREATEDBY(){
		return cREATEDBY;
	}

	public void setLASTUPDATEDBY(int lASTUPDATEDBY){
		this.lASTUPDATEDBY = lASTUPDATEDBY;
	}

	public int getLASTUPDATEDBY(){
		return lASTUPDATEDBY;
	}

	public void setINTFILLHDRID(int iNTFILLHDRID){
		this.iNTFILLHDRID = iNTFILLHDRID;
	}

	public int getINTFILLHDRID(){
		return iNTFILLHDRID;
	}

	public void setTXTDESC(String tXTDESC){
		this.tXTDESC = tXTDESC;
	}

	public String getTXTDESC(){
		return tXTDESC;
	}

	public void setCREATIONDATE(String cREATIONDATE){
		this.cREATIONDATE = cREATIONDATE;
	}

	public String getCREATIONDATE(){
		return cREATIONDATE;
	}

	public void setXXSHPSMTTFILLHDR(Object xXSHPSMTTFILLHDR){
		this.xXSHPSMTTFILLHDR = xXSHPSMTTFILLHDR;
	}

	public Object getXXSHPSMTTFILLHDR(){
		return xXSHPSMTTFILLHDR;
	}

	public void setINTFILLTIMEID(int iNTFILLTIMEID){
		this.iNTFILLTIMEID = iNTFILLTIMEID;
	}

	public int getINTFILLTIMEID(){
		return iNTFILLTIMEID;
	}

	public void setLASTUPDATELOGIN(int lASTUPDATELOGIN){
		this.lASTUPDATELOGIN = lASTUPDATELOGIN;
	}

	public int getLASTUPDATELOGIN(){
		return lASTUPDATELOGIN;
	}

	public void setINTDESC(int iNTDESC){
		this.iNTDESC = iNTDESC;
	}

	public int getINTDESC(){
		return iNTDESC;
	}

	public void setDTTIME(String dTTIME){
		this.dTTIME = dTTIME;
	}

	public String getDTTIME(){
		return dTTIME;
	}

	public void setLASTUPDATEDATE(String lASTUPDATEDATE){
		this.lASTUPDATEDATE = lASTUPDATEDATE;
	}

	public String getLASTUPDATEDATE(){
		return lASTUPDATEDATE;
	}

	public void setINTUSERID(int iNTUSERID){
		this.iNTUSERID = iNTUSERID;
	}

	public int getINTUSERID(){
		return iNTUSERID;
	}

	@Override
 	public String toString(){
		return 
			"TimeDataItem{" + 
			"cREATED_BY = '" + cREATEDBY + '\'' + 
			",lAST_UPDATED_BY = '" + lASTUPDATEDBY + '\'' + 
			",iNT_FILL_HDR_ID = '" + iNTFILLHDRID + '\'' + 
			",tXT_DESC = '" + tXTDESC + '\'' + 
			",cREATION_DATE = '" + cREATIONDATE + '\'' + 
			",xXSHP_SMT_T_FILL_HDR = '" + xXSHPSMTTFILLHDR + '\'' + 
			",iNT_FILL_TIME_ID = '" + iNTFILLTIMEID + '\'' + 
			",lAST_UPDATE_LOGIN = '" + lASTUPDATELOGIN + '\'' + 
			",iNT_DESC = '" + iNTDESC + '\'' + 
			",dT_TIME = '" + dTTIME + '\'' + 
			",lAST_UPDATE_DATE = '" + lASTUPDATEDATE + '\'' + 
			",iNT_USER_ID = '" + iNTUSERID + '\'' + 
			"}";
		}
}