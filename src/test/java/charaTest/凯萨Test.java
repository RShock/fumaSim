package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.core.charas.Chara;
import xiaor.core.charas.ImportedChara;
import xiaor.core.charas.木桩;
import xiaor.core.charas.超级机器人木桩;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 凯萨Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void setUp() {
    }

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建凯萨测试() {
        Chara 凯萨 = ImportedChara.initChara("异界_凯萨 攻击力100 生命值300");
        board.addOurChara(凯萨);
        木桩 木桩1 = 木桩.init("生命0");
        board.addEnemyChara(木桩1);
        board.initSkills();
        board.run("1a1 1q1");
        assertEquals(-847, 木桩1.getLife(), 1);
    }

    @Test
    void 凯萨尝试攻击() {
        Chara 凯萨 = ImportedChara.initChara("异界_凯萨 攻击力100 生命值300 星5 绊1 潜6 队长");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(凯萨);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1a1
                """);
        assertEquals(-1513, dummy.getLife());
    }

    @Test
    void 凯萨尝试大招() {
        Chara 凯萨 = ImportedChara.initChara("异界_凯萨 攻击力100 生命值300 星5 绊1 潜6");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(凯萨);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1q1
                """);
        assertEquals(-769, dummy.getLife());
    }

    @Test
    void 单体打桩() {
        Chara 凯萨 = ImportedChara.initChara("异界_凯萨 攻击力588258 生命值2941292 星5 绊2 潜6 队长");
        超级机器人木桩 dummy = 超级机器人木桩.init("");
        board.addOurChara(凯萨);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1a1 1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                1a1 1a1 1a1 1q1
                """);
        assertEquals(1811017746, dummy.getLife());
    }
}