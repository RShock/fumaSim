package charaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;
import xiaor.charas.超级机器人木桩;

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
}
