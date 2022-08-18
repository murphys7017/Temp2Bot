package polaris.core.utils;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一个字符工具类 包括提取
 *  中括号中的内容
 *  根据莱温斯坦距离判断字符相似度
 *  unicode编码解码
 *  根据分隔符和行分割文本为Map<String,String>
 * @author polaris
 * @version 1.0.0
 */
public class CharacterUtil {
    /**
     * 根据分隔符和行分割文本为Map<String,String>
     *     # 开头视为注释
     *     忽略空白行
     *
     * @param filePath 固定回复的文本路径
     * @return  返回 key:问 value:答 map
     */
    public static Map<String,String> loadSpecificFile(String filePath,String splitKey){
        try {
            List<String> list = FileUtils.readLines(new File(filePath),"UTF-8");
            Map<String,String> SpecificReply = new HashMap<>();
            for (String s : list) {
                if (!s.startsWith("#") && !s.equals("")) {
                    String keyValue[] = s.split(splitKey);
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
     * @Title: unicodeEncode
     * @Description: unicode编码
     * @param string
     * @return
     */
    public static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }
    /**
     * @Title: unicodeDecode
     * @Description: unicode解码
     * @param string
     * @return
     */
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }
    /**
     * 提取中括号中内容，忽略中括号中的中括号
     * @param msg xxx[A]xxx[B]
     * @return [A,B] list
     */
    public static List<String> extractBracketMessage(String msg) {

        List<String> list = new ArrayList<String>();
        int start = 0;
        int startFlag = 0;
        int endFlag = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == '[') {
                startFlag++;
                if (startFlag == endFlag + 1) {
                    start = i;
                }
            } else if (msg.charAt(i) == ']') {
                endFlag++;
                if (endFlag == startFlag) {
                    list.add(msg.substring(start + 1, i));
                }
            }
        }
        return list;
    }

    /**
     * 莱文斯坦距离，又称 Levenshtein 距离，是编辑距离的一种。指两个字串之间，由一个转成另一个所需的最少编辑操作次数。
     * @param a
     * @param b
     * @return
     */
    public static float Levenshtein(String a, String b) {
        if (a == null && b == null) {
            return 1f;
        }
        if (a == null || b == null) {
            return 0F;
        }
        int editDistance = editDis(a, b);
        return 1 - ((float) editDistance / Math.max(a.length(), b.length()));
    }

    private static int editDis(String a, String b) {

        int aLen = a.length();
        int bLen = b.length();

        if (aLen == 0) return aLen;
        if (bLen == 0) return bLen;

        int[][] v = new int[aLen + 1][bLen + 1];
        for (int i = 0; i <= aLen; ++i) {
            for (int j = 0; j <= bLen; ++j) {
                if (i == 0) {
                    v[i][j] = j;
                } else if (j == 0) {
                    v[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    v[i][j] = v[i - 1][j - 1];
                } else {
                    v[i][j] = 1 + Math.min(v[i - 1][j - 1], Math.min(v[i][j - 1], v[i - 1][j]));
                }
            }
        }
        return v[aLen][bLen];
    }

}
