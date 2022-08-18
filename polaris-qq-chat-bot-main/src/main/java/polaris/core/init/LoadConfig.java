package polaris.core.init;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import polaris.core.utils.FileLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author polaris.
 */
public class LoadConfig {
    public static void loadAll(){
        loadBanWords();
        loadCityCode();
        loadMusicList();
        loadSpecificReply();
        loadBotSet();
        LoginBot.defaultLogin();
        LoadPlugin.loadJar();
    }
    public static void loadBotSet(){
        Yaml yml = new Yaml();
        Map botSet = null;
        try {
            botSet = yml.load(FileUtils.openInputStream(new File("BotSet.yml")));
            RUN_VARIABLE.FUNCTIONS_SET = yml.load(FileUtils.openInputStream(new File("FunctionSet.yml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BOT_SET.QQ = Long.valueOf(botSet.get("QQ").toString());
        BOT_SET.Password = botSet.get("Password").toString();
        BOT_SET.ApplyGroups = new ArrayList<>();
        for (Integer a : (List<Integer>) botSet.get("ApplyGroups")) {
            BOT_SET.ApplyGroups.add(a.longValue());
        }
        BOT_SET.Admin = Long.valueOf(botSet.get("Admin").toString());
        BOT_SET.Protocol = botSet.get("Protocol").toString();
        BOT_SET.Name = botSet.get("Name").toString();
        BOT_SET.BotImage = botSet.get("BotImage").toString();
        BOT_SET.Introduction = botSet.get("Introduction").toString();
        BOT_SET.WebSocketPort = Integer.parseInt(botSet.get("WebSocketPort").toString());
        BOT_SET.RecallIn = Integer.parseInt(botSet.get("RecallIn").toString());
        BOT_SET.SensitiveWordsBanned = Integer.parseInt(botSet.get("SensitiveWordsBanned").toString());
        BOT_SET.BanWordFilePath = botSet.get("BanWordFilePath").toString();
        BOT_SET.SpecificReplyFilePath = botSet.get("SpecificReplyFilePath").toString();
        BOT_SET.JarPlugin = (List<Map<String, String>>) botSet.get("JarPlugin");
        BOT_SET.SeleniumDriverPath = botSet.get("SeleniumDriverPath").toString();
        BOT_SET.SeleniumDriverName = botSet.get("SeleniumDriverName").toString();
        BOT_SET.UnmuteSay = botSet.get("UnmuteSay").toString();
        BOT_SET.MemberJoinSay = botSet.get("MemberJoinSay").toString();
        BOT_SET.MemberLeaveSay = botSet.get("MemberLeaveSay").toString();
        BOT_SET.ToBannedNumberSay = botSet.get("ToBannedNumberSay").toString();
        BOT_SET.ToUnableBannedNumberSay = botSet.get("ToUnableBannedNumberSay").toString();
        BOT_SET.ToNudgeSay = (List<String>) botSet.get("ToNudgeSay");
        BOT_SET.WhenCalled = botSet.get("WhenCalled").toString();
    }
    public static void loadCityCode(){
        Map<String,String> specificReply = FileLoader.loadSpecificReplyFile(BOT_SET.Workspace+ File.separator + "TalkData"+File.separator + "cityData.txt");
        if (!specificReply.isEmpty()) {
            RUN_VARIABLE.SPECIFIC_REPLY = specificReply;
        }
    }
    public static void loadSpecificReply() {
        Map<String,String> specificReply = FileLoader.loadSpecificReplyFile(BOT_SET.SpecificReplyFilePath);
        if (!specificReply.isEmpty()) {
            RUN_VARIABLE.SPECIFIC_REPLY = specificReply;
        }

    }

    public static void loadBanWords() {
        File file = new File(BOT_SET.BanWordFilePath);
        try {
            RUN_VARIABLE.BAN_WORD.addAll(FileUtils.readLines(file,"UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void loadMusicList() {
        try {
            List<String> list = FileUtils.readLines(new File("work/TalkData/MusicData.txt"),"UTF-8");
            Map<String,String> map = new HashMap<>();
            list.forEach(key -> {
                map.put(key.split("->")[0],key.split("->")[1]);
            });
            RUN_VARIABLE.MUSIC_LIST = map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
