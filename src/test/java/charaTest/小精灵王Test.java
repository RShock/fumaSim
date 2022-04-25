package charaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.*;
import xiaor.excel.ExcelCharaProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 小精灵王Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建小精灵王测试() {
        Chara 小精灵王 = ExcelCharaProvider.getCharaByName("机灵古怪 塞露西亚");
        GameBoard.getInstance().addOurChara(小精灵王);
    }

    @Test
    void 小精灵王尝试攻击() {
        Chara 小精灵王 = ImportedChara.initChara("机灵古怪 塞露西亚 攻击力100 潜力5");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 小精灵王先大招再攻击() {
        Chara 小精灵王 = ImportedChara.initChara("机灵古怪 塞露西亚 攻击力100 羁绊1 星3 潜力5");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-450, dummy.getLife());
    }

    @Test
    void 三绊小精灵王先大招再攻击() {
        Chara 小精灵王 = ImportedChara.initChara("机灵古怪 塞露西亚 攻击力100 羁绊3 星3 潜力5");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-678, dummy.getLife());
    }

    @Test
    void 五绊小精灵王先大招再攻击() {
        Chara 小精灵王 = ImportedChara.initChara("机灵古怪 塞露西亚 攻击力100 羁绊5 星3 潜力5");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-917, dummy.getLife());
    }
}