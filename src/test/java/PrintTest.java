import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintTest {
    @Test
    void should_print() {
        System.out.println("Default charset : " + Charset.defaultCharset());
        System.out.println("file.encoding   : " + System.getProperty("file.encoding"));
        System.out.println("native.encoding : " + System.getProperty("native.encoding"));
        System.out.println("你好");
    }
}