package pl.sda.hibernate.services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "screen.implementation", havingValue = "system-out", matchIfMissing = true)
public class SystemOutScreen implements Screen {
    @Override
    public void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    @Override
    public void println(String text) {
        System.out.println(text);
    }
}
