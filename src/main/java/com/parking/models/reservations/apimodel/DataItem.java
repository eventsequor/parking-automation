package com.parking.models.reservations.apimodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataItem{

	@JsonProperty("reservation_area")
	private String reservationArea;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("Marca")
	private String marca;

	@JsonProperty("Schedule")
	private String schedule;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("id")
	private int id;

	@JsonProperty("plat")
	private String plat;

	@JsonProperty("Day")
	private String day;

	@JsonProperty("location_id")
	private int locationId;

	@JsonProperty("status")
	private int status;

	public void setReservationArea(String reservationArea){
		this.reservationArea = reservationArea;
	}

	public String getReservationArea(){
		return reservationArea;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setMarca(String marca){
		this.marca = marca;
	}

	public String getMarca(){
		return marca;
	}

	public void setSchedule(String schedule){
		this.schedule = schedule;
	}

	public String getSchedule(){
		return schedule;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPlat(String plat){
		this.plat = plat;
	}

	public String getPlat(){
		return plat;
	}

	public void setDay(String day){
		this.day = day;
	}

	public String getDay(){
		return day;
	}

	public void setLocationId(int locationId){
		this.locationId = locationId;
	}

	public int getLocationId(){
		return locationId;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}