package StarRail.module.guide.daily;


import StarRail.module.guide.PublicMethods;
import StarRail.util.MatchingUi;
import StarRail.util.MouseUtil;
import StarRail.util.basic.random.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import static StarRail.util.MouseUtil.*;
import static StarRail.util.basic.keyboard.Keyboard.*;
import static StarRail.util.basic.random.RandomGenerator.randomWait;
import static StarRail.util.basic.screen.ScreenUtil.ocrRecognition;
import static StarRail.util.basic.screen.ScreenUtil.regionScreenImage;


/**
 * 每日实训 模块
 *
 * @author : 真寻
 * 2023/7/21
 */
@Slf4j
@Component
public class DailyPractice {
    private final Map<String, String> knownTasksList = Map.ofEntries(

            Map.entry("拍照1次", "task1"),
            Map.entry("完成1次材料", "task2"),
            Map.entry("侵蚀隧", "task3"),
            Map.entry("使用1件消耗品", "task4"),
            Map.entry("派遣1次委托", "task5"),
            Map.entry("金", "task6"),
            Map.entry("凝滞", "task7"),
            Map.entry("将任意遗器等级", "task8"),
            Map.entry("累计施放2次秘技", "task9"),
            Map.entry("赤", "task10"),
            Map.entry("使用支援角色", "task11")
//            Map.entry("分解任意1件遗器","task12")
    );
    @Autowired
    private MatchingUi matchingUi;
    @Autowired
    private PublicMethods publicMethods;

    /**
     * 拖动每日实训卡片
     * 最小拖动距离
     * 1920：650
     * 125%：520
     * 抖动距离：170
     *
     * @param direction 拖动方向
     *                  0：向左拖动【默认】
     *                  1：向右拖动
     */
    private void draggingCards(int... direction) {
        int orientation = 0;
        for (int i : direction) {
            orientation = i;
        }

        // 起始区间
        int[] startingCoordinate = {960, 450, 1130, 620};
        // 目标区间
        int[] targetCoordinates = {270, 450, 440, 620};

        if (orientation == 0) {
            // 向左拖动卡片
            dragOperations(startingCoordinate, targetCoordinates);
        }
        if (orientation == 1) {
            // 向右拖动卡片
            dragOperations(targetCoordinates, startingCoordinate);
        }

        // 随机等待
        randomWait();
    }

    /**
     * 关闭
     * 适用范围：背包, 合成, 邮件
     *
     * @param matching 【可选】页面检测 默认 true
     */
    protected void closeButton(boolean... matching) {
        int[] area = new int[]{1470, 31, 1510, 70};
        mouseOperation(area);

        boolean mc = true;
        for (boolean b : matching) {
            mc = b;
        }
        if (mc) {
            matchingUi.menuUi();
        }
    }

    /**
     * 卡片内容完整性检查
     *
     * @param area 识别区域坐标
     */
    private String getCardContent(int[] area) {
        int number = 0;
        String result;
        do {
            result = ocrRecognition(regionScreenImage(area));
            System.out.println(result);
            number++;
            if (number > 3) {
                break;
            }
        } while ("".equals(result));
        return result;
    }

    /**
     * 获取每日实训内容
     *
     * @return 每日实训内容
     */
    private String[] getDailyTrainingTasks() {
        // 第1~4卡片数量
        int cardQuantity4 = 4;
        // 第5~6卡片数量
        int cardQuantity6 = 6;

        String[] result = new String[6];

        // 第1~4张卡片识别区初始选框
        // w = 203, h = 105, s = 65
        int[] area = {235, 377, 437, 483};

        // 获取第1~4张卡片内容
        for (int i = 0; i < cardQuantity4; i++) {
            result[i] = getCardContent(area);

            if (i == cardQuantity4 - 1) {
                break;
            }

            area[0] = area[0] + 243;
            area[2] = area[2] + 243;
        }

        // 向左拖动卡片
        draggingCards();

        // 第5~6张卡片识别区初始选框
        // w = 203, h = 105, s = 65
        area = new int[]{829, 377, 1032, 483};

        // 获取第5~6张卡片内容
        for (int i = 4; i < cardQuantity6; i++) {
            result[i] = getCardContent(area);
            if (i == cardQuantity6 - 1) {
                break;
            }
            area[0] = area[0] + 243;
            area[2] = area[2] + 243;
        }

        // 向右拖动卡片
        draggingCards(1);

        return result;
    }

