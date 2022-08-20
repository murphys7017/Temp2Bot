package xyz.polaris.plugin;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;

import net.mamoe.mirai.message.data.PlainText;
import org.pf4j.Extension;
import xyz.polaris.function_interface.MessageEventHandlerInterface;
import xyz.polaris.plugin.pojo.DailyWeather;
import xyz.polaris.plugin.pojo.RealTimeWeather;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author polaris
 * @version 0.0.1
 */
@Extension
public class GetWeather implements MessageEventHandlerInterface {
    public static Bot bot = null;
    public static WeatherService weatherService;
    private Boolean isFirst = true;



    @Override
    public List<String> getFunctionKeys() {
        try {
            weatherService = new WeatherService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList("天气", "今天天气怎么样");
    }

    @Override
    public String allowPower() {
        return null;
    }

    @Override
    public Boolean getResponse(MessageEvent messageEvent) {
        bot = messageEvent.getBot();
        if (isFirst){
            WeatherReminder weatherReminder = new WeatherReminder();
            isFirst = false;
        }
        String msg = messageEvent.getMessage().get(PlainText.Key).contentToString();
        String location = msg.split("天气")[0];
        try {
            RealTimeWeather realTimeWeather = weatherService.realTimeWeather(location);
            List<DailyWeather> dailyWeathers = weatherService.dailyWeathers(location,3);
            String res = "地点:" +location+"\n" +
                    "==**实时天气**==\n"+
                    "数据获取时间:"+realTimeWeather.getObsTime()+"\n" +
                    "当前天气:"+realTimeWeather.getText()+"\n" +
                    "体感温度:"+realTimeWeather.getFeelsLike()+"\n" +
                    realTimeWeather.getWindDir()+" "+realTimeWeather.getWindScale()+"级\n" +
                    "详细信息请见:"+realTimeWeather.getFxLink()+"\n"+
                    "==**3日天气**==\n";
            for (DailyWeather dailyWeather : dailyWeathers) {
                res =res + "==*"+dailyWeather.getFxDate()+"*==\n" +
                        "白天:"+dailyWeather.getTextDay()+"   夜间:"+dailyWeather.getTextNight()+"\n" +
                        "白天 风向:"+dailyWeather.getWindDirDay()+"  风速"+dailyWeather.getWindScaleDay()+"级\n" +
                        "夜间 风向:"+dailyWeather.getWindDirNight()+"  风速"+dailyWeather.getWindScaleNight()+"级\n" +
                        "最高温:"+dailyWeather.getTempMax()+"    最低温:"+dailyWeather.getTempMax()+"\n" +
                        "预计降水量:"+dailyWeather.getPrecip()+"\n"+
                        "能见度:"+dailyWeather.getVis()+"公里\n" +
                        "相对湿度:"+dailyWeather.getHumidity()+"\n" +
                        "紫外线指数:"+dailyWeather.getUvIndex()+"\n" +
                        "云量:"+dailyWeather.getCloud()+"\n"+
                        "日出时间:"+dailyWeather.getSunrise()+"   日落时间:"+dailyWeather.getSunset()+"\n"+
                        "详细信息请见:"+dailyWeather.getFxLink()+"\n";
            }
            messageEvent.getSubject().sendMessage(res.replaceAll("00:00:00 ",""));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
