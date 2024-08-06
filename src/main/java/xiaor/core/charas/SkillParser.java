package xiaor.core.charas;

import xiaor.core.GameBoard;
import xiaor.core.excel.ExcelCharaProvider;
import xiaor.core.excel.vo.SkillExcelVo;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.skillbuilder.SkillBuilder;
import xiaor.core.skillbuilder.SkillType;
import xiaor.core.skillbuilder.action.Action;
import xiaor.core.skillbuilder.action.BuffAction;
import xiaor.core.damageCal.DamageBase;
import xiaor.core.skillbuilder.skill.BuffType;
import xiaor.core.skillbuilder.action.DamageAction;
import xiaor.core.skillbuilder.skill.Skill;
import xiaor.core.skillbuilder.skill.buff.Buff;
import xiaor.core.skillbuilder.skill.buff.UniqueBuff;
import xiaor.core.skillbuilder.trigger.Trigger;
import xiaor.core.skillbuilder.when.WhenBuilder;
import xiaor.core.tools.GlobalDataManager;
import xiaor.core.tools.KeyEnum;
import xiaor.core.Tools;
import xiaor.core.trigger.TriggerEnum;
import xiaor.core.trigger.TriggerManager;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static xiaor.core.Common.INFINITY;

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
        return (currentTurn - b) % a == 0 && currentTurn >= b;
    }

    public void addSkill(int turn) {
        if (vo.getEffect().equals("没做")) return;
        SkillType skillType = vo.getSkillType();
        if (!checkSkillType(chara, skillType)) return;
        TriggerEnum triggerEnum = vo.getTrigger();
        String skillString = vo.getEffect();
        Trigger trigger = switch (triggerEnum) {
            case 游戏开始时, 被动光环, 回合结束, 游戏开始时2 -> Trigger.when(triggerEnum);
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
        Skill skill = parseSkill(tempSkill, skillString, 0).build(skillId);
        chara.addSkill(skill);
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
        Matcher matcher = Tools.find(condition, "^(?<target>.*)(?<checker>(有ID为|数量为|数量大于|检查).*)$");
        String checker = matcher.group("checker");

        String target1 = matcher.group("target");
        List<Chara> target = parseChooser(target1);
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

        if (checker.startsWith("检查")) {
            Matcher matcher1 = Tools.find(checker, "检查(?<buffType>.*)大于(?<amount>\\d+)");
            String type = matcher1.group("buffType");
            BuffType buffType = Enum.valueOf(BuffType.class, type);
            int amount = Integer.parseInt(matcher1.group("amount"));
            //todo 查询角色buff层数
            double buffAmount = TriggerManager.queryBuff(buffType, target.get(0)).map(Buff::getMulti).orElse(0.0);
            return buffAmount >= amount;

        }
        throw new RuntimeException(checker + "未受支持");
    }

    private WhenBuilder parseSkill(WhenBuilder tempSkill, String effect, int partCnt) {   //标记当前技能是当前字符串的第几个（以逗号分割）

        if (effect.contains(",")) {
            int index = effect.indexOf(",");
            String firstPart = effect.substring(0, index);
            String lastPart = effect.substring(index + 1);
            return parseSkill(
                    tempSkill.act(parseAction(firstPart, partCnt)).and(),
                    lastPart,
                    partCnt + 1);
        } else {
            tempSkill.act(parseAction(effect, partCnt));
        }
        return tempSkill;
    }

    private Action parseAction(String part, int partCnt) {
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
        if (part.matches("自身技能.*层")) {   //驯鹿技能：被攻击时，触发[使“油门当刹车踩”的我方全体必杀技伤害增加效果增加1层]的处理
            Matcher matcher = Tools.find(part, "自身技能(?<skillId>\\d+)\\+1层");
            String skillId = matcher.group("skillId");
            return Action.buildFreeAction(() -> {
                Optional<Skill> first = TriggerManager.getInstance().getSkills().stream()
                        .filter(skill -> skill instanceof UniqueBuff<?>)
                        .filter(skill -> ((UniqueBuff<?>) skill).getId().equals(skillId))
                        .findFirst();
                if (first.isEmpty()) {
                    Logger.INSTANCE.log(LogType.警告, "未找到指定buff" + skillId);
                }
                UniqueBuff buff = (UniqueBuff) first.get();
                buff.add(1);
                return true;
            });
        }
        if (part.matches(".*失去技能.*")) {
            Matcher matcher = Tools.find(part, "(?<target>(其他友方|自身|目标|敌方全体|ID\\d+|友方|队伍中.{3}|[a|e]?\\{.*}))" +
                    "失去技能(?<skillId>\\d+)");
            List<Chara> target = parseChooser(matcher.group("target"));
            String skillId = matcher.group("skillId");

            return Action.buildFreeAction(() -> {
                target.forEach(t -> {
                    t.skills.forEach(skill -> {
                        if (skill.getId().equals(skillId)){
                            skill.disable();
                        }
                    });
                });
                return true;
            });
        }
        return parseBuffAction(part, partCnt);
    }

    //TODO
    private Action parseBuffAction(String part, int partCnt) {
        //e.g. 自身攻击力+20%
        Pattern pattern = Pattern.compile(
                "(?<target>(其他友方|自身|目标|敌方全体|ID\\d+|友方|队伍中.{3}|[a|e]?\\{.*}))" +
                        "(?<buffType>.*)" +
                        "(?<incDec>[+=-])" +
                        "((?<multi>\\d+(\\.\\d+)?)" +
                        "(?<isPercent>%?))" +
                        "(\\(最多(?<maxLevel>\\d+)层\\))?" +
                        "(\\((?<lastedTurn>\\d+)回合\\))?"
        );

        Matcher matcher = pattern.matcher(part);
        if (!matcher.find()) {
            throw new RuntimeException("%s匹配失败".formatted(part));
        }

        List<Chara> target = parseChooser(matcher.group("target"));
        BuffType buffType = Enum.valueOf(BuffType.class, matcher.group("buffType"));
        double multi = Double.parseDouble(matcher.group("multi"));
        if (!matcher.group("isPercent").isEmpty()) {    // 数字后面有百分号的，需要把数字除以100
            multi /= 100.0;
        }
        BuffAction buff;
        switch (matcher.group("incDec")) {          // +-为正常Buff, =为移除层数的buff
            case "-":
                multi *= -1;
            case "+", "":
                buff = BuffAction.create(chara, buffType)
                        .to(target)
                        .id(skillId + " " + partCnt)
                        .multi(multi)
                        .lastedTurn(INFINITY)
                        .name("%s %s%s%s%%".formatted(vo, buffType, matcher.group("incDec"), Double.parseDouble(matcher.group("multi"))));
                break;
            case "=":
                return Action.buildFreeAction(() -> {
                    TriggerManager.getInstance().getSkills().stream()
                            .filter(skill -> skill instanceof UniqueBuff<?>)
                            .map(skill -> (UniqueBuff<?>) skill)
                            .filter(ub -> (ub.getBuffType().equals(buffType) && target.contains(ub.getAcceptor())))         // 找到buff类型相同且角色正确的buff移除
                            .forEach(uniqueBuff -> uniqueBuff.currentLevel = 0);
                    return true;
                });
            default:
                throw new IllegalStateException("Unexpected value: " + matcher.group("incDec"));
        }

        if (!switchChecker.isEmpty()) {
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
        switch (substring) {
            case "目标" -> {
                return Collections.singletonList(GameBoard.getCurrentEnemy());
            }
            case "自身" -> {
                return Collections.singletonList(chara);
            }
            case "友方" -> {
                return GameBoard.getAlly();
            }
            case "敌方全体" -> {
                return GameBoard.getEnemy();
            }
            case "其他友方" -> {
                return GameBoard.getAlly().stream().filter(chara1 -> !chara1.equals(chara)).collect(Collectors.toList());
            }
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
        Matcher matcher = Tools.find(part, "对(?<target>.*?)(?<multi>\\d+(\\.\\d+)?)%(?<base>自身生命)?(?<type>(技能|普攻|必杀触发|普攻触发))伤害((?<times>\\d+)次)?");
        DamageAction.DamageType type;
        List<Chara> target = parseChooser(matcher.group("target"));
        type = switch (matcher.group("type")) {
            case "技能" -> DamageAction.DamageType.必杀伤害;
            case "普攻" -> DamageAction.DamageType.普通伤害;
            case "普攻触发" -> DamageAction.DamageType.普攻触发伤害;
            case "必杀触发" -> DamageAction.DamageType.必杀触发伤害;
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
