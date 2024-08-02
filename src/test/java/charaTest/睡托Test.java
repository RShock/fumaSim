package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.core.charas.Chara;
import xiaor.core.charas.ImportedChara;
import xiaor.core.charas.木桩;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 睡托Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void setUp() {
    }

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建睡托测试() {
        Chara tuo = ImportedChara.initChara("不健全遐想_托特拉 攻击力100");
        GameBoard.getInstance().addOurChara(tuo);
    }

    @Test
    void 睡托尝试攻击() {
        Chara tuo = ImportedChara.initChara("不健全遐想_托特拉 攻击力100");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(tuo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 睡托先大招再攻击() {
        Chara tuo = ImportedChara.initChara("不健全遐想_托特拉 攻击力100 羁绊1 星3 潜力5");

        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(tuo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-268, dummy.getLife());
    }
//
//    @Test
//    void 睡托队长大招再攻击() {
//        Chara tuo = ImportedChara.initChara("不健全遐想_托特拉 攻击力100 队长 羁绊1 星3 潜力5");
//
//        木桩 dummy = 木桩.init("生命500");
//        board.addOurChara(tuo);
//        board.addEnemyChara(dummy);
//        board.initSkills();
//        board.run("1q1 1a1");
//        assertEquals(-184, dummy.getLife());
//    }
//
//    @Test
//    void 睡托队长技普攻击测试() {
//        Chara tuo = ImportedChara.initChara("不健全遐想_托特拉 攻击力100 队长 羁绊1 星3 潜力5");
//
//        木桩 dummy = 木桩.init("生命500");
//
//        board.addOurChara(tuo);
//        board.addEnemyChara(dummy);
//        board.initSkills();
//        board.run("1a1");
//        assertEquals(340, dummy.getLife());
//    }
//
//    @Test
//    void 睡托大战矮子王测试() {      // 本测试涉及属性克制，出错即为属性克制不生效
//        Chara tuo = ImportedChara.initChara("不健全遐想_托特拉 攻击力48991 队长 羁绊2 星3 潜力5");
//
//        木桩 矮子王 = 木桩.init("生命2341894 水属性");
//        board.addOurChara(tuo);
//        board.addEnemyChara(矮子王);
//        board.initSkills();
//        board.run("1a1 1a1 1q1 1a1 1a1");
//        assertEquals(1183459, 矮子王.getLife(), 100);
//    }
//
//    @Test
//    void 五星睡托的大招再攻击() {
//        Chara tuo = ImportedChara.initChara("不健全遐想_托特拉 攻击力100 羁绊1 星5 潜力5");
//
//        木桩 dummy = 木桩.init("生命500");
//
//        board.addOurChara(tuo);
//        board.addEnemyChara(dummy);
//        board.initSkills();
//        board.run("1q1 1a1");
//        assertEquals(-145, dummy.getLife());
//    }
//
//    @Test
//    void 睡托buff的消退() {
//        Chara tuo = ImportedChara.initChara("不健全遐想_托特拉 攻击力100 羁绊1 星3 潜力5");
//
//        木桩 dummy = 木桩.init("生命50000");
//
//        board.addOurChara(tuo);
//        board.addEnemyChara(dummy);
//        board.initSkills();
//        board.run("1q1 1a1 1a1 1a1 1a1 1a1 1a1 1a1 1a1 1a1 1a1 1a1");
//        assertEquals(47350, dummy.getLife());
//    }
}