package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.Element;
import xiaor.GameBoard;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skillbuilder.action.BuffType;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.SelfTrigger;
import xiaor.tools.Tools;

import static xiaor.Common.INFI;
import static xiaor.GameBoard.getCurrentEnemy;
import static xiaor.skillbuilder.SkillType.*;
import static xiaor.skillbuilder.action.DamageAction.DamageType.*;
import static xiaor.tools.TriggerEnum.*;

@SuperBuilder(toBuilder = true)
public class 精灵王_塞露西亚 extends BaseChara {

    public 精灵王_塞露西亚() {
        this("精灵王");
    }

    public 精灵王_塞露西亚(String name) {
        super();
        this.name = name;
        this.element = Element.风属性;
        this.isLeader = false;
    }

    @Override
    public void initSkills() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
