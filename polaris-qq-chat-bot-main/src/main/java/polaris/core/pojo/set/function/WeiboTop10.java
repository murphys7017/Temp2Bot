package polaris.core.pojo.set.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeiboTop10 {
    public Boolean IsUsing;
    public Boolean IfNeedAt;
    public List<String> CommandSet;
}
