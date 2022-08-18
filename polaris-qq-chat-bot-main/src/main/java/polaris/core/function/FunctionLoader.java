package polaris.core.function;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import polaris.core.service.impl.BaseFunction;
import polaris.core.utils.FileLoader;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * explain：获取应用上下文并获取相应的接口实现类
 *
 * @author polaris
 * @version 1.0
 * @deprecated
 * 获取function接口的实现
 * 获取jar包插件
 */
@Component
public class FunctionLoader implements ApplicationContextAware {
    @Autowired
    BaseFunction baseFunction;
    static Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    /**
     * 用于保存接口实现类名及对应的类
     */
    private static Map<String, Function> internalFunctionsMap = new HashMap<>();

    /**
     * 获取应用上下文并获取相应的接口实现类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //根据接口类型返回相应的所有bean
        Map<String, Function> tempInternalFunctionsMap = applicationContext.getBeansOfType(Function.class);
        Set<String> internalFunctionKeys = tempInternalFunctionsMap.keySet();
        for (String internalFunctionKey : internalFunctionKeys) {
            tempInternalFunctionsMap.get(internalFunctionKey).getFunctionKeys().forEach(functionKey -> {
                internalFunctionsMap.put(functionKey,tempInternalFunctionsMap.get(internalFunctionKey));
            });
        }
    }

    /**
     * 获取所有实现集合
     * @return
     */
    public static Map<String, Function> getInternalFunctionsMap() {
        return internalFunctionsMap;
    }


    /**
     * 获取对应服务
     * @param key
     * @return
     */
    public static Function getInternalService(String key) {
        return internalFunctionsMap.get(key);
    }

}
