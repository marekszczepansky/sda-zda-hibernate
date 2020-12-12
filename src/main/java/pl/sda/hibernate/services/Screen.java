package pl.sda.hibernate.services;

public interface Screen {
    void printf(String format, Object... args);

    void println(String text);
}
