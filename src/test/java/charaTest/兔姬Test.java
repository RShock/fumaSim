package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.core.charas.Chara;
import xiaor.core.charas.ImportedChara;
import xiaor.core.charas.木桩;
import xiaor.core.charas.超级机器人木桩;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 兔姬Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void setUp() {
    }

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建兔姬测试() {
        Chara 兔姬 = ImportedChara.initChara("恶兔魔王_兔姬 攻击力100 星5 绊5");
        board.addOurChara(兔姬);
        木桩 木桩1 = 木桩.init("生命0");
        board.addEnemyChara(木桩1);
        board.initSkills();
        board.run("1a1 1q1");
        assertEquals(-795, 木桩1.getLife(), 1);
    }

    @Test
    void 单体打桩() {
        Chara 兔姬 = ImportedChara.initChara("恶兔魔王_兔姬 攻击力1382 星3 绊1 队长");
        超级机器人木桩 dummy = 超级机器人木桩.init("");
        board.addOurChara(兔姬);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                """);
        assertEquals(2146821108, dummy.getLife());
    }
}