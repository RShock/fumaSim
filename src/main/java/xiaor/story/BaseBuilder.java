package xiaor.story;

import xiaor.*;

import java.util.List;
import java.util.function.Function;

/**
 * 将触发器模式转为函数式流写法
 */
public class BaseBuilder implements BuilderInterface{
    public int thenId;
    public Chara caster;
    public List<Chara> acceptors;
    public TriggerEnum trigger;
    public int lasted;
    public String name;
    public BaseBuilder preBuilder;
    public BaseBuilder nextBuilder;
    public SkillType type;
    protected Function<MessagePack, Boolean> checker;


    public BaseBuilder() {
        this.preBuilder = this;
        this.thenId = Tools.getNewID();
    }

    public BaseBuilder(BaseBuilder builder) {
        this.preBuilder = builder;
        this.caster = builder.caster;
        this.acceptors = builder.acceptors;
        this.type = builder.type;
        this.thenId = Tools.getNewID();
        this.lasted = builder.lasted;
        builder.nextBuilder = this;
    }

    public BuffBuilder increaseAtk(double multi) {
        return new BuffBuilder(this).multi(multi).buffType(BuffType.攻击力百分比增加);
    }

    public BuffBuilder increaseNormalAtk(double multi) {
        return new BuffBuilder(this).multi(multi).buffType(BuffType.普攻伤害增加);
    }

    public ThenBuilder then() {
        return new ThenBuilder(this);
    }

    public AndBuilder and() {
        return new AndBuilder(this);
    }

    public DamageBuilder damageMulti(double multi) {
        return new DamageBuilder(this).damageMulti(multi);
    }

    @Override
    public void build() {
        if(preBuilder != this)
            preBuilder.build();
        else{
            buildThis();
        }
    }

    @Override
    public BaseBuilder buildThis() {
        return nextBuilder.buildThis();
    }

    public ThenBuilder when(TriggerEnum trigger) {
        return new ThenBuilder(this).when(trigger);
    }

    public void callNext() {
        TriggerManager.sendMessage(TriggerEnum.内部事件,MessagePack.newIdPack(thenId));
    }

    public ThenBuilder whenSelf(TriggerEnum trigger) {
        return new ThenBuilder(this).when(trigger).checker(pack -> caster.self(pack));
    }

    public BuffBuilder increaseDamage(double multi) {
        return new BuffBuilder(this).multi(multi).buffType(BuffType.造成伤害增加);
    }
}
