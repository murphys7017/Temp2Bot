package polaris.core.pojo.set.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.contact.NormalMember;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignIn {
    public Boolean IsUsing;
    public Boolean IfNeedAt;
    public String SignIn;
    public String ToSignInSay;
    public String DidISignIn;
    public String DidHeSignIn;
    public Map<Long,Date> LastSignInTime = new HashMap<>();
    public Map<Long,List<NormalMember>> memberListMap = new HashMap<>();
    public List<String> CommandSet;
}
