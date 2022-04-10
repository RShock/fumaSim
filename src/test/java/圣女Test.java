import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.木桩;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xiaor.charas.ImportedChara.initChara;

public class 圣女Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 圣女堆叠buff() {
        Chara 圣女 = initChara("丰收圣女 菲欧拉 攻击力670745 星5 绊5 潜6");
        木桩 木桩1 = 木桩.init("");
        木桩 木桩2 = 木桩.init("");
        木桩 木桩3 = 木桩.init("");
        木桩 木桩4 = 木桩.init("");
        木桩 木桩5 = 木桩.init("");
        board.addOurChara(圣女);
        board.addOurChara(木桩1);
        board.addOurChara(木桩2);
        board.addOurChara(木桩3);
        board.addOurChara(木桩4);
        board.addEnemyChara(木桩5);

        board.initSkills();
        board.run("""
                    1a1 |
                    1a1 |
                    1a1 |
                    1a1 |
                    1a1 |
                    1a1
                """);
        assertEquals(0, 木桩5.getLife(), 10);
    }
}