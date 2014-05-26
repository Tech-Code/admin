package com.techapi.bus.dao;

import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.chetuobang.logic.redis.RedisFactory;
import com.techapi.bus.model.RedisEnumKey;

/**
 * @Description:
 * @author hongjia.hu
 * @date 2014-3-8
 */
public class RedisDaoTest {

	@Test
	public void testRedis() {
		String key = RedisEnumKey.BUS_LINE.toString() + ":*";

		Jedis jedis = RedisFactory.getFactory(1);
		Set<String> set = jedis.keys(key);
		for (String str : set) {
			System.out.println(str);
		}

		RedisFactory.returnResource(jedis);
	}

	@Test
	public void testGet() {
		String key = "bus_app_l:02053e48a87161f27e29bdc0:280124";

		Jedis jedis = RedisFactory.getFactory(1);
		String value = jedis.get(key);
		System.out.println(value);
		RedisFactory.returnResource(jedis);
	}
}
