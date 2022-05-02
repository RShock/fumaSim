package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;

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
        Chara 蛋壳 = ImportedChara.initChara("暗黑圣诞_艾可 攻击力100 生命值300 星1 绊1 潜1 队长");
        木桩 dummy = 木桩.init("生命0");
        board.addOurChara(蛋壳);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                1a1
                1d
                1q1
                """);
        // 待数值测试
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
