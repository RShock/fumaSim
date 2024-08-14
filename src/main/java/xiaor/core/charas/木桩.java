package xiaor.core.charas;

import xiaor.core.damageCal.DamageBase;
import xiaor.core.damageCal.DamageCal;
import xiaor.core.msgpack.DamageRecordPack;
import xiaor.core.msgpack.MessagePack;
import xiaor.core.skillbuilder.SkillBuilder;
import xiaor.core.skillbuilder.action.Action;
import xiaor.core.skillbuilder.action.DamageAction;

import static xiaor.core.skillbuilder.SkillType.普攻;
import static xiaor.core.skillbuilder.action.DamageAction.DamageType.普通伤害;
import static xiaor.core.trigger.TriggerEnum.释放普攻;

public class 木桩 extends Chara {
    public static 木桩 init(String s) {
        木桩 木桩 = new 木桩();
        木桩.name = "木桩";
        baseInit(木桩, s);
        return 木桩;
    }

    @Override
    public void initSkills() {
//        Action action = new Action();
//         action.setAction(pack -> {
//                new DamageCal(((MessagePack)pack)).normalAttack(1.0, DamageBase.攻击, 1);});
//        SkillBuilder.createNewSkill("木桩的普攻")
//                .when(释放普攻)
//
//                .act(action)
//                .build();
    }
}
