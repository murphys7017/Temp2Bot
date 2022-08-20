package polaris.core;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import xyz.polaris.function_interface.MessageEventHandlerInterface;

import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author polaris
 * @version 1.0
 */
public class RUN_VARIABLE {
    public static Bot BOT;
    public static java.util.Set<String> BAN_WORD = new HashSet<>();

    public static Map<String, String> CITY_CODE = new HashMap<>();
    public static Map<String, String> SPECIFIC_REPLY = new HashMap<>();

    public static Map<String,String> MUSIC_LIST = new HashMap<>();

    // 内置功能设置 计划去除
    public static Map<String, Object> FUNCTIONS_SET = new HashMap<>();


    public static Map<Long, Integer> NUDGE_TIMES = new HashMap<>();

    public static Map<String, MessageEventHandlerInterface> PLUGINS_MAP = new HashMap<>();

    public static Boolean RESPONSINGS = false;

    public static PluginManager PLUGINS_MANAGER;

}
