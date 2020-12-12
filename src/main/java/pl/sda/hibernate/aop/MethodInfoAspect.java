package pl.sda.hibernate.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.sda.hibernate.dao.BaseDao;
import pl.sda.hibernate.services.Screen;

@Aspect
@Component
public class MethodInfoAspect {

    private final Screen screen;

    public MethodInfoAspect(Screen screen) {
        this.screen = screen;
    }

    @Before("@annotation(logEntry)")
    public void logCalledMethodName(JoinPoint joinPoint, LogEntry logEntry) {
        final String methodName = joinPoint.getSignature().getName();
        final String className = joinPoint.getTarget().getClass().getSimpleName();
        screen.printf("[M] Method '%s' called, source class name '%s'\n", methodName, className);
    }

    @Before("target(target)")
    public void logAllCalledMethodName(JoinPoint joinPoint, @SuppressWarnings("rawtypes") BaseDao target) {
        final String methodName = joinPoint.getSignature().getName();
        final String className = joinPoint.getTarget().getClass().getSimpleName();
        screen.printf("[C] Method '%s' called, source class name '%s'\n", methodName, className);
    }
}
