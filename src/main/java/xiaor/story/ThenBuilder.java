package xiaor.story;

import xiaor.*;

import java.util.function.Function;

import static xiaor.TriggerEnum.内部事件;

public class ThenBuilder extends BaseBuilder {

    private final int preId;

    public ThenBuilder(BaseBuilder builder) {
        super(builder);
        this.preId = builder.thenId;
        this.trigger = TriggerEnum.内部事件;
    }

    public ThenBuilder when(TriggerEnum trigger) {
        this.trigger = trigger;
        return this;
    }

    protected ThenBuilder checker(Function<MessagePack, Boolean> checker) {
        this.checker = checker;
        return this;
    }

    public ThenBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ThenBuilder buildThis() {
        switch (trigger) {
            case 内部事件 -> {
                Skill tempSkill = BaseSkill.builder()
                        .time(1)
                        .name("tempSKill " + preId)
                        .type(SkillTime.CONTINUIOUS)
                        .trigger(内部事件)
                        .check(pack -> pack.id == preId)
                        .cast(pack -> {
                            System.out.println("内部事件then触发:" + preId);
                            nextBuilder.buildThis();
                            callNext();
                            return true;
                        })
                        .build();
                TriggerManager.registerSkill(tempSkill);
            }
            default -> {
                Skill tempSkill = BaseSkill.builder()
                        .time(1)
                        .name(name)
                        .type(SkillTime.CONTINUIOUS)
                        .trigger(trigger)
                        .check(pack -> true)
//                        .check(pack -> pack.id == preId)
                        .cast(pack -> {
                            nextBuilder.buildThis();
                            callNext();
                            return true;
                        })
                        .build();
                TriggerManager.registerSkill(tempSkill);
            }
        }
        return this;
    }

}
