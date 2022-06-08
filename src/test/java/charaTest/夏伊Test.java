package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;
import xiaor.charas.超级机器人木桩;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 夏伊Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void setUp() {
    }

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建夏日伊布测试() {
        Chara 夏伊 = ImportedChara.initChara("夏日_伊布利斯 攻击力2990 星3 绊1");
        board.addOurChara(夏伊);
        超级机器人木桩 木桩1 = 超级机器人木桩.init("");
        board.addEnemyChara(木桩1);
        board.initSkills();
        board.run("1a1 1a1 1a1 1a1 1q1 1a1 1a1 1a1 1q1 1a1 1a1 1a1 1q1");
//        assertEquals(0, 木桩1.getLife(), 1);
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
//        assertEquals(2146821108, dummy.getLife());
    }
}