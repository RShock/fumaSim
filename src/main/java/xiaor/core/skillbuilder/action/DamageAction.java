package xiaor.core.skillbuilder.action;

import xiaor.core.charas.Chara;
import xiaor.core.damageCal.DamageCal;
import xiaor.core.damageCal.DamageBase;
import xiaor.core.msgpack.MessagePack;

import java.util.List;

public class DamageAction {

    private List<Chara> target;

    private DamageType damageType;

    public double multi;    //伤害倍率

    public int times;   //多次伤害
    public long dotDamage;  //流血伤害专用

    public DamageBase baseType = DamageBase.攻击;

    public DamageAction damageBase(DamageBase base) {
        this.baseType = base;
        return this;
    }

    public enum DamageType {
        普通伤害,
        必杀伤害,
        流血伤害,    //dot,无来源伤害
        普攻触发伤害,
        必杀触发伤害
    }

    public static DamageAction create(DamageType damageType) {
        DamageAction damageAction = new DamageAction();
        damageAction.damageType = damageType;
        damageAction.multi = 1.0;   //默认倍率1.0
        return damageAction;
    }

    public DamageAction multi(Double multi) {
        this.multi = multi;
        return this;
    }

    public DamageAction times(int times) {
        this.times = times;
        return this;
    }

    public DamageAction to(List<Chara> enemy) {
        this.target = enemy;
        return this;
    }

    public DamageAction dotDamage(long dotDamage) {
        this.dotDamage = dotDamage;
        return this;
    }

    public Action build() {
        Action action = new Action();
        switch (damageType) {
            case 必杀伤害 -> action.setAction(pack -> {
                if (target != null && !target.isEmpty()) {
                    ((MessagePack)pack).acceptors = target;
                }
                new DamageCal(((MessagePack)pack)).skillAttack(multi, baseType, times);
            });
            case 普通伤害 -> action.setAction(pack -> {
                if (target != null && !target.isEmpty()) {
                    ((MessagePack)pack).acceptors = target;
                }
                new DamageCal(((MessagePack)pack)).normalAttack(multi, baseType, times);
            });
            case 流血伤害 -> action.setAction(pack -> {
                if (target != null && !target.isEmpty()) {
                    ((MessagePack)pack).acceptors = target;
                }
                new DamageCal(((MessagePack)pack)).dotAttack(multi, baseType);
            });
            case 普攻触发伤害 -> action.setAction(pack -> {
                if (target != null && !target.isEmpty()) {
                    ((MessagePack)pack).acceptors = target;
                }
                new DamageCal(((MessagePack)pack)).normalAddAttack(multi, baseType, times);
            });
            case 必杀触发伤害 -> action.setAction(pack -> {
                if (target != null && !target.isEmpty()) {
                    ((MessagePack)pack).acceptors = target;
                }
                new DamageCal(((MessagePack)pack)).skillAddAttack(multi, baseType, times);
            });
            default -> throw new RuntimeException("未定义技能类型");
        }
        return action;
    }

}
