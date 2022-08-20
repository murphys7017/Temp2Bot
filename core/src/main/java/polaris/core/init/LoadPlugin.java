package polaris.core.init;

import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import xyz.polaris.function_interface.MessageEventHandlerInterface;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author polaris
 */
public class LoadPlugin {
    public static void loadJar() {
        PluginManager pluginManager = new JarPluginManager();
        for (Map<String, String> pluginInfo : BOT_SET.JarPlugin) {
            pluginManager.loadPlugin(Paths.get(pluginInfo.get("path")));
        }
        pluginManager.startPlugins();
        List<MessageEventHandlerInterface> plugins = pluginManager.getExtensions(MessageEventHandlerInterface.class);
        for (MessageEventHandlerInterface plugin : plugins) {
            for (String functionKey : plugin.getFunctionKeys()) {
                RUN_VARIABLE.PLUGINS_MAP.put(functionKey,plugin);
            }
        }
        RUN_VARIABLE.PLUGINS_MANAGER = pluginManager;
    }
}
