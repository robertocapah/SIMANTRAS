package shp.template.Data.ResponseDataJson.downloadAllData;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class TProgramVisitItem{

	@SerializedName("intUserId")
	private int intUserId;

	@SerializedName("intRoleId")
	private int intRoleId;

	@SerializedName("txtNotes")
	private String txtNotes;

	@SerializedName("txtProgramVisitId")
	private String txtProgramVisitId;

	@SerializedName("intType")
	private int intType;

	@SerializedName("intStatus")
	private int intStatus;

	@SerializedName("dtStart")
	private String dtStart;

	@SerializedName("dtEnd")
	private String dtEnd;

	public void setIntUserId(int intUserId){
		this.intUserId = intUserId;
	}

	public int getIntUserId(){
		return intUserId;
	}

	public void setIntRoleId(int intRoleId){
		this.intRoleId = intRoleId;
	}

	public int getIntRoleId(){
		return intRoleId;
	}

	public void setTxtNotes(String txtNotes){
		this.txtNotes = txtNotes;
	}

	public String getTxtNotes(){
		return txtNotes;
	}

	public void setTxtProgramVisitId(String txtProgramVisitId){
		this.txtProgramVisitId = txtProgramVisitId;
	}

	public String getTxtProgramVisitId(){
		return txtProgramVisitId;
	}

	public void setIntType(int intType){
		this.intType = intType;
	}

	public int getIntType(){
		return intType;
	}

	public void setIntStatus(int intStatus){
		this.intStatus = intStatus;
	}

	public int getIntStatus(){
		return intStatus;
	}

	public void setDtStart(String dtStart){
		this.dtStart = dtStart;
	}

	public String getDtStart(){
		return dtStart;
	}

	public void setDtEnd(String dtEnd){
		this.dtEnd = dtEnd;
	}

	public String getDtEnd(){
		return dtEnd;
	}

	@Override
 	public String toString(){
		return 
			"TProgramVisitItem{" + 
			"intUserId = '" + intUserId + '\'' + 
			",intRoleId = '" + intRoleId + '\'' + 
			",txtNotes = '" + txtNotes + '\'' + 
			",txtProgramVisitId = '" + txtProgramVisitId + '\'' + 
			",intType = '" + intType + '\'' + 
			",intStatus = '" + intStatus + '\'' + 
			",dtStart = '" + dtStart + '\'' + 
			",dtEnd = '" + dtEnd + '\'' + 
			"}";
		}
}