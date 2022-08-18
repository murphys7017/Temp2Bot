package polaris.core.function.impl;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polaris.core.RUN_VARIABLE;
import polaris.core.function.Function;
import polaris.core.init.LoadConfig;
import polaris.core.service.impl.BaseFunction;

import java.util.*;

@Service("MusicShare")
public class MusicShareImpl implements Function {
    @Autowired
    BaseFunction baseFunction;
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    List<String> CommandSet = null;
    boolean isUsing = true;
    boolean ifNeedAt = false;
    String ChangePlaylist = "";
    String Playlist = "";


    @Override
    public List<String> getFunctionKeys() {
        Map<String, Object> config = (Map<String, Object>) RUN_VARIABLE.FUNCTIONS_SET.get("MusicShare");
        this.CommandSet = (List<String>) config.get("CommandSet");
        this.isUsing = (boolean) config.get("IsUsing");
        this.ifNeedAt = (boolean) config.get("IfNeedAt");
        this.Playlist = config.get("Playlist").toString();
        this.ChangePlaylist = config.get("ChangePlaylist").toString();
        List<String> res = new ArrayList<>();
        res.addAll(CommandSet);
        res.add(ChangePlaylist);
        return res;
    }

    @Override
    public Boolean getResponse(MessageEvent event) {
        if (!isUsing){
            return false;
        }
        if (ifNeedAt){
            if (!event.getMessage().contains(At.Key)){
                return false;
            }
        }
        Bot bot = event.getBot();
        String msg = event.getMessage().get(PlainText.Key).contentToString();
        if (msg.startsWith(" ")){
            msg = msg.substring(1);
        }
        if (msg.equals(ChangePlaylist)) {
            event.getSubject().sendMessage("开始更新歌单,请在web启动后扫码登录网易云,并耐心等待");
            baseFunction.downloadNeteaseCloudMusicList(Playlist);
            LoadConfig.loadMusicList();
            return true;
        } else if(CommandSet.contains(msg)) {
            Random random = new Random();
            int n = random.nextInt(RUN_VARIABLE.MUSIC_LIST.size());
            event.getSubject().sendMessage(MiraiCode.deserializeMiraiCode(RUN_VARIABLE.MUSIC_LIST.get(n)));
            return true;
        }
        return false;

    }


}
