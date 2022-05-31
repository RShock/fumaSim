import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xiaor.tools.record.ExcelDamageRecord;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class 中文Test {
    @Test
    public void should_print_hello_world() {
        System.out.println("Default charset : " + Charset.defaultCharset());
        System.out.println("file.encoding   : " + System.getProperty("file.encoding"));
        System.out.println("native.encoding : " + System.getProperty("native.encoding"));
        System.out.println("你好");
    }
}
