package xiaor.charas;

import xiaor.skill.buff.UniqueBuff;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.SkillType;
import xiaor.skillbuilder.action.Action;
import xiaor.skill.BuffType;
import xiaor.tools.Tools;
import xiaor.trigger.TriggerEnum;
import xiaor.trigger.TriggerManager;

import java.util.Optional;

public class 五十四层boss_诺诺可 extends Chara {
    Double 必杀伤害加成;

    public static 五十四层boss_诺诺可 init(String s) {
        五十四层boss_诺诺可 诺诺可 = new 五十四层boss_诺诺可();
        诺诺可.name = "诺诺可";
        诺诺可.element = Element.水属性;
        诺诺可.role = Role.攻击者;
        诺诺可.life = 73252441;
        诺诺可.attack =  1441837;
        诺诺可.必杀伤害加成 = 0.1;
        baseInit(诺诺可, s);
        return 诺诺可;
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
                    .filter(buff -> buff.getBuffType().equals(BuffType.受到攻击者伤害))
                    .findFirst();
            shieldInc = 来自小精灵王的buff.map(buff -> (int) (attack * 0.5 * (1 + 必杀伤害加成 + buff.currentLevel * 0.06)))
                    .orElseGet(() -> (int) (attack * 0.5 * (1 + 必杀伤害加成)));
            Tools.log(Tools.LogColor.YELLOW, "【boss动作】诺诺可为自己添加了"+ shieldInc + "点护盾");
            this.shield += shieldInc;
        });
        SkillBuilder.createNewSkill(this, SkillType.一星被动)
                .when(TriggerEnum.回合结束)
                .act(action)
                .build();
    }
}
