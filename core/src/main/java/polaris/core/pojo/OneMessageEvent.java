package polaris.core.pojo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author polaris
 * @deprecated
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneMessageEvent {
    private String type;
    private String kind;
    private long botId;
    private long time;
    private long fromId;
    private long targetId;
    private List<OriginMessage> originMessageList = new ArrayList<>();
    private MessageEvent messageEvent;
    public OneMessageEvent(MessageEvent event) {
        this.messageEvent = event;
        JSONArray messageArray = JSONArray.parseArray(MessageChain.serializeToJsonString(event.getMessage()));
        JSONObject messageObj = messageArray.getJSONObject(0);
        this.type = messageObj.getString("type");
        this.kind = messageObj.getString("kind");
        this.botId = messageObj.getLong("botId");
        this.time = messageObj.getLong("time");
        this.fromId = messageObj.getLong("fromId");
        this.targetId = messageObj.getLong("targetId");
        for (int i = 1; i <= messageArray.size()-1;i++ ) {
            JSONObject jsonObject = messageArray.getJSONObject(i);
            if (jsonObject.getString("type").equals("Image")) {
                Image image = Image.fromId(jsonObject.getString("imageId"));
                jsonObject.put("imageId",Image.queryUrl(image));
            }
            this.originMessageList.add(jsonObject.toJavaObject(OriginMessage.class));
        }
    }
}
