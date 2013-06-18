package com.vadymlotar.demo.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ResponseItem {
	@Id
	private String id;
	private long requestDuration;

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

}
