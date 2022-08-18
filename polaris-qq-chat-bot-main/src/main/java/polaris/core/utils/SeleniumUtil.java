package polaris.core.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import polaris.core.BOT_SET;

import java.util.HashMap;
import java.util.Map;

/**
 * @author polaris.zh
 * @version 1.0.0
 */
public class SeleniumUtil {
    public static WebDriver getSeleniumDriver(){
        System.setProperty(BOT_SET.SeleniumDriverName, BOT_SET.SeleniumDriverPath);
        WebDriver driver = null;
        if (BOT_SET.SeleniumDriverPath.contains("chromedriver")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");

            driver = new ChromeDriver(options);
        } else if (BOT_SET.SeleniumDriverPath.contains("msedgedriver")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");

            driver = new EdgeDriver(options);
        }
        return driver;
    }
}
