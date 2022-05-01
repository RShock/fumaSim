package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;

import static org.junit.jupiter.api.Assertions.assertEquals;

class 凯撒Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void setUp() {
    }

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建凯撒测试() {
        Chara 凯撒 = ImportedChara.initChara("异界_凯萨 攻击力100 生命值300");
        board.addOurChara(凯撒);
        木桩 木桩1 = 木桩.init("生命0");
        board.addEnemyChara(木桩1);
        board.initSkills();
        board.run("1a1 1q1");
        assertEquals(-847, 木桩1.getLife(), 1);
    }


}