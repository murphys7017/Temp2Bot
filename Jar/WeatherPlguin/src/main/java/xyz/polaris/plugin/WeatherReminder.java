package xyz.polaris.plugin;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import xyz.polaris.plugin.pojo.DailyWeather;
import xyz.polaris.plugin.pojo.RealTimeWeather;
import xyz.polaris.plugin.pojo.WeatherWarining;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author polaris
 * 天气预警
 */
public class WeatherReminder {
    Map<Integer,List<String>> AttentionGroup = null;
    Map<Integer,List<String>> AttentionUser = null;

    Set<String> locationSet = new HashSet<>();
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    Set<String> warningsId = new HashSet<>();

    // TODO: change to schedule
    public WeatherReminder(){
        // 加载参数
        init();

        // 天气预警
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("查询是否有天气预警");
            try {
                getWeatherWarning();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        },0,2, TimeUnit.HOURS);


        // 天气提醒
        // TODO: TEST
        long oneDay = 24 * 60 * 60 * 1000;
        long initDelay  = getTimeMillis("07:00:00") - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("run 天气提醒");
            try {
                getWeatherInfo();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }, initDelay, oneDay, TimeUnit.MILLISECONDS);

    }

    public void init() {
        AttentionGroup = (Map<Integer, List<String>>) GetWeather.weatherService.configurations.get("AttentionGroup");
        AttentionUser = (Map<Integer, List<String>>) GetWeather.weatherService.configurations.get("AttentionUser");
        for (List<String> value : AttentionUser.values()) {
            locationSet.addAll(value);
        }
        for (List<String> value : AttentionGroup.values()) {
            locationSet.addAll(value);
        }
    }

    public void getWeatherWarning() {

        // 获取对应天气预警
        Map<String, List<WeatherWarining>> wariningMap = new HashMap<>();
        for (String s : locationSet) {
            wariningMap.put(s, GetWeather.weatherService.weatherWarinings(s));
        }

        // 发送消息
        for (Integer group : AttentionGroup.keySet()) {
            for (String key : AttentionGroup.get(group)) {
                for (WeatherWarining weatherWarining : wariningMap.get(key)) {
                    if (warningsId.add(weatherWarining.getId())) {
                        String msg = weatherWarining.getTitle() + "\n" +
                                weatherWarining.getSender() + "\n" +
                                weatherWarining.getText() + "\n" +
                                weatherWarining.getFxLink() + "\n";
                        GetWeather.bot.getGroup(group.longValue()).sendMessage(msg);
                    }

                }
            }
            for (Integer friend : AttentionUser.keySet()) {
                for (String key : AttentionUser.get(friend)) {
                    for (WeatherWarining weatherWarining : wariningMap.get(key)) {
                        if (warningsId.add(weatherWarining.getId())) {
                            String msg = weatherWarining.getTitle() + "\n" +
                                    weatherWarining.getSender() + "\n" +
                                    weatherWarining.getText() + "\n" +
                                    weatherWarining.getFxLink() + "\n";

                            GetWeather.bot.getFriend(friend.longValue()).sendMessage(msg);
                        }
                    }
                }
            }
        }
    }

    public void getWeatherInfo() throws IOException {
        // 将所有的地址放到一个set避免重复地址浪费使用次数

        Set<String> locationSet = new HashSet<>();
        for (List<String> value : AttentionUser.values()) {
            for (String s : value) {
                locationSet.add(s);
            }
        }
        for (List<String> value : AttentionGroup.values()) {
            for (String s : value) {
                locationSet.add(s);
            }
        }
        Map<String,List<DailyWeather>> infoMap = new HashMap<>();
        Map<String, RealTimeWeather> realTimeWeatherInfoMap = new HashMap<>();
        for (String s : locationSet) {
            infoMap.put(s,GetWeather.weatherService.dailyWeathers(s,3));
            realTimeWeatherInfoMap.put(s,GetWeather.weatherService.realTimeWeather(s));
        }

        // 发送消息
        for (Integer group : AttentionGroup.keySet()) {
            for (String key : AttentionGroup.get(group)) {
                List<DailyWeather> dailyWeatherList = infoMap.get(key);
                RealTimeWeather realTimeWeather = realTimeWeatherInfoMap.get(key);
                String msg = key+":\n" +
                              "当前天气 "+realTimeWeather.getText()+"   体感温度:"+realTimeWeather.getFeelsLike()+"\n" +
                              "今日天气:"+" 今天白天"+dailyWeatherList.get(0).getTextDay()+"   今天夜间:"+dailyWeatherList.get(0).getTextNight()+"  最高温\\最低温:"+dailyWeatherList.get(0).getTempMax()+"\\"+dailyWeatherList.get(0).getTempMin()+"\n" +
                              "明天天气:"+" 明天白天"+dailyWeatherList.get(1).getTextDay()+"   明天夜间:"+dailyWeatherList.get(1).getTextNight()+"  最高温\\最低温:"+dailyWeatherList.get(1).getTempMax()+"\\"+dailyWeatherList.get(1).getTempMin()+"\n" +
                                "详细信息:"+realTimeWeather.getFxLink();
                GetWeather.bot.getGroup(group.longValue()).sendMessage(msg);
            }
            for (Integer friend : AttentionUser.keySet()) {
                for (String key : AttentionUser.get(friend)) {
                    List<DailyWeather> dailyWeatherList = infoMap.get(key);
                    RealTimeWeather realTimeWeather = realTimeWeatherInfoMap.get(key);
                    String msg = key+":\n" +
                            "当前天气 "+realTimeWeather.getText()+"   体感温度:"+realTimeWeather.getFeelsLike()+"\n" +
                            "今日天气:"+" 今天白天"+dailyWeatherList.get(0).getTextDay()+"   今天夜间:"+dailyWeatherList.get(0).getTextNight()+"  最高温\\最低温:"+dailyWeatherList.get(0).getTempMax()+"\\"+dailyWeatherList.get(0).getTempMin()+"\n" +
                            "明天天气:"+" 明天白天"+dailyWeatherList.get(1).getTextDay()+"   明天夜间:"+dailyWeatherList.get(1).getTextNight()+"  最高温\\最低温:"+dailyWeatherList.get(1).getTempMax()+"\\"+dailyWeatherList.get(1).getTempMin()+"\n" +
                            "详细信息:"+realTimeWeather.getFxLink();

                    GetWeather.bot.getFriend(friend.longValue()).sendMessage(msg);
                }
            }
        }

    }


    /**
     * 获取指定时间对应的毫秒数
     * @param time "HH:mm:ss"
     * @return
     */
    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
