package StarRail.module.guide.challenge;

import StarRail.general.Coordinates;
import StarRail.general.Record;
import StarRail.general.StarRailProperties;
import StarRail.module.guide.PublicMethods;
import StarRail.util.MatchingUi;
import StarRail.util.MouseUtil;
import StarRail.util.basic.random.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Point;
import org.springframework.beans.factory.annotation.Autowired;

import static StarRail.util.MouseUtil.detectAndClick;
import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.basic.keyboard.Keyboard.simulatingKeyPress;
import static StarRail.util.basic.mouse.Mouse.mouseLeftClick;
import static StarRail.util.basic.random.RandomGenerator.randomWait;
import static StarRail.util.basic.screen.ScreenUtil.getScreenCoordinate;
import static StarRail.util.basic.screen.ScreenUtil.saveErrorImage;

/**
 * 挑战通用方法
 *
 * @author : 真寻
 * 2023/11/18
 */
@Slf4j
public class Challenge {
    @Autowired
    protected Coordinates.ChallengeCoordinates challengeCoordinates;
    @Autowired
    protected Coordinates.GuideCoordinates guideCoordinates;
    @Autowired
    private Coordinates.GeneralCoordinates generalCoordinates;
    @Autowired
    protected Record record;
    @Autowired
    protected StarRailProperties properties;
    @Autowired
    protected PublicMethods publicMethods;
    @Autowired
    private MatchingUi matchingUi;

    /**
     * 挑战过程(开始挑战->再来一次?挑战结束)
     */
    protected void challenge(int select, String name) {
        randomWait();

        // 选择对应的挑战
        if (select > 3) {
            publicMethods.draggingRight();
        }
        if (select > 5) {
            publicMethods.draggingRight();
        }
        if (select > 7) {
            publicMethods.draggingRight();
        }

        Point point;
        do {
            point = getScreenCoordinate("src/main/resources/template/" + name + select + ".png", 1);
            // 拖动
            if (point != null) {
                if (point.x > guideCoordinates.getLeftX() && point.x < guideCoordinates.getRightX()) {
                    break;
                }
            }
            publicMethods.draggingRight();
//            draggingRight(1);
            randomWait(700, 1300);
        } while (true);

        // 传送
        int[] area = new int[]{guideCoordinates.getX1(), (int) (point.y + guideCoordinates.getY1()), guideCoordinates.getX2(), (int) (point.y + guideCoordinates.getY2())};
        mouseOperation(area);

        // 体力值
        int physicalPowerValue = record.getPhysicalPowerValue();
        // 当前挑战单次体力消耗
        int challengeCost = record.getChallengeCost();

        /*
        体力清理
         */
        if (name.equals("empirical_material_") || name.equals("trace_material_")) {
            // 挑战次数
            int number = physicalPowerValue / challengeCost;
            if (record.isTryAgain()) {
                if (number >= 6) {
                    number = 6;
                    challengeCost = 60;
                } else {
                    challengeCost = number * challengeCost;
                }
            } else {
                number = 1;
            }
            // 选择挑战次数
            mouseOperation(challengeNumber(number));
        }

        // 开启挑战
        startChallenge();

        if (name.equals("breakthrough_material_")) {
            // 随机点击
            mouseLeftClick();
            randomWait();
            mouseLeftClick();
            randomWait();
            mouseLeftClick();
        }

        // 是否再来一次
        if (record.isTryAgain()) {
            // 根据体力计算再来一次的次数
            int number = physicalPowerValue / challengeCost;

            // 历战余响
            if (name.equals("weeklyChallenge_")) {
                number = 3;
            }

            for (int i = number; i > 1; i--) {
                // 挑战状态检测


                // 等待挑战完成
                if (matchingUi.endChallengeUi()) {
                    physicalPowerValue -= challengeCost;
                    detectAndClick(challengeCoordinates.getChallengeArr1(), "再来一次");
                }
            }
            physicalPowerValue -= challengeCost * number;
        } else {
            physicalPowerValue -= challengeCost;
        }

        // 挑战状态检测

        // 更新体力值
        record.setPhysicalPowerValue(physicalPowerValue);
    }

