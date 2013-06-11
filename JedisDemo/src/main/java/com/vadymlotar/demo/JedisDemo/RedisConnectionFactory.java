package com.vadymlotar.demo.JedisDemo;

import org.apache.commons.pool.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Provides an ability to establish Jedis connection and get it from the
 * connection pool. Each connection must be returned back to the pool.
 * 
 * @author vlotar
 * 
 */
public class RedisConnectionFactory {
	private static final int PORT = 6379;
	private static final String HOST_NAME = "localhost";
	// connection pool
	private JedisPool jedisPool = null;
	// single instance
	public static Jedis JEDIS = new Jedis(HOST_NAME, PORT);

	public RedisConnectionFactory() {
		init();
	}

	private void init() {
		try {
			GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
			poolConfig.maxActive = 100;
			poolConfig.maxIdle = 10;
			poolConfig.minIdle = 2;
			poolConfig.maxWait = 100;
			poolConfig.testWhileIdle = true;
			poolConfig.testOnBorrow = true;
			poolConfig.testOnReturn = true;
			poolConfig.minEvictableIdleTimeMillis = 10000;
			poolConfig.timeBetweenEvictionRunsMillis = 5000;
			poolConfig.numTestsPerEvictionRun = 10;
			// create JEDIS pool
			this.jedisPool = new JedisPool(poolConfig, HOST_NAME, PORT);
			// check connection
			checkConnection();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * This method can throw an exception if connection to REDIS cannot be
	 * established
	 */
	private void checkConnection() {
		// get connection from the pool
		Jedis cache = getCache();
		// ping redis server
		ping(cache);
		// return connection
		returnCache(cache);
	}

	/**
	 * @param cache
	 */
	public void ping(Jedis cache) {
		if (!"PONG".equals(cache.ping())) {
			throw new IllegalStateException("Cannot ping REDIS server");
		}
	}

	/**
	 * Gets Jedis pool
	 * 
	 * @return
	 */
	private JedisPool getJedisPool() {
		if (this.jedisPool == null) {
			init();
		}
		return this.jedisPool;
	}

	/**
	 * Get the cache client from the pool
	 * 
	 * @return the {@link Jedis}
	 * @throws Exception
	 */
	public Jedis getCache() {
		return this.getJedisPool().getResource();
	}

	/**
	 * Puts cache client back to the pool
	 * 
	 * @param cache
	 */
	public void returnCache(Jedis cache) {
		this.getJedisPool().returnResource(cache);
	}

}
