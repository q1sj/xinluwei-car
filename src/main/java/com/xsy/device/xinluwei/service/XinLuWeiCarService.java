package com.xsy.device.xinluwei.service;

import com.xsy.device.config.DeviceConfigService;
import com.xsy.device.config.DeviceException;
import com.xsy.device.xinluwei.config.XinLuWeiCarDeviceConfig;
import com.xsy.device.xinluwei.config.XinLuWeiCarDeviceConfigs;
import com.xsy.device.xinluwei.entity.CarRecord;
import com.xsy.device.xinluwei.enums.XinLuWeiCarTypeEnum;
import com.xsy.device.xinluwei.enums.XinLuWeiCarNoColorEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author Q1sj
 * @date 2022.7.21 9:10
 */
@Slf4j
public abstract class XinLuWeiCarService implements DeviceConfigService<XinLuWeiCarDeviceConfig> {
    /**
     * 将接口接收到数据转换为carInfo对象后调用此方法
     *
     * @param carInfo
     */
    public abstract void handle(XinLuWeiCarDeviceConfig config, CarInfo carInfo);

    /**
     * 处理信路威回调接口接收到的数据
     *
     * @param carRecord
     */
    public void handle(CarRecord carRecord) {
        Objects.requireNonNull(carRecord);
        String deviceCode = carRecord.getDeviceCode();
        if (StringUtils.isBlank(deviceCode)) {
            throw new DeviceException("DeviceCode不能为空");
        }
        XinLuWeiCarDeviceConfig config = XinLuWeiCarDeviceConfigs.getByDeviceCode(deviceCode);
        if (config == null) {
            log.error("deviceCode:{}不存在配置信息", deviceCode);
            return;
        }
        CarInfo carInfo = new CarInfo();
        carInfo.setCarNo(carRecord.getPlateNumber());
        carInfo.setCarNoColor(XinLuWeiCarNoColorEnum.getByCode(carRecord.getPlateColor()));
        carInfo.setCarType(XinLuWeiCarTypeEnum.getByCode(carRecord.getCarModels()));
        carInfo.setRecordTime(carRecord.getUploadTime());
        carInfo.setHeadImageBase64(carRecord.getHeadImage());
        this.handle(config, carInfo);
    }

    @Override
    public Class<XinLuWeiCarDeviceConfig> getConfigClass() {
        return XinLuWeiCarDeviceConfig.class;
    }

    @Override
    public void login(XinLuWeiCarDeviceConfig config) throws DeviceException {
        // 信路威设备对接方式为 在设备后台配置http回调接口,此方法用于缓存当前设备配置信息,没有真正登录操作
        config.setOnline(true);
        XinLuWeiCarDeviceConfigs.staticSet.add(config);
    }

    @Override
    public void logout(XinLuWeiCarDeviceConfig config) throws DeviceException {
        config.setOnline(false);
        XinLuWeiCarDeviceConfigs.staticSet.remove(config);
    }

    @Data
    protected static class CarInfo {
        /**
         * 车牌号
         */
        private String carNo;
        /**
         * 车牌颜色
         */
        private XinLuWeiCarNoColorEnum carNoColor;
        /**
         * 车辆类型
         */
        private XinLuWeiCarTypeEnum carType;
        /**
         * 记录时间
         */
        private Date recordTime;
        /**
         * 车头大图 base64
         */
        private String headImageBase64;
    }
}
