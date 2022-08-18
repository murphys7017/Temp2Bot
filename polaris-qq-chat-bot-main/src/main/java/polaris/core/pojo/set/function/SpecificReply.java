package polaris.core.pojo.set.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificReply {
    public Boolean IsUsing;
    public Boolean IfNeedAt;
    public String SpecificReplyFilePath;
    public String SpecificReplyLocalFilePath;
    public String SpecificReplyNetworkFilePath;
    public List<String> CommandSet;
}
