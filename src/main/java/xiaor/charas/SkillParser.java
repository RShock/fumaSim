package xiaor.charas;

import xiaor.GameBoard;
import xiaor.excel.vo.SkillExcelVo;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.Action;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skillbuilder.action.BuffType;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.SelfTrigger;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.skillbuilder.trigger.TriggerBuilder;
import xiaor.skillbuilder.when.WhenBuilder;
import xiaor.tools.TriggerEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static xiaor.Common.INFI;

public class SkillParser {
    public static void addSkill(ImportedChara chara, SkillExcelVo vo) {
        System.out.println("正在解析" + vo.getSkillId());
        if (vo.getEffect().equals("没做")) return;
        SkillType skillType = vo.getSkillType();
        switch (skillType) {
            case 队长技 -> {
                if (!chara.isLeader()) return;
            }
            case 一星被动 -> {
                if (chara.getStar() < 1) {
                    System.out.println(chara+"没1星，1星技能不触发，是否忘记填写了星数？");
                    return;
                }
            }
            case 三星被动 -> {
                if (chara.getStar() < 3) {
                    System.out.println(chara+"没3星，3星技能不触发，是否忘记填写了星数？");
                    return;
                }
            }
            case 五星被动 -> {
                if (chara.getStar() < 5){
                    System.out.println(chara+"没5星，5星技能不触发");
                    return;
                }
            }
            case 六潜被动 -> {
                if (!chara.is6()) {
                    System.out.println(chara+"没6潜，6潜技能不触发，是否忘记填写了潜力？");
                    return;
                }
            }
            case 十二潜被动 -> {
                if (!chara.is12()) return;
            }
            case 必杀技1绊 -> {
                if (chara.getSkillLevel() != 1) return;
            }
            case 必杀技2绊 -> {
                if (chara.getSkillLevel() != 2) return;
            }
            case 必杀技3绊 -> {
                if (chara.getSkillLevel() != 3) return;
            }
            case 必杀技4绊 -> {
                if (chara.getSkillLevel() != 4) return;
            }
            case 必杀技5绊 -> {
                if (chara.getSkillLevel() != 5) return;
            }
            case 他人给予技能 -> {
                return;
            }
            case 普攻 -> {
            }
        }
        Trigger trigger;
        TriggerEnum triggerEnum = vo.getTrigger();
        trigger = switch (triggerEnum) {
            case 游戏开始时, 被动光环 -> TriggerBuilder.when(triggerEnum);
            default -> SelfTrigger.act(chara, triggerEnum);
        };
        String skillString = vo.getEffect();
        List<Supplier<Boolean>> switchChecker = new ArrayList<>();
        if (skillString.startsWith("如果")) {
            //这个技能是条件触发型的，触发器触发后还需要额外的校验
            Pattern pattern = Pattern.compile(
                    "如果(?<condition>.*)则(?<skill>.*)");
            Matcher matcher = pattern.matcher(vo.getEffect());
            matcher.find();
            switchChecker.add(() -> parseCondition(matcher.group("condition")));
            skillString = matcher.group("skill");
        }

        if (skillString.contains("获得技能")) {
            throw new RuntimeException("还没做");
        }
        WhenBuilder tempSkill = SkillBuilder.createNewSkill(chara, skillType).when(trigger);
        parseSkill(chara, tempSkill, skillString, switchChecker);
    }

    private static Boolean parseCondition(String condition) {
        //e.g. 队伍中风属性数量为5
        List<Chara> tmpChooser = GameBoard.getAlly();
        if(condition.startsWith("队伍中")) {
            return parseMatcher(tmpChooser.stream(), condition.substring(3));
        }
        throw new RuntimeException("未支持");
    }

    private static Boolean parseMatcher(Stream<Chara> stream, String condition) {
        System.out.println("parse:" + condition);
        //e.g. 风属性数量为5
        if(condition.startsWith("风属性")) {
            stream = stream.filter(chara -> chara.getElement().equals(Element.风属性));
            return parseMatcher(stream, condition.substring(3));
        }
        if(condition.startsWith("有ID为")) {
            Pattern pattern = Pattern.compile(
                    "有ID为(?<id>\\d+)");
            Matcher matcher = pattern.matcher(condition);
            matcher.find();
            return stream.anyMatch(chara -> chara.getCharaId() == Integer.parseInt(matcher.group("id")));
        }
        if(condition.startsWith("数量为")) {
            Pattern pattern = Pattern.compile(
                    "数量为(?<amount>\\d+)");
            Matcher matcher = pattern.matcher(condition);
            matcher.find();
            return stream.count() == Integer.parseInt(matcher.group("amount"));
        }
        throw new RuntimeException("未受支持");
    }

