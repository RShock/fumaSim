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
}