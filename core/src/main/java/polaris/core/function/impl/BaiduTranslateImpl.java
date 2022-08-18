package polaris.core.function.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import polaris.core.RUN_VARIABLE;
import polaris.core.function.Function;
import polaris.core.utils.CharacterUtil;
import polaris.core.utils.TransApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @deprecated
 */
@Service("BaiduTranslate")
public class BaiduTranslateImpl implements Function {
    Logger logger = LoggerFactory.getLogger("TEST USE LOG");
    List<String> commandSet = null;
    boolean isUsing = true;
    String  BaiduTranslateAPPID = "";
    String  BaiduTranslateKey = "";


    @Override
    public List<String> getFunctionKeys() {
        Map<String, Object> config = (Map<String, Object>) RUN_VARIABLE.FUNCTIONS_SET.get("BaiduTranslate");
        this.commandSet = (List<String>) config.get("CommandSet");
        this.isUsing = (boolean) config.get("IsUsing");
        this.BaiduTranslateAPPID = config.get("BaiduTranslateAPPID").toString();
        this.BaiduTranslateKey = config.get("BaiduTranslateKey").toString();
        List<String> res = new ArrayList<>();
        res.addAll(commandSet);
        return res;
    }

    @Override
    public Boolean getResponse(MessageEvent event) {
        if (!isUsing){
            return false;
        }
        Bot bot = event.getBot();
        String msg = event.getMessage().get(PlainText.Key).contentToString();
        if (msg.startsWith(" ")){
            msg = msg.substring(1);
        }

        for (String s : commandSet) {
            if (msg.startsWith(s)){
                msg = msg.split(s)[0];
                String res = TransApi.getTransResult(msg,"auto","zh",BaiduTranslateAPPID,BaiduTranslateKey);
                JSONObject jsonObject = JSON.parseObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
                jsonObject = jsonArray.getJSONObject(0);
                res = CharacterUtil.unicodeDecode(jsonObject.getString("dst"));
                event.getSubject().sendMessage(res);
                return true;
            }
        }

        return false;
    }
}
