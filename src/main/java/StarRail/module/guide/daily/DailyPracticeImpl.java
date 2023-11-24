package StarRail.module.guide.daily;

import StarRail.general.Coordinates;
import StarRail.general.Record;
import StarRail.module.guide.PublicMethods;
import StarRail.module.guide.challenge.materials.BreakthroughMaterial;
import StarRail.module.guide.challenge.materials.EmpiricalMaterials;
import StarRail.module.guide.challenge.materials.TraceMaterial;
import StarRail.module.guide.challenge.relic.Relic;
import StarRail.util.MatchingUi;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Point;
import org.springframework.beans.factory.annotation.Autowired;

import static StarRail.util.MouseUtil.detectAndClick;
import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.basic.keyboard.Keyboard.simulatingKeyPress;
import static StarRail.util.basic.random.RandomGenerator.getRandomInt;
import static StarRail.util.basic.random.RandomGenerator.randomWait;
import static StarRail.util.basic.screen.ScreenUtil.getScreenCoordinate;
import static StarRail.util.basic.screen.ScreenUtil.gettingNumbers;

/**
 * @author : 真寻
 * 2023/7/29
 */
@Slf4j
public class DailyPracticeImpl extends DailyPractice {
    @Autowired
    private Record record;
    @Autowired
    private MatchingUi matchingUi;
    @Autowired
    private Coordinates.MenuCoordinates menuCoordinates;
    @Autowired
    private TraceMaterial traceMaterial;
    @Autowired
    private EmpiricalMaterials empiricalMaterials;
    @Autowired
    private BreakthroughMaterial breakthroughMaterial;
    @Autowired
    private Relic relic;
    @Autowired
    private Coordinates.GeneralCoordinates generalCoordinates;
    @Autowired
    private PublicMethods publicMethods;

    /**
     * 拍照1次
     *
     * @return 100 活跃值
     */
    protected int task1() {
        simulatingKeyPress("esc");

        matchingUi.menuUi();

        // 点击相机
        mouseOperation(menuCoordinates.getMenuArr9());
        randomWait();

        // 拍照
        Point point = getScreenCoordinate("data/template/shutter.png");
        mouseOperation(point);

        mouseOperation(menuCoordinates.getMenuArr10());
        randomWait();
        mouseOperation(menuCoordinates.getMenuArr10());

        matchingUi.mainUi();

        return 100;
    }

    /**
     * 合成一次材料
     *
     * @return 100 活跃值
     */
    protected int task2() {
        simulatingKeyPress("esc");

        matchingUi.menuUi();

        // 点击合成按钮
        mouseOperation(menuCoordinates.getMenuArr13());

        // 等待Ui
        randomWait();

        //切换到材料合成
        mouseOperation(menuCoordinates.getMenuArr14());

        // 合成
        detectAndClick(menuCoordinates.getMenuArr15(), "合成");
        // 确认
        mouseOperation(menuCoordinates.getMenuArr16());

        // 点击空白处关闭
        publicMethods.clickBlankClose();

        simulatingKeyPress("esc");
        randomWait();
        simulatingKeyPress("esc");

        matchingUi.mainUi();

        return 100;
    }

    /**
     * 侵蚀隧洞
     *
     * @return 100 活跃值
     */
    protected int task3() {
        if (record.getPhysicalPowerValue() < 40) {
            return 0;
        }

        record.setTryAgain(false);
        relic.tunnelOfErosion();
        return 100;
    }

    /**
     * 使用1件消耗品（奇巧零食）
     *
     * @return 100 活跃值
     */
    protected int task4() {
        int number;
        int maxNumber = 5;
        do {
            number = gettingNumbers(generalCoordinates.getGeneralArr2());
        } while (number == -1);

        if (number == maxNumber) {
            // 释放秘技
            simulatingKeyPress("e");
            // 切换角色
            simulatingKeyPress("2");
            // 释放秘技
            simulatingKeyPress("e");
        }

        // 打开背包
        openBackpack();
        // 选择消耗品
        mouseOperation(menuCoordinates.getMenuArr17());

        // 选择奇巧零食
        Point point;
        do {
            point = getScreenCoordinate("data/template/kit_kat_snacks.png", 35);
        } while (point == null);
        mouseOperation(point);

        // 使用
        detectAndClick(menuCoordinates.getMenuArr18(), "使用");

        // 确认使用
        detectAndClick(menuCoordinates.getMenuArr19(), "确认");

        // 等待
        randomWait();

        // 返回菜单
        closeButton();

        // 返回主界面
        simulatingKeyPress("esc");
        matchingUi.mainUi();

        return 100;
    }

    /**
     * 派遣1次委托
     *
     * @return 100 活跃值
     */
    protected int task5() {
        if (record.isEntrustProgress()) {
            return 100;
        }
        return 0;
    }

    /**
     * 完成1次 拟造花萼(金)
     *
     * @return 100 活跃值
     */
    protected int task6() {
        if (record.getPhysicalPowerValue() < 10) {
            log.info("开拓力不足, 无法完成实训任务 完成1次 拟造花萼(金)");
            return 0;
        }

        record.setTryAgain(false);
        empiricalMaterials.empiricalMaterials();
        return 100;
    }

