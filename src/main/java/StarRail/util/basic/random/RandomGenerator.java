package StarRail.util.basic.random;

import java.util.Random;

/**
 * 随机产生器
 *
 * @author : 真寻
 */
public class RandomGenerator {
    /**
     * 随机等待700~2100毫秒
     *
     * @param wTime 【可选】指定等待时间（毫秒）
     */
    public static void randomWait(int... wTime) {
        int waitTime = getRandomInt(700, 2100);
        for (int time : wTime) {
            waitTime = time;
        }

//        System.out.println("等待 " + waitTime + " 毫秒");
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 随机等待指定区间的毫秒
     *
     * @param wTime 指定等待时间（毫秒）
     */
    public static void randomWait(int wTime,int wTime1) {
        int waitTime = getRandomInt(wTime, wTime1);

//        System.out.println("等待 " + waitTime + " 毫秒");
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成指定范围内的随机整数
     *
     * @param min 随机整数的最小值（包含）
     * @param max 随机整数的最大值（不包含）
     * @return 生成的随机整数
     */
    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * 生成指定范围内的随机double类型的数
     *
     * @param min 随机double类型的数的最小值（包含）
     * @param max 随机double类型的数的最大值（不包含）
     * @return 生成的随机double类型的数
     */
    public static double getRandomDouble(double min, double max) {
        Random random = new Random();
        return random.nextDouble() * (max - min) + min;
    }

    /**
     * 随机事件开关
     *
     * @return 生产的随机boolean
     */
    public static boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

}
