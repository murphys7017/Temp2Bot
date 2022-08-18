package polaris.core.utils;

import net.mamoe.mirai.contact.Contact;
import polaris.core.BOT_SET;
import polaris.core.pojo.OneMessageEvent;
import polaris.core.pojo.OriginMessage;

import java.util.Objects;
/**
 * 消息发送模块
 * 发送普通文本消息或者带[]之类的
 * @author polaris
 */
public class MessageSender {
    private static Contact contact = null;
    private static int recallIn = -1;


    /**
     * @param subject Contact对象
     * @param response 单条消息
     * @return 是否发送成功
     */
    public static boolean toSend(Contact subject,String response) {
        contact = subject;

        if (response.startsWith(" ")) {
            response = response.substring(1);
        }
        if (response.contains("recallIn")){
            String[] m = response.split("recallIn");
            response = m[0];
            recallIn = Integer.parseInt(m[1]);
        }

        //TODO
        if (response.contains("[recall]")){
            recallIn = BOT_SET.RecallIn;
        }
        if (recallIn == -1){
            subject.sendMessage(Objects.requireNonNull(MessageBuilder.buildMessageChain(response, subject)));
            return true;
        }else if (recallIn > 0){
            subject.sendMessage(Objects.requireNonNull(MessageBuilder.buildMessageChain(response, subject))).recallIn(recallIn * 1000L);
            recallIn = -1;
            return true;
        }

        return false;
    }



    /**
     * @deprecated 废弃,存在逻辑问题,这个方法触发的是固定回复
     * @param event OneMessageEvent 消息事件
     * @return 是否发送成功
     */
    public static boolean toSend(OneMessageEvent event) {
        contact = event.getMessageEvent().getSubject();
        for (OriginMessage originMessage : event.getOriginMessageList()) {
            String msg = originMessage.getContent();
            toSend(contact,msg);
        }
        return false;
    }



}
