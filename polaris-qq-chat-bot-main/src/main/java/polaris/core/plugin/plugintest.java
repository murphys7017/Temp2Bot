package polaris.core.plugin;

import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import xyz.polaris.function_interface.MessageEventHandlerInterface;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class plugintest {
    public static void test(){
        PluginManager pluginManager = new JarPluginManager();
        pluginManager.loadPlugin(Paths.get("E:\\Code\\Polaris\\PlguinTest\\target\\WeatherPlguin-1.0.0.jar"));
        pluginManager.startPlugins();
        List<MessageEventHandlerInterface> plugins = pluginManager.getExtensions(MessageEventHandlerInterface.class);
    }
}
