import org.junit.jupiter.api.Disabled;
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

import static xiaor.charas.CharaName.*;

public class FullTestTest {
    @Disabled
    @Test
    public void 风队测试() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList(幼精, 露露, CharaName.精灵王, 千鹤, 沃沃),
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
                            3Q 2Q 1Q 4A 5A
                        """);
        String luluNot5flower = """
                            2A 1A 3A 4D 5A
                            2A 1A 3A 4D 5A
                            2A 4Q 1A 3A 5Q
                            2A 1A 3A 4D 5A
                            3Q 2A 1Q 4A 5A
                            2A 4Q 3A 1A 5A
                            2A 4D 3A 1A 5A
                            2A 3A 1A 4A 5A
                            3Q 2Q 1Q 4Q 5A
                            2A 4D 3A 1A 5A
                            2A 3A 1A 4A 5Q
                            2A 3A 1A 4Q 5A
                            3Q 2A 1Q 4D 5A
                            2A 3A 1A 4A 5A
                            2A 3A 1A 4Q 5A
                            2A 3A 1A 4D 5A
                            3Q 2Q 1Q 4A 5A
                            2A 3A 1A 4Q 5A
                            2A 3A 1A 4D 5Q
                            2A 3A 1A 4A 5A
                            3Q 2A 1Q 4Q 5A
                            2A 3A 1A 4D 5A
                            2A 3A 1A 4A 5A
                            2A 3A 1A 4Q 5A
                            3Q 2Q 1Q 4A 5A
                """;
        fullTest.setAction(2, 4, luluNot5flower);
        fullTest.setAction(6, 4, luluNot5flower);
        fullTest.fullTest();
    }

    @Test
    @Disabled
    public void 梦露队长风队测试() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList(
                梦露, 露露, 幼精, 千鹤, 沃沃),
                """
                          2A 1Q 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5Q
                          2A 1A 3A 4Q 5A
                          2Q 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4Q 5A
                          2Q 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5Q
                          2A 1A 3A 4A 5A
                          2Q 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2Q 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5Q
                          2A 1A 3A 4A 5A
                          2Q 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2Q 3Q 1Q 4A 5A
                        """);
        String mengluNot3flower = """
                                  2A 1Q 3A 4D 5A
                                  2A 1A 3A 4D 5A
                                  2A 1A 3A 4Q 5Q
                                  2A 1A 3A 4D 5A
                                  2Q 3Q 1Q 4A 5A
                                  2A 1A 3A 4Q 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2Q 3Q 1Q 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5Q
                                  2A 1A 3A 4Q 5A
                                  2A 1Q 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3Q 4Q 5A
                                  2Q 1Q 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5Q
                                  2A 1A 3Q 4Q 5A
                                  2Q 1Q 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3Q 4Q 5A
                                  2Q 1Q 3A 4A 5A
                                """;
        String luluNot5flower = """
                          2A 1Q 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5Q
                          2A 1A 3A 4Q 5A
                          2A 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4Q 5A
                          2Q 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5Q
                          2A 1A 3A 4A 5A
                          2A 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2Q 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5Q
                          2A 1A 3A 4A 5A
                          2A 3Q 1Q 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2A 1A 3A 4A 5A
                          2Q 3Q 1Q 4A 5A
                                """;
        String bothNot3flower = """
                                  2A 1Q 3A 4D 5A
                                  2A 1A 3A 4D 5A
                                  2A 1A 3A 4Q 5Q
                                  2A 1A 3A 4D 5A
                                  2A 3Q 1Q 4A 5A
                                  2A 1A 3A 4Q 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2Q 3Q 1Q 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5Q
                                  2A 1A 3A 4Q 5A
                                  2A 1Q 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3Q 4Q 5A
                                  2Q 1Q 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5Q
                                  2A 1A 3Q 4Q 5A
                                  2A 1Q 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3A 4A 5A
                                  2A 1A 3Q 4Q 5A
                                  2Q 1Q 3A 4A 5A
                                """;
        fullTest.setAction(1, 3, mengluNot3flower);
        fullTest.setAction(2, 5, luluNot5flower);
        fullTest.setAction(6, 5, luluNot5flower);
        fullTest.setAction(6, 3, bothNot3flower);
        fullTest.fullTest();
    }

    @Test
    @Disabled
    public void 风队梦露换精灵王测试() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList(
                幼精, 露露, 梦露, 千鹤, 沃沃),
                """
                        2A 3Q 1A 4D 5A
                        2A 1A 3A 4D 5A
                        2A 1A 3A 4Q 5Q
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4D 5A
                        2A 1A 3A 4Q 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4A 5A
                        2A 1A 3A 4Q 5A
                        2A 1A 3A 4A 5Q
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4A 5A
                        2A 1A 3A 4Q 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4A 5A
                        2A 1A 3A 4Q 5A
                        2A 1A 3A 4A 5Q
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4A 5A
                        2A 1A 3A 4Q 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4D 5A                       
                        """);
        String mengluNot3flower =
                """
                        2A 3Q 1A 4D 5A
                        2A 1A 3A 4D 5A
                        2A 1A 3A 4Q 5Q
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4D 5A
                        2A 1A 3A 4Q 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5Q
                        2A 1A 3A 4Q 5A
                        2Q 3Q 1A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1Q 3A 4Q 5A
                        2Q 3Q 1A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5Q
                        2A 1Q 3A 4Q 5A
                        2Q 3Q 1A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1Q 3A 4Q 5A
                        2Q 3Q 1A 4D 5A                      
                        """;
        String luluNot5flower =                 """
                        2A 3Q 1A 4D 5A
                        2A 1A 3A 4D 5A
                        2A 1A 3A 4Q 5Q
                        2A 1A 3A 4A 5A
                        2A 3Q 1Q 4D 5A
                        2A 1A 3A 4Q 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5Q
                        2A 1A 3A 4A 5A
                        2A 3Q 1Q 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5Q
                        2A 1A 3A 4A 5A
                        2A 3Q 1Q 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4D 5A                       
                        """;
        String bothNot3flower =  """
                        2A 3Q 1A 4D 5A
                        2A 1A 3A 4D 5A
                        2A 1A 3A 4Q 5Q
                        2A 1A 3A 4A 5A
                        2A 3Q 1Q 4D 5A
                        2A 1A 3A 4Q 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2Q 3Q 1Q 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5Q
                        2A 1A 3A 4Q 5A
                        2A 3Q 1A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1Q 3A 4Q 5A
                        2Q 3Q 1A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5Q
                        2A 1Q 3A 4Q 5A
                        2A 3Q 1A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1Q 3A 4Q 5A
                        2Q 3Q 1A 4D 5A                      
                        """;
        fullTest.setAction(1, 3, mengluNot3flower);
        fullTest.setAction(2, 5, luluNot5flower);
        fullTest.setAction(6, 5, luluNot5flower);
        fullTest.setAction(6, 3, bothNot3flower);
        fullTest.fullTest();
    }
}
