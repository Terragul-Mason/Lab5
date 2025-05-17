package Main;

import somepackage.SomeBean;
import injector.Injector;

public class Main {
    public static void main(String[] args) {
        String propertiesFile = "depsA.properties";
        SomeBean sb = new Injector(propertiesFile).inject(new SomeBean());
        sb.foo();
    }
}
