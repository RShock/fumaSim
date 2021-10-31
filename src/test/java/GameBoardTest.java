
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.胆小纸袋狼_沃沃;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @Test
    void should_create_Wowo() {
        胆小纸袋狼_沃沃 wowo = new 胆小纸袋狼_沃沃();
        GameBoard.getInstance().addOurChara(wowo);
    }

    @Test
    void should_Wowo_attack() {
        GameBoard board = GameBoard.getInstance();
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().name("沃沃1").attack(100).build();
        胆小纸袋狼_沃沃 wowo2 = 胆小纸袋狼_沃沃.builder().name("沃沃2").life(500).build();
        board.addOurChara(wowo);
        board.addEnemyChara(wowo2);
        board.initSkills();
        board.run("1a1");
        assertEquals(400, wowo2.getLife());
    }

    @Test
    void should_Wowo_skill_and_attack() {
        GameBoard board = GameBoard.getInstance();
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().name("沃沃1").attack(100).build();
        胆小纸袋狼_沃沃 wowo2 = 胆小纸袋狼_沃沃.builder().name("沃沃2").life(500).build();
        board.addOurChara(wowo);
        board.addEnemyChara(wowo2);
        board.initSkills();
        board.run("1d1 1a1");
        assertEquals(104, wowo2.getLife());
        //第一回合开大打200 第二回合不开大打196
    }

    @Test
    void should_Wowo_leader_much_stronger() {
        GameBoard board = GameBoard.getInstance();
        胆小纸袋狼_沃沃 wowo = 胆小纸袋狼_沃沃.builder().isLeader(true).attack(100).name("沃沃1号").build();
        胆小纸袋狼_沃沃 wowo2 = 胆小纸袋狼_沃沃.builder().life(500).name("沃沃2号").build();
        board.addOurChara(wowo);
        board.addEnemyChara(wowo2);
        board.initSkills();
        board.run("1d1 1a1");
        assertEquals(104, wowo2.getLife());
    }
}