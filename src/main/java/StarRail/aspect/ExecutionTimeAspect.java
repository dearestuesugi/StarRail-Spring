//package StarRail.aspect;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * 计算执行时间
// *
// * @author : 真寻
// * 2023/11/11
// */
//@Slf4j
//@Aspect
//@Component
//public class ExecutionTimeAspect {
//    @Pointcut("execution(* StarRail.module.quit.QuitStarRail.quitStarRail(..))")
////@Pointcut("execution(* StarRail..*(..))")
//    public void pointcut() {
//    }
//
//    @Around("pointcut()")
//    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object proceed = joinPoint.proceed();
//        long end = System.currentTimeMillis();
//        log.info("游戏已关闭, 耗时 " + (end - start) / 1000 + "秒");
//        System.out.println("Execution time of " + joinPoint.getSignature().getName() + " :: " + (end - start) + " ms");
//        return proceed;
//    }
//}
//
