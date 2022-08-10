package com.xsy.device.xinluwei.service;

import com.xsy.device.xinluwei.config.XinLuWeiCarDeviceConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Q1sj
 * @date 2022.7.21 11:29
 */
@Slf4j
public class SimpleXinLuWeiCarServiceImpl extends XinLuWeiCarService {
    @Override
    public void handle(XinLuWeiCarDeviceConfig config, CarInfo carInfo) {
        log.info("config:{} carInfo:{}", config, carInfo);
    }
}
