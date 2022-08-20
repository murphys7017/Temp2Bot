package xyz.polaris.plugin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
import java.util.List;

public class GetMusicData {
    public static void main(String[] args) {
        System.setProperty("webdriver.edge.driver", "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.get("https://y.music.163.com/m/playlist?id=826019094&userid=551118830&creatorId=551118830");
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
        List<MusicInfoPojo> musicInfoList = new ArrayList<>();
        for (WebElement element : driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"))) {
            MusicInfoPojo musicInfoPojo = new MusicInfoPojo();
            System.out.println(element.getText());
            musicInfoPojo.setMusicLink("https://music.163.com/#"+element.findElement(By.xpath("//*/td[2]/div/div/div/span/a")).getAttribute("href"));
            System.out.println(element.findElement(By.xpath("//*/td[2]/div/div/div/span/a")).getAttribute("title").split("-")[0].trim());
            musicInfoPojo.setTitle1(element.findElement(By.xpath("//*/td[2]/div/div/div/span/a")).getAttribute("title").split("-")[0].trim());
            musicInfoPojo.setTitle2(element.findElement(By.xpath("//*/td[2]/div/div/div/span/span")).getAttribute("title").trim());
            musicInfoPojo.setDuration(element.findElement(By.xpath("//*/td[3]/span")).getText());
            musicInfoPojo.setImageLink(element.findElement(By.xpath("//*/td[3]/div/span[2]")).getAttribute("data-res-pic"));
            musicInfoPojo.setSinger(element.findElement(By.xpath("//*/td[4]/div/span")).getAttribute("title"));
            musicInfoPojo.setSingetLink("https://music.163.com/#"+element.findElement(By.xpath("//*/td[4]/div/span/a")).getAttribute("href"));
            musicInfoPojo.setAlbum(element.findElement(By.xpath("//*/td[5]/div/a")).getAttribute("title"));
            musicInfoPojo.setAlbum("https://music.163.com/#"+element.findElement(By.xpath("//*/td[5]/div/a")).getAttribute("href"));

            musicInfoList.add(musicInfoPojo);
            System.out.println("***************************************************************");
        }
        System.out.println(musicInfoList);
    }
}