    private static void parseSkill(ImportedChara chara, WhenBuilder tempSkill, String effect, List<Supplier<Boolean>> switchChecker) {
        System.out.println("parse:" + effect);
        if (effect.contains(",")) {
            int index = effect.indexOf(",");
            String firstPart = effect.substring(0, index);
            String lastPart = effect.substring(index + 1);
            parseSkill(chara, tempSkill.act(parseAction(chara, firstPart, switchChecker)).and(), lastPart, Collections.emptyList());
            return;
        } else {
            tempSkill.act(parseAction(chara, effect, switchChecker));
        }
        tempSkill.build();
        return;
    }

    private static Action parseAction(ImportedChara chara, String part, List<Supplier<Boolean>> switchChecker) {
        if (part.startsWith("对")) {
            return parseDamageAction(chara, part);
        }
        return parseBuffAction(chara, part, switchChecker);
    }

    //TODO
    private static Action parseBuffAction(ImportedChara chara, String part, List<Supplier<Boolean>> switchChecker) {
        System.out.println("buffParse:" + part);
        //e.g. 自身攻击力+20%
        Pattern pattern = Pattern.compile(
                "(?<target>(自身|目标|我方群体|敌方群体|ID|友方|队伍中.{3}|(?<ID>\\d+)))(?<buffType>.*)" +
                        "(?<incdec>[+-])((BUFF(?<buffId>\\d+))|(?<multi>\\d+))%?" +
                        "(\\(最多(?<maxlevel>\\d+)层\\))?"
                );
        Matcher matcher = pattern.matcher(part);
        matcher.find();
        List<Chara> target;
        String targetWord = matcher.group("target");
        switch (targetWord) {
            case "目标" -> {
                target = Collections.singletonList(GameBoard.getCurrentEnemy());
            }
            case "ID" -> {
                int id = Integer.parseInt(matcher.group("ID"));
                target = GameBoard.getAlly().stream().filter(c -> c.getCharaId() == id).collect(Collectors.toList());
                if(target.isEmpty()) {
                    System.out.println("未发现指定ID角色：" + id);
                }
            }
            case "自身" -> {
                target = Collections.singletonList(chara);
            }
            case "我方群体","友方" -> {
                target = GameBoard.getAlly();
            }
            default -> {
                if(targetWord.startsWith("队伍中")){
                    target = parseChooser(GameBoard.getAlly().stream(), targetWord.substring(3));
                    targetWord.substring(3);
                }
                else
                    throw new RuntimeException("还没做");
            }
        }
        BuffType buffType = Enum.valueOf(BuffType.class, matcher.group("buffType"));
        int symbol = matcher.group("incdec").equals("+") ? 1 : -1;
        double multi = Double.parseDouble(matcher.group("multi"))/100.0;
        BuffAction buff = BuffAction.create(chara, buffType)
                .to(target)
                .multi(symbol * multi)
                .lastedTurn(INFI)
                .name(buffType + matcher.group("incdec") + Double.parseDouble(matcher.group("multi")) + '%');
        if(switchChecker.size() != 0) {
            buff = buff.enabledCheck(switchChecker);
        }
        String maxlevel = matcher.group("maxlevel");
        if(maxlevel != null) {
            buff = buff.maxLevel(Integer.parseInt(maxlevel));
        }
        return buff.build();
    }

    private static List<Chara> parseChooser(Stream<Chara> stream, String substring) {
        if(substring.equals("风属性")) {
            return stream.filter(chara -> chara.getElement().equals(Element.风属性)).collect(Collectors.toList());
        }
        if(substring.equals("水属性")) {
            return stream.filter(chara -> chara.getElement().equals(Element.水属性)).collect(Collectors.toList());
        }
        if(substring.equals("暗属性") || substring.equals("闇属性")) {
            return stream.filter(chara -> chara.getElement().equals(Element.暗属性)).collect(Collectors.toList());
        }
        if(substring.equals("光属性")) {
            return stream.filter(chara -> chara.getElement().equals(Element.光属性)).collect(Collectors.toList());
        }
        if(substring.equals("火属性")) {
            return stream.filter(chara -> chara.getElement().equals(Element.火属性)).collect(Collectors.toList());
        }
        throw new RuntimeException("未支持的分类" + substring);
    }

    private static Action parseDamageAction(ImportedChara chara, String part) {
        System.out.println("normalAtkParse:" + part);
        Pattern pattern = Pattern.compile("对(?<target>(目标|群体))(?<multi>\\d+)%(?<type>(技能|普攻))伤害");
        Matcher matcher = pattern.matcher(part);
        matcher.find();
        DamageAction.DamageType type;
        Chara target;
        switch (matcher.group("target")) {
            case "目标" -> target = GameBoard.getCurrentEnemy();
            default -> throw new RuntimeException("不支持的target类型：" + matcher.group("target"));
        }
        type = switch (matcher.group("type")) {
            case "技能" -> DamageAction.DamageType.必杀伤害;
            case "普攻" -> DamageAction.DamageType.普通伤害;
            default -> throw new RuntimeException("不支持的伤害类型：" + matcher.group("type"));
        };
        return DamageAction.create(chara, type)
                .multi(Double.parseDouble(matcher.group("multi")) / 100)
                .to(target)
                .build();
    }
}
