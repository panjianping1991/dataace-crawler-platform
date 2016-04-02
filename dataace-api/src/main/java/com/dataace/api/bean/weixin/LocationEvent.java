package com.dataace.api.bean.weixin;

public class LocationEvent extends WeixinMessage {

	private Double latitude;//维度
	private Double longitude;//精度
	private Double precision;//地理位置精度  ，具体含义不明
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getPrecision() {
		return precision;
	}
	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	
	
	
	
	
	
}
