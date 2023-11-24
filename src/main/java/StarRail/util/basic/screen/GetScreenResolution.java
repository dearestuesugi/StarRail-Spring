package StarRail.util.basic.screen;

import java.awt.*;

/**
 * 获取屏幕分辨率
 *
 * @author : 真寻
 * 2023/7/26
 */

public class GetScreenResolution {
    /**
     * 获取当前屏幕分辨率与物理分辨率的缩放比例
     *
     * @return 缩放比例
     */
    protected static double getDeviceScaleFactor() {
        // 获取当前系统中所有的屏幕设备
        GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

        // 获取默认的屏幕配置
        GraphicsConfiguration config = gd[0].getDefaultConfiguration();

        // 返回默认的变换对象的 X 缩放比例，即当前屏幕分辨率与物理分辨率的缩放比例
        return config.getDefaultTransform().getScaleX();
    }

    /**
     * 获取屏幕的最大分辨率。
     *
     * @return 屏幕的最大分辨率
     */
    protected static Dimension getMaximumScreenResolution() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = graphicsDevice.getDisplayMode().getWidth();
        int height = graphicsDevice.getDisplayMode().getHeight();
        return new Dimension(width, height);
    }

    /**
     * 获取当前屏幕分辨率。
     *
     * @return 当前屏幕分辨率
     */
    public static Dimension getCurrentScreenResolution() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}