    /**
     * 匹配已知每日实训内容并完成
     * 此方法采用反射
     * 【可选的另一种做法是，采用 Levenshtein 距离算法】
     */
    public void cleanDailyPractice() {
        // 初始活跃值
        int activeValue = 0;
        // 奖励所需活跃值
        int needActiveValue = 500;
        // 临时变量
        int temp;

        // 打开指南
        openGuideUi();

        String[] dailyTrainingContent = getDailyTrainingTasks();
        log.info("今日实训内容 {}", Arrays.toString(dailyTrainingContent));

        // 返回主界面
        simulatingKeyPress("esc");
        matchingUi.mainUi();

        // 匹配已知任务
        DailyPracticeImpl dailyPractice = new DailyPracticeImpl();


        for (String item : dailyTrainingContent) {
            for (String ktl : knownTasksList.keySet()) {
                if (item.contains(ktl)) {
                    try {
                        log.info("开始完成实训任务 {}", ktl);
                        // 通过反射执行对应方法
                        Method method = dailyPractice.getClass().getDeclaredMethod(knownTasksList.get(ktl));
                        temp = (int) method.invoke(dailyPractice);
                        if (temp != 0) {
                            activeValue += temp;
                            log.info("实训任务 {} 已完成, 当前活跃值为 {}", ktl, activeValue);
                        }
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        log.error(String.valueOf(e));
                        throw new RuntimeException(e);
                    }
                }
            }
            // 判断活跃值是否提前达标
            if (activeValue >= needActiveValue) {
                break;
            }
        }

        // 判断活跃值是否达标
        if (activeValue >= needActiveValue) {
            // 领取活跃值
            int[] area = {319, 606, 425, 633};
            boolean result = detectAndClick(area, "领取", 3);
            while (result) {
                result = detectAndClick(area, "领取", 3);
            }

            area = new int[]{1217, 220, 1257, 260};
            mouseOperation(area);

            publicMethods.clickBlankClose();

            log.info("活跃值达到 {}, 奖励已领取", activeValue);

            // 返回主界面
            simulatingKeyPress("esc");
        } else {
            log.info("奖励未领取! 当前活跃值为 {}, 还需 {} 活跃值", activeValue, needActiveValue - activeValue);
        }

        matchingUi.mainUi();
    }

    /**
     * 打开背包
     *
     * @return true:通过主界面打开   false:通过菜单打开
     */
    protected boolean openBackpack() {
        Random random = new Random();
        if (random.nextBoolean()) {
            Robot robot = keyDown("alt");
            int[] area = {1325, 17, 1363, 60};
            MouseUtil.mouseOperation(area);
            RandomGenerator.randomWait();
            keyUp(robot, "alt");

            // 等待Ui
            RandomGenerator.randomWait();
            return true;
        }

        // 打开菜单面板
        simulatingKeyPress("esc");
        matchingUi.menuUi();

        // 选择背包
        int[] area = new int[]{1137, 671, 1215, 750};
        MouseUtil.mouseOperation(area);

        // 等待Ui
        RandomGenerator.randomWait();
        return false;
    }

    /**
     * 打开指南
     */
    private void openGuideUi() {
        System.out.println(1);
        // 打开每日实训面板
        simulatingKeyPress("1");

        matchingUi.guideUi();
        System.out.println(1);
    }

}
