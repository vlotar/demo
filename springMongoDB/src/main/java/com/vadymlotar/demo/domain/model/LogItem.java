package com.vadymlotar.demo.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LogItem {
	@Id
	private String id;
	private String response;
	private long requestDuration;
	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getRequestDuration() {
		return requestDuration;
	}

	public void setRequestDuration(long requestDuration) {
		this.requestDuration = requestDuration;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	
}
