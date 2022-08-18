package polaris.core;

import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.utils.BotConfiguration;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.yaml.snakeyaml.Yaml;
import polaris.core.init.LoadConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;


@EnableScheduling
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        LoadConfig.loadAll();
        SpringApplication.run(DemoApplication.class, args);

    }
}
