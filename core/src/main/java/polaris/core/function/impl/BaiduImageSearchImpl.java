package polaris.core.function.impl;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import polaris.core.function.Function;
import polaris.core.utils.SeleniumUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @deprecated
 */
//TODO
@Service("BaiduImageSearch")
public class BaiduImageSearchImpl implements Function {
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    List<String> responseList = new ArrayList<>();
    boolean isUsing = true;
    boolean ifNeedAt = false;
    String startCommand = "";
    String closeCommand = "";


    @Override
    public List<String> getFunctionKeys() {
        Map<String, Object> config = (Map<String, Object>) RUN_VARIABLE.FUNCTIONS_SET.get("BaiduImageSearch");
        this.responseList = (List<String>) config.get("ResponseList");
        this.isUsing = (boolean) config.get("IsUsing");
        this.ifNeedAt = (boolean) config.get("IfNeedAt");
        this.startCommand = config.get("StartCommand").toString();
        this.closeCommand = config.get("CloseCommand").toString();
        List<String> res = new ArrayList<>();
        res.addAll(responseList);
        res.add(startCommand);
        res.add(closeCommand);
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

        Image image = event.getMessage().get(Image.Key);
        logger.info(image.toString());
        // 创建
        WebDriver driver = SeleniumUtil.getSeleniumDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://graph.baidu.com/pcpage/index?tpl_from=pc");
            driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[7]/div/span[1]/input")).sendKeys(Image.queryUrl(image));
            driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[7]/div/span[2]")).click();
            Thread.sleep(3000);



            WebElement titleElement = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]"));
            if (titleElement.findElements(By.className("general-title")) == null){
                File screen = titleElement.getScreenshotAs(OutputType.FILE);
                image = ExternalResource.uploadAsImage(screen,event.getSubject());
                Thread.sleep(1000);
                event.getSubject().sendMessage(new MessageChainBuilder().append(new PlainText("百度百科:")).append(image).build());

                //图片来源
                List<WebElement> webElementList = driver.findElements(By.className("graph-same-list-item"));
                for (int i = 0; i < webElementList.size(); i++) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", webElementList.get(i));
                    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,200)");
                    File sc = webElementList.get(i).getScreenshotAs(OutputType.FILE);
                    String link = webElementList.get(i).findElement(By.tagName("a")).getAttribute("href");
                    image = ExternalResource.uploadAsImage(sc, event.getSubject());
                    Thread.sleep(1000);
                    event.getSubject().sendMessage(new MessageChainBuilder().append(new PlainText("图片来源"+(i+1)+":")).append(image).append(new PlainText("link:")).append(new PlainText(link)).build());

                }
            }else {
                event.getSubject().sendMessage("没有在百度百科找到相关信息");
                List<WebElement> webElementList = driver.findElements(By.className("graph-same-list-item"));
                for (int i = 0; i < webElementList.size(); i++) {
                    File sc = webElementList.get(i).getScreenshotAs(OutputType.FILE);
                    String link = webElementList.get(i).findElement(By.tagName("a")).getAttribute("href");
                    image = ExternalResource.uploadAsImage(sc,event.getSubject());
                    Thread.sleep(1000);
                    event.getSubject().sendMessage(new MessageChainBuilder().append(new PlainText("图片来源"+(i+1)+":")).append(image).append(new PlainText("link:")).append(new PlainText(link)).build());

                }
            }

            //相似图链接
            event.getSubject().sendMessage(new MessageChainBuilder().append(new PlainText("相似图链接:")).append(driver.findElement(By.className("general-waterfall")).findElement(By.tagName("a")).getAttribute("href")).build());

        } catch (InterruptedException e) {
            return false;
        } finally{
            driver.close();
        }
        return true;

    }

}
