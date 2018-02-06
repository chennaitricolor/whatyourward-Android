package com.thoughtworks.whatyourward.data.model.ward;

public class Ward {
	private ZoneInfo zoneInfo;
	private String wardName;
	private String wardNo;
	private String wardOfficePhone;
	private String wardWhatsappGroupLink;
	private String wardOfficeEmail;
	private String wardOfficeAddress;


	public Ward(String wardNo, String wardName, String wardOfficeAddress, String wardOfficePhone, String wardOfficeEmail, String whatsappGroupLink) {
		this.wardNo = wardNo;
		this.wardName = wardName;
		this.wardOfficeAddress = wardOfficeAddress;
		this.wardOfficeEmail = wardOfficeEmail;
		this.wardOfficePhone = wardOfficePhone;
		this.wardWhatsappGroupLink = whatsappGroupLink;

		this.zoneInfo = null;
	}
	public void setZoneInfo(ZoneInfo zoneInfo){
		this.zoneInfo = zoneInfo;
	}

	public ZoneInfo getZoneInfo(){
		return zoneInfo;
	}

	public void setWardName(String wardName){
		this.wardName = wardName;
	}

	public String getWardName(){
		return wardName;
	}

	public void setWardNo(String wardNo){
		this.wardNo = wardNo;
	}

	public String getWardNo(){
		return wardNo;
	}

	public void setWardOfficePhone(String wardOfficePhone){
		this.wardOfficePhone = wardOfficePhone;
	}

	public String getWardOfficePhone(){
		return wardOfficePhone;
	}

	public void setWardWhatsappGroupLink(String wardWhatsappGroupLink){
		this.wardWhatsappGroupLink = wardWhatsappGroupLink;
	}

	public String getWardWhatsappGroupLink(){
		return wardWhatsappGroupLink;
	}

	public void setWardOfficeEmail(String wardOfficeEmail){
		this.wardOfficeEmail = wardOfficeEmail;
	}

	public String getWardOfficeEmail(){
		return wardOfficeEmail;
	}

	public void setWardOfficeAddress(String wardOfficeAddress){
		this.wardOfficeAddress = wardOfficeAddress;
	}

	public String getWardOfficeAddress(){
		return wardOfficeAddress;
	}

}
