package xiaor.story;

import org.junit.platform.commons.util.StringUtils;
import xiaor.*;

import java.util.Collections;
import java.util.function.Function;

import static xiaor.Common.INFI;
import static xiaor.Trigger.SKILL;

public class SkillAtkBuilder extends BaseBuilder {
    public Function<Package, Boolean> check;
    public Function<Package, Boolean> cast;

    public double multi;
    private SkillType type;

    public SkillAtkBuilder(Chara caster) {
        super();
        this.caster = caster;
        this.preBuilder = this;
    }

    public enum SkillType {
        必杀,
        队长技能;
    }

    public SkillAtkBuilder when(Trigger trigger) {
        this.trigger = trigger;
        return this;
    }

    public SkillAtkBuilder type(SkillType type) {
        this.type = type;
        return this;
    }

    public SkillAtkBuilder acceptor(Chara acceptor) {
        this.acceptor = Collections.singletonList(acceptor);
        return this;
    }

//    public SkillAtkBuilder check(Function<Package, Boolean> check) {
//        this.check = check;
//        return this;
//    }
//
//    public SkillAtkBuilder cast(Function<MessagePack, Boolean> cast) {
//        this.cast = cast;
//        return this;
//    }

    public SkillAtkBuilder damageMulti(double multi) {
        this.multi = multi;
        return this;
    }

    public SkillAtkBuilder toCurrentEnemy() {
        this.acceptor = Collections.singletonList(GameBoard.getCurrentEnemy());
        return this;
    }

    public SkillAtkBuilder during(int during) {
        this.lasted = during;
        return this;
    }

    public SkillAtkBuilder name(String name) {
        this.name = name;
        return this;
    }

    public static SkillAtkBuilder createSkill(Chara caster) {
        return new SkillAtkBuilder(caster);
    }

    public BaseBuilder buildThis() {
        switch (type) {
            case 必杀 -> {
                int idBeforeCast = TriggerManager.getNewID();
                if(StringUtils.isBlank(name)) {
                    name = caster + "_必杀技";
                }
                BaseSkill atkSkill = BaseSkill.builder()
                        .name(name)
                        .trigger(SKILL)
                        .check(pack -> pack.checkCaster(this.caster))
                        .cast(pack -> {
                            new DamageCal(pack).skillAttack(multi);
                            TriggerManager.sendMessage(Trigger.内部事件,MessagePack.newIdPack(idBeforeCast));
                            return true;
                        })
                        .time(INFI)
                        .build();
                TriggerManager.registerSkill(atkSkill);
                this.thenId = idBeforeCast;
            }
        }
        if(nextBuilder != null)
            return nextBuilder.buildThis();
        else{
            return this;
        }
    }
}
