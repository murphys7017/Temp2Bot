package polaris.core.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import polaris.core.BOT_SET;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author poalris
 * @version 0.0.1
 * @deprecated
 */
public class DownloadMusicList {
    public static void downloadNeteaseCloudMusicList(String url) throws InterruptedException, IOException {
        String codePart1 = "[mirai:musicshare:NeteaseCloudMusic,";//+name+
        String codePart2 = ",由"+ BOT_SET.Name+"分享,https\\://y.music.163.com/m/song?id=";//+id+
        String codePart3 = "&uct=NFaoz9U6M8V9vlmB0estoA%3D%3D&app_version=8.7.11,"+BOT_SET.BotImage+",http\\://music.163.com/song/media/outer/url?id=";//+id+
        String codePart4 = "&userid=551118830&sc=wmv,\\[分享\\]";//+name+
        String codePart5 = "]";
        System.setProperty(BOT_SET.SeleniumDriverName,BOT_SET.SeleniumDriverPath);
        WebDriver driver = SeleniumUtil.getSeleniumDriver();
        driver.get(url);
        Thread.sleep(1000*30);
        driver.navigate().refresh();
        Thread.sleep(1000);
        driver.switchTo().frame("g_iframe");
        List<WebElement> webElementList = driver.findElements(By.xpath("//a[contains(@href,\"song?\")]"));
        List<String> musicList = new ArrayList<>();
        for (WebElement webElement : webElementList) {
            System.out.println( webElement.toString());

            Map<String,String> stringMap = new HashMap<>();
            String id = webElement.getAttribute("href").split("=")[1];
            String name = webElement.findElement(By.tagName("b")).getAttribute("title");
            String code = "";
            code = codePart1+name+codePart2+id+codePart3+id+codePart4+name+codePart5;
            musicList.add(code);
        }

        FileUtils.writeLines(new File("work/TalkData/MusicData.txt.bk"),musicList,false);
        driver.close();
    }
}
