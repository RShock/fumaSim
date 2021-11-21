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
    void 精灵王和沃沃千鹤暴打54层() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力505739 羁绊2 星3 潜力6 队长");
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力361418 星3 绊1 潜6");
        胆小纸袋狼_沃沃 沃沃 = 胆小纸袋狼_沃沃.init("攻击力204650 星3 绊2 潜5");
        木桩 木桩a = 木桩.init("风属性");
        木桩 木桩b = 木桩.init("风属性");
        五十四层boss_诺诺可 诺诺可 = 五十四层boss_诺诺可.init("");
        board.addOurChara(小精灵王);
        board.addOurChara(千鹤);
        board.addOurChara(沃沃);
        board.addOurChara(木桩a);
        board.addOurChara(木桩b);
        board.addEnemyChara(诺诺可);
        board.initSkills();
        board.run("""
                1a1 2d1 3a1 4d1 5d1
                1a1 2d1 3a1 4d1 5d1
                1a1 2q1 3q1 4d1 5d1
                1a1 2a1 3a1 4d1 5d1
                1q1 2d1 3a1 4d1 5d1
                1a1 2q1 3d1 4d1 5d1
                """);
        assertEquals(2871426, 诺诺可.getLife(), 10);
    }
}