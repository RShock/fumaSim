import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class 风队Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 风队全队集合测试() {
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力505739 羁绊2 星3 潜力6 队长");
        法斯公主_露露 露露 = 法斯公主_露露.init("攻击力675452 星5 绊5 潜6");
        精灵王_塞露西亚 精灵王 = 精灵王_塞露西亚.init("攻击力479798 星4 绊5 潜6");
        胆小纸袋狼_沃沃 沃沃 = 胆小纸袋狼_沃沃.init("攻击力204650 星3 绊2 潜5");
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力361418 星3 绊1 潜6");

        五十四层boss_诺诺可 诺诺可 = 五十四层boss_诺诺可.init("");
        board.addOurChara(小精灵王);
        board.addOurChara(露露);
        board.addOurChara(精灵王);
        board.addOurChara(沃沃);
        board.addOurChara(千鹤);
        board.addEnemyChara(诺诺可);

        board.initSkills();
        board.run("""
                1a1 2a1 3a1 4a1 5d1
                1a1 2a1 3a1 4a1 5d1
                1a1 2a1 3a1 5q1 4q1
                1a1 2a1 3a1 4a1 5d1
                1q1
                """);
        assertEquals(-820408, 诺诺可.getLife(), 10);
    }
}
