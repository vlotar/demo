package com.vadymlotar.demo.JedisDemo;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.junit.After;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class BulkLoadTest {
	private RedisConnectionFactory connectionFactory = new RedisConnectionFactory();
	
	@After
	public void clean() {
		Jedis jedis = connectionFactory.getCache();
		// clean REDIS databases
		jedis.flushAll();
		connectionFactory.returnCache(jedis);
	}

	@Test
	public void testBulkLoad() {
		int poolSize = 10;

		StopWatch watch = new StopWatch();
		watch.start();

		ExecutorService executorService = Executors
				.newFixedThreadPool(poolSize);
		for (int i = 0; i < poolSize; i++) {
			executorService.execute(new RedisSetTask());
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

		System.out.println("Total time is: " + watch.getTime());
		System.out.println("Info: "
				+ RedisConnectionFactory.JEDIS.info());
		System.out.println("DB Size: "
				+ RedisConnectionFactory.JEDIS.dbSize());

		System.out
				.println("***************************************************************");		
	}
	
	public class RedisSetTask implements Runnable {

		@Override
		public void run() {
			Jedis jedis = connectionFactory.getCache();
			int size = 100000;

			for (int i = 0; i < size; i++) {
				String keyValue = UUID.randomUUID().toString();
				jedis.set(keyValue, keyValue);
			}
			connectionFactory.returnCache(jedis);
		}

	}
	
}
