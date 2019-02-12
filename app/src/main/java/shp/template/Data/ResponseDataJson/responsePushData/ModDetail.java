package shp.template.Data.ResponseDataJson.responsePushData;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ModDetail{

	@SerializedName("txtProgramVisitId")
	private String txtProgramVisitId;

	@SerializedName("dtStart")
	private String dtStart;

	@SerializedName("dtEnd")
	private String dtEnd;

	public void setTxtProgramVisitId(String txtProgramVisitId){
		this.txtProgramVisitId = txtProgramVisitId;
	}

	public String getTxtProgramVisitId(){
		return txtProgramVisitId;
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
			"ModDetail{" + 
			"txtProgramVisitId = '" + txtProgramVisitId + '\'' + 
			",dtStart = '" + dtStart + '\'' + 
			",dtEnd = '" + dtEnd + '\'' + 
			"}";
		}
}