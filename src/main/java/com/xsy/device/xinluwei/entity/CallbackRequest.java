package com.xsy.device.xinluwei.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Q1sj
 */
@Data
public class CallbackRequest implements Serializable {
    /**
     *调用批次-留空，如："id":""
     */
    private String id;
    /**
     * 调用时间，取时间毫秒数
     */
    private String requestTime;
    /**
     * 取值设备的sn号（）
     */
    private String sessionId;
    /**
     * 接口命令，写死：device.data.acquisition
     */
    private String command;
    /**
     * 参数体-请参考参数提说明
     */
    private CarRecord params;
}
