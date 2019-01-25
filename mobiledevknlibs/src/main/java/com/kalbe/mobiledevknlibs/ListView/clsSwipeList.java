package com.kalbe.mobiledevknlibs.ListView;

import java.io.Serializable;

public class clsSwipeList implements Serializable{
	
	private String _txtId;
	private String _txtTitle;
	private String _txtDescription;
	private String _txtDescription2;
	private String _txtDescription3;
	 
	public String Property_txtId="txtId";
	public String Property_txtTitle="txtTitle";
	public String Property_txtDescription="txtDescription";
	public String Property_txtDescription2="txtDescription2";
	public String Property_txtDescription3="txtDescription3";
	
	public String PropertyAll=Property_txtId+","+Property_txtTitle+ "," + Property_txtDescription + "," + Property_txtDescription2 + "," + Property_txtDescription3;

	public String get_txtId() {
		return _txtId;
	}

	public void set_txtId(String _txtId) {
		this._txtId = _txtId;
	}

	public String get_txtTitle() {
		return _txtTitle;
	}

	public void set_txtTitle(String _txtTitle) {
		this._txtTitle = _txtTitle;
	}

	public String get_txtDescription() {
		return _txtDescription;
	}

	public void set_txtDescription(String _txtDescription) {
		this._txtDescription = _txtDescription;
	}
	public String get_txtDescription2(){
		return _txtDescription2;
	}
	public void set_txtDescription2(String _txtDescription2){
		this._txtDescription2=_txtDescription2;
	}
	public String get_txtDescription3(){
		return _txtDescription3;
	}
	public void set_txtDescription3(String _txtDescription3){
		this._txtDescription3=_txtDescription3;
	}
}
