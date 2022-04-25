package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.charas.*;
import xiaor.GameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 千鹤Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建千鹤测试() {
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤");
        GameBoard.getInstance().addOurChara(千鹤);
    }

    @Test
    void 千鹤尝试攻击() {
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力100");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 千鹤先大招再攻击() {
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力100 星3 绊1 潜5");

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
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力100 星3 绊1 潜5");
        木桩 dummy = 木桩.init("生命500");
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1 1a1 1d1 1d1 1a1 1a1");
        assertEquals(100, dummy.getLife());
    }

    @Test
    void 千鹤队长的大招() {
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力100 队长 星3 绊1 潜6");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1");
        assertEquals(-983, dummy.getLife());
    }

    @Test
    void 千鹤沃沃联手大战精灵王测试() {
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力361418 队长 星3 绊1 潜6");
        Chara 沃沃 = ImportedChara.initChara("胆小纸袋狼 沃沃 攻击力204650 星3 绊2 潜5");
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
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力361418 星3 绊1 潜6");
        Chara 沃沃 = ImportedChara.initChara("胆小纸袋狼 沃沃 队长 攻击力204650 星3 绊2 潜5");
        木桩 精灵王 = 木桩.init("生命11821290");
        board.addOurChara(沃沃);
        board.addOurChara(千鹤);
        board.addEnemyChara(精灵王);
        board.initSkills();
        board.run("1a1 2d1 1a1 2d1 1q1 2q1 1a1 2a1 1a1 2d1 1a1 2q1 1a1 2a1");
        assertEquals(1494377, 精灵王.getLife(), 100);
    }
}