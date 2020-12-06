package pl.sda.hibernate.services;

public class Screen {
    public void printf(String format, Object ... args) {
        System.out.printf(format, args);
    }

    public void println(String text) {
        System.out.println(text);
    }
}
