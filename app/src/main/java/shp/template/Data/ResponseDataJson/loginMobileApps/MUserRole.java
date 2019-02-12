package shp.template.Data.ResponseDataJson.loginMobileApps;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class MUserRole{

	@SerializedName("intRoleID")
	private int intRoleID;

	@SerializedName("txtRoleName")
	private String txtRoleName;

	public void setIntRoleID(int intRoleID){
		this.intRoleID = intRoleID;
	}

	public int getIntRoleID(){
		return intRoleID;
	}

	public void setTxtRoleName(String txtRoleName){
		this.txtRoleName = txtRoleName;
	}

	public String getTxtRoleName(){
		return txtRoleName;
	}

	@Override
 	public String toString(){
		return 
			"MUserRole{" + 
			"intRoleID = '" + intRoleID + '\'' + 
			",txtRoleName = '" + txtRoleName + '\'' + 
			"}";
		}
}