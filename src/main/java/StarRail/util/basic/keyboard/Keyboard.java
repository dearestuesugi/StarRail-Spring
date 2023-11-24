package StarRail.util.basic.keyboard;

import StarRail.util.basic.random.RandomGenerator;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author : 真寻
 * 2023/7/26
 */
public class Keyboard {
    /**
     * 按键按下间隔时间
     */
    private final static int minPressTime = 100;
    private final static int maxPressTime = 500;
    /**
     * 按键释放间隔时间
     */
    private final static int minReleaseTime = 100;
    private final static int maxReleaseTime = 300;

    /**
     * 按下按键
     */
    public static Robot keyDown(String key) {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        // 判断按键类型
        if ("ctrl".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_CONTROL);
        } else if ("alt".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_ALT);
        } else if ("shift".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        } else if ("windows".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_WINDOWS);
        } else if ("esc".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_ESCAPE);
        } else if ("f4".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_F4);
        } else if ("f2".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_F2);
        } else if ("-".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_MINUS);
        } else if (":".equalsIgnoreCase(key)){
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_SEMICOLON);
        }else {
            // 如果是普通字符，A-Z a-z 0-9,转换成 int 类型的 key code
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));

            // 如果是大写字母，先按下Shift键
            if (Character.isUpperCase(key.charAt(0))) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            robot.keyPress(keyCode);
        }
        robot.delay(RandomGenerator.getRandomInt(minPressTime, maxPressTime));

        return robot;
    }

    /**
     * 释放按键
     */
    public static void keyUp(Robot robot, String key) {
        if ("ctrl".equalsIgnoreCase(key)) {
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } else if ("alt".equalsIgnoreCase(key)) {
            robot.keyRelease(KeyEvent.VK_ALT);
        } else if ("shift".equalsIgnoreCase(key)) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else if ("windows".equalsIgnoreCase(key)) {
            robot.keyRelease(KeyEvent.VK_WINDOWS);
        } else if ("esc".equalsIgnoreCase(key)) {
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        } else if ("f4".equalsIgnoreCase(key)) {
            robot.keyRelease(KeyEvent.VK_F4);
        } else if ("f2".equalsIgnoreCase(key)) {
            robot.keyPress(KeyEvent.VK_F2);
        } else if ("-".equalsIgnoreCase(key)) {
            robot.keyRelease(KeyEvent.VK_MINUS);
        }else if (":".equalsIgnoreCase(key)){
            robot.keyRelease(KeyEvent.VK_SEMICOLON);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }else {
            // 如果是普通字符，转换成 int 类型的 key code
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));

            robot.keyRelease(keyCode);

            // 如果是大写字母，释放Shift键
            if (Character.isUpperCase(key.charAt(0))) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
        robot.delay(RandomGenerator.getRandomInt(minReleaseTime, maxReleaseTime));
        new Thread(robot::waitForIdle).start();
    }

    /**
     * 模拟按键
     */
    public static void simulatingKeyPress(String key) {
        Robot robot = keyDown(key);
        keyUp(robot, key);
//        RandomGenerator.randomWait();
    }


    /**
     * 模拟按下组合键并释放
     *
     * @param keys 组合键字符串（例如："ctrl shift esc",可以包括普通字符）
     */
    public static void simulateCombinationKeyPress(String keys) {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        // 将组合键字符串按照空格分隔成数组
        String[] keyArray = keys.split("\\s+");

        // 循环按下按键
        for (String key : keyArray) {
            if ("ctrl".equalsIgnoreCase(key)) {
                robot.keyPress(KeyEvent.VK_CONTROL);
            } else if ("alt".equalsIgnoreCase(key)) {
                robot.keyPress(KeyEvent.VK_ALT);
            } else if ("shift".equalsIgnoreCase(key)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            } else if ("windows".equalsIgnoreCase(key)) {
                robot.keyPress(KeyEvent.VK_WINDOWS);
            } else if ("esc".equalsIgnoreCase(key)) {
                robot.keyPress(KeyEvent.VK_ESCAPE);
            } else if ("f4".equalsIgnoreCase(key)) {
                robot.keyPress(KeyEvent.VK_F4);
            }
            else {
                // 如果是普通字符，转换成 int 类型的 key code
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
                robot.keyPress(keyCode);
            }
            robot.delay(RandomGenerator.getRandomInt(minPressTime, maxPressTime));
        }


        // 反向循环释放按键
        for (int i = keyArray.length - 1; i >= 0; i--) {
            String key = keyArray[i];
            if ("ctrl".equalsIgnoreCase(key)) {
                robot.keyRelease(KeyEvent.VK_CONTROL);
            } else if ("alt".equalsIgnoreCase(key)) {
                robot.keyRelease(KeyEvent.VK_ALT);
            } else if ("shift".equalsIgnoreCase(key)) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if ("windows".equalsIgnoreCase(key)) {
                robot.keyRelease(KeyEvent.VK_WINDOWS);
            } else if ("esc".equalsIgnoreCase(key)) {
                robot.keyRelease(KeyEvent.VK_ESCAPE);
            } else if ("f4".equalsIgnoreCase(key)) {
                robot.keyRelease(KeyEvent.VK_F4);
            } else {
                // 如果是普通字符，转换成 int 类型的 key code
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
                robot.keyRelease(keyCode);
            }
            robot.delay(RandomGenerator.getRandomInt(minReleaseTime, maxReleaseTime));
        }
        robot.waitForIdle();
    }

}
