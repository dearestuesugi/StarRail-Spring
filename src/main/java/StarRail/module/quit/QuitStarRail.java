package StarRail.module.quit;

import StarRail.general.Coordinates;
import StarRail.util.MatchingUi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static StarRail.util.MouseUtil.detectAndClick;
import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.basic.keyboard.Keyboard.simulatingKeyPress;

/**
 * 退出星轨
 *
 * @author : 真寻
 * 2023/10/5
 */
@Slf4j
@Component
public class QuitStarRail {
    @Autowired
    private Coordinates.QuitStarRailCoordinates coordinates;
    @Autowired
    private MatchingUi matchingUi;

    /**
     * 退出星轨
     */
    public void quitStarRail() {
        // 开始时间
        long sTime = System.currentTimeMillis();

        simulatingKeyPress("esc");
        if (matchingUi.menuUi()) {
            // 点击退出按钮
            mouseOperation(coordinates.getQuitArr1());
            // 确认退出
            detectAndClick(coordinates.getQuitArr2(), "确认");
            // 确认退出游戏
            detectAndClick(coordinates.getQuitArr3(), "退出");
            detectAndClick(coordinates.getQuitArr4(), "确定");
            // 关闭启动器
            detectAndClick(coordinates.getQuitArr5(), "X");
            // 结束时间
            long eTime = System.currentTimeMillis();
            log.info("游戏已关闭, 耗时 " + (eTime - sTime) / 1000 + "秒");
        }
    }
}
