package xiaor.skillbuilder;

import xiaor.charas.Chara;
import xiaor.trigger.TriggerEnum;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.skillbuilder.when.WhenBuilder;

public class SkillBuilder {
    private final SkillType skillType;

    public static SkillBuilder createNewSkill(SkillType type) {
        return new SkillBuilder(type);
    }

    protected SkillBuilder(SkillType type) {
        this.skillType = type;
    }

    public WhenBuilder when(Trigger trigger) {
        return new WhenBuilder(skillType, trigger);
    }

    public WhenBuilder when(TriggerEnum triggerEnum) {
        return this.when(Trigger.when(triggerEnum));
    }
}
