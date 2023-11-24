package StarRail.util;

import StarRail.general.Coordinates;
import StarRail.util.basic.random.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static StarRail.util.basic.screen.ScreenUtil.ocrRecognition;
import static StarRail.util.basic.screen.ScreenUtil.regionScreenImage;

/**
 * Ui检测
 *
 * @author : 真寻
 */
@Slf4j
@Component
public class MatchingUi {
    @Autowired
    private Coordinates.GeneralCoordinates generalCoordinates;
    @Autowired
    private Coordinates.MenuCoordinates menuCoordinates;
    @Autowired
    private Coordinates.RewardCoordinates rewardCoordinates;
    @Autowired
    private Coordinates.ChallengeCoordinates challengeCoordinates;
    @Autowired
    private Coordinates.GuideCoordinates guideCoordinates;

    /**
     * 检测指定区域内的UI是否加载完成
     *
     * @param area         匹配坐标数组【左上X,Y,右下X,Y】
     * @param matchingText 待匹配的文本
     * @param waitingTime  等待的时间【默认30秒】
     * @return 匹配结果 boolean
     */
    public static boolean uiJudgment(int[] area, String matchingText, int... waitingTime) {
        // 等待时间
        int time = 30000;
        // 启用日志记录
        boolean enableLog = false;

        int i = 0;
        for (int t : waitingTime) {
            time = t * 1000;

//            if (i == 1) {
//                enableLog = false;
//            }
//
//            i++;
        }

        // 开始时间
        long sTime = System.currentTimeMillis();
        //结束时间
        long eTime = System.currentTimeMillis();
        while (eTime - sTime < time) {
//            System.out.println(matchingText);
            if (ocrRecognition(regionScreenImage(area), matchingText)) {
//                mouseOperation(area[0], area[1], area[2], area[3]);
                return true;
            }
            RandomGenerator.randomWait(1000);
            eTime = System.currentTimeMillis();

        }
        if (enableLog) {
            log.error("匹配 " + matchingText + " 失败！");
        }
        return false;
    }

    /**
     * 主 界面检测【默认30秒】
     *
     * @return 匹配结果 boolean
     */
    public boolean mainUi() {
        return uiJudgment(generalCoordinates.getGeneralArr1(), "导航");
    }

    /**
     * 主 界面检测【需指定等待时间】
     *
     * @param time 等待时间
     * @return 匹配结果 boolean
     */
    public boolean mainUi(int time) {
        return uiJudgment(generalCoordinates.getGeneralArr1(), "导航", time);
    }

    /**
     * 菜单(esc) 界面检测
     *
     * @return 匹配结果 boolean
     */
    public boolean menuUi() {
        // 截图坐标
        return uiJudgment(menuCoordinates.getMenuArr11(), "委托");
    }

    /**
     * 邮件领取
     *
     * @return 匹配结果 boolean
     */
    public boolean emailRewardsUi() {
        return uiJudgment(menuCoordinates.getMenuArr12(), "领取", 3);
    }

    /**
     * 挑战完成 界面检测——时间上限 22 分钟
     *
     * @return boolean
     */
    public boolean endChallengeUi() {
        return uiJudgment(challengeCoordinates.getChallengeArr3(), "退出关卡", 1320);
    }

    /**
     * 无名勋礼 界面检测
     */
    public boolean unknownRewardUi() {
        return uiJudgment(rewardCoordinates.getRewardArr1(), "奖励", 5);
    }

    /**
     * 每日实训 界面检测
     *
     * @return boolean
     */
    public boolean guideUi() {
        return uiJudgment(guideCoordinates.getGuideUi1(), "实训", 3);
    }

    /**
     * 地图 界面检测
     *
     * @return boolean
     */
    public boolean mapUi() {
        return uiJudgment(generalCoordinates.getGeneralArr3(), "地图光标移动");
    }

    /**
     * 生存索引 界面检测
     *
     * @return boolean
     */
    public boolean survivalUi() {
        return uiJudgment(guideCoordinates.getGuideUi1(), "生存索引", 3);
    }


//    /**
//     * 启动器更新 按钮
//     *
//     * @return boolean
//     */
//    public boolean launcherUpdates() {
//        int[] area = {777, 473, 939, 519};
//        return uiJudgment(area, "更新");
//    }
//
//    /**
//     * 启动器更新完成 按钮
//     *
//     * @return boolean
//     */
//    public boolean updateCompletedUi() {
//        int[] area = {630, 547, 880, 588};
//        return uiJudgment(area, "打开启动器", 5);
//    }
//
//    /**
//     * 游戏更新 按钮
//     *
//     * @return boolean
//     */
//    public boolean gameUpdates() {
//        int[] area = {1100, 639, 1160, 670};
//        return uiJudgment(area, "更");
//    }
//
//    /**
//     * 用户协议 界面检测
//     */
//    public boolean userAgreementUi() {
//        int[] area = {802, 566, 1039, 610};
//        return uiJudgment(area, "同意");
//    }
//
//    /**
//     * 启动【模拟宇宙】 按钮检测
//     *
//     * @return boolean
//     */
//    public boolean startButtonDetection() {
//        int[] startButton = {1160, 760, 1399, 799};
//        return uiJudgment(startButton, "启动");
//    }
//
//    /**
//     * 模拟宇宙 界面检测
//     *
//     * @return boolean
//     */
//    public boolean simulatedUniverseMainUi() {
//        int[] startButton = {1039, 827, 1201, 852};
//        return uiJudgment(startButton, "主菜单", 5);
//    }
//
//    /**
//     * 选择奇物 界面检测
//     *
//     * @return boolean
//     */
//    public boolean curiosityUi() {
//        int[] area = {707, 80, 857, 145};
//        return uiJudgment(area, "选择奇物", 3);
//    }
//
//    /**
//     * 选择命途 界面检测
//     *
//     * @return boolean
//     */
//    public boolean selectDestinyUi() {
//        int[] area = {80, 50, 130, 75};
//        return uiJudgment(area, "命途", 3);
//    }


}
