package com.xsy.device.xinluwei.service;

import com.xsy.device.xinluwei.config.XinLuWeiCarDeviceConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Q1sj
 * @date 2026/1/13 上午11:06
 */
@Slf4j
public class SimpleXinLuWeiCarV2ServiceImpl extends XinLuWeiCarV2Service {
	@Override
	public void handle(XinLuWeiCarDeviceConfig config, CarInfo carInfo) {
		log.info("config:{} carInfo:{}", config, carInfo);
	}
}
