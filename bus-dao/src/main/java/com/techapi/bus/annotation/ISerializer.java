package com.techapi.bus.annotation;
/**
 * @author gpzuestc
 * @mail: guopengzhang@sohu-inc.com
 * @date: 2013-7-8
 * @todo: 
 * 
 */
public interface ISerializer {
	/**
	 * 对象序列化
	 * @param o
	 * @return
	 */
	byte[] encode(Object o);
	
	/**
	 * 反序列化生成对象
	 * @param bytes
	 * @return
	 */
	Object decode(byte[] bytes);
}
