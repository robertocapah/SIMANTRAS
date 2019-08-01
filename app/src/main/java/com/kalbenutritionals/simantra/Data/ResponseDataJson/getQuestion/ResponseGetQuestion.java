package com.kalbenutritionals.simantra.Data.ResponseDataJson.getQuestion;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseGetQuestion{

	@SerializedName("result")
	private Result result;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("INT_FILL_HDR_ID")
	private int iNTFILLHDRID;

	@SerializedName("txtLoadingMessage")
	private String txtLoadingMessage;

	@SerializedName("txtQRCode")
	private String txtQRCode;

	@SerializedName("TimeData")
	private List<TimeDataItem> timeData;

	@SerializedName("INT_DESC")
	private int iNTDESC;

	@SerializedName("dataIssue")
	private DataIssue dataIssue;

	public String getTxtLoadingMessage() {
		return txtLoadingMessage;
	}

	public void setTxtLoadingMessage(String txtLoadingMessage) {
		this.txtLoadingMessage = txtLoadingMessage;
	}

	public String getTxtQRCode() {
		return txtQRCode;
	}

	public void setTxtQRCode(String txtQRCode) {
		this.txtQRCode = txtQRCode;
	}

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setINTFILLHDRID(int iNTFILLHDRID){
		this.iNTFILLHDRID = iNTFILLHDRID;
	}

	public int getINTFILLHDRID(){
		return iNTFILLHDRID;
	}

	public void setTimeData(List<TimeDataItem> timeData){
		this.timeData = timeData;
	}

	public List<TimeDataItem> getTimeData(){
		return timeData;
	}

	public void setINTDESC(int iNTDESC){
		this.iNTDESC = iNTDESC;
	}

	public int getINTDESC(){
		return iNTDESC;
	}

	public void setDataIssue(DataIssue dataIssue){
		this.dataIssue = dataIssue;
	}

	public DataIssue getDataIssue(){
		return dataIssue;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetQuestion{" + 
			"result = '" + result + '\'' + 
			",data = '" + data + '\'' + 
			",iNT_FILL_HDR_ID = '" + iNTFILLHDRID + '\'' + 
			",timeData = '" + timeData + '\'' + 
			",iNT_DESC = '" + iNTDESC + '\'' + 
			",dataIssue = '" + dataIssue + '\'' + 
			"}";
		}
}