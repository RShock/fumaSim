package xiaor.story;

import xiaor.*;

import static xiaor.Trigger.内部事件;

public class ThenBuilder extends BaseBuilder {

    private final int preId;

    public ThenBuilder(BaseBuilder builder) {
        super(builder);
        this.preId = builder.thenId;
        this.trigger = Trigger.内部事件;
    }

    @Override
    public ThenBuilder buildThis() {
        Skill tempSkill = BaseSkill.builder()
                .time(1)
                .name("tempSKill " + preId)
                .type(SkillTime.CONTINUIOUS)
                .trigger(内部事件)
                .check(pack -> pack.id == preId)
                .cast(pack -> {
                    System.out.println("内部事件then触发:" + preId);
                    nextBuilder.buildThis();
                    return true;
                })
                .build();
        TriggerManager.registerSkill(tempSkill);
        return this;
    }
}
