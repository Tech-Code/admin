package com.techapi.bus.annotation;


import java.util.List;
import java.util.Map;

public interface CacheProxy {
	public static final String VALIDCHECKKEY_PREFIX="validcheckkey.";
	public Object get(String key);
	public String getString(String key);
	public boolean put(String key,Object value);
	/**
	 * 带过期时间的put，单位秒
	 * @param key
	 * @param value
	 * @param expires
	 * @return
	 * boolean
	 */
	public boolean put(String key,Object value,int expires);
	public boolean delete(String key);
	/**
	 * 
	 * @param key
	 * @param validCheckKey 使用validCheckKey做有效性检查，存储用的数据结构为list，validKey--标识listvalue，value是key-valueCheckKey
	 * @return
	 * List<Object>
	 */
	public List<Object> listWithValidCheck(String key,String validCheckKey);
	/**
	 * 
	 * @param key
	 * @return
	 * List<Object>
	 */
	public List<Object> list(String key);
	//一次性全量save list
	/**
	 * 
	 * @param key
	 * @param list
	 * @param invalidCheckKey 把checkKey的缓存删掉，从而重新设置为有效
	 * @return
	 * boolean
	 */
	public boolean saveList(String key,List<Object> list);
	/**
	 * 带过期的savelist，单位秒
	 * @param key
	 * @param list
	 * @param expires
	 * @return
	 * boolean
	 */
	public boolean saveList(String key,List<Object> list,int expires);
	/**
	 * 
	 * @param key
	 * @param list
	 * @param validCheckKey 设置validCheckKey做有效性检查，存储用的数据结构为list，validKey--标识listvalue，value是key-valueCheckKey
	 * @return
	 * boolean
	 */
	public boolean saveListWithValidCheck(String key,List<Object> list,String validCheckKey);
	/**
	 * 删除list形式的缓存
	 * @param key
	 * @return
	 * boolean
	 */
	public boolean deleteList(String key);
	public Long increase(String key);
	
	/**
	 * 批量获取对象List
	 * @param keys
	 * @return
	 * List<Object>
	 */
	public List<Object> mGet(List<String> keys);
	/**
	 * 批量获取字符串List
	 * @param keys
	 * @return
	 * List<String>
	 */
	public List<String> mGetString(List<String> keys);
	/**
	 * 批量保存对象数据
	 * @param map
	 * @return
	 * boolean
	 */
	public boolean mPut(Map<String, Object> map);
}
