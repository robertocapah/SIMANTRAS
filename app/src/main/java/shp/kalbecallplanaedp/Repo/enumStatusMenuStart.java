package shp.kalbecallplanaedp.Repo;

public enum enumStatusMenuStart {
	 FormLogin  (1),
	 UserActiveLogin  (2),
	 PushDataMobile (3);
//	 PushDataSalesForce (4);
	
	enumStatusMenuStart(int id) {
			this.idConfigData = id;
	}
	public int getidData() {
        return this.idConfigData;
    }
	private  final int idConfigData;
}
