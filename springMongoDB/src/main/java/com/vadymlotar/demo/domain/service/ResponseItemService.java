package com.vadymlotar.demo.domain.service;

import com.vadymlotar.demo.controller.model.RequestStatistics;
import com.vadymlotar.demo.domain.model.ResponseItem;

public interface ResponseItemService {
	//don't use dashes
	static final String COLLECTION_NAME = "requests";
	
	public void addResponseItem(ResponseItem responseItem);

	public RequestStatistics getRequestStatistics();
}
