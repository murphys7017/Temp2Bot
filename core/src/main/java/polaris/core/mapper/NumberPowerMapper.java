package polaris.core.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author polaris
 * @version 1.0
 */
@Mapper
public interface NumberPowerMapper {
    String getNumberPower(Long qq);
}
