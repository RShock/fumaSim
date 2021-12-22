package xiaor.excel;

import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.excel.vo.SkillExcelVo;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.Action;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.SelfTrigger;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.skillbuilder.trigger.TriggerBuilder;
import xiaor.skillbuilder.when.WhenBuilder;
import xiaor.tools.TriggerEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        WhenBuilder tempSkill = SkillBuilder.createNewSkill(chara, skillType).when(trigger);
        parseSkill(chara, tempSkill, vo.getEffect());
    }

    private static void parseSkill(ImportedChara chara, WhenBuilder tempSkill, String effect) {
        if (effect.contains(",")) {
            int index = effect.indexOf(",");
            String firstPart = effect.substring(0, index);
            String lastPart = effect.substring(index + 1);
            parseSkill(chara, tempSkill.act(parseAction(chara, firstPart)).and(), lastPart);
        } else {
            tempSkill.act(parseAction(chara, effect));
        }
        tempSkill.build();
        return;
    }

    private static Action parseAction(ImportedChara chara, String part) {
        if(part.startsWith("对")) {
            return parseDamageAction(chara, part);
        }
        if(part.startsWith("如果")) {
            return parseCase(chara, part);
        }
        return parseBuffAction(chara, part);
    }

    //TODO
    private static Action parseBuffAction(ImportedChara chara, String part) {
        return null;
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
        switch (matcher.group("target")){
            case "目标" ->
                target = GameBoard.getCurrentEnemy();
            default -> throw new RuntimeException("不支持的target类型："+matcher.group("target"));
        }
        type = switch (matcher.group("type")) {
            case "技能" -> DamageAction.DamageType.必杀伤害;
            case "普攻" -> DamageAction.DamageType.普通伤害;
            default -> throw new RuntimeException("不支持的伤害类型："+matcher.group("type"));
        };
        return DamageAction.create(chara, type)
                .multi(Double.parseDouble(matcher.group("multi"))/100)
                .to(target)
                .build();
    }
}
