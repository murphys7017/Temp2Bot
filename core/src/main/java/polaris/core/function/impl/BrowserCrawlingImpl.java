package polaris.core.function.impl;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
@Service("BrowserCrawling")
public class BrowserCrawlingImpl implements Function {
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    List<String> UrlList = null;
    List<String> XpathList = null;
    List<String> TriggerWordList = null;
    boolean isUsing = true;
    boolean ifNeedAt = false;


    @Override
    public List<String> getFunctionKeys() {
        Map<String, Object> config = (Map<String, Object>) RUN_VARIABLE.FUNCTIONS_SET.get("BrowserCrawling");
        this.UrlList = (List<String>) config.get("UrlList");
        this.XpathList = (List<String>) config.get("XpathList");
        this.TriggerWordList = (List<String>) config.get("TriggerWordList");
        this.isUsing = (boolean) config.get("IsUsing");
        this.ifNeedAt = (boolean) config.get("IfNeedAt");
        List<String> res = new ArrayList<>();
        res.addAll(TriggerWordList);
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

        int index = TriggerWordList.indexOf(msg);
        if (index == -1){
            return false;
        }else {
            getWebPage(event.getSubject(), index);
            return true;
        }
    }



    private void getWebPage(Contact contact,int index){
        WebDriver driver = SeleniumUtil.getSeleniumDriver();
        try {
            driver.get(UrlList.get(index));
            Thread.sleep(1000);
            WebElement webElement = driver.findElement(By.xpath(XpathList.get(index)));

            Long width = (Long) ((JavascriptExecutor)driver).executeScript("return document.documentElement.scrollWidth");
            Long height = (Long) ((JavascriptExecutor)driver).executeScript("return document.documentElement.scrollHeight");
            //设置浏览器弹窗页面的大小
            driver.manage().window().setSize(new Dimension(width.intValue(), height.intValue()));

            if (webElement != null){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", webElement);
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,200)");
                File screen = webElement.getScreenshotAs(OutputType.FILE);
                if (screen.exists()){
                    Image image = ExternalResource.uploadAsImage(screen,contact);
                    Thread.sleep(1000);
                    contact.sendMessage(new MessageChainBuilder().append(image).build());
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            driver.close();
        }

    }
}
