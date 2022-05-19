package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.超级机器人木桩;
import xiaor.tools.Tools;

import java.util.Arrays;

import static xiaor.charas.CharaName.*;

public class 梦露Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 梦露全队集合测试() {
        Chara 梦露 = ImportedChara.initChara("梦游魔境_露露 攻击力1816 羁绊1 星4 潜力6 队长");
        Chara 露露 = ImportedChara.initChara("法斯公主_露露 攻击力2440 星5 绊5 潜6");
        Chara 幼精 = ImportedChara.initChara("机灵古怪_塞露西亚 攻击力2288 星4 绊3 潜6");
        Chara 千鹤 = ImportedChara.initChara("复生公主_千鹤 攻击力1430 星3 绊1 潜6");
        Chara 沃沃 = ImportedChara.initChara("胆小纸袋狼_沃沃 攻击力1618 星3 绊2 潜6");

        Chara 测试角色 = ImportedChara.initChara("测试角色 生命7758206");
        board.addOurChara(梦露);
        board.addOurChara(露露);
        board.addOurChara(幼精);
        board.addOurChara(千鹤);
        board.addOurChara(沃沃);
        board.addEnemyChara(测试角色);

        board.initSkills();
        TestTools.stepCheckRun(board,
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

    @Test
    void 梦露圣女春忍剑圣夏狐() {
        Tools.initMaxChara(Arrays.asList(梦露, 圣女, 春忍, 剑圣, 夏狐));
        GameBoard.getAlly().get(3).setBaseAttack(663879);
        超级机器人木桩 木桩 = 超级机器人木桩.init("");
        board.addEnemyChara(木桩);
        board.initSkills();
        TestTools.stepCheckRun(board,
                """
                        2a 1q 3a 4a 5a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 4q 3a 5a 1a
                        2a 5q 1q 4a 3q
                        2q 4q 3a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 1q 3a 4a 5a
                        2a 4q 3q 5a 1a
                        2a 3a 4a 5a 1a
                        2q 1q 3a 4a 5a
                        2a 1a
                        """,
                Arrays.asList(
                    2147483192,2147483192-9737510,2147483192-9737510-9323740-2797122-4475395,0,2090986711,
                        0,0,0,0,2012419726,
                        0,0,0,0,1914948390,
                        0,0,0,0,1803358197,
                        0,0,0,0,1682377391,
                        1682377391, 1663478170, 1553055093,1553055093-31599243-11849716-18959545,1437548734,
                        0,0,0,0,1156578565,
                        1156578565,1126392284,975425047,0,819929325,
                        0,0,0,0,666413997,
                        0,0,0,0,397384579,
                        0,0,0,0,13566343
                ),
                木桩
        );
    }

    @Test
    void 梦露圣女春忍剑圣夏狐11T打完() {
        GameBoard gameBoard = GameBoard.getInstance();
        int minAtk = TestTools.biSearch((atk) -> {
            gameBoard.resetBoard();
            Tools.initMaxChara(Arrays.asList(梦露, 圣女, 春忍, 剑圣, 夏狐));

            GameBoard.getAlly().get(3).setBaseAttack(atk);
            超级机器人木桩 木桩 = 超级机器人木桩.init("");
            board.addEnemyChara(木桩);
            board.initSkills();
            board.run(
                    """
                            2a 1q 3a 4a 5a
                            2a 3a 4a 5a 1a
                            2a 3a 4a 5a 1a
                            2a 4q 3a 5a 1a
                            2a 5q 1q 4a 3q
                            2q 4q 5a 3a 1a
                            2a 3a 4a 5a 1a
                            2a 1q 3a 4a 5a
                            2a 4q 3q 5a 1a
                            2a 3a 4a 5a 1a
                            2q 1q 3a 4a 5a
                            """
            );
            return 木桩.getLife() < 0;
        });
        System.out.println(minAtk);
    }
}