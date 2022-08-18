package polaris.core.pojo.set.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemindMeSet {
    public Boolean IsUsing;
    public Boolean IfNeedAt;
    public String OkSay;
    public String ErrorSay;
    public List<String> CommandSet;
}
