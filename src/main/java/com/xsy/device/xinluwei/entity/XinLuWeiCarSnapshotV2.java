/**
 * Copyright 2026 json.cn
 */
package com.xsy.device.xinluwei.entity;

import lombok.Data;

/**
 * 服务区内车辆抓拍数据推送
 */
@Data
public class XinLuWeiCarSnapshotV2 {
	/**
	 * 数据ID，唯一值
	 */
	private String guid;
	/**
	 * 设备 sn
	 */
	private String deviceSn;
	/**
	 * 车辆分类：客车/货车/特种车辆
	 */
	private String carClass;
	/**
	 * 车型 {@link com.xsy.device.xinluwei.enums.XinLuWeiCarTypeEnum}
	 */
	private String vehicleType;
	/**
	 * 危化品标志：易爆物品，惰性气体，有毒物品，易腐物品，易燃物品，高温物品，氧化剂，医疗物品，危险品
	 */
	private String dangerous;
	/**
	 * 车牌号
	 */
	private String vehiclePlate;
	/**
	 * 车牌颜色 {@link com.xsy.device.xinluwei.enums.XinLuWeiCarNoColorEnum}
	 */
	private Integer plateColor;
	/**
	 * 车轴数
	 */
	private Integer axleCount;
	/**
	 * 服务区编号
	 */
	private String saCode;
	/**
	 * 服务区名称
	 */
	private String saName;
	/**
	 * 服务区方向：上行/下行
	 */
	private String saDirection;
	/**
	 * 行驶方向：入/出
	 */
	private String driveType;
	/**
	 * 设备位置：匝道/专用车位
	 */
	private String devicePosition;
	/**
	 * 通行时间
	 */
	private String passTime;
	/**
	 * 车牌图地址
	 * 绝对地址,如：http://ip:port/img2/plate.jpg
	 */
	private String plateImage;
	/**
	 * 车头大图地址
	 * 绝对地址,如：http://ip:port/img2/head.jpg
	 */
	private String headImage;
	/**
	 * 车侧大图地址
	 * 绝对地址,如：http://ip:port/img2/side.jpg
	 */
	private String sideImage;
	/**
	 * 车尾大图地址
	 * 绝对地址,如：http://ip:port/img2/tail.jpg
	 */
	private String tailImage;
}