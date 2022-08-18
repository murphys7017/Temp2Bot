package polaris.core.pojo.set.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.contact.Contact;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiduImageSearch {
    public Boolean IsUsing;
    public Boolean IfNeedAt;
    public List<String> CommandSet;
    public Long sponsor = null;
    public Contact sponsorContact = null;
}
