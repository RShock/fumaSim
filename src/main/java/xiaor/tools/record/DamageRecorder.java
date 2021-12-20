package xiaor.tools.record;

import lombok.Getter;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.skill.BaseSkill;
import xiaor.skill.Skill;
import xiaor.tools.TriggerEnum;
import xiaor.tools.TriggerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static xiaor.Common.INFI;

/**
 * 统计者，暂时用来统计所有的伤害
 */
@Getter
public class DamageRecorder {

    List<DamageRecord> records;

    private static DamageRecorder damageRecorder = new DamageRecorder();

    public static DamageRecorder getInstance() {
        return damageRecorder;
    }

    private DamageRecorder() {
        records = new ArrayList<>();
    }

    public static void init() {
        Skill skill = BaseSkill.builder().name("【系统级规则】伤害记录器").trigger(TriggerEnum.造成伤害)
                .time(INFI)
                .check(pack -> true)
                .cast(pack -> {
                    DamageRecorder.addDamageRecord(pack.getResult());
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);
    }

    public static void addDamageRecord(DamageRecord record) {
        getInstance().records.add(record);
    }

    public void clear() {
        getInstance().records.clear();
    }

    public void countDamage() {
        Map<Chara, List<DamageRecord>> collect = records.stream().collect(Collectors.groupingBy(record -> record.caster));
        collect.forEach((key, value) -> {
            int sum = value.stream().mapToInt(DamageRecord::num).sum();
            System.out.printf("%s造成了%d点伤害%n", key, sum);
        });
    }

    public record DamageRecord(TriggerEnum type, String detail, Chara caster, Chara accepter, int num) {}
}