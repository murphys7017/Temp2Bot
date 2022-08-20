package polaris.core.init;

import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.message.data.Audio;
import net.mamoe.mirai.utils.BotConfiguration;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * @author polaris.
 */
public class LoginBot {
    public static void defaultLogin(){
        if (BOT_SET.QQ != null && BOT_SET.Password != null) {
            RUN_VARIABLE.BOT = BotFactory.INSTANCE.newBot(BOT_SET.QQ, BOT_SET.Password,new BotConfiguration(){
                {
                    setWorkingDir(new File(BOT_SET.Workspace));
                    setCacheDir(new File(BOT_SET.CacheDir));
                    setHeartbeatStrategy(HeartbeatStrategy.STAT_HB);
                    if ("ANDROID_PHONE".equals(BOT_SET.Protocol)) {
                        setProtocol(MiraiProtocol.ANDROID_PHONE);
                    }else if ("ANDROID_PAD".equals(BOT_SET.Protocol)) {
                        setProtocol(MiraiProtocol.ANDROID_PAD);
                    }else if ("ANDROID_WATCH".equals(BOT_SET.Protocol)) {
                        setProtocol(MiraiProtocol.ANDROID_WATCH);
                    }
                    fileBasedDeviceInfo("DeviceInfo.json");
                    enableContactCache();
                    setLoginCacheEnabled(true);
                }
            });

            RUN_VARIABLE.BOT.login();

            for (String s : new String[]{"NudgeEventHandler", "MessageEventHandler", "MemberSpecialTitleChangeEventHandler", "MemberPermissionChangeEventHandler", "FriendInputStatusChangedEventHandler", "MemberMuteEventHandler","MemberUnmuteEventHandler","MemberJoinEventHandler","NudgeEventHandler","MemberLeaveEventHandler"}) {
                try {
                    RUN_VARIABLE.BOT.getEventChannel().registerListenerHost((ListenerHost) Class.forName("polaris.core.collector."+s).getDeclaredConstructor().newInstance());

                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
