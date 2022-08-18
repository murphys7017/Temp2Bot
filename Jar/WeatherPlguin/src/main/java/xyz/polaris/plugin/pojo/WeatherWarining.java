package xyz.polaris.plugin.pojo;

/**
 * @author polaris
 */
public class WeatherWarining {
    // 当前数据的响应式页面，便于嵌入网站或应用
    private String fxLink;

    // 本条预警的唯一标识，可判断本条预警是否已经存在
    private String id;

    // 预警发布单位，可能为空
    private String sender;

    // 预警发布时间
    private String pubTime;

    // 预警信息标题
    private String title;
    
    // 预警开始时间，可能为空
    private String startTime;

    // 预警结束时间，可能为空
    private String endTime;

    // 预警信息的发布状态
    private String status;

    // 预警严重等级
    private String severity;

    //预警严重等级颜色，可能为空
    private String severityColor;

    // 预警类型ID
    private String type;

    // 预警类型名称
    private String typeName;

    // 预警信息的紧迫程度，可能为空
    private String urgency;

    // 预警信息的确定性，可能为空
    private String certainty;

    // 预警详细文字描述
    private String text;

    // 与本条预警相关联的预警ID，当预警状态为cancel或update时返回。可能为空
    private String related;

}
