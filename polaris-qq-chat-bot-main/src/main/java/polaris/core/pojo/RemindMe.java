package polaris.core.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("remind_me")
public class RemindMe {
    @TableId("remind_time")
    private Long remindTime;
    @TableField("group_id")
    private Long groupID;
    @TableField("subject_id")
    private Long subjectID;
    @TableField("content")
    private String content;
}
