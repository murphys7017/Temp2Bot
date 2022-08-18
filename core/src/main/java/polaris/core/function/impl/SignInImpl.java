package polaris.core.function.impl;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polaris.core.BOT_SET;
import polaris.core.RUN_VARIABLE;
import polaris.core.function.Function;
import polaris.core.mapper.NumberEventMapper;
import polaris.core.pojo.event.NumberEventEnum;
import polaris.core.pojo.event.NumberEventPojo;
import polaris.core.utils.MessageSender;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("SignIn")
public class SignInImpl implements Function {
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    @Autowired
    NumberEventMapper numberEventMapper;
    boolean isUsing = true;
    boolean ifNeedAt = false;
    String  SignIn = "";
    String  ToSignInSay = "";
    String   SignInInfo = "";




    @Override
    public List<String> getFunctionKeys() {
        Map<String, Object> config = (Map<String, Object>) RUN_VARIABLE.FUNCTIONS_SET.get("SignIn");
        this.isUsing = (boolean) config.get("IsUsing");
        this.ifNeedAt = (boolean) config.get("IfNeedAt");
        this.SignIn = config.get("SignIn").toString();

        this.ToSignInSay = config.get("ToSignInSay").toString();

        this.SignInInfo = config.get("SignInInfo").toString();
        List<String> res = new ArrayList<>();
        res.add(SignIn);
        res.add(SignInInfo);
        return res;
    }

    @Override
    public Boolean getResponse(MessageEvent event) {
        if (!isUsing){
            return false;
        }
        if (ifNeedAt){
            if (!event.getMessage().contains(At.Key)){
                return false;
            }
        }
        Bot bot = event.getBot();
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date date = new Date();
        sdf.applyPattern("yyyy-MM-dd");
        String msg = event.getMessage().get(PlainText.Key).contentToString();
        if (msg.startsWith(" ")){
            msg = msg.substring(1);
        }
        // 今天的签到列表
        List<NumberEventPojo> signEventList = new ArrayList<>();
        signEventList.addAll(numberEventMapper.selectTodaySignInEvent(sdf.format(date).substring(0,10), event.getSubject().getId()));
        if (msg.equals(SignIn)){
            if (numberEventMapper.selectSignInEvent(sdf.format(date),event.getSender().getId(),event.getSubject().getId()) == null){
                numberEventMapper.addNumberEvent(new NumberEventPojo(event, NumberEventEnum.SIGNIN));
                MessageSender.toSend(event.getSubject(),ToSignInSay);
                MessageSender.toSend(event.getSubject(),"顺便说一句,你是第"+signEventList.size()+1 + "个签到的");

            }else {
                MessageSender.toSend(event.getSubject(),"今天你已经签到了(☆▽☆)");
            }
            return true;
        }else if (msg.equals(SignInInfo)) {
            if (event.getSender().getId() == BOT_SET.Admin) {
                MessageSender.toSend(event.getSubject(),"今日签到信息:");
                MessageChainBuilder messageChainBuilder = new MessageChainBuilder();

                signEventList.forEach(numberEventPojo -> {
                    PlainText plainTextName = new PlainText(bot.getGroup(numberEventPojo.getGroup()).getMembers().get(numberEventPojo.getQq()).getNameCard()+"\n  今日签到时间:"+numberEventPojo.getOccurrenceTime()+"\n    签到次数:"+numberEventMapper.countSignInEvent(numberEventPojo.getQq(), event.getSubject().getId())+"\n");
                    messageChainBuilder.add(plainTextName);
                });

                event.getSender().sendMessage(messageChainBuilder.build());
            }
            return true;
        }else {
            return false;
        }
    }
}
