package com.techapi.bus.util;
/**
 * 缓存时间
* @ClassName: TTL 
* @author jiayusun@sohu-inc.com 
* @date 2014-1-16 上午10:49:10 
 */
public enum TTL {
	
	_30S(30),_1M(60),_2M(120),_3M(180),_5M(300),_10M(600),_20M(1200),_30M(1800),_1H(3600),_1D(86400);	
	private int time;
	private TTL(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
}