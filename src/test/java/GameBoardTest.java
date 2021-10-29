
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
        GameBoard.getInstance().addOurChara(new 胆小纸袋狼_沃沃());
        GameBoard.getInstance().addEnemyChara(new 胆小纸袋狼_沃沃());

    }

}