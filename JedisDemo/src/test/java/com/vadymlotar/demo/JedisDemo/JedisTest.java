package com.vadymlotar.demo.JedisDemo;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * Demonstrates basic work/operations with Jedis client
 * @author vlotar
 *
 */
public class JedisTest {
	private RedisConnectionFactory connectionFactory = new RedisConnectionFactory();

	@After
	public void clean() {
		Jedis jedis = connectionFactory.getCache();
		// clean REDIS databases
		jedis.flushAll();
		connectionFactory.returnCache(jedis);
	}

	@Test
	/**
	 * This test shows that single connection to Redis is also working.
	 * No further tests for this connection, but keep in mind that it should be the same like for usual connection retrieved from the connection pool.
	 */
	public void testSet_1() {
		// check that single connection is also working.
		RedisConnectionFactory.JEDIS.set("qwe", "ewq");
		Assert.assertEquals("ewq", RedisConnectionFactory.JEDIS.get("qwe"));
	}

	@Test
	public void testSet() {
		Jedis jedis = connectionFactory.getCache();
		jedis.set("abc", "cba");
		Assert.assertEquals("cba", jedis.get("abc"));
		connectionFactory.returnCache(jedis);
	}

	@Test
	public void testDelete() {
		Jedis jedis = connectionFactory.getCache();
		jedis.set("abc", "cba");
		jedis.del("abc");
		Assert.assertNull(jedis.get("abc"));
		connectionFactory.returnCache(jedis);
	}

	@Test
	public void testDbSize() {
		Jedis jedis = connectionFactory.getCache();
		jedis.set("abc", "cba");
		Assert.assertEquals(new Long(1), jedis.dbSize());
		jedis.set("1", "1");
		Assert.assertEquals(new Long(2), jedis.dbSize());
		connectionFactory.returnCache(jedis);
	}

	@Test
	public void testAddToSortedSet() {
		Jedis jedis = connectionFactory.getCache();
		jedis.zadd("items", 1.0, "123");
		Assert.assertEquals(new Long(1), jedis.zcard("items"));
		jedis.zadd("items", 1.0, "121");
		Assert.assertEquals(new Long(2), jedis.zcard("items"));
		connectionFactory.returnCache(jedis);
	}

	@Test
	public void testRemoveFromSortedSet() {
		Jedis jedis = connectionFactory.getCache();
		jedis.zadd("items", 1.0, "123");
		Assert.assertEquals(new Long(1), jedis.zcard("items"));
		jedis.zrem("items", "123");
		Assert.assertEquals(new Long(0), jedis.zcard("items"));
		connectionFactory.returnCache(jedis);
	}
	
	@Test
	public void testAddToList() {
		Jedis jedis = connectionFactory.getCache();
		jedis.rpush("items", "111");
		jedis.rpush("items", "222");
		//check the list size
		Assert.assertEquals(new Long(2), jedis.llen("items"));
		connectionFactory.returnCache(jedis);
	}
	
	@Test
	public void testRemoveFromList() {
		Jedis jedis = connectionFactory.getCache();
		jedis.rpush("items", "111");
		jedis.rpush("items", "222");
		//check the list size
		Assert.assertEquals(new Long(2), jedis.llen("items"));
		jedis.lrem("items", 0, "111");
		Assert.assertEquals(new Long(1), jedis.llen("items"));
		connectionFactory.returnCache(jedis);
	}
	
	@Test
	public void testRangeList() {
		Jedis jedis = connectionFactory.getCache();
		jedis.rpush("items", "111");
		jedis.rpush("items", "222");
		Assert.assertTrue(jedis.lrange("items", 0, 2).contains("111"));
		Assert.assertTrue(jedis.lrange("items", 0, 2).contains("222"));
		connectionFactory.returnCache(jedis);
	}

}
