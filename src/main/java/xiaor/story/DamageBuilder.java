package xiaor.story;

import org.junit.platform.commons.util.StringUtils;
import xiaor.BaseSkill;
import xiaor.Chara;
import xiaor.DamageCal;
import xiaor.TriggerManager;

import java.util.Collections;

import static xiaor.Common.INFI;
import static xiaor.TriggerEnum.大招;
import static xiaor.TriggerEnum.普通攻击;

public class DamageBuilder extends BaseBuilder{
    public double multi;
    public DamageType damageType;

    public DamageBuilder(BaseBuilder builder) {
        super(builder);
    }

    public DamageBuilder() {
        super();
    }

    public enum DamageType {
        普通伤害,
        必杀伤害
    }

    public DamageBuilder damageMulti(double multi) {
        this.multi = multi;
        return this;
    }

    public DamageBuilder type(SkillType type) {
        this.type = type;
        return this;
    }

    public DamageBuilder damageType(DamageType type) {
        this.damageType = type;
        return this;
    }

    public DamageBuilder caster(Chara caster) {
        this.caster = caster;
        return this;
    }

    @Override
    public BaseBuilder buildThis() {
        BaseSkill atkSkill;
        switch (damageType) {
            case 必杀伤害 -> {
                if(StringUtils.isBlank(name)) {
                    name = caster + "_必杀技";
                }
                atkSkill = BaseSkill.builder()
                        .name(name)
                        .trigger(大招)
                        .check(pack -> pack.checkCaster(this.caster))
                        .cast(pack -> {
                            new DamageCal(pack).skillAttack(multi);
                            callNext();
                            return true;
                        })
                        .time(INFI)
                        .build();

            }
            case 普通伤害 -> {
                if(StringUtils.isBlank(name)) {
                    name = caster + "_普通攻击";
                }
                atkSkill = BaseSkill.builder()
                        .name(name)
                        .trigger(普通攻击)
                        .check(pack -> pack.checkCaster(this.caster))
                        .cast(pack -> {
                            new DamageCal(pack).normalAttack(multi);
                            callNext();
                            return true;
                        })
                        .time(INFI)
                        .build();
            }
            default -> throw new RuntimeException("未定义技能类型");
        }
        TriggerManager.registerSkill(atkSkill);
        if(nextBuilder != null)
            return nextBuilder.buildThis();
        else{
            return this;
        }
    }

    public DamageBuilder to(Chara currentEnemy) {
        this.acceptor = Collections.singletonList(currentEnemy);
        return this;
    }

    public DamageBuilder name(String name) {
        this.name = name;
        return this;
    }
}
