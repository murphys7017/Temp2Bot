package polaris.core.controller;

import net.mamoe.mirai.message.data.At;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import polaris.core.RUN_VARIABLE;
import polaris.core.mapper.NumberEventMapper;

import polaris.core.pojo.event.NumberEventPojo;
import polaris.core.utils.MessageSender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author polaris
 *
 */
@Component
public class ScheduleEventHandle {
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    @Autowired
    private NumberEventMapper numberEventMapper;


    //TODO
    @Scheduled(fixedRate = 1000*30)
    private void checkReminderMe() {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = dateFormat.format(date);
        List<NumberEventPojo> remindMeList = numberEventMapper.selectReaminEvent(nowTime.substring(0,10),nowTime.substring(11));

        if (remindMeList != null || !remindMeList.isEmpty()){
            remindMeList.forEach(numberEventPojo -> {
                try {
                    logger.info(String.valueOf(dateFormat.parse(numberEventPojo.getOccurrenceDate()+" "+numberEventPojo.getOccurrenceTime()).getTime() - System.currentTimeMillis() < 1000*30));
                    if (dateFormat.parse(numberEventPojo.getOccurrenceDate()+" "+numberEventPojo.getOccurrenceTime()).getTime() - System.currentTimeMillis() < 1000*30){
                        At at = new At(numberEventPojo.getQq());
                        if (numberEventPojo.getQq().equals(numberEventPojo.getGroup())){
                            MessageSender.toSend(RUN_VARIABLE.BOT.getFriend(numberEventPojo.getQq()),numberEventPojo.getRemark());
                        }else {
                            RUN_VARIABLE.BOT.getFriend(numberEventPojo.getQq()).sendMessage(at.plus(numberEventPojo.getRemark()));
                        }
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        }else {
            logger.info("checkReminderMe  data is null");
        }
    }
}
