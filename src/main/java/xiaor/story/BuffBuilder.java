package xiaor.story;

import xiaor.*;

import java.util.Collections;

import static xiaor.TriggerEnum.内部事件;
import static xiaor.TriggerEnum.攻击力计算;

public class BuffBuilder extends BaseBuilder{
    public double multi;

    public BuffBuilder(BaseBuilder preBuilder) {
        super(preBuilder);
    }

    public BuffBuilder() {
        super();
    }

    public BuffBuilder(Chara caster){
        this.caster = caster;
    }

    public BuffBuilder buffType(SkillType type) {
        this.type = type;
        return this;
    }

    public BuffBuilder multi(double multi) {
        this.multi = multi;
        return this;
    }

    public BuffBuilder type(SkillType type) {
        this.type = type;
        return this;
    }

    public BuffBuilder name(String name) {
        this.name = name;
        return this;
    }
    public static BuffBuilder createSkill(Chara caster) {
        return new BuffBuilder().caster(caster);
    }

    protected BuffBuilder caster(Chara caster) {
        this.caster = caster;
        return this;
    }

    public BuffBuilder toSelf() {
        acceptor = Collections.singletonList(caster);
        return this;
    }

    public BuffBuilder lasted(int turn) {
        this.lasted = turn;
        return this;
    }

    public BuffBuilder during(int during) {
        this.lasted = during;
        return this;
    }

    public BuffBuilder toAlly() {
        this.acceptor = GameBoard.getAlly();
        return this;
    }

    @Override
    public BaseBuilder buildThis() {

        Buff buff;
        switch (type) {
//            case 队长技能 -> {
//                buff = Buff.builder()
//                        .caster(caster)
//                        .acceptor(caster)
//                        .buffName(name)
//                        .time(lasted)
//                        .type(SkillTime.CONTINUIOUS)
//
//            }
            default ->{
                buff = Buff.builder()
                        .caster(caster)
                        .acceptor(caster)
                        .buffName(name)
                        .time(lasted)
                        .type(SkillTime.CONTINUIOUS)
                        .trigger(攻击力计算)
                        .check(pack -> pack.checkCaster(caster))
                        .cast(pack -> {
                            pack.getDamageCal().changeDamage(BuffType.攻击力百分比增加, multi);
                            callNext();
                            return true;
                        })
                        .build();
            }
        }

        TriggerManager.registerBuff(buff);
        if(nextBuilder != null)
            return nextBuilder.buildThis();
        else{
            return this;
        }
    }

//    public BuffBuilder
}
