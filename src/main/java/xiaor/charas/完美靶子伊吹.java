package xiaor.charas;

import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skillbuilder.skill.BuffType;
import xiaor.trigger.TriggerEnum;

public class 完美靶子伊吹 extends Chara {

    public static 完美靶子伊吹 init(String s) {
        完美靶子伊吹 伊吹 = new 完美靶子伊吹();
        伊吹.name = "伊吹";
        伊吹.element = Element.火属性;
        伊吹.role = Role.攻击者;
        伊吹.life = 204337481;
//        伊吹.attack =  1441837;
        baseInit(伊吹, s);
        return 伊吹;
    }

    @Override
    public void initSkills() {
        //受伤减少50%
        SkillBuilder.createNewSkill(this, SkillType.一星被动)
                .when(TriggerEnum.游戏开始时)
                .act(BuffAction.create(this, BuffType.受到伤害)
                        .multi(-0.5).toSelf().name("受伤减少50%").build())
                .build();

        //属性相克效果减少50%
        SkillBuilder.createNewSkill(this, SkillType.一星被动)
                .when(TriggerEnum.游戏开始时)
                .act(BuffAction.create(this, BuffType.属性相克效果)
                        .multi(0.5).toSelf().name("属性相克效果减少50%").build())
                .build();
    }
}