    /**
     * 完成1次 凝滞虚影
     *
     * @return 100 活跃值
     */
    protected int task7() {
        if (record.getPhysicalPowerValue() < 30) {
            log.info("开拓力不足, 无法完成实训任务 完成1次 凝滞虚影");
            return 0;
        }

        record.setTryAgain(false);
        breakthroughMaterial.breakthroughMaterial();
        return 100;
    }

    /**
     * 将任意遗器等级提升1次
     *
     * @return 100 活跃值
     */
    protected int task8() {
        // 打开背包
        openBackpack();

        // 选择遗器界面
        mouseOperation(menuCoordinates.getMenuArr20());

        // 拖动3~7次
        int number = getRandomInt(7, 13);
        for (int i = 0; i < number; i++) {
            publicMethods.draggingLeft();
        }

        // 选择遗器
        mouseOperation(menuCoordinates.getMenuArr21());

        // 点击详情
        detectAndClick(menuCoordinates.getMenuArr22(), "详情");

        // 等待Ui
        randomWait();

        // 点击强化
        detectAndClick(menuCoordinates.getMenuArr23(), "强化");

        // 添加
        Point point;
        do {
            mouseOperation(menuCoordinates.getMenuArr24());
            point = getScreenCoordinate("data/template/lose.png", 35);
        } while (point == null);
        // 选择遗失碎金
        mouseOperation(point);

        // 强化
        mouseOperation(menuCoordinates.getMenuArr23());
        randomWait(1500);
        mouseOperation(menuCoordinates.getMenuArr23());

        // 关闭页面
        closeButton(false);
        randomWait();
        closeButton(false);

        matchingUi.mainUi();
        return 100;
    }

    /**
     * 累计施放2次秘技
     *
     * @return 100 活跃值
     */
    protected int task9() {
        int number;
        int minNumber = 2;
        System.out.println("aaa");

        do {
            /*
             * bug!!
             * 坐标可能有问题，数字无法正常识别
             *
             */
            int[] area = {1322, 670, 1336, 691};
            number = gettingNumbers(area);
            if (number == -1) {
                simulatingKeyPress("e");
                randomWait();
            }
            System.out.println("bbb");
        } while (number == -1);

        System.out.println("ccc");

        if (number >= minNumber) {
            simulatingKeyPress("e");
            randomWait(1000);
            randomWait();
            simulatingKeyPress("e");
            randomWait();
            System.out.println("ddd");
        } else {
            task4();
            task9();
            System.out.println("eee");
        }
        System.out.println("fff");

        return 100;
    }

    /**
     * 完成1次 拟造花萼(赤)
     *
     * @return 100 活跃值
     */
    protected int task10() {
        if (record.getPhysicalPowerValue() < 10) {
            log.info("开拓力不足, 无法完成实训任务 完成1次 拟造花萼(赤)");
            return 0;
        }

        record.setTryAgain(false);
        traceMaterial.traceMaterial();
        return 100;
    }

    /**
     * 使用支援角色并获得战斗胜利1次
     *
     * @return 200
     */
    protected int task11() {
        if (record.isUsingSupportRolesAndWin()) {
            return 200;
        }
        return 0;
    }

//    /**
//     * 分解任意1件遗器
//     *
//     * @return 100
//     */
//    protected int task12() {
//        // 打开背包
//        boolean returnValue = openBackpack();
//
//        // 选择遗器界面
//        int[] area = new int[]{631, 25, 695, 77};
//        mouseOperation(area);
//
//        // 选择分解
//        area = new int[]{925, 775, 1051, 803};
//        mouseOperation(area);
//
//        // 拖动10~15次
//        int number = getRandomInt(10, 15);
//        for (int i = 0; i < number; i++) {
//            draggingRight(0, 1);
//        }
//
//        do {
//            // 选择遗器
//            area = new int[]{419, 152, 1399, 667};
//            mouseOperation(area);
//
//            // 分解
//            area = new int[]{1170, 770, 1479, 802};
//            detectAndClick(area, "分解");
//
//            // 确认
//            area = new int[]{809, 616, 1062, 651};
//        }
//        while (!detectAndClick(area, "确认", 3));
//
//        // 关闭
//        clickBlankClose();
//        closeButton(false);
//        randomWait();
//        if (returnValue) {
//            closeButton(false);
//        } else {
//            closeButton(true);
//            simulatingKeyPress("esc");
//        }
//        matchingUi.mainUi();
//        return 100;
//    }

    /**
     * 累计击碎3个可破坏物
     *
     * @return 200
     */
    protected int task13() {
        // 打开地图
        simulatingKeyPress("m");

        // 打开星轨航图
        int[] area = {1153, 93, 1500, 123};
        detectAndClick(area, "航图");

        // 选择雅利洛-IV
        area = new int[]{760, 150, 820, 255};
        mouseOperation(area);

        // 选择城郊雪原
        area = new int[]{1139, 310, 1500, 370};
        detectAndClick(area, "城郊雪原");

        // 传送
        area = new int[]{611, 407, 639, 435};
        mouseOperation(area);
        area = new int[]{1091, 761, 1457, 793};
        detectAndClick(area, "传送");

        matchingUi.mainUi();

        return 200;
    }


}
