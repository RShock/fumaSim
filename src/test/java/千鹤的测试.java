import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.Element;
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
        复生公主_千鹤 千鹤 = 复生公主_千鹤.builder().name("千鹤").attack(100).build();
        木桩 dummy = 木桩.builder().life(500).name("活动假人").build();
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 千鹤先大招再攻击() {
        复生公主_千鹤 千鹤 = 复生公主_千鹤.builder().name("千鹤").attack(100).element(Element.风属性).skillLevel(1).build();
        木桩 dummy = 木桩.builder().life(500).name("活动假人").build();
        board.addOurChara(千鹤);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1d1 1a1");
        assertEquals(-70, dummy.getLife());
        //第一回合开大打330 第二回合不开大打112
    }
//
//    @Test
//    void 沃沃队长大招再攻击() {
//        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().isLeader(true).attack(100).name("沃沃1号").build();
//        木桩 dummy = 木桩.builder().life(500).name("活动假人").build();
//        board.addOurChara(wowo);
//        board.addEnemyChara(dummy);
//        board.initSkills();
//        board.run("1d1 1a1");
//        assertEquals(-184, dummy.getLife());
//    }
//
//    @Test
//    void 沃沃队长技普攻击测试() {
//        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().isLeader(true).attack(100).name("沃沃").build();
//        木桩 deadman = 木桩.builder().life(500).name("木桩").build();
//        board.addOurChara(wowo);
//        board.addEnemyChara(deadman);
//        board.initSkills();
//        board.run("1a1");
//        assertEquals(340, deadman.getLife());
//    }
//
//    @Test
//    void 沃沃大战矮子王测试() {
//        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().isLeader(true).attack(48991).name("沃沃").skillLevel(2).element(Element.风属性).build();
//        木桩 矮子王 = 木桩.builder().life(2341894).name("矮子王").element(Element.水属性).build();
//        board.addOurChara(wowo);
//        board.addEnemyChara(矮子王);
//        board.initSkills();
//        board.run("1a1 1a1 1q1 1a1 1a1");
//        assertEquals(1183459, 矮子王.getLife(), 100);
//    }
//
//    @Test
//    void 五星沃沃的大招再攻击() {
//        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().star(5).attack(100).name("沃沃").build();
//        木桩 dummy = 木桩.builder().life(500).name("活动假人").build();
//        board.addOurChara(wowo);
//        board.addEnemyChara(dummy);
//        board.initSkills();
//        board.run("1d1 1a1");
//        assertEquals(-145, dummy.getLife());
//    }
}