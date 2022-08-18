from re import M, U
import random
import torch
import os
from datetime import datetime
import logging
from transformers import GPT2LMHeadModel
from transformers import BertTokenizerFast
import torch.nn.functional as F
import json

try:
    configuration = json.load(open("ModelSet.json", encoding='utf-8'))
except:
    configuration = json.load(open("./ModelSet.json", encoding='utf-8'))
    
model_path = configuration['model_path']                    # 对话模型路径
vocab_path = configuration['vocab_path']                    # 选择词库
repetition_penalty = configuration['repetition_penalty']    # 重复惩罚参数，若生成的对话重复性较高，可适当提高该参数
topk = configuration['topk']                                # 最高k选1
topp = configuration['topp']                                # 最高积累概率
max_len = configuration['max_len']                          # 每个utterance的最大长度,超过指定长度则进行截断
max_history_len = configuration['max_history_len']          # dialogue history的最大长度
temperature = configuration['temperature']                  # 生成的temperature
no_cuda = configuration['no_cuda']                          # 不使用GPU进行预测

CUDA_VISIBLE_DEVICES = configuration['CUDA_VISIBLE_DEVICES']# 指定使用的显卡

ReplyFrequency = configuration['ReplyFrequency']            # 回复频率[随机] 1 一句一回 2 两句随机回 3 三句随机回 ... 


# model ***********************************

PAD = '[PAD]'
pad_id = 0

SPECIAL_ANSWER = []
SPECIAL_QUESTION = []



USE_CUDA = torch.cuda.is_available()
if no_cuda:
    USE_CUDA = False
device = torch.device("cuda" if USE_CUDA else "cpu")



def create_logger():
    """
    将日志输出到日志文件和控制台
    """
    logger = logging.getLogger(__name__)
    logger.setLevel(logging.INFO)

    formatter = logging.Formatter(
        '%(asctime)s - %(levelname)s - %(message)s')


    # 创建一个handler，用于将日志输出到控制台
    console = logging.StreamHandler()
    console.setLevel(logging.DEBUG)
    console.setFormatter(formatter)
    logger.addHandler(console)

    return logger


def top_k_top_p_filtering(logits, top_k=0, top_p=0.0, filter_value=-float('Inf')):
    assert logits.dim() == 1  # batch size 1 for now - could be updated for more but the code would be less clear
    top_k = min(top_k, logits.size(-1))  # Safety check
    if top_k > 0:
        # Remove all tokens with a probability less than the last token of the top-k
        # torch.topk()返回最后一维最大的top_k个元素，返回值为二维(values,indices)
        # ...表示其他维度由计算机自行推断
        indices_to_remove = logits < torch.topk(logits, top_k)[0][..., -1, None]
        logits[indices_to_remove] = filter_value  # 对于topk之外的其他元素的logits值设为负无穷

    if top_p > 0.0:
        sorted_logits, sorted_indices = torch.sort(logits, descending=True)  # 对logits进行递减排序
        cumulative_probs = torch.cumsum(F.softmax(sorted_logits, dim=-1), dim=-1)

        # Remove tokens with cumulative probability above the threshold
        sorted_indices_to_remove = cumulative_probs > top_p
        # Shift the indices to the right to keep also the first token above the threshold
        sorted_indices_to_remove[..., 1:] = sorted_indices_to_remove[..., :-1].clone()
        sorted_indices_to_remove[..., 0] = 0

        indices_to_remove = sorted_indices[sorted_indices_to_remove]
        logits[indices_to_remove] = filter_value
    return logits


logger = create_logger()
logger.info('using device:{}'.format(device))
os.environ["CUDA_VISIBLE_DEVICES"] = CUDA_VISIBLE_DEVICES
tokenizer = BertTokenizerFast(vocab_file=vocab_path, sep_token="[SEP]", pad_token="[PAD]", cls_token="[CLS]")
# tokenizer = BertTokenizer(vocab_file=args.voca_path)
model = GPT2LMHeadModel.from_pretrained(model_path)
model = model.to(device)
model.eval()



# 存储聊天记录，每个utterance以token的id的形式进行存储
history = []

def chat(text):
    if random.randint(1,ReplyFrequency) == 1:
        return False
    try:
        text_ids = tokenizer.encode(text, add_special_tokens=False)
        history.append(text_ids)
        input_ids = [tokenizer.cls_token_id]  # 每个input以[CLS]为开头

        for history_id, history_utr in enumerate(history[-max_history_len:]):
            input_ids.extend(history_utr)
            input_ids.append(tokenizer.sep_token_id)

        input_ids = torch.tensor(input_ids).long().to(device)
        input_ids = input_ids.unsqueeze(0)
        response = []  # 根据context，生成的response
        # 最多生成max_len个token
        for _ in range(max_len):
            outputs = model(input_ids=input_ids)
            logits = outputs.logits
            next_token_logits = logits[0, -1, :]

            # 对于已生成的结果generated中的每个token添加一个重复惩罚项，降低其生成概率
            for id in set(response):
                next_token_logits[id] /= repetition_penalty

            next_token_logits = next_token_logits / temperature

            # 对于[UNK]的概率设为无穷小，也就是说模型的预测结果不可能是[UNK]这个token
            next_token_logits[tokenizer.convert_tokens_to_ids('[UNK]')] = -float('Inf')
            filtered_logits = top_k_top_p_filtering(next_token_logits, top_k=topk, top_p=topp)

            # torch.multinomial表示从候选集合中无放回地进行抽取num_samples个元素，权重越高，抽到的几率越高，返回元素的下标
            next_token = torch.multinomial(F.softmax(filtered_logits, dim=-1), num_samples=1)

            if next_token == tokenizer.sep_token_id:  # 遇到[SEP]则表明response生成结束
                break

            response.append(next_token.item())
            input_ids = torch.cat((input_ids, next_token.unsqueeze(0)), dim=1)
        history.append(response)
        text = tokenizer.convert_ids_to_tokens(response)
        text = "".join(text)
        logger.info(text)
        return text
    except KeyboardInterrupt:
        logger.info(False)
        return False

