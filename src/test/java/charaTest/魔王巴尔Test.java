package charaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.core.charas.Chara;
import xiaor.core.charas.Element;
import xiaor.core.charas.ImportedChara;
import xiaor.core.charas.超级机器人木桩;
import xiaor.mutual.tester.FullTest;

import java.io.IOException;
import java.util.Arrays;

import static xiaor.core.charas.CharaName.*;

public class 魔王巴尔Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建巴尔测试() {
        Chara 巴尔 = ImportedChara.initChara("魔王_巴尔");
        GameBoard.getInstance().addOurChara(巴尔);
    }

    @Test
    void 巴尔行动测试() {
        Chara 巴尔 = ImportedChara.initChara("魔王_巴尔 攻击力1243 生命值300 星3 绊5 潜5 队长");
        超级机器人木桩 dummy = 超级机器人木桩.init("生命7758206");
        board.addOurChara(巴尔);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1a 1a 1a 1a 1q
                1a 1a 1q 1a 1a 1q
                """);
        Assertions.assertEquals(7687172, dummy.getLife());
    }

    @Test
    @Disabled
    void 假如巴尔是风队成员() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList(幼精, 露露, 精灵王, 千鹤, 巴尔),
                """
                            2A 1A 3A 4D 5A
                            2A 1A 3A 4D 5A
                            2A 4Q 1A 3A 5A
                            2A 1A 3A 4D 5A
                            2Q 3Q 1Q 4A 5Q
                            2A 4Q 3A 1A 5A
                            2A 4A 3A 1A 5Q
                            2A 3A 1A 4D 5A
                            2Q 3Q 1Q 4Q 5Q
                            2A 4A 3A 1A 5A
                            2A 3A 1A 4A 5Q
                            2A 3A 1A 4A 5A
                            2Q 3Q 1Q 4Q 5Q
                            2A 3A 1A 4A 5A
                            2A 3A 1A 4A 5Q
                            2A 3A 1A 4A 5A
                            2Q 3Q 1Q 4Q 5Q
                            2A 3A 1A 4A 5A
                            2A 3A 1A 4A 5Q
                            2A 3A 1A 4A 5A
                            2Q 3Q 1Q 4Q 5Q
                            2A 3A 1A 4A 5A
                            2A 3A 1A 4A 5Q
                            2A 3A 1A 4A 5A
                            2Q 3Q 1Q 4Q 5Q
                        """);
        GameBoard.getInstance().getOurChara().get(4).setElement(Element.风属性);
        fullTest.fullTest();
    }
}
