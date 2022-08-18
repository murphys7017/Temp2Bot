package polaris.core.collector;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import polaris.core.BOT_SET;
import polaris.core.controller.MessageResponseController;

@Component
public class MessageEventHandler extends SimpleListenerHost {
    Logger logger = LoggerFactory.getLogger("EventHandler");
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception){
        exception.printStackTrace();
        // 处理事件处理时抛出的异常
    }


    @NotNull
    @EventHandler
    public ListeningStatus onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        Bot bot = event.getBot();
        if (bot.getGroups().contains(event.getSubject().getId())){
            if (BOT_SET.ApplyGroups.contains(event.getSubject().getId())) {
                MessageResponseController.MessageQueue.add(event);
            }
        }else {
            MessageResponseController.MessageQueue.add(event);
        }

        return ListeningStatus.LISTENING; // 表示继续监听事件
        // return ListeningStatus.STOPPED; // 表示停止监听事件
    }

}
