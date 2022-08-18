package polaris.core.collector;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import polaris.core.BOT_SET;
import polaris.core.utils.MessageBuilder;

import java.util.Objects;

public class MemberLeaveEventHandler extends SimpleListenerHost {
    Logger logger = LoggerFactory.getLogger("EventHandler");
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        exception.printStackTrace();
        // 处理事件处理时抛出的异常
    }


    @NotNull
    @EventHandler
    public ListeningStatus onMessage(@NotNull MemberLeaveEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理

        event.getGroup().sendMessage(Objects.requireNonNull(MessageBuilder.buildMessageChain(event.getMember().getNameCard()+"&&"+ BOT_SET.MemberLeaveSay, event.getGroup())));
        return ListeningStatus.LISTENING; // 表示继续监听事件

        // return ListeningStatus.STOPPED; // 表示停止监听事件

    }
}
