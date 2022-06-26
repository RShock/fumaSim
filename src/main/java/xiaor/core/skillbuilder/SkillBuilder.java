package xiaor.core.skillbuilder;

import xiaor.core.skillbuilder.trigger.Trigger;
import xiaor.core.trigger.TriggerEnum;
import xiaor.core.skillbuilder.when.WhenBuilder;

public record SkillBuilder(String name) {

    public static SkillBuilder createNewSkill(String skillName) {
        return new SkillBuilder(skillName);
    }

    public WhenBuilder when(Trigger trigger) {
        return new WhenBuilder(trigger).name(name);
    }

    public WhenBuilder when(TriggerEnum triggerEnum) {
        return this.when(Trigger.when(triggerEnum));
    }
}
