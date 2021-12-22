package xiaor.charas;


import xiaor.GameBoard;
import xiaor.tools.Tools;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.SelfTrigger;
import xiaor.skillbuilder.action.BuffType;

import static xiaor.Common.INFI;
import static xiaor.GameBoard.getCurrentEnemy;
import static xiaor.tools.TriggerEnum.*;
import static xiaor.skillbuilder.action.DamageAction.DamageType.*;
import static xiaor.skillbuilder.SkillType.*;

public class 胆小纸袋狼_沃沃 extends Chara {

    public static 胆小纸袋狼_沃沃 init(String s) {
        胆小纸袋狼_沃沃 沃沃 = new 胆小纸袋狼_沃沃();
        沃沃.name = "沃沃";
        沃沃.element = Element.风属性;
        沃沃.role = Role.攻击者;
        baseInit(沃沃, s);
        return 沃沃;
    }

    @Override
    public void initSkills() {
        double[] multi = {0, 0.96, 1.18, 1.41, 1.63, 1.86}; //攻击力增加buff

        SkillBuilder.createNewSkill(this, 必杀技)
                .when(SelfTrigger.act(this, 释放必杀))
                .act(DamageAction.create(this, 必杀伤害)
                        .multi(2.0).to(getCurrentEnemy()).build())
                .and()
                .act(
                        BuffAction.create(this, BuffType.普攻伤害增加)
                                .multi(multi).toSelf().lastedTurn(6)
                                .name(this + "大招附带普攻增加" + Tools.toPercent(multi[getSkillLevel()]))
                                .build())
                .build();

        SkillBuilder.createNewSkill(this, 普攻)
                .when(SelfTrigger.act(this, 释放普攻))
                .act(DamageAction.create(this, 普通伤害).build())
                .build();

        if (isLeader()) {
            SkillBuilder.createNewSkill(this, 队长技)
                    .when(游戏开始时)
                    .act(
                            BuffAction.create(this, BuffType.普攻伤害增加)
                                    .multi(0.2).toSelf().lastedTurn(INFI)
                                    .name(this + "自己普攻攻击力增加20%")
                                    .build())
                    .and()
                    .act(
                            BuffAction.create(this, BuffType.普攻伤害增加)
                                    .multi(0.4).toAlly().lastedTurn(INFI)
                                    .name(this + "全队普攻攻击力增加40%")
                                    .build())
                    .build();

        }

        if (star >= 3) {
            SkillBuilder.createNewSkill(this, 三星被动)
                    .when(SelfTrigger.act(this, 释放必杀后))
                    .name(this + "三星技能 释放必杀后 普攻对125号位追击")
                    .act(
                            SkillBuilder.createNewSkill(this, 三星被动)
                                    .when(SelfTrigger.act(this, 释放普攻后))
                                    .lastedTurn(6)
                                    .act(DamageAction.create(this, 追击普通伤害).multi(0.3).to(GameBoard.selectTarget(1)).build())
                                    .and()
                                    .act(DamageAction.create(this, 追击普通伤害).multi(0.3).to(GameBoard.selectTarget(2)).build())
                                    .and()
                                    .act(DamageAction.create(this, 追击普通伤害).multi(0.3).to(GameBoard.selectTarget(5)).build())
                                    .toAction()
                    )
                    .build();
        }
//
        //五星技能 月夜狼嚎
        if(star >= 5) {
            SkillBuilder.createNewSkill(this, 五星被动)
                    .when(释放必杀后)
                    .act(
                            BuffAction.create(this, BuffType.造成伤害增加)
                                    .multi(0.2).toSelf().lastedTurn(INFI)
                                    .name(this + "造成伤害增加20%（最多2层）")
                                    .level(1)
                                    .maxLevel(2)
                                    .build()
                    )
                    .build();
        }

        //六潜技能
        if(is6()) {
//            SkillBuilder.createNewSkill(this, 六潜技能)
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
