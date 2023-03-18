package com.parking.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUser {

	@JsonProperty("data")
	private Data data;

	@JsonProperty("support")
	private Support support;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setSupport(Support support){
		this.support = support;
	}

	public Support getSupport(){
		return support;
	}
}