package newtest;

import charaTest.TestTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.core.GameBoard;
import xiaor.core.Tools;
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
                        2A 3A 4A 1A 5A 1A1 |
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        2Q 3Q 4Q 5Q 1Q
                        """,
                Arrays.asList(0L, 0L, 5053419591L, 5044378526L, 5022312161L,
                0L, 0L, 0L, 4999850433L, 4969937639L
                )
                , 木桩);
    }

    @Disabled
    @Test
    public void 鬼罪木队完全测试() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList(睡托, 小白, 可巴, 星红, 可伊),
                """
                        2A 3A 4A 1A 5A
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2A 3A 4Q 1Q 5A
                        2Q 3Q 5Q 1A 4A
                        
                        2A 3A 1A 4A 5A
                        2A 3A 4Q 1Q 5A
                        2A 3A 1A 4A 5A
                        2Q 3Q 5Q 1A 4A
                        2A 3A 4Q 1Q 5A
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2Q 3Q 4Q 1Q 5Q
                        """);
        fullTest.fullTest();
    }
}