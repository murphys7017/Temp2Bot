package polaris.core.pojo.plugin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PluginInfo {
    private String pluginName;
    private String pluginID;
    private Boolean isActivate;
    private String sessionID;

    public String getStringKey(){
        return pluginName+pluginID+sessionID;
    }
}
