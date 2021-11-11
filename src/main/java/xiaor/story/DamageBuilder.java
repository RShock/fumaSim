package xiaor.story;

import org.junit.platform.commons.util.StringUtils;
import xiaor.*;
import xiaor.skill.BaseSkill;

import java.util.Collections;

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

    public DamageBuilder lasted(int lasted) {
        this.lasted = lasted;
        return this;
    }

    public enum DamageType {
        普通伤害,
        必杀伤害
    }

    public DamageBuilder damageMulti(double multi) {
        this.multi = multi;
        return this;
    }

    public DamageBuilder damageMulti(double[] doubles) {
        return this.damageMulti(doubles[caster.getSkillLevel()]);
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
                    name = caster + "的必杀技，倍率" + Tools.toPercent(multi);
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
                        .time(lasted)
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
                        .time(lasted)
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
        this.acceptors = Collections.singletonList(currentEnemy);
        return this;
    }

    public DamageBuilder name(String name) {
        this.name = name;
        return this;
    }
}
