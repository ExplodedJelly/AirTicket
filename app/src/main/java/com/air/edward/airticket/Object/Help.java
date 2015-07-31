package com.air.edward.airticket.Object;

public class Help {
	private int id;
	private String airport;		//机场名称
	private String weather;		//机场天气
	private String drome;  		//机场咨询电话
	private String bus;			//机场大巴电话
	private String tip;			//机场小提示
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getDrome() {
		return drome;
	}
	public void setDrome(String drome) {
		this.drome = drome;
	}
	public String getBus() {
		return bus;
	}
	public void setBus(String bus) {
		this.bus = bus;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
}
