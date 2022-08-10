package com.xsy.device.xinluwei.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * (CarRecord)实体类
 *
 * @author Q1sj
 * 请求示例：
 * {
 *     "id":"00791ea20f994cc0a764009f6f671aa1",
 *     "sessionId":"VECAM-D01-LS13014429",
 *     "command":"device.data.acquisition",
 *     "params":{
 *         "guid":"20180228153445_0508C85F8A7A4C3BBD66D2A179EE17E8",
 *         "uploadTime":"2018-02-28 15:34:45",
 *         "deviceCode":"VECAM-D01-LS13014429",
 *         "gantryId":"Gxxxxxxx",
 *         "headLaneCode":"Gxxxxxxx",
 *         "sideLaneCode":"Gxxxxxxx",
 *         "carClass":"货车",
 *         "carType":"货车",
 *         "carModels":"1",
 *         "specialClass":"腐",
 *         "platePosition":"",
 *         "lanePosition":"",
 *         "plateNumber":"桂C66666",
 *         "plateColor":"1",
 *         "plateImage":"base64数据",
 *         "plateImageWidth":"96",
 *         "plateImageHeight":"64",
 *         "headImage":"base64数据",
 *         "headImageWidth":"3392",
 *         "headmageHeight":"2272",
 *         "headCaptureTime":"2018-02-28 10:45:45",
 *         "tailImage":"base64数据",
 *         "tailImageWidth":"3222",
 *         "tailImageHeight":"2722",
 *         "tailCaptureTime":"2018-02-28 10:45:45",
 *         "sideImage":"base64数据",
 *         "sideImageWidth":null,
 *         "sideImageHeight":null,
 *         "sideCaptureTime":"2018-02-28 10:45:45",
 *         "carLength":null,
 *         "carHight":null,
 *         "axleCount":"6",
 *         "axleType":"1+2",
 *         "impostorAxleCount":"0",
 *         "wheelbase":"350",
 *         "speed":null,
 *         "wheelCount":null,
 *         "firstAuthoritative":99,
 *         "averageAuthoritative":99,
 *         "attached":"",
 *         "envState":"",
 *         "headSceneType":"",
 *         "sideSceneType":"",
 *         "headDrivingDirection":"",
 *         "sideDrivingDirection":"",
 *         "changeLane":"",
 *         "cover":"",
 *         "resultType":"",
 *              "specialVehicle":"1",
 *              "extendAttribute":""
 *     }
 * }
 */
@Data
@NoArgsConstructor
public class CarRecord implements Serializable {

    public static final String DEFAULT_PLATE_NUMBER = "默A00000";

    /**
     * 唯一id，格式要求：年月日时分秒_32位uuid，例如：
     * 20180411105208_0508C85F8A7A4C3BBD66D2A179EE17E8
     * 其中的时间参考捕获时间，不可空
     */
    private String guid;
    /**
     * 上传时间 格式yyyy-MM-dd HH:mm:ss 不可空
     * 有些设备推送时间带毫秒 yyyy-MM-dd HH:mm:ss.SSS
     */
    private String uploadTime;
    /**
     * 门架id，可空
     */
    private String gantryId;
    /**
     * 设备的SN号，不可空
     */
    private String deviceCode;

    /**
     * 车头车道号，可空
     */
    private String headLaneCode;
    /**
     * 车侧车道号，可空
     */
    private String sideLaneCode;
    /**
     * 车辆分类，按如下范围赋值（客车/货车/特种车辆），默认null如：
     * "carClass":null，可空
     */
    private String carClass;
    /**
     * 车辆类型 可空，例如：轿车
     */
    private String carType;
    /**
     * 车型、参照交通部的收费车型（1~4 客1~客4 、11~16 货1~货6、专项车21~26 专项1~专项6）
     */
    private String carModels;
    /**
     * 危险品标志，例如：爆、腐等 没有null
     */
    private String specialClass;
    /**
     * 车牌号，无车牌时，请赋值“默A00000”
     */
    private String plateNumber;
    /**
     * 车牌号颜色
     * 参照交通部标准(:0-蓝色1-黄色2-黑色3-白色，4- 渐变绿色5- 黄绿双拼色6- 蓝白渐变色7- 临时牌照9- 未确定11-绿色12-红色)
     */
    private String plateColor;
    /**
     * 车牌小图(jpg格式)，无图时，留空nulll；赋值时请将图片转为base64数据后赋值，可空
     */
    @ToString.Exclude
    private String plateImage;
    /**
     * 车头大图(jpg格式)，无图时，留空nulll；赋值时请将图片转为base64数据后赋值，可空
     */
    @ToString.Exclude
    private String headImage;
    /**
     * 车尾图（jpg格式），无图时，留空nulll；赋值时请将图片转为base64数据后赋值，可空
     */
    @ToString.Exclude
    private String tailImage;
    /**
     * 车身图(JPG格式)，无图时，留空nulll；赋值时请将图片转为base64数据后赋值，可空
     */
    @ToString.Exclude
    private String sideImage;
    // 省略部分没有用到的字段
}
