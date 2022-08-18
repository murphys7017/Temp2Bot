package polaris.core.function.impl;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polaris.core.RUN_VARIABLE;
import polaris.core.function.Function;
import polaris.core.mapper.NumberEventMapper;
import polaris.core.mapper.RemindMeMapper;
import polaris.core.pojo.event.NumberEventEnum;
import polaris.core.pojo.event.NumberEventPojo;
import polaris.core.service.impl.BaseFunction;
import polaris.core.utils.MessageSender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("RemindMe")
public class RemindMeImpl implements Function {
    @Autowired
    BaseFunction baseFunction;

    @Autowired
    RemindMeMapper remindMeMapper;

    @Autowired
    NumberEventMapper numberEventMapper;
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    List<String> commandSet = null;
    boolean isUsing = true;
    boolean ifNeedAt = false;
    String okSay = "";


    @Override
    public List<String> getFunctionKeys() {
        Map<String, Object> config = (Map<String, Object>) RUN_VARIABLE.FUNCTIONS_SET.get("RemindMe");
        this.commandSet = (List<String>) config.get("CommandSet");
        this.isUsing = (boolean) config.get("IsUsing");
        this.ifNeedAt = (boolean) config.get("IfNeedAt");
        this.okSay = config.get("OkSay").toString();
        List<String> res = new ArrayList<>();
        res.addAll(commandSet);
        return res;
    }

    // TODO
    @Override
    public Boolean getResponse(MessageEvent event) {
        if (!isUsing){
            return false;
        }
        if (ifNeedAt){
            if (!event.getMessage().contains(At.Key)){
                return false;
            }
        }
        Bot bot = event.getBot();
        String msg = event.getMessage().get(PlainText.Key).contentToString();
        if (msg.startsWith(" ")){
            msg = msg.substring(1);
        }
        for (String s : commandSet) {
            if (msg.contains(s)){
                String timeAndDate = msg.split(s)[0];
                String info = msg.split(s)[1];
                numberEventMapper.addNumberEvent(new NumberEventPojo(event, NumberEventEnum.REMIND,timeFormat(timeAndDate),info));
                MessageSender.toSend(event.getSubject(), okSay);
            }
        }


        return false;
    }





    public static void main(String[] args) throws ParseException {
        System.out.println(timeFormat("12点"));
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = dateFormat.format(date);

        String mString = nowTime.substring(0,10);
        System.out.println(mString);
        String hourString = nowTime.substring(11, 13);
        System.out.println(nowTime.substring(11));
    }

    private static String timeFormat(String time) {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = dateFormat.format(date);
        String mString = nowTime.substring(0,10);
        String hourString = nowTime.substring(11, 13);
        if (time.contains("-")){
            String[] timePart = time.split("-");
            String timeF = getFormatTimeString(timePart[1],hourString);
            if (timeF != null){
                timeF = nowTime.substring(0,8)+ timePart[0] +" "+timeF;
                return timeF;
            }
        }else {
            String timeF = getFormatTimeString(time,hourString);
            if (timeF != null){
                timeF = nowTime.substring(0,10) +" "+timeF;
                return timeF;
            }
        }
        return null;
    }
    private static String getFormatTimeString(String time, String hourString){
        if (time.contains("点")){
            String [] timePart = time.split("点");
            if (timePart.length == 1){
                return timePart[0]+":00:00";
            }else if (timePart.length == 2){
                if (timePart[1].contains("分")){
                    timePart[1] = timePart[1].split("分")[0];
                }
                return timePart[0]+":"+timePart[1]+":00";
            }
        }
        if (time.contains(":")){
            String [] timePart = time.split(":");
            if (timePart.length == 1){
                return timePart[0]+":00:00";
            }else if (timePart.length == 2){
                return timePart[0]+":"+timePart[1]+":00";
            }
        }
        if (time.contains(".")){
            String [] timePart = time.split("\\.");
            if (timePart.length == 1){
                return timePart[0]+":00:00";
            }else if (timePart.length == 2){
                return timePart[0]+":"+timePart[1]+":00";
            }
        }
        if (time.contains(" ")){
            String [] timePart = time.split(" ");
            if (timePart.length == 1){
                return timePart[0]+":00:00";
            }else if (timePart.length == 2){
                return timePart[0]+":"+timePart[1]+":00";
            }
        }
        return null;
    }
}
