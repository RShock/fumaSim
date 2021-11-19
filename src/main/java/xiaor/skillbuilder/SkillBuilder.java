package xiaor.skillbuilder;

import xiaor.charas.Chara;
import xiaor.tools.TriggerEnum;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.skillbuilder.trigger.TriggerBuilder;
import xiaor.skillbuilder.when.WhenBuilder;

public class SkillBuilder {
    private final SkillType skillType;
    private final Chara caster; //技能释放者
    private String name;

    public static SkillBuilder createNewSkill(Chara caster, SkillType type) {
        return new SkillBuilder(caster, type);
    }

    protected SkillBuilder(Chara caster, SkillType type) {
        this.caster = caster;
        this.skillType = type;
        this.name = caster + " " + type;
    }

    public WhenBuilder when(Trigger trigger) {
        return new WhenBuilder(skillType, trigger).name(name);
    }

    public WhenBuilder when(TriggerEnum triggerEnum) {
        return this.when(TriggerBuilder.when(triggerEnum));
    }
}