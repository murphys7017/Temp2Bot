package polaris.core;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class YmlTest {
    @Test
    public void loadBotSetYml() throws IOException {
        Yaml yml = new Yaml();
        Map botSet = yml.load(FileUtils.openInputStream(new File("BotSet.yml")));
        BOT_SET.QQ = Long.valueOf(botSet.get("QQ").toString());
        BOT_SET.Password = botSet.get("Password").toString();

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
        BOT_SET.PluginFolder = botSet.get("PluginFolder").toString();
        BOT_SET.SeleniumDriverPath = botSet.get("SeleniumDriverPath").toString();
        BOT_SET.SeleniumDriverName = botSet.get("SeleniumDriverName").toString();
        BOT_SET.UnmuteSay = botSet.get("UnmuteSay").toString();
        BOT_SET.MemberJoinSay = botSet.get("MemberJoinSay").toString();
        BOT_SET.MemberLeaveSay = botSet.get("MemberLeaveSay").toString();
        BOT_SET.ToBannedNumberSay = botSet.get("ToBannedNumberSay").toString();
        BOT_SET.ToUnableBannedNumberSay = botSet.get("ToUnableBannedNumberSay").toString();
        BOT_SET.ToNudgeSay = (List<String>) botSet.get("ToNudgeSay");
        BOT_SET.WhenCalled = botSet.get("WhenCalled").toString();
        System.out.println(botSet);

    }
}
