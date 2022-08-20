package xyz.polaris.plugin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicInfoPojo {
    private String musicLink;
    private String title1;
    private String title2;
    private String singer;
    private String singetLink;
    // 时长
    private String duration;
    private String album;
    private String albumLink;
    private String imageLink;

}
