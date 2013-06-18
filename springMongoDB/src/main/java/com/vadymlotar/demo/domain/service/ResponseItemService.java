package com.vadymlotar.demo.domain.service;

import com.vadymlotar.demo.domain.model.ResponseItem;

public interface ResponseItemService {
	static final String COLLECTION_NAME = "requests-responses";
	
	public void addResponseItem(ResponseItem responseItem);
}
