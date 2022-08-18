package polaris.core;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;

public class SeleniumTest {
    @Test
    public void weiboTok10() throws InterruptedException, IOException {
        System.setProperty("webdriver.edge.driver","C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.get("https://tophub.today/n/KqndgxeLl9");
        Thread.sleep(1000);
        WebElement webElement = driver.findElement(By.xpath("//*[@id=\"page\"]/div[2]/div[2]/div[1]/div[2]/div"));

        Long width = (Long) ((JavascriptExecutor)driver).executeScript("return document.documentElement.scrollWidth");
        Long height = (Long) ((JavascriptExecutor)driver).executeScript("return document.documentElement.scrollHeight");
        //设置浏览器弹窗页面的大小
        driver.manage().window().setSize(new Dimension(width.intValue(), height.intValue()));

        File srcFile = webElement.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("jj.png"));

    }
}
