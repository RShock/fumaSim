package xiaor.charas;

import xiaor.GameBoard;
import xiaor.excel.ExcelCharaProvider;
import xiaor.excel.vo.SkillExcelVo;
import xiaor.logger.LogType;
import xiaor.logger.Logger;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.Action;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.damageCal.DamageBase;
import xiaor.skillbuilder.skill.BuffType;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.skillbuilder.when.WhenBuilder;
import xiaor.tools.GlobalDataManager;
import xiaor.tools.KeyEnum;
import xiaor.tools.Tools;
import xiaor.trigger.TriggerEnum;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static xiaor.Common.INFINITY;

public class SkillParser {

    final Chara chara;
    final List<SkillExcelVo> vos;
    final int skillId;
    final SkillExcelVo vo;

    final List<Supplier<Boolean>> switchChecker = new ArrayList<>();

    public SkillParser(Chara chara, List<SkillExcelVo> vos, int skillId) {
        this.chara = chara;
        this.vos = vos;
        this.skillId = skillId;
        this.vo = findSkillVoBySkillId(skillId);
    }

    public static void addSkill(Chara chara, List<SkillExcelVo> vos, int skillId) {
        new SkillParser(chara, vos, skillId).addSkill(0);
    }

    public static void addSkill(Chara chara, List<SkillExcelVo> vos, int skillId, int turn) {
        new SkillParser(chara, vos, skillId).addSkill(turn);
    }

    private static boolean turnCheck(int currentTurn, int a, int b) {
        return (currentTurn - b) % a == 0 && currentTurn  >= b;
    }

    public void addSkill(int turn) {
        if (vo.getEffect().equals("没做")) return;
        SkillType skillType = vo.getSkillType();
        if (!checkSkillType(chara, skillType)) return;
        TriggerEnum triggerEnum = vo.getTrigger();
        String skillString = vo.getEffect();
        Trigger trigger = switch (triggerEnum) {
            case 游戏开始时, 被动光环, 回合结束 -> Trigger.when(triggerEnum);
            case 回合开始时 -> {
                Matcher matcher = Tools.find(skillString, "(?<turnsA>\\d+)N(\\+(?<turnsB>\\d+))?回合触发:(?<effect>.*)");
                int a = Integer.parseInt(matcher.group("turnsA"));
                int b = Integer.parseInt(Optional.of(matcher.group("turnsB")).orElse("0"));
                skillString = matcher.group("effect");
                yield Trigger.when(triggerEnum, () -> turnCheck(GlobalDataManager.getIntData(KeyEnum.GAME_TURN), a, b));
            }
            default -> Trigger.selfAct(chara, triggerEnum);
        };
        WhenBuilder tempSkill = SkillBuilder.createNewSkill(vo.toString()).when(trigger);
        if (turn != 0) {
            tempSkill.lastedTurn(turn);
        }
        if (skillString.startsWith("如果")) { //这个技能是激活型的，需要额外的检验条件，如果没激活会提示未激活
            skillString = parseExtraCondition();
        }
        parseSkill(tempSkill, skillString).build();
    }

    private SkillExcelVo findSkillVoBySkillId(Integer skillId) {
        return vos.stream().filter(skillExcelVo -> skillId == skillExcelVo.getSkillId()).findFirst()
                .orElseThrow(() -> new RuntimeException("不存在的Skill Id" + skillId));
    }

