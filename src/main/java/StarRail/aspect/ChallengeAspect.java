//package StarRail.aspect;
//
//import StarRail.general.Coordinates;
//import StarRail.general.Record;
//import StarRail.general.StarRailProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import static StarRail.util.MatchingUi.endChallengeUi;
//import static StarRail.util.MouseUtil.detectAndClick;
//import static StarRail.module.guide.UiOperations.endChallenge;
//import static StarRail.module.guide.UiOperations.startChallenge;
//
///**
// *
// *
// * @author : 真寻
// * 2023/11/15
// */
//@Slf4j
//@Aspect
//@Component
//public class ChallengeAspect {
//    @Autowired
//    private Coordinates.ChallengeCoordinates coordinates;
//    @Autowired
//    private Record record;
//    @Autowired
//    private StarRailProperties properties;
//
//    //@Pointcut("execution(* StarRail..*(..))")
//    @Pointcut("execution(* StarRail.module.guide.challenge.relic.Relic.tunnelOfErosion(..))")
//    private void pointcut() {
//    }
//
//    /**
//     * 挑战过程(开始挑战->再来一次?挑战结束) -> 日志记录
//     *
//     * @param joinPoint
//     * @return
//     * @throws Throwable
//     */
//    @Around("pointcut()")
//    public Object challenge (ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        String str = (String)joinPoint.proceed();
//        startChallengeAdvice();
//        long end = System.currentTimeMillis();
//        endChallengeAdvice(str,end-start);
//
//        return null;
//    }
//
//
//
//    /**
//     * 挑战过程(开始挑战->再来一次?挑战结束)
//     */
//    private void startChallengeAdvice(){
//        // 是否使用支援角色
//        boolean usingSupportRoles = properties.getUsingSupportRoles();
//        // 再来一次
//        boolean tryAgain = record.isTryAgain();
//        // 体力值
//        int physicalPowerValue = record.getPhysicalPowerValue();
//        // 当前挑战单次体力消耗
//        int challengeCost = record.getChallengeCost();
//
//        // 开启挑战
//        startChallenge(usingSupportRoles);
//
//        // 是否再来一次
//        if (tryAgain) {
//            // 根据体力计算再来一次的次数
//            int number1 = physicalPowerValue / (challengeCost * 6);
//            for (int i = number1; i > 1; i--) {
//                // 等待挑战完成
//                if (endChallengeUi()) {
//                    physicalPowerValue -= 60;
//                    detectAndClick(coordinates.getChallengeArr1(), "再来一次");
//                }
//            }
//            physicalPowerValue -= challengeCost * 6;
//        } else {
//            physicalPowerValue -= challengeCost * record.getChallengeCount();
//        }
//
//        // 更新体力值
//        record.setPhysicalPowerValue(physicalPowerValue);
//    }
//
//    /**
//     * 挑战结束
//     */
//    private void endChallengeAdvice(String log,long time){
//        endChallenge(log,time);
//    }
//}
