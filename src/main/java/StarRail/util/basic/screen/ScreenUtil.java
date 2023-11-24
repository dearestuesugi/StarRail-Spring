package StarRail.util.basic.screen;

import StarRail.util.basic.random.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.MultiResolutionImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对屏幕上的图像进行 OCR 识别、模板匹配等操作集
 *
 * @author : 真寻
 * 2023/7/26
 */
@Slf4j
public class ScreenUtil {
    /**
     * 正则规则
     */
    private static final Pattern PATTERN = Pattern.compile("\\d+");

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * 将BufferedImage对象转成Mat对象
     *
     * @param image 带转换的BufferedImage对象
     * @return Mat对象（保留原有色彩）
     */
    private static Mat convertBufferedImageToMat(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int type = image.getType();

        byte[] data = new byte[0];
        switch (type) {
            case BufferedImage.TYPE_INT_RGB:
                int[] intData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
                data = new byte[intData.length * 3];
                for (int i = 0; i < intData.length; i++) {
                    data[i * 3] = (byte) ((intData[i] & 0xFF0000) >> 16);
                    data[i * 3 + 1] = (byte) ((intData[i] & 0xFF00) >> 8);
                    data[i * 3 + 2] = (byte) (intData[i] & 0xFF);
                }
                break;
            case BufferedImage.TYPE_3BYTE_BGR:
            case BufferedImage.TYPE_CUSTOM:
                data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                break;
            default:
                log.error("无法将BufferedImage对象转成Mat对象 不支持的图片类型 {} ", type);
                System.exit(-1);
        }

        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.put(0, 0, data);

        if (type == BufferedImage.TYPE_INT_RGB) {
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
        }

        return mat;
    }

    /**
     * 模板匹配
     *
     * @return 模板匹配中心坐标
     */
    private static Point templateMatching(Mat sourceImage, Mat template) {
        Mat result = new Mat();

        // 执行模板匹配
        int matchMethod = Imgproc.TM_SQDIFF_NORMED;
        Imgproc.matchTemplate(sourceImage, template, result, matchMethod);
        Core.normalize(result, result, 0.0, 1.0, Core.NORM_MINMAX, -1, new Mat());

        // 找到匹配结果的最小值和最大值
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

        // 找到匹配结果的位置
        Point matchLoc = mmr.minLoc;

        // 匹配精度
        double threshold = 0.000000003;

        if (mmr.minVal < threshold) {
            return new Point(matchLoc.x, matchLoc.y);
        } else {
            return null;
        }
    }


    /**
     * 获取模板坐标【区域】
     *
     * @param area          识别区域
     * @param templatePath  模板路径
     * @param rangeOfOffset 【可选】传入一个整数, 用于限定偏移范围【默认 +—15】
     * @return 偏移后的模板坐标 Point or Null
     */
    public static Point getScreenCoordinate(int[] area, String templatePath, int... rangeOfOffset) {
        // 获取当前屏幕画面
        Mat mat = convertBufferedImageToMat(regionScreenImage(area));

        // 加载模板
        Mat template = Imgcodecs.imread(templatePath);

        // 获取屏幕画面与模板匹配的结果
        Point point = templateMatching(mat, template);

        // 获取坐标
        if (point != null) {
            // 获取实际坐标
            point = getScaledScreenCoordinate(point);

            // 获取中心坐标
            point = new Point((int) (point.x + template.cols() / 2), (int) (point.y + template.cols() / 2));

            // 限定偏移范围
            int min = -15;
            int max = 15;
            for (int i : rangeOfOffset) {
                min = -i;
                max = i;
            }

            // 返回处理后的坐标
            point = new Point(point.x + RandomGenerator.getRandomInt(min, max), point.y + RandomGenerator.getRandomInt(min, max));
        }
        return point;
    }

    /**
     * 获取模板坐标【全屏】
     *
     * @param templatePath  模板路径
     * @param rangeOfOffset 【可选】传入一个整数, 用于限定偏移范围【默认 +—15】
     * @return 偏移后的模板坐标 Point or Null
     */
    public static Point getScreenCoordinate(String templatePath, int... rangeOfOffset) {
        // 获取当前屏幕画面
        Mat mat = convertBufferedImageToMat(getHighResolutionScreenImage());

        // 加载模板
        Mat template = Imgcodecs.imread(templatePath);

        // 获取屏幕画面与模板匹配的结果
        Point point = templateMatching(mat, template);

        // 获取坐标
        if (point != null) {
            // 获取实际坐标
            point = getScaledScreenCoordinate(point);

            // 获取中心坐标
            point = new Point((int) (point.x + template.cols() / 2), (int) (point.y + template.cols() / 2));

            // 限定偏移范围
            int min = -15;
            int max = 15;
            for (int i : rangeOfOffset) {
                min = -i;
                max = i;
            }

            // 返回处理后的坐标
            point = new Point(point.x + RandomGenerator.getRandomInt(min, max), point.y + RandomGenerator.getRandomInt(min, max));
        }
        return point;
    }


    /**
     * 对给定的图像进行 OCR 识别，判断是否包含指定字符
     *
     * @param bufferedImage 给定的图像
     * @return boolean
     */
    public static boolean ocrRecognition(BufferedImage bufferedImage, String character) {
        boolean isMatch = false;
        try {
            ITesseract instance = new Tesseract();
            //设置 Tesseract 的数据路径
            instance.setDatapath("E:\\Develop\\Tesseract-OCR\\tessdata");
            //设置 Tesseract 的语言为简体中文
            instance.setLanguage("chi_sim");
            //执行 OCR 识别
            String result = instance.doOCR(bufferedImage);
//            System.out.println("传入字符 " + character);

            isMatch = result.contains(character);
//            System.out.println("识别结果 " + result + " " + isMatch);
        } catch (Exception e) {
            log.error("OCR 识别失败!" + e);
        }
        return isMatch;
    }

