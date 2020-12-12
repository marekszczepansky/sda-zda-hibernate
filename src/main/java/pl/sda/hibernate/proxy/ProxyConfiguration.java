package pl.sda.hibernate.proxy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sda.hibernate.services.Screen;

import java.lang.reflect.Proxy;

@Configuration
public class ProxyConfiguration {

    @Bean
    @ConditionalOnProperty(value = "screen.implementation", havingValue = "proxy")
    Screen createScreenProxy(){
        final Object baseObject = new Object();
        return (Screen) Proxy.newProxyInstance(
                Screen.class.getClassLoader(),
                new Class<?>[]{Screen.class},
                (proxy, method, args) -> {
                    if ("println".equals(method.getName())) {
                        System.out.println("(Proxy) " + args[0]);
                        return null;

                    } else if ("printf".equals(method.getName())) {
                        System.out.printf("(Proxy) " + args[0], (Object[]) args[1]);
                        return null;
                    } else {
                        return method.invoke(baseObject, args);
                    }
                }
        );
    }
}
