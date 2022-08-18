package xyz.polaris.plugin.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author polaris
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    private String id;

    private String name;
    // 地区/城市的属性

    private String type;
    // 评分
    private int rank;

    // 所在国家
    private String country;

    // 该地的天气预报网页链接，便于嵌入你的网站或应用
    private String fxLink;

    // 地区/城市目前与UTC时间偏移的小时数
    private String utcOffset;

    // 时区
    private String tz;
    /**
     * 地区/城市是否当前处于夏令时
     * 1 表示当前处于夏令时
     * 0 表示当前不是夏令时
     */
    private String isDst;

    // 地区/城市所属一级行政区域
    private String adm1;
    // 地区/城市的上级行政区划名称
    private String adm2;

    // 纬
    private String lat;
    // 经
    private String lon;
}
