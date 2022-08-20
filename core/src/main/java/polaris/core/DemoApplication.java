package polaris.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import polaris.core.init.LoadConfig;


@EnableScheduling
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        LoadConfig.loadAndLogin();
        SpringApplication.run(DemoApplication.class, args);

    }
}
