package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.Chara;
import xiaor.Element;
import xiaor.GameBoard;
import xiaor.Tools;
import xiaor.story.SkillBuilder;

import static xiaor.Common.INFI;
import static xiaor.TriggerEnum.游戏开始时;
import static xiaor.TriggerEnum.释放必杀后;
import static xiaor.story.DamageBuilder.DamageType.必杀伤害;
import static xiaor.story.DamageBuilder.DamageType.普通伤害;
import static xiaor.story.SkillType.*;

@SuperBuilder(toBuilder = true)
public class 法斯公主_露露 extends BaseChara {

    public 法斯公主_露露() {
        this("露露");
    }

    public 法斯公主_露露(String name) {
        super();
        this.name = name;
        this.element = Element.风属性;
        this.isLeader = false;
    }

    @Override
    public void defense(Chara acceptor) {

    }

    @Override
    public void initSkills() {
        double[] multi = {0, 0.69, 0.73, 0.76, 0.80, 0.80};


    }

    @Override
    public String toString() {
        return super.toString();
    }
}
