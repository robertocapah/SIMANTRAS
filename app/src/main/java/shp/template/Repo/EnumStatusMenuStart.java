package shp.template.Repo;

public enum EnumStatusMenuStart {
	 FormLogin  (1),
	 UserActiveLogin  (2),
	 PushDataMobile (3);
//	 PushDataSalesForce (4);
	
	EnumStatusMenuStart(int id) {
			this.idConfigData = id;
	}
	public int getidData() {
        return this.idConfigData;
    }
	private  final int idConfigData;
}
