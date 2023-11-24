package StarRail.module.initiate;

import StarRail.general.Coordinates;
import StarRail.general.StarRailProperties;
import StarRail.util.MatchingUi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static StarRail.util.MatchingUi.uiJudgment;
import static StarRail.util.MouseUtil.detectAndClick;
import static StarRail.util.MouseUtil.mouseOperation;
import static StarRail.util.basic.random.RandomGenerator.randomWait;
import static StarRail.util.basic.keyboard.Keyboard.simulatingKeyPress;
import static StarRail.util.basic.mouse.Mouse.mouseLeftClick;
import static StarRail.util.basic.screen.ScreenUtil.saveErrorImage;

/**
 * 启动星轨
 *
 * @author : 真寻
 * 2023/10/3
 */
@Slf4j
@Component
public class StartStarRail {
    @Autowired
    private Coordinates.StartStarRailCoordinates coordinates;
    @Autowired
    private StarRailProperties properties;
    @Autowired
    private MatchingUi matchingUi;

    /**
     * 启动星轨启动器
     */
    public void startStarRail() {
        // 打开星轨启动器
        ProcessBuilder pb = new ProcessBuilder(properties.getStarRailPath());

        try {
            pb.start();
        } catch (IOException e) {
            log.error("请以管理员权限运行星轨启动器！");
            System.exit(-1);
        }

        // 检查星轨启动器更新
        if (detectAndClick(coordinates.getStartArr1(), "开始游戏", 5)) {
            if (detectAndClick(coordinates.getStartArr2(), "更新", 3)) {
                log.info("启动器有更新,正在更新......");
                if (detectAndClick(coordinates.getStartArr3(), "打开启动器")) {
                    detectAndClick(coordinates.getStartArr1(), "开始游戏");
                    log.info("更新完成");
                }
            }
            enterStarRail();
        } else {
            if (detectAndClick(coordinates.getStartArr2(), "更新", 3)) {
                detectAndClick(coordinates.getStartArr1(), "开始游戏", 220);
            } else {
                saveErrorImage();
                log.error("启动器UI有变");
                System.exit(-1);
            }
        }
    }

    /**
     * 进入星轨
     * 220秒内未加载完成则截图并退出
     */
    private void enterStarRail() {
        // 游戏启动界面加载时间
        int loadTime = 1000 * 600;
        // 开始时间
        long sTime = System.currentTimeMillis();
        // 结束时间
        long eTime = System.currentTimeMillis();

        // 220秒内未加载完成则截图并退出
        while (eTime - sTime < loadTime) {
            // 登录账号
            if (detectAndClick(coordinates.getStartArr4(), "账号密码", 3)) {
                // 输入账号
                mouseOperation(coordinates.getStartArr5());

                for (String s : properties.getCipher().split("")) {
                    simulatingKeyPress(s);
                }

                randomWait();

                // 输入密码
                mouseOperation(coordinates.getStartArr6());
                for (String s : properties.getCipher().split("")) {
                    simulatingKeyPress(s);
                }

                // 同意用户协议
                mouseOperation(coordinates.getStartArr7());

                // 进入游戏
                mouseOperation(coordinates.getStartArr8());
            }

            // 进入游戏
            if (detectAndClick(coordinates.getStartArr9(), "点击进入", 3)) {
                while (eTime - sTime < loadTime) {
                    if (uiJudgment(coordinates.getStartArr10(), "列车补给", 2)) {
                        randomWait(2000,5000);
                        mouseLeftClick();
                        randomWait();
                        mouseLeftClick();
                        randomWait();
                        mouseLeftClick();
                    }
                    if (matchingUi.mainUi(2)) {
                        randomWait(2200);
                        eTime = System.currentTimeMillis();
                        log.info("进入游戏, 耗时 " + (eTime - sTime) / 1000 + "秒");
                        return;
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            eTime = System.currentTimeMillis();
        }

        // 保存截图
        saveErrorImage();
        log.error(loadTime / 1000 + "秒内未进入游戏，截图已保存");
        System.exit(-1);
    }
}
