import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 小精灵王Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建小精灵王测试() {
        机灵古怪_赛露西亚 小精灵王 = new 机灵古怪_赛露西亚();
        GameBoard.getInstance().addOurChara(小精灵王);
    }

    @Test
    void 小精灵王尝试攻击() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力100 潜力5");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 小精灵王先大招再攻击() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力100 羁绊1 星3 潜力5");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-450, dummy.getLife());
    }

    @Test
    void 三绊小精灵王先大招再攻击() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力100 羁绊3 星3 潜力5");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-678, dummy.getLife());
    }

    @Test
    void 五绊小精灵王先大招再攻击() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力100 羁绊5 星3 潜力5");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-917, dummy.getLife());
    }

    @Test
    void 死斗伊吹朱点() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力560316 羁绊2 星4 潜力6 队长");
        完美靶子伊吹 伊吹 = 完美靶子伊吹.init("");
        board.addOurChara(小精灵王);
        board.addEnemyChara(伊吹);
        board.initSkills();
        board.run("""
                1a1
                """);
        assertEquals(203959968, 伊吹.getLife(), 10);
    }

    @Test
    void 站桩48回合() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力10000 羁绊5 星5 潜力12");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
            1a1 1a1 1a1 1a1 1q1
            1a1 1a1 1a1 1q1 
            1a1 1a1 1a1 1q1
        """);
        int dummyLife1 = dummy.getLife();

        board.resetBoard();
        小精灵王 = 机灵古怪_赛露西亚.init("攻击力10000 羁绊5 星5 潜力12");
        dummy = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
            1a1 1a1 1a1 1a1 1q1
            1a1 1a1 1a1 1q1
        """);
        int dummyLife2 = dummy.getLife();
        Assertions.assertEquals(dummyLife1-dummyLife2, 559820*4);
    }


}