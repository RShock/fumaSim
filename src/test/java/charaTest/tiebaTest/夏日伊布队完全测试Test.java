package charaTest.tiebaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.木桩;
import xiaor.tools.Tools;
import xiaor.tools.tester.FullTest;

import java.io.IOException;
import java.util.Arrays;

import static xiaor.charas.CharaName.*;

public class 夏日伊布队完全测试Test {
    @Disabled
    @Test
    public void  夏日伊布队测试() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList(夏伊, 巴尔, 春忍, 夏狐, 剑圣),
                """
                          2A 3A 4A 5A 1A
                          2A 3A 4A 5A 1A
                          2Q 3A 4A 5A 1A
                          2A 5Q 3A 4A 1A
                          1Q 4Q 2Q 3Q 5A
                          
                          1A 2A 3A 4A 5A
                          5Q 1A 2Q 3A 4A
                          1A 2A 3A 4A 5A
                          1Q 4Q 2Q 3Q 5A
                          1A 2A 3A 4A 5Q
                          
                          1A 2Q 3A 4A 5A
                          1A 2A 3A 4A 5A
                          1Q 4Q 2Q 3Q 5Q
                          1A 2A 3A 4A 5A
                          1A 2Q 3A 4A 5A
                          
                          1A 2A 3A 4A 5Q
                          1Q 4Q 2Q 3Q 5A
                          1A 2A 3A 4A 5A
                          1A 2Q 3A 4A 5Q
                          1A 2A 3A 4A 5A
                        
                          1Q 4Q 2Q 3A 5A
                        """);
        fullTest.fullTest();
    }

    @Test
    void 正式测试伊布log() {
        GameBoard board = GameBoard.getInstance();
        board.resetBoard();
        Tools.initMaxChara(Arrays.asList(夏伊, 梦露, 春忍, 夏狐, 剑圣));
        木桩 木桩1 = 木桩.init("生命0");
        board.addEnemyChara(木桩1);
        board.initSkills();
        board.run("""
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5q 1q
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5q 1q
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5a 1a
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5a 1a
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5q 1q
                """);
        Assertions.assertEquals(-4106797728L, 木桩1.getLife());

    }
}
