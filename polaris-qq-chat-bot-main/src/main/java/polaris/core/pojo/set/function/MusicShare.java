package polaris.core.pojo.set.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicShare {
    public Boolean IsUsing;
    public Boolean IfNeedAt;
    public String Playlist;
    public String ChangePlaylist;
    public List<String> CommandSet;
}
