package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.*;
import xiaor.story.SkillAtkBuilder;

import java.util.function.Function;

import static xiaor.Common.INFI;
import static xiaor.story.SkillAtkBuilder.SkillType.必杀;

@SuperBuilder(toBuilder = true)
public class 胆小纸袋狼_沃沃 extends BaseChara {

    public 胆小纸袋狼_沃沃() {
        super("沃沃", false);
    }

    public 胆小纸袋狼_沃沃(String name) {
        super(name);
    }

    public 胆小纸袋狼_沃沃(String name, boolean isLeader) {
        super(name, isLeader);
    }

    @Override
    public void defense(Chara acceptor) {

    }

    @Override
    public void initSkills() {
        double[] multi = {0, 0.96, 1.18, 1.41, 1.63, 1.86};

        SkillAtkBuilder.createSkill(this)
                .type(必杀)
                .damageMulti(2)
                .toCurrentEnemy()
                .then()
                .increaseAtk(multi[getSkillLevel()])
                .toSelf()
                .lasted(6)
                .name(this+"必杀附带普攻增加")
                .build();

        //普攻技能
        TriggerManager.getInstance().registerNormalAttack(this, this + "记住了普攻", 1.00);

        //队长技能
        if(isLeader()) {
            TriggerManager.getInstance().registerSelfAttackInc(this, this + "_队长技能_自身攻击+20%",
                    Trigger.游戏开始时,0.2);
            GameBoard.getInstance().getOurChara().forEach(chara -> {
                TriggerManager.getInstance().registerAttackInc(this, chara,
                        this + "_队长技能_队员攻击+40%", Trigger.游戏开始时, 0.4);
            });
        }

        //3星技能 3连爪击

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
