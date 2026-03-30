package com.xsy.device.xinluwei.service;

import com.xsy.device.config.DeviceConfigService;
import com.xsy.device.config.DeviceException;
import com.xsy.device.xinluwei.config.XinLuWeiCarDeviceConfig;
import com.xsy.device.xinluwei.config.XinLuWeiCarDeviceConfigs;
import com.xsy.device.xinluwei.entity.XinLuWeiCarSnapshotV2;
import com.xsy.device.xinluwei.enums.XinLuWeiCarNoColorEnum;
import com.xsy.device.xinluwei.enums.XinLuWeiCarTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author Q1sj
 * @date 2022.7.21 9:10
 */
@Slf4j
public abstract class XinLuWeiCarV2Service implements DeviceConfigService<XinLuWeiCarDeviceConfig> {

	public abstract void handle(XinLuWeiCarDeviceConfig config, CarInfo carInfo);

	public void handle(XinLuWeiCarSnapshotV2 car) {
		CarInfo carInfo = new CarInfo();
		carInfo.setGuid(car.getGuid());
		carInfo.setCarNo(car.getVehiclePlate());
		carInfo.setCarNoColor(XinLuWeiCarNoColorEnum.getByCode(Objects.toString(car.getPlateColor())));
		carInfo.setCarType(XinLuWeiCarTypeEnum.getByCode(car.getVehicleType()));
		carInfo.setDangerous(car.getDangerous());
		Date recordTime;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			recordTime = sdf.parse(car.getPassTime());
		} catch (ParseException e) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				recordTime = sdf.parse(car.getPassTime());
			} catch (ParseException ex) {
				log.error("解析时间失败,passTime={}", car.getPassTime());
				recordTime = new Date();
			}
		}
		carInfo.setRecordTime(recordTime);
		carInfo.setPlateImageUrl(car.getPlateImage());
		carInfo.setHeadImageUrl(car.getHeadImage());
		carInfo.setSideImageUrl(car.getSideImage());
		carInfo.setTailImageUrl(car.getTailImage());
		carInfo.setCarColor(car.getCarColor());
		XinLuWeiCarDeviceConfig deviceConfig = XinLuWeiCarDeviceConfigs.getByDeviceCode(car.getDeviceSn());
		handle(deviceConfig, carInfo);
	}

	@Override
	public Class<XinLuWeiCarDeviceConfig> getConfigClass() {
		return XinLuWeiCarDeviceConfig.class;
	}

	@Override
	public void login(XinLuWeiCarDeviceConfig config) throws DeviceException {
		// 信路威设备对接方式为 在设备后台配置http回调接口,此方法用于缓存当前设备配置信息,没有真正登录操作
		config.setOnline(true);
		config.setVersion("v2");
		XinLuWeiCarDeviceConfigs.staticSet.add(config);
	}

	@Override
	public void logout(XinLuWeiCarDeviceConfig config) throws DeviceException {
		config.setOnline(false);
		XinLuWeiCarDeviceConfigs.staticSet.remove(config);
	}

	@Data
	public static class CarInfo {
		/**
		 * 数据ID，唯一值
		 */
		private String guid;
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
		private String dangerous;
		/**
		 * 记录时间
		 */
		private Date recordTime;
		/**
		 * 车牌图片 url
		 */
		private String plateImageUrl;
		/**
		 * 车头大图 url
		 */
		private String headImageUrl;
		/**
		 * 车身大图 url
		 */
		private String sideImageUrl;
		/**
		 * 车尾大图 url
		 */
		private String tailImageUrl;
		/**
		 * 车身颜色
		 */
		private String carColor;
	}
}