    private String parseExtraCondition() {
        String skillString;
        //这个技能是条件触发型的，触发器触发后还需要额外的校验
        Matcher matcher = Tools.find(vo.getEffect(), "如果(?<condition>.*)则(?<skill>.*)");
        switchChecker.add(() -> parseCondition(matcher.group("condition")));
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
                    Logger.INSTANCE.log(LogType.其他, chara + "没1星，1星技能不触发，是否忘记填写了星数？");
                    return false;
                }
            }
            case 三星被动 -> {
                if (chara.getStar() < 3) {
                    Logger.INSTANCE.log(LogType.其他, chara + "没3星，3星技能不触发，是否忘记填写了星数？");
                    return false;
                }
            }
            case 五星被动 -> {
                if (chara.getStar() < 5) {
                    Logger.INSTANCE.log(LogType.其他, chara + "没5星，5星技能不触发");
                    return false;
                }
            }
            case 六潜被动 -> {
                if (!chara.is6()) {
                    Logger.INSTANCE.log(LogType.其他, chara + "没6潜，6潜技能不触发，是否忘记填写了潜力？");
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
            case 普攻, 防御 -> {
            }
        }
        return true;
    }

    private Boolean parseCondition(String condition) {
//        System.out.println("parse:" + condition);
        Matcher matcher = Tools.find(condition, "^(?<target>.*)(?<checker>(有ID为|数量为|数量大于).*)$");
        List<Chara> target = parseChooser(matcher.group("target"));
        String checker = matcher.group("checker");
        if (checker.startsWith("有ID为")) {
            Matcher matcher1 = Tools.find(checker, "有ID为(?<id>\\d+)");
            return target.stream().anyMatch(chara -> chara.getCharaId() == Integer.parseInt(matcher1.group("id")));
        }
        if (checker.startsWith("数量为")) {
            Matcher matcher1 = Tools.find(checker, "数量为(?<amount>\\d+)");
            return target.size() == Integer.parseInt(matcher1.group("amount"));
        }
        if (checker.startsWith("数量大于")) {
            Matcher matcher1 = Tools.find(checker, "数量大于(?<amount>\\d+)");
            return target.size() >= Integer.parseInt(matcher1.group("amount"));
        }
        throw new RuntimeException(checker + "未受支持");
    }

    private WhenBuilder parseSkill(WhenBuilder tempSkill, String effect) {
        if (effect.contains(",")) {
            int index = effect.indexOf(",");
            String firstPart = effect.substring(0, index);
            String lastPart = effect.substring(index + 1);
            return parseSkill(
                    tempSkill.act(parseAction(firstPart)).and(),
                    lastPart);
        } else {
            tempSkill.act(parseAction(effect));
        }
        return tempSkill;
    }

    private Action parseAction(String part) {
        if (part.startsWith("对")) {
            return parseDamageAction(part);
        }
        if (part.matches((".+获得技能.+"))) {
            Matcher matcher = Tools.find(part, "(?<target>.+)获得技能(?<skill>\\d+)(\\((?<turn>\\d+)回合\\))?");
            List<Chara> target = parseChooser(matcher.group("target"));
            //这个技能是该角色给别人或者自己的的 例如幼精给精灵王

            SkillExcelVo givenVo = findSkillVoBySkillId(Integer.parseInt(matcher.group("skill")));
            if (givenVo.getSkillType() != SkillType.动态技能) {
                throw new RuntimeException("技能" + givenVo.getSkillId() + "不是动态的");
            }
            return Action.buildFreeAction(() -> {
                target.forEach(chara1 -> {
                    if (!switchChecker.stream().allMatch(Supplier::get)) {
                        return; //获得技能也可能有附加条件
                    }
                    if (matcher.group("turn") != null) {
                        addSkill(chara1, vos, givenVo.getSkillId(), Integer.parseInt(matcher.group("turn")));
                    } else {
                        addSkill(chara1, vos, givenVo.getSkillId());
                    }
                });
                return true;
            });
        }
        return parseBuffAction(part);
    }

    //TODO
    private Action parseBuffAction(String part) {
        //e.g. 自身攻击力+20%
        Pattern pattern = Pattern.compile(
                "(?<target>(其他友方|自身|目标|敌方全体|ID\\d+|友方|队伍中.{3}|[a|e]?\\{.*}))" +
                        "(?<buffType>.*)" +
                        "(?<incDec>[+-])" +
                        "(" +
                        "(BUFF(?<buffId>\\d+))|" +
                        "((?<multi>\\d+(\\.\\d+)?)%?)" +
                        ")" +
                        "(\\(最多(?<maxLevel>\\d+)层\\))?" +
                        "(\\((?<lastedTurn>\\d+)回合\\))?"
        );

        Matcher matcher = pattern.matcher(part);
        if (!matcher.find()) {
            throw new RuntimeException("%s匹配失败".formatted(part));
        }
        List<Chara> target = parseChooser(matcher.group("target"));
        BuffType buffType = Enum.valueOf(BuffType.class, matcher.group("buffType"));
        int symbol = matcher.group("incDec").equals("-") ? -1 : 1;  //兼顾了null的情况
        double multi = Double.parseDouble(matcher.group("multi")) / 100.0;
        BuffAction buff = BuffAction.create(chara, buffType)
                .to(target)
                .multi(symbol * multi)
                .lastedTurn(INFINITY)
                .name("%s %s%s%s%%".formatted(vo, buffType, matcher.group("incDec"), Double.parseDouble(matcher.group("multi"))));
        if (switchChecker.size() != 0) {
            buff = buff.enabledCheck(switchChecker);
        }
        String maxLevel = matcher.group("maxLevel");
        if (maxLevel != null) {
            buff = buff.maxLevel(Integer.parseInt(maxLevel));
        }
        String lastedTurn = matcher.group("lastedTurn");
        if (lastedTurn != null) {
            buff = buff.lastedTurn(Integer.parseInt(lastedTurn));
        }
        return buff.build();
    }

    //选择
    private List<Chara> parseChooser(String substring) {
        Stream<Chara> stream = GameBoard.getAlly().stream();
        if (substring.startsWith("队伍中")) {
            String finalSubstring = substring.substring(3);
            return switch (finalSubstring) {
                case "风属性", "水属性", "暗属性", "光属性", "火属性" ->
                        stream.filter(chara -> chara.getElement().equals(Enum.valueOf(Element.class, finalSubstring)))
                                .collect(Collectors.toList());
                case "攻击者", "治疗者", "妨碍者", "辅助者", "守护者" ->
                        stream.filter(chara -> chara.getRole().equals(Enum.valueOf(Role.class, finalSubstring)))
                                .collect(Collectors.toList());
                default -> throw new RuntimeException("不支持的分类" + finalSubstring);
            };
        }
        if (substring.matches("[a|e]\\{\\d+(_\\d+)*}")) {  // e.g. e{1_2_3}
            char beginSubString = substring.charAt(0);
            String[] pos = substring.substring(2, substring.length() - 1).split("_");
            return switch (beginSubString) {
                case 'a' -> Arrays.stream(pos)
                        .map(Integer::parseInt)
                        .map(GameBoard::selectAlly)
                        .collect(Collectors.toList());
                case 'e' -> Arrays.stream(pos)
                        .map(Integer::parseInt)
                        .map(GameBoard::selectTarget)
                        .collect(Collectors.toList());
                default -> throw new RuntimeException("不支持的分类" + beginSubString);
            };
        }
        if (substring.matches("\\{.*}")) {     //e.g. {精灵王 塞露西亚}
            String finalSubstring = substring.substring(1, substring.length() - 1);
            return GameBoard.getAlly().stream().filter(chara ->
                            chara.getCharaId() == ExcelCharaProvider.searchIdByCharaName(finalSubstring))
                    .collect(Collectors.toList());
        }
        if (substring.equals("目标")) {
            return Collections.singletonList(GameBoard.getCurrentEnemy());
        }
        if (substring.equals("自身")) {
            return Collections.singletonList(chara);
        }
        if (substring.equals("友方")) {
            return GameBoard.getAlly();
        }
        if (substring.equals("敌方全体")) {
            return GameBoard.getEnemy();
        }
        if (substring.equals("其他友方")) {
            return GameBoard.getAlly().stream().filter(chara1 -> !chara1.equals(chara)).collect(Collectors.toList());
        }
        if (substring.startsWith("ID")) {
            int id = Integer.parseInt(substring.substring(2));
            List<Chara> target = GameBoard.getAlly().stream().filter(c -> c.getCharaId() == id).collect(Collectors.toList());
            if (target.isEmpty()) {
                Logger.INSTANCE.log(LogType.其他, "未发现指定ID角色：" + id);
            }
            return target;
        }
        throw new RuntimeException("未支持的分类" + substring);
    }

    private Action parseDamageAction(String part) {
//        System.out.println("normalAtkParse:" + part);
        Matcher matcher = Tools.find(part, "对(?<target>.*?)(?<multi>\\d+(\\.\\d+)?)%(?<base>自身生命)?(?<type>(技能|普攻))伤害((?<times>\\d+)次)?");
        DamageAction.DamageType type;
        List<Chara> target = parseChooser(matcher.group("target"));
        type = switch (matcher.group("type")) {
            case "技能" -> DamageAction.DamageType.必杀伤害;
            case "普攻" -> DamageAction.DamageType.普通伤害;
            default -> throw new RuntimeException("不支持的伤害类型：" + matcher.group("type"));
        };
        int times = matcher.group("times") == null ? 1 : Integer.parseInt(matcher.group("times"));
        return DamageAction.create(type)
                .multi(Double.parseDouble(matcher.group("multi")) / 100)
                .to(target)
                .times(times)
                .damageBase(matcher.group("base") == null ? DamageBase.攻击 :
                        DamageBase.生命)
                .build();
    }
}
