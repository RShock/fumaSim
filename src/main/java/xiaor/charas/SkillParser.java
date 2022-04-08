package xiaor.charas;

import xiaor.GameBoard;
import xiaor.excel.vo.SkillExcelVo;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.Action;
import xiaor.skillbuilder.action.ActionBuilder;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skill.BuffType;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.SelfTrigger;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.skillbuilder.trigger.TriggerBuilder;
import xiaor.skillbuilder.when.WhenBuilder;
import xiaor.tools.GlobalDataManager;
import xiaor.tools.KeyEnum;
import xiaor.tools.TriggerEnum;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static xiaor.Common.INFI;

public class SkillParser {

    public static void addSkill(Chara chara, List<SkillExcelVo> vos, int skillId) {
        addSkill(chara, vos, skillId, 0);
    }

    public static void addSkill(Chara chara, List<SkillExcelVo> vos, int skillId, int turn) {
        SkillExcelVo vo = findSkillVoBySkillId(vos, skillId);
        System.out.println("正在解析" + vo.getSkillId());
        if (vo.getEffect().equals("没做")) return;
        SkillType skillType = vo.getSkillType();
        if (!checkSkillType(chara, skillType)) return;
        TriggerEnum triggerEnum = vo.getTrigger();
        String skillString = vo.getEffect();
        Trigger trigger = switch (triggerEnum) {
            case 游戏开始时, 被动光环, 回合结束 -> TriggerBuilder.when(triggerEnum);
            case 回合开始 -> {
                Pattern pattern = Pattern.compile("(?<turnsA>\\d+)N(\\+(?<turnsB>\\d+))?回合触发:(?<effect>.*)");
                Matcher matcher = pattern.matcher(skillString);
                matcher.find();
                int a = Integer.parseInt(matcher.group("turnsA"));
                int b = Integer.parseInt(Optional.of(matcher.group("turnsB")).orElse("0"));
                skillString = matcher.group("effect");
                yield TriggerBuilder.when(triggerEnum,() -> GlobalDataManager.getIntData(KeyEnum.GAMETURN) % a == b);
            }
            default -> SelfTrigger.act(chara, triggerEnum);
        };
        if (skillString.matches(".+获得技能.+")) {
            Pattern pattern = Pattern.compile("(?<target>.+)获得技能(?<skill>\\d+)(\\((?<turn>\\d+)回合\\))?");
            Matcher matcher = pattern.matcher(skillString);
            matcher.find();
            List<Chara> target = parseChooser(chara, matcher.group("target"));
            //这个技能是该角色给别人或者自己的的 例如幼精给精灵王
            target.forEach(chara1 -> {
                SkillExcelVo givenVo = findSkillVoBySkillId(vos, Integer.parseInt(matcher.group("skill")));
                if(givenVo.getSkillType() != SkillType.动态技能) {
                    throw new RuntimeException("技能"+ givenVo.getSkillId() + "不是动态的");
                }
                SkillBuilder.createNewSkill(chara, skillType).when(trigger)
                        .act(ActionBuilder.getFreeAction(() -> {
                            if(matcher.group("turn") != null) {
                                addSkill(chara1, vos, givenVo.getSkillId(), Integer.parseInt(matcher.group("turn")));
                                return true;
                            }
                            addSkill(chara1, vos, givenVo.getSkillId());
                            return true;
                        })).build();
            });
            return;
        }
        List<Supplier<Boolean>> switchChecker = new ArrayList<>();
        WhenBuilder tempSkill = SkillBuilder.createNewSkill(chara, skillType).when(trigger);
        if (turn != 0) {
            tempSkill.lastedTurn(turn);
        }
        if (skillString.startsWith("如果")) { //这个技能是激活型的，需要额外的检验条件，如果没激活会提示未激活
            skillString = parseExtraCondition(chara, vo, switchChecker);
        }
        parseSkill(chara, tempSkill, skillString, switchChecker).build();
    }

    private static SkillExcelVo findSkillVoBySkillId(List<SkillExcelVo> vos, int skillId) {
        return vos.stream().filter(skillExcelVo -> skillId == skillExcelVo.getSkillId()).findFirst()
                .orElseThrow(() -> new RuntimeException("不存在的Skill Id"));
    }

