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

public class 椿测试队Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 满配椿队测试() {
        Tools.initMaxChara(Arrays.asList("千年灵狐 椿", "鲜血魔王 洛缇亚", "闪耀歌姬 黑白诺艾莉", "魔物终结 鬼醉木", "乘风破浪 兰儿"));
        超级机器人木桩 木桩 = 超级机器人木桩.init("生命5063653034");
        board.addEnemyChara(木桩);
        board.initSkills();
        TestTools.stepCheckRunL(GameBoard.getInstance(), """
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        1D 2D 3D 4D 5D
                        2Q 3Q 4Q 1Q 5Q
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        2A 3D 4D 5D 1D
                        """,
                Arrays.asList(0L, 0L, 5061284182L, 5058783356L, 5055891464L,
                        0L, 0L, 0L, 0L, 5045125597L,
                        0L, 0L, 0L, 0L, 5032785107L,
                        0L, 0L, 0L, 0L, 5032785107L,
                        0L, 0L, 4770453814L, 4304202770L, 3199447458L,
                        0L, 0L, 0L, 0L, 3121410520L,
                        0L, 0L, 0L, 0L, 3078886051L,
                        0L, 0L, 0L, 0L, 3078886051L


                )
                , 木桩);
    }

    @Disabled
    @Test
    public void 椿队完全测试() throws IOException {
        FullTest fullTest = new FullTest(Arrays.asList("千年灵狐 椿", "鲜血魔王 洛缇亚", "闪耀歌姬 黑白诺艾莉", "魔物终结 鬼醉木", "偶像经纪人 梅丝米奈雅"),
                """
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        1D 2D 3D 4D 5D
                        2Q 3Q 1Q 4Q 5Q
                        
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        1D 2D 3D 4D 5D
                        2Q 3Q 1Q 4Q 5Q
                        
                        2A 3A 1A 4A 5A
                        2A 3A 1A 4A 5A
                        1D 2D 3D 4D 5D
                        2Q 3Q 1Q 4Q 5Q
                        """);
        fullTest.fullTest();
    }
}