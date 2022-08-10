package com.xsy.device.xinluwei.controller;

import com.xsy.device.xinluwei.entity.CallbackRequest;
import com.xsy.device.xinluwei.entity.Result;
import com.xsy.device.xinluwei.service.SimpleXinLuWeiCarServiceImpl;
import com.xsy.device.xinluwei.service.XinLuWeiCarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Q1sj
 * @date 2022.7.21 9:05
 */
@Slf4j
@RestController
public class XinLuWeiController {

    private final XinLuWeiCarService xinLuWeiCarService;

    public XinLuWeiController(@Autowired(required = false) XinLuWeiCarService xinLuWeiCarService) {
        this.xinLuWeiCarService = xinLuWeiCarService != null ? xinLuWeiCarService : new SimpleXinLuWeiCarServiceImpl();
    }

    @RequestMapping("/xinluwei/callback")
    public Result callback(@RequestBody CallbackRequest car) {
        log.info("信路威卡口回调接口:{}", car);
        String sessionId = car.getSessionId();
        String id = car.getId();
        try {
            xinLuWeiCarService.handle(car.getParams());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage(), id, sessionId);
        }
        return Result.ok(id, sessionId);
    }

}
