package polaris.core.pojo.set.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowserCrawling {
    public Boolean IsUsing;
    public Boolean IfNeedAt;
    public List<String> UrlList;
    public List<String> XpathList;
    public List<String> TriggerWordList;
}
