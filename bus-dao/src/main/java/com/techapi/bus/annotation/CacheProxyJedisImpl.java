package com.techapi.bus.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component("cacheProxy")
public class CacheProxyJedisImpl implements CacheProxy {

	private static final Log log = LogFactory.getLog(CacheProxyJedisImpl.class);
	private ISerializer serializer = new HessianSerializer();
	private static Random random = new Random();
	private static final List<JedisPool> poolList = new ArrayList<JedisPool>();
	
	static{
		String hostAndPort = "180.184.33.132:6379";
		if (StringUtils.isNotBlank(hostAndPort)) {
			String[] hostAndPortArr = hostAndPort.split(":");
			if (hostAndPortArr != null && hostAndPortArr.length == 2) {
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxIdle(100);
				config.setMaxTotal(50);
				config.setMinIdle(40);
				for(int i = 0; i < 8; i++){
					JedisPool jp = new JedisPool(config, hostAndPortArr[0], Integer.valueOf(hostAndPortArr[1]),5000,"JL8qEYSCq7");
					poolList.add(jp);
				}
			}
		}
	}
	
	public static JedisPool getJedisPool(){
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
				jp.returnResource(j);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				jp.returnBrokenResource(j);
			}
			obj = serializer.decode(bytesValue);
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
		return put(key, value, -1);
	}

	@Override
	public boolean delete(String key) {
		if (StringUtils.isNotBlank(key)) {
			Jedis j = null;
			JedisPool jp = getJedisPool();
			try {
				j = jp.getResource();
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
	public boolean put(String key, Object value, int expires) {
		if (StringUtils.isNotBlank(key) && value != null) {
			Jedis j = null;
			JedisPool jp = getJedisPool();
			try {

				if (value instanceof String) {
					if (expires > 0) {
						j = jp.getResource();
						j.setex(key, expires, value.toString());
					} else {
						j = jp.getResource();
						j.set(key, value.toString());
					}
				} else {
					byte[] bytesValue = serializer.encode(value);
					byte[] bytesKey = key.getBytes();
					if (expires > 0) {
						j = jp.getResource();
						j.setex(bytesKey, expires, bytesValue);
					} else {
						j = jp.getResource();
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
		if(keys.size()==0){
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
		if(keys.size()==0){
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
		if (mapSize==0) {
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
}
