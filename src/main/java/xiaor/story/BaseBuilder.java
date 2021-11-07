package xiaor.story;

import xiaor.*;

import java.util.List;

/**
 * 将触发器模式转为函数式流写法
 */
public class BaseBuilder implements BuilderInterface{
    public int thenId;
    public Chara caster;
    public List<Chara> acceptor;
    public TriggerEnum trigger;
    public int lasted;
    public String name;
    public BaseBuilder preBuilder;
    public BaseBuilder nextBuilder;
    public SkillType type;


    public BaseBuilder() {
        this.preBuilder = this;
        this.thenId = TriggerManager.getNewID();
    }

    public BaseBuilder(BaseBuilder builder) {
        this.preBuilder = builder;
        this.caster = builder.caster;
        this.acceptor = builder.acceptor;
        this.type = builder.type;
        this.thenId = TriggerManager.getNewID();
        builder.nextBuilder = this;
    }

    public BuffBuilder increaseAtk(double multi) {
        return new BuffBuilder(this).multi(multi);
    }

    public ThenBuilder then() {
        return new ThenBuilder(this);
    }

    public AndBuilder and() {
        return new AndBuilder(this);
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
}
