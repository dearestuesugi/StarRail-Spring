package StarRail.module.guide;

import StarRail.general.Coordinates;
import StarRail.util.MatchingUi;
import StarRail.util.MouseUtil;
import StarRail.util.basic.random.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static StarRail.util.MouseUtil.detectAndClick;
import static StarRail.util.basic.keyboard.Keyboard.simulatingKeyPress;
import static StarRail.util.basic.random.RandomGenerator.randomWait;
import static StarRail.util.basic.screen.ScreenUtil.ocrRecognition;
import static StarRail.util.basic.screen.ScreenUtil.regionScreenImage;

/**
 * @author : 真寻
 * 2023/9/5
 */
@Component
public class PublicMethods {
    @Autowired
    private MatchingUi matchingUi;
    @Autowired
    private Coordinates.GuideCoordinates guideCoordinates;
    @Autowired
    private Coordinates.GeneralCoordinates generalCoordinates;
    @Autowired
    private Coordinates.ChallengeCoordinates challengeCoordinates;

//    /**
//     * 难度选择
//     *
//     * @param level 挑战的难度
//     * @return 难度对应的区域坐标
//     */
//    public static int[] selectionDifficulty(int level) {
//        /* 初始坐标
//         * w : 53 h : 53
//         * s : 33 + h = 86
//         */
//        int[] initialCoordinates = {79, 105, 132, 158};
//        int interval = 86;
//        initialCoordinates = new int[]{
//                initialCoordinates[0], initialCoordinates[1] + interval * (level - 1),
//                initialCoordinates[2], initialCoordinates[3] + interval * (level - 1)
//        };
//
//        return initialCoordinates;
//    }

    /**
     * 生存索引左侧标题栏拖动
     * 最小拖动距离
     * 1920：350
     * 125%：280
     * 抖动距离：280
     *
     * @param direction 拖动方向
     *                  0：向上拖动【默认】
     *                  1：向下拖动
     */
    public void draggingLeft(int... direction) {
        int orientation = 0;
        for (int i : direction) {
            orientation = i;
        }

        // 起始区间
        int[] startingCoordinate =  guideCoordinates.getLCardDown();
        // 目标区间
        int[] targetCoordinates = guideCoordinates.getLCardUp();

        if (orientation == 0) {
            // 向上拖动卡片
            MouseUtil.dragOperations(targetCoordinates, startingCoordinate);
        }
        if (orientation == 1) {

            // 向下拖动卡片
            MouseUtil.dragOperations(startingCoordinate, targetCoordinates);
        }

        // 随机等待
        RandomGenerator.randomWait();
    }

    /**
     * 生存索引右侧内容栏拖动
     * 最小拖动距离
     * 1920：350
     * 125%：280
     * 抖动距离：280
     *
     * @param direction 拖动方向
     *                  0:向上拖动【默认】
     *                  1:向下拖动
     *                  甩动开关【可选】
     *                  0:关闭甩动
     *                  1：开启甩动
     */
    public  void draggingRight(int... direction) {
        boolean sweep = false;
        int orientation = 0;
        if (direction.length == 1) {
            orientation = direction[0];
        }
        if (direction.length == 2) {
            orientation = direction[0];
            if (direction[1] == 1) {
                sweep = true;
            }
        }

        // 起始区间
        int[] startingCoordinate = guideCoordinates.getRCardDown();
        // 目标区间
        int[] targetCoordinates = guideCoordinates.getRCardUp();

        if (orientation == 0) {
            // 向上拖动卡片
            MouseUtil.dragOperations(targetCoordinates, startingCoordinate, sweep);
        }
        if (orientation == 1) {

            // 向下拖动卡片
            MouseUtil.dragOperations(startingCoordinate, targetCoordinates, sweep);
        }

        // 随机等待
        RandomGenerator.randomWait();
    }

    /**
     * 挑战状态检测
     *
     * @param again       再来一次次数
     * @param waitingTime 检测时长
     * @return 再来一次剩余次数
     */
    public int challengeStatus(int again, int waitingTime) {
        int time = waitingTime * 1000;
        // 开始时间
        long sTime = System.currentTimeMillis();
        //结束时间
        long eTime = System.currentTimeMillis();
        while (eTime - sTime < time) {
            // 挑战失败
            if (ocrRecognition(regionScreenImage(challengeCoordinates.getChallengeArr9()), "战斗失败")) {
                detectAndClick(challengeCoordinates.getChallengeArr9(), "战斗失败");
                matchingUi.mainUi();

                // 降低难度, 再来一次


                break;
            }

            // 挑战成功
            if (ocrRecognition(regionScreenImage(challengeCoordinates.getChallengeArr1()), "再来一次")) {
                if (again == 0) {
                    break;
                }
                again--;
                detectAndClick((challengeCoordinates.getChallengeArr1()), "再来一次");
            }

            randomWait(5000);
            eTime = System.currentTimeMillis();
        }
        return again;
    }

    /**
     * 打开生存索引界面
     */
    public void openSurvivalUi() {
        // 打开每日实训面板
        simulatingKeyPress("f4");

        // 等待UI
        do {
            if (matchingUi.guideUi()) {
                // 打开生存索引面板
                MouseUtil.mouseOperation(guideCoordinates.getGuideUi2());
            }
        } while (!matchingUi.survivalUi());
    }


    /**
     * 点击空白处关闭
     * 适用范围：无名勋礼, 邮件, 模拟宇宙, 背包
     */
    public void clickBlankClose() {
        // 点击空白处关闭
        boolean result = MouseUtil.detectAndClick(generalCoordinates.getGeneralArr4(), "点击空白处关闭", 5);
        if (!result) {
            MouseUtil.detectAndClick(generalCoordinates.getGeneralArr4(), "点击空自处关闭", 5);
        }
    }
}
