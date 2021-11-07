package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.*;
import xiaor.story.SkillBuilder;

import static xiaor.TriggerEnum.游戏开始时;
import static xiaor.story.SkillType.*;

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

        SkillBuilder.createDamageSkill(this)
                .type(必杀)
                .damageMulti(2)
                .to(GameBoard.getCurrentEnemy())
                .then()
                .increaseAtk(multi[getSkillLevel()])
                .toSelf()
                .lasted(6)
                .name(this + "必杀附带普攻增加")
                .build();

        SkillBuilder.createDamageSkill(this)
                .type(普攻)
                .damageMulti(1)
                .name(this + "记住了普攻")
                .to(GameBoard.getCurrentEnemy())
                .build();

        if (isLeader()) {
            SkillBuilder.createSkill(this)
                    .type(队长技能)
                    .when(游戏开始时)
                    .increaseAtk(0.2)
                    .name(this + "自己攻击力增加20%")
                    .toSelf()
                    .and()
                    .increaseAtk(0.4)
                    .name(this + "全队攻击力增加40%")
                    .toAlly()
                    .build();
        }

        //3连爪击
        if (star >= 3) {
            SkillBuilder.createSkill(this)
                    .type(三星技能)
                    .when(self(释放必杀后))
                    .damageMulti(0.3)
                    .spType(沃沃的追击)
                    .to(GB.selectedTarget(1))
                    .and()
                    .damageMulti(0.3)
                    .spType(沃沃的追击)
                    .to(GB.selectedTarget(2))
                    .and()
                    .damageMulti(0.3)
                    .spType(沃沃的追击)
                    .to(GB.selectedTarget(5))
                    .build();

        }


//        //队长技能
//        if(isLeader()) {
//            TriggerManager.getInstance().registerSelfAttackInc(this, this + "_队长技能_自身攻击+20%",
//                    游戏开始时,0.2);
//            GameBoard.getInstance().getOurChara().forEach(chara -> {
//                TriggerManager.getInstance().registerAttackInc(this, chara,
//                        this + "_队长技能_队员攻击+40%", 游戏开始时, 0.4);
//            });
//        }

        //3星技能 3连爪击

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
