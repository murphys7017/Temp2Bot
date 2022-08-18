package polaris.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import polaris.core.pojo.RemindMe;

import java.util.List;

@Mapper
public interface RemindMeMapper extends BaseMapper<RemindMe> {
}
