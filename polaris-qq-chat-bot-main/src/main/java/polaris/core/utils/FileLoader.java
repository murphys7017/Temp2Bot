package polaris.core.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileLoader {
    /**
     * 加载固定回复的文本
     *
     * @param filePath 固定回复的文本路径
     * @return  返回 key:问 value:答 map
     */
    public static Map<String,String> loadSpecificReplyFile(String filePath){
        try {
            List<String> list = FileUtils.readLines(new File(filePath),"UTF-8");
            Map<String,String> SpecificReply = new HashMap<>();
            for (String s : list) {
                if (!s.startsWith("#") && !s.equals("")) {
                    String keyValue[] = s.split("->");
                    List<String> keys = CharacterUtil.extractBracketMessage(keyValue[0]);
                    for (String key : keys) {
                        SpecificReply.put(key,keyValue[1]);
                    }
                }
            }

            return SpecificReply;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }




    /**
     * 将MP3转为amr
     *
     * @param source MP3 file
     * @return file path string
     */
//    public static File Mp3ToAmr(File source){
//        File target = new File(SET.CONFIG.BotSet.Workspace + File.separator + "temp.amr");
//        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("libamr_nb");//编码器
//
//        audio.setBitRate(12200);//比特率
//        audio.setChannels(1);//声道；1单声道，2立体声
//        audio.setSamplingRate(8000);//采样率（重要！！！）
//
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("amr");//格式
//        attrs.setAudioAttributes(audio);//音频设置
//        Encoder encoder = new Encoder();
//        try {
//            encoder.encode(source, target, attrs);
//        } catch (EncoderException e) {
//            e.printStackTrace();
//        }
//        return target;
//    }

    /**
     * 获取文件夹内的所有文件名
     *
     * @param path 文件夹
     * @return file name list<string>
     */
    public static List<String> getFileNameList(String path){
        List<String> fileNames = new ArrayList<>();
        try {
            File file = new File(path);
            File[] files = file.listFiles();
            for (File file1 : files) {
                fileNames.add(file1.getName());
            }
            return fileNames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *在指定文件夹内随机选一个文件
     *
     * @return string
     */
    public static String getRandomFile(String path) {
        try {
            File file = new File(path);
            File[] files = file.listFiles();
            if (files!=null&&files.length > 0) {
                int i = new Random().nextInt(files.length - 1);
                return files[i].getName();
            } else {//
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
