package xiaor.charas;


import xiaor.GameBoard;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skillbuilder.action.BuffType;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.SelfTrigger;
import xiaor.tools.GlobalDataManager;
import xiaor.tools.Tools;
import xiaor.tools.TriggerEnum;

import static xiaor.Common.INFI;
import static xiaor.skillbuilder.SkillType.*;
import static xiaor.skillbuilder.action.DamageAction.DamageType.必杀伤害;
import static xiaor.skillbuilder.action.DamageAction.DamageType.普通伤害;
import static xiaor.tools.KeyEnum.GAMETURN;
import static xiaor.tools.TriggerEnum.*;

public class 精灵王_塞露西亚 extends Chara {

    public static 精灵王_塞露西亚 init(String s) {
        精灵王_塞露西亚 精灵王 = new 精灵王_塞露西亚();
        精灵王.name = "精灵王";
        精灵王.element = Element.风属性;
        精灵王.role = Role.辅助者;
        baseInit(精灵王, s);
        return 精灵王;
    }

    @Override
    public void initSkills() {

        double[] multi = {0, 4.75, 5.5, 6.25, 6.25, 6.25}; //宝具倍率
        double[] multi2 = {0, 0.15, 0.15, 0.15, 0.2, 0.25}; //全体增攻

        //大招 以攻击力x对目标造成伤害，并使得我方全体攻击力增加y（3回合）
        SkillBuilder.createNewSkill(this, 必杀)
                .when((SelfTrigger.act(this, 释放必杀)))
                .act(DamageAction.create(this, 必杀伤害)
                        .multi(multi).to(GameBoard.getCurrentEnemy()).build())
                .and()
                .act(BuffAction.create(this, BuffType.攻击力百分比增加)
                        .name("精灵王大招全体增攻"+ Tools.toPercent(multi2[getSkillLevel()]))
                        .multi(multi2).toAlly().build())
                .build();

        //普通攻击
        SkillBuilder.createNewSkill(this, 普攻)
                .when(SelfTrigger.act(this, 释放普攻))
                .act(DamageAction.create(this, 普通伤害).build())
                .build();

        //队长技能 使我方全体普攻伤害+50%
        //不做

        //普攻时，触发使我方全体普攻伤害增加7.5%（1回合）效果
        SkillBuilder.createNewSkill(this, SkillType.被动)
                .when(SelfTrigger.act(this, TriggerEnum.释放普攻后))
                .act(BuffAction.create(this, BuffType.普攻伤害增加).multi(0.075).lastedTurn(1)
                        .toAlly().name("普攻时，触发使我方全体普攻伤害增加7.5%（1回合）")
                        .build())
                .build();
        //必杀时，触发使目标受到伤害增加10%（3回合）并防御解除效果（不做）
        SkillBuilder.createNewSkill(this, SkillType.三星被动)
                .when(SelfTrigger.act(this, TriggerEnum.释放必杀后))
                .act(BuffAction.create(this, BuffType.受到伤害增加).multi(0.1).lastedTurn(3)
                        .name("必杀时，触发使目标受到伤害增加10%（3回合）")
                        .toCurrentEnemy().build())
                .build();
        //每经过3回合，触发：使我方全体攻击力增加20%（1回合）效果
        if(star >= 5) {
            SkillBuilder.createNewSkill(this, SkillType.五星被动)
                    .when(TriggerEnum.回合开始)
                    .check(() -> GlobalDataManager.getIntData(GAMETURN) % 3 == 1 && GlobalDataManager.getIntData(GAMETURN) != 1)
                    .act(BuffAction.create(this, BuffType.攻击力百分比增加).multi(0.2)
                            .name("每过3回合触发全体攻击力+20%")
                            .lastedTurn(1).toAlly().build())
                    .build();
        }

        //6潜  使自身普攻伤害增加10%
        if (is6) {
            SkillBuilder.createNewSkill(this, 六潜被动)
                    .when(游戏开始时)
                    .act(BuffAction.create(this, BuffType.普攻伤害增加)
                            .multi(0.1).toSelf().lastedTurn(INFI)
                            .name(this + "普攻伤害增加+10%")
                            .build())
                    .build();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
