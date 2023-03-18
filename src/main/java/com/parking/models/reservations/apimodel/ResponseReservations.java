package com.parking.models.reservations.apimodel;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseReservations {

	@JsonProperty("data")
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}
}