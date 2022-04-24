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
        System.out.println(Charset.defaultCharset());
        System.out.println("你好");
    }
}