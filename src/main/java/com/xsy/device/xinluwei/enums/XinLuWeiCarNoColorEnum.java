package com.xsy.device.xinluwei.enums;

import com.xsy.device.xinluwei.entity.CarRecord;
import lombok.AllArgsConstructor;

/**
 * 信路威车牌颜色
 *
 * @author Q1sj
 * @date 2022.7.21 9:41
 */
@AllArgsConstructor
public enum XinLuWeiCarNoColorEnum {
    // 参照交通部标准(:0-蓝色1-黄色2-黑色3-白色，4- 渐变绿色5- 黄绿双拼色6- 蓝白渐变色7- 临时牌照9- 未确定11-绿色12-红色)
    BLUE("0", "蓝色"),
    YELLOW("1", "黄色"),
    BLACK("2", "黑色"),
    WHITE("3", "白色"),
    GRADIENT_GREEN("4", "渐变绿色"),
    YELLOW_GREEN("5", "黄绿双拼色"),
    BLUE_WHITE("6", "蓝白渐变色"),
    TEMPORARY("7", "临时牌照"),
    NOT_DETERMINED("9", "未确定"),
    GREEN("11", "绿色"),
    RED("12", "红色");

    /**
     * {@link CarRecord#getPlateColor()}
     */
    public final String code;
    public final String desc;

    public static XinLuWeiCarNoColorEnum getByCode(String code) {
        for (XinLuWeiCarNoColorEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return name() + "(" + code + "," + desc + ")";
    }
}
