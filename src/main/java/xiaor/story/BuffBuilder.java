package xiaor.story;

import xiaor.*;

import java.util.Collections;

import static xiaor.TriggerEnum.*;

public class BuffBuilder extends BaseBuilder{
    public double multi;
    protected BuffType buffType;

    public BuffBuilder(BaseBuilder preBuilder) {
        super(preBuilder);
    }

    public BuffBuilder() {
        super();
    }

    public BuffBuilder(Chara caster){
        this.caster = caster;
    }

    public BuffBuilder buffType(BuffType buffType) {
        this.buffType = buffType;
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

    public BuffBuilder toAlly() {
        this.acceptor = GameBoard.getAlly();
        return this;
    }

    @Override
    public BaseBuilder buildThis() {

        Buff buff;
        switch (buffType) {
            case 攻击力百分比增加 -> {
                buff = Buff.builder()
                        .caster(caster)
                        .acceptor(caster)
                        .name(name)
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
            case 普攻伤害增加 -> {
                buff = Buff.builder()
                        .caster(caster)
                        .acceptor(caster)
                        .name(name)
                        .time(lasted)
                        .type(SkillTime.CONTINUIOUS)
                        .trigger(普攻伤害计算)
                        .check(pack -> pack.checkCaster(caster))
                        .cast(pack -> {
                            pack.getDamageCal().changeDamage(BuffType.普攻伤害增加, multi);
                            callNext();
                            return true;
                        })
                        .build();
            }
            default ->{
                throw new RuntimeException("未支持的buff类型");
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
