package xiaor.core.skillbuilder.when;

import lombok.NoArgsConstructor;
import xiaor.core.charas.Chara;
import xiaor.core.skillbuilder.action.Action;
import xiaor.core.skillbuilder.skill.BaseSkill;
import xiaor.core.skillbuilder.trigger.Trigger;
import xiaor.core.skillbuilder.skill.SkillStatus;
import xiaor.core.trigger.TriggerManager;

import static xiaor.core.Common.INFINITY;

@NoArgsConstructor
public class WhenBuilder {
    private Trigger trigger;
    private Action action;
    private String name;
    private int turn;   //持续回合
    private WhenBuilder preBuilder; //whenBuilder需要懒计算否则会出问题，只在build时依序处理前面所有的指令

    public WhenBuilder(Trigger trigger) {
        this.trigger = trigger;
        this.turn = INFINITY;
        this.preBuilder = this;
    }

    public WhenBuilder act(Action action) {
        if (this.action != null) throw new RuntimeException("重复赋值act,建议中间加上and");
        this.action = action;
        return this;
    }

    public BaseSkill build(Chara chara, int skillId) {
        if (preBuilder != this)
            preBuilder.build(chara, skillId);
        this.action.setTime(turn);
        BaseSkill skill = BaseSkill.builder()
                .name(name)
                .skillStatus(SkillStatus.持续的)
                .trigger(trigger.getTriggerType())
                .check(trigger.getChecker())
                .cast(action.getAction())
                .time(action.getTime())
                .skillId(skillId)
                .build();
        TriggerManager.registerSkill(skill);
        chara.addSkill(skill);
        return skill;
    }

    public WhenBuilder and() {
        WhenBuilder whenBuilder = new WhenBuilder();
        whenBuilder.trigger = trigger;
        whenBuilder.turn = turn;
        whenBuilder.name = name;
        whenBuilder.preBuilder = this;
        return whenBuilder;
    }

    @SuppressWarnings("UnusedReturnValue")
    public WhenBuilder lastedTurn(int turn) {
        this.turn = turn;
        return this;
    }

    public WhenBuilder name(String skillName) {
        this.name = skillName;
        return this;
    }

    @Override
    public String toString() {
        return this.name + "||" +this.action.getName();
    }
}
