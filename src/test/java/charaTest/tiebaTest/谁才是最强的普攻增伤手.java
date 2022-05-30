package charaTest.tiebaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.charas.木桩;
import xiaor.charas.超级机器人木桩;
import xiaor.tools.Tools;
import xiaor.tools.tester.FullTest;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xiaor.charas.CharaName.*;

public class 谁才是最强的普攻增伤手 {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 确保正确的测试() {
        Chara 凯萨 = ImportedChara.initChara("异界_凯萨 攻击力588258 生命值2941292 星5 绊2 潜6 队长");
        Chara 圣女 = ImportedChara.initChara("丰收圣女_菲欧拉 攻击力566650  星5 绊2 潜6");
        Chara 剑圣 = ImportedChara.initChara("剑圣_神无雪 攻击力597491 星4 绊5 潜6 ");
        Chara 夏狐 = ImportedChara.initChara("夏日_静 攻击力661306 星5 绊2 潜6");
        Chara 春忍 = ImportedChara.initChara("新春_凛月 攻击力760784 星5 绊1 潜6");
        超级机器人木桩 dummy = 超级机器人木桩.init("");
        board.addOurChara(凯萨);
        board.addOurChara(圣女);
        board.addOurChara(剑圣);
        board.addOurChara(夏狐);
        board.addOurChara(春忍);
        board.addEnemyChara(dummy);
        board.initSkills();
        board.run("""
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5q 1q
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4q 5q 1a
                        2a 3q 4a 5a 1q
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5q 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1q
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5q 1a
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5a 1q
                        2a 3a 4q 5q 1a
                        2a 3q 4a 5a 1a
                """);
        assertEquals(108542914, dummy.getLife());
    }

    @Test
    @Disabled
    void 正式测试1之春忍() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 春忍
        FullTest fullTest = new FullTest(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 春忍),
                """
                            2a 3a 4a 5a 1a
                            2a 3a 4a 5a 1a
                            2a 3a 4a 5a 1a
                            2a 3q 4a 5a 1a
                            2a 3a 4q 5q 1q
                            
                            2q 3q 4a 5a 1a
                            2a 3a 4a 5a 1a
                            2a 3a 4q 5q 1a
                            2a 3q 4a 5a 1q
                            2a 3a 4a 5a 1a
                            
                            2q 3q 4q 5q 1a
                            2a 3a 4a 5a 1a
                            2a 3a 4a 5a 1q
                            2a 3q 4a 5a 1a
                            2a 3a 4q 5q 1a
                            
                            2q 3q 4a 5a 1a
                            2a 3a 4a 5a 1q
                            2a 3a 4q 5q 1a
                            2a 3q 4a 5a 1a
                            2a 3a 4a 5a 1a
                            
                            2q 3q 4q 5q 1q
                        """);
        fullTest.fullTest();
    }

    @Test
    @Disabled
    void 正式测试2之春忍() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 梦露
        FullTest fullTest = new FullTest(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 梦露),
                """
                            2a 3a 4a 5a 1a
                            2a 3a 4a 5a 1a
                            2a 3a 4a 5a 1a
                            2a 3q 4a 5a 1a
                            2a 3a 4q 5q 1q
                            
                            2q 3q 4a 5a 1a
                            2a 3a 4a 5a 1a
                            2a 3a 4q 5q 1a
                            2a 3q 4a 5a 1q
                            2a 3a 4a 5a 1a
                            
                            2q 3q 4q 5q 1a
                            2a 3a 4a 5a 1a
                            2a 3a 4a 5a 1q
                            2a 3q 4a 5a 1a
                            2a 3a 4q 5q 1a
                            
                            2q 3q 4a 5a 1a
                            2a 3a 4a 5a 1q
                            2a 3a 4q 5q 1a
                            2a 3q 4a 5a 1a
                            2a 3a 4a 5a 1a
                            
                            2q 3q 4q 5q 1q
                        """);
        fullTest.fullTest();
    }

    @Test
    void 正式测试春忍log() {
        //队伍配置 凯撒 圣女 剑圣 夏静 春忍
        Tools.initMaxChara(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 春忍));
        木桩 木桩1 = 木桩.init("生命0");
        board.addEnemyChara(木桩1);
        board.initSkills();
        board.run("""
                    2a 3a 4a 5a 1a
                    2a 3a 4a 5a 1a
                    2a 3a 4a 5a 1a
                    2a 3q 4a 5a 1a
                    2a 3a 4q 5q 1q
                    
                    2q 3q 4a 5a 1a
                    2a 3a 4a 5a 1a
                    2a 3a 4q 5q 1a
                    2a 3q 4a 5a 1q
                    2a 3a 4a 5a 1a
                    
                    2q 3q 4q 5q 1a
                    2a 3a 4a 5a 1a
                    2a 3a 4a 5a 1q
                    2a 3q 4a 5a 1a
                    2a 3a 4q 5q 1a
                    
                    2q 3q 4a 5a 1a
                    2a 3a 4a 5a 1q
                    2a 3a 4q 5q 1a
                    2a 3q 4a 5a 1a
                    2a 3a 4a 5a 1a
                    
                    2q 3q 4q 5q 1q
                """);
        Assertions.assertEquals(-5167043446L, 木桩1.getLife());

    }


}
