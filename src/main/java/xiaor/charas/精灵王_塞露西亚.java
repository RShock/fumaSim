package xiaor.charas;


import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skillbuilder.action.BuffType;
import xiaor.skillbuilder.trigger.SelfTrigger;
import xiaor.tools.GlobalDataManager;
import xiaor.tools.TriggerEnum;

import static xiaor.Common.INFI;
import static xiaor.skillbuilder.SkillType.六潜被动;
import static xiaor.tools.KeyEnum.GAMETURN;
import static xiaor.tools.TriggerEnum.游戏开始时;

public class 精灵王_塞露西亚 extends Chara {

    public static 精灵王_塞露西亚 init(String s) {
        精灵王_塞露西亚 精灵王 = new 精灵王_塞露西亚();
        精灵王.name = "精灵王";
        精灵王.element = Element.风属性;
        精灵王.role = Role.攻击者;
        baseInit(精灵王, s);
        return 精灵王;
    }

    @Override
    public void initSkills() {
        //大招 以攻击力x对目标造成伤害，并使得我方全体攻击力增加y（3回合）

        //普攻

        //队长技能 使我方全体普攻伤害+50%
        //不做

        //普攻时，触发使我方全体普攻伤害增加7.5%（1回合）效果
        SkillBuilder.createNewSkill(this, SkillType.被动)
                .when(SelfTrigger.act(this, TriggerEnum.释放普攻后))
                .act(BuffAction.create(this, BuffType.普攻伤害增加).multi(0.075).lastedTurn(1)
                        .toAlly().build())
                .build();
        //必杀时，触发使目标受到伤害增加10%（3回合）并防御解除效果（不做）
        SkillBuilder.createNewSkill(this, SkillType.三星被动)
                .when(SelfTrigger.act(this, TriggerEnum.释放必杀后))
                .act(BuffAction.create(this, BuffType.受到伤害增加).multi(0.1).lastedTurn(3)
                        .toCurrentEnemy().build())
                .build();
        //每经过3回合，触发：使我方全体攻击力增加20%（1回合）效果
        SkillBuilder.createNewSkill(this, SkillType.五星被动)
                .when(TriggerEnum.回合开始)
                .check(() -> GlobalDataManager.getIntData(GAMETURN) % 3 == 1)
                .act(BuffAction.create(this, BuffType.攻击力百分比增加).multi(0.2).lastedTurn(1).toAlly().build())
                .build();
        //6潜  使自身普攻伤害增加10%
        if (is6) {
            SkillBuilder.createNewSkill(this, 六潜被动)
                    .when(游戏开始时)
                    .act(BuffAction.create(this, BuffType.攻击力百分比增加)
                            .multi(0.1).toSelf().lastedTurn(INFI)
                            .name(this + "自身攻击+10%")
                            .build())
                    .build();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
