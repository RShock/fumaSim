package xiaor.core.charas;

import xiaor.core.damageCal.DamageBase;
import xiaor.core.skillbuilder.SkillBuilder;
import xiaor.core.skillbuilder.action.DamageAction;
import xiaor.core.skillbuilder.skill.BaseSkill;
import xiaor.core.skillbuilder.trigger.Trigger;
import xiaor.core.trigger.TriggerManager;

import static xiaor.core.skillbuilder.action.DamageAction.DamageType.普通伤害;
import static xiaor.core.trigger.TriggerEnum.释放普攻;

public class 超级机器人木桩 extends Chara {
    public static 超级机器人木桩 init(String s) {
        超级机器人木桩 木桩 = new 超级机器人木桩();
        木桩.name = "木桩";
        木桩.role = Role.攻击者;
        木桩.life = 2147483192;
        木桩.baseAttack = 100;

        baseInit(木桩, s);
        return 木桩;
    }

    @Override
    public void initSkills() {
        BaseSkill 木桩的普攻 = SkillBuilder.createNewSkill("木桩的普攻")
                .when(Trigger.selfAct(this, 释放普攻))
                .act(DamageAction.create(普通伤害).multi(1.0).to(tar -> tar).times(1).damageBase(DamageBase.攻击).build())
                .build(this,-999);
    }
}
