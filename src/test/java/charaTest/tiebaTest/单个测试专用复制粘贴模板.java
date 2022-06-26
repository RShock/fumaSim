package charaTest.tiebaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.mutual.tester.OneTest;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xiaor.core.charas.CharaName.凯萨;

public class 单个测试专用复制粘贴模板 {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    //三个人4星1绊表现如何呢？
    @Test
    @Disabled
    void 修复难度测试() throws IOException {
        OneTest oneTest = new OneTest(Arrays.asList(凯萨), """
                        1a
                """);
        board.getOurChara().get(0).setBaseAttack(100);
        oneTest.test();
    }

}
