package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.Chara;
import xiaor.Element;
import xiaor.GameBoard;
import xiaor.Tools;
import xiaor.newStory.SkillBuilder;
import xiaor.newStory.SkillType;
import xiaor.newStory.action.BuffAction;
import xiaor.newStory.action.BuffType;
import xiaor.newStory.action.DamageAction;
import xiaor.newStory.trigger.SelfTrigger;

import static xiaor.Common.INFI;
import static xiaor.GameBoard.getCurrentEnemy;
import static xiaor.TriggerEnum.释放大招;
import static xiaor.TriggerEnum.释放普攻;
import static xiaor.newStory.SkillType.普攻;
import static xiaor.newStory.action.DamageAction.DamageType.必杀伤害;
import static xiaor.newStory.action.DamageAction.DamageType.普通伤害;

@SuperBuilder(toBuilder = true)
public class 复生公主_千鹤 extends BaseChara {

    public 复生公主_千鹤() {
        this("千鹤");
    }

    public 复生公主_千鹤(String name) {
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
        double[] multi1 = {0.0, 3.3, 3.76, 4.22, 4.22, 4.22}; //必杀倍率
        double[] multi2 = {0.0, 0.12, 0.12, 0.12, 0.15, 0.18}; //风属性易伤倍率

        //使目标受到的风属性伤害增加$1（2层）  再以攻击力$2对目标造成伤害 CD4
        SkillBuilder.createNewSkill(this, SkillType.必杀)
                .when(SelfTrigger.act(this, 释放大招))
                .act(BuffAction.create(this, BuffType.受到风属性伤害增加)
                        .multi(multi2).toCurrentEnemy().level(1).maxLevel(2)
                        .name(this+"必杀前给目标增加" + Tools.toPercent(multi2[getSkillLevel()]) + "的风属性易伤")
                        .lastedTurn(INFI).build())
                .and()
                .act(DamageAction.create(this, 必杀伤害)
                        .multi(multi1).to(getCurrentEnemy()).build())
                .build();

        SkillBuilder.createNewSkill(this, 普攻)
                .when(SelfTrigger.act(this, 释放普攻))
                .act(DamageAction.create(this, 普通伤害).build())
                .build();

//        SkillBuilder2.createSkill(this)
//                .whenSelf(TriggerEnum.释放大招)
//                .name(this+"的大招")
//                .addNewBuff(BuffType.受到风属性伤害增加)
//                .multi(new double[]{0.0, 0.12, 0.12, 0.12, 0.15, 0.18})
//                .name(this + "必杀附带的易伤")
//                .to(GameBoard.getCurrentEnemy())
//                .level(1)
//                .maxLevel(2)
//                .lasted(INFI)
//                .then()
//                .addNewDamage(必杀伤害)
//                .damageMulti(new double[]{0.0, 3.3, 3.76, 4.22, 4.22, 4.22})
//                .name(this + "的必杀伤害")
//                .to(GameBoard.getCurrentEnemy())
//                .build();
//        //普通攻击
//        SkillBuilder2.createDamageSkill(this)
//                .type(普攻)
//                .damageType(普通伤害)
//                .damageMulti(1)
//                .name(this + "的基础攻击")
//                .to(GameBoard.getCurrentEnemy())
//                .build();

        //队长技 使自身攻击力+90% 必杀技伤害增加+30%


        //使自身受到伤害减少15% 但是受到火属性伤害增加30%（不做）

        //防御时，触发：使自身当前必杀技CD减少1回合效果（不做）

        //5星被动 必杀时，触发使我方全体风属性角色必杀技伤害增加15%（最多2层）效果

        //6潜 使得自身必杀技伤害增加10%


    }

    @Override
    public String toString() {
        return super.toString();
    }
}
