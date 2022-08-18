package polaris.core.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import polaris.core.pojo.event.NumberEventPojo;

import java.util.List;

/**
 * @author polaris
 * @version 1.0
 * QQ中的好友触发的事件
 * type
 *      0 触发违禁词
 */
@Mapper
public interface NumberEventMapper {

    @Insert("INSERT INTO NumberEventTable (qq, eventType, \"group\", occurrenceTime, occurrenceDate, remark) VALUES(#{qq}, #{eventType}, #{group}, #{occurrenceTime}, #{occurrenceDate}, #{remark});")
    int addNumberEvent(NumberEventPojo numberEventPojo);

    @Select("SELECT * FROM NumberEventTable WHERE eventType = 'SIGNIN' AND occurrenceDate = #{date} AND \"group\" = #{group}")
    List<NumberEventPojo> selectTodaySignInEvent(String date,Long group);

    @Select("SELECT * FROM NumberEventTable WHERE eventType = 'SIGNIN' AND occurrenceDate = #{date} AND qq = #{qq} AND \"group\" = #{group}")
    NumberEventPojo selectSignInEvent(@Param("date") String date, @Param("qq") Long qq,Long group);

    @Select("SELECT count(*) FROM NumberEventTable WHERE eventType = 'SIGNIN' AND qq = #{qq} AND \"group\" = #{group}")
    int countSignInEvent(@Param("qq") Long qq,Long group);

    @Select("SELECT * FROM NumberEventTable WHERE eventType = 'REMIND' AND occurrenceDate = #{occurrenceDate} AND occurrenceTime > #{occurrenceTime}")
    List<NumberEventPojo> selectReaminEvent(String occurrenceDate,String occurrenceTime);
}
