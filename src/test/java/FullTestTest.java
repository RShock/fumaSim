import org.junit.jupiter.api.Test;
import xiaor.charas.Chara;
import xiaor.charas.CharaName;
import xiaor.charas.ImportedChara;
import xiaor.excel.ExcelWriter;
import xiaor.tools.record.ExcelDamageRecord;
import xiaor.tools.tester.FullTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class FullTestTest {
    @Test
    public void 风队测试() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList(CharaName.幼精, CharaName.露露, CharaName.精灵王, CharaName.千鹤, CharaName.沃沃),
                """
                            2A 1A 3A 4D 5A
                            2A 1A 3A 4D 5A
                            2A 4Q 1A 3A 5Q
                            2A 1A 3A 4D 5A
                            3Q 2Q 1Q 4A 5A
                            2A 4Q 3A 1A 5A
                            2A 4D 3A 1A 5A
                            2A 3A 1A 4A 5A
                            3Q 2Q 1Q 4Q 5A
                            2A 4D 3A 1A 5A
                            2A 3A 1A 4A 5Q
                            2A 3A 1A 4Q 5A
                            3Q 2Q 1Q 4D 5A
                            2A 3A 1A 4A 5A
                            2A 3A 1A 4Q 5A
                            2A 3A 1A 4D 5A
                            3Q 2Q 1Q 4A 5A
                            2A 3A 1A 4Q 5A
                            2A 3A 1A 4D 5Q
                            2A 3A 1A 4A 5A
                            3Q 2Q 1Q 4Q 5A
                            2A 3A 1A 4D 5A
                            2A 3A 1A 4A 5A
                            2A 3A 1A 4Q 5A
                            3Q 2Q 1Q 4D 5A
                        """);
    }

}