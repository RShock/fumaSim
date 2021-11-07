
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.Element;
import xiaor.GameBoard;
import xiaor.charas.木桩;
import xiaor.charas.胆小纸袋狼_沃沃;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

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
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().name("沃沃1").attack(100).build();
        木桩 dummy = 木桩.builder().life(500).name("活动假人").build();
        board.addOurChara(wowo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, dummy.getLife());
    }

    @Test
    void 沃沃先大招再攻击() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().name("沃沃1").attack(100).build();
        胆小纸袋狼_沃沃 wowo2 = 胆小纸袋狼_沃沃.builder().name("沃沃2").life(500).build();
        board.addOurChara(wowo);
        board.addEnemyChara(wowo2);
        board.initSkills();
        board.run("1d1 1a1");
        assertEquals(-70, wowo2.getLife());
        //第一回合开大打200 第二回合不开大打196
    }

    @Test
    void 沃沃队长大招再攻击() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().isLeader(true).attack(100).name("沃沃1号").build();
        木桩 dummy = 木桩.builder().life(500).name("活动假人").build();
        board.addOurChara(wowo);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("1d1 1a1");
        assertEquals(-184, dummy.getLife());
    }

    @Test
    void 沃沃队长技普攻击测试() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().isLeader(true).attack(100).name("沃沃").build();
        木桩 deadman = 木桩.builder().life(500).name("木桩").build();
        board.addOurChara(wowo);
        board.addEnemyChara(deadman);
        board.initSkills();
        board.run("1a1");
        assertEquals(340, deadman.getLife());
    }

    @Test
    void 沃沃大战矮子王测试() {
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().isLeader(true).attack(48991).name("沃沃").skillLevel(2).element(Element.风属性).build();
        木桩 矮子王 = 木桩.builder().life(2341894).name("矮子王").element(Element.水属性).build();
        board.addOurChara(wowo);
        board.addEnemyChara(矮子王);
        board.initSkills();
        board.run("1a1 1a1 1q1 1a1 1a1");
        assertEquals(1183459, 矮子王.getLife(), 100);
    }
}