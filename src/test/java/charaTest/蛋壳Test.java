package charaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.core.charas.Chara;
import xiaor.core.charas.ImportedChara;
import xiaor.core.charas.木桩;
import xiaor.core.charas.超级机器人木桩;

public class 蛋壳Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 创建蛋壳测试() {
        Chara 蛋壳 = ImportedChara.initChara("暗黑圣诞_艾可");
        GameBoard.getInstance().addOurChara(蛋壳);
    }

    @Test
    void 蛋壳行动测试() {
        Chara 蛋壳 = ImportedChara.initChara("暗黑圣诞_艾可 攻击力663393 生命值300 星5 绊3 潜6 队长");
        超级机器人木桩 dummy = 超级机器人木桩.init("");
        board.addOurChara(蛋壳);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1a 1a 1a 1a 1a 1q
                1a 1a 1a 1a 1q
                1a 1a 1a 1a 1q
                """);
        Assertions.assertEquals(2106410922, dummy.getLife());
    }

    @Test
    void 蛋壳蛋矮存在条件测试() {
        // 动态技能
        Chara 蛋壳 = ImportedChara.initChara("暗黑圣诞_艾可 攻击力100 生命值300 星1 绊1 潜1 队长");
        Chara 蛋矮 = ImportedChara.initChara("圣诞矮人王_兰儿 攻击力100 生命值300 星1 绊1 潜1");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(蛋壳);
        board.addOurChara(蛋矮);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1a1 2a1
                """);
    }
}
