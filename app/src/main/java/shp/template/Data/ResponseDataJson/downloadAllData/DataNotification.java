package shp.template.Data.ResponseDataJson.downloadAllData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataNotification{

	@SerializedName("Notification")
	private List<NotificationItem> notification;

	public void setNotification(List<NotificationItem> notification){
		this.notification = notification;
	}

	public List<NotificationItem> getNotification(){
		return notification;
	}

	@Override
 	public String toString(){
		return 
			"DataNotification{" + 
			"notification = '" + notification + '\'' + 
			"}";
		}
}