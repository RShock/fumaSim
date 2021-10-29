
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
        胆小纸袋狼_沃沃 wowo = new 胆小纸袋狼_沃沃();
        胆小纸袋狼_沃沃 wowo2 = new 胆小纸袋狼_沃沃();
        wowo.setAttack(100);
        wowo2.setLife(101);
        board.addOurChara(wowo);
        board.addEnemyChara(wowo2);
        board.run("1a1");
        assertEquals(1, wowo2.getLife());
    }

}