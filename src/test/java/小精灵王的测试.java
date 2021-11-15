import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.Element;
import xiaor.GameBoard;
import xiaor.charas.复生公主_千鹤;
import xiaor.charas.木桩;
import xiaor.charas.机灵古怪_赛露西亚;
import xiaor.charas.胆小纸袋狼_沃沃;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 小精灵王的测试 {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void setUp() {
    }

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
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.builder().name("小精灵王").attack(100).build();
        木桩 dummy = 木桩.builder().life(500).name("活动假人").build();
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 小精灵王先大招再攻击() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.builder().name("小精灵王").skillLevel(1).attack(100).build();
        木桩 dummy = 木桩.builder().life(0).name("活动假人").build();
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-460, dummy.getLife());
    }

    @Test
    void 三绊小精灵王先大招再攻击() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.builder().name("小精灵王").skillLevel(3).attack(100).build();
        木桩 dummy = 木桩.builder().life(0).name("活动假人").build();
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-678, dummy.getLife());
    }

    @Test
    void 五绊小精灵王先大招再攻击() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.builder().name("小精灵王").skillLevel(5).attack(100).build();
        木桩 dummy = 木桩.builder().life(0).name("活动假人").build();
        board.addOurChara(小精灵王);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1q1 1a1");
        assertEquals(-917, dummy.getLife());
    }
}