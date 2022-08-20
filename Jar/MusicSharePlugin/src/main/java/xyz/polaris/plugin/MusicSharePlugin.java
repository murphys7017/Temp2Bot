package xyz.polaris.plugin;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import org.apache.commons.io.FileUtils;
import org.pf4j.Extension;
import org.yaml.snakeyaml.Yaml;
import xyz.polaris.function_interface.MessageEventHandlerInterface;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author polaris
 * @version 0.0.1
 */
@Extension
public class MusicSharePlugin implements MessageEventHandlerInterface {
    private Bot bot;
    Map<String ,Object> settings = null;


    @Override
    public List<String> getFunctionKeys() {
        loadSettings();

        return (List<String>) settings.get("TriggerWord");
    }

    @Override
    public String allowPower() {
        return null;
    }

    @Override
    public Boolean getResponse(MessageEvent messageEvent) {
        bot = messageEvent.getBot();
        return false;
    }

    public void loadSettings() {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        Yaml yaml = new Yaml();
        try {
            settings = yaml.load(FileUtils.openInputStream(new File(path.replace(".jar",".yml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
