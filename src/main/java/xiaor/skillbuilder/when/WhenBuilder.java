package xiaor.skillbuilder.when;

import lombok.NoArgsConstructor;
import xiaor.skillbuilder.skill.BaseSkill;
import xiaor.skillbuilder.skill.SkillStatus;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.Action;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.trigger.TriggerManager;

import static xiaor.Common.INFI;

@NoArgsConstructor
public class WhenBuilder {
    private Trigger trigger;
    private Action action;
    private String name;
    private int turn;   //持续回合
    private WhenBuilder preBuilder; //whenBuilder需要懒计算否则会出问题，只在build时依序处理前面所有的指令

    public WhenBuilder(SkillType skillType, Trigger trigger) {
        this.trigger = trigger;
        this.turn = INFI;
        this.preBuilder = this;
    }

    public WhenBuilder act(Action action) {
        if(this.action != null)throw new RuntimeException("重复赋值act,建议中间加上and");
        this.action = action;
        return this;
    }

    public WhenBuilder name(String name) {
        this.name = name;
        return this;
    }

    public void build() {
        if(preBuilder != this)
            preBuilder.build();
        this.action.setTime(turn);
        BaseSkill skill = BaseSkill.builder()
                .name(name)
                .skillStatus(SkillStatus.持续的)
                .trigger(trigger.getTriggerType())
                .check(trigger.getChecker())
                .cast(action.getAction())
                .time(action.getTime())
                .build();
        TriggerManager.registerSkill(skill);
    }

    public WhenBuilder and() {
        WhenBuilder whenBuilder = new WhenBuilder();
        whenBuilder.trigger = trigger;
        whenBuilder.turn = turn;
        whenBuilder.name = name;
        whenBuilder.preBuilder = this;
        return whenBuilder;
    }

    public WhenBuilder lastedTurn(int turn) {
        this.turn = turn;
        return this;
    }
}
