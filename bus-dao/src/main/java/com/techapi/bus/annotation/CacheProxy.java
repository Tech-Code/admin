package com.techapi.bus.annotation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisPool;

public interface CacheProxy {

	public Object get(String key);

	public Object get(String key, int dbIdx);
	
	public JedisPool getJedisPool();

	public String getString(String key);

	public boolean put(String key, Object value);

	/**
	 * 带过期时间的put，单位秒
	 * 
	 * @param key
	 * @param value
	 * @param expires
	 * @return boolean
	 */
	public boolean put(String key, Object value, int expires);

	public boolean put(String key, Object value, int expires,int index);

	public boolean delete(String key);

    public boolean delete(String key, int index);

	/**
	 * 
	 * @param key
	 * @return List<Object>
	 */
	public List<Object> list(String key);

	// 一次性全量save list
	/**
	 * 
	 * @param key
	 * @param list
	 * @param invalidCheckKey
	 *            把checkKey的缓存删掉，从而重新设置为有效
	 * @return boolean
	 */
	public boolean saveList(String key, List<Object> list);

	/**
	 * 带过期的savelist，单位秒
	 * 
	 * @param key
	 * @param list
	 * @param expires
	 * @return boolean
	 */
	public boolean saveList(String key, List<Object> list, int expires);

	/**
	 * 删除list形式的缓存
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean deleteList(String key);

	public Long increase(String key);

	/**
	 * 批量获取对象List
	 * 
	 * @param keys
	 * @return List<Object>
	 */
	public List<Object> mGet(List<String> keys);

	/**
	 * 批量获取字符串List
	 * 
	 * @param keys
	 * @return List<String>
	 */
	public List<String> mGetString(List<String> keys);

	/**
	 * 批量保存对象数据
	 * 
	 * @param map
	 * @return boolean
	 */
	public boolean mPut(Map<String, Object> map);

	/**
	 * @param keyRegex
	 * @param i
	 * @return
	 */
	public Set<String> keys(String keyRegex, int i);

	/**
	 * @param keys
	 * @param i
	 * @return
	 */
	public List<Object> mGet(List<String> keys, int idx);
}
