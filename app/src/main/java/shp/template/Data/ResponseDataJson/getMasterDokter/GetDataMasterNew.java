package shp.template.Data.ResponseDataJson.getMasterDokter;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class GetDataMasterNew{

	@SerializedName("dataDokter")
	private List<DataDokterItem> dataDokter;

	@SerializedName("result")
	private Result result;

	@SerializedName("dataApotek")
	private List<DataApotekItem> dataApotek;

	@SerializedName("intTypeVisit")
	private int intTypeVisit;

	public void setDataDokter(List<DataDokterItem> dataDokter){
		this.dataDokter = dataDokter;
	}

	public List<DataDokterItem> getDataDokter(){
		return dataDokter;
	}

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setDataApotek(List<DataApotekItem> dataApotek){
		this.dataApotek = dataApotek;
	}

	public List<DataApotekItem> getDataApotek(){
		return dataApotek;
	}

	public void setIntTypeVisit(int intTypeVisit){
		this.intTypeVisit = intTypeVisit;
	}

	public int getIntTypeVisit(){
		return intTypeVisit;
	}

	@Override
 	public String toString(){
		return 
			"GetDataMasterNew{" + 
			"dataDokter = '" + dataDokter + '\'' + 
			",result = '" + result + '\'' + 
			",dataApotek = '" + dataApotek + '\'' + 
			",intTypeVisit = '" + intTypeVisit + '\'' + 
			"}";
		}
}