package shp.template.Common;


import shp.template.Repo.EnumStatusMenuStart;

public class ClsStatusMenuStart {
	private EnumStatusMenuStart _intStatus;
	private String _txtDeskripsi;
	private ClsmMenuData _clsmMenuData;
	public EnumStatusMenuStart get_intStatus() {
		return _intStatus;
	}
	public void set_intStatus(EnumStatusMenuStart _intStatus) {
		this._intStatus = _intStatus;
	}
	public String get_txtDeskripsi() {
		return _txtDeskripsi;
	}
	public void set_txtDeskripsi(String _txtDeskripsi) {
		this._txtDeskripsi = _txtDeskripsi;
	}
	public ClsmMenuData get_clsmMenuData() {
		return _clsmMenuData;
	}
	public void set_clsmMenuData(ClsmMenuData _clsmMenuData) {
		this._clsmMenuData = _clsmMenuData;
	}
}
