import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xiaor.GameBoard;
import xiaor.charas.*;
import xiaor.tools.record.DamageRecorder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class 风队Test {

    GameBoard board = GameBoard.getInstance();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

//    @Test
//    void 风队全队集合测试2() {
//        Chara 小精灵王 = ImportedChara.initChara("机灵古怪 塞露西亚 攻击力560316 羁绊3 星4 潜力6 队长");
//        法斯公主_露露 露露 = 法斯公主_露露.init("攻击力675452 星5 绊5 潜6");
//        精灵王_塞露西亚 精灵王 = 精灵王_塞露西亚.init("攻击力542380 星5 绊5 潜6");
//        胆小纸袋狼_沃沃 沃沃 = 胆小纸袋狼_沃沃.init("攻击力448115 星4 绊2 潜5");
//        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力395839 星3 绊2 潜6");
//
//        超级机器人木桩 木桩 = 超级机器人木桩.init("");
//        board.addOurChara(小精灵王);
//        board.addOurChara(露露);
//        board.addOurChara(精灵王);
//        board.addOurChara(沃沃);
//        board.addOurChara(千鹤);
//        board.addEnemyChara(木桩);
//
//        board.initSkills();
//        board.run("""
//                1a1 2a1 3a1 4a1 5d1
//                1a1 2a1 3a1 4a1 5d1
//                1a1 2a1 3a1 5q1 4q1
//                1a1 2a1 3a1 4a1 5d1
//                1q1
//                """);
//        assertEquals(-820408, 木桩.getLife(), 10);
//    }

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


    @Test
    void 风队满配打桩统计() {
        //精灵王 面板攻击力243585 (152116)
        //沃沃 270601 (169126)
        //千鹤 256871
        //露露 248889
        //幼精灵 281230
        机灵古怪_赛露西亚 小精灵王 = 机灵古怪_赛露西亚.init("攻击力281230 羁绊5 星5 潜力6 队长");
        法斯公主_露露 露露 = 法斯公主_露露.init("攻击力248899 星5 绊5 潜6");
        精灵王_塞露西亚 精灵王 = 精灵王_塞露西亚.init("攻击力243585 星5 绊5 潜6");
        胆小纸袋狼_沃沃 沃沃 = 胆小纸袋狼_沃沃.init("攻击力270601 星5 绊5 潜6");
        复生公主_千鹤 千鹤 = 复生公主_千鹤.init("攻击力256871 星5 绊5 潜6");

        完美靶子伊吹 伊吹 = 完美靶子伊吹.init("");
        board.addOurChara(小精灵王);
        board.addOurChara(露露);
        board.addOurChara(精灵王);
        board.addOurChara(千鹤);
        board.addOurChara(沃沃);
        board.addEnemyChara(伊吹);

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