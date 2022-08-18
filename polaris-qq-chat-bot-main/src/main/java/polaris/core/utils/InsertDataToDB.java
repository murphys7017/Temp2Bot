package polaris.core.utils;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import polaris.core.DemoApplication;

/**
 * @deprecated
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Component
public class InsertDataToDB {
//    @Autowired
//    private SpecifyReplyService specifyReplyService;
//
//    @Autowired
//    private BanWordService banWordService;
//
//    @Autowired
//    private MusicListService musicListService;
//
//    @Test
//    public void downloadNeteaseCloudMusicList() throws InterruptedException, IOException {
//        String codePart1 = "[mirai:musicshare:NeteaseCloudMusic,";//+name+
//        String codePart2 = ",由Polaris分享,https\\://y.music.163.com/m/song?id=";//+id+
//        String codePart3 = "&uct=NFaoz9U6M8V9vlmB0estoA%3D%3D&app_version=8.7.11,https://m.qpic.cn/psc?/V548iLWq2KMuag360X0I36WOzl0bgVdZ/bqQfVz5yrrGYSXMvKr.cqQ0zrmqQw81QMm18McByfjemn2VWVLCrVPkQnvEGoYMXX6iSl*nPNu8EVDia6dsLF9mV9oJynrmaN3m.IhJ41JU!/b&bo=XgWwBLAF.AQDB4Q!&rf=viewer_4,http\\://music.163.com/song/media/outer/url?id=";//+id+
//        String codePart4 = "&userid=551118830&sc=wmv,\\[分享\\]";//+name+
//        String codePart5 = "]";
//        System.setProperty("webdriver.edge.driver","C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedgedriver.exe");
//        WebDriver driver = new EdgeDriver();
//        driver.get("https://y.music.163.com/m/playlist?id=826019094&userid=551118830&creatorId=551118830");
//        Thread.sleep(1000*30);
//        driver.navigate().refresh();
//        Thread.sleep(1000);
//        driver.switchTo().frame("g_iframe");
//        List<WebElement> webElementList = driver.findElements(By.xpath("//a[contains(@href,\"song?\")]"));
//        List<OneMusic> musicList = new ArrayList<>();
//        for (WebElement webElement : webElementList) {
//            String id = webElement.getAttribute("href").split("=")[1];
//            String name = webElement.findElement(By.tagName("b")).getAttribute("title");
//            String code = "";
//            code = codePart1+name+codePart2+id+codePart3+id+codePart4+name+codePart5;
//            musicList.add( new OneMusic(code,name,"","https://y.music.163.com/m/playlist?id=826019094&userid=551118830&creatorId=551118830"));
//        }
//
//        QueryWrapper<OneMusic> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("song_list","https://y.music.163.com/m/playlist?id=826019094&userid=551118830&creatorId=551118830");
//        musicListService.saveBatch(musicList);
//
//
//        driver.close();
//    }



}
