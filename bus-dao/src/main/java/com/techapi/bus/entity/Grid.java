package com.techapi.bus.entity;

public class Grid {
	// 网格ID。
	private String id;
	// 网格经度序号。
	private int x;
	// 网格纬度序号。
	private int y;
	// 表示此网格是否为expand创建的，请参考MapService类。
	private boolean expand = false;
	// 此网格是否已包含数据，此属性用来避免从发布商获取的网格数据重复进入缓存（然后是等待入库）。
	private boolean hadData = false;

	public Grid() {

	}

	public Grid(String id, int x, int y) {
		setId(id);
		setX(x);
		setY(y);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public boolean isHadData() {
		return hadData;
	}

	public void setHadData(boolean hadData) {
		this.hadData = hadData;
	}
}
