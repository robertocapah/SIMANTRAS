package shp.template.Data.ResponseDataJson.downloadAllData;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LtInfoDetailItem{

	@SerializedName("txtInfoProgramDetailId")
	private String txtInfoProgramDetailId;

	@SerializedName("dtDateChecklist")
	private String dtDateChecklist;

	@SerializedName("intFileAttachmentId")
	private int intFileAttachmentId;

	@SerializedName("txtInfoProgramHeaderId")
	private String txtInfoProgramHeaderId;

	@SerializedName("intSubDetailActivityId")
	private int intSubDetailActivityId;

	@SerializedName("bitCheck")
	private boolean bitCheck;

	public void setTxtInfoProgramDetailId(String txtInfoProgramDetailId){
		this.txtInfoProgramDetailId = txtInfoProgramDetailId;
	}

	public String getTxtInfoProgramDetailId(){
		return txtInfoProgramDetailId;
	}

	public void setDtDateChecklist(String dtDateChecklist){
		this.dtDateChecklist = dtDateChecklist;
	}

	public String getDtDateChecklist(){
		return dtDateChecklist;
	}

	public void setIntFileAttachmentId(int intFileAttachmentId){
		this.intFileAttachmentId = intFileAttachmentId;
	}

	public int getIntFileAttachmentId(){
		return intFileAttachmentId;
	}

	public void setTxtInfoProgramHeaderId(String txtInfoProgramHeaderId){
		this.txtInfoProgramHeaderId = txtInfoProgramHeaderId;
	}

	public String getTxtInfoProgramHeaderId(){
		return txtInfoProgramHeaderId;
	}

	public void setIntSubDetailActivityId(int intSubDetailActivityId){
		this.intSubDetailActivityId = intSubDetailActivityId;
	}

	public int getIntSubDetailActivityId(){
		return intSubDetailActivityId;
	}

	public void setBitCheck(boolean bitCheck){
		this.bitCheck = bitCheck;
	}

	public boolean isBitCheck(){
		return bitCheck;
	}

	@Override
 	public String toString(){
		return 
			"LtInfoDetailItem{" + 
			"txtInfoProgramDetailId = '" + txtInfoProgramDetailId + '\'' + 
			",dtDateChecklist = '" + dtDateChecklist + '\'' + 
			",intFileAttachmentId = '" + intFileAttachmentId + '\'' + 
			",txtInfoProgramHeaderId = '" + txtInfoProgramHeaderId + '\'' + 
			",intSubDetailActivityId = '" + intSubDetailActivityId + '\'' + 
			",bitCheck = '" + bitCheck + '\'' + 
			"}";
		}
}