    public static String ocrRecognition(BufferedImage bufferedImage) {
        String result = "";
        try {
            ITesseract instance = new Tesseract();
            //设置 Tesseract 的数据路径
            instance.setDatapath("E:\\Develop\\Tesseract-OCR\\tessdata");
            //设置 Tesseract 的语言为简体中文
            instance.setLanguage("chi_sim");
            //执行 OCR 识别
            result = instance.doOCR(bufferedImage);
        } catch (Exception e) {
            log.error("OCR 识别失败!", e);
        }
        return result;
    }

    public static String ocrRecognition(int[] area) {
        BufferedImage bufferedImage = regionScreenImage(area);

        String result = "";
        try {
            ITesseract instance = new Tesseract();
            //设置 Tesseract 的数据路径
            instance.setDatapath("E:\\Develop\\Tesseract-OCR\\tessdata");
            //设置 Tesseract 的语言为简体中文
            instance.setLanguage("chi_sim");
            //执行 OCR 识别
            result = instance.doOCR(bufferedImage);
        } catch (Exception e) {
            log.error("OCR 识别失败!", e);
        }
        return result;
    }

    /**
     * 对指定区域进行 OCR 识别，返回数字
     *
     * @param area 区域坐标
     * @return int 识别的数字
     */
    public static int gettingNumbers(int[] area) {
        String result = ocrRecognition(regionScreenImage(area));
        System.out.println(result);
        // 使用正则表达式提取数字
        Matcher matcher = PATTERN.matcher(result);
        if (matcher.find()) {
            result = matcher.group();
        } else {
            result = "-1";
//            log.error("OCR 识别失败，未能提取数字" + result);
        }
        return Integer.parseInt(result);
    }

    /**
     * 保存错误截图
     */
    public static void saveErrorImage() {
        // 获取日期
        LocalDate dateTmp = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 文件夹名称
        String date = dateTmp.format(dateTimeFormatter);

        // 获取时间
        LocalTime timeTmp = LocalTime.now();
        dateTimeFormatter = DateTimeFormatter.ofPattern("HH-mm-ss");

        // 文件名称
        String fileName = timeTmp.format(dateTimeFormatter);

        Path path = Paths.get("data/logs/error images/" + date);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            File file = new File(path + "/" + fileName + ".png");
            ImageIO.write(getHighResolutionScreenImage(), "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定区域截图
     *
     * @param area 截图区域，即给定的截图范围：{左上角的横坐标,左上角的纵坐标,右下角的横坐标,右下角的纵坐标}
     * @return 高分辨率区域截图 BufferedImage
     */
    public static BufferedImage regionScreenImage(int[] area) {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        // 屏幕分辨率
        Dimension screen = GetScreenResolution.getCurrentScreenResolution();

        //截图区域
        screen.height = area[3] - area[1];
        screen.width = area[2] - area[0];

        Rectangle screenRect = new Rectangle(screen);
        //添加左上角的坐标
        screenRect.x = area[0];
        screenRect.y = area[1];

        /*
         * createMultiResolutionScreenCapture方法获取包含多个分辨率的屏幕截图。
         *
         * new Rectangle(x, y, width, height),表示需要截取的屏幕区域。
         */
        MultiResolutionImage multiResolutionImage = robot.createMultiResolutionScreenCapture(screenRect);
        /*
         * getResolutionVariant方法选择想要的分辨率，并将其转换为BufferedImage类型。
         */
        Image image = multiResolutionImage.getResolutionVariant(GetScreenResolution.getMaximumScreenResolution().width, GetScreenResolution.getMaximumScreenResolution().height);
        return (BufferedImage) image;
    }

    /**
     * 获取全屏截图
     *
     * @return 高分辨率全屏截图 BufferedImage
     */
    protected static BufferedImage getHighResolutionScreenImage() {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        /*
         * createMultiResolutionScreenCapture方法获取包含多个分辨率的屏幕截图。
         *
         * new Rectangle(x, y, width, height),表示需要截取的屏幕区域。
         */
        MultiResolutionImage multiResolutionImage = robot.createMultiResolutionScreenCapture(new Rectangle(0, 0, GetScreenResolution.getCurrentScreenResolution().width, GetScreenResolution.getCurrentScreenResolution().height));
        /*
         * getResolutionVariant方法选择想要的分辨率，并将其转换为BufferedImage类型。
         */
        Image image = multiResolutionImage.getResolutionVariant(GetScreenResolution.getMaximumScreenResolution().width, GetScreenResolution.getMaximumScreenResolution().height);
        return (BufferedImage) image;
    }

    /**
     * 将坐标缩放到适配当前屏幕分辨率下的坐标值(当前仅适配 1920X1080 DPI=1.25)
     *
     * @param srcPoint 需要缩放的坐标
     * @return 缩放后的坐标
     */
    private static Point getScaledScreenCoordinate(Point srcPoint) {
        // 默认缩放比例
        String defaultScaleFactor = "1.25";
        double xScaleFactor = 1;
        double yScaleFactor = 1;

        // 获取缩放比例
        double scaleFactor = GetScreenResolution.getDeviceScaleFactor();
        String strScaleFactor = String.valueOf(scaleFactor);

        // DPI缩放
        if (defaultScaleFactor.equals(strScaleFactor)) {
            xScaleFactor = 0.8;
            yScaleFactor = 0.8;
        } else {
            log.error("当前屏幕屏幕缩放率未适配!【缩放比例：】" + scaleFactor);
            System.exit(-1);
        }

        // 缩放坐标并返回
        return new Point(srcPoint.x * xScaleFactor, srcPoint.y * yScaleFactor);
    }
}

