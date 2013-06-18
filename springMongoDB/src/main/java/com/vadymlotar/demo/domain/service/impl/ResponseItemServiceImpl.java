package com.vadymlotar.demo.domain.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.vadymlotar.demo.domain.model.ResponseItem;
import com.vadymlotar.demo.domain.service.ResponseItemService;

@Repository
public class ResponseItemServiceImpl implements ResponseItemService {
	
	@Autowired
	private MongoTemplate mongo;

	@Override
	public void addResponseItem(ResponseItem responseItem) {
		if (!mongo.collectionExists(ResponseItem.class)) {
			mongo.createCollection(ResponseItem.class);
		}
		responseItem.setId(UUID.randomUUID().toString());
		mongo.insert(responseItem, COLLECTION_NAME);
	}

}
