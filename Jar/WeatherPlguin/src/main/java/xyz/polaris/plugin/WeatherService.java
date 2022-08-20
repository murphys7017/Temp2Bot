package xyz.polaris.plugin;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import xyz.polaris.plugin.pojo.City;
import xyz.polaris.plugin.pojo.DailyWeather;
import xyz.polaris.plugin.pojo.RealTimeWeather;
import xyz.polaris.plugin.pojo.WeatherWarining;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author polaris
 */
public class WeatherService {
    private String API_KEY = "";
    public Map<String, Object> configurations = null;

    /**
     * init
     * 加载配置文件
     * @throws IOException
     */
    public WeatherService() throws IOException {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        Yaml yaml = new Yaml();
        Map<String, Object> objectMap = yaml.load(FileUtils.openInputStream(new File(path.replace(".jar",".yml"))));
        configurations = objectMap;
        API_KEY = objectMap.get("KEY").toString();
    }

    /**
     * 获取城市id
     * @param location
     *        城市名称 支持模糊查询
     * @return
     * @throws IOException
     */
    public List<City> getLocation(String location) throws IOException {
        location = java.net.URLEncoder.encode(location, StandardCharsets.UTF_8);
        Map<String, String> params = new HashMap<>();
        params.put("location",location);
        params.put("key",API_KEY);
        return getLocation(params);
    }

    /**
     * 获取城市id
     * @param params
     *          参数map
     *          包括 key 地名 等其他参数
     * @return
     * @throws IOException
     */
    public List<City> getLocation(Map<String, String> params) throws IOException {
        String url = ApiEnum.LOACTION_API.getPath(params);
        JSONObject jsonObject = HttpUtil.sendGet(url);
        if (jsonObject.get("code").toString().equals("200")){
            JSONArray jsonArray = jsonObject.getJSONArray("location");
            List<City> cityList = jsonArray.toJavaList(City.class);
            return cityList;
        }else {
            return null;
        }
    }

    /**
     * 获取实时天气
     * @param location
     * @return
     * @throws IOException
     */
    public RealTimeWeather realTimeWeather(String location) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("key",API_KEY);
        params.put("location",getLocation(location).get(0).getId());
        String url = ApiEnum.REAL_TIME_WEATHER_API.getPath(params);
        JSONObject jsonObject = HttpUtil.sendGet(url);
        if (jsonObject.get("code").toString().equals("200")){
            String fxLink = jsonObject.get("fxLink").toString();
            RealTimeWeather realTimeWeather = jsonObject.getJSONObject("now").toJavaObject(RealTimeWeather.class);
            realTimeWeather.setFxLink(fxLink);
            return realTimeWeather;
        }else {
            return null;
        }
    }

    /**
     * 获取3天或者7天天气
     * @param location
     * @param threeOrSeven
     *          3 或者 7
     * @return
     * @throws IOException
     */
    public List<DailyWeather> dailyWeathers(String location,int threeOrSeven) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("key",API_KEY);
        params.put("location",getLocation(location).get(0).getId());
        String url = null;
        if (threeOrSeven == 3){
            url = ApiEnum.THREE_DAYS_WEATHER_API.getPath(params);
        }else if (threeOrSeven == 7){
            url = ApiEnum.SEVEN_DAYS_WEATHER_API.getPath(params);
        }
        JSONObject jsonObject = HttpUtil.sendGet(url);
        if (jsonObject.get("code").toString().equals("200")){
            String fxLink = jsonObject.get("fxLink").toString();
            JSONArray jsonArray = jsonObject.getJSONArray("daily");
            List<DailyWeather> dailyWeatherList = jsonArray.toJavaList(DailyWeather.class);
            dailyWeatherList.forEach(dailyWeather -> {
                dailyWeather.setFxLink(fxLink);
            });
            return dailyWeatherList;
        }else {
            return null;
        }
    }

    /**
     * 获取当前地区预警信息
     * @return
     */
    public List<WeatherWarining> weatherWarinings(String location) {
        Map<String, String> params = new HashMap<>();
        params.put("key", API_KEY);
        try {
            params.put("location", getLocation(location).get(0).getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<WeatherWarining> weatherWariningList = new ArrayList<>();
        try {
            String url = ApiEnum.WEATHER_DIDASTER_WARNING_API.getPath(params);
            JSONObject jsonObject = HttpUtil.sendGet(url);
            if (jsonObject.get("code").toString().equals("200")) {
                JSONArray jsonArray = jsonObject.getJSONArray("warning");
                weatherWariningList = jsonArray.toJavaList(WeatherWarining.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return weatherWariningList;
    }
}
