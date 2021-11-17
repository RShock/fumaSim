
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.charas.Element;
import xiaor.GameBoard;
import xiaor.charas.木桩;
import xiaor.charas.胆小纸袋狼_沃沃;

import static org.junit.jupiter.api.Assertions.*;

class 沃沃的测试 {

    GameBoard board = GameBoard.getInstance();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建沃沃测试() {
        胆小纸袋狼_沃沃 wowo = new 胆小纸袋狼_沃沃();
        GameBoard.getInstance().addOurChara(wowo);
    }

    @Test
    void 沃沃尝试攻击() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.init("攻击力100");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(wowo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 沃沃先大招再攻击() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.init("攻击力100 羁绊1 星3 潜力5");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(wowo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-70, dummy.getLife());
        //第一回合开大打200 第二回合不开大打196
    }

    @Test
    void 沃沃队长大招再攻击() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.init("攻击力100 队长 羁绊1 星3 潜力5");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(wowo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-184, dummy.getLife());
    }

    @Test
    void 沃沃队长技普攻击测试() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.init("攻击力100 队长 羁绊1 星3 潜力5");
        木桩 dummy = 木桩.init("生命500");

        board.addOurChara(wowo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(340, dummy.getLife());
    }

    @Test
    void 沃沃大战矮子王测试() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.init("攻击力48991 队长 羁绊2 星3 潜力5");
        木桩 矮子王 = 木桩.init("生命2341894 水属性");
        board.addOurChara(wowo);
        board.addEnemyChara(矮子王);
        board.initSkills();
        board.run("1a1 1a1 1q1 1a1 1a1");
        assertEquals(1183459, 矮子王.getLife(), 100);
    }

    @Test
    void 五星沃沃的大招再攻击() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.init("攻击力100 羁绊1 星5 潜力5");
        木桩 dummy = 木桩.init("生命500");

        board.addOurChara(wowo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-145, dummy.getLife());
    }

    @Test
    void 沃沃buff的消退() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.init("攻击力100 羁绊1 星3 潜力5");
        木桩 dummy = 木桩.init("生命50000");

        board.addOurChara(wowo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1 1a1 1a1 1a1 1a1 1a1 1a1 1a1 1a1 1a1 1a1");
        assertEquals(47350, dummy.getLife());
    }
}