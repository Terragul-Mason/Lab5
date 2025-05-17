package somepackage;

import injector.AutoInjectable;

/**
 * Класс с зависимостями, которые внедряются через аннотацию {@link AutoInjectable}.
 */
public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;

    @AutoInjectable
    private SomeOtherInterface field2;

    /**
     * Вызывает внедренные зависимости.
     */
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}
