package xiaor.charas;

import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.Element;
import xiaor.charas.ImportedChara;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static xiaor.Common.INFI;

public class SkillParser {
    public static void addSkill(ImportedChara chara, SkillExcelVo vo) {
        if (vo.getEffect().equals("没做")) return;
        SkillType skillType = vo.getSkillType();
        switch (skillType) {
            case 队长技 -> {
                if (!chara.isLeader()) return;
            }
            case 一星被动 -> {
                if (chara.getStar() < 1) return;
            }
            case 三星被动 -> {
                if (chara.getStar() < 3) return;
            }
            case 五星被动 -> {
                if (chara.getStar() < 5) return;
            }
            case 六潜被动 -> {
                if (!chara.is6()) return;
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
        if (skillString.startsWith("如果")) {
            //这个技能是条件触发型的，触发器触发后还需要额外的校验
            Pattern pattern = Pattern.compile(
                    "如果(?<condition>.*)则(?<skill>.*)");
            Matcher matcher = pattern.matcher(vo.getEffect());
            matcher.find();
            trigger.append(() -> parseCondition(matcher.group("condition")));
            skillString = matcher.group("skillString");
        }
        if (skillString.contains("获得技能")) {
            throw new RuntimeException("还没做");
        }
        WhenBuilder tempSkill = SkillBuilder.createNewSkill(chara, skillType).when(trigger);
        parseSkill(chara, tempSkill, skillString);
    }

    private static Boolean parseCondition(String condition) {
        //e.g. 队伍中风属性数量为5
        List<Chara> tmpChooser = GameBoard.getAlly();
        if(condition.startsWith("队伍中")) {
            return parseChooser(tmpChooser.stream(), condition.substring(3));
        }
        throw new RuntimeException("未支持");
    }

    private static Boolean parseChooser(Stream<Chara> stream, String condition) {
        //e.g. 风属性数量为5
        if(condition.startsWith("风属性")) {
            stream = stream.filter(chara -> chara.getElement().equals(Element.风属性));
            return parseChooser(stream, condition.substring(3));
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
                    "数量为(?<amout>\\d+)");
            Matcher matcher = pattern.matcher(condition);
            matcher.find();
            return stream.count() == Integer.parseInt(matcher.group("amount"));
        }
        throw new RuntimeException("未受支持");
    }

    private static void parseSkill(ImportedChara chara, WhenBuilder tempSkill, String effect) {
        if (effect.startsWith("如果")) {
        }
        if (effect.contains(",")) {
            int index = effect.indexOf(",");
            String firstPart = effect.substring(0, index);
            String lastPart = effect.substring(index + 1);
            parseSkill(chara, tempSkill.act(parseAction(chara, firstPart)).and(), lastPart);
            return;
        } else {
            tempSkill.act(parseAction(chara, effect));
        }
        tempSkill.build();
        return;
    }

    private static Action parseAction(ImportedChara chara, String part) {
        if (part.startsWith("对")) {
            return parseDamageAction(chara, part);
        }
        return parseBuffAction(chara, part);
    }

    //TODO
    private static Action parseBuffAction(ImportedChara chara, String part) {
        //e.g. 自身攻击力+20%
        Pattern pattern = Pattern.compile(
                "(?<target>(自身|目标|我方群体|敌方群体|ID(?<ID>\\d+)))(?<buffType>.*)(?<incdec>[+-])((BUFF(?<buffId>\\d+))|(?<multi>\\d+))%?");
        Matcher matcher = pattern.matcher(part);
        matcher.find();
        List<Chara> target = new ArrayList<>();
        switch (matcher.group("target")) {
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
            case "我方群体" -> {
                target = GameBoard.getAlly();
            }
            default -> throw new RuntimeException("还没做");
        }
        BuffType buffType = Enum.valueOf(BuffType.class, matcher.group("buffType"));
        int symbol = matcher.group("incdec").equals("+") ? 1 : -1;
        double multi = Double.parseDouble(matcher.group("multi"))/100.0;
        return BuffAction.create(chara, buffType)
                .to(target)
                .multi(symbol*multi)
                .lastedTurn(INFI)
                .name(buffType + matcher.group("incdec") + Double.parseDouble(matcher.group("multi")) + '%')
                .build();
    }

    private static Action parseCase(ImportedChara chara, String substring) {
        return null;
    }

    private static Action parseDamageAction(ImportedChara chara, String part) {
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
