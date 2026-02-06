package com.xsy.device.xinluwei.controller;

import com.xsy.device.xinluwei.entity.CallbackRequest;
import com.xsy.device.xinluwei.entity.Result;
import com.xsy.device.xinluwei.entity.XinLuWeiCarSnapshotV2;
import com.xsy.device.xinluwei.service.XinLuWeiCarService;
import com.xsy.device.xinluwei.service.XinLuWeiCarV2Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 信路威设备接口
 *
 * @author Q1sj
 * @date 2022.7.21 9:05
 */
@Slf4j
@RestController
public class XinLuWeiController {

    private final XinLuWeiCarService xinLuWeiCarService;
    private final XinLuWeiCarV2Service xinLuWeiCarV2Service;

    public XinLuWeiController(@Autowired XinLuWeiCarService xinLuWeiCarService,
                              @Autowired XinLuWeiCarV2Service xinLuWeiCarV2Service) {
        this.xinLuWeiCarService = xinLuWeiCarService;
        this.xinLuWeiCarV2Service = xinLuWeiCarV2Service;
    }

    /**
     * 卡口数据上传
     *
     * @param car
     * @return
     */
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

    /**
     * 卡口数据上传v2
     *
     *
     * @param car
     * @return
     */
    @RequestMapping("/xinluwei/callback/v2")
    public ResultV2 v2(@RequestBody XinLuWeiCarSnapshotV2 car) {
        log.info("信路威v2车辆抓拍数据上传接口:{}", car);
        try {
            xinLuWeiCarV2Service.handle(car);
        } catch (Exception e) {
            log.error("信路威v2车辆抓拍数据上传接口执行失败:{}", e.getMessage(), e);
            return ResultV2.error("执行失败");
        }
        return ResultV2.ok();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultV2 {
        private Integer code;
        private String message;
        private Object data;

        public static ResultV2 ok() {
            return new ResultV2(0, "执行成功", null);
        }

        public static ResultV2 error(String message) {
            return new ResultV2(-1, message, null);
        }
    }
}