    private static String parseExtraCondition(Chara curChara, SkillExcelVo vo, List<Supplier<Boolean>> switchChecker) {
        String skillString;
        //这个技能是条件触发型的，触发器触发后还需要额外的校验
        Pattern pattern = Pattern.compile("如果(?<condition>.*)则(?<skill>.*)");
        Matcher matcher = pattern.matcher(vo.getEffect());
        matcher.find();
        switchChecker.add(() -> parseCondition(curChara, matcher.group("condition")));
        skillString = matcher.group("skill");
        return skillString;
    }

    private static boolean checkSkillType(Chara chara, SkillType skillType) {
        switch (skillType) {
            case 队长技 -> {
                if (!chara.isLeader()) return false;
            }
            case 一星被动 -> {
                if (chara.getStar() < 1) {
                    System.out.println(chara + "没1星，1星技能不触发，是否忘记填写了星数？");
                    return false;
                }
            }
            case 三星被动 -> {
                if (chara.getStar() < 3) {
                    System.out.println(chara + "没3星，3星技能不触发，是否忘记填写了星数？");
                    return false;
                }
            }
            case 五星被动 -> {
                if (chara.getStar() < 5) {
                    System.out.println(chara + "没5星，5星技能不触发");
                    return false;
                }
            }
            case 六潜被动 -> {
                if (!chara.is6()) {
                    System.out.println(chara + "没6潜，6潜技能不触发，是否忘记填写了潜力？");
                    return false;
                }
            }
            case 十二潜被动 -> {
                if (!chara.is12()) return false;
            }
            case 必杀技1绊 -> {
                if (chara.getSkillLevel() != 1) return false;
            }
            case 必杀技2绊 -> {
                if (chara.getSkillLevel() != 2) return false;
            }
            case 必杀技3绊 -> {
                if (chara.getSkillLevel() != 3) return false;
            }
            case 必杀技4绊 -> {
                if (chara.getSkillLevel() != 4) return false;
            }
            case 必杀技5绊 -> {
                if (chara.getSkillLevel() != 5) return false;
            }
            case 动态技能 -> {
                return true;
            }
            case 普攻 -> {
            }
        }
        return true;
    }

    private static Boolean parseCondition(Chara curChara, String condition) {
//        System.out.println("parse:" + condition);
        Pattern pattern = Pattern.compile(
                "^(?<target>.*)(?<checker>(有ID为|数量为).*)$"
        );
        Matcher matcher = pattern.matcher(condition);
        matcher.find();
        List<Chara> target = parseChooser(curChara, matcher.group("target"));
        String checker = matcher.group("checker");
        if (checker.startsWith("有ID为")) {
            Pattern pattern1 = Pattern.compile("有ID为(?<id>\\d+)");
            Matcher matcher1 = pattern1.matcher(checker);
            matcher1.find();
            return target.stream().anyMatch(chara -> chara.getCharaId() == Integer.parseInt(matcher1.group("id")));
        }
        if (checker.startsWith("数量为")) {
            Pattern pattern1 = Pattern.compile("数量为(?<amount>\\d+)");
            Matcher matcher1 = pattern1.matcher(checker);
            matcher1.find();
            return target.size() == Integer.parseInt(matcher1.group("amount"));
        }
        throw new RuntimeException(checker + "未受支持");
    }

    private static WhenBuilder parseSkill(Chara chara, WhenBuilder tempSkill, String effect, List<Supplier<Boolean>> switchChecker) {
//        System.out.println("parse:" + effect);
        if (effect.contains(",")) {
            int index = effect.indexOf(",");
            String firstPart = effect.substring(0, index);
            String lastPart = effect.substring(index + 1);
            return parseSkill(chara, tempSkill.act(parseAction(chara, firstPart, switchChecker)).and(), lastPart, switchChecker);
        } else {
            tempSkill.act(parseAction(chara, effect, switchChecker));
        }
        return tempSkill;
    }

    private static Action parseAction(Chara chara, String part, List<Supplier<Boolean>> switchChecker) {
        if (part.startsWith("对")) {
            return parseDamageAction(chara, part);
        }
        return parseBuffAction(chara, part, switchChecker);
    }

