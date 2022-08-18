# Polaris

---


![Polaris](http://r.photo.store.qq.com/psc?/V548iLWq2KMuag360X0I36WOzl0bgVdZ/bqQfVz5yrrGYSXMvKr.cqT9pKf2Rao24cqxtIZYYUPP4y8ePHS9aK7Tvnsh*iDan0gohaRuSbNV9z918qPUbJSrINgMH8CeyDfw3t1vU4Ew!/r)

---


一个基于miari的qq聊天机器人

Polaris是Alice的重构改进版,扩展了原有功能,预留了不少位置

项目的名字来源于我的游戏ID

同时打算以这个项目作为我的毕业设计,所以决定叫Polaris

项目会持续更新直到我毕业(还剩一年)



---

#### 声明

**一切开发旨在学习，请勿用于非法用途**

- Polaris项目只是单纯的想分享出来,所以完全免费,也不接受任何捐赠
- 不支持也不建议用Polaris项目来盈利
- Polaris基于mirai

#### 功能大概有

- 响应取消禁言
- 加入欢迎
- 群发言管理,违规时撤回禁言
- 戳一戳响应
- 触发回复
- 触发回复,可发送本地图片音频,api返回的图片和音频
- 音乐分享
- 百度图片搜索
- 百度翻译
- 天气查询
- 微博热榜
- 签到
- edge浏览器页面抓取为图片发送
- 自定义插件
- 还在持续更新中,计划
  - 添加数据库
  - 添加权限系统
  - 添加ui
  - 编译安卓版
  - 添加问答模型
  - 添加一个词袋模型

### 联系我

- 邮箱  Yuki7017@outlook.com
- B站 [Aki-Polaris的个人空间_哔哩哔哩_bilibili](https://space.bilibili.com/41761329)
- Gitee [October.Wr (october_Wr) - Gitee.com](https://gitee.com/october_Wr)
- 开黑啦 https://kaihei.co/OM6Efk

## 项目介绍

### 依赖

- windows7以上
- jdk14 (发行版内置)
- edge及对应SeleniumDriver

### 目录结构

```
├─.mvn
│  └─wrapper
│          maven-wrapper.jar
│          maven-wrapper.properties
│
├─Plugin
│  └─ChatModel
│      │  .keep
│      │  Chat.py
│      │  hook-ctypes.macholib.py
│      │  ModelSet.json
│      │  PluginSet.json
│      │  PythonExample.py
│      │  打包指令.txt
│      │
│      ├─data
│      │  ├─model
│      │  │  │  .keep
│      │  │  │
│      │  │  └─model_epoch40_50w
│      │  │          config.json
│      │  │          ReadMe.txt
│      │  │
│      │  └─voc
│      │          vocab.txt
│      │          vocab2.txt
│      │
│      └─__pycache__
│              Chat.cpython-37.pyc
│
├─PluginExample
│  └─python
│          PluginSet.json
│          PythonExample.py
│
├─README
│      image-20220529111717577.png
│      image-20220529111932636.png
│      image-20220529112053321.png
│      image-20220529112135525.png
│
├─src
│  ├─main
│  │  ├─java
│  │  │  ├─META-INF
│  │  │  │      MANIFEST.MF
│  │  │  │
│  │  │  └─yuki
│  │  │      └─mirai
│  │  │          └─demo
│  │  │              │  DemoApplication.java
│  │  │              │  HandlerSetter.java
│  │  │              │  RunVariable.java
│  │  │              │  Set.java
│  │  │              │  WebSocketConfig.java
│  │  │              │  WebSocketServer.java
│  │  │              │
│  │  │              ├─function
│  │  │              │  │  Function.java
│  │  │              │  │  FunctionLoader.java
│  │  │              │  │  FunctionManager.java
│  │  │              │  │  function包说明.md
│  │  │              │  │
│  │  │              │  └─impl
│  │  │              │          BaiduImageSearchImpl.java
│  │  │              │          BaiduTranslateImpl.java
│  │  │              │          BrowserCrawlingImpl.java
│  │  │              │          GetWeatherImpl.java
│  │  │              │          MusicShareImpl.java
│  │  │              │          SignInImpl.java
│  │  │              │          SpecificReplyImpl.java
│  │  │              │          WeiboTop10Impl.java
│  │  │              │
│  │  │              ├─handlers
│  │  │              │      FriendInputStatusChangedEventHandler.java
│  │  │              │      MemberJoinEventHandler.java
│  │  │              │      MemberLeaveEventHandler.java
│  │  │              │      MemberMuteEventHandler.java
│  │  │              │      MemberPermissionChangeEventHandler.java
│  │  │              │      MemberSpecialTitleChangeEventHandler.java
│  │  │              │      MemberUnmuteEventHandler.java
│  │  │              │      MessageEventHandler.java
│  │  │              │      NudgeEventHandler.java
│  │  │              │      WhenCalledEventHandler.java
│  │  │              │
│  │  │              ├─pojo
│  │  │              │  │  OneMessageEvent.java
│  │  │              │  │  OriginMessage.java
│  │  │              │  │
│  │  │              │  ├─plugin
│  │  │              │  │      PluginInfo.java
│  │  │              │  │      PluginMessage.java
│  │  │              │  │
│  │  │              │  └─set
│  │  │              │      │  Bot.java
│  │  │              │      │  Config.java
│  │  │              │      │  ConfigVariable.java
│  │  │              │      │  FunctionSet.java
│  │  │              │      │
│  │  │              │      └─function
│  │  │              │              BaiduImageSearch.java
│  │  │              │              BaiduTranslate.java
│  │  │              │              BrowserCrawling.java
│  │  │              │              MusicShare.java
│  │  │              │              SignIn.java
│  │  │              │              SpecificReply.java
│  │  │              │              WeatherInfo.java
│  │  │              │              WeiboTop10.java
│  │  │              │
│  │  │              └─utils
│  │  │                      CharacterUtil.java
│  │  │                      DownloadMusicList.java
│  │  │                      FileLoader.java
│  │  │                      Http2Api.java
│  │  │                      HttpGet.java
│  │  │                      MD5.java
│  │  │                      MessageBuilder.java
│  │  │                      MessageSender.java
│  │  │                      TransApi.java
│  │  │
│  │  └─resources
│  │      │  application.yml
│  │      │
│  │      ├─static
│  │      └─templates
│  └─test
│      └─java
│          └─yuki
│              └─mirai
│                  └─demo
│                          SeleniumTest.java
│
├─tools
│  └─data
│          cityData.txt
│          MusicData.txt
│
└─work
    │  DeviceInfo.json
    │
    ├─TalkData
    │      BanWords.txt
    │      SpecificReply.txt
    │      SpecificReplyLocal.txt
    │      SpecificReplyNetwork.txt
    │
    ├─tempDownload
    └─usingFile
        ├─file
        ├─ghs
        │      1579876690539.jpg
        │      1579876699468.jpg
        │      1579876701174.jpg
        │      1579876703731.jpg
        │      1579876707581.jpg
        │      1579876713633.jpg
        │
        ├─video
        │
        └─voice
                asd.mp3
                baba.mp3
                dsa.mp3
                sad.mp3
                zxc.mp3
```

### 目录及文件说明

#### work bot的工作目录

##### TalkData 对话用到的文件,可自己编辑

##### tempDownload 临时下载存放位置

##### DeviceInfo.json 登录设备信息



#### src 项目源代码



#### PluginExample 插件示例



#### Plugin 插件



#### tools 工具







## 使用说明

### 使用发行版

#### 从群文件或者GitHub,Gitee下载发行版,解压



#### 修改Config.yml

##### 必须修改项

- QQ
- Password
- Admin
- ApplyGroup

##### 注意

- bot的qq号最好有在bot部署的系统中用qq或者tim登录一段时间
- 最好不要开设备保护,安全保护之类的
- 滑块验证码参考[[Polaris\][mirai]如果你倒霉遇到了滑块验证_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1Ka411J7wr/)

- yml格式问题

- 各设置项解释配置文件里有

- 最好不要用文本文档打开,容易看不清

- 所有的路径要么绝对路径要么相对路径

  - 相对路径不要 \ 开头
  - 路径参考配置文件

  

### 安装Selenium

打开edge浏览器

在地址栏输入,查看浏览器版本

```
edge://settings/help
```

![image-20220529111717577](https://github.com/lzt815049548/polaris-qq-chat-bot/blob/main/README/image-20220529111717577.png)



然后打开这个网址 [Microsoft Edge 驱动程序](https://developer.microsoft.com/zh-cn/microsoft-edge/tools/webdriver/)

**下载自己对应的版本**

![image-20220529111932636](https://github.com/lzt815049548/polaris-qq-chat-bot/blob/main/README/image-20220529111932636.png)



下载完为一个压缩包

![image-20220529112053321](https://github.com/lzt815049548/polaris-qq-chat-bot/blob/main/README/image-20220529112053321.png)

**将压缩包内的msedgedriver.exe解压出来**

**编辑config.yml配置,指定驱动位置**

建议放在C:\Program Files (x86)\Microsoft\Edge\Application\内

![image-20220529112135525](https://github.com/lzt815049548/polaris-qq-chat-bot/blob/main/README/image-20220529112135525.png)



**完成**



## 功能介绍



### 使用文本,本地资源,网络资源进行回复 SpecificReply

理论上来说

凡是发一个关键词触发bot发送图片文字语音

都可以用固定回复实现,不管是网上的还是本地的

前提api没有什么奇奇怪怪的验证之类的

三个固定回复没有不同,只是为的清楚分了三种

可以只编写一个

**格式**

```
[触发词1][触发词2][触发词3]...->[][][]或者文本

->为分隔符
```



### 百度翻译 BaiduTranslate

消息以t&或者T&开头回自动翻译为中文



### 音乐分享 MusicShare

发送CommandSet中的关键词会分享歌单中的音乐

发送ChangePlaylist关键词会更新



### 百度搜图 BaiduImageSearch

发送CommandSet中的关键词会启动浏览器

自动搜索指定的图片返回百科 可能来源 相似图



### 天气查询 WeatherInfo

发送CommandSet中的关键词会启动浏览器

自动查询天气返回结果



### 微博热榜 WeiboTop10

发送CommandSet中的关键词会启动浏览器

自动查询微博热榜前15



### 签到 SignIn

SignIn 签到词

ToSignInSay 对签到成功的人说

DidISignIn 查询自己是否签到

DidHeSignIn 名字+[DidHeSignIn]查询别人是否签到

CommandSet 重置,清空



### 自定义页面抓取

根据指定的地址抓取页面元素

UrlList 页面的链接

XpathList 元素的xpath地址

TriggerWordList 触发词

**UrlList  XpathList  TriggerWordList  内容顺序必须一一对应**



### [][][]指令

这种指令是我定义的一种在bot内通用的发送消息的格式

是为了简化操作和扩展可自定义的范围

一条指令只能发送一条消息,但是可以通过&&或者||拼接起来

来达成发送复合消息的效果

&& 表示且 第一优先级

|| 表示或 第二优先级

当没有文本不是指令时会发送文本

#### 本地

```
[文件所在文件夹][文件名|随机][如果是图片可选是否为闪照][可选是否自动撤回]
[path][fileName|random]
[path][fileName|random][IsFlashImage][recall]

[path][fileName|random]
[path][fileName|random][recall]
```

**例子**

```
随机发送:[F:\IDEA\QQBot-Mirai\work\usingFile\ghs][random]
带自动撤回的:[F:\IDEA\QQBot-Mirai\work\usingFile\ghs][random][recall]
发送闪照:[F:\IDEA\QQBot-Mirai\work\usingFile\ghs][random][IsFlashImage]
发送指定:[F:\IDEA\QQBot-Mirai\work\usingFile\ghs][weigong-001.jpg]
```

#### api

```
因为涉及到返回数据的解析,所以复杂一点
[请求方法][类型][url][额外参数]

[GET|POST][txt][url][可选参数正则表达式用于提取]

[GET|POST][image][url]

如下json
提取其中的文本和图片
      # {
      #   'code': 0,
      #   'msg': '',
      #   'info':
      #         {
      #             'pid': '82584014',
      #             'uid': '1670478',
      #             'title': '銀髪悪魔っ娘②',
      #         }
      #   'author': 'ろむむ/8月末納期お仕事募集中',
      #   'r18': 'false',
      #   'width': '799',
      #   'height': '765',
      #   'tags': '['オリジナル','原创','悪魔っ娘','恶魔娘','ASMR','悪魔','恶魔','銀髪','银发','仰臥','仰卧','サキュバスproject','ルナ','Luna','はいてない','真空' ]',
      #   'url': 'https://pximg.lolicon.run/img-original/img/2020/06/27/01/21/50/82584014_p0.jpg'
      # }
[GET|POST][json][url][pid*txt&uid*txt&title*txt&url*image]

                      key-key-key...*type[txt|voice|image]
```

**例子**

```
文本:[GET][txt][https://api.ixiaowai.cn/ylapi/index.php]

#带正则提取的
文本-正则:[GET][txt][https://api.ixiaowai.cn/ylapi/index.php][\b[a-z]{3}\b]


图片:[GET][image][https://api.ixiaowai.cn/api/api.php]


json:pid&&uid&&title&&[GET][json][https://gurepu.lolicon.run/api/pixiv/setu.php][pid*txt&uid*txt&title*txt&url*image]
```





## 插件开发

插件是websocket客户端

连接的url为ws_url+插件名称+插件id

### 收到的消息格式

```
{
    'time':long,
    'messageType':'msg',
    'message':[
        {
            'type': 'MessageSource', 
            'kind': 'GROUP', 
            'botId': 2762018040, 
            'ids': [39954], 
            'internalIds': [377228493], 
            'time': 1653757967, 
            'fromId': 815049548, 
            'targetId': 817581299, 
            'originalMessage': [
                {
                    'type': 'PlainText', 
                    'content': '1'
                }
            ]
        }
    ]
}
```

### 发送的消息格式

```
{
    'time':long, # 必须为收到消息的time 不然发不出去
    'messageType':'command|code', # command指令消息  code mirai code 二选一必填
    'message': str
}
```



## 插件

### ChatModel

基于GPT2中文闲聊模型实现的一个闲聊ai

因为训练语料来源微博贴吧所以会有一点点暴躁
