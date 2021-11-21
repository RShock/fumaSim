package xiaor.charas;

import xiaor.skill.UniqueBuff;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.Action;
import xiaor.skillbuilder.action.BuffType;
import xiaor.tools.Tools;
import xiaor.tools.TriggerEnum;
import xiaor.tools.TriggerManager;

import java.util.Optional;

public class 时之裂缝_复旦 extends Chara {
    Double 必杀伤害加成;

    public static 时之裂缝_复旦 init(String s) {
        时之裂缝_复旦 复旦 = new 时之裂缝_复旦();
        复旦.name = "复旦";
        复旦.element = Element.光属性;
        复旦.role = Role.守护者;
        复旦.life = 73252441;
        复旦.attack =  1441837;
        baseInit(复旦, s);
        return 复旦;
    }

    @Override
    public void initSkills() {
        // 剧本  这里假设boss护盾持续时间为无限只能被打破
        //丑陋的代码 因为小精灵王会加成诺诺可的护盾
        Action action = new Action();
        action.setAction(pack -> {
            if(必杀伤害加成 < 0.4) {
                必杀伤害加成 += 0.05;
            }
            int shieldInc;
            Optional<UniqueBuff> 来自小精灵王的buff = TriggerManager.getSkill().stream()
                    .filter(skill -> skill instanceof UniqueBuff)
                    .map(skill -> (UniqueBuff)skill)
                    .filter(buff -> buff.getBuffType().equals(BuffType.受到攻击者伤害增加))
                    .findFirst();
            shieldInc = 来自小精灵王的buff.map(buff -> (int) (attack * 0.5 * (1 + 必杀伤害加成 + buff.currentLevel * 0.06)))
                    .orElseGet(() -> (int) (attack * 0.5 * (1 + 必杀伤害加成)));
            Tools.log(Tools.LogColor.YELLOW, "【boss动作】诺诺可为自己添加了"+ shieldInc + "点护盾");
            this.shield += shieldInc;
            return true;
        });
        SkillBuilder.createNewSkill(this, SkillType.被动)
                .when(TriggerEnum.回合结束)
                .act(action)
                .build();
    }
}
