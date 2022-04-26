import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;


public class 中文Test {
    @Test
    public void should_print_hello_world() {
        System.out.println("Default charset : " + Charset.defaultCharset());
        System.out.println("file.encoding   : " + System.getProperty("file.encoding"));
        System.out.println("native.encoding : " + System.getProperty("native.encoding"));
        System.out.println("你好");
    }

    @Test
    void name() {
        double ceil = Math.ceil(Math.ceil(279015 * 1.3) * 2 * 1.25);
        System.out.println(ceil);
    }
}
