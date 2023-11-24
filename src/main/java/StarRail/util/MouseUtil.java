package StarRail.util;

import StarRail.util.basic.mouse.Mouse;
import StarRail.util.basic.random.RandomGenerator;
import org.opencv.core.Point;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @author : 真寻
 * 2023/7/26
 */
public class MouseUtil {
    /**
     * 等待UI加载完成之后点击
     *
     * @param area         匹配坐标数组【左上X,Y,右下X,Y】
     * @param matchingText 待匹配的文本
     * @param waitingTime  等待的时间【默认30秒】
     * @return 匹配结果 boolean
     */
    public static boolean detectAndClick(int[] area, String matchingText, int... waitingTime) {
        if (MatchingUi.uiJudgment(area, matchingText, waitingTime)) {
            mouseOperation(area);
            return true;
        }
        return false;
    }

    /**
     * 鼠标移动点击操作
     *
     * @param area 区域坐标
     */
    public static void mouseOperation(int[] area) {
        // 选择坐标
        int x = RandomGenerator.getRandomInt(area[0], area[2]);
        int y = RandomGenerator.getRandomInt(area[1], area[3]);

        // 点击坐标
        Mouse.smoothMove(x, y);
        Mouse.mouseLeftClick();

        // 随机等待
        RandomGenerator.randomWait();
    }

    /**
     * 鼠标移动点击操作
     *
     * @param point 坐标
     */
    public static void mouseOperation(Point point) {
        // 选择坐标
        int x = (int) point.x;
        int y = (int) point.y;

        // 点击坐标
        Mouse.smoothMove(x, y);
        Mouse.mouseLeftClick();

        // 随机等待
        RandomGenerator.randomWait();
    }

    /**
     * 鼠标拖动操作
     *
     * @param section  拖动首区间
     * @param section1 目标区间
     * @param wait     甩动开关【默认:false】
     */
    public static void dragOperations(int[] section, int[] section1, boolean... wait) {
        // 选择起始坐标
        int x = RandomGenerator.getRandomInt(section[0], section[2]);
        int y = RandomGenerator.getRandomInt(section[1], section[3]);
        Mouse.smoothMove(x, y);

        // 单击鼠标
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        // 单击
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        //选择终点坐标
        x = RandomGenerator.getRandomInt(section1[0], section1[2]);
        y = RandomGenerator.getRandomInt(section1[1], section1[3]);
        Mouse.smoothMove(x, y);

        boolean w = false;
        for (boolean b : wait) {
            w = b;
        }
        if (!w) {
            RandomGenerator.randomWait(300);
        }

        // 释放
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
