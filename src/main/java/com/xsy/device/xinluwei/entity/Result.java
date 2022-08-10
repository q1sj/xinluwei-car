package com.xsy.device.xinluwei.entity;

import lombok.Data;

/**
 * @author Q1sj
 * @date 2022.7.21 9:03
 * 响应示例:
 * {
 * "data":{
 * "systemTime":"121232433243243432432"
 * },
 * "id":"00791ea20f994cc0a764009f6f671aa1",
 * "sesssionId":"VECAM-D01-LS13014429",
 * "code":"0",
 * "message":"执行成功",
 * "memo":null
 * }
 */
@Data
public class Result {
    /**
     * 响应批次（赞无用），每次响应都不一样
     */
    private String id;
    /**
     * 会话标签（与调用时一致）
     */
    private String sessionId;
    /**
     * 状态码，返回0时正常，其他皆错误
     */
    private String code;
    /**
     * 状态信息描述
     */
    private String message;
    /**
     * 数据体（系统时间）
     */
    private Data data;
    /**
     * 附加备注信息
     */
    private String memo;

    public static Result ok(String id, String sessionId) {
        Result result = new Result();
        result.setId(id);
        result.setSessionId(sessionId);
        result.setCode("0");
        result.setMessage("执行成功");
        result.setData(new Data());

        return result;
    }

    public static Result error(String message, String id, String sessionId) {
        Result result = new Result();
        result.setId(id);
        result.setSessionId(sessionId);
        result.setCode("-1");
        result.setMessage(message);
        result.setData(new Data());

        return result;
    }

    @lombok.Data
    public static class Data {
        /**
         * 系统时间毫秒数
         */
        private Long systemTime;

        public Data() {
            this.systemTime = System.currentTimeMillis();
        }
    }
}
