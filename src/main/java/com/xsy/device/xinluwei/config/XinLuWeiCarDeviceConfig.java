package com.xsy.device.xinluwei.config;

import com.xsy.device.config.car.CarDeviceConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Q1sj
 * @date 2022.7.21 9:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XinLuWeiCarDeviceConfig extends CarDeviceConfig {
    /**
     * 设备编号
     * 在设备后台中查看
     */
    private String deviceCode;
}
