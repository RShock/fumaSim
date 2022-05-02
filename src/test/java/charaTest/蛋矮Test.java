package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;

public class 蛋矮Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建蛋矮测试() {
        Chara 蛋矮 = ImportedChara.initChara("圣诞矮人王_兰儿");
        GameBoard.getInstance().addOurChara(蛋矮);
    }

    @Test
    void 蛋矮行动测试() {
        Chara 蛋矮 = ImportedChara.initChara("圣诞矮人王_兰儿 攻击力100 生命值300 星1 绊1 潜1 队长");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(蛋矮);
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
