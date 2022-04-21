import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.完美靶子伊吹;
import xiaor.tools.record.DamageRecorder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class 梦露Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 梦露全队集合测试() {
        Chara 梦露 = ImportedChara.initChara("梦游魔境 露露 攻击力1816 羁绊1 星4 潜力6 队长");
        Chara 露露 = ImportedChara.initChara("法斯公主 露露 攻击力2440 星5 绊5 潜6");
        Chara 幼精 = ImportedChara.initChara("机灵古怪 塞露西亚 攻击力2288 星4 绊3 潜6");
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力1430 星3 绊1 潜6");
        Chara 沃沃 = ImportedChara.initChara("胆小纸袋狼 沃沃 攻击力1618 星3 绊2 潜6");

        Chara 测试角色 = ImportedChara.initChara("测试 角色 生命7758206");
        board.addOurChara(梦露);
        board.addOurChara(露露);
        board.addOurChara(幼精);
        board.addOurChara(千鹤);
        board.addOurChara(沃沃);
        board.addEnemyChara(测试角色);

        board.initSkills();
        TestTool.stepCheckRun(board,
                """
                        2a 1q 3a 4a 5a
                        2a 1a 3a 4a 5a
                        2a 1a 3a 4a 5q
                        2a 1a 3a 4a 5a
                        2q 1q 3a 4a 5a
                        """,
                Arrays.asList(7758206, 7745284, 7722021, 7705625, 7686926,
                        7686926, 7667499, 7643027, 7625821, 7606200,
                        7606200, 7585848, 7560170, 7542155, 7532935,
                        7532935, 7516217, 7495092, 7480301, 7441905,
                        0,0,0,0,7323419

                        ),
                测试角色
                );
    }
}