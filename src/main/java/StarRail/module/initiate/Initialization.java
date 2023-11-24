//package StarRail.module.initiate;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Properties;
//
//import static module.profiles.StartRailProfiles.getStartRailProperties;
//import static module.profiles.StartRailProfiles.updateStartRailProfiles;
//
///**
// * 初始化
// *
// * @author : 真寻
// * 2023/10/3
// */
//public class Initialization {
//    /**
//     * 初始化变量
//     */
//    public static void initialization() {
//        // 结算时间
//        int deadline = 4;
//        // 当前时间
//        LocalDateTime nowTime = LocalDateTime.now();
//        // 当前小时
//        int nowHour = nowTime.getHour();
//        // 昨天凌晨4点
//        LocalDateTime yesterday4am = LocalDateTime.of(nowTime.minusDays(1).toLocalDate(), LocalTime.of(4, 0));
//        // 今天凌晨4点
//        LocalDateTime today4am = LocalDateTime.of(nowTime.toLocalDate(), LocalTime.of(4, 0));
//        // 明天凌晨0点
//        LocalDateTime tomorrow0am = LocalDateTime.of(nowTime.plusDays(1).toLocalDate(), LocalTime.of(0, 0));
//
//        // 读取配置
//        Properties properties = getStartRailProperties();
//        /*
//         * 历战余响
//         */
//        // 获取当前是周几
//        int week = nowTime.getDayOfWeek().getValue();
//        // 如果是周一，重置历战余响标识
//        if (week == 1) {
//            // 历战余响完成时间
//            String weeklyChallengeCompletionTime = properties.getProperty("weeklyChallengeCompletionTime");
//            // 格式化输入的时间
//            LocalDateTime weeklyTime = LocalDateTime.parse(weeklyChallengeCompletionTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            // 判断输入时间是否在昨天凌晨4点之后, 今天凌晨4点之前
//            boolean today1 = weeklyTime.isAfter(yesterday4am) && weeklyTime.isBefore(today4am) && nowHour < deadline;
//            // 判断输入时间是否在今天凌晨4点之后, 明天凌晨0点之前
//            boolean today2 = weeklyTime.isAfter(today4am) && weeklyTime.isBefore(tomorrow0am);
//            if (!(today1 || today2)) {
//                updateStartRailProfiles("appointedDaySign", "false");
//            }
//        }
//    }
//}
