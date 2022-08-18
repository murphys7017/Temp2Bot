package polaris.core.service.impl;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import polaris.core.mapper.NumberEventMapper;
import polaris.core.mapper.NumberPowerMapper;
import polaris.core.pojo.event.NumberEventEnum;
import polaris.core.pojo.event.NumberEventPojo;
import polaris.core.pojo.event.NumberPowerEnum;
import polaris.core.utils.FileLoader;
import polaris.core.utils.MessageSender;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Poalris
 * @version 1.0
 * 负责执行最基本的操作
 *  例如 违规词检查
 *      权限管理
 *      日志记录
 *      等
 */
@Component
public class BaseFunction {
    @Autowired
    NumberEventMapper numberEventMapper;

    @Autowired
    NumberPowerMapper numberPowerMapper;
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");


    /**
     * 是否为封禁词
     * @param messageEvent 收到的消息事件
     * @return 是否为封禁词
     * */
    public Boolean banWorkCheck(MessageEvent messageEvent){
        boolean isBanWorkCheck = false;
        String msg = messageEvent.getMessage().get(PlainText.Key).contentToString();
        for (String key : RUN_VARIABLE.BAN_WORD){
            if (msg.contains(key)) {
                logger.info("[BAN]  "+msg);
                isBanWorkCheck = true;
                MessageSender.toSend(messageEvent.getSubject(),BOT_SET.ToBannedNumberSay);
                numberEventMapper.addNumberEvent(new NumberEventPojo(messageEvent, NumberEventEnum.BAN));
                MessageSource.recall(messageEvent.getMessage());
            }
        }
        return isBanWorkCheck;
    }

    /**
     * 获取发送消息的人的权限
     * @param messageEvent  收到的消息事件
     * @return  消息发送人的权限
     */

    public NumberPowerEnum powerCheck(MessageEvent messageEvent){
        Bot bot = messageEvent.getBot();
        NumberPowerEnum numberPowerEnum = null;
        // try是否是群消息
        try {
            Group group = bot.getGroup(messageEvent.getSubject().getId());
            NormalMember sender = group.getMembers().get(messageEvent.getSender().getId());
            if ("ADMINISTRATOR".equals(sender.getPermission())) {
                numberPowerEnum = NumberPowerEnum.ADMINISTER;
            }else if ("OWNER".equals(sender.getPermission())) {
                numberPowerEnum = NumberPowerEnum.OWNER;
            }
        } catch (Exception ignored) {

        }

        if (numberPowerEnum == null) {
            String power = numberPowerMapper.getNumberPower(messageEvent.getSender().getId());
            if (power == null || power.equals("")){
                if ("LEVEL_GOOD".equals(power)) {
                    numberPowerEnum = NumberPowerEnum.LEVEL_GOOD;
                }else if ("LEVEL_ORDINARY".equals(power)) {
                    numberPowerEnum = NumberPowerEnum.LEVEL_ORDINARY;
                }else if ("LEVEL_BAD".equals(power)) {
                    numberPowerEnum = NumberPowerEnum.LEVEL_BAD;
                }
            }else {
                numberPowerEnum = NumberPowerEnum.LEVEL_GOOD;
            }

        }
        return numberPowerEnum;
    }




    public static void main(String[] args) {
        downloadNeteaseCloudMusicList("https://y.music.163.com/m/playlist?id=826019094&userid=551118830&creatorId=551118830");
    }

    public static void downloadNeteaseCloudMusicList(String url) {
        String codePart1 = "[mirai:musicshare:NeteaseCloudMusic,";//+name+
        String codePart2 = ",由"+ BOT_SET.Name +"分享,https\\://y.music.163.com/m/song?id=";//+id+
        String codePart3 = "&uct=NFaoz9U6M8V9vlmB0estoA%3D%3D&app_version=8.7.11,"+ BOT_SET.BotImage +",http\\://music.163.com/song/media/outer/url?id=";//+id+
        String codePart4 = "&userid=551118830&sc=wmv,\\[分享\\]";//+name+
        String codePart5 = "]";
        System.setProperty(BOT_SET.SeleniumDriverName, BOT_SET.SeleniumDriverPath);
        WebDriver driver = new EdgeDriver();
        driver.get(url);
        try {
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.navigate().refresh();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.switchTo().frame("g_iframe");
        List<WebElement> webElementList = driver.findElements(By.xpath("//a[contains(@href,\"song?\")]"));
        List<String> musicList = new ArrayList<>();
        for (WebElement webElement : webElementList) {
            String id = webElement.getAttribute("href").split("=")[1];
            String name = webElement.findElement(By.tagName("b")).getAttribute("title");
            String code = "";
            code = name+"->"+codePart1+name+codePart2+id+codePart3+id+codePart4+name+codePart5;
            code = code.replaceAll(" "," ");
            musicList.add(code);
        }

        try {
            FileUtils.writeLines(new File("work/TalkData/MusicData.txt"),musicList,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver.close();
    }


}