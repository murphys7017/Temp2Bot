package polaris.core;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class BOT_SET {
    // 账号
    public static Long QQ;
    // 密码
    public static String Password;
    // 登录协议
    public static String Protocol = "ANDROID_PHONE";
    // bot name
    public static String Name = "Polaris";
    public static String BotImage;
    // bot的介绍
    public static String Introduction;
    // 工作路径
    public static String Workspace = "work";
    // 缓存路径
    public static String CacheDir = "cache";
    // 聊天功能的websocket端口
    public static int WebSocketPort = 8875;
    // 可以通过那个qq进行管理
    public static Long Admin;

    // 对那些群生效
    public static List<Long> ApplyGroups;
    // 被解除禁言时
    public static String UnmuteSay;
    // 群聊成员加入说
    public static String MemberJoinSay;
    // 如何处理违规
    public static int SensitiveWordsBanned;

    public static String BanWordFilePath = "work\\TalkData\\BanWords.txt";
    public static String SpecificReplyFilePath = "work\\TalkData\\SpecificReply.txt";
    public static List<Map<String, String>> JarPlugin;
    // 对被ban的人说
    public static String ToBannedNumberSay;
    public static String ToUnableBannedNumberSay;
    // 戳一戳
    public static List<String> ToNudgeSay;
    public static int RecallIn;
    public static String SeleniumDriverPath = "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedgedriver.exe";
    public static String SeleniumDriverName = "webdriver.edge.driver";
    public static Long FrequencyOfSpeeches = 500L;
    public static String WhenCalled;
    public static String MemberLeaveSay;


}