    /**
     * 挑战完成
     *
     * @param text    完成的挑战名
     * @param getBack 【可选】是否返回生存索引界面, 默认 false
     */
    public void endChallenge(String text, boolean... getBack) {
        // 退出关卡
        detectAndClick(challengeCoordinates.getChallengeArr2(), "退出关卡", 1320);
        if (matchingUi.mainUi()) {
            log.info("{} 挑战完成", text);
            // 打开地图
            RandomGenerator.randomWait();
            simulatingKeyPress("m");

            // 等待UI
            if (matchingUi.mapUi()) {
                // 获取锚点坐标
                Point point = getScreenCoordinate("src/main/resources/template/anchor_poin.png");
                if (point == null) {
                    saveErrorImage();
                    log.error("无法获取锚点坐标 ");
                    simulatingKeyPress("esc");
                } else {
                    // 点击锚点
                    MouseUtil.mouseOperation(point);
                    RandomGenerator.randomWait();

                    // 传送至锚点
                    if (!detectAndClick(generalCoordinates.getGeneralArr5(), "传送")) {
                        saveErrorImage();
                        log.error("无法获取锚点坐标 ");
                        simulatingKeyPress("esc");
                    }
                }
            }

            // 等待UI
            boolean returningIndex = false;
            for (boolean b : getBack) {
                returningIndex = b;
            }
            if (matchingUi.mainUi()) {
                RandomGenerator.randomWait();
                if (returningIndex) {
                    // 返回生存索引界面
                    publicMethods.openSurvivalUi();
                }
            }
        }

    }

    /**
     * 开始挑战
     */
    private void startChallenge() {
        // 是否使用支援角色
        boolean usingSupportRoles = properties.getUsingSupportRoles();
        // 挑战
        detectAndClick(challengeCoordinates.getChallengeArr4(), "挑");

        // 是否使用支援角色
        if (usingSupportRoles) {
            RandomGenerator.randomWait();
            // detectAndClick(area, "支援");
            MouseUtil.mouseOperation(challengeCoordinates.getChallengeArr5());

            // 根据挑战选择支援角色？


            detectAndClick(challengeCoordinates.getChallengeArr6(), "入队");
        } else {
            // 使用配队1

        }

        // 开始挑战
        detectAndClick(challengeCoordinates.getChallengeArr7(), "开始挑战");
    }

    /**
     * 获取对应挑战次数的坐标
     *
     * @param number 挑战次数
     * @return 所选挑战次数的坐标
     */
    private int[] challengeNumber(int number) {
        /*
         * 初始坐标-第一次
         *     W:350    H:26    S: +-23
         *     圆点偏移量:10    圆点中心: -5    圆点初始X:1070    圆点间隔S:66
         *     area = new int[]{1060,705,1410,731};
         */
        int[] area = challengeCoordinates.getChallengeArr8();

        // 处理坐标
        if (number == 6) {
            area = new int[]{area[0], area[1], area[2], area[3] - 10};
        }
        number = number - 1;
        area = new int[]{area[0] + number * 66, area[1], area[2] + number * 66, area[3]};

        return area;
    }

//    /**
//     * 挑战状态检测
//     */
//    void test(){
//        // 挑战状态检测
//        PublicMethods publicMethods = new PublicMethods();
//        int result = publicMethods.challengeStatus(numberOfChallenges, time);
//        if (result == 0) {
//            // 更新体力
//            physicalPowerValue -= appointedDaySignConsumption;
//            // 更新历战余响标识
//
//            updateStartRailProfiles("appointedDaySign", "true");
//            updateStartRailProfiles("weeklyChallengeCompletionTime", dateTimeFormat());
//
//            // 退出关卡, 结束挑战, 回复状态
//            endChallenge("行迹高级材料/光锥 " + name[weeklyChallengeSelection - 1] + "·历战余响", true);
//        } else {
//            physicalPowerValue -= appointedDaySignConsumption - (result + 1) * 30;
//            if (numberRetries != 0) {
//                publicMethods.openSurvivalUi();
//                weeklyChallenge();
//                numberRetries--;
//            }
//            log.warn("历战余响挑战失败");
//
//        }
//    }

}
