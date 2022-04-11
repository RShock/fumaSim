package xiaor.skillbuilder;

import xiaor.trigger.TriggerEnum;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.skillbuilder.when.WhenBuilder;

public class SkillBuilder {

    private final String name;

    public SkillBuilder(String name) {
        this.name = name;
    }

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
