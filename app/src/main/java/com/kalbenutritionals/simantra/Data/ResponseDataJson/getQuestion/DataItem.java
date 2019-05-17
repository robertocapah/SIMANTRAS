package com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("INT_SEQ")
	private String iNTSEQ; //buat urutan posisi

	@SerializedName("TXT_VALIDATE_NAME")
	private String tXTVALIDATENAME;

	@SerializedName("TXT_FORM_NAME")
	private String tXTFORMNAME;

	@SerializedName("TXT_TYPE_NAME")
	private String tXTTYPENAME;

	@SerializedName("INT_DATA_VERS_ID")
	private int iNTDATAVERSID;

	@SerializedName("BIT_IMG")
	private String bITIMG;

	@SerializedName("TXT_POSITION_NAME")
	private String tXTPOSITIONNAME;

	@SerializedName("listDatIsian")
	private List<ListDatIsianItem> listDatIsian;

	@SerializedName("INT_FORM_DTL_ID")
	private int iNTFORMDTLID;

	@SerializedName("TXT_FORM_DESC")
	private String tXTFORMDESC;

	@SerializedName("INT_IMG_NEED")
	private String iNTIMGNEED;

	@SerializedName("INT_POSITION_ID")
	private int iNTPOSITIONID;

	@SerializedName("INT_FORM_VERS_ID")
	private int iNTFORMVERSID;

	@SerializedName("INT_SEQ_POSITION")
	private int iNTSEQPOSITION;

	@SerializedName("INT_VALIDATE_ID")
	private int iNTVALIDATEID;

	@SerializedName("BIT_DATA")
	private String bITDATA;

	@SerializedName("INT_TYPE_ID")
	private int iNTTYPEID;

	public void setINTSEQ(String iNTSEQ){
		this.iNTSEQ = iNTSEQ;
	}

	public String getINTSEQ(){
		return iNTSEQ;
	}

	public void setTXTVALIDATENAME(String tXTVALIDATENAME){
		this.tXTVALIDATENAME = tXTVALIDATENAME;
	}

	public String getTXTVALIDATENAME(){
		return tXTVALIDATENAME;
	}

	public void setTXTFORMNAME(String tXTFORMNAME){
		this.tXTFORMNAME = tXTFORMNAME;
	}

	public String getTXTFORMNAME(){
		return tXTFORMNAME;
	}

	public void setTXTTYPENAME(String tXTTYPENAME){
		this.tXTTYPENAME = tXTTYPENAME;
	}

	public String getTXTTYPENAME(){
		return tXTTYPENAME;
	}

	public void setINTDATAVERSID(int iNTDATAVERSID){
		this.iNTDATAVERSID = iNTDATAVERSID;
	}

	public int getINTDATAVERSID(){
		return iNTDATAVERSID;
	}

	public void setBITIMG(String bITIMG){
		this.bITIMG = bITIMG;
	}

	public String getBITIMG(){
		return bITIMG;
	}

	public void setTXTPOSITIONNAME(String tXTPOSITIONNAME){
		this.tXTPOSITIONNAME = tXTPOSITIONNAME;
	}

	public String getTXTPOSITIONNAME(){
		return tXTPOSITIONNAME;
	}

	public void setListDatIsian(List<ListDatIsianItem> listDatIsian){
		this.listDatIsian = listDatIsian;
	}

	public List<ListDatIsianItem> getListDatIsian(){
		return listDatIsian;
	}

	public void setINTFORMDTLID(int iNTFORMDTLID){
		this.iNTFORMDTLID = iNTFORMDTLID;
	}

	public int getINTFORMDTLID(){
		return iNTFORMDTLID;
	}

	public void setTXTFORMDESC(String tXTFORMDESC){
		this.tXTFORMDESC = tXTFORMDESC;
	}

	public String getTXTFORMDESC(){
		return tXTFORMDESC;
	}

	public void setINTIMGNEED(String iNTIMGNEED){
		this.iNTIMGNEED = iNTIMGNEED;
	}

	public String getINTIMGNEED(){
		return iNTIMGNEED;
	}

	public void setINTPOSITIONID(int iNTPOSITIONID){
		this.iNTPOSITIONID = iNTPOSITIONID;
	}

	public int getINTPOSITIONID(){
		return iNTPOSITIONID;
	}

	public void setINTFORMVERSID(int iNTFORMVERSID){
		this.iNTFORMVERSID = iNTFORMVERSID;
	}

	public int getINTFORMVERSID(){
		return iNTFORMVERSID;
	}

	public void setINTSEQPOSITION(int iNTSEQPOSITION){
		this.iNTSEQPOSITION = iNTSEQPOSITION;
	}

	public int getINTSEQPOSITION(){
		return iNTSEQPOSITION;
	}

	public void setINTVALIDATEID(int iNTVALIDATEID){
		this.iNTVALIDATEID = iNTVALIDATEID;
	}

	public int getINTVALIDATEID(){
		return iNTVALIDATEID;
	}

	public void setBITDATA(String bITDATA){
		this.bITDATA = bITDATA;
	}

	public String getBITDATA(){
		return bITDATA;
	}

	public void setINTTYPEID(int iNTTYPEID){
		this.iNTTYPEID = iNTTYPEID;
	}

	public int getINTTYPEID(){
		return iNTTYPEID;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"iNT_SEQ = '" + iNTSEQ + '\'' + 
			",tXT_VALIDATE_NAME = '" + tXTVALIDATENAME + '\'' + 
			",tXT_FORM_NAME = '" + tXTFORMNAME + '\'' + 
			",tXT_TYPE_NAME = '" + tXTTYPENAME + '\'' + 
			",iNT_DATA_VERS_ID = '" + iNTDATAVERSID + '\'' + 
			",bIT_IMG = '" + bITIMG + '\'' + 
			",tXT_POSITION_NAME = '" + tXTPOSITIONNAME + '\'' + 
			",listDatIsian = '" + listDatIsian + '\'' + 
			",iNT_FORM_DTL_ID = '" + iNTFORMDTLID + '\'' + 
			",tXT_FORM_DESC = '" + tXTFORMDESC + '\'' + 
			",iNT_IMG_NEED = '" + iNTIMGNEED + '\'' + 
			",iNT_POSITION_ID = '" + iNTPOSITIONID + '\'' + 
			",iNT_FORM_VERS_ID = '" + iNTFORMVERSID + '\'' + 
			",iNT_SEQ_POSITION = '" + iNTSEQPOSITION + '\'' + 
			",iNT_VALIDATE_ID = '" + iNTVALIDATEID + '\'' + 
			",bIT_DATA = '" + bITDATA + '\'' + 
			",iNT_TYPE_ID = '" + iNTTYPEID + '\'' + 
			"}";
		}
}