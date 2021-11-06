package xiaor.story;

import xiaor.Buff;
import xiaor.MessagePack;
import xiaor.SkillTime;
import xiaor.TriggerManager;

import java.util.Collections;
import java.util.function.Function;

import static xiaor.Trigger.内部事件;
import static xiaor.Trigger.攻击力计算;

public class BuffBuilder extends BaseBuilder{
    public BuffType type;
    public double multi;

    public BuffBuilder(double multi, BaseBuilder preBuilder) {
        super(preBuilder);
        this.multi = multi;
        this.caster = preBuilder.caster;
        this.acceptor = preBuilder.acceptor;
//        this.name = preBuilder.name;
    }

    public BuffBuilder() {
        super();
    }

    public BuffBuilder buffType(BuffType type) {
        this.type = type;
        return this;
    }

    public BuffBuilder multi(double multi) {
        this.multi = multi;
        return this;
    }

    public BuffBuilder name(String name) {
        this.name = name;
        return this;
    }

    public static BuffBuilder createBuffBuilder() {
        return new BuffBuilder();
    }

    public BuffBuilder toSelf() {
        acceptor = Collections.singletonList(caster);
        return this;
    }

    public BuffBuilder lasted(int turn) {
        this.lasted = turn;
        return this;
    }

    @Override
    public BaseBuilder buildThis() {
        Buff atkIncBuff = Buff.builder()
                .caster(caster)
                .acceptor(caster)
                .buffName(name)
                .time(lasted)
                .type(SkillTime.CONTINUIOUS)
                .trigger(攻击力计算)
                .check(pack -> pack.checkCaster(caster))
                .cast(pack -> pack.getDamageCal().changeDamage(BuffType.攻击力百分比增加, multi))
                .build();
        TriggerManager.registerBuff(atkIncBuff);
        if(nextBuilder != null)
            return nextBuilder.buildThis();
        else{
            return this;
        }
    }

//    public BuffBuilder
}
