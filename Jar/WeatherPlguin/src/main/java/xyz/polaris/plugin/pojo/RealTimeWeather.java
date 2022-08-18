package xyz.polaris.plugin.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author polaris
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeWeather {

    private String fxLink;

    //能见度，默认单位：公里
    private String vis;

    // 温度
    private String temp;

    // 数据观测时间
    @JSONField(format = "yyyy-MM-dd'T'HH:mmXXX")
    private Date obsTime;

    // 天气状况和图标的代码，图标可通过天气状况和图标下载


    // 风向360角度
    private String wind360;

    // 风向
    private String windDir;

    // 大气压强，默认单位：百帕
    private String pressure;

    // 体感温度，默认单位：摄氏度
    private String feelsLike;

    // 云量，百分比数值。可能为空
    private String cloud;

    //当前小时累计降水量，默认单位：毫米
    private String precip;

    //露点温度。可能为空
    private String dew;

    // 相对湿度，百分比数值
    private String humidity;

    // 天气状况的文字描述，包括阴晴雨雪等天气状态的描述
    private String text;

    //风速，公里/小时
    private String windSpeed;

    //风力等级
    private String windScale;
}
