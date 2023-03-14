package com.xsy.device.xinluwei.config;

import com.xsy.device.xinluwei.service.SimpleXinLuWeiCarServiceImpl;
import com.xsy.device.xinluwei.service.XinLuWeiCarService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Q1sj
 * @date 2023.3.14 11:07
 */
@Component
@Configuration
public class XinLuWeiCarServiceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(XinLuWeiCarService.class)
    public XinLuWeiCarService xinLuWeiCarService() {
        return new SimpleXinLuWeiCarServiceImpl();
    }
}
