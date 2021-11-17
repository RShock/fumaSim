package xiaor.skillbuilder.when;

import lombok.NoArgsConstructor;
import xiaor.skillbuilder.trigger.SelfTrigger;
import xiaor.tools.TriggerManager;
import xiaor.skillbuilder.action.Action;
import xiaor.skillbuilder.action.ActionBuilder;
import xiaor.skillbuilder.trigger.InternalEventTrigger;
import xiaor.skillbuilder.trigger.Trigger;
import xiaor.skill.BaseSkill;
import xiaor.skill.SkillTime;
import xiaor.skillbuilder.SkillType;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static xiaor.Common.INFI;

@NoArgsConstructor
public class WhenBuilder {
    private Trigger trigger;
    private Action action;
    private String name;
    private SkillType skillType;
    private int turn;   //持续回合
    private WhenBuilder preBuilder; //whenBuilder需要懒计算否则会出问题，只在build时依序处理前面所有的指令

    public WhenBuilder(SkillType skillType, Trigger trigger) {
        this.trigger = trigger;
        this.skillType = skillType;
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
        fillAction();
        this.action.setTime(turn);
        BaseSkill skill = BaseSkill.builder()
                .name(name)
                .skillTime(SkillTime.持续的)
                .trigger(trigger.getTriggerType())
                .check(trigger.getChecker())
                .cast(action.getAction())
                .time(action.getTime())
                .build();
        TriggerManager.registerSkill(skill);
    }

    public WhenBuilder then() {
        fillAction();
        WhenBuilder whenBuilder = new WhenBuilder();
        whenBuilder.trigger = InternalEventTrigger.getTrigger(action.getActionId());
        whenBuilder.preBuilder = this;
        return whenBuilder;
    }

    public WhenBuilder and() {
        fillAction();
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

    public Action toAction() {
        Action action = new Action();
        action.setAction(pack -> {
            this.build();
            return true;
        });
        action.setTime(this.turn);
        return action;
    }

    private void fillAction() {
        if(action == null){
            //when事件为空，这种情况一般是要触发then事件,但是删掉then用when也能起到同样作用
            action = ActionBuilder.getEmptyAction();
        }
    }

    //额外修改checker 特殊情况使用
    public WhenBuilder check(Supplier<Boolean> checker) {
        this.trigger.checker = (pack -> checker.get());
        return this;
    }
}
