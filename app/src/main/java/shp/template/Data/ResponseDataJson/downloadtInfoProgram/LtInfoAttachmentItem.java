package shp.template.Data.ResponseDataJson.downloadtInfoProgram;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LtInfoAttachmentItem{

	@SerializedName("txtFilePath")
	private String txtFilePath;

	@SerializedName("txtfilename")
	private String txtfilename;

	@SerializedName("txtFileLinkEncrypt")
	private String txtFileLinkEncrypt;

	@SerializedName("txtDescription")
	private String txtDescription;

	@SerializedName("intFileAttachmentId")
	private int intFileAttachmentId;

	@SerializedName("txtFileLink")
	private String txtFileLink;

	public void setTxtFilePath(String txtFilePath){
		this.txtFilePath = txtFilePath;
	}

	public String getTxtFilePath(){
		return txtFilePath;
	}

	public void setTxtfilename(String txtfilename){
		this.txtfilename = txtfilename;
	}

	public String getTxtfilename(){
		return txtfilename;
	}

	public void setTxtFileLinkEncrypt(String txtFileLinkEncrypt){
		this.txtFileLinkEncrypt = txtFileLinkEncrypt;
	}

	public String getTxtFileLinkEncrypt(){
		return txtFileLinkEncrypt;
	}

	public void setTxtDescription(String txtDescription){
		this.txtDescription = txtDescription;
	}

	public String getTxtDescription(){
		return txtDescription;
	}

	public void setIntFileAttachmentId(int intFileAttachmentId){
		this.intFileAttachmentId = intFileAttachmentId;
	}

	public int getIntFileAttachmentId(){
		return intFileAttachmentId;
	}

	public void setTxtFileLink(String txtFileLink){
		this.txtFileLink = txtFileLink;
	}

	public String getTxtFileLink(){
		return txtFileLink;
	}

	@Override
 	public String toString(){
		return 
			"LtInfoAttachmentItem{" + 
			"txtFilePath = '" + txtFilePath + '\'' + 
			",txtfilename = '" + txtfilename + '\'' + 
			",txtFileLinkEncrypt = '" + txtFileLinkEncrypt + '\'' + 
			",txtDescription = '" + txtDescription + '\'' + 
			",intFileAttachmentId = '" + intFileAttachmentId + '\'' + 
			",txtFileLink = '" + txtFileLink + '\'' + 
			"}";
		}
}