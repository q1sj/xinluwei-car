package com.xsy.device.xinluwei.enums;

import com.xsy.device.xinluwei.entity.CarRecord;
import lombok.AllArgsConstructor;

/**
 * @author Q1sj
 * @date 2022.7.21 11:21
 */
@AllArgsConstructor
public enum XinLuWeiCarTypeEnum {
    // 车型、参照交通部的收费车型（1~4 客1~客4 、11~16 货1~货6、专项车21~26 专项1~专项6）
    bus1("1"),
    bus2("2"),
    bus3("3"),
    bus4("4"),
    truck1("11"),
    truck2("12"),
    truck3("13"),
    truck4("14"),
    truck5("15"),
    truck6("16"),
    special1("21"),
    special2("22"),
    special3("23"),
    special4("24"),
    special5("25"),
    special6("26"),
    ;

    /**
     * {@link CarRecord#getCarModels()}
     */
    public final String code;

    public static XinLuWeiCarTypeEnum getByCode(String code) {
        for (XinLuWeiCarTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
