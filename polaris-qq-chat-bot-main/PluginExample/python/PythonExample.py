import websocket
import json

try:
    configuration = json.load(open("PluginSet.json", encoding='utf-8'))
except:
    configuration = json.load(open("./PluginSet.json", encoding='utf-8'))

url = configuration["WS_url"]
if url[-1] == '/':
    url = url + configuration["PluginName"] + '/' + configuration["PluginID"]
else:
    url = '/' + url + configuration["PluginName"] + '/' + configuration["PluginID"]

'''
messages:
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
'''

activate = []


# 只需要实现do_function
# 返回是是否发送消息,要发送的消息
def do_function(messages):
    # 设置返回的消息类型
    plugin_message_type = 'command'
    res_message = ''
    if messages['kind'] == 'GROUP':
        if messages['originalMessage'][0]['type'] == 'PlainText':
            content = messages['originalMessage'][0]['content']
            for key in configuration['PythonExample']:
                if key == content:
                    activate.append(messages['targetId'])
                    return True, plugin_message_type, 'start!'

    else:
        if messages['originalMessage'][0]['type'] == 'PlainText':
            content = messages['originalMessage'][0]['content']
            for key in configuration['PythonExample']:
                if key == content:
                    activate.append(messages['fromId'])
                    return True, plugin_message_type, 'start!'

    # return 是否发送,消息类型[command|code],message
    return False, plugin_message_type, res_message


# 尽量勿动
# websocket
def on_message(ws, plugin_message):  # 服务器有数据更新时，主动推送过来的数据
    plugin_message = json.loads(plugin_message)
    messages = json.loads(plugin_message['message'])[0]
    plugin_message['message'] = ''
    if_res = False
    # 执行
    if_res, plugin_message['messageType'], plugin_message['message'] = do_function(messages)
    if if_res:
        ws.send(json.dumps(plugin_message))


def on_error(ws, error):  # 程序报错时，就会触发on_error事件
    print(error)


def on_close(ws):
    print("Connection closed ……")


def on_open(ws):  # 连接到服务器之后就会触发on_open事件，这里用于send数据
    print("成功建立连接", ws)


if __name__ == "__main__":
    # websocket.enableTrace(True)
    ws = websocket.WebSocketApp(url=url,
                                on_message=on_message,
                                on_error=on_error,
                                on_close=on_close)
    ws.on_open = on_open
    ws.run_forever(ping_timeout=30)
