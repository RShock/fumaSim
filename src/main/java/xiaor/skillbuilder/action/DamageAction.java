package xiaor.skillbuilder.action;

import xiaor.charas.Chara;
import xiaor.DamageCal;

import java.util.Collections;
import java.util.List;

public class DamageAction extends ActionBuilder {

    private List<Chara> target;

    private DamageType damageType;

    public double multi;    //伤害倍率

    public Chara caster;

    public enum DamageType {
        普通伤害,
        必杀伤害,
    }

    public static DamageAction create(Chara chara, DamageType damageType) {
        DamageAction damageAction = new DamageAction();
        damageAction.damageType = damageType;
        damageAction.multi = 1.0;   //默认倍率1.0
        damageAction.caster = chara;
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
                    pack.acceptors = target;
                }
                new DamageCal(pack).skillAttack(multi);
            });
            case 普通伤害 -> action.setAction(pack -> {
                if (target != null && !target.isEmpty()) {
                    pack.acceptors = target;
                }
                new DamageCal(pack).normalAttack(multi);
            });
            default -> throw new RuntimeException("未定义技能类型");
        }
        return action;
    }

}
