package StarRail.module.guide.challenge.weekly;

import StarRail.general.Coordinates;
import StarRail.module.guide.challenge.Challenge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static StarRail.util.MouseUtil.detectAndClick;

/**
 * 历战余响 模块
 *
 * @author : 真寻
 * 2023/7/27
 */
@Slf4j
@Component
public class WeeklyChallenge extends Challenge {
    /**
     * 挑战名
     */
    private final String[] name = {"毁灭的开端", "寒潮的落幕", "不死的神实"};

    @Autowired
    private Coordinates.GuideCoordinates coordinates;

    /**
     * 历战余响挑战
     */
    public void weeklyChallenge() {
        // 挑战选择
        int weeklyChallengeSelection = properties.getWeeklyChallengeSelection();

        log.info("开启 {}·历战余响 挑战", name[weeklyChallengeSelection - 1]);

        // 打开生存索引
        publicMethods.openSurvivalUi();

        // 拖动
        publicMethods.draggingLeft();

        // 选择历战余响
        detectAndClick(coordinates.getWeekly(), "历战余响");

        // 拖动显示完整界面
        publicMethods.draggingRight();

        // 挑战
        challenge(weeklyChallengeSelection, "weeklyChallenge_");

        // 退出关卡, 结束挑战, 回复状态
        endChallenge("行迹高级材料/光锥 " + name[weeklyChallengeSelection - 1] + "·历战余响");
    }
}
