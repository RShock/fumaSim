package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.core.charas.Chara;
import xiaor.core.charas.ImportedChara;
import xiaor.core.charas.木桩;

public class 圣诞鹿Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建圣诞鹿测试() {
        Chara 圣诞鹿 = ImportedChara.initChara("圣诞驯鹿_希依");
        GameBoard.getInstance().addOurChara(圣诞鹿);
    }

    @Test
    void 圣诞鹿行动测试() {
        Chara 圣诞鹿 = ImportedChara.initChara("圣诞驯鹿_希依 攻击力100 生命值300 星1 绊1 潜1 队长");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(圣诞鹿);
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
