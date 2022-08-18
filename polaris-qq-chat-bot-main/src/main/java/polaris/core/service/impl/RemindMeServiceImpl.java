package polaris.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import polaris.core.mapper.RemindMeMapper;
import polaris.core.pojo.RemindMe;
import polaris.core.service.RemindMeService;

@Service
public class RemindMeServiceImpl extends ServiceImpl<RemindMeMapper, RemindMe> implements RemindMeService {
}
