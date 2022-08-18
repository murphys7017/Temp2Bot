package polaris.core.pojo.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author polaris
 * @version 1.0
 * QQ用户事件类
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberEventPojo {
    private Long qq;
    private Long group;
    private NumberEventEnum eventType;
    private String occurrenceTime;
    private String occurrenceDate;

    private String remark;

    public NumberEventPojo(MessageEvent messageEvent, NumberEventEnum eventType) {
        this.eventType = eventType;
        this.qq = messageEvent.getSender().getId();
        this.group = messageEvent.getSubject().getId();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("HH:mm:ss");
        Date date = new Date();
        this.occurrenceTime = sdf.format(date);
        sdf.applyPattern("yyyy-MM-dd");
        this.occurrenceDate = sdf.format(date);
    }

    public NumberEventPojo(MessageEvent messageEvent, NumberEventEnum eventType,String time) {
        this.eventType = eventType;
        this.qq = messageEvent.getSender().getId();
        this.group = messageEvent.getSubject().getId();
        this.occurrenceTime = time.split(" ")[1];
        this.occurrenceDate = time.split(" ")[0];
    }
    public NumberEventPojo(MessageEvent messageEvent, NumberEventEnum eventType,String time,String remark) {
        this.eventType = eventType;
        this.qq = messageEvent.getSender().getId();
        this.group = messageEvent.getSubject().getId();
        this.occurrenceTime = time.split(" ")[1];
        this.occurrenceDate = time.split(" ")[0];
        this.remark = remark;
    }
}
