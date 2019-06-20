package com.kalbenutritionals.simantra.Data.ResponseDataJson.filePDF;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("file_name")
	private String fileName;

	@SerializedName("link_file")
	private String linkFile;

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getFileName(){
		return fileName;
	}

	public void setLinkFile(String linkFile){
		this.linkFile = linkFile;
	}

	public String getLinkFile(){
		return linkFile;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"file_name = '" + fileName + '\'' + 
			",link_file = '" + linkFile + '\'' + 
			"}";
		}
}