package newtest;

import charaTest.TestTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.core.Tools;
import xiaor.core.charas.Chara;
import xiaor.core.charas.ImportedChara;
import xiaor.core.charas.超级机器人木桩;
import xiaor.mutual.tester.FullTest;

import java.io.IOException;
import java.util.Arrays;

import static xiaor.core.charas.CharaName.*;

public class 鬼醉木测试队Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 满配鬼罪木队测试() {
        Tools.initMaxChara(Arrays.asList("性诞驯鹿 希依", "鲜血魔王 洛缇亚", "闪耀歌姬 黑白诺艾莉", "魔物终结 鬼醉木", "偶像经纪人 梅丝米奈雅"));
        超级机器人木桩 木桩 = 超级机器人木桩.init("生命5063653034 水属性");
        board.mode = "敌方行动";
        board.addEnemyChara(木桩);
        board.initSkills();
        TestTools.stepCheckRunL(GameBoard.getInstance(), """
                        2A 3A 4A 1A 5A 1A4
                        2A 3A 4A 1A 5A 1A4
                        2A 3A 4A 1A 5A 1A2
                        2A 3A 4A 1A 5A 1A2
                        2Q 3Q 4Q 5Q 1Q 1A1
                        2A 3A 4A 1A 5A 1A2
                        """,
                Arrays.asList(0L, 0L, 5053419591L, 5044378526L, 5022312161L,
                        0L, 0L, 0L, 0L, 4969937639L,
                        0L, 0L, 0L, 0L, 4911925544L,
                        0L, 0L, 0L, 0L, 4853913449L,
                        0L, 0L, 4185624539L, 2050764620L, 997143405L,
                        0L, 0L, 0L, 0L, 431099410L

                )
                , 木桩);
    }

    @Test
    public void simpT() {
        Tools.initMaxChara(Arrays.asList("魔物终结 鬼醉木", ""));
        超级机器人木桩 木桩 = 超级机器人木桩.init("生命5063653034 水属性");
        board.mode = "敌方行动";
        board.addEnemyChara(木桩);
        board.initSkills();
        TestTools.stepCheckRunL(GameBoard.getInstance(), """
                        1A 1A 1A 1A
                         """,
                Arrays.asList(0L, 0L, 0L, 0L), 木桩);
    }

    @Test
    public void simpT2() {
        Chara 鬼厨 = ImportedChara.initChara("魔物终结_鬼醉木 攻击力100 星5 队长");
        超级机器人木桩 ourDum = 超级机器人木桩.init("水属性");
        超级机器人木桩 ourDum2 = 超级机器人木桩.init("水属性");
        ourDum.setName("工具友军");
        ourDum2.setName("工具友军2");
        board.addOurChara(鬼厨);
        board.addOurChara(ourDum);
        board.addOurChara(ourDum2);
        超级机器人木桩 dummy = 超级机器人木桩.init("");
        board.addEnemyChara(dummy);
        board.initSkills();
        TestTools.stepCheckRunL(GameBoard.getInstance(), """
                        1Q 2A 3A 1A 2A 3A 1A 2A 3A
                        """,
                Arrays.asList(0L, 0L, 0L, 0L, 0L, 0L,0L,0L,0L), dummy);
    }

    @Disabled
    @Test
    public void 鬼罪木队完全测试() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList("性诞驯鹿 希依", "鲜血魔王 洛缇亚", "闪耀歌姬 黑白诺艾莉", "魔物终结 鬼醉木", "偶像经纪人 梅丝米奈雅"),
                """
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2Q 3Q 1Q 4Q 5Q
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2Q 3Q 1Q 4Q 5Q
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2Q 3Q 1Q 4Q 5Q
                        """);
        fullTest.fullTest();
    }
}