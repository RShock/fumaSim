package xiaor.charas;


import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skillbuilder.action.BuffType;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.SelfTrigger;

import static xiaor.skillbuilder.SkillType.必杀技;
import static xiaor.skillbuilder.SkillType.普攻;
import static xiaor.skillbuilder.action.DamageAction.DamageType.必杀伤害;
import static xiaor.skillbuilder.action.DamageAction.DamageType.普通伤害;
import static xiaor.tools.TriggerEnum.*;

public class 法斯公主_露露 extends Chara {

    public static 法斯公主_露露 init(String s) {
        法斯公主_露露 露露 = new 法斯公主_露露();
        露露.name = "露露";
        露露.element = Element.风属性;
        露露.role = Role.治疗者;
        baseInit(露露, s);
        return 露露;
    }
    @Override
    public void initSkills() {
        double[] multi = {0, 0.69, 0.73, 0.76, 0.80, 0.80};
        //以攻击力200%对我方全体进行治疗，并获得”每回合以攻击力x%进行治疗（5回合）"效果 CD5->4
        SkillBuilder.createNewSkill(this, 必杀技)
                .when(SelfTrigger.act(this, 释放必杀))
                .act(DamageAction.create(this, 必杀伤害).multi(0.0).build())
                .build();
        //以攻击力50%对我方全体进行治疗

        //普通攻击
        SkillBuilder.createNewSkill(this, 普攻)
                .when(SelfTrigger.act(this, 释放普攻))
                .act(DamageAction.create(this, 普通伤害).multi(0.0).build())
                .build();
        //队长技 使我方全体必杀技伤害增加25%

        //普攻时，触发“以攻击力25%对我方HP最低者进行治疗" hp最低应该是百分比（吃必杀技加成）

        //攻击时，触发”以自身攻击力20%使我方全体攻击力增加(1回合）“（右侧效应）
        SkillBuilder.createNewSkill(this, SkillType.三星被动)
                .when(SelfTrigger.act(this, 攻击后))
                .act(BuffAction.create(this, BuffType.攻击力数值增加).multi(0.2).toAlly()
                        .name(this+"的攻击后20%增攻").lastedTurn(1).build())
                .build();

        // 必杀时，触发使我方全体必杀技伤害增加25%（1回合）
        if(star >= 5) {
            SkillBuilder.createNewSkill(this, SkillType.五星被动)
                    .when(SelfTrigger.act(this, 释放必杀后))
                    .act(BuffAction.create(this, BuffType.必杀技伤害增加).multi(0.25).toAlly()
                            .name(this+"大招后队友必杀技伤害提高25%").lastedTurn(1).build())
                    .build();
        }
        //防御减伤

        //免疫沉默


    }

    @Override
    public String toString() {
        return super.toString();
    }
}
