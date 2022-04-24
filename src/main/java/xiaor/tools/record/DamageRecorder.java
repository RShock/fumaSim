package xiaor.tools.record;

import lombok.Getter;
import xiaor.charas.Chara;
import xiaor.skillbuilder.skill.BaseSkill;
import xiaor.skillbuilder.skill.Skill;
import xiaor.tools.GlobalDataManager;
import xiaor.tools.KeyEnum;
import xiaor.tools.Tools;
import xiaor.trigger.TriggerEnum;
import xiaor.trigger.TriggerManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static xiaor.Common.INFINITY;

/**
 * 统计者，暂时用来统计所有的伤害
 */
@Getter
public class DamageRecorder {

    List<DamageRecord> records;

    private static final DamageRecorder damageRecorder = new DamageRecorder();

    public static DamageRecorder getInstance() {
        return damageRecorder;
    }

    private DamageRecorder() {
        records = new ArrayList<>();
    }

    public static void init() {
        Skill skill = BaseSkill.builder().name("【系统规则】伤害记录器记录伤害").trigger(TriggerEnum.造成伤害)
                .time(INFINITY)
                .check(pack -> true)
                .cast(pack -> {
                    DamageRecord result = pack.getResult();
                    result.setActionId(GlobalDataManager.getIntData(KeyEnum.ACTION_ID));
                    DamageRecorder.addDamageRecord(result);
                }).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("【系统规则】伤害记录器切换到下一次攻击").trigger(TriggerEnum.释放行动)
                .time(INFINITY)
                .check(pack -> true)
                .cast(pack -> {
                    GlobalDataManager.incIntData(KeyEnum.ACTION_ID);
                }).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("【系统规则】伤害记录器初始化").trigger(TriggerEnum.游戏开始时)
                .time(INFINITY)
                .check(pack -> true)
                .cast(pack -> GlobalDataManager.putIntData(KeyEnum.ACTION_ID, -1)).build();
        TriggerManager.registerSkill(skill);
    }

    public static void addDamageRecord(DamageRecord record) {
        getInstance().records.add(record);
    }

    public List<Integer> exportDamagePerAction() {
        return getInstance().records.stream().collect(Collectors.groupingBy(DamageRecord::getActionId))
                .entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(set -> set.getValue().stream().mapToInt(DamageRecord::getNum).sum())
                .collect(Collectors.toList());
    }

    public void clear() {
        getInstance().records.clear();
    }

    public void countDamage() {
        Map<Chara, List<DamageRecord>> collect = records.stream().collect(Collectors.groupingBy(DamageRecord::getCaster));
        collect.forEach((key, value) -> {
            int sum = value.stream().mapToInt(DamageRecord::getNum).sum();
            Tools.log("%s造成了%d点伤害%n".formatted(key, sum));
        });
    }

}
