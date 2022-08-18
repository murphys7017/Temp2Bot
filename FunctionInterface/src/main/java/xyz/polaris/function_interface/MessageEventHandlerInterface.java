package xyz.polaris.function_interface;

import org.pf4j.ExtensionPoint;

import java.util.List;
import net.mamoe.mirai.event.events.MessageEvent;
/**
 * @author poalris
 * @version 1.0.0
 */
public interface MessageEventHandlerInterface extends ExtensionPoint {
    // core获取插件触发的关键词
    List<String> getFunctionKeys();

    // core获取插件允许的成员权限
    String allowPower();

    // core获取响应
    Boolean getResponse(MessageEvent event);
}
