package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.超级机器人木桩;

import java.util.Arrays;

public class 圣诞队Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 圣诞队3人测试() {
        Chara 蛋壳 = ImportedChara.initChara("暗黑圣诞_艾可 攻击力663393 生命值300 星5 绊3 潜6 队长");
        Chara 蛋矮 = ImportedChara.initChara("圣诞矮人王_兰儿 攻击力798273 星5 绊3 潜6");
        Chara 驯鹿 = ImportedChara.initChara("圣诞驯鹿_希依 攻击力554962 星5 绊5 潜6");
        Chara 友方看戏工具人1 = ImportedChara.initChara("测试角色");
        Chara 友方看戏工具人2 = ImportedChara.initChara("测试角色");
        超级机器人木桩 木桩 = 超级机器人木桩.init("");
        board.addOurChara(蛋壳);
        board.addOurChara(蛋矮);
        board.addOurChara(驯鹿);
        board.addOurChara(友方看戏工具人1);
        board.addOurChara(友方看戏工具人2);
        board.addEnemyChara(木桩);

        TestTools.stepCheckRun(GameBoard.getInstance(), """
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1q 3a |
                            2a 1a 3a
                           
                        """,
                Arrays.asList(2147483192, 2136808090, 2136372672, 0,
                        0, 2124341832, 2123885680, 0
                ), 木桩);


    }
}