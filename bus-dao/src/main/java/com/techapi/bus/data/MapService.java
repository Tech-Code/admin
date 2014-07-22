package com.techapi.bus.data;

import com.techapi.bus.entity.Grid;
import com.techapi.bus.entity.LatLng;

import java.util.ArrayList;
import java.util.List;


public class MapService {

	public static final float LNG_STEP = 0.016f;
	public static final float LAT_STEP = 0.0128f;

	public static final String ZOOM_HEX_STRING = Integer.toHexString(9);

	public static final int GRID_METER = 174;

	/**
	 * 返回指定经纬度的网格对象。
	 * 
	 * @param latlng
	 * @return
	 */
	public static Grid getGirdByLatLng(LatLng latlng) {
		int[] xy = getGirdSeqnumByLatLng(latlng);
		int x = xy[1];
		int y = xy[0];
		return new Grid(ZOOM_HEX_STRING + Integer.toHexString(x) + 'l'
				+ Integer.toHexString(y), x, y);
	}

	/**
	 * 根据中心点和距离返回矩形范围内覆盖的网格集合。
	 * 
	 * @param center
	 * @param range
	 * @param expand
	 *            设置是否在range上再增加一次GRID_METER，这将影响实际返回的网格集合将多包含一圈的网格，这些网格的Grid.
	 *            expand=true。
	 * @return
	 */
	public static List<Grid> getGridsOfBoundingBox(LatLng center, int range,
			boolean expand) {

		if (expand) {
			range = range + GRID_METER * 2;
		} else {
			range += GRID_METER;
		}

		LatLng[] latlngs = getBoundingBox(center, range);

		return getGridsOfBoundingBox(latlngs[0], latlngs[1], expand);
	}

	/**
	 * 根据中心点和半径返回矩形范围的最小最大经纬度。
	 * 
	 * @param center
	 * @param range
	 * @return LatLng[] 0 最小经纬度 1 最大经纬度
	 */
	public static LatLng[] getBoundingBox(LatLng center, int range) {
		float radiusOflngStep = (float) ((range / 1.11111 / Math.cos(Math
				.toRadians(center.getLat()))) / 1e5);
		float radiusOflatStep = (float) ((range / 1.11111) / 1e5);

		LatLng min = new LatLng(center.getLat() - radiusOflatStep,
				center.getLng() - radiusOflngStep);
		LatLng max = new LatLng(center.getLat() + radiusOflatStep,
				center.getLng() + radiusOflngStep);

		return new LatLng[] { min, max };
	}

	/**
	 * 根据中心点和距离返回圆形范围内覆盖的网格集合。
	 * 
	 * @param center
	 * @param radius
	 * @param expand
	 *            设置是否在radius上再增加一次GRID_METER，这将影响实际返回的网格集合将多包含一圈的网格，这些网格的Grid.
	 *            expand=true。
	 * @return
	 */
	public static List<Grid> getGridsOfCircle(LatLng center, int radius,
			boolean expand) {
		return getGridsOfBoundingBox(center, radius, expand);
	}

	/**
	 * 返回矩形范围内的网格集合。 此方法基于地图的第10级比例尺计算。
	 * 
	 * @param min
	 *            左下角经纬度
	 * @param max
	 *            右上角经纬度
	 * @param expand
	 *            是否将最外层的网格Grid.expand设置为true。
	 * @return
	 */
	public static List<Grid> getGridsOfBoundingBox(LatLng min, LatLng max,
			boolean expand) {

		List<Grid> grids = new ArrayList<Grid>(10);

		int[] xy = getGirdSeqnumByLatLng(min);
		int ix = xy[1];
		int iy = xy[0];

		xy = getGirdSeqnumByLatLng(max);
		int mx = xy[1];
		int my = xy[0];

		for (int x = ix; x <= mx; x++) {
			for (int y = iy; y <= my; y++) {
				String id = ZOOM_HEX_STRING + Integer.toHexString(x) + 'l'
						+ Integer.toHexString(y);
				Grid grid = new Grid(id, x, y);
				if (expand && (x == ix || x == mx || y == iy || y == my)) {
					grid.setExpand(true);
				}
				grids.add(grid);
				// System.out.println("####### create grid " + grid.getId() +
				// " expand " + grid.isExpand());
			}
		}

		return grids;
	}

	/**
	 * 返回指定进纬度的网格序号。
	 * 
	 * @param latlng
	 * @return 0 纬度序号 1 经度序号
	 */
	private static int[] getGirdSeqnumByLatLng(LatLng latlng) {
		int[] seqnum = new int[2];
		seqnum[0] = (int) Math.floor(latlng.getLat() / LAT_STEP);
		seqnum[1] = (int) Math.floor(latlng.getLng() / LNG_STEP);
		return seqnum;
	}

	/**
	 * @Description: 获取range网格集合
	 * @param center
	 *            中心经纬度
	 * @param grids
	 *            maxRange集合
	 * @param range
	 *            currentRange
	 * @param expand
	 *            是否扩展
	 */
	public static List<Grid> getRangeGrids(LatLng center, List<Grid> grids,
			int range, boolean expand) {
		LatLng[] latlngs = getBoundingBox(center, range);
		List<Grid> filterGrids = getGridsOfBoundingBox(latlngs[0], latlngs[1],
				expand);

		StringBuffer sb = new StringBuffer();
		for (int i = filterGrids.size() - 1; i >= 0; i--) {
			if (filterGrids.get(i).isExpand()) {
				filterGrids.remove(i);
			} else {
				sb.append(filterGrids.get(i).getId()).append(",");
			}
		}

		for (int i = grids.size() - 1; i >= 0; i--) {
			if (sb.indexOf(grids.get(i).getId()) > -1) {
				grids.remove(i);
			}
		}

		return grids;
	}
}
