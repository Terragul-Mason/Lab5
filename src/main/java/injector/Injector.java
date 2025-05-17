package injector;

import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    private final Properties properties;

    public Injector() {
        properties = new Properties();
        try (var inputStream = getClass().getClassLoader().getResourceAsStream("dependencies.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Файл dependencies.properties не найден в resources!");
            }
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке dependencies.properties", e);
        }
    }

    public <T> T inject(T obj) {
        Class<?> cls = obj.getClass();

        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                String interfaceName = field.getType().getCanonicalName();
                String implClassName = properties.getProperty(interfaceName);

                if (implClassName == null) {
                    throw new RuntimeException("Не найдена реализация для интерфейса: " + interfaceName);
                }

                try {
                    Class<?> implClass = Class.forName(implClassName);
                    Object dependency = implClass.getDeclaredConstructor().newInstance();

                    field.setAccessible(true);
                    field.set(obj, dependency);
                } catch (Exception e) {
                    throw new RuntimeException("Ошибка при внедрении зависимости в поле: " + field.getName(), e);
                }
            }
        }

        return obj;
    }
}
