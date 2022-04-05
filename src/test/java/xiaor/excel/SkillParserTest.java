package xiaor.excel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class SkillParserTest {
    @Test
    void regex_test() {
        Pattern pattern = Pattern.compile("对(?<target>(目标|群体))(?<amount>\\d+)%(?<type>(技能|普攻))伤害");
        Matcher matcher = pattern.matcher("对目标100%技能伤害");
        matcher.find();

        Assertions.assertEquals("目标", matcher.group("target"));
        Assertions.assertEquals("100", matcher.group("amount"));
        Assertions.assertEquals("技能", matcher.group("type"));
    }

    @Test
    void regex_test2() {
        Pattern pattern = Pattern.compile(
                "(?<target>(目标|群体|ID(?<ID>\\d+)))(?<buffType>.*)(?<incdec>[+-])(?<multi>\\d+)%?");
        Matcher matcher = pattern.matcher("目标受到伤害+20%");
        matcher.find();

        Assertions.assertEquals("目标", matcher.group("target"));
        Assertions.assertEquals("受到伤害", matcher.group("buffType"));
        Assertions.assertEquals("20", matcher.group("multi"));
        Assertions.assertEquals("+", matcher.group("incdec"));
    }

    @Test
    void regex_test3() {
        Pattern pattern = Pattern.compile(
                "(?<target>(自身|目标|我方群体|敌方群体|ID(?<ID>\\d+)|友方|队伍中.{3}|))" +
                        "(?<buffType>.*)" +
                        "(?<incdec>[+-])" +
                        "(" +
                        "(BUFF(?<buffId>\\d+))|" +
                        "((?<multi>\\d+(\\.\\d+)?)%?)" +
                        ")" +
                        "(\\(最多(?<maxlevel>\\d+)层\\))?(\\((?<lastedTurn>\\d+)回合\\))?"
        );
        Matcher matcher = pattern.matcher("目标受到普攻伤害+7.5%(最多4层)");
        matcher.find();
        Assertions.assertEquals("7.5", matcher.group("multi"));
    }

    @Test
    void regex_test4() {
        String part = "目标受到普攻伤害+7.5%(最多4层)";
        Pattern pattern = Pattern.compile(
                "(?<target>(自身|目标|我方群体|敌方群体|ID(?<ID>\\d+)|友方|队伍中.{3}|))" +
                        "(?<buffType>.*)" +
                        "(?<incdec>[+-])" +
                        "(" +
                        "(BUFF(?<buffId>\\d+))|" +
                        "((?<multi>\\d+(\\.\\d+)?)%?)" +
                        ")" +
                        "(\\(最多(?<maxlevel>\\d+)层\\))?(\\((?<lastedTurn>\\d+)回合\\))?"
        );

        Matcher matcher = pattern.matcher(part);
        matcher.find();
        matcher.group("multi");
    }
}