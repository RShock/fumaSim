package charaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.*;
import xiaor.tools.Tools;
import xiaor.tools.record.DamageRecorder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class 风队Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void 风队全队集合测试() {
        Stream<String> stringStream = Stream.of("a", "b", "c");
        List<String> stringList = stringStream.toList();
        for (String s : stringList) {
            System.out.println(s);
        }

        Chara 小精灵王 = ImportedChara.initChara("机灵古怪 塞露西亚 攻击力505739 羁绊2 星3 潜力6 队长");
        Chara 露露 = ImportedChara.initChara("法斯公主 露露 攻击力675452 星5 绊5 潜6");
        Chara 精灵王 = ImportedChara.initChara("精灵王 塞露西亚 攻击力479798 星4 绊5 潜6");
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力361418 星3 绊1 潜6");
        Chara 沃沃 = ImportedChara.initChara("胆小纸袋狼 沃沃 攻击力204650 星3 绊2 潜5");

        Chara 测试角色 = ImportedChara.initChara("测试 角色 生命0");
        board.addOurChara(小精灵王);
        board.addOurChara(露露);
        board.addOurChara(精灵王);
        board.addOurChara(沃沃);
        board.addOurChara(千鹤);
        board.addEnemyChara(测试角色);

        board.initSkills();
        board.run("""
                1a1 2a1 3a1 4a1 5d1
                1a1 2a1 3a1 4a1 5d1
                1a1 2a1 3a1 5q1 4q1
                1a1 2a1 3a1 4a1 5d1
                1q1
                """);

        assertEquals(-52025264, 测试角色.getLife(), 10);
    }

    @Test
    void 风队满配打桩统计() {
        Chara 小精灵王 = ImportedChara.initChara("机灵古怪 塞露西亚 攻击力281230 羁绊5 星5 潜力6 队长");
        Chara 露露 = ImportedChara.initChara("法斯公主 露露 攻击力248899 星5 绊5 潜6");
        Chara 精灵王 = ImportedChara.initChara("精灵王 塞露西亚 攻击力243585 星5 绊5 潜6");
        Chara 沃沃 = ImportedChara.initChara("胆小纸袋狼 沃沃 攻击力270601 星5 绊5 潜6");
        Chara 千鹤 = ImportedChara.initChara("复生公主 千鹤 攻击力256871 星5 绊5 潜6");

        木桩 伊吹 = 木桩.init("生命0");
        board.addOurChara(小精灵王);
        board.addOurChara(露露);
        board.addOurChara(精灵王);
        board.addOurChara(千鹤);
        board.addOurChara(沃沃);
        board.addEnemyChara(伊吹);
        小精灵王.setDisabled();
        board.initSkills();
        board.run("""
                2A 1A 3A 4D 5A
                2A 1A 3A 4D 5A
                2A 4Q 1A 3A 5Q
                2A 1A 3A 4D 5A
                3Q 2Q 1Q 4A 5A
                2A 4Q 3A 1A 5A
                2A 4D 3A 1A 5A
                2A 3A 1A 4A 5A
                3Q 2Q 1Q 4Q 5A
                2A 4D 3A 1A 5A
                2A 3A 1A 4A 5Q
                2A 3A 1A 4Q 5A
                3Q 2Q 1Q 4D 5A
                2A 3A 1A 4A 5A
                2A 3A 1A 4Q 5A
                2A 3A 1A 4D 5A
                3Q 2Q 1Q 4A 5A
                2A 3A 1A 4Q 5A
                2A 3A 1A 4D 5Q
                2A 3A 1A 4A 5A
                3Q 2Q 1Q 4Q 5A
                2A 3A 1A 4D 5A
                2A 3A 1A 4A 5A
                2A 3A 1A 4Q 5A
                3Q 2Q 1Q 4D 5A
                """);
        DamageRecorder.getInstance().countDamage();
    }

    /**
     * 1W     2A 1A 3A 4D 5A   00006>11127
     * 2W     2A 1A 3A 4D 5A   11127>22248
     * 3W     2A 4Q 1A 3A 5Q   22248>33311
     * 4W     2A 1A 3A 4D 5A   33311>44432
     * 5W     3Q 2Q 1Q 4A 5A  44432>11143
     * 6W     2A 4Q 3A 1A 5A  11143>22214
     * 7W     2A 4D 3A 1A 5A  22214>33335
     * 8W     2A 3A 1A 4A 5A  33335>44446
     * 9W     3Q 2Q 1Q 4Q 5A 44446>11117
     * 10W    2A 4D 3A 1A 5A  11117>22238
     * 11W    2A 3A 1A 4A 5Q  22238>33341
     * 12W    2A 3A 1A 4Q 5A  33341>44412
     * 13W    3Q 2Q 1Q 4D 5A 44412>11133
     * 14W    2A 3A 1A 4A 5A  11133>22244
     * 15W    2A 3A 1A 4Q 5A  22244>33315
     * 16W    2A 3A 1A 4D 5A  33315>44436
     * 17W    3Q 2Q 1Q 4A 5A 44436>11147
     * 18W    2A 3A 1A 4Q 5A  11147>22218
     * 19W    2A 3A 1A 4D 5Q  22218>33331
     * 20W    2A 3A 1A 4A 5A   33331>44442
     * 21W    3Q 2Q 1Q 4Q 5A  44442>11113
     * 22W    2A 3A 1A 4D 5A   11113>22234
     * 23W    2A 3A 1A 4A 5A    22234>33345
     * 24W    2A 3A 1A 4Q 5A   33345>44416
     * 25W    3Q 2Q 1Q 4D 5A  44416>11137(W26)
     */

    @Test
    void BK风队17回合测试() {
        Tools.initMaxChara(Arrays.asList("机灵古怪 塞露西亚", "梦游魔境 千鹤", "精灵王 塞露西亚", "梦游魔境 露露", "胆小纸袋狼 沃沃"));
        超级机器人木桩 木桩 = 超级机器人木桩.init("");
        ;
        /**
         * bk的千鹤只有2花 露露1花
         */
        GameBoard.getAlly().get(1).setSkillLevel(2);
        GameBoard.getAlly().get(1).setBaseAttack(779251);
        GameBoard.getAlly().get(3).setSkillLevel(1);
        GameBoard.getAlly().get(3).setBaseAttack(601984);

        board.addEnemyChara(木桩);
        board.initSkills();
        TestTools.stepCheckRun(GameBoard.getInstance(), """
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5A
                        2A 1A 3A 4A 5Q
                        2A 1A 3A 4A 5A
                        2Q 3Q 4Q 1Q 5A
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        2Q 3Q 4Q 1Q 5A
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5Q
                        2A 3A 4A 1A 5A
                        2Q 3Q 4Q 1Q 5A
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        2A 3A 4A 1A 5A
                        2Q 3Q 4Q 1Q 5A
                        """,
                Arrays.asList(2147483192, 2141572019, 2134441204, 2127796466, 2120254406,
                        0, 2113821161, 2106136152, 0, 2090860911,
                        0, 0, 0, 0, 2058552047,
                        0, 0, 0, 0, 1991969069,
                        0, 0, 0, 0, 1811284684,
                        0, 0, 0, 0, 1658704867,
                        0, 0, 0, 0, 1496960151,
                        0, 0, 0, 0, 1407536370,
                        0, 0, 0, 0, 1238390761,
                        0, 0, 0, 0, 1098594884,
                        0, 0, 0, 0, 981296914,
                        0, 0, 0, 0, 856416137,
                        0, 0, 0, 0, 573323481,
                        0, 0, 0, 0, 357904294,
                        0, 0, 0, 0, 142485107,
                        0, 0, 0, 0, 10534882,
                        0, 0, 0, 0, 0
                ), 木桩);
    }
}