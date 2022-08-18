package polaris.core.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageBuilder {
    private static MessageChainBuilder messageChainBuilder = null;
    private static Contact contact = null;

    /**
     *
     * @param value 发送由&&或者||拼接的多条消息
     * @return MessageChain
     */
    public static MessageChain buildMessageChain(String value, Contact cont){
        messageChainBuilder = new MessageChainBuilder();
        contact = cont;
        if (value == null) {
            return null;
        }
        String[] values = value.split("&&");
        for (String s : values) {
            String[] ss = s.split("\\|\\|");
            if (ss.length > 1) {
                messageBuilder(ss[(int) (Math.random() * ss.length)]);
                return messageChainBuilder.build();
            }else {
                messageBuilder(s);
            }
        }
        return messageChainBuilder.build();
    }
    public static MessageChainBuilder buildMessageChainBuilder(String value, Contact cont){
        messageChainBuilder = new MessageChainBuilder();
        contact = cont;
        if (value == null) {
            return null;
        }
        String[] values = value.split("&&");
        for (String s : values) {
            String[] ss = s.split("\\|\\|");
            if (ss.length > 1) {
                messageBuilder(ss[(int) (Math.random() * ss.length)]);
                return messageChainBuilder;
            }else {
                messageBuilder(s);
            }
        }
        return messageChainBuilder;
    }



    /**
     * @param value 单条回复值
     */

    public static void messageBuilder(String value){
        List<String> keys = CharacterUtil.extractBracketMessage(value);
        if (keys.isEmpty()) {
            messageChainBuilder.append(new PlainText(value));
        }else {
            if (keys.get(0).equals("GET")){
                getRequestMessageBuilder(keys);
            }else if (keys.get(0).equals("POST")){
                postRequestMessageBuilder(keys);
            }else {
                localMessageBuilder(keys);
            }

        }

    }

    /**
     * 根据get数据构建消息链
     * @param keys [][][]指令
     */
    private static void getRequestMessageBuilder(List<String> keys){
        //类型 文本
        if (keys.get(1).equals("txt")) {
            String res = Http2Api.doGetText(keys.get(2));
            getApiTxt(keys, res);
        }else if (keys.get(1).equals("image")){
            String path = Http2Api.downloadPicture(keys.get(2));
            if (keys.contains("IsFlashImage")){
                imageMessageBuilder(path,true);
            }else {
                imageMessageBuilder(path,false);
            }
        }else if (keys.get(1).equals("voice")){
            String path = Http2Api.getDownloadFile(keys.get(2));
            voiceMessageBuilder(path);
        }else if (keys.get(1).equals("json")) {
            String res = Http2Api.doGetText(keys.get(2));
            getApiJson(keys, res);
        }
    }



    /**
     * 构建post请求的消息链
     * @param keys [][][]指令
     */
    private static void postRequestMessageBuilder(List<String> keys){
        //类型 文本
        if (keys.get(1).equals("txt")) {
            String res = Http2Api.doPost(keys.get(2),null);
            getApiTxt(keys, res);
        }else if (keys.get(1).equals("image")){
            String path = Http2Api.downloadPicture(keys.get(2));
            if (keys.contains("IsFlashImage")){
                imageMessageBuilder(path,true);
            }else {
                imageMessageBuilder(path,false);
            }
        }else if (keys.get(1).equals("voice")){
            String path = Http2Api.postDownloadFile(keys.get(2));
            voiceMessageBuilder(path);
        }else if (keys.get(2).equals("json")) {
            String res = Http2Api.doPost(keys.get(2),null);
            getApiJson(keys, res);
        }
    }

    /**
     * 获取api的json数据并根据命令构建消息链
     * @param keys [][][]指令
     * @param res json str
     */
    private static void getApiJson(List<String> keys, String res) {
        JSONObject jsonObject = null;
        try {
            jsonObject =JSONObject.parseObject(res);
        }catch (JSONException e) {
            res = res.replace("\"[","[").replace("]\"","]");
            jsonObject =JSONObject.parseObject(res);
        }

        if (jsonObject != null) {
            String obj = "";
            for (String s : keys.get(3).split("&")) {
                String type = s.split("\\*")[1];
                String path = s.split("\\*")[0];
                for (String key : path.split("-")) {
                    obj = jsonObject.getString(key);
                    try {
                        jsonObject =JSONObject.parseObject(obj);
                    }catch (JSONException e) {
                        if (type.equals("txt")){
                            messageChainBuilder.append(new PlainText(obj));
                        }else if (type.equals("image")) {
                            if (keys.contains("IsFlashImage")){
                                imageMessageBuilder(Http2Api.downloadPicture(obj),true);
                            }else {
                                imageMessageBuilder(Http2Api.downloadPicture(obj),false);
                            }
                        }else if (type.equals("voice")) {
                            voiceMessageBuilder(Http2Api.getDownloadFile(obj));
                        }

                    }
                }
            }

            for (String key : keys.get(3).split("-")) {

            }
        }
    }

    /**
     * 获取api的文本数据并根据命令构建消息链
     * @param keys 命令
     * @param res str
     */
    private static void getApiTxt(List<String> keys, String res) {
        if (res != null) {
            if (keys.size() == 4){
                //将规则封装成对象。
                Pattern p = Pattern.compile(keys.get(3));
                //让正则对象和要作用的字符串相关联。获取匹配器对象。
                Matcher m  = p.matcher(res);
                while(m.find())
                {
                    messageChainBuilder.append(new PlainText(m.group()));
                }
            }else {
                messageChainBuilder.append(new PlainText(res));
            }
        }
    }

    /**
     * 构建使用本地资源的消息链
     * @param keys [][][]指令
     */
    private static void localMessageBuilder(List<String> keys){
        String filePath = keys.get(0);
        String suffix = "";
        if (keys.get(1).equals("random")){
            filePath = filePath + File.separator+ FileLoader.getRandomFile(filePath);
        }else {
            filePath =filePath+File.separator+ keys.get(1);
        }
        suffix = filePath.split("\\.")[1];

        Set<String> imageSuffix = new HashSet<>(Arrays.asList("xbm", "tif", "pjp", "svgz", "jpg", "jpeg", "ico", "tiff", "gif", "svg", "jfif", "webp", "png", "bmp", "pjpeg", "avif"));

        if (imageSuffix.contains(suffix)){
            imageMessageBuilder(filePath, keys.contains("IsFlashImage"));
        }else if (suffix.equals("mp3") || suffix.equals("amr")){
            voiceMessageBuilder(filePath);
        }
    }

    /**
     * 构建图片消息链
     * @param path 图片路径,支持常见图片
     * @param isFlashImage 是否为闪照
     */
    private static void imageMessageBuilder(String path, Boolean isFlashImage){
        File file = new File(path);
        if (file.exists()) {
            Image image = ExternalResource.uploadAsImage(new File(path),contact);
            if (isFlashImage){
                messageChainBuilder.append( FlashImage.from(image));
            }else {
                messageChainBuilder.append(image);
            }
        }
    }

    /**
     * 构建语音消息链
     * @param path 音频文件路径 amr
     */
    private static void voiceMessageBuilder(String path){
        File file = new File(path);
        if (path.split("\\.")[1].equals("mp3")) {
            // file = FileLoader.Mp3ToAmr(file);
        }
        if (file.exists()) {
            Audio audio = null;
            ExternalResource resource = ExternalResource.create(file);
            try {

                audio = contact.getBot().getFriend(contact.getId()).uploadAudio(resource);
            }catch (Exception ignored) {

            }
            try {
                audio = contact.getBot().getGroup(contact.getId()).uploadAudio(resource);
            }catch (Exception ignored) {

            }
            messageChainBuilder.append(audio);
        }
    }
}
