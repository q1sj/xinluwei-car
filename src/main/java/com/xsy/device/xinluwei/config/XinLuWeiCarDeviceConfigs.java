package com.xsy.device.xinluwei.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 信路威卡口设备配置集合
 *
 * @author Q1sj
 * @date 2022.7.21 10:31
 */
@Data
@Component
@ConfigurationProperties(prefix = "device-configs.xinluwei.car", ignoreInvalidFields = true)
public class XinLuWeiCarDeviceConfigs {
    private Set<XinLuWeiCarDeviceConfig> set;
    public static Set<XinLuWeiCarDeviceConfig> staticSet;

    @PostConstruct
    public void init() {
        if (set == null) {
            set = new CopyOnWriteArraySet<>();
        } else {
            set = new CopyOnWriteArraySet<>(set);
        }
        staticSet = set;
    }

    public static XinLuWeiCarDeviceConfig getByDeviceCode(String deviceCode) {
        for (XinLuWeiCarDeviceConfig config : staticSet) {
            if (config.getDeviceCode().equals(deviceCode)) {
                return config;
            }
        }
        return null;
    }
}
