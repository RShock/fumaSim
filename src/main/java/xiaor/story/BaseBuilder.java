package xiaor.story;

import xiaor.*;

import java.util.List;
import java.util.function.Function;

/**
 * 将触发器模式转为函数式流写法
 */
public class BaseBuilder implements BuilderInterface{
    List<Function<MessagePack, Boolean>> callBacks;
    BaseBuilder preBuilder;
    BaseBuilder nextBuilder;

    public BaseBuilder() {
        preBuilder = this;
    }

    public BaseBuilder(BaseBuilder preBuilder) {
        this.preBuilder = preBuilder;
        preBuilder.nextBuilder = this;
    }


//    public BaseBuilder then(Function<MessagePack,Boolean> callBack) {
//        callBacks.add(callBack);
//    }

    public IncAtkBuilder incAtk() {
        return new IncAtkBuilder(this);
    }

//    public Skill buildSkillAttack() {
//        callBack
//
//        if(story.acceptor == null || story.name == null || story.multi == 0)throw new RuntimeException("不完整的Story");
//        TriggerManager.getInstance().registerSkillAttack(story.castor, story.name, story.multi,);
//        return
//    }

    @Override
    public void build() {

    }
}
