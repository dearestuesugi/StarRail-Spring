package StarRail.module.reward;//package StarRail.module.reward;

import StarRail.general.Coordinates;
import StarRail.module.guide.PublicMethods;
import StarRail.util.MatchingUi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static StarRail.util.MouseUtil.detectAndClick;
import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.basic.keyboard.Keyboard.simulatingKeyPress;
import static StarRail.util.basic.random.RandomGenerator.randomWait;

/**
 * 无名勋礼
 *
 * @author : 真寻
 * 2023/10/3
 */
@Slf4j
@Component
public class Reward {
    @Autowired
    private Coordinates.RewardCoordinates rewardCoordinates;
    @Autowired
    private MatchingUi matchingUi;
    @Autowired
    private PublicMethods publicMethods;

    /**
     * 无名勋礼
     */
    public void unknownReward() {
        log.info("领取无名勋礼奖励");
        simulatingKeyPress("f2");

        if (matchingUi.unknownRewardUi()) {
            // 切换任务界面
            mouseOperation(rewardCoordinates.getRewardArr2());

            // 领取任务进度
            boolean result = detectAndClick(rewardCoordinates.getRewardArr3(), "一键领取", 3);
            if (result) {
                randomWait(5000);
            }

            // 切换奖励界面
            mouseOperation(rewardCoordinates.getRewardArr4());

            result = detectAndClick(rewardCoordinates.getRewardArr5(), "领取", 3);
            if (result) {
                // 点击空白处关闭

                publicMethods.clickBlankClose();
                log.info("无名勋礼奖励领取完毕");
            } else {
                log.info("当前无奖励可领");
            }

            // 返回主界面
            simulatingKeyPress("esc");

            matchingUi.mainUi();
        }

    }
}
