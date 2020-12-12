package pl.sda.hibernate.services;

import org.springframework.stereotype.Service;

//@Service
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
