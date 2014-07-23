package com.techapi.bus.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//@Component("cacheProxy")
public class CacheProxyJedisImpl implements CacheProxy {

	private static final Log log = LogFactory.getLog(CacheProxyJedisImpl.class);
	private ISerializer serializer = new HessianSerializer();
	private static Random random = new Random();
	private static final List<JedisPool> poolList = new ArrayList<JedisPool>();

	public CacheProxyJedisImpl(String hostname, String password, Integer port,
			Integer maxIdle, Integer maxTotal, Integer minIdle,
			Integer minEvictableIdleTimeMillis,
			Integer timeBetweenEvictionRunsMillis) {
		JedisPoolConfig config = new JedisPoolConfig();
		if (maxIdle != null && maxIdle != 0) {
			config.setMaxIdle(maxIdle);
		}
		if (maxTotal != null && maxTotal != 0) {
			config.setMaxTotal(maxTotal);
		}
		if (minIdle != null && minIdle != 0) {
			config.setMinIdle(minIdle);
		}
		if (minEvictableIdleTimeMillis != null
				&& minEvictableIdleTimeMillis != 0) {
			config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		}
		if (timeBetweenEvictionRunsMillis != null
				&& timeBetweenEvictionRunsMillis != 0) {
			config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		}

		for (int i = 0; i < 8; i++) {
			JedisPool jp = new JedisPool(config, hostname, port, 5000, password);
			poolList.add(jp);
		}
	}

	public JedisPool getJedisPool() {
		return poolList.get(random.nextInt(poolList.size()));
	}

	@Override
	public Object get(String key) {
		Object obj = null;
		if (StringUtils.isNotBlank(key)) {
			Jedis j = null;
			JedisPool jp = getJedisPool();
			byte[] bytesValue = null;
			try {
				byte[] bytesKey = key.getBytes();
				j = jp.getResource();
				bytesValue = j.get(bytesKey);

				/**
				 * <pre>
				 * Patch For:
				 * 如果对象结构发变化，如增加属性，使用Hessian反序列化时会发生异常，此时需要remove该key
				 * 或者设置该key生存时间为0，防止每次get该key，反序列化出现同样的异常
				 * </pre>
				 */
				obj = serializer.decode(bytesValue);
				if (obj == null) {
					j.expire(bytesKey, 0);
				}
				jp.returnResource(j);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				jp.returnBrokenResource(j);
			}
		}
		return obj;
	}

	@Override
	public Object get(String key, int dbIdx) {
		Object obj = null;
		if (StringUtils.isNotBlank(key)) {
			Jedis jedis = null;
			JedisPool jp = getJedisPool();
			byte[] bytesValue = null;
			try {
				byte[] bytesKey = key.getBytes();
				jedis = jp.getResource();
				jedis.select(dbIdx);
				bytesValue = jedis.get(bytesKey);

				/**
				 * <pre>
				 * Patch For:
				 * 如果对象结构发变化，如增加属性，使用Hessian反序列化时会发生异常，此时需要remove该key
				 * 或者设置该key生存时间为0，防止每次get该key，反序列化出现同样的异常
				 * </pre>
				 */
				obj = serializer.decode(bytesValue);
				if (obj == null) {
					jedis.expire(bytesKey, 0);
				}
				jp.returnResource(jedis);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				jp.returnBrokenResource(jedis);
			}
		}
		return obj;
	}

	@Override
	public String getString(String key) {
		String str = null;
		if (StringUtils.isNotBlank(key)) {
			Jedis j = null;
			JedisPool jp = getJedisPool();
			try {
				j = jp.getResource();
				str = j.get(key);
				jp.returnResource(j);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				jp.returnBrokenResource(j);
			}
		}
		return str;
	}

	@Override
	public boolean put(String key, Object value) {
		return put(key, value, -1, 0);
	}

	@Override
	public boolean put(String key, Object value, int expires) {
		return put(key, value, expires, 0);
	}

	@Override
	public boolean delete(String key, int index) {
		if (StringUtils.isNotBlank(key)) {
			Jedis j = null;
			JedisPool jp = getJedisPool();
			try {
				j = jp.getResource();
				j.select(index);
				j.del(key);
				j.del(key.getBytes());
				jp.returnResource(j);
				return true;
			} catch (Exception e) {
				jp.returnBrokenResource(j);
				log.error(e.getMessage(), e);
			}
		}
		return false;
	}

	@Override
	public boolean delete(String key) {
		return delete(key, 0);
	}

	@Override
	public List<Object> list(String key) {
		return (List<Object>) get(key);
	}

	@Override
	public boolean saveList(String key, List<Object> list) {
		return put(key, list);
	}

	@Override
	public boolean deleteList(String key) {
		return delete(key);
	}

