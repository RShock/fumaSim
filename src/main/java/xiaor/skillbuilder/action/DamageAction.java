package xiaor.skillbuilder.action;

import xiaor.charas.Chara;
import xiaor.damageCal.DamageCal;
import xiaor.damageCal.DamageBase;
import xiaor.msgpack.MessagePack;

import java.util.List;

public class DamageAction {

    private List<Chara> target;

    private DamageType damageType;

    public double multi;    //伤害倍率

    public DamageBase baseType = DamageBase.攻击;

    public DamageAction damageBase(DamageBase base) {
        this.baseType = base;
        return this;
    }
    public enum DamageType {
        普通伤害,
        必杀伤害,
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

    public DamageAction to(List<Chara> enemy) {
        this.target = enemy;
        return this;
    }

    public Action build() {
        Action action = new Action();
        switch (damageType) {
            case 必杀伤害 -> action.setAction(pack -> {
                if (target != null && !target.isEmpty()) {
                    ((MessagePack)pack).acceptors = target;
                }
                new DamageCal(((MessagePack)pack)).skillAttack(multi, baseType);
            });
            case 普通伤害 -> action.setAction(pack -> {
                if (target != null && !target.isEmpty()) {
                    ((MessagePack)pack).acceptors = target;
                }
                new DamageCal(((MessagePack)pack)).normalAttack(multi, baseType);
            });
            default -> throw new RuntimeException("未定义技能类型");
        }
        return action;
    }

}
