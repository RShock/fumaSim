package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;

public class 花旦Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建花旦测试() {
        Chara 花旦 = ImportedChara.initChara("花嫁_撒旦");
        GameBoard.getInstance().addOurChara(花旦);
    }

    @Test
    void 花旦行动测试() {
        Chara 花旦 = ImportedChara.initChara("花嫁_撒旦 攻击力100 生命值300 星1 绊1 潜1 队长");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(花旦);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1a1
                1d
                1q1
                """);
        // 待数值测试
    }
}
