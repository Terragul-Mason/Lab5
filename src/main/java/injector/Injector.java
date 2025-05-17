package injector;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Класс Injector реализует механизм внедрения зависимостей на основе аннотации {@link AutoInjectable}.
 * Зависимости указываются в файлах properties, находящихся в resources.
 */
public class Injector {
    private final Properties properties;

    /**
     * Загружает зависимости из указанного properties-файла.
     *
     * @param propertiesFileName имя файла в ресурсах (например, "depsA.properties")
     * @throws RuntimeException если файл не найден или возникла ошибка загрузки
     */
    public Injector(String propertiesFileName) {
        properties = new Properties();
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (inputStream == null) {
                throw new RuntimeException("Файл " + propertiesFileName + " не найден в resources!");
            }
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке " + propertiesFileName, e);
        }
    }

    /**
     * Внедряет зависимости в поля объекта, аннотированные {@link AutoInjectable}.
     *
     * @param obj объект, в который нужно внедрить зависимости
     * @return объект с внедренными зависимостями
     * @throws RuntimeException при ошибке создания зависимостей
     */
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
