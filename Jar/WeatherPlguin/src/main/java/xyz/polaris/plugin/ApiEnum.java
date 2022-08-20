package xyz.polaris.plugin;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public enum ApiEnum {
    LOACTION_API("https://geoapi.qweather.com/v2/city/lookup?"),
    REAL_TIME_WEATHER_API("https://devapi.qweather.com/v7/weather/now?"),
    THREE_DAYS_WEATHER_API("https://devapi.qweather.com/v7/weather/3d?"),
    SEVEN_DAYS_WEATHER_API("https://devapi.qweather.com/v7/weather/7d?"),
    HOURLY_WEATHER_API("https://devapi.qweather.com/v7/weather/24h?"),
    WEATHER_DIDASTER_WARNING_API("https://devapi.qweather.com/v7/warning/now?"),
    SUNRISE_SUNSET_API("https://devapi.qweather.com/v7/astronomy/sun?"),
    MOON_INFO_API("https://devapi.qweather.com/v7/astronomy/moon?");



    private String apiUrl;
    private ApiEnum(String path) {
        this.apiUrl = path;
    }

    public String getPath(Map<String,String> params) throws UnsupportedEncodingException {
        StringBuilder res = new StringBuilder();
        res.append(this.apiUrl);
        for (String s : params.keySet()) {
            res.append(s).append("=").append(params.get(s)).append("&");
        }
        return res.toString();
    }
}
