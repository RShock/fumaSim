package xiaor.charas;


import xiaor.*;

import java.util.function.Function;


public class 胆小纸袋狼_沃沃 extends BaseChara {

    public 胆小纸袋狼_沃沃() {
        initSkills();
    }

    @Override
    public void skill(Chara acceptor) {

    }

    @Override
    public void defense(Chara acceptor) {

    }

    @Override
    public void initSkills() {
        //必杀
        TriggerManager.getInstance().registerSkill(
                BaseSkill.builder()
                        .trigger(Trigger.SKILL)
                        .check(this::self)
                        .cast(pack ->
                                getSkill(getSkillLevel()).apply(pack)
                        )
                        .build());

        //普攻技能
        TriggerManager.getInstance().registerNormalAttack(this, 1.00);

    }

    @Override
    public Function<MessagePack, Boolean> getSkill(int level) {
        double[] multi = {0.96, 1.18, 1.41, 1.63, 1.86};
        return messagePack -> {
            TriggerManager.getInstance().registerSkillAttack(this, 2.00);
            TriggerManager.getInstance().registerSelfAttackInc(this, multi[level]);
            return true;
        };
    }
}
