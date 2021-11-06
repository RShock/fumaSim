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
    public List<Chara> acceptor;
    public Trigger trigger;
    public int lasted;
    public String name;
    public BaseBuilder preBuilder;
    public BaseBuilder nextBuilder;

    public BaseBuilder() {
        this.preBuilder = this;
    }

    public BaseBuilder(BaseBuilder builder) {
        this.preBuilder = builder;
        this.caster = builder.caster;
        this.acceptor = builder.acceptor;
        builder.nextBuilder = this;
    }


    public BuffBuilder increaseAtk(double multi) {
        BuffBuilder buffBuilder = new BuffBuilder(multi, this);
        return buffBuilder;
    }

    public ThenBuilder then() {
        return new ThenBuilder(this);
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
}
