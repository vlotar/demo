package com.vadymlotar.demo.domain.service;

import com.vadymlotar.demo.controller.model.RequestStatistics;
import com.vadymlotar.demo.domain.model.LogItem;

public interface LogItemService {
	
	static final String COLLECTION_NAME = "requests";
	
	public void addLogItem(LogItem logItem);

	public RequestStatistics getRequestStatistics();
}
