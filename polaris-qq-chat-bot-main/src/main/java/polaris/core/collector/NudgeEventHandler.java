package polaris.core.collector;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.NudgeEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import polaris.core.utils.MessageSender;

public class NudgeEventHandler extends SimpleListenerHost {
    Logger logger = LoggerFactory.getLogger("EventHandler");
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception){
        exception.printStackTrace();
        // 处理事件处理时抛出的异常
    }


    @NotNull
    @EventHandler
    public ListeningStatus onMessage(@NotNull NudgeEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
        // 如果是戳bot
        if (event.getTarget().getId() == event.getBot().getId()){
            // 如果历史记录中被这个人戳过
            if (RUN_VARIABLE.NUDGE_TIMES.containsKey(event.getFrom().getId())){
                // 被戳了几次
                int times = RUN_VARIABLE.NUDGE_TIMES.get(event.getFrom().getId());
                // 如果大于现有回复范围
                if (times > BOT_SET.ToNudgeSay.size()) {
                    if (times > BOT_SET.ToNudgeSay.size()+5) {
                        MessageSender.toSend(event.getSubject(),"这是你第"+times+"戳我了,放过我把::>_<::");
                    }else {
                        MessageSender.toSend(event.getSubject(),BOT_SET.ToNudgeSay.get(BOT_SET.ToNudgeSay.size() - 1));
                    }
                }else {
                    MessageSender.toSend(event.getSubject(),BOT_SET.ToNudgeSay.get(times-1));
                }
                RUN_VARIABLE.NUDGE_TIMES.put(event.getFrom().getId(),times+1);

            }else {
                // 如果没被戳过
                MessageSender.toSend(event.getSubject(),BOT_SET.ToNudgeSay.get(0));
                RUN_VARIABLE.NUDGE_TIMES.put(event.getFrom().getId(),1);
            }

        }
        return ListeningStatus.LISTENING; // 表示继续监听事件
        // return ListeningStatus.STOPPED; // 表示停止监听事件
    }

}
