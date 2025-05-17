import injector.Injector;
import somepackage.SomeBean;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit-тесты для класса {@link Injector}.
 */
public class InjectorTest {

    /**
     * Тестирует внедрение зависимостей с использованием реализации SomeImpl.
     * Ожидаемый вывод: "A" и "C".
     */
    @Test
    public void testInjectionWithSomeImpl() {
        SomeBean sb = new Injector("depsA.properties").inject(new SomeBean());
        System.out.println("Вывод для SomeImpl:");
        assertDoesNotThrow(sb::foo);
    }

    /**
     * Тестирует внедрение зависимостей с использованием реализации OtherImpl.
     * Ожидаемый вывод: "B" и "C".
     */
    @Test
    public void testInjectionWithOtherImpl() {
        SomeBean sb = new Injector("depsB.properties").inject(new SomeBean());
        System.out.println("Вывод для OtherImpl:");
        assertDoesNotThrow(sb::foo);
    }
}
