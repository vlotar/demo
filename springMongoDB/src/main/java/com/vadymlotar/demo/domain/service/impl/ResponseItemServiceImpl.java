package com.vadymlotar.demo.domain.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.vadymlotar.demo.controller.model.RequestStatistics;
import com.vadymlotar.demo.domain.model.ResponseItem;
import com.vadymlotar.demo.domain.service.ResponseItemService;

@Repository
public class ResponseItemServiceImpl implements ResponseItemService {
	
	@Autowired
	private MongoTemplate mongo;

	public void addResponseItem(ResponseItem responseItem) {
		if (!mongo.collectionExists(COLLECTION_NAME)) {
			DBCollection collection = mongo.createCollection(COLLECTION_NAME);
			//create TTL Index
			BasicDBObject index = new BasicDBObject("date", 1);
			BasicDBObject options = new BasicDBObject("expireAfterSeconds", TimeUnit.MINUTES.toSeconds(60));
			collection.ensureIndex(index, options);
		}	
		responseItem.setId(UUID.randomUUID().toString());
		responseItem.setDate(new Date());
		mongo.insert(responseItem, COLLECTION_NAME);
	}

	@Override
	public RequestStatistics getRequestStatistics() {
		RequestStatistics requestStatistics = new RequestStatistics();
		calculateTotalCount(requestStatistics);		
		calculateAvgDuration(requestStatistics);
		calculateMaxDuration(requestStatistics);
		calculateMinDuration(requestStatistics);
		//calculateMedianDuration(requestStatistics);
		return requestStatistics;
	}

	/*private void calculateMedianDuration(RequestStatistics requestStatistics) {
		String mapFunction = "function(){emit( {sorting: this.requestDuration * -1,'med'},this.requestDuration);}";
		String reduceFunction = "function(key,values){ var mean = values[(int)values.length/2]; return mean;}";

		@SuppressWarnings("rawtypes")
		MapReduceResults<Map> result = mongo.mapReduce(COLLECTION_NAME,
				mapFunction, reduceFunction, Map.class);
		for (Map<String, Double> map : result) {
			requestStatistics.setAvgDuration(map.get("value"));
		}
	}*/

	private void calculateMinDuration(RequestStatistics requestStatistics) {
		String mapFunction = "function(){emit( 'min',this.requestDuration);}";
		String reduceFunction = "function(key,values){  var minValue=values[0];for(var i=1;i<values.length;i++){if(values[i]<minValue){minValue=values[i];}}return minValue;}";
		
		@SuppressWarnings("rawtypes")
		MapReduceResults<Map> result = mongo.mapReduce(COLLECTION_NAME, mapFunction, reduceFunction, Map.class);
		for(@SuppressWarnings("rawtypes") Map<String, Map> map : result) {
			requestStatistics.setMinDuration((double) map.get("value").get("floatApprox"));
		}
		
	}

	private void calculateMaxDuration(RequestStatistics requestStatistics) {
		String mapFunction = "function(){emit( 'max',this.requestDuration);}";
		String reduceFunction = "function(key,values){  var maxValue=values[0];for(var i=1;i<values.length;i++){if(values[i]>maxValue){maxValue=values[i];}}return maxValue;}";
		
		@SuppressWarnings("rawtypes")
		MapReduceResults<Map> result = mongo.mapReduce(COLLECTION_NAME, mapFunction, reduceFunction, Map.class);
		for(@SuppressWarnings("rawtypes") Map<String, Map> map : result) {
			requestStatistics.setMaxDuration((double) map.get("value").get("floatApprox"));
		}
	}

	/**
	 * @param requestStatistics
	 */
	private void calculateAvgDuration(RequestStatistics requestStatistics) {
		// get avg request duration
		String mapFunction = "function(){emit( 'avg',this.requestDuration);}";
		String reduceFunction = "function(key,values){ var total = 0; for(var i = 0; i < values.length; i++) total += values[i]; var mean = total/values.length;return mean;}";
		
		@SuppressWarnings("rawtypes")
		MapReduceResults<Map> result = mongo.mapReduce(COLLECTION_NAME, mapFunction, reduceFunction, Map.class);
		for(Map<String, Double> map : result) {
			requestStatistics.setAvgDuration(map.get("value"));
		}
	}

	/**
	 * @param requestStatistics
	 */
	private void calculateTotalCount(RequestStatistics requestStatistics) {
		//get collection size
		Query query = new Query();
		requestStatistics.setTotalCount(mongo.count(query, COLLECTION_NAME));
	}

}
