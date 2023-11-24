package StarRail.util.basic.mouse;


import StarRail.util.basic.random.RandomGenerator;
import StarRail.util.basic.screen.GetScreenResolution;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * 鼠标事件
 *
 * @author 真寻
 */
public class Mouse {
    /**
     * 鼠标随机移动次数
     */
    public static void mouseRandomEvents() {
        int frequency = RandomGenerator.getRandomInt(1, 7);
        System.out.println("鼠标随机移动：" + frequency + "次");
        for (int i = 1; i <= frequency; i++) {
            randomCoordinate();
            try {
                Thread.sleep(RandomGenerator.getRandomInt(10, 210));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 产生随机光标并移动
     */
    private static void randomCoordinate() {
        // 移动范围
        int defaultX = 0;
        int defaultY = 0;
        Dimension dimension = GetScreenResolution.getCurrentScreenResolution();
        int maxX = dimension.width;
        int maxY = dimension.height;

        // 随机坐标
        int x = RandomGenerator.getRandomInt(defaultX, maxX);
        int y = RandomGenerator.getRandomInt(defaultY, maxY);

        // 随机移动
        smoothMove(x, y);
    }

    /**
     * 平滑移动鼠标
     *
     * @param x       目标位置的x坐标
     * @param y       目标位置的y坐标
     * @param maxTime 鼠标移动的最大时间(秒)【可选】
     *                <p>
     *                有一些可能的优化方案：
     *                调整移动距离和时间间隔的计算方法。前面提到的计算方法有可能会导致移动的时间或距离存在一定的误差，因此可以根据具体情况调整计算方法以减小误差。
     *                <p>
     *                使用加速度控制移动速度。可以设置一个初始速度，然后逐渐加速到最大速度，再逐渐减速到目标位置。这样做可以使移动更加平滑和连续。
     *                <p>
     *                根据鼠标当前位置和目标位置之间的路径曲线来计算移动点的位置和时间间隔。这样可以使移动更加自然和符合人类的移动规律，但是需要一定的数学和物理基础。
     */
    public static void smoothMove(int x, int y, double... maxTime) {
        // 随机移动最大时间
        double defaultMaxTime = RandomGenerator.getRandomDouble(0.7, 1.7);
//        System.out.println("最大移动时间" + defaultMaxTime);

        for (double d : maxTime) {
            defaultMaxTime = d;
        }

        // 每个点之间的最大距离
        double maxDistance = RandomGenerator.getRandomDouble(0.9, 1.3);

//        System.out.println("每个点之间的最大距离" + maxDistance);

        // 获取当前鼠标坐标
        Point currentPosition = MouseInfo.getPointerInfo().getLocation();
        int currentX = currentPosition.x;
        int currentY = currentPosition.y;

        // 计算当前坐标与目标坐标的距离
        int distanceX = x - currentX;
        int distanceY = y - currentY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

//        System.out.println("当前坐标与目标坐标的距离" + distance);

        // 计算鼠标移动所需要的时间（不能超过最大时间）
        double time = Math.min(distance / maxDistance, defaultMaxTime);

//        System.out.println("计算鼠标移动所需要的时间" + time);

        // 计算每个点之间移动的时间
        double interval = time / Math.ceil(distance / maxDistance);

//        System.out.println("计算每个点之间移动的时间" + interval);

        // 计算每个点的坐标
        double stepX = distanceX / Math.ceil(distance / maxDistance);
        double stepY = distanceY / Math.ceil(distance / maxDistance);

        // 创建Robot对象
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        // 移动鼠标到第一个点
        robot.mouseMove(currentX, currentY);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //在每个点停留interval的时间，以实现平滑的移动效果
        for (int i = 0; i < Math.ceil(distance / maxDistance); i++) {
            robot.mouseMove((int) (currentX + stepX * i), (int) (currentY + stepY * i));
            try {
                Thread.sleep((int) (interval * 500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 最后，移动鼠标到最终目标坐标
        robot.mouseMove(x, y);
    }

    /**
     * 点击鼠标左键
     */
    public static void mouseLeftClick() {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        // 单击
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        // 随机延时等待
//        int a = RandomEvent.getRandomInt(111, 521);
        int a = RandomGenerator.getRandomInt(111, 521);
        robot.delay(a);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}