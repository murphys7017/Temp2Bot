package polaris.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author polaris
 * @deprecated
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginMessage {
    private String type;
    private String content;
    private String imageId;
    private Long target;
    private String args;
}
