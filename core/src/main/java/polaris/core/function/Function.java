package polaris.core.function;

import net.mamoe.mirai.event.events.MessageEvent;

import java.util.List;

public interface Function {
     List<String> getFunctionKeys();
     Boolean getResponse(MessageEvent event);

}
