package polaris.core.utils;

import polaris.core.BOT_SET;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Http2Api {
        /**
         * 向指定 URL 发送POST方法的请求
         *
         * @param httpUrl
         *            发送请求的 URL
         * @param param
         *            请求参数是json
         * @return 所代表远程资源的响应结果
         */
        public static String doPost(String httpUrl, String param) {

            HttpURLConnection connection = null;
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            String result = null;
            try {
                URL url = new URL(httpUrl);
                // 通过远程url连接对象打开连接
                connection = (HttpURLConnection) url.openConnection();
                // 设置连接请求方式
                connection.setRequestMethod("POST");
                // 设置连接主机服务器超时时间：15000毫秒
                connection.setConnectTimeout(15000);
                // 设置读取主机服务器返回数据超时时间：60000毫秒
                connection.setReadTimeout(60000);

                // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
                connection.setDoOutput(true);
                // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
                connection.setDoInput(true);
                // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
                connection.setRequestProperty("Content-Type", "application/json");
                // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
                // connection.setRequestProperty("Authorization", "Bearer
                // da3efcbf-0845-4fe3-8aba-ee040be542c0");
                // 通过连接对象获取一个输出流
                os = connection.getOutputStream();
                // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
                if (param != null) {
                    os.write(param.getBytes());
                }
                // 通过连接对象获取一个输入流，向远程读取
                if (connection.getResponseCode() == 200) {

                    is = connection.getInputStream();
                    // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                    br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                    StringBuffer sbf = new StringBuffer();
                    String temp = null;
                    // 循环遍历一行一行读取数据
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append("\r\n");
                    }
                    result = sbf.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭资源
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != os) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 断开与远程地址url的连接
                connection.disconnect();
            }
            return result;
        }




    /**
     * 向指定 URL 发送Get方法的请求
     *
     * @param httpUrl
     *            发送请求的 URL
     *
     * @return 所代表远程资源的响应结果
     */
    public static String doGetText(String httpUrl) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("GET");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.54 Safari/537.36 Edg/101.0.1210.39");

            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            // connection.setRequestProperty("Authorization", "Bearer
            // da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
            if (connection.getResponseCode() == 300){
                System.out.println("300");
                result = connection.getURL().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }
    public static String getDownloadFile(String url) {
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            URL sendUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("contentType", "utf-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
//            httpURLConnection.setRequestProperty(
//                    "User-agent", InetAddress.getLocalHost().getHostAddress() + ":"
//                            + System.getProperty("catalina.home"));
            httpURLConnection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.54 Safari/537.36 Edg/101.0.1210.39");
            out = httpURLConnection.getOutputStream();
            // 清空缓冲区数据
            out.flush();
            // 获取HTTP状态码
            int httpStatusCode = httpURLConnection.getResponseCode();
            System.out.println(httpStatusCode);
            if(httpStatusCode!=200){
                throw new RuntimeException("异常");
            }
            in = httpURLConnection.getInputStream();
            // 获取文件长度
            int len = httpURLConnection.getContentLength();
            // 路径+文件名
            String pathAndName = BOT_SET.Workspace+ File.separator +"tempDownload"+ File.separator + new Random().nextInt(1000);

            // 保存文件
            saveFileByByte(in, pathAndName, len);
            return pathAndName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                httpURLConnection = null;
            }

        }
    }
    //链接url下载图片
    /**
     * 功能描述:
     * @param:  urlList  图片url地址
     * @param:  path     下载到本地的路径
     * @return: void
     * @author: lemon
     * @since: 2019/9/27 0027
     */
    public static String downloadPicture(String urlList) {
        // 路径+文件名
        String pathAndName = BOT_SET.Workspace+ File.separator +"tempDownload"+ File.separator + new Random().nextInt(100000);
        try {
            URL url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(pathAndName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            return pathAndName;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postDownloadFile(String url) {
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            URL sendUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("contentType", "utf-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
//            httpURLConnection.setRequestProperty(
//                    "User-agent", InetAddress.getLocalHost().getHostAddress() + ":"
//                            + System.getProperty("catalina.home"));
            httpURLConnection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.54 Safari/537.36 Edg/101.0.1210.39");

            out = httpURLConnection.getOutputStream();
            // 清空缓冲区数据
            out.flush();
            // 获取HTTP状态码
            int httpStatusCode = httpURLConnection.getResponseCode();
            if(httpStatusCode!=200){
                throw new RuntimeException("异常");
            }
            in = httpURLConnection.getInputStream();
            // 获取文件长度
            int len = httpURLConnection.getContentLength();
            // 路径+文件名
            String pathAndName = BOT_SET.Workspace+ File.separator +"tempDownload"+ File.separator + new Random().nextInt(100000);

            // 保存文件
            saveFileByByte(in, pathAndName, len);
            return pathAndName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                httpURLConnection = null;
            }

        }
    }

    //写文件
    private static void saveFileByByte(InputStream in, String path, int len)
            throws Exception {
        byte[] byteDatas = new byte[len];
        BufferedOutputStream bw = null;
        try {
            // 创建文件对象
            File f = new File(path);
            // 创建文件路径
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            // 写入文件
            bw = new BufferedOutputStream(new FileOutputStream(path));
            int bytesRead = 0;
            while ((bytesRead = in.read(byteDatas, 0, byteDatas.length)) != -1) {
                bw.write(byteDatas, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