    //TODO
    private static Action parseBuffAction(Chara chara, String part, List<Supplier<Boolean>> switchChecker) {
        System.out.println("buffParse:" + part);
        //e.g. 自身攻击力+20%
        Pattern pattern = Pattern.compile(
        "(?<target>(自身|目标|我方群体|敌方群体|ID\\d+|友方|队伍中.{3}|))" +
                "(?<buffType>.*)" +
                "(?<incdec>[+-])" +
                "(" +
                "(BUFF(?<buffId>\\d+))|" +
                "((?<multi>\\d+(\\.\\d+)?)%?)" +
                ")" +
                "(\\(最多(?<maxlevel>\\d+)层\\))?" +
                "(\\((?<lastedTurn>\\d+)回合\\))?"
                );

        Matcher matcher = pattern.matcher(part);
        matcher.find();
        List<Chara> target = parseChooser(chara, matcher.group("target"));
        BuffType buffType = Enum.valueOf(BuffType.class, matcher.group("buffType"));
        int symbol = matcher.group("incdec").equals("+") ? 1 : -1;
        double multi = Double.parseDouble(matcher.group("multi")) / 100.0;
        BuffAction buff = BuffAction.create(chara, buffType)
                .to(target)
                .multi(symbol * multi)
                .lastedTurn(INFI)
                .name(buffType + matcher.group("incdec") + Double.parseDouble(matcher.group("multi")) + '%');
        if (switchChecker.size() != 0) {
            buff = buff.enabledCheck(switchChecker);
        }
        String maxlevel = matcher.group("maxlevel");
        if (maxlevel != null) {
            buff = buff.maxLevel(Integer.parseInt(maxlevel));
        }
        String lastedTurn = matcher.group("lastedTurn");
        if (lastedTurn != null) {
            buff = buff.lastedTurn(Integer.parseInt(lastedTurn));
        }
        return buff.build();
    }

    //选择
    private static List<Chara> parseChooser(Chara curChara, String substring) {
        Stream<Chara> stream = GameBoard.getAlly().stream();
        if (substring.startsWith("队伍中")) {
            substring = substring.substring(3);
            if (substring.equals("风属性")) {
                return stream.filter(chara -> chara.getElement().equals(Element.风属性)).collect(Collectors.toList());
            }
            if (substring.equals("水属性")) {
                return stream.filter(chara -> chara.getElement().equals(Element.水属性)).collect(Collectors.toList());
            }
            if (substring.equals("暗属性") || substring.equals("闇属性")) {
                return stream.filter(chara -> chara.getElement().equals(Element.暗属性)).collect(Collectors.toList());
            }
            if (substring.equals("光属性")) {
                return stream.filter(chara -> chara.getElement().equals(Element.光属性)).collect(Collectors.toList());
            }
            if (substring.equals("火属性")) {
                return stream.filter(chara -> chara.getElement().equals(Element.火属性)).collect(Collectors.toList());
            }
        }
        if (substring.matches("\\{\\d+(_\\d+)*\\}")) {  // e.g. {1_2_3}
            return Arrays.stream(substring.substring(1, substring.length()-1).split("_"))
                    .map(Integer::parseInt)
                    .map(GameBoard::selectTarget)
                    .collect(Collectors.toList());
        }
        if (substring.equals("目标")) {
            return Collections.singletonList(GameBoard.getCurrentEnemy());
        }
        if (substring.equals("自身")) {
            return Collections.singletonList(curChara);
        }
        if (substring.equals("友方")) {
            return GameBoard.getAlly();
        }
        if (substring.equals("群体")) {
            return GameBoard.getEnemy();
        }
        if (substring.startsWith("ID")) {
            int id = Integer.parseInt(substring.substring(2));
            List<Chara> target = GameBoard.getAlly().stream().filter(c -> c.getCharaId() == id).collect(Collectors.toList());
            if (target.isEmpty()) {
                System.out.println("未发现指定ID角色：" + id);
            }
            return target;
        }
        throw new RuntimeException("未支持的分类" + substring);
    }

    private static Action parseDamageAction(Chara chara, String part) {
        System.out.println("normalAtkParse:" + part);
        Pattern pattern = Pattern.compile("对(?<target>.*?)(?<multi>\\d+)%(?<type>(技能|普攻))伤害");
        Matcher matcher = pattern.matcher(part);
        matcher.find();
        DamageAction.DamageType type;
        List<Chara> target = parseChooser(chara, matcher.group("target"));
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
