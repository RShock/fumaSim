import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.charas.Element;
import xiaor.GameBoard;
import xiaor.charas.复生公主_千鹤;
import xiaor.charas.木桩;
import xiaor.charas.胆小纸袋狼_沃沃;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 千鹤的测试 {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void setUp() {
    }

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建千鹤测试() {
        复生公主_千鹤 wowo = new 复生公主_千鹤();
        GameBoard.getInstance().addOurChara(wowo);
    }

    @Test
    void 千鹤尝试攻击() {
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力100");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 千鹤先大招再攻击() {
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力100 星3 绊1 潜5");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(19, dummy.getLife());
        //第一回合开大打330*1.12 第二回合不开大打112
    }

    @Test
    void 千鹤连普通拳() {
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力100 星3 绊1 潜5");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1 1a1 1d1 1d1 1a1 1a1");
        assertEquals(100, dummy.getLife());
    }

    @Test
    void 千鹤队长的大招() {
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力100 队长 星3 绊1 潜6");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1");
        assertEquals(-983, dummy.getLife());
    }

    @Test
    void 千鹤沃沃联手大战精灵王测试() {
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力361418 队长 星3 绊1 潜6");
        胆小纸袋狼_沃沃 沃沃 = 胆小纸袋狼_沃沃.init("攻击力204650 星3 绊2 潜5");
        木桩 精灵王 = 木桩.init("生命11821290");
        board.addOurChara(千鹤);
        board.addOurChara(沃沃);
        board.addEnemyChara(精灵王);
        board.initSkills();
        board.run("1d1 2a1 1d1 2a1 1q1 2q1 1a1 2a1 1d1 2a1 1q1");
        assertEquals(798558, 精灵王.getLife(), 100);
    }

    @Test
    void 沃沃千鹤联手大战精灵王测试() {
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力361418 星3 绊1 潜6");
        胆小纸袋狼_沃沃 沃沃 = 胆小纸袋狼_沃沃.init("攻击力204650 队长 星3 绊2 潜5");
        木桩 精灵王 = 木桩.init("生命11821290");
        board.addOurChara(沃沃);
        board.addOurChara(千鹤);
        board.addEnemyChara(精灵王);
        board.initSkills();
        board.run("1a1 2d1 1a1 2d1 1q1 2q1 1a1 2a1 1a1 2d1 1a1 2q1 1a1 2a1");
        assertEquals(1494377, 精灵王.getLife(), 100);
    }
}