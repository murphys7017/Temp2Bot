package polaris.core.controller;

import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import polaris.core.function.FunctionLoader;
import polaris.core.function.Function;
import polaris.core.service.impl.BaseFunction;
import polaris.core.utils.MessageSender;
import xyz.polaris.function_interface.MessageEventHandlerInterface;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author polaris
 * @version 1.0
 */
@Component
public class MessageResponseController {
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");

    @Autowired
    BaseFunction baseFunction;


    public static ConcurrentLinkedQueue<MessageEvent> MessageQueue = new ConcurrentLinkedQueue<>();


    @Scheduled(fixedRate = 100)
    private void responseMessage() {
        for (int i = 0; i <MessageQueue.size();i++){
            // 是否发送消息标志
            boolean isSend = false;
            // 取出最新的消息事件
            MessageEvent messageEvent = MessageQueue.poll();
            // 消息对应的bot
            Bot bot = messageEvent.getBot();

            // ban检查
            if (!baseFunction.banWorkCheck(messageEvent)){
                // 非ban
                String msg = messageEvent.getMessage().get(PlainText.Key).contentToString();
                if (msg.startsWith(" ")){
                    msg = msg.substring(1);
                }

                // 比对内部方法的key 如果发送消息 打破循环
                for (String key :FunctionLoader.getInternalFunctionsMap().keySet()){
                    if (msg.contains(key) || msg.equals(key)) {
                        Function function = FunctionLoader.getInternalService(key);
                        isSend = function.getResponse(messageEvent);
                        if (isSend) {
                            break;
                        }

                    }
                }
                for (String key : RUN_VARIABLE.PLUGINS_MAP.keySet()) {
                    if (msg.contains(key)) {
                        MessageEventHandlerInterface function =RUN_VARIABLE.PLUGINS_MAP.get(key);
                        isSend = function.getResponse(messageEvent);
                        if (isSend) {
                            break;
                        }

                    }
                }

                // websocket plugins


                // 如果内部外部websocket都没有响应 使用固定回复
                if (!isSend) {
                    String response = RUN_VARIABLE.SPECIFIC_REPLY.get(msg);
                    if (response != null) {
                        MessageSender.toSend(messageEvent.getSubject(),response);
                        isSend = true;
                    }
                }
                // 如果固定回复没有 使用音乐分享
//                if (!isSend) {
//                    for (String s : RUN_VARIABLE.MUSIC_LIST.keySet()) {
//                        if (msg.contains(s)){
//                            messageEvent.getSubject().sendMessage(MiraiCode.deserializeMiraiCode(RUN_VARIABLE.MUSIC_LIST.get(s)));
//                        }
//                    }
//                }

                if (!isSend){
                    if (msg.equals(BOT_SET.Name)){
                        MessageSender.toSend(messageEvent.getSubject(),BOT_SET.WhenCalled);
                    }
                }


            }

        }

    }



}
