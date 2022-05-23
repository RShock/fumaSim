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
        Chara 友方看戏工具人2 = ImportedChara.initChara("测试角色");   //不补上工具人 蛋矮buff会乱加
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
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1q 3q |
                            2q 1a 3a |
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1a 3a |
                            2a 1q 3q |
                            2a 1a 3a |
                            2q 1a 3a |
                            2a 1a 3a |
                        """,
                Arrays.asList(2147483192, 2136808090, 2136372672,
                        0, 2124341832, 2123885680,
                        0, 2110420818, 2109943932,
                        0, 2094966764, 2094469144,
                        0, 2077901385, 2077403765,
                        0, 2006948089, 2006450469,
                        0, 1989370305, 1988872685,
                        0, 1971792521, 1971294901,
                        0, 1954214737, 1953717117,
                        0, 1936636953, 1936139333,
                        0, 1865683657, 0,
                        0, 1846273501, 1845658580,
                        0,0,0,
                        0,0,0,
                        0,0,0,
                        0,0,1720771428,
                        0,0,1701595413,
                        0,0,1683168567
                ), 木桩);
    }
}