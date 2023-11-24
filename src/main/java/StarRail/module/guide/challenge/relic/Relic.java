package StarRail.module.guide.challenge.relic;

import StarRail.general.Coordinates;
import StarRail.general.StarRailProperties;
import StarRail.module.guide.challenge.Challenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static StarRail.util.MouseUtil.mouseOperation;

/**
 * 侵蚀隧洞【遗器】
 *
 * @author : 真寻
 * 2023/8/7
 */
@Slf4j
@Component
public class Relic extends Challenge {
    private final String[] name = {"霜风", "讯拳", "漂泊", "睿治", "圣颂", "野焰", "药使"};
    @Autowired
    private StarRailProperties properties;
    @Autowired
    private Coordinates.GuideCoordinates coordinates;

    /**
     * 遗器
     */
    public void tunnelOfErosion() {
        // 遗器选择
        int legacySelection = properties.getLegacySelection();

        log.info("开启 {}之径·侵蚀隧洞 挑战", name[legacySelection - 1]);

        // 打开生存索引
        publicMethods.openSurvivalUi();

        // 选择侵蚀隧洞
        mouseOperation(coordinates.getTunnel());

        // 挑战
        challenge(legacySelection, "relic_");

        // 结束挑战
        endChallenge("遗器" + name[legacySelection - 1] + "之径·侵蚀隧洞");
    }
}
