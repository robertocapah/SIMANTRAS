package shp.template.Data.ResponseDataJson.downloadtInfoProgram;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("LtInfoDetail")
	private List<LtInfoDetailItem> ltInfoDetail;

	@SerializedName("LtInfoAttachment")
	private List<LtInfoAttachmentItem> ltInfoAttachment;

	@SerializedName("LtInfoHeader")
	private List<LtInfoHeaderItem> ltInfoHeader;

	public void setLtInfoDetail(List<LtInfoDetailItem> ltInfoDetail){
		this.ltInfoDetail = ltInfoDetail;
	}

	public List<LtInfoDetailItem> getLtInfoDetail(){
		return ltInfoDetail;
	}

	public void setLtInfoAttachment(List<LtInfoAttachmentItem> ltInfoAttachment){
		this.ltInfoAttachment = ltInfoAttachment;
	}

	public List<LtInfoAttachmentItem> getLtInfoAttachment(){
		return ltInfoAttachment;
	}

	public void setLtInfoHeader(List<LtInfoHeaderItem> ltInfoHeader){
		this.ltInfoHeader = ltInfoHeader;
	}

	public List<LtInfoHeaderItem> getLtInfoHeader(){
		return ltInfoHeader;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"ltInfoDetail = '" + ltInfoDetail + '\'' + 
			",ltInfoAttachment = '" + ltInfoAttachment + '\'' + 
			",ltInfoHeader = '" + ltInfoHeader + '\'' + 
			"}";
		}
}