	@Override
	public Long increase(String key) {
		Long ret = 0L;
		if (StringUtils.isNotBlank(key)) {
			Jedis j = null;
			JedisPool jp = getJedisPool();
			try {
				j = jp.getResource();
				ret = j.incr(key);
				jp.returnResource(j);
			} catch (Exception e) {
				jp.returnBrokenResource(j);
				log.error(e.getMessage(), e);
			}
		}
		return ret;
	}

	public ISerializer getSerializer() {
		return serializer;
	}

	public void setSerializer(ISerializer serializer) {
		this.serializer = serializer;
	}

	@Override
	public boolean put(String key, Object value, int expires, int index) {
		if (StringUtils.isNotBlank(key) && value != null) {
			Jedis j = null;
			JedisPool jp = getJedisPool();
			try {

				if (value instanceof String) {
					if (expires > 0) {
						j = jp.getResource();
						j.select(index);
						j.setex(key, expires, value.toString());
					} else {
						j = jp.getResource();
						j.select(index);
						j.set(key, value.toString());
					}
				} else {
					byte[] bytesValue = serializer.encode(value);
					byte[] bytesKey = key.getBytes();
					if (expires > 0) {
						j = jp.getResource();
						j.select(index);
						j.setex(bytesKey, expires, bytesValue);
					} else {
						j = jp.getResource();
						j.select(index);
						j.set(bytesKey, bytesValue);
					}
				}
				jp.returnResource(j);
				return true;
			} catch (Exception e) {
				jp.returnBrokenResource(j);
				log.error(e.getMessage(), e);
			}
		}
		return false;

	}

	@Override
	public boolean saveList(String key, List<Object> list, int expires) {
		return put(key, list, expires);
	}

	@Override
	public List<Object> mGet(List<String> keys) {
		if (keys.size() == 0) {
			return new ArrayList<Object>();
		}
		Jedis j = null;
		JedisPool jp = getJedisPool();
		byte[][] bytesKeys = new byte[keys.size()][];
		for (int i = 0; i < keys.size(); i++) {
			bytesKeys[i] = keys.get(i).getBytes();
		}
		List<byte[]> bytesValueList = null;
		try {
			j = jp.getResource();
			bytesValueList = j.mget(bytesKeys);
			jp.returnResource(j);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			jp.returnBrokenResource(j);
		}
		List<Object> retList = new ArrayList<Object>();
		for (byte[] bytesValue : bytesValueList) {
			retList.add(serializer.decode(bytesValue));
		}
		return retList;

	}

	@Override
	public List<String> mGetString(List<String> keys) {
		if (keys.size() == 0) {
			return new ArrayList<String>();
		}
		Jedis j = null;
		JedisPool jp = getJedisPool();
		String[] stringKeys = new String[keys.size()];
		for (int i = 0; i < keys.size(); i++) {
			stringKeys[i] = keys.get(i);
		}

		List<String> stringValueList = null;
		try {
			j = jp.getResource();
			stringValueList = j.mget(stringKeys);
			jp.returnResource(j);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			jp.returnBrokenResource(j);
		}
		return stringValueList;
	}

	@Override
	public boolean mPut(Map<String, Object> map) {
		Jedis j = null;
		JedisPool jp = getJedisPool();
		int mapSize = map.size();
		if (mapSize == 0) {
			return true;
		}
		byte[][] bytesKeysValues = new byte[mapSize * 2][];
		Set<String> set = map.keySet();
		int i = 0;
		for (String string : set) {
			bytesKeysValues[i] = string.getBytes();
			i++;
			bytesKeysValues[i] = serializer.encode(map.get(string));
			i++;
		}
		try {
			j = jp.getResource();
			j.mset(bytesKeysValues);
			jp.returnResource(j);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			jp.returnBrokenResource(j);
		}

		return false;
	}

	@Override
	public Set<String> keys(String keyRegex, int idx) {
		// TODO Auto-generated method stub
		Set<String> result = null;
		if (StringUtils.isEmpty(keyRegex)) {
			return result;
		}

		JedisPool jp = getJedisPool();
		Jedis jedis = jp.getResource();
		try {
			jedis.select(idx);
			result = jedis.keys(keyRegex);
			jp.returnResource(jedis);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			jp.returnBrokenResource(jedis);
		}
		return result;
	}

	@Override
	public List<Object> mGet(List<String> keys, int idx) {
		// TODO Auto-generated method stub
		if (keys.size() == 0) {
			return new ArrayList<Object>();
		}
		Jedis j = null;
		JedisPool jp = getJedisPool();
		byte[][] bytesKeys = new byte[keys.size()][];
		for (int i = 0; i < keys.size(); i++) {
			bytesKeys[i] = keys.get(i).getBytes();
		}
		List<byte[]> bytesValueList = null;
		try {
			j = jp.getResource();
			j.select(idx);
			bytesValueList = j.mget(bytesKeys);
			jp.returnResource(j);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			jp.returnBrokenResource(j);
		}
		List<Object> retList = new ArrayList<Object>();
		for (byte[] bytesValue : bytesValueList) {
			retList.add(serializer.decode(bytesValue));
		}
		return retList;
	}
}
