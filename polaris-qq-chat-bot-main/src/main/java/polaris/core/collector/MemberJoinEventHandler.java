package polaris.core.collector;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import polaris.core.BOT_SET;
import polaris.core.utils.MessageBuilder;

public class MemberJoinEventHandler extends SimpleListenerHost {
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        exception.printStackTrace();
        // 处理事件处理时抛出的异常
    }


    @NotNull
    @EventHandler
    public ListeningStatus onMessage(@NotNull MemberJoinEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理

        logger.info(String.valueOf(event));
        MessageChainBuilder messageChainBuilder = MessageBuilder.buildMessageChainBuilder(BOT_SET.MemberJoinSay,event.getGroup());
        messageChainBuilder.append(new At(event.getMember().getId()));
        event.getGroup().sendMessage(messageChainBuilder.build());

        return ListeningStatus.LISTENING; // 表示继续监听事件

        // return ListeningStatus.STOPPED; // 表示停止监听事件

    }
}