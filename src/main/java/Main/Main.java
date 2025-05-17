package Main;

import somepackage.SomeBean;
import injector.Injector;

/**
 * Главный класс приложения для демонстрации внедрения зависимостей.
 */
public class Main {
    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        String propertiesFile = "depsA.properties";
        SomeBean sb = new Injector(propertiesFile).inject(new SomeBean());
        sb.foo();
    }
}
