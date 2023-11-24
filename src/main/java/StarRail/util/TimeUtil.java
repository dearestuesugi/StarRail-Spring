package StarRail.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间处理集
 *
 * @author : 真寻
 * 2023/7/26
 */
public class TimeUtil {
    /**
     * 获取当前时间与记录的时间的差
     *
     * @param dateTime    记录的时间
     * @param timeSection 【可选】返回的时间区间 H:小时【默认】， S:秒
     * @return 时间差
     */
    public static long getDuration(String dateTime, String... timeSection) {
        // 区间
        String h = "H";
        String s = "S";

        // 默认返回 小时
        String section = h;
        for (String st : timeSection) {
            section = st;
        }

        // 获取当前时间
        LocalDateTime nowDateTime = LocalDateTime.now();

        // 格式化输入的时间
        LocalDateTime dateTime1 = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 返回秒
        if (s.equals(section)) {
            return getDuration(dateTime1, nowDateTime).toSeconds();
        }

        // 返回小时
        return getDuration(dateTime1, nowDateTime).toHours();
    }

    /**
     * 获取当前时间并格式化输出
     *
     * @return 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String dateTimeFormat() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    /**
     * 获取时间差
     *
     * @param dateTime1 开始时间
     * @param dateTime2 结束时间
     * @return 时间差
     */
    private static Duration getDuration(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return Duration.between(dateTime1, dateTime2);
    }
}
