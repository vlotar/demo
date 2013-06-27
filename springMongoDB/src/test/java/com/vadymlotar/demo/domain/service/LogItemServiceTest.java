package com.vadymlotar.demo.domain.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.util.StopWatch;

import com.vadymlotar.demo.domain.model.LogItem;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class LogItemServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private LogItemService responseItemService;
	
	@Test
	public void testAddResponseItem() {
		int poolSize = 10;
		int elementsToBeInserted = 100000;

		StopWatch watch = new StopWatch();
		watch.start();

		ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
		for (int i = 0; i < poolSize; i++) {
			executorService.execute(new MongoInsertTask(elementsToBeInserted));
		}

		// waiting when executor finishes job
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE,
					TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		watch.stop();

		System.out.println("Total time is: " + watch.getTotalTimeMillis());

		System.out
				.println("***************************************************************");
	}
	
	private class MongoInsertTask implements Runnable {
		private int size;

		public MongoInsertTask(int size) {
			this.size = size;
		}

		@Override
		public void run() {
			for (int i = 0; i < size; i++) {
				LogItem responseItem = new LogItem();
				//put some magic number as request duration 
				responseItem.setRequestDuration(100);
				responseItem
						.setResponse("response");
				responseItemService.addLogItem(responseItem);
			}
		}
		
	}
}
