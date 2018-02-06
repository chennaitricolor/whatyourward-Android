package com.thoughtworks.whatyourward.data.model.ward;

public class ZoneInfo{
	private String zoneNo;
	private String zonalOfficePhone;
	private String zonalOfficeAddress;
	private String zoneName;


	public ZoneInfo(String zoneNo, String zoneName, String zonalOfficeAddress, String zonalOfficePhone) {

		this.zoneNo = zoneNo;
		this.zoneName = zoneName;
		this.zonalOfficeAddress = zonalOfficeAddress;
		this.zonalOfficePhone = zonalOfficePhone;
	}


	public void setZoneNo(String zoneNo){
		this.zoneNo = zoneNo;
	}

	public String getZoneNo(){
		return zoneNo;
	}

	public void setZonalOfficePhone(String zonalOfficePhone){
		this.zonalOfficePhone = zonalOfficePhone;
	}

	public String getZonalOfficePhone(){
		return zonalOfficePhone;
	}

	public void setZonalOfficeAddress(String zonalOfficeAddress){
		this.zonalOfficeAddress = zonalOfficeAddress;
	}

	public String getZonalOfficeAddress(){
		return zonalOfficeAddress;
	}

	public void setZoneName(String zoneName){
		this.zoneName = zoneName;
	}

	public String getZoneName(){
		return zoneName;
	}

}
