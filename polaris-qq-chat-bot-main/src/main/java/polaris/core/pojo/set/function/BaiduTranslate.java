package polaris.core.pojo.set.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiduTranslate {
    public Boolean IsUsing;
    public String BaiduTranslateAPPID;
    public String BaiduTranslateKey;
    public List<String> CommandSet;
}