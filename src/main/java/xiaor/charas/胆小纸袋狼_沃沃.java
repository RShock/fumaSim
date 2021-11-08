package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.*;
import xiaor.story.DamageBuilder;
import xiaor.story.SkillBuilder;

import static xiaor.Common.INFI;
import static xiaor.TriggerEnum.游戏开始时;
import static xiaor.TriggerEnum.释放必杀后;
import static xiaor.story.DamageBuilder.DamageType.必杀伤害;
import static xiaor.story.DamageBuilder.DamageType.普通伤害;
import static xiaor.story.SkillType.*;

@SuperBuilder(toBuilder = true)
public class 胆小纸袋狼_沃沃 extends BaseChara {

    public 胆小纸袋狼_沃沃() {
        this("沃沃");
    }

    public 胆小纸袋狼_沃沃(String name) {
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
        double[] multi = {0, 0.96, 1.18, 1.41, 1.63, 1.86};

        SkillBuilder.createDamageSkill(this)
                .type(必杀)
                .damageType(必杀伤害)
                .damageMulti(2)
                .to(GameBoard.getCurrentEnemy())
                .then()
                .increaseNormalAtk(multi[getSkillLevel()])
                .toSelf()
                .lasted(6)
                .name(this + "必杀附带普攻增加"+ Tools.toPercent(multi[getSkillLevel()]))
                .build();

        SkillBuilder.createDamageSkill(this)
                .type(普攻)
                .damageType(普通伤害)
                .damageMulti(1)
                .name(this + "的基础攻击")
                .to(GameBoard.getCurrentEnemy())
                .build();

        if (isLeader()) {
            SkillBuilder.createSkill(this)
                    .type(队长技能)
                    .lasted(INFI)
                    .when(游戏开始时)
                    .name("激活沃沃的队长技能")
                    .increaseNormalAtk(0.2)
                    .name(this + "自己普攻攻击力增加20%")
                    .toSelf()
                    .and()
                    .increaseNormalAtk(0.4)
                    .name(this + "全队普攻攻击力增加40%")
                    .toAlly()
                    .build();
        }

        //3星技能 3连爪击
        if (star >= 3) {
            SkillBuilder.createSkill(this)
                    .type(三星技能)
                    .whenSelf(释放必杀后)
                    .name(this+"对125号位追击")
                    .lasted(6)
                    .damageMulti(0.3)
                    .damageType(普通伤害)
                    .to(GameBoard.selectTarget(1))
                    .and()
                    .damageMulti(0.3)
                    .damageType(普通伤害)
                    .to(GameBoard.selectTarget(2))
                    .and()
                    .damageMulti(0.3)
                    .damageType(普通伤害)
                    .to(GameBoard.selectTarget(5))
                    .build();
        }

        //五星技能 月夜狼嚎
        if(star >= 5) {
            SkillBuilder.createSkill(this)
                    .type(五星技能)
                    .whenSelf(释放必杀后)
                    .name(this+"必杀后添加造成伤害增加20%（最多2层）")
                    .lasted(INFI)
                    .increaseDamage(0.2)
                    .toSelf()
                    .name(this+"造成伤害增加20%（最多2层）")
                    .lasted(INFI)
                    .level(1)
                    .maxLevel(2)
                    .build();
        }

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
