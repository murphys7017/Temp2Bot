package polaris.core.function.impl;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import polaris.core.function.Function;
import polaris.core.init.LoadConfig;
import polaris.core.service.impl.BaseFunction;
import polaris.core.utils.MessageSender;

import java.util.Arrays;
import java.util.List;

@Service("DebugTools")
public class DebugTools implements Function {
    @Autowired
    BaseFunction baseFunction;
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    @Override
    public List<String> getFunctionKeys() {
        return Arrays.asList("show function command set","show ban set","老婆在吗","reload bot set","restart bot");
    }

    @Override
    public Boolean getResponse(MessageEvent event) {
        if (event.getSender().getId() == BOT_SET.Admin){
            String msg = event.getMessage().get(PlainText.Key).toString();

            if (msg.startsWith(" ")){
                msg = msg.substring(1);
            }
            if ("show function command set".equals(msg)){
                event.getSender().sendMessage(RUN_VARIABLE.FUNCTIONS_SET.toString());
            }else if ("show ban set".equals(msg)){
                event.getSender().sendMessage(RUN_VARIABLE.BAN_WORD.toString());
            } else if ("老婆在吗".equals(msg)){
                MessageSender.toSend(event.getSubject(),"在的呢,怎么了吖&&[work/usingFile/][1.gif]");
            }else if ("reload bot set".equals(msg)){
                LoadConfig.loadAllConfig();
            }else if ("restart bot".equals(msg)){
                LoadConfig.loadAndLogin();
            }

        }
        return null;
    }
}
