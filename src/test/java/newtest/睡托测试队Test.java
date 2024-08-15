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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xiaor.core.charas.CharaName.*;

public class 睡托测试队Test {
    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 满配睡托队测试() {
        Tools.initMaxChara(Arrays.asList("不健全遐想 托特拉", "调皮捣蛋 白", "甜心可可 巴尔", "双星之红 安丝蒂", "性诞恋歌 伊布力斯"));
        超级机器人木桩 木桩 = 超级机器人木桩.init("生命5063653034");

        board.addEnemyChara(木桩);
        board.initSkills();
        TestTools.stepCheckRunL(GameBoard.getInstance(), """
                        2A 3A 4A 5A 1A
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
                        """,
                Arrays.asList(0L, 0L, 0L, 0L, 5037236622L,
                        0L, 0L, 0L, 0L, 5006317850L,
                        0L, 0L, 0L, 0L, 4972757436L,
                        0L, 0L, 0L, 0L, 4724590586L,
                        0L, 0L, 4443988314L, 4398345311L, 4371122978L,
                        0L, 0L, 4325479975L, 4301174321L, 4188020239L,  //6t
                        0L, 0L, 4032996873L, 3319305004L, 3170086138L,  //7t
                        0L, 0L, 3104707923L, 3070878957L, 2918519481L,  //8t
                        0L, 0L, 2407670118L, 2315635814L, 2256584837L,  //9t
                        0L, 0L, 2015703285L, 912771142L, 693028642L,
                        0L, 0L, 599420075L, 550729896L, 343726091L,
                        0L, 0L, 250117524L, 201427345L, -5576460L     //12t
                )//694134 554709
                , 木桩);
    }

    @Disabled
    @Test
    public void 魔法少女队完全测试() throws IOException {
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