package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.*;
import xiaor.tools.record.DamageRecorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static xiaor.charas.ImportedChara.initChara;

public class 小恶魔Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 小恶魔全队集合测试() {
        Chara 夏日_露露 = initChara("夏日 露露 攻击力846348 星5 绊5 潜力6");
        Chara 黑白 = initChara("偶像 黑白诺艾利 攻击力813242 星5 绊1 潜6");
        Chara 圣女 = initChara("丰收圣女 菲欧拉 攻击力670745 星5 绊5 潜6");
        Chara 小恶魔 = initChara("小恶魔 布兰妮 攻击力901043 星3 绊2 潜6 队长");
        Chara 春忍 = initChara("新春 凛月 攻击力515833 星5 绊1 潜6");

        木桩 彩色鸟 = 木桩.init("生命79706648 风属性");
        board.addOurChara(小恶魔);
        board.addOurChara(夏日_露露);
        board.addOurChara(黑白);
        board.addOurChara(圣女);
        board.addOurChara(春忍);
        board.addEnemyChara(彩色鸟);

        board.initSkills();
        board.run("""
                    2a1 4a1 3d1 5a1 1a1
                    2a1 3a1 4a1 5d1 1q1
                """);
        assertTrue(彩色鸟.getLife() > 0);
    }
}