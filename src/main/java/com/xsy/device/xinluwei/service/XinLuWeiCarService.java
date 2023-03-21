package com.xsy.device.xinluwei.service;

import com.xsy.device.config.DeviceConfigService;
import com.xsy.device.config.DeviceException;
import com.xsy.device.xinluwei.config.XinLuWeiCarDeviceConfig;
import com.xsy.device.xinluwei.config.XinLuWeiCarDeviceConfigs;
import com.xsy.device.xinluwei.entity.CarRecord;
import com.xsy.device.xinluwei.enums.XinLuWeiCarNoColorEnum;
import com.xsy.device.xinluwei.enums.XinLuWeiCarTypeEnum;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Q1sj
 * @date 2022.7.21 9:10
 */
@Slf4j
public abstract class XinLuWeiCarService implements DeviceConfigService<XinLuWeiCarDeviceConfig> {
    /**
     * 过滤间隔时间内相同数据
     */
    @Value("${device-configs.xinluwei.car.filter.interval-minute:2}")
    public int filterIntervalMinute;
    /**
     * 过滤数据时是否匹配车牌颜色
     */
    @Value("${device-configs.xinluwei.car.filter.match-car-no-color:true}")
    public boolean filterMatchCarNoColor;
    /**
     * key deviceCode
     */
    private final Map<String, List<CarInfo>> map = new HashMap<>();

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
        carInfo.setSpecialClass(carRecord.getSpecialClass());

        String uploadTimeStr = carRecord.getUploadTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            carInfo.setRecordTime(sdf.parse(uploadTimeStr));
        } catch (ParseException e) {
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss.SSS");
            try {
                carInfo.setRecordTime(sdf.parse(uploadTimeStr));
            } catch (ParseException e1) {
                log.warn("信路威卡口记录时间解析失败{} 使用服务器时间", uploadTimeStr);
                carInfo.setRecordTime(new Date());
            }
        }
        carInfo.setHeadImageBase64(carRecord.getHeadImage());
        if (filterDuplicateData(deviceCode, carInfo)) {
            return;
        }
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

    public int getFilterIntervalMinute() {
        return filterIntervalMinute;
    }

    public boolean isFilterMatchCarNoColor() {
        return filterMatchCarNoColor;
    }

    public void setFilterIntervalMinute(int filterIntervalMinute) {
        this.filterIntervalMinute = filterIntervalMinute;
    }

    public void setFilterMatchCarNoColor(boolean filterMatchCarNoColor) {
        this.filterMatchCarNoColor = filterMatchCarNoColor;
    }

    /**
     * 过滤重复数据
     *
     * @param deviceCode
     * @param carInfo
     * @return
     */
    private boolean filterDuplicateData(String deviceCode, CarInfo carInfo) {
        synchronized (XinLuWeiCarService.class) {
            // 根据设备id获取数据缓存
            List<CarInfo> cacheList = map.computeIfAbsent(deviceCode, key -> new ArrayList<>());
            int filterIntervalMinute = getFilterIntervalMinute();
            // 删除超时间隔时间的无效数据
            cacheList.removeIf(c -> c.recordTime.before(DateUtils.addMinutes(carInfo.recordTime, -filterIntervalMinute)));
            for (CarInfo item : cacheList) {
                if (Objects.equals(item.carNo, carInfo.carNo)) {
                    if (!isFilterMatchCarNoColor()) {
                        log.warn("间隔{}分钟内存在相同车牌号 {} 不处理", filterIntervalMinute, item.carNo);
                        return true;
                    } else if (Objects.equals(item.carNoColor, carInfo.carNoColor)) {
                        log.warn("间隔{}分钟内存在相同车牌号 {} 车牌颜色{} 不处理", filterIntervalMinute, item.carNo, item.carNoColor);
                        return true;
                    }
                }
            }
            cacheList.add(carInfo);
        }
        return false;
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
         * 危险品标志，例如：爆、腐等 没有null
         */
        private String specialClass;
        /**
         * 记录时间
         */
        private Date recordTime;
        /**
         * 车头大图 base64
         */
        @ToString.Exclude
        private String headImageBase64;
    }
}
