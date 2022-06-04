package charaTest.tiebaTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.*;
import xiaor.tools.Tools;
import xiaor.tools.tester.FullTest;
import xiaor.tools.tester.OneTest;

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
    void 正式测试2之梦露() throws IOException {
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
        Assertions.assertEquals(-3692573854L, 木桩1.getLife());

    }

    @Test
    @Disabled
    void 咩让我完成的测试() throws IOException {
        OneTest oneTest = new OneTest(Arrays.asList(凯萨,圣女,春忍,夏狐,梦露),
                Tools.handleAction("""
                        5A 1A 2A 3A 4A
                        5A 1A 2A 3A 4A
                        5A 1A 2A 3A 4A
                        5A 1A 2A 3A 4A
                        5Q 1A 2Q 3Q 4Q
                        
                        5A 1Q 3A 2A 4A
                        5A 1A 2A 3A 4A
                        5A 1A 3Q 4Q 2Q
                        5Q 1A 2A 3A 4A
                        5A 1A 2A 3A 4A
                        
                        5A 1Q 3A 4A 2Q
                        5A 1A 2Q 3Q 4A
                        5Q 1A 2A 3A 4A
                        5A 1A 2A 3A 4A
                        5A 1A 3A 4A 2Q
                        5A 1Q 2Q 3Q 4A
                        5Q 1A 2A 3A 4A
                        5A 1A 2A 3A 4A
                        5A 1A 3A 4A 2Q
                        5A 1A 2Q 3Q 4A
                        5Q 1Q 2A 3A 4A
                        """,true));
        GameBoard.getInstance().getOurChara().forEach(chara -> chara.setSkillLevel(1));
        oneTest.test();
    }

    @Test
    void 兔姬确保正确的测试() {
        Chara 凯萨 = ImportedChara.initChara("异界_凯萨 攻击力588258 生命值2941292 星5 绊2 潜6 队长");
        Chara 圣女 = ImportedChara.initChara("丰收圣女_菲欧拉 攻击力566650  星5 绊2 潜6");
        Chara 剑圣 = ImportedChara.initChara("剑圣_神无雪 攻击力597491 星4 绊5 潜6 ");
        Chara 夏狐 = ImportedChara.initChara("夏日_静 攻击力661306 星5 绊2 潜6");
        Chara 兔姬 = ImportedChara.initChara("恶兔魔王_兔姬 攻击力38840 星3 绊1 潜1");
        超级机器人木桩 dummy = 超级机器人木桩.init("");
        board.addOurChara(凯萨);
        board.addOurChara(圣女);
        board.addOurChara(剑圣);
        board.addOurChara(夏狐);
        board.addOurChara(兔姬);
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
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5q 1q
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5a 1a
                        
                        2q 3q 4a 5a 1a
                        2d 3d 4d 5d 1q
                        2d 3d 4d 5d 1a
                        2d 3d 4d 5d 1a
                        2d 3d 4d 5d 1a
                        
                        2d 3d 4d 5d 1q
                """);
        assertEquals(599969997, dummy.getLife());
    }

    @Test
    @Disabled
    void 正式测试3之兔姬() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 兔姬
        FullTest fullTest = new FullTest(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 兔姬),
                """
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5q 1q
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5q 1q
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5a 1a
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5a 1a
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5q 1q
                        """);
        fullTest.fullTest();
    }

    @Test
    void 正式测试兔姬log() {
        //队伍配置 凯撒 圣女 剑圣 夏静 兔姬
        Tools.initMaxChara(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 兔姬));
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
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5q 1q
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5a 1a
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5a 1a
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5q 1q
                """);
        Assertions.assertEquals(-3494291522L, 木桩1.getLife());

    }

    //三个人4星1绊表现如何呢？
    @Test
    @Disabled
    void 兔姬4星1绊() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 兔姬
        Tools.init4星1绊Chara(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 兔姬));
        木桩 木桩1 = 木桩.init("生命0");
        new OneTest("""
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5q 1q
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5q 1q
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5a 1a
                        2a 3a 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3q 4a 5a 1a
                        2a 3a 4q 5a 1a
                        
                        2q 3q 4a 5a 1a
                        2a 3a 4a 5q 1q
                        2a 3a 4q 5a 1a
                        2a 3q 4a 5a 1a
                        2a 3a 4a 5a 1a
                        
                        2q 3q 4q 5q 1q
                """).test();
    }

    @Test
    @Disabled
    void 春忍4星1绊() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 春忍
        Tools.init4星1绊Chara(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 春忍));
        new OneTest("""
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
                """).test();

    }

    @Test
    @Disabled
    void 梦露满配5桩() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 春忍
        Tools.initMaxChara(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 梦露));
        new OneTest("""
                            2a1 3a1 4a1 5a1 1a1
                            2a1 3a1 4a1 5a1 1a1
                            2a1 3a1 4a1 5a1 1a1
                            2a1 3q1 4a1 5a1 1a1
                            2a1 3a1 4q1 5q1 1q1
                            
                            2q2 3q2 4a2 5a2 1a2
                            2a2 3a2 4a2 5a2 1a2
                            2a2 3a2 4q2 5q2 1a2
                            2a2 3q2 4a2 5a2 1q2
                            
                            2a3 3a3 4a3 5a3 1a3
                            2q3 3q3 4q3 5q3 1a3
                            2a3 3a3 4a3 5a3 1a3
                            2a3 3a3 4a3 5a3 1q3
                            
                            2a4 3q4 4a4 5a4 1a4
                            2a4 3a4 4q4 5q4 1a4
                            2q4 3q4 4a4 5a4 1a4
                            2a4 3a4 4a4 5a4 1q4
                           
                            2a5 3a5 4q5 5q5 1a5
                            2a5 3q5 4a5 5a5 1a5
                            2a5 3a5 4a5 5a5 1a5
                            2q5 3q5 4q5 5q5 1q5
                        """).test();

    }

    //三个人4星1绊表现如何呢？
    @Test
    @Disabled
    void 兔姬满配5桩() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 兔姬
        Tools.init4星1绊Chara(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 兔姬));
        木桩 木桩1 = 木桩.init("生命0");
        new OneTest("""
                        2a1 3a1 4a1 5a1 1a1
                        2a1 3a1 4a1 5a1 1a1
                        2a1 3a1 4a1 5a1 1a1
                        2a1 3q1 4a1 5a1 1a1
                        2a1 3a1 4q1 5q1 1q1
                        
                        2q2 3q2 4a2 5a2 1a2
                        2a2 3a2 4a2 5a2 1a2
                        2a2 3a2 4q2 5a2 1a2
                        2a2 3q2 4a2 5q2 1q2
                        
                        2a3 3a3 4a3 5a3 1a3
                        2q3 3q3 4q3 5a3 1a3
                        2a3 3a3 4a3 5a3 1a3
                        2a3 3a3 4a3 5q3 1q3
                        
                        2a4 3q4 4a4 5a4 1a4
                        2a4 3a4 4q4 5a4 1a4
                        2q4 3q4 4a4 5a4 1a4
                        2a4 3a4 4a4 5q4 1q4
                        
                        2a5 3a5 4q5 5a5 1a5
                        2a5 3q5 4a5 5a5 1a5
                        2a5 3a5 4a5 5a5 1a5
                        2q5 3q5 4q5 5q5 1q5
                """).test();
    }

    @Test
    @Disabled
    void 春忍满配5桩() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 春忍
        Tools.init4星1绊Chara(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 春忍));
        new OneTest("""
                    2a1 3a1 4a1 5a1 1a1
                            2a1 3a1 4a1 5a1 1a1
                            2a1 3a1 4a1 5a1 1a1
                            2a1 3q1 4a1 5a1 1a1
                            2a1 3a1 4q1 5q1 1q1
                            
                            2q2 3q2 4a2 5a2 1a2
                            2a2 3a2 4a2 5a2 1a2
                            2a2 3a2 4q2 5q2 1a2
                            2a2 3q2 4a2 5a2 1q2
                            
                            2a3 3a3 4a3 5a3 1a3
                            2q3 3q3 4q3 5q3 1a3
                            2a3 3a3 4a3 5a3 1a3
                            2a3 3a3 4a3 5a3 1q3
                            
                            2a4 3q4 4a4 5a4 1a4
                            2a4 3a4 4q4 5q4 1a4
                            2q4 3q4 4a4 5a4 1a4
                            2a4 3a4 4a4 5a4 1q4
                           
                            2a5 3a5 4q5 5q5 1a5
                            2a5 3q5 4a5 5a5 1a5
                            2a5 3a5 4a5 5a5 1a5
                            2q5 3q5 4q5 5q5 1q5
                """).test();

    }

    @Test
    @Disabled
    void 梦露4星1绊() throws IOException {
        //队伍配置 凯撒 圣女 剑圣 夏静 春忍
        Tools.init4星1绊Chara(Arrays.asList(凯萨, 圣女, 剑圣, 夏狐, 梦露));
        new OneTest("""
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
                        """).test();

    }